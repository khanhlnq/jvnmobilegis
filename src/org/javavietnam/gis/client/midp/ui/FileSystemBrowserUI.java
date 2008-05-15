/*
 * $Id$
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
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
 * khanh.lnq AT gmail.com
 *
 * @version: 1.0
 * TODO Binh: Change author and date informations
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.*;

import org.javavietnam.gis.client.midp.model.MessageCodes;
import org.javavietnam.gis.shared.midp.ApplicationException;

// TODO Binh: Format all source files using NetBeans Source/Format menu
public class FileSystemBrowserUI extends List implements CommandListener {

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

	private UIController uiController;
	private FileConnection fileConnection;
	private String currPath;
	private Image dirIcon;
	private Image fileIcon;
	private Enumeration enumeration;
	private Command save;
	private Command saveAs;
	private Command view;
	private Command properties;
	private Command back;

	public FileSystemBrowserUI(UIController uiController) {
		super(uiController.getString(UIConstants.FILE_SYSTEM), List.IMPLICIT);
		this.uiController = uiController;
		setCurrPath(MEGA_ROOT);
		dirIcon = this.uiController.getImage(UIConstants.ICON_DIR);
		fileIcon = this.uiController.getImage(UIConstants.ICON_FILE);

                // TODO Binh: Hide View command for now. We already have SELECT_COMMAND
		view = new Command(uiController.getString(UIConstants.VIEW),
				Command.SCREEN, 1);
		save = new Command(uiController.getString(UIConstants.SAVE),
				Command.SCREEN, 2);
		saveAs = new Command(uiController.getString(UIConstants.SAVE_AS),
				Command.SCREEN, 3);
		// TODO Binh: Hide Properties command for now
                properties = new Command(
				uiController.getString(UIConstants.PROPERTIES), Command.SCREEN,
				4);
		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 5);
	}

	public void getCurrDir() throws IOException {
		if (MEGA_ROOT.equals(currPath)) {
			enumeration = FileSystemRegistry.listRoots();
			this.deleteAll();
		} else {
			fileConnection = (FileConnection) Connector
					.open("file://localhost/" + currPath);
			enumeration = fileConnection.list();
			// not root - draw UP_DIRECTORY
			this.deleteAll();
			append(UP_DIRECTORY, dirIcon);
		}

		while (enumeration.hasMoreElements()) {
			String fileName = (String) enumeration.nextElement();

			if (fileName.charAt(fileName.length() - 1) == SEP) {
				// This is directory
				append(fileName, dirIcon);
			} else {
				// this is regular file
				append(fileName, fileIcon);
			}
		}

		addCommand(view);

		// Do not allow creating files/directories beside root
		if (!currPath.equals(MEGA_ROOT)) {
			addCommand(save);
			addCommand(saveAs);
			addCommand(properties);
		} else {
			removeCommand(save);
			removeCommand(saveAs);
			removeCommand(properties);
		}

		addCommand(back);

		uiController.setCommands(this);

		setCommandListener(this);

		if (fileConnection != null) {
			fileConnection.close();
		}
	}

	public void commandAction(Command command, Displayable display) {
		String label = getString(getSelectedIndex());
		if (List.SELECT_COMMAND == command) {
			if (label.endsWith(SEP_STR) || label.endsWith(UP_DIRECTORY)) {
				uiController.browseFileSystemRequested(display);
			} else {
				uiController.saveMapToFileRequested(display);
			}
		}
		if (command == view) {
			uiController.browseFileSystemRequested(display);
		} else if (command == save) {
			if (label.endsWith(SEP_STR) || label.endsWith(UP_DIRECTORY)) {
				uiController
						.showErrorAlert(
								new ApplicationException(
										uiController
												.getMessage(MessageCodes.ERROR_CAN_NOT_SAVE_MAP_TO_DIR)),
								display);
			} else {
				uiController.saveMapToFileRequested(display);
			}
		} else if (command == saveAs) {
			uiController.saveAsMapToFileRequested();
		} else if (command == properties) {
			if (label.endsWith(UP_DIRECTORY)) {
				uiController
						.showErrorAlert(
								new ApplicationException(
										uiController
												.getMessage(MessageCodes.ERROR_CAN_NOT_VIEW_UP_DIR_PROPS)),
								display);
			} else {
				uiController.viewPropertiesRequested(display);
			}
		} else if (command == back) {
			uiController.viewMapRequested();
		} else {
			uiController.commandAction(command, display);
		}
	}

	/**
	 * @return the currDirName
	 */
	public String getCurrPath() {
		return currPath;
	}

	/**
	 * @param currDirName
	 *            the currDirName to set
	 */
	public void setCurrPath(String currPath) {
		this.currPath = currPath;
	}
}
