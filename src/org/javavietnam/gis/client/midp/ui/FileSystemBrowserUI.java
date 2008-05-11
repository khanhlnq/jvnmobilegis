package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

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
	private Command view;
	private Command properties;
	private Command create;
	private Command delete;
	private Command back;
	private Command exit;

	public FileSystemBrowserUI(UIController uiController) {
		super(uiController.getString(UIConstants.FILE_SYSTEM), List.IMPLICIT);
		this.uiController = uiController;
		currPath = MEGA_ROOT;
		dirIcon = this.uiController.getImage(UIConstants.ICON_DIR);
		fileIcon = this.uiController.getImage(UIConstants.ICON_FILE);

		exit = new Command(uiController.getString(UIConstants.EXIT),
				Command.EXIT, 1);
		view = new Command(uiController.getString(UIConstants.VIEW),
				Command.SCREEN, 1);
		create = new Command(uiController.getString(UIConstants.NEW),
				Command.SCREEN, 2);
		save = new Command(uiController.getString(UIConstants.SAVE),
				Command.SCREEN, 3);
		properties = new Command(
				uiController.getString(UIConstants.PROPERTIES), Command.SCREEN,
				4);
		delete = new Command(uiController.getString(UIConstants.DELETE),
				Command.SCREEN, 5);
		back = new Command(uiController.getString(UIConstants.BACK),
				Command.SCREEN, 6);
	}

	public void getCurrDir() throws IOException{
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
			addCommand(properties);
			addCommand(create);
			addCommand(delete);
		}

		addCommand(back);

		uiController.setCommands(this);

		setCommandListener(this);

		if (fileConnection != null) {
			fileConnection.close();
		}

	}

	public void commandAction(Command command, Displayable display) {
		// TODO Auto-generated method stub
		if (command == save) {
			uiController.viewSaveToFileInputRequested();
		} else if (command == view) {
			uiController.browseFileSystemRequested(display);
		} else if (command == properties) {
			uiController.viewPropertiesRequested(display);
		} else if (command == create) {
			uiController.viewFileSystemCreatorRequested();
		} else if (command == delete) {
			uiController.deleteItemRequested(display);
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
