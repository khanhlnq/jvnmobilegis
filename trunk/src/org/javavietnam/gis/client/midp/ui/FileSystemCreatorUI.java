// TODO Binh: Add Java file header

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.*;

public class FileSystemCreatorUI extends Form implements CommandListener {
	private UIController uiController;
	private TextField nameInput;
	private Command creatOK;
	private Command back;

	public FileSystemCreatorUI(UIController uiController) {
		super("New File");
		this.uiController = uiController;
		nameInput = new TextField("Enter Name", null, 256, TextField.ANY);

		creatOK = new Command(uiController.getString(UIConstants.OK),
				Command.OK, 1);
		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 2);

		append(nameInput);
		addCommand(creatOK);
		addCommand(back);
		setCommandListener(this);
	}

	public void commandAction(Command command, Displayable display) {
		if (command == creatOK) {
		    uiController.saveMapToFileRequested();
		} else if (command == back) {
			uiController.viewFileSystemBrowserUIRequested();
		}
	}

	/**
	 * @return the nameInput
	 */
	public TextField getNameInput() {
		return nameInput;
	}

}
