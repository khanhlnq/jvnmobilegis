/*
 * MapServerUI.java
 *
 * Created on May 3, 2006, 4:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import org.javavietnam.gis.client.midp.model.Preferences;


/**
 * @author  Khanh
 */
public class PreferencesUI extends Form implements CommandListener {

    private UIController uiController;
    private ChoiceGroup languageChoice;
    private TextField serverField;
    private TextField webGISField;
    private TextField findPathLayerField;
    private Command saveCommand;

    public PreferencesUI(UIController uiController) {
        super(uiController.getString(UIConstants.PREFERENCES));
        try {
            this.uiController = uiController;

            String[] languages = { uiController.getString(UIConstants.ENGLISH), uiController.getString(UIConstants.VIETNAMESE) };

            languageChoice = new ChoiceGroup(uiController.getString(UIConstants.LANGUAGE), Choice.POPUP, languages, null);
            serverField = new TextField(uiController.getString(UIConstants.GET_CAPABILITIES_TITLE), "", 255, TextField.URL);
            webGISField = new TextField(uiController.getString(UIConstants.WEBGIS_URL), "", 255, TextField.URL);
            findPathLayerField = new TextField(uiController.getString(UIConstants.FINDPATH_LAYER), "", 255, TextField.URL);

            uiController.mapServersCmd.addCommands(serverField);

            append(languageChoice);
            append(serverField);
            append(webGISField);
            append(findPathLayerField);

            saveCommand = new Command(uiController.getString(UIConstants.SAVE), Command.SCREEN, 0);

            addCommand(saveCommand);
            setCommandListener(this);
        }
        catch (Exception e) {
            System.out.println("*************** Exception: ");
            e.printStackTrace();
        }
    }

    public void init(Preferences preferences) {
        if ("en-US".equals(preferences.getDefaultLocale())) {
            languageChoice.setSelectedIndex(0, true);
        }
        else if ("vi".equals(preferences.getDefaultLocale())) {
            languageChoice.setSelectedIndex(1, true);
        }

        serverField.setString(preferences.getWmsServerURL());
        webGISField.setString(preferences.getWebGISURL());
        findPathLayerField.setString(preferences.getFindPathLayer());
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

    public String getFindPathLayer() {
        return findPathLayerField.getString();
    }

    public void commandAction(Command command, Displayable displayable) {
        if (saveCommand == command) {
            uiController.savePreferencesRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

}