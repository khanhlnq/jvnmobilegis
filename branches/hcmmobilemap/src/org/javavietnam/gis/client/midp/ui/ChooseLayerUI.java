/**
 * $Id: SortLayerListUI.java 201 2008-04-24 03:14:15Z khanh.lnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/SortLayerListUI.java $
 * $Author: khanh.lnq $
 * $Revision: 201 $
 * $Date: 2008-04-24 10:14:15 +0700 (Thu, 24 Apr 2008) $
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
 * @author: An Nguyen
 * @Date Created: 27 Dec 2007
 */
package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Ticker;

import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.model.LayerInformation;

/**
 * @author anntv
 *
 */
public class ChooseLayerUI extends List implements CommandListener {

    private final UIController uiController;
    private final Command backCommand;
    private final Command nextCommand;
    private Vector layerList;
    private Ticker ticker;

    public ChooseLayerUI(UIController uiController) {
        super(uiController.getString(UIConstants.CHOOSE_LAYER), Choice.EXCLUSIVE);

        ticker = new Ticker(uiController.getString(UIConstants.CHOOSE_LAYER_TICKER));
        setTicker(ticker);

        this.uiController = uiController;
        setLayerList(new Vector());

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 0);
        nextCommand = new Command(uiController.getString(UIConstants.NEXT), Command.SCREEN, 1);

        addCommand(backCommand);
        addCommand(nextCommand);

        // uiController.setCommands(this);
        setCommandListener(this);
    }

    public void init(Vector layerList) throws ApplicationException {
        this.layerList = layerList;

        deleteAll();
        for (int i = 0; i < layerList.size(); i++) {
            append(layerList.elementAt(i).toString(), null);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        } else if (command == nextCommand) {
            uiController.chooseAttributeRequested();
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
     * @param layerList
     *            The layerList to set.
     * @uml.property name="layerList"
     */
    private void setLayerList(Vector layerList) {
        this.layerList = layerList;
    }

    public LayerInformation getSelectedLayer() {
        return (LayerInformation) layerList.elementAt(getSelectedIndex());
    }
}