/*
 * FindPathUI.java
 *
 * Created on April 20, 2006, 11:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.javavietnam.gis.shared.midp.SearchFeatureResultParser;
import org.javavietnam.gis.shared.midp.model.MapFeature;


/**
 * @author  Khanh
 */
public class SearchFeatureResultUI extends List implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command viewOnMapCommand;
    private Command nextCommand;

    private Vector features;

    private int numResult = 0;

    public SearchFeatureResultUI(UIController uiController) {
        super(uiController.getString(UIConstants.SEARCH_FEATURE_RESULT_UI_TITLE), List.IMPLICIT);
        this.uiController = uiController;

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 2);
        viewOnMapCommand = new Command(uiController.getString(UIConstants.VIEW_ON_MAP), Command.SCREEN, 1);
        nextCommand = new Command(uiController.getString(UIConstants.NEXT), Command.SCREEN, 0);

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
        }
        else {
            append(uiController.getString(UIConstants.NO_FEATURE_FOUND), null);
        }

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.searchUIRequested();
        }
        else if (command == viewOnMapCommand || command == List.SELECT_COMMAND) {
            if (!(uiController.getString(UIConstants.NO_FEATURE_FOUND)).equals(getString(getSelectedIndex()))) {
                uiController.viewFeatureRequested();
            }
        }
        else if (command == nextCommand) {
            uiController.searchFeatureRequested(numResult + 1);
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return  the numResult
     * @uml.property  name="numResult"
     */
    public int getNumResult() {
        return numResult;
    }

    public MapFeature getSelectedFeature() {
        MapFeature feature = (MapFeature) features.elementAt(getSelectedIndex());
        return feature;
    }

}