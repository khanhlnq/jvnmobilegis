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
 * http://jvnmobilegis.googlecode.com/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq at javavietnam.org
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.*;


/**
 * @author Khanh
 */
class FeatureInfoUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;

    private StringItem resultStr;

    public FeatureInfoUI(UIController uiController) {
        super(uiController.getString(UIConstants.FEATUREINFO_UI_TITLE));
        this.uiController = uiController;
        resultStr = new StringItem(uiController.getString(UIConstants.FEATUREINFO_RESULT_STR), "");
        append(resultStr);

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 5);

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
        } else {
            uiController.commandAction(command, displayable);
        }
    }

}
