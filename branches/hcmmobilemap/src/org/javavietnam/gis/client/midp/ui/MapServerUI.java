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

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 * @author Khanh
 */
class MapServerUI extends Form implements CommandListener {

    private UIController uiController;
    private TextField serverField;
    private Command fetchCommand;

    public MapServerUI(UIController uiController, String serviceURL) {
        super(uiController.getString(UIConstants.SERVER_UI_TITLE));
        try {
            this.uiController = uiController;

            serverField = new TextField(uiController
                    .getString(UIConstants.GET_CAPABILITIES_TITLE), serviceURL,
                    255, TextField.URL);

            uiController.mapServersCmd.addCommands(this);

            append(serverField);

            fetchCommand = new Command(uiController
                    .getString(UIConstants.GET_CAPABILITIES_CMD),
                    Command.SCREEN, 0);

            addCommand(fetchCommand);
            uiController.setCommands(this);

            setCommandListener(this);
        } catch (Exception e) {
            System.out.println("*************** Exception: ");
            e.printStackTrace();
        }
    }

    public String getServerURL() {
        return serverField.getString();
    }

    public void setServerURL(String serviceURL) {
        serverField.setString(serviceURL);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (fetchCommand == command) {
            uiController.getCapabilitiesRequested();
        } else if (uiController.mapServersCmd.mapServer01 == command) {
            serverField.setString(uiController
                    .getString(UIConstants.MAPSERVER_01_URL));
        } else if (uiController.mapServersCmd.mapServer02 == command) {
            serverField.setString(uiController
                    .getString(UIConstants.MAPSERVER_02_URL));
        } else if (uiController.mapServersCmd.mapServer03 == command) {
            serverField.setString(uiController
                    .getString(UIConstants.MAPSERVER_03_URL));
        } else {
            uiController.commandAction(command, displayable);
        }
    }

}