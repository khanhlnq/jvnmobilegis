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
 * @version: 1.0.3
 * @author: Binh Pham
 * @Date Created: 09 May 2008
 */
package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.*;

import org.javavietnam.gis.client.midp.model.MessageCodes;
import org.javavietnam.gis.shared.midp.ApplicationException;

public class FileSystemCreatorUI extends Form implements CommandListener {

    private UIController uiController;
    private boolean itemExisting;
    private TextField nameInput;
    private Command saveOK;
    private Command back;

    public FileSystemCreatorUI(UIController uiController) {
        super(uiController.getString(UIConstants.SAVE_AS));
        this.uiController = uiController;

        nameInput = new TextField(
                uiController.getString(UIConstants.FILE_NAME), null, 256,
                TextField.ANY);
        saveOK = new Command(uiController.getString(UIConstants.OK),
                Command.OK, 1);
        back = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 2);

        append(nameInput);
        addCommand(saveOK);
        addCommand(back);

        setCommandListener(this);
    }

    public void commandAction(Command command, Displayable display) {
        if (command == saveOK) {
            if (getNameInputValue().equals("")) {
                uiController.showErrorAlert(new ApplicationException(
                        this.uiController.getMessage(MessageCodes.MISSING_NAME_INPUT)),
                        display);
            } else {
                uiController.executeSaveMapToFile();
            }
        } else if (command == back) {
            uiController.fileSystemBrowserUIRequested();
        }
    }

    public String getNameInputValue() {
        return nameInput.getString();
    }

    public void setNameInputValue(String nameInput) {
        this.nameInput.setString(nameInput);
    }

    public boolean isItemExisting() {
        return itemExisting;
    }

    public void setItemExisting(boolean itemExisting) {
        this.itemExisting = itemExisting;
    }

    private void callKeyPressed(int keyCode) {
    }
}
