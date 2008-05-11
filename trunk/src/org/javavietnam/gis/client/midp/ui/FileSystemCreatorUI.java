package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
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
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

public class FileSystemCreatorUI extends Form implements CommandListener {
	private UIController uiController;
	private FileSystemBrowserUI fileSystemBrowserUI;
	private FileConnection fileConnection;
	private Image dirIcon;
	private Image fileIcon;
	private Image[] iconList;
	private TextField nameInput;
	private ChoiceGroup typeInput;
	private Command creatOK;
	private Command back;
	private Command exit;

	private boolean isSaveToFileInput;

	public static final String[] typeList = { "Regular File", "Directory" };

	public FileSystemCreatorUI(UIController uiController,
			FileSystemBrowserUI fileSystemBrowserUI) {
		super("New File");
		this.uiController = uiController;
		this.fileSystemBrowserUI = fileSystemBrowserUI;
		dirIcon = this.uiController.getImage(UIConstants.ICON_DIR);
		fileIcon = this.uiController.getImage(UIConstants.ICON_FILE);
		iconList = new Image[] { fileIcon, dirIcon };
		nameInput = new TextField("Enter Name", null, 256, TextField.ANY);
		typeInput = new ChoiceGroup("Enter File Type", Choice.EXCLUSIVE,
				typeList, iconList);

		creatOK = new Command(uiController.getString(UIConstants.OK),
				Command.OK, 1);
		exit = new Command(uiController.getString(UIConstants.EXIT),
				Command.EXIT, 1);
		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 2);

		append(nameInput);
		append(typeInput);
		addCommand(creatOK);
		addCommand(back);
		addCommand(exit);
		isSaveToFileInput = false;
		setCommandListener(this);
	}

	public void commandAction(Command command, Displayable display) {
		// TODO Auto-generated method stub
		if (command == creatOK) {
			if (isSaveToFileInput == false) {
				uiController.createOKRequested(nameInput, typeInput);
			} else {
				uiController.saveMapToFileRequested();
			}
		} else if (command == back) {
			uiController.backToFileSystemBrowserUIRequested();
		} 
	}

	public void removeTypeInput() {
		// Delete typeInput item.
		if (this.size() == 2) {
			delete(1);
			isSaveToFileInput = !isSaveToFileInput;
		}
	}

	public void addTypeInput() {
		if (this.size() == 1) {
			append(typeInput);
			isSaveToFileInput = !isSaveToFileInput;
		}

	}

	/**
	 * @return the nameInput
	 */
	public TextField getNameInput() {
		return nameInput;
	}

}
