/*
 * $Id: MapServerUI.java 110 2007-09-12 06:49:44Z khanhlnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/branches/hcmmobilemap/src/org/javavietnam/gis/client/midp/ui/MapServerUI.java $
 * $Author: khanhlnq $
 * $Revision: 110 $
 * $Date: 2007-09-12 13:49:44 +0700 (Wed, 12 Sep 2007) $
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
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import org.javavietnam.gis.shared.midp.model.FeatureInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;

/**
 * @author taina
 */
class FindStreetUI extends Form implements CommandListener {

    private final UIController uiController;
    private final Command findStreetCmd;
    private final Command backCmd;
    private TextField stNameField;
    private ChoiceGroup distGroup;
    private Vector distList;
    private String distAttribute;

    public FindStreetUI(UIController uiController) {
        super(uiController.getString(UIConstants.FIND_STREET_TITLE));
        this.uiController = uiController;

        stNameField = new TextField(uiController.getString(UIConstants.STREET_NAME_FIELD), "",
                50, TextField.ANY);
        distGroup = new ChoiceGroup(uiController.getString(UIConstants.STREET_DISTRICT_GROUP),
                ChoiceGroup.POPUP);

        append(stNameField);
        append(distGroup);

        backCmd = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 0);
        findStreetCmd = new Command(uiController.getString(UIConstants.FIND_STREET_CMD), Command.SCREEN, 1);

        addCommand(backCmd);
        addCommand(findStreetCmd);
        uiController.setCommands(this);
        setCommandListener(this);
    }

    public void init(Vector treeNode, String distAttribute) {
        distGroup.deleteAll();
        distList = null;
        distList = new Vector();
        this.distAttribute = distAttribute;

        if (null != treeNode && 0 != treeNode.size()) {
            for (int i = 0; i < treeNode.size(); i++) {
                Object nodeElement = treeNode.elementAt(i);
                FeatureInformation featureInfo = ((TreeNode) nodeElement).getFeatureInformation();
                distList.addElement(featureInfo);
                distGroup.append(featureInfo.getField(distAttribute), null);
            }
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCmd) {
            uiController.viewMapRequested();
        } else if (command == findStreetCmd) {
            uiController.findStreetRequest();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

    public String getStreetName() {
        return stNameField.getString();
    }

    public FeatureInformation getDistrict() {
        return (FeatureInformation) distList.elementAt(distGroup.getSelectedIndex());
    }
}