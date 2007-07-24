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

import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import java.util.Vector;


/**
 */
class LayerListUI extends List implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command getMapCommand;

    private Vector layerList;

    public LayerListUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_LIST_TITLE), List.MULTIPLE);
        this.uiController = uiController;
        setLayerList(new Vector());

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 5);
        getMapCommand = new Command(uiController.getString(UIConstants.GETMAP), Command.SCREEN, 0);
        addCommand(backCommand);
        addCommand(getMapCommand);
        setCommandListener(this);
    }

    public void init(Vector treeNode) {
        deleteAll();
        setTitle(treeNode + "");
        layerList.removeAllElements();
        for (int i = 0; i < treeNode.size(); i++) {
            // System.out.println("**** treeNode class name: " + treeNode.getClass().getName() + ". Element class name:
            // " +
            // treeNode.elementAt(i).getClass().getName());
            LayerInformation layerInfo = ((TreeNode) treeNode.elementAt(i)).getLayerInformation();
            layerList.addElement(layerInfo);
            append(layerInfo.getField("name"), null);
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
     * @uml.property name="layerList"
     */
    public Vector getLayerList() {
        return layerList;
    }

    /**
     * @param layerList The layerList to set.
     * @uml.property name="layerList"
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
                LayerInformation layerInfo = (LayerInformation) layerList.elementAt(i);
                selectLayerList.addElement(layerInfo);
            }
        }
        return selectLayerList;
    }

}
