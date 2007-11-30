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

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import org.javavietnam.gis.client.midp.model.Preferences;

/**
 * @author Khanh
 */
class PreferencesUI extends Form implements CommandListener {

    private UIController uiController;
    private ChoiceGroup languageChoice;
    private TextField serverField;
    private TextField webGISField;
    // private TextField findPathLayerField;
    private Command saveCommand;

    public PreferencesUI(UIController uiController) {
        super(uiController.getString(UIConstants.PREFERENCES));
        try {
            this.uiController = uiController;

            String[] languages = { uiController.getString(UIConstants.ENGLISH),
                    uiController.getString(UIConstants.VIETNAMESE), uiController.getString(UIConstants.DUTCH) };

            languageChoice = new ChoiceGroup(uiController
                    .getString(UIConstants.LANGUAGE), Choice.POPUP, languages,
                    null);
            serverField = new TextField(uiController
                    .getString(UIConstants.GET_CAPABILITIES_TITLE), "", 255,
                    TextField.URL);
            webGISField = new TextField(uiController
                    .getString(UIConstants.WEBGIS_URL), "", 255, TextField.URL);
            // findPathLayerField = new
            // TextField(uiController.getString(UIConstants.FINDPATH_LAYER), "",
            // 255, TextField.URL);

            append(languageChoice);
            append(serverField);
            append(webGISField);
            // append(findPathLayerField);

            saveCommand = new Command(uiController.getString(UIConstants.SAVE),
                    Command.SCREEN, 0);

            addCommand(saveCommand);
            uiController.setCommands(this);

            setCommandListener(this);
        } catch (Exception e) {
            System.out.println("*************** Exception: ");
            e.printStackTrace();
        }
    }

    public void init(Preferences preferences) {
        if ("en-US".equals(preferences.getDefaultLocale())) {
            languageChoice.setSelectedIndex(0, true);
        } else if ("vi".equals(preferences.getDefaultLocale())) {
            languageChoice.setSelectedIndex(1, true);
        } else if ("nl-NL".equals(preferences.getDefaultLocale())) {
            languageChoice.setSelectedIndex(2, true);
        }

        serverField.setString(preferences.getWmsServerURL());
        webGISField.setString(preferences.getWebGISURL());
        // findPathLayerField.setString(preferences.getFindPathLayer());
    }

    public int getSelectedLanguage() {
        return languageChoice.getSelectedIndex();
    }

    public String getServerURL() {
        return serverField.getString();
    }

    public String getWebGISURL() {
        return webGISField.getString();
    }

    /*
     * public String getFindPathLayer() { return findPathLayerField.getString(); }
     */

    public void commandAction(Command command, Displayable displayable) {
        if (saveCommand == command) {
            uiController.savePreferencesRequested();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

}