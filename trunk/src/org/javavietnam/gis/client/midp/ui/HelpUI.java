/*
 * HelpUI.java
 *
 * Created on May 16, 2006, 5:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;


/**
 * @author  khanhlnq
 */
public class HelpUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;

    private StringItem helpStr;

    public HelpUI(UIController uiController, boolean isTouchScreen) {
        super(uiController.getString(UIConstants.HELP_TITLE));
        this.uiController = uiController;
        String str = uiController.getString(UIConstants.HELP_STRING);
        if (isTouchScreen) {
            str = str + "\n" + uiController.getString(UIConstants.HAS_TOUCHSCREEN);
        }
        str = str + "\n\n" + uiController.getString(UIConstants.COPYRIGHT);
        helpStr = new StringItem(uiController.getString(UIConstants.KEYPADS), str);

        append(helpStr);

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);

        addCommand(backCommand);
        setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }
}
