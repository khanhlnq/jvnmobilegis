package org.javavietnam.gis.client.midp.ui;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

public class FileContentUI extends Form implements CommandListener {
	private UIController uiController;
	private FileSystemBrowserUI fileSystemBrowserUI;
	private String fileName;
	private FileConnection fileConnection;
	private DataInputStream dis;
	private InputStream is;
	private String textContent;
	private Image imageContent;
	private Command back;
	private Command exit;

	public FileContentUI(UIController uiController,
			FileSystemBrowserUI fileSystemBrowserUI) {
		super("");
		this.uiController = uiController;
		this.fileSystemBrowserUI = fileSystemBrowserUI;

		exit = new Command(uiController.getString(UIConstants.EXIT),
				Command.EXIT, 1);
		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 1);

		addCommand(back);
		addCommand(exit);
		setCommandListener(this);
	}

	public void getFileContent(String fileName) throws IOException {
		this.deleteAll();
		fileConnection = (FileConnection) Connector
				.open("file://localhost/"
						+ fileSystemBrowserUI.getCurrPath() + fileName);
		setTitle("View file: " + fileName);

		if (!fileConnection.exists()) {
			throw new IOException("File does not exists");
		}

		int fileSize = (int)fileConnection.fileSize();
		
		if (fileSize > 0) {
			if (fileName.endsWith(".png")) {
				dis = fileConnection.openDataInputStream();
				byte[] data = new byte[fileSize];
				
				dis.read(data);
				imageContent = Image.createImage(data, 0, data.length);
				append(imageContent);
			} else {
				is = fileConnection.openInputStream();
				byte[] data = new byte[fileSize];
				
				is.read(data);
				append(new String(data, 0, data.length));
			}
		}

		if (is != null) {
			is.close();
		}
		if (dis != null) {
			dis.close();
		}
		fileConnection.close();
	}

	public void commandAction(Command command, Displayable display) {
		// TODO Auto-generated method stub
		if (command == back) {
			uiController.backToFileSystemBrowserUIRequested();
		}
	}
}
