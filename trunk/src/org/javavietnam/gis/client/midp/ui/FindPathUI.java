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
 * khanh.lnq at javavietnam.org
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import org.javavietnam.gis.shared.midp.FindPathResultParser;
import org.javavietnam.gis.shared.midp.model.PathStreet;

import javax.microedition.lcdui.*;
import java.util.Vector;


/**
 * @author Khanh
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
        } else {
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
        } else if (command == viewPathCommand && isPathFound) {
            uiController.viewPathRequested();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

}