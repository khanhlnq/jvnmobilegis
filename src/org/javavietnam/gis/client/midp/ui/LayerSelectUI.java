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

import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import org.javavietnam.gis.shared.midp.model.LayerInformation;

/**
 * @author     Khanh
 */
class LayerSelectUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command confirmCommand;
    private ChoiceGroup choiceLayer;
    private ChoiceGroup choiceAskNextTime;
    /**
     * @uml.property  name="askNextTime"
     */
    private boolean askNextTime = true;

    public LayerSelectUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_SELECT_TITLE));
        this.uiController = uiController;
        String[] askNextTimeArr = new String[2];
        askNextTimeArr[0] = uiController.getString(UIConstants.YES);
        askNextTimeArr[1] = uiController.getString(UIConstants.NO);
        choiceLayer = new ChoiceGroup(uiController
                .getString(UIConstants.CHOOSE_LAYER_FOR_INFO),
                Choice.EXCLUSIVE);
        choiceAskNextTime = new ChoiceGroup(uiController
                .getString(UIConstants.ASK_NEXT_TIME), Choice.EXCLUSIVE,
                askNextTimeArr, null);
        choiceAskNextTime.setSelectedIndex(0, true);
        append(choiceLayer);
        append(choiceAskNextTime);

        confirmCommand = new Command(uiController
                .getString(UIConstants.CONFIRM), Command.OK, 0);
        backCommand = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 5);

        addCommand(confirmCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void init(Vector layerList) {
        choiceLayer.deleteAll();
        for (int i = 0; i < layerList.size(); i++) {
            choiceLayer.append(((LayerInformation) layerList.elementAt(i))
                    .getField("name"), null);
        }
        askNextTime = true;
        // choiceAskNextTime.setSelectedIndex(0, true);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        } else if (command == confirmCommand) {
            askNextTime = (0 == choiceAskNextTime.getSelectedIndex());
            uiController.getFeatureInfoRequested();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return    Returns the askNextTime.
     * @uml.property  name="askNextTime"
     */
    public boolean isAskNextTime() {
        return askNextTime;
    }

    public String getInfoLayerName() {
        return choiceLayer.getString(choiceLayer.getSelectedIndex());
    }

}
