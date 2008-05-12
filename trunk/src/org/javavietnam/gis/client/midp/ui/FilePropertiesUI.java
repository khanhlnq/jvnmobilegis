package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class FilePropertiesUI extends Form implements CommandListener {
	private UIController uiController;
	private FileSystemBrowserUI fileSystemBrowserUI;
	private FileConnection fileConnection;
	private ChoiceGroup attrs;
	private Command back;

	public static final String[] attrList = { "Read", "Write", "Hidden" };

	public FilePropertiesUI(UIController uiController,
			FileSystemBrowserUI fileSystemBrowserUI) {
		super("");
		this.uiController = uiController;
		this.fileSystemBrowserUI = fileSystemBrowserUI;

		attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE, attrList, null);

		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 2);

	}

	public void getProperties(String fileName) throws IOException{
		fileConnection = (FileConnection) Connector
				.open("file://localhost/"
						+ fileSystemBrowserUI.getCurrPath() + fileName);
		if (fileName.endsWith(FileSystemBrowserUI.UP_DIRECTORY)) {
			return;
		}

		if (!fileConnection.exists()) {
			throw new IOException("File does not exists");
		}
		attrs.setSelectedFlags(new boolean[] { fileConnection.canRead(),
				fileConnection.canWrite(), fileConnection.isHidden() });

		append(new StringItem("Location:", fileName));
		append(new StringItem("Type: ",
				fileConnection.isDirectory() ? "Directory" : "Regular File"));
		append(new StringItem("Modified:", myDate(fileConnection
				.lastModified())));
		append(attrs);

		addCommand(back);
		setCommandListener(this);

		fileConnection.close();
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
		sb.append(FileSystemBrowserUI.monthList[cal.get(Calendar.MONTH)]);
		sb.append(' ');
		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}

	public void commandAction(Command command, Displayable display) {
		if (command == back) {
			uiController.viewFileSystemBrowserUIRequested();
		} 
	}
}
