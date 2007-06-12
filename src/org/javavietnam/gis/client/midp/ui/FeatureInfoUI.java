/*
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
 *
 *
 * =====================================================
 *
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;


/**
 * @author  Khanh
 */
public class FeatureInfoUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;

    private StringItem resultStr;

    public FeatureInfoUI(UIController uiController) {
        super(uiController.getString(UIConstants.FEATUREINFO_UI_TITLE));
        this.uiController = uiController;
        resultStr = new StringItem(uiController.getString(UIConstants.FEATUREINFO_RESULT_STR), "");
        append(resultStr);

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);

        addCommand(backCommand);
        setCommandListener(this);
    }

    public void init(String result) {
        // System.out.println("************* FeatureInfo String: " + result);
        resultStr.setText(result);
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
