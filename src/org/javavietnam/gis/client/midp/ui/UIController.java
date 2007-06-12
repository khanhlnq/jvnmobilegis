/*
 * Copyright 2001, 2002, 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Except for any files in PNG format (which are marked with the filename
 * extension ".png"), GIF format (which are marked with the filename
 * extension ".gif"), or JPEG format (which are marked with the filename
 * extension ".jpg"), redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 * - Redistribution of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * You may compile, use, perform and display the following files with
 * original Java Smart Ticket Sample Application code obtained from Sun
 * Microsystems, Inc. only:
 * - files in PNG format and having the ".png" extension
 * - files in GIF format and having the ".gif" extension
 * - files in JPEG format and having the ".jpg" extension
 * You may not modify or redistribute .png, .gif, or .jpg files in any
 * form, in whole or in part, by any means without prior written
 * authorization from Sun Microsystems, Inc. and its licensors, if any.
 * Neither the name of Sun Microsystems, Inc., the 'Java Smart Ticket
 * Sample Application', 'Java', 'Java'-based names, or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or maintenance
 * of any nuclear facility.
 * $Id$
 */
/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import org.javavietnam.gis.client.midp.MobileGISMIDlet;
import org.javavietnam.gis.client.midp.model.ErrorMessageCodes;
import org.javavietnam.gis.client.midp.model.ModelFacade;
import org.javavietnam.gis.client.midp.model.Preferences;
import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.IndexedResourceBundle;
import org.javavietnam.gis.shared.midp.VietSign;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.MapFeature;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;


/**
 */
public class UIController {

    private static final String BASE_NAME_UI_RESOURCES = "UIResources2";

    public static class EventIds {

        public static final byte EVENT_ID_GETMAPWMS = 1;
        public static final byte EVENT_ID_GETCAPABILITIESWMS = 2;
        public static final byte EVENT_ID_UPDATEMAPWMS = 3;
        public static final byte EVENT_ID_FINDPATHWMS = 4;
        public static final byte EVENT_ID_VIEWPATHWMS = 5;
        public static final byte EVENT_ID_GETFEATUREINFO = 6;
        public static final byte EVENT_ID_SEARCHFEATURE = 7;
        public static final byte EVENT_ID_VIEWFEATURE = 8;
        public static final byte EVENT_ID_CHECKUPDATE = 9;

        private EventIds() {
        }

    }

    private static final String[] iconPaths = { "/icons/JVNMobileGIS.png", };
    private MIDlet midlet;
    public Display display;
    private IndexedResourceBundle resourceBundle;
    private ModelFacade model;
    public VietSign vietSign;
    public MapServersCmd mapServersCmd;
    private Image[] icons = new Image[iconPaths.length];
    private Command mainMenuCommand;
    private Command exitCommand;
    private Command aboutCommand;
    private Alert alert;
    private MainMenuUI mainMenuUI;
    private PreferencesUI preferencesUI;
    private MapServerUI mapServerUI;
    private MapViewUI mapViewUI;
    private FindPathUI findPathUI;
    private SearchFeatureUI searchFeatureUI;
    private SearchFeatureResultUI searchFeatureResultUI;
    private LayerSelectUI layerSelectUI;
    private FeatureInfoUI featureInfoUI;
    private HelpUI helpUI;
    private LayerListUI layerListUI;
    private ProgressObserverUI progressObserverUI;

    public UIController(MIDlet midlet, ModelFacade model) {
        this.midlet = midlet;
        this.display = Display.getDisplay(midlet);
        this.model = model;
        vietSign = new VietSign(midlet);
    }

    /**
     * @return  Returns the display.
     * @uml.property  name="display"
     */
    public Display getDisplay() {
        return display;
    }

    public String getString(int uiConstant) {
        return resourceBundle.getString(uiConstant);
    }

    public MIDlet getMIDlet() {
        return midlet;
    }

    /**
     * @return  the model
     * @uml.property  name="model"
     */
    public ModelFacade getModel() {
        return model;
    }

    public void init() throws ApplicationException {
        resourceBundle = model.getResourceBundle(BASE_NAME_UI_RESOURCES);

        progressObserverUI = new ProgressObserverUI(this);

        model.setProgressObserver(progressObserverUI);

        mapServersCmd = new MapServersCmd(this);

        mainMenuUI = new MainMenuUI(this);
        mapServerUI = new MapServerUI(this, model.getPreferences().getWmsServerURL());
        preferencesUI = new PreferencesUI(this);
        mapViewUI = new MapViewUI(this, false);
        layerListUI = new LayerListUI(this);
        findPathUI = new FindPathUI(this);
        searchFeatureUI = new SearchFeatureUI(this);
        searchFeatureResultUI = new SearchFeatureResultUI(this);
        layerSelectUI = new LayerSelectUI(this);
        featureInfoUI = new FeatureInfoUI(this);
        if (mapViewUI.hasPointerEvents() && mapViewUI.hasPointerMotionEvents()) {
            helpUI = new HelpUI(this, true);
        }
        else {
            helpUI = new HelpUI(this, false);
        }

        createCommands();
        setCommands(mapViewUI);
        setCommands(preferencesUI);
        setCommands(layerListUI);
        setCommands(mapServerUI);

        for (int i = 0; i < iconPaths.length; i++) {
            try {
                icons[i] = Image.createImage(iconPaths[i]);
            }
            catch (IOException ioe) {
            }
        }

        Alert alert = new Alert(null, getString(UIConstants.MOBILEGIS_CLIENT)
                                      + " version "
                                      + midlet.getAppProperty(MobileGISMIDlet.PROPERTY_MIDLET_VERSION)
                                      + "\n"
                                      + getString(UIConstants.COPYRIGHT), icons[UIConstants.ICON_IDX_SPLASH], null);
        alert.setTimeout(UIConstants.SPLASH_TIMEOUT);
        display.setCurrent(alert, mainMenuUI);

    }

    public void destroy() {
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == mainMenuCommand) {
            mainMenuRequested();
        }
        else if (command == exitCommand) {
            exitRequested();
        }
    }

    public void showErrorAlert(Exception e) {
        showErrorAlert(new ApplicationException(e), mainMenuUI);
    }

    public void showErrorAlert(ApplicationException ae, Displayable d) {
        showErrorAlert(ae.getMessage(), d);
    }

    public void showErrorAlert(String message) {
        showErrorAlert(message, display.getCurrent());
    }

    private void showErrorAlert(String message, Displayable d) {
        Alert alert = new Alert(getString(UIConstants.ERROR));

        alert.setType(AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        alert.setString(message);
        display.setCurrent(alert, d);
    }

    public void showInfoAlert(String message, Displayable d) {
        showInfoAlert(null, message, d);
    }

    public void showInfoAlert(String title, String message, Displayable d) {
        Alert alert = new Alert((title == null) ? getString(UIConstants.MOBILEGIS_CLIENT) : title);

        alert.setType(AlertType.INFO);
        alert.setTimeout(Alert.FOREVER);
        alert.setString(message);
        display.setCurrent(alert, d);
    }

    public void runWithProgress(Thread thread, String title, boolean stoppable) {
        progressObserverUI.init(title, stoppable);
        getDisplay().setCurrent(progressObserverUI);
        thread.start();
    }

    public void mainMenuRequested() {
        display.setCurrent(mainMenuUI);
    }

    public void preferencesUIRequested() {
        try {
            preferencesUI.init(model.getPreferences());
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }
        display.setCurrent(preferencesUI);
    }

    public void savePreferencesRequested() {
        try {
            Preferences preferences = model.getPreferences();
            switch (preferencesUI.getSelectedLanguage()) {
            case 0:
                preferences.setDefaultLocale("en_US");
                break;
            case 1:
                preferences.setDefaultLocale("vi");
                break;
            default:
                break;
            }
            preferences.setWmsServerURL(preferencesUI.getServerURL());
            preferences.setWebGISURL(preferencesUI.getWebGISURL());
            preferences.setFindPathLayer(preferencesUI.getFindPathLayer());
            model.setPreferences(preferences);

            // apply new language and reload
            if (!model.getLocale().equals(preferences.getDefaultLocale())) {
                // reload when the locale changed
                model.setLocale(preferences.getDefaultLocale());
                init();
            }
            else {
                // just return to main menu
                mainMenuRequested();
            }
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }
    }

    public void checkUpdateRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_CHECKUPDATE, mainMenuUI), getString(UIConstants.PROCESSING), true);
    }

    public void mapServerRequested() {
        try {
            mapServerUI.setServerURL(model.getPreferences().getWmsServerURL());
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }

        display.setCurrent(mapServerUI);
    }

    public void layerListRequested() {
        display.setCurrent(layerListUI);
    }

    public void viewMapRequested() {
        display.setCurrent(mapViewUI);
    }

    public void findPathResultRequested() {
        display.setCurrent(findPathUI);
    }

    public void helpRequested() {
        display.setCurrent(helpUI);
    }

    public void getMapRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_GETMAPWMS, layerListUI), getString(UIConstants.PROCESSING), true);
    }

    public void getFeatureInfoRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_GETFEATUREINFO, mapViewUI), getString(UIConstants.PROCESSING), true);
    }

    public void selectInfoLayerRequested() {
        if (layerSelectUI.isAskNextTime()) {
            display.setCurrent(layerSelectUI);
        }
        else {
            getFeatureInfoRequested();
        }
    }

    public void findPathRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_FINDPATHWMS, mapViewUI), getString(UIConstants.PROCESSING), true);
    }

    public void searchFeatureRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_SEARCHFEATURE, searchFeatureUI), getString(UIConstants.PROCESSING), true);
    }

    public void searchFeatureRequested(int start) {
        searchFeatureUI.setStart(start);
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_SEARCHFEATURE, searchFeatureUI), getString(UIConstants.PROCESSING), true);
    }

    public void searchUIRequested() {
        try {
            // set new bounding box for search
            searchFeatureUI.initParam(mapViewUI.getBoundingBox(), model.getPreferences().getWebGISURL());
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }

        display.setCurrent(searchFeatureUI);
    }

    public void searchResultUIRequested() {
        display.setCurrent(searchFeatureResultUI);
    }

    public void viewPathRequested() {
        mapViewUI.setIsViewPath(true);
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_VIEWPATHWMS, findPathUI), getString(UIConstants.PROCESSING), false);
    }

    public void viewFeatureRequested() {
        mapViewUI.setIsViewFeature(true);
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_VIEWFEATURE, searchFeatureUI), getString(UIConstants.PROCESSING), false);
    }

    public void updateMapRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_UPDATEMAPWMS, mapViewUI), getString(UIConstants.PROCESSING), true);
    }

    public void getCapabilitiesRequested() {
        // Save new server URL
        try {
            Preferences preference = model.getPreferences();
            preference.setWmsServerURL(mapServerUI.getServerURL());
            model.setPreferences(preference);
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }

        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_GETCAPABILITIESWMS, mapServerUI), getString(UIConstants.PROCESSING), false);
    }

    public void exitRequested() {
        System.out.println("Bye Bye");
        // FIXME - Not yet implemented.
        midlet.notifyDestroyed();
    }

    class EventDispatcher extends Thread {

        private int taskId;
        private Displayable fallbackUI;

        EventDispatcher(int taskId, Displayable fallbackUI) {
            this.taskId = taskId;
            this.fallbackUI = fallbackUI;

            return;
        }

        public void run() {
            try {
                switch (taskId) {
                case EventIds.EVENT_ID_GETMAPWMS: {
                    Image img = getMapWMS(mapViewUI, layerListUI.getSelectedLayerList());

                    if (img == null) {
                        showErrorAlert(getString(UIConstants.GET_MAP_WMS_ERROR), mainMenuUI);
                    }
                    else {
                        mapViewUI.init(img);
                        display.setCurrent(mapViewUI);
                    }

                    break;
                }

                case EventIds.EVENT_ID_VIEWPATHWMS: {
                    Image img = viewPathWMS(mapViewUI);

                    if (img == null) {
                        showErrorAlert(getString(UIConstants.VIEWPATH_ERROR), findPathUI);
                    }
                    else {
                        mapViewUI.init(img);
                        display.setCurrent(mapViewUI);
                    }

                    break;
                }

                case EventIds.EVENT_ID_VIEWFEATURE: {
                    MapFeature feature = searchFeatureResultUI.getSelectedFeature();
                    // Recenter map view to this feature
                    mapViewUI.reCenterAtFeature(feature);

                    // Update new map
                    Image img = updateMapWMS(mapViewUI, layerListUI.getSelectedLayerList());

                    if (img == null) {
                        showErrorAlert(getString(UIConstants.GET_MAP_WMS_ERROR), mainMenuUI);
                    }
                    else {
                        mapViewUI.init(img);
                        display.setCurrent(mapViewUI);
                    }

                    break;
                }

                case EventIds.EVENT_ID_FINDPATHWMS: {
                    String result = findPathWMS(mapViewUI);
                    findPathUI.init(result);
                    display.setCurrent(findPathUI);

                    break;
                }

                case EventIds.EVENT_ID_SEARCHFEATURE: {
                    String result = searchFeature(searchFeatureUI);
                    searchFeatureResultUI.init(result);
                    searchResultUIRequested();

                    break;
                }

                case EventIds.EVENT_ID_GETFEATUREINFO: {
                    String result = getFeatureInfo(mapViewUI, layerListUI.getSelectedLayerList(), layerSelectUI.getInfoLayerName());
                    featureInfoUI.init(result);
                    display.setCurrent(featureInfoUI);

                    break;
                }

                case EventIds.EVENT_ID_UPDATEMAPWMS: {
                    Image img = updateMapWMS(mapViewUI, layerListUI.getSelectedLayerList());

                    if (img == null) {
                        showErrorAlert(getString(UIConstants.GET_MAP_WMS_ERROR), mainMenuUI);
                    }
                    else {
                        mapViewUI.init(img);
                        display.setCurrent(mapViewUI);
                    }

                    break;
                }

                case EventIds.EVENT_ID_GETCAPABILITIESWMS: {
                    Vector constructedDataTree = getCapabilitiesWMS(mapServerUI.getServerURL());

                    if (constructedDataTree == null) {
                        showErrorAlert(getString(UIConstants.GET_CAPABILITIES_WMS_ERROR), mainMenuUI);
                    }
                    else {
                        layerListUI.init((Vector) constructedDataTree.elementAt(0));

                        display.setCurrent(layerListUI);
                    }

                    break;
                }

                case EventIds.EVENT_ID_CHECKUPDATE: {
                    String currentVersion = checkUpdate(midlet.getAppProperty(MobileGISMIDlet.PROPERTY_UPDATE_URL));
                    String oldVersion = midlet.getAppProperty(MobileGISMIDlet.PROPERTY_MIDLET_VERSION);
                    // System.out.println("****** Current Version = " + currentVersion + ". oldVersion = " +
                    // oldVersion);
                    if (null != currentVersion && currentVersion.compareTo(oldVersion) > 0) {
                        showInfoAlert(getString(UIConstants.UPDATE_AVAILABE), mainMenuUI);
                    }
                    else {
                        showInfoAlert(getString(UIConstants.NO_UPDATE_AVAILABE), mainMenuUI);
                    }

                    break;
                }

                } // for switch - case
            }
            catch (ApplicationException ae) {
                ae.printStackTrace();
                if (ae.getCode() == ErrorMessageCodes.ERROR_OPERATION_INTERRUPTED) {
                    display.setCurrent(fallbackUI);
                }
                else if (ae.getCode() == ErrorMessageCodes.ERROR_CANNOT_CONNECT) {
                    showErrorAlert(ae, fallbackUI);
                }
                else if (ae.getCode() == ErrorMessageCodes.NO_SELECTED_LAYER) {
                    showErrorAlert(ae, fallbackUI);
                }
                else if (ae.getCode() == ErrorMessageCodes.NO_SELECTED_POINT) {
                    showErrorAlert(ae, fallbackUI);
                }
                else {
                    showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + ae.getMessage(), fallbackUI);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage(), mainMenuUI);
            }
        }

    }

    private void createCommands() {
        exitCommand = new Command(getString(UIConstants.EXIT), Command.EXIT, 15);
        mainMenuCommand = new Command(getString(UIConstants.MAIN_MENU), Command.OK, 13);
        aboutCommand = new Command(getString(UIConstants.ABOUT), Command.OK, 14);
    }

    private void setCommands(Displayable displayable) {
        displayable.addCommand(exitCommand);
        displayable.addCommand(mainMenuCommand);
        displayable.addCommand(aboutCommand);
    }

    public Image getImage(byte imageIndex) {
        return icons[imageIndex];
    }

    private Image getMapWMS(WMSRequestParameter requestParam, Vector layerList) throws ApplicationException {
        if (0 < layerList.size()) {
            LayerInformation layerInfo = (LayerInformation) layerList.elementAt(0);
            mapViewUI.initParam(layerInfo.getLatLonBoundingBox(),
                                layerInfo.getServerInformation().getGetMapURL(),
                                layerInfo.getField("srs"));
        }
        // Init layers for select layer UI
        layerSelectUI.init(layerListUI.getSelectedLayerList());
        return model.getMapWMS(requestParam, layerList);
    }

    private String findPathWMS(WMSRequestParameter requestParam) throws ApplicationException {
        return model.findPathWMS(requestParam);
    }

    private String searchFeature(SearchFeatureParameter searchParam) throws ApplicationException {
        return model.searchFeature(searchParam);
    }

    private String getFeatureInfo(WMSRequestParameter requestParam, Vector layerList, String infoLayer) throws ApplicationException {
        return model.getFeatureInfo(requestParam, layerList, infoLayer);
    }

    private Image viewPathWMS(WMSRequestParameter requestParam) throws ApplicationException {
        return model.viewPathWMS(requestParam);
    }

    private Image updateMapWMS(WMSRequestParameter requestParam, Vector layerList) throws ApplicationException {
        return model.getMapWMS(requestParam, layerList);
    }

    private String checkUpdate(String updateURL) throws ApplicationException {
        return model.checkUpdate(updateURL);
    }

    private Vector getCapabilitiesWMS(String serverURL) throws ApplicationException {
        return model.getCapabilitiesWMS(serverURL);
    }

}
