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

import javax.microedition.lcdui.*;

import org.javavietnam.gis.client.midp.model.MessageCodes;
import org.javavietnam.gis.shared.midp.ApplicationException;

public class FileSystemCreatorUI extends Form implements CommandListener {
	private UIController uiController;
	private TextField nameInput;
	private Command creatOK;
	private Command back;

	public FileSystemCreatorUI(UIController uiController) {
		super(uiController.getString(UIConstants.SAVE_AS));
		this.uiController = uiController;
		nameInput = new TextField(
				uiController.getString(UIConstants.FILE_NAME), null, 256,
				TextField.ANY);

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
			if (getNameInput().getString().equals("")) {
				uiController.showErrorAlert(new ApplicationException(
						uiController
								.getMessage(MessageCodes.MISSING_NAME_INPUT)),
						display);
			} else {
				uiController.saveMapToFileRequested(null);
			}
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
