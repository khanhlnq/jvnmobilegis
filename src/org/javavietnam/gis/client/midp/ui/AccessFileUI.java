/*
 * $Id: ConfirmDialogUI.java 169 2007-12-26 03:46:34Z phamvubinh123 $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/ConfirmDialogUI.java $
 * $Author: phamvubinh123 $
 * $Revision: 169 $
 * $Date: 2007-12-26 10:46:34 +0700 (Wed, 26 Dec 2007) $
 *
 * ====================================================================
 *
 * Copyright (C) 2006-2007 by JVNGIS
 *
 * All copyright notices regarding JVNMobileGIS MUST remain
 * intact in the Java codes and resource files.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Support can be obtained from project homepage at:
 * http://code.google.com/p/jvnmobilegis/
 *
 * Correspondence and Marketing Questions can be sent to:
 * phamvubinh123 AT gmail.com
 * @version: 1.0
 * @author: Binh Pham
 * @Date Created: 22 Dec 2007
 */

package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

public class AccessFileUI extends Form implements CommandListener, Runnable{
	private UIController uiController;
	private Enumeration e = null;
	public static final String[] attrList = { "Read", "Write", "Hidden" };
	public static final String[] typeList = { "Regular File", "Directory" };
	public static final String[] monthList = { "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	/* special string denotes upper directory */
	public static final String UP_DIRECTORY = "..";

	/*
	 * special string that denotes upper directory accessible by this browser.
	 * this virtual directory contains all roots.
	 */
	public static final String MEGA_ROOT = "/";

	/* separator string as defined by FC specification */
	public static final String SEP_STR = "/";

	/* separator character as defined by FC specification */
	public static final char SEP = '/';
	public String currDirName;
	public Command save = new Command("Save", Command.OK, 1);
	private Command view = new Command("View", Command.ITEM, 1);
	private Command prop = new Command("Properties", Command.ITEM, 2);
	private Command create = new Command("New", Command.ITEM, 2);
	private Command delete = new Command("Delete", Command.ITEM, 3);
	private Command exit = new Command("Exit", Command.EXIT, 3);

	// add delete file functionality

	public Command creatOK = new Command("OK", Command.OK, 1);
	private Command back = new Command("Back", Command.BACK, 2);
	private TextField nameInput; // Input field for new file name
	private ChoiceGroup typeInput; // Input field for file type (regular/dir)
	public Image dirIcon;
	public Image fileIcon;
	public Image[] iconList;

	public AccessFileUI(UIController uiController) {
		super("");
		this.uiController = uiController;
		currDirName = MEGA_ROOT;

		try {
			dirIcon = Image.createImage("/icons/dir.png");
		} catch (IOException e) {
			dirIcon = null;
		}

		try {
			fileIcon = Image.createImage("/icons/file.png");
		} catch (IOException e) {
			fileIcon = null;
		}

		iconList = new Image[] { fileIcon, dirIcon };
	}

	public void commandAction(Command c, Displayable display) {
		if (c == save) {
			uiController.saveMapToFileRequested();
		} else if (c == view) {
			uiController.viewFileOrDirRequested(display);
		} else if (c == prop) {
			uiController.viewPropertiesRequested(display);
		} else if (c == create) {
			createFileOrDir();
		} else if (c == creatOK) {
			uiController.createOKRequested(nameInput, typeInput);
		} else if (c == back) {
			uiController.showCurrDir();
		} else if (c == exit) {
			
		} else if (c == delete) {
			uiController.deleteFileOrFolderRequested(display);
		}
	}

	public Displayable createFileOrDir() {
		Form creator = new Form("New File");
		nameInput = new TextField("Enter Name", null, 256, TextField.ANY);
		typeInput = new ChoiceGroup("Enter File Type", Choice.EXCLUSIVE,
				typeList, iconList);
		creator.append(nameInput);
		creator.append(typeInput);
		creator.addCommand(creatOK);
		creator.addCommand(back);
		creator.addCommand(exit);
		creator.setCommandListener(this);
		return creator;
	}
	
	public void removeCreatingCommand() {
		this.removeCommand(creatOK);
		this.removeCommand(back);
	}
	
	/**
     * Show file list in the current directory .
     */
	public void showCurrDir(Display display) {
        FileConnection currDir = null;
        List browser;

        try {
            if (MEGA_ROOT.equals(currDirName)) {
                new Thread(this).start(); 
                browser = new List(currDirName, List.IMPLICIT);
            } else {
                currDir = (FileConnection)Connector.open("file://localhost/" + currDirName);
                e = currDir.list();
                browser = new List(currDirName, List.IMPLICIT);
                // not root - draw UP_DIRECTORY
                browser.append(UP_DIRECTORY, dirIcon);
            }

            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();

                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    browser.append(fileName, dirIcon);
                } else {
                    // this is regular file
                    browser.append(fileName, fileIcon);
                }
            }

            browser.setSelectCommand(view);

            //Do not allow creating files/directories beside root
            if (!MEGA_ROOT.equals(currDirName)) {
                browser.addCommand(prop);
                browser.addCommand(create);
                browser.addCommand(delete);
            }

            browser.addCommand(exit);

            browser.setCommandListener(this);

            if (currDir != null) {
                currDir.close();
            }

            display.setCurrent(browser);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
	
	
	public void run() {
		// Get list root
		e = FileSystemRegistry.listRoots();
	}
	
	public void showFile(String fileName, Display display) {
        try {
            FileConnection fc =
                (FileConnection)Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            InputStream fis = fc.openInputStream();
            byte[] b = new byte[1024];

            int length = fis.read(b, 0, 1024);

            fis.close();
            fc.close();

            TextBox viewer =
                new TextBox("View File: " + fileName, null, 1024,
                    TextField.ANY | TextField.UNEDITABLE);

            viewer.addCommand(back);
            viewer.addCommand(exit);
            viewer.setCommandListener(this);

            if (length > 0) {
                viewer.setString(new String(b, 0, length));
            }

            display.setCurrent(viewer);
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
        }
    }
	
	public void checkDeleteFolder(String folderName, Display display) {
        try {
            FileConnection fcdir =
                (FileConnection)Connector.open("file://localhost/" + currDirName + folderName);
            Enumeration content = fcdir.list("*", true);

            //only empty directory can be deleted
            if (!content.hasMoreElements()) {
                fcdir.delete();
                showCurrDir(display);
            } else {
                Alert cantDeleteFolder =
                    new Alert("Error!", "Can not delete The non-empty folder: " + folderName, null,
                        AlertType.ERROR);
                cantDeleteFolder.setTimeout(Alert.FOREVER);
                display.setCurrent(cantDeleteFolder);
            }
        } catch (IOException ioe) {
            System.out.println(currDirName + folderName);

            ioe.printStackTrace();
        }
    }
	
	public void createFile(String newName, boolean isDirectory, Display display) {
        try {
            FileConnection fc = (FileConnection)Connector.open("file:///" + currDirName + newName);

            if (isDirectory) {
                fc.mkdir();
            } else {
                fc.create();
            }

            showCurrDir(display);
        } catch (Exception e) {
            String s = "Can not create file '" + newName + "'";

            if ((e.getMessage() != null) && (e.getMessage().length() > 0)) {
                s += ("\n" + e);
            }

            Alert alert = new Alert("Error!", s, null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
            // Restore the commands that were removed in commandAction()
            display.getCurrent().addCommand(creatOK);
            display.getCurrent().addCommand(back);
        }
    }
	
	public void delete(String currFile, Display display) {
        if (!currFile.equals(AccessFileUI.UP_DIRECTORY)) {
            if (currFile.endsWith(AccessFileUI.SEP_STR)) {
                checkDeleteFolder(currFile, display);
            } else {
                deleteFile(currFile, display);
                showCurrDir(display);
            }
        } else {
            Alert cantDeleteFolder =
                new Alert("Error!",
                    "Can not delete The up-directory (..) " + "symbol! not a real folder", null,
                    AlertType.ERROR);
            cantDeleteFolder.setTimeout(Alert.FOREVER);
            display.setCurrent(cantDeleteFolder);
        }
    }
	
	public void deleteFile(String fileName, Display display) {
        try {
            FileConnection fc = (FileConnection)Connector.open("file:///" + currDirName + fileName);
            fc.delete();
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access/delete file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
        }
    }
	
	public void showProperties(String fileName, Display display) {
        try {
            if (fileName.equals(AccessFileUI.UP_DIRECTORY)) {
                return;
            }

            FileConnection fc =
                (FileConnection)Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            Form props = new Form("Properties: " + fileName);
            ChoiceGroup attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE, attrList, null);

            attrs.setSelectedFlags(new boolean[] { fc.canRead(), fc.canWrite(), fc.isHidden() });

            props.append(new StringItem("Location:", currDirName));
            props.append(new StringItem("Type: ", fc.isDirectory() ? "Directory" : "Regular File"));
            props.append(new StringItem("Modified:", myDate(fc.lastModified())));
            props.append(attrs);

            props.addCommand(back);
            props.addCommand(exit);
            props.setCommandListener(this);

            fc.close();

            display.setCurrent(props);
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
        }
    }
	
	public void traverseDirectory(String fileName, Display display) {
        /* In case of directory just change the current directory
         * and show it
         */
        if (currDirName.equals(AccessFileUI.MEGA_ROOT)) {
            if (fileName.equals(AccessFileUI.UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }

            currDirName = fileName;
        } else if (fileName.equals(AccessFileUI.UP_DIRECTORY)) {
            // Go up one directory
            int i = currDirName.lastIndexOf(AccessFileUI.SEP, currDirName.length() - 2);

            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = AccessFileUI.MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }

        showCurrDir(display);
    }
	
	private String myDate(long time) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(time));

        StringBuffer sb = new StringBuffer();

        sb.append(cal.get(Calendar.HOUR_OF_DAY));
        sb.append(':');
        sb.append(cal.get(Calendar.MINUTE));
        sb.append(':');
        sb.append(cal.get(Calendar.SECOND));
        sb.append(',');
        sb.append(' ');
        sb.append(cal.get(Calendar.DAY_OF_MONTH));
        sb.append(' ');
        sb.append(AccessFileUI.monthList[cal.get(Calendar.MONTH)]);
        sb.append(' ');
        sb.append(cal.get(Calendar.YEAR));

        return sb.toString();
    }
}
