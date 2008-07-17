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

import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.model.FeatureInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;

/**
 * @author anntv
 *
 */
public class FeatureInBBoxUI extends List implements CommandListener {

    private final UIController uiController;
    private final Command backCommand;
    private final Command viewOnMapCommand;
    private Vector featureList;
    private String selectedAttribute;

    public FeatureInBBoxUI(UIController uiController) {
        super(uiController.getString(UIConstants.CHOOSE_ATTRIBUTE), Choice.IMPLICIT);

        this.uiController = uiController;

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 0);
        viewOnMapCommand = new Command(uiController.getString(UIConstants.VIEW_ON_MAP), Command.SCREEN, 1);

        addCommand(backCommand);
        addCommand(viewOnMapCommand);

        uiController.setCommands(this);
        setCommandListener(this);
    }

    public void init(Vector treeNode, String selectedAttribute) throws ApplicationException {
        deleteAll();
        setTitle(uiController.getString(UIConstants.SEARCH_RESULT));
        featureList = null;
        featureList = new Vector();
        this.selectedAttribute = selectedAttribute;

        for (int i = 0; i < treeNode.size(); i++) {
            Object nodeElement = treeNode.elementAt(i);
            FeatureInformation featureInfo = ((TreeNode) nodeElement).getFeatureInformation();
            featureList.addElement(featureInfo);
            append(featureInfo.getField(selectedAttribute), null);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            if (uiController.isHcmMap()) {
                uiController.viewMapRequested();
            } else {
                uiController.backChooseAttribute();
            }
        } else if (command == viewOnMapCommand) {
            uiController.viewMapByBBox();
        }
    }

    public FeatureInformation getSelectedFeature() {
        return (FeatureInformation) featureList.elementAt(getSelectedIndex());
    }
}