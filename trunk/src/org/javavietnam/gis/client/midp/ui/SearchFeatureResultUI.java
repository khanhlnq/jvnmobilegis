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

import org.javavietnam.gis.shared.midp.SearchFeatureResultParser;
import org.javavietnam.gis.shared.midp.model.MapFeature;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import java.util.Vector;

/**
 * @author Khanh
 */
class SearchFeatureResultUI extends List implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command viewOnMapCommand;
    private Command nextCommand;

    private Vector features;

    /**
     */
    private int numResult = 0;

    public SearchFeatureResultUI(UIController uiController) {
        super(uiController
                .getString(UIConstants.SEARCH_FEATURE_RESULT_UI_TITLE),
                Choice.IMPLICIT);
        this.uiController = uiController;

        backCommand = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 5);
        viewOnMapCommand = new Command(uiController
                .getString(UIConstants.VIEW_ON_MAP), Command.SCREEN, 1);
        nextCommand = new Command(uiController.getString(UIConstants.NEXT),
                Command.SCREEN, 0);

        addCommand(backCommand);
        setCommandListener(this);
    }

    public void init(String result) {
        deleteAll();
        features = null;
        features = new Vector();
        numResult = 0;
        SearchFeatureResultParser parser = new SearchFeatureResultParser(result);
        removeCommand(viewOnMapCommand);
        removeCommand(nextCommand);

        features = new Vector();
        features = parser.parseFeatures();
        numResult = parser.getNumResult();

        if (null != features && 0 < features.size()) {
            for (int i = 0; i < features.size(); i++) {
                MapFeature feature = (MapFeature) features.elementAt(i);
                append(feature.getName(), null);
            }
            addCommand(viewOnMapCommand);
            if (0 < numResult) {
                addCommand(nextCommand);
            }
        } else {
            append(uiController.getString(UIConstants.NO_FEATURE_FOUND), null);
        }

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.searchUIRequested();
        } else if (command == viewOnMapCommand
                || command == List.SELECT_COMMAND) {
            if (!(uiController.getString(UIConstants.NO_FEATURE_FOUND))
                    .equals(getString(getSelectedIndex()))) {
                uiController.viewFeatureRequested();
            }
        } else if (command == nextCommand) {
            uiController.searchFeatureRequested(numResult + 1);
        } else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return the numResult
     */
    public int getNumResult() {
        return numResult;
    }

    public MapFeature getSelectedFeature() {
        return (MapFeature) features.elementAt(getSelectedIndex());
    }

}