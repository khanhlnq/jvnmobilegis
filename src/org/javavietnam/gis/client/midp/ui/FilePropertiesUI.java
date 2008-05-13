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
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

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
	private StringItem location, type, dateModified;
	private Command back;

	public static final String[] attrList = { "Read", "Write", "Hidden" };

	public FilePropertiesUI(UIController uiController,
			FileSystemBrowserUI fileSystemBrowserUI) {
		super("");
		this.uiController = uiController;
		this.fileSystemBrowserUI = fileSystemBrowserUI;

		attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE, attrList, null);

		location = new StringItem("Location:", null);
		type = new StringItem("Type: ", null);
		dateModified = new StringItem("Modified:", null);

		back = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 2);
		
		append(location);
		append(type);
		append(dateModified);
		append(attrs);

		addCommand(back);
		setCommandListener(this);

	}

	public void getProperties(String fileName) throws IOException {
		fileConnection = (FileConnection) Connector.open("file://localhost/"
				+ fileSystemBrowserUI.getCurrPath() + fileName);
		if (fileName.endsWith(FileSystemBrowserUI.UP_DIRECTORY)) {
			return;
		}

		if (!fileConnection.exists()) {
			throw new IOException("File does not exists");
		}

		attrs.setSelectedFlags(new boolean[] { fileConnection.canRead(),
				fileConnection.canWrite(), fileConnection.isHidden() });
		
		System.gc();

		location.setText(fileName);
		type.setText(fileConnection.isDirectory() ? "Directory"
				: "Regular File");
		dateModified.setText(myDate(fileConnection.lastModified()));

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
