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
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;

/**
 * @author khanhlnq
 */
class LayerListUI extends List implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command getMapCommand;

    /**
     */
    private Vector layerList;

    public LayerListUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_LIST_TITLE),
                Choice.MULTIPLE);
        this.uiController = uiController;
        setLayerList(new Vector());

        backCommand = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 5);
        getMapCommand = new Command(uiController.getString(UIConstants.GETMAP),
                Command.SCREEN, 0);
        addCommand(backCommand);
        addCommand(getMapCommand);
        uiController.setCommands(this);

        setCommandListener(this);
    }

    public void init(Vector treeNode) {
        deleteAll();
        setTitle(treeNode + "");
        layerList.removeAllElements();
        for (int i = 0; i < treeNode.size(); i++) {
            Object nodeElement = treeNode.elementAt(i);
            LayerInformation layerInfo = ((TreeNode) nodeElement)
                    .getLayerInformation();
            layerList.addElement(layerInfo);
            append(layerInfo.getField("title"), null);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.mapServerRequested();
        } else if (command == getMapCommand) {
            uiController.getMapRequested();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return Returns the layerList.
     */
    public Vector getLayerList() {
        return layerList;
    }

    /**
     * @param layerList
     *            The layerList to set.
     */
    private void setLayerList(Vector layerList) {
        this.layerList = layerList;
    }

    public Vector getSelectedLayerList() {
        boolean[] layerFlags;
        layerFlags = new boolean[size()];
        Vector selectLayerList = new Vector();

        getSelectedFlags(layerFlags);
        for (int i = 0; i < layerList.size(); i++) {
            if (layerFlags[i]) {
                LayerInformation layerInfo = (LayerInformation) layerList
                        .elementAt(i);
                selectLayerList.addElement(layerInfo);
            }
        }
        return selectLayerList;
    }

}
