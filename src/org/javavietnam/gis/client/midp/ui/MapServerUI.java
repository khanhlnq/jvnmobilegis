/*
 * MapServerUI.java
 *
 * Created on May 3, 2006, 4:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;


/**
 * @author  Khanh
 */
public class MapServerUI extends Form implements CommandListener {

    private UIController uiController;
    private TextField serverField;
    private Command fetchCommand;

    public MapServerUI(UIController uiController, String serviceURL) {
        super(uiController.getString(UIConstants.SERVER_UI_TITLE));
        try {
            this.uiController = uiController;

            serverField = new TextField(uiController.getString(UIConstants.GET_CAPABILITIES_TITLE), serviceURL, 255, TextField.URL);

            uiController.mapServersCmd.addCommands(serverField);

            append(serverField);

            fetchCommand = new Command(uiController.getString(UIConstants.GET_CAPABILITIES_CMD), Command.SCREEN, 0);

            addCommand(fetchCommand);
            setCommandListener(this);
        }
        catch (Exception e) {
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
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

}