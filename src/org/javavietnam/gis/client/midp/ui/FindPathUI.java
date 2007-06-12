/*
 * FindPathUI.java
 *
 * Created on April 20, 2006, 11:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import org.javavietnam.gis.shared.midp.FindPathResultParser;
import org.javavietnam.gis.shared.midp.model.PathStreet;


/**
 * @author  Khanh
 */
public class FindPathUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command viewPathCommand;

    private StringItem resultStr;

    private Vector path;

    private boolean isPathFound = false;

    public FindPathUI(UIController uiController) {
        super(uiController.getString(UIConstants.FIND_PATH_TITLE));
        this.uiController = uiController;
        resultStr = new StringItem(uiController.getString(UIConstants.FINDPATH_RESULT_STR), "");
        append(resultStr);

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);

        viewPathCommand = new Command(uiController.getString(UIConstants.VIEW_PATH), Command.SCREEN, 0);

        addCommand(backCommand);
        setCommandListener(this);
    }

    public void init(String result) {
        FindPathResultParser parser = new FindPathResultParser(result);
        isPathFound = false;
        removeCommand(viewPathCommand);
        // resultStr.setText(result);
        // System.out.println("************* Path String: " + result);
        path = new Vector();
        path = parser.parsePath();

        if (null != path && 0 < path.size()) {
            StringBuffer pathStr = new StringBuffer();
            resultStr.setLabel(uiController.getString(UIConstants.PATH_THROUGH)
                               + " "
                               + path.size()
                               + " "
                               + uiController.getString(UIConstants.STREETS)
                               + ":");
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) {
                    pathStr.append(" -> ");
                }
                pathStr.append(((PathStreet) path.elementAt(i)).getName());
            }
            resultStr.setText(pathStr.toString());

            isPathFound = true;
            addCommand(viewPathCommand);
        }
        else {
            resultStr.setLabel(uiController.getString(UIConstants.NOTICE));
            String note = parser.getNotice();
            if (null != note) {
                note.trim();
            }
            if (null == note || "".equals(note)) {
                note = uiController.getString(UIConstants.FINDPATH_ERROR);
            }
            resultStr.setText(note);
        }

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        }
        else if (command == viewPathCommand && isPathFound) {
            uiController.viewPathRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

}