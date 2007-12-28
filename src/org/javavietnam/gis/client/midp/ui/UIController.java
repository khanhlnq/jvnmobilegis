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
 */

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

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import org.bouncycastle.util.encoders.Base64;
import org.javavietnam.gis.client.midp.JVNMobileGISMIDlet;
import org.javavietnam.gis.client.midp.model.MessageCodes;
import org.javavietnam.gis.client.midp.model.ModelFacade;
import org.javavietnam.gis.client.midp.model.Preferences;
import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.IndexedResourceBundle;
import org.javavietnam.gis.shared.midp.VietSign;
import org.javavietnam.gis.shared.midp.model.Credentials;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;

/**
 * @author khanhlnq
 */
public class UIController {

    private static final String BASE_NAME_UI_RESOURCES = "UIResources2";
    private static final String BASE_NAME_MESSAGE_RESOURCES = "MessageResources";

    public static class EventIds {

        public static final byte EVENT_ID_GETMAPWMS = 1;
        public static final byte EVENT_ID_GETCAPABILITIESWMS = 2;
        public static final byte EVENT_ID_UPDATEMAPWMS = 3;
        // public static final byte EVENT_ID_FINDPATHWMS = 4;
        // public static final byte EVENT_ID_VIEWPATHWMS = 5;
        public static final byte EVENT_ID_GETFEATUREINFO = 6;
//        public static final byte EVENT_ID_SEARCHFEATURE = 7;
//        public static final byte EVENT_ID_VIEWFEATURE = 8;
        public static final byte EVENT_ID_CHECKUPDATE = 9;
    }
    private static final String[] iconPaths = {"/icons/JVNMobileGIS_icon.png",
    										    "/icons/map_server_icon.png",
    										    "/icons/preferences_icon.png",
    										    "/icons/check_update_icon.png",
    										    "/icons/en.png",
    										    "/icons/vn.png",
    										    "/icons/nl.png",
    										    "/icons/error_alert.png",
    										    "/icons/info_alert.png"};
    private final MIDlet midlet;
    /**
     * @uml.property name="display"
     */
    private final Display display;
    private IndexedResourceBundle resourceBundle;
    private IndexedResourceBundle messageBundle;
    private final ModelFacade model;
    public VietSign vietSign;
    public MapServersCmd mapServersCmd;
    private final Image[] icons = new Image[iconPaths.length];
    private Command mainMenuCommand;
    private Command exitCommand;
    private Command aboutCommand;
    // private Alert alert;
    private MainMenuUI mainMenuUI;
    private PreferencesUI preferencesUI;
    private MapServerUI mapServerUI;
    private MapViewUI mapViewUI;
    // private FindPathUI findPathUI;
//    private SearchFeatureUI searchFeatureUI;
//    private SearchFeatureResultUI searchFeatureResultUI;
    private LayerSelectUI layerSelectUI;
    private FeatureInfoUI featureInfoUI;
    private HelpUI helpUI;
    private LayerListUI layerListUI;
    private SelectedLayerListUI selectedLayerListUI;    
    private ProgressObserverUI progressObserverUI;
    private PromptDialog promptDialog;
    private ConfirmDialogUI confirmDialogUI;
    private final Credentials credentials;

    public UIController(MIDlet midlet, ModelFacade model) {
        this.credentials = new Credentials();
        this.midlet = midlet;
        this.display = Display.getDisplay(midlet);
        this.model = model;
        vietSign = new VietSign(midlet);
    }

    /**
     * @return Returns the display.
     * @uml.property name="display"
     */
    private Display getDisplay() {
        return display;
    }

    public String getString(int uiConstant) {
        return resourceBundle.getString(uiConstant);
    }

    public String getMessage(int messageId) {
        return messageBundle.getString(messageId);
    }

    public MIDlet getMIDlet() {
        return midlet;
    }

    /**
     * @return the model
     * @uml.property name="model"
     */
    public ModelFacade getModel() {
        return model;
    }

    public void init() throws ApplicationException {
        resourceBundle = model.getResourceBundle(BASE_NAME_UI_RESOURCES);
        messageBundle = model.getResourceBundle(BASE_NAME_MESSAGE_RESOURCES);

        createCommands();
        // setCommands(mapViewUI);
        // setCommands(preferencesUI);
        // setCommands(layerListUI);
        // setCommands(mapServerUI);

        progressObserverUI = new ProgressObserverUI(this);

        model.setProgressObserver(getProgressObserverUI());

        mapServersCmd = new MapServersCmd(this);

        if (getMapViewUI().hasPointerEvents() && getMapViewUI().hasPointerMotionEvents()) {
            helpUI = new HelpUI(this, true);
        } else {
            helpUI = new HelpUI(this, false);
        }
        promptDialog = new PromptDialog(this);

        for (int i = 0; i < iconPaths.length; i++) {
            try {
                icons[i] = Image.createImage(iconPaths[i]);
            } catch (IOException ioe) {
            	System.out.println("can not get image " + iconPaths[i]);
            }
        }

        Alert alert = new Alert(
            null,
            getString(UIConstants.MOBILEGIS_CLIENT) + " version " + (null == midlet.getAppProperty(JVNMobileGISMIDlet.PROPERTY_MIDLET_VERSION) ? ""
            : midlet.getAppProperty(JVNMobileGISMIDlet.PROPERTY_MIDLET_VERSION)) + " \n" + getString(UIConstants.COPYRIGHT),
            icons[UIConstants.ICON_IDX_SPLASH], null);
        alert.setTimeout(UIConstants.SPLASH_TIMEOUT);
        display.setCurrent(alert, getMainMenuUI());
    }

    public void destroy() {
    }

    public MainMenuUI getMainMenuUI() {
        if (mainMenuUI == null) {
            mainMenuUI = new MainMenuUI(this);
        }
        return mainMenuUI;
    }

    public PreferencesUI getPreferencesUI() {
        if (preferencesUI == null) {
            preferencesUI = new PreferencesUI(this);
        }
        return preferencesUI;
    }

    public MapServerUI getMapServerUI() {
        if (mapServerUI == null) {
            try {
                mapServerUI = new MapServerUI(this, model.getPreferences().getWmsServerURL());
            } catch (ApplicationException ae) {
                ae.printStackTrace();
                showErrorAlert(ae, getMainMenuUI());
            }
        }
        return mapServerUI;
    }

    public MapViewUI getMapViewUI() {
        if (mapViewUI == null) {
            mapViewUI = new MapViewUI(this, false);
        }
        return mapViewUI;
    }

//    public SearchFeatureUI getSearchFeatureUI() {
//        return searchFeatureUI;
//    }
//
//    public SearchFeatureResultUI getSearchFeatureResultUI() {
//        return searchFeatureResultUI;
//    }
    public LayerSelectUI getLayerSelectUI() {
        if (layerSelectUI == null) {
            layerSelectUI = new LayerSelectUI(this);
        }
        return layerSelectUI;
    }

    public FeatureInfoUI getFeatureInfoUI() {
        if (featureInfoUI == null) {
            featureInfoUI = new FeatureInfoUI(this);
        }
        return featureInfoUI;
    }

    public HelpUI getHelpUI() {
        return helpUI;
    }

    public LayerListUI getLayerListUI() {
        if (layerListUI == null) {
            layerListUI = new LayerListUI(this);
        }
        return layerListUI;
    }

    public void setLayerListUI(LayerListUI layerListUI) {
        this.layerListUI = layerListUI;
    }
    
    public SelectedLayerListUI getSelectedLayerListUI() {
        if (selectedLayerListUI == null) {
        	selectedLayerListUI = new SelectedLayerListUI(this);        	        	        	
        }
        return selectedLayerListUI;
    }

    public void setSelectedLayerListUI(SelectedLayerListUI selectedLayerListUI) {
        this.selectedLayerListUI = selectedLayerListUI;
    }
        
    /**
	 * @return the selectedLayerList
	 */
	public Vector getSelectedLayerList() {
		return getLayerListUI().getSelectedLayerList();
	}

    public ProgressObserverUI getProgressObserverUI() {
        return progressObserverUI;
    }

    public PromptDialog getPromptDialog() {
        return promptDialog;
    }

    public ConfirmDialogUI getConfirmDialogUI() {
        if (confirmDialogUI == null) {
            confirmDialogUI = new ConfirmDialogUI(this);
        }
        return confirmDialogUI;
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == mainMenuCommand) {
            mainMenuRequested();
        } else if (command == aboutCommand) {
            try {
                showInfoAlert(
                    getString(UIConstants.ABOUT),
                    getString(UIConstants.MOBILEGIS_CLIENT) + " version " + (null == midlet.getAppProperty(JVNMobileGISMIDlet.PROPERTY_MIDLET_VERSION) ? ""
                    : midlet.getAppProperty(JVNMobileGISMIDlet.PROPERTY_MIDLET_VERSION)) + " \n" + getString(UIConstants.COPYRIGHT) + "\n" + getString(UIConstants.DOWNLOADED_DATA_SIZE) +
                    ": " + model.getDownloadedDataSize(), display.getCurrent());
            } catch (ApplicationException ae) {
            }
        } else if (command == exitCommand) {
            exitRequested();
        }
    }

    public void showErrorAlert(Exception e) {
        showErrorAlert(new ApplicationException(e), getMainMenuUI());
    }

    private void showErrorAlert(ApplicationException ae, Displayable d) {
        showErrorAlert(ae.getMessage(), d);
    }

    private void showErrorAlert(String message) {
        showErrorAlert(message, display.getCurrent());
    }

    private void showErrorAlert(String message, Displayable d) {
        Alert alert = new Alert(getString(UIConstants.ERROR));

        alert.setImage(getImage(UIConstants.ICON_ERROR_ALERT));
        alert.setType(AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        alert.setString(message);
        display.setCurrent(alert, d);
    }

    private void showInfoAlert(String message, Displayable d) {
        showInfoAlert(null, message, d);
    }

    private void showInfoAlert(String title, String message, Displayable d) {
        Alert alert = new Alert(
            (title == null) ? getString(UIConstants.MOBILEGIS_CLIENT)
            : title);
        alert.setImage(getImage(UIConstants.ICON_INFO_ALERT));
        alert.setType(AlertType.INFO);
        alert.setTimeout(Alert.FOREVER);
        alert.setString(message);
        display.setCurrent(alert, d);
    }

    private void runWithProgress(Thread thread, String title, boolean stoppable) {
        getProgressObserverUI().init(title, stoppable);
        getDisplay().setCurrent(getProgressObserverUI());
        thread.start();
    }

    public void mainMenuRequested() {
        display.setCurrent(getMainMenuUI());
    }

    public void preferencesUIRequested() {
        try {
            getPreferencesUI().init(model.getPreferences());
        } catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }
        display.setCurrent(getPreferencesUI());
    }

    public void savePreferencesRequested() {
        try {
            Preferences preferences = model.getPreferences();
            switch (getPreferencesUI().getSelectedLanguage()) {
                case 0:
                    preferences.setDefaultLocale("en-US");
                    break;
                case 1:
                    preferences.setDefaultLocale("vi");
                    break;
                case 2:
                    preferences.setDefaultLocale("nl-NL");
                default:
                    break;
            }
            preferences.setWmsServerURL(getPreferencesUI().getServerURL());
            preferences.setWebGISURL(getPreferencesUI().getWebGISURL());
            // preferences.setFindPathLayer(preferencesUI.getFindPathLayer());
            model.setPreferences(preferences);

            // apply new language and reload
            if (!model.getLocale().equals(preferences.getDefaultLocale())) {
                // reload when the locale changed
                model.setLocale(preferences.getDefaultLocale());
                init();
            } else {
                // just return to main menu
                mainMenuRequested();
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }
    }

    public void checkUpdateRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_CHECKUPDATE, getMainMenuUI()), getString(UIConstants.PROCESSING), true);
    }

    public void mapServerRequested() {
        try {
            getMapServerUI().setServerURL(model.getPreferences().getWmsServerURL());
        } catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }

        display.setCurrent(getMapServerUI());
    }

    public void layerListRequested() {
        display.setCurrent(getLayerListUI());
    }
    
    public void selectedLayerListRequested() {
        display.setCurrent(getSelectedLayerListUI());
    }

    public void viewMapRequested() {
        display.setCurrent(getMapViewUI());
    }

    /*
     * public void findPathResultRequested() { display.setCurrent(findPathUI); }
     */
    public void helpRequested() {
        display.setCurrent(getHelpUI());
    }

    public void getMapRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_GETMAPWMS, getLayerListUI()), getString(UIConstants.PROCESSING), true);
    }

    public void getFeatureInfoRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_GETFEATUREINFO, getMapViewUI()), getString(UIConstants.PROCESSING), true);
    }

    public void selectInfoLayerRequested() {
        if (getLayerSelectUI().isAskNextTime()) {
            display.setCurrent(getLayerSelectUI());
        } else {
            getFeatureInfoRequested();
        }
    }

    /*
     * public void findPathRequested() { runWithProgress(new
     * EventDispatcher(EventIds.EVENT_ID_FINDPATHWMS, mapViewUI),
     * getString(UIConstants.PROCESSING), true); }
     */
//    public void searchFeatureRequested() {
//        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_SEARCHFEATURE,getSearchFeatureUI()), getString(UIConstants.PROCESSING), true);
//    }
//
//    public void searchFeatureRequested(int start) {
//        getSearchFeatureUI().setStart(start);
//        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_SEARCHFEATURE,getSearchFeatureUI()), getString(UIConstants.PROCESSING), true);
//    }
//
//    public void searchUIRequested() {
//        try {
//            // set new bounding box for search
//            getSearchFeatureUI().initParam(getMapViewUI().getBoundingBox(), model.getPreferences().getWebGISURL());
//        } catch (ApplicationException e) {
//            e.printStackTrace();
//            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
//        }
//
//        display.setCurrent(getSearchFeatureUI());
//    }
//
//    public void searchResultUIRequested() {
//        display.setCurrent(getSearchFeatureResultUI());
//    }
//
//    /*
//     * public void viewPathRequested() { mapViewUI.setIsViewPath(true);
//     * runWithProgress(new EventDispatcher(EventIds.EVENT_ID_VIEWPATHWMS,
//     * findPathUI), getString(UIConstants.PROCESSING), false); }
//     */
//    public void viewFeatureRequested() {
//        getMapViewUI().setIsViewFeature(true);
//        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_VIEWFEATURE,getSearchFeatureUI()), getString(UIConstants.PROCESSING), false);
//    }
    public void updateMapRequested() {
        runWithProgress(new EventDispatcher(EventIds.EVENT_ID_UPDATEMAPWMS, getMapViewUI()), getString(UIConstants.PROCESSING), true);
    }

    public void getCapabilitiesRequested() {
        // Save new server URL
        try {
            Preferences preference = model.getPreferences();
            preference.setWmsServerURL(getMapServerUI().getServerURL());
            model.setPreferences(preference);
        } catch (ApplicationException e) {
            e.printStackTrace();
            showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage());
        }

        runWithProgress(new EventDispatcher(
            EventIds.EVENT_ID_GETCAPABILITIESWMS, getMapServerUI()),
            getString(UIConstants.PROCESSING), false);
    }

    public void exitRequested() {
        System.out.println("Bye Bye");
        // FIXME - Not yet implemented.
        midlet.notifyDestroyed();
    }

    class EventDispatcher extends Thread {

        private final int taskId;
        private final Displayable fallbackUI;

        EventDispatcher(int taskId, Displayable fallbackUI) {
            this.taskId = taskId;
            this.fallbackUI = fallbackUI;
        }

        public void run() {
            try {
                switch (taskId) {
                    case EventIds.EVENT_ID_GETMAPWMS: {
                        Image img = getMapWMS(getMapViewUI(), getLayerListUI().getSelectedLayerList());

                        if (img == null) {
                            showErrorAlert(
                                getString(UIConstants.GET_MAP_WMS_ERROR), getMainMenuUI());
                        } else {
                            getMapViewUI().init(img);
                            display.setCurrent(getMapViewUI());
                        }

                        break;
                    }
                    /*
                     * case EventIds.EVENT_ID_VIEWPATHWMS: { Image img =
                     * viewPathWMS(mapViewUI); if (img == null) {
                     * showErrorAlert(getString(UIConstants.VIEWPATH_ERROR),
                     * findPathUI); } else { mapViewUI.init(img);
                     * display.setCurrent(mapViewUI); } break; }
                     */

//                    case EventIds.EVENT_ID_VIEWFEATURE: {
//                        MapFeature feature = getSearchFeatureResultUI().getSelectedFeature();
//                        // Recenter map view to this feature
//                        getMapViewUI().reCenterAtFeature(feature);
//
//                        // Update new map
//                        Image img = updateMapWMS( getMapViewUI(),getLayerListUI().getSelectedLayerList());
//
//                        if (img == null) {
//                            showErrorAlert(
//                                getString(UIConstants.GET_MAP_WMS_ERROR),getMainMenuUI());
//                        } else {
//                            getMapViewUI().init(img);
//                            display.setCurrent(getMapViewUI());
//                        }
//
//                        break;
//                    }
//                    /*
//                     * case EventIds.EVENT_ID_FINDPATHWMS: { String result =
//                     * findPathWMS(mapViewUI); findPathUI.init(result);
//                     * display.setCurrent(findPathUI); break; }
//                     */
//
//                    case EventIds.EVENT_ID_SEARCHFEATURE: {
//                        String result = searchFeature(getSearchFeatureUI());
//                        getSearchFeatureResultUI().init(result);
//                        searchResultUIRequested();
//
//                        break;
//                    }
                    case EventIds.EVENT_ID_GETFEATUREINFO: {
                        String result = getFeatureInfo(getMapViewUI(), getLayerListUI().getSelectedLayerList(), getLayerSelectUI().getInfoLayerName());
                        getFeatureInfoUI().init(result);
                        display.setCurrent(getFeatureInfoUI());

                        break;
                    }
                    case EventIds.EVENT_ID_UPDATEMAPWMS: {
                        Image img = updateMapWMS(getMapViewUI(), getLayerListUI().getSelectedLayerList());

                        if (img == null) {
                            showErrorAlert(
                                getString(UIConstants.GET_MAP_WMS_ERROR), getMainMenuUI());
                        } else {
                            getMapViewUI().init(img);
                            display.setCurrent(getMapViewUI());
                        }

                        break;
                    }
                    case EventIds.EVENT_ID_GETCAPABILITIESWMS: {
                        Vector constructedDataTree = getCapabilitiesWMS(getMapServerUI().getServerURL());

                        if (constructedDataTree == null) {
                            showErrorAlert(
                                getString(UIConstants.GET_CAPABILITIES_WMS_ERROR), getMainMenuUI());
                        } else {
                            getLayerListUI().init(constructedDataTree);

                            display.setCurrent(getLayerListUI());
                        }

                        break;
                    }
                    case EventIds.EVENT_ID_CHECKUPDATE: {
                        String currentVersion = checkUpdate(
                            getString(UIConstants.CONF_UPDATE_URL)).trim();
                        String oldVersion = midlet.getAppProperty(JVNMobileGISMIDlet.PROPERTY_MIDLET_VERSION);
                        // System.out.println("****** Current Version = " +
                        // currentVersion + ". oldVersion = " +
                        // oldVersion);
                        if (null != currentVersion && currentVersion.compareTo(oldVersion) > 0) {
                            showInfoAlert(getString(UIConstants.UPDATE_AVAILABE), getMainMenuUI());
                        } else {
                            showInfoAlert(
                                getString(UIConstants.NO_UPDATE_AVAILABE), getMainMenuUI());
                        }

                        break;
                    }
                } // for switch - case
            } catch (ApplicationException ae) {
                ae.printStackTrace();
                if (ae.getCode() == MessageCodes.ERROR_OPERATION_INTERRUPTED) {
                    display.setCurrent(fallbackUI);
                } else if (ae.getCode() == MessageCodes.ERROR_CANNOT_CONNECT) {
                    showErrorAlert(ae, fallbackUI);
                } else if (ae.getCode() == MessageCodes.NO_SELECTED_LAYER) {
                    showErrorAlert(ae, fallbackUI);
//                } else if (ae.getCode() == MessageCodes.NO_SELECTED_POINT) {
//                    showErrorAlert(ae, fallbackUI);
                } else if (ae.getCode() == MessageCodes.ERROR_UNAUTHORIZED) {
                    try {
                        promtForCredentials(model.getWwwAuthenticate());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + ex.getMessage());
                    }
                } else {
                    showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + ae.getMessage(), fallbackUI);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert(getString(UIConstants.UNKNOWN_ERROR) + ":\n" + e.getMessage(), getMainMenuUI());
            }
        }
    }

    private void createCommands() {
        exitCommand = new Command(getString(UIConstants.EXIT), Command.EXIT, 75);
        mainMenuCommand = new Command(getString(UIConstants.MAIN_MENU),
            Command.OK, 13);
        aboutCommand = new Command(getString(UIConstants.ABOUT), Command.OK, 14);
    }

    public void setCommands(Displayable displayable) {
        displayable.addCommand(exitCommand);
        displayable.addCommand(mainMenuCommand);
        displayable.addCommand(aboutCommand);
    }

    public Image getImage(byte imageIndex) {
        return icons[imageIndex];
    }

    private Image getMapWMS(WMSRequestParameter requestParam, Vector layerList)
        throws ApplicationException {
        if (0 < layerList.size()) {
            LayerInformation layerInfo = (LayerInformation) layerList.elementAt(0);
            getMapViewUI().initParam(layerInfo.getLatLonBoundingBox(), layerInfo.getServerInformation().getGetMapURL(), layerInfo.getField("srs"));
        }
        // Init layers for select layer UI
        getLayerSelectUI().init(getLayerListUI().getSelectedLayerList());
                
        return model.getMapWMS(requestParam, getSelectedLayerListUI().getSelectedLayerList());
        //return model.getMapWMS(requestParam, layerList);
    }

    /*
     * private String findPathWMS(WMSRequestParameter requestParam) throws
     * ApplicationException { return model.findPathWMS(requestParam); }
     */
    private String searchFeature(SearchFeatureParameter searchParam)
        throws ApplicationException {
        return model.searchFeature(searchParam);
    }

    private String getFeatureInfo(WMSRequestParameter requestParam,
        Vector layerList, String infoLayer) throws ApplicationException {
        return model.getFeatureInfo(requestParam, layerList, infoLayer);
    }

    /*
     * private Image viewPathWMS(WMSRequestParameter requestParam) throws
     * ApplicationException { return model.viewPathWMS(requestParam); }
     */
    private Image updateMapWMS(WMSRequestParameter requestParam,
        Vector layerList) throws ApplicationException {
        return model.getMapWMS(requestParam, layerList);
    }

    private String checkUpdate(String updateURL) throws ApplicationException {
        return model.checkUpdate(updateURL);
    }

    private Vector getCapabilitiesWMS(String serverURL)
        throws ApplicationException {
        return model.getCapabilitiesWMS(serverURL);
    }

    /**
     * @return
     * @uml.property name="credentials"
     */
    public Credentials getCredentials() {
        return credentials;
    }

    public void promtForCredentials(String challenge) {
        try {
            java.lang.String realm = null;
            if (challenge == null) {
                throw new ApplicationException(
                    MessageCodes.MISSING_CHALLENGE);
            }
            challenge = challenge.trim();
            if (!challenge.trim().toLowerCase().startsWith("basic")) {
                throw new ApplicationException(
                    MessageCodes.AUTH_SCHEME_NOT_BASIC);
            }
            int length = challenge.length();
            // we don't check for extra double quotes...
            if ((length < 8) || (!challenge.substring(5, 13).equals(" realm=\"")) || (challenge.charAt(length - 1) != '\"')) {
                throw new ApplicationException(
                    MessageCodes.AUTH_REALM_SYNTAX_ERROR);
            }
            realm = challenge.substring(13, length - 1);

            getPromptDialog().setTitle(realm);
            display.setCurrent(getPromptDialog());
        } catch (ApplicationException ex) {
            ex.printStackTrace();
        }
    }

    public void calculateCredentials() {
        if ("".equals(getPromptDialog().getUsername()) || "".equals(getPromptDialog().getPassword())) {
            showErrorAlert(getString(UIConstants.MUST_GIVE_USER_PWD));
        } else {
            credentials.setUsername(getPromptDialog().getUsername());
            credentials.setPassword(getPromptDialog().getPassword());
            // Calculate the credentials
            byte[] credentialsBA = (credentials.getUsername() + ":" + credentials.getPassword()).getBytes();
            byte[] encodedCredentialsBA = Base64.encode(credentialsBA);
            credentials.setCredentials(new String(encodedCredentialsBA));
            // System.out.println("************ Calculated credentials:"
            // + credentials.getCredentials());
            try {
                // Set credentials for HTTPS
                model.setCredentials(credentials.getCredentials());
            } catch (ApplicationException e) {
                e.printStackTrace();
                showErrorAlert(e, getMainMenuUI());
            }
            // After saving credentials, get server's capabilities
            getCapabilitiesRequested();
        }
    }

    public void confirm(int messageId) {
        getConfirmDialogUI().showConfirm(messageId);
        display.setCurrent(getConfirmDialogUI());
    }

    public void confirmAction(int messageId, boolean accepted) {
        switch (messageId) {
            case MessageCodes.CONFIRM_SAVE_PREFERENCES:
                if (accepted) {
                    this.savePreferencesRequested();
                } else {
                    mainMenuRequested();
                }
                break;

            default:
                break;
        }


    }
}
