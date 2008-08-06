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

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.Ticker;

import org.javavietnam.gis.client.midp.util.BTManager;
import org.javavietnam.gis.client.midp.util.GpsBt;
import org.javavietnam.gis.client.midp.util.Location;

/**
 * @author Administrator
 * 
 */
public class LBSMainForm extends Form implements CommandListener {

    private String latitude;
    private String longitude;

    private Command cmdBack;
    private Command cmdSearchGps;
    private Form gpsForm;
    private Command cmdSearch;
    private Command cmdShowMeOnMap;
    // private Command cmdStop;
    private Command cmdSelect;
    private ChoiceGroup choiceGps;
    private StringItem gpsState;
    private StringItem quality;
    private StringItem latitudeText;
    private StringItem longitudeText;
    private StringItem time;
    private StringItem satellite;

    // private static final int STATE_IDLE = 0;
    private static final int STATE_SEARCH = 1;
    private static final int STATE_READING = 2;
    private int state = 0;

    private GPSThread thread;
    private final UIController uiController;

    /**
     * @param title
     */
    public LBSMainForm(UIController uiController) {
        super("Gps Info");
        this.uiController = uiController;

        setCommandListener(this);
        cmdSearchGps = new Command("Search", Command.ITEM, 1);
        cmdShowMeOnMap = new Command("Show me on map!", Command.ITEM, 2);
        // cmdStop = new Command("Stop", Command.STOP, 3);
        cmdBack = new Command("Back", Command.BACK, 4);
        addCommand(cmdShowMeOnMap);
        addCommand(cmdSearchGps);
        addCommand(cmdBack);

        gpsState = new StringItem("Status", "No Gps Found");
        quality = new StringItem("Quality", "No Gps Found");
        latitudeText = new StringItem("Latitude", "No Gps Found");
        longitudeText = new StringItem("Longitude", "No Gps Found");
        time = new StringItem("Time", "NA");
        satellite = new StringItem("Satellite", "No Gps Found");

        append(gpsState);
        append(quality);
        append(latitudeText);
        append(longitudeText);
        append(time);
        append(satellite);
    }

    public void commandAction(Command cmd, Displayable display) {
        try {
            if (thread != null) {
                thread.active = false;
            }

            if (GpsBt.instance().isActive || GpsBt.instance().isConnected()) {
                GpsBt.instance().stop();
            }

            if (display == this) {
                if (cmd == cmdSearchGps) {
                    // removeCommand(cmdStop);
                    // removeCommand(cmdShowMeOnMap);
                    display(initGPSForm());
                    doAction(STATE_SEARCH);
                } else if (cmd == cmdShowMeOnMap) {
                    // thread.active = false;
                    // thread = null;

                    if (gpsState.getText().trim().equals("Connected")) {
                        uiController.getMapViewUI().updateMyNMEALocation(
                                latitude, longitude);
                    } else {
                        display(new Alert("Error",
                                "You're not connected to a GPS device!", null,
                                AlertType.ERROR), this);
                    }
                    // } else if (cmd == cmdStop) {
                    // thread.active = false;
                    // thread = null;
                    // removeCommand(cmdStop);
                } else if (cmd == cmdBack) {
                    uiController.viewMapRequested();
                    // thread.active = false;
                }
            } else if (display == gpsForm) {
                if (cmd == cmdSearch) {
                    doAction(STATE_SEARCH);
                }
                if (cmd == cmdSelect) {
                    int option = choiceGps.getSelectedIndex();
                    // any device selected?
                    if (option != -1) {
                        // System.gc();
                        // addCommand(cmdStop);
                        // set gps reader to selected device
                        GpsBt.instance().setDevice(
                                BTManager.instance().getServiceURL(option),
                                BTManager.instance().getDeviceName(option));
                        // start reading value;
                        GpsBt.instance().start();
                        display(this);

                        doAction(STATE_READING);
                    }
                } else if (cmd == cmdBack) {
                    display(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            display(new Alert("Error", e.toString() + ": " + e.getMessage(),
                    null, AlertType.ERROR), uiController.getMapViewUI());
        }
    }

    public void display(Displayable display) {
        // shows the display.
        uiController.getDisplay().setCurrent(display);
    }

    public void display(Alert alert, Displayable next) {
        // shows the alert screen.
        uiController.getDisplay().setCurrent(alert, next);
    }

    public Command initBackCommand() {
        if (cmdBack == null) {
            cmdBack = new Command("Back", Command.BACK, 1);
        }
        return cmdBack;
    }

    public Displayable initGPSForm() {
        if (gpsForm == null) {
            gpsForm = new Form("Gps Search");
            gpsForm.setCommandListener(this);
            cmdSearch = new Command("Search", Command.ITEM, 2);
            gpsForm.addCommand(cmdSearch);
            cmdSelect = new Command("Select", Command.ITEM, 1);
            gpsForm.addCommand(cmdSelect);
            gpsForm.addCommand(initBackCommand());

            choiceGps = new ChoiceGroup("GPS:", ChoiceGroup.EXCLUSIVE);
            gpsForm.append(choiceGps);
        }
        return gpsForm;
    }

    public void doAction(int action) {
        state = action;
        if (thread == null || !thread.isAlive()) {
            thread = null;
            thread = new GPSThread(this);
            thread.active = true;
            thread.start();
        }
    }

    class GPSThread extends Thread {
        private boolean active = false;

        private LBSMainForm mainForm;

        public GPSThread(LBSMainForm lbsMainForm) {
            super();
            this.mainForm = lbsMainForm;
        }

        public void run() {
            while (active) {
                switch (state) {
                case (STATE_SEARCH):
                    choiceGps.deleteAll();
                    gpsForm.setTicker(new Ticker("Searching devices..."));
                    BTManager.instance().find(BTManager.getRFCOMM_UUID());
                    int size = BTManager.instance().btDevicesFound.size();
                    for (int i = 0; i < size; i++) {
                        choiceGps.append(BTManager.instance().getDeviceName(i),
                                null);
                    }
                    gpsForm.setTicker(null);
                    if (size == 0) {
                        display(new Alert("No devices", "No devices found",
                                null, AlertType.INFO), mainForm);
                    }

                    active = false;
                    break;
                case (STATE_READING):
                    GpsBt gpsBt = GpsBt.instance();
                    if (gpsBt.isConnected()) {
                        gpsState.setText("Connected");
                        Location location = gpsBt.getLocation();
                        quality.setText(location.quality + "");

                        latitude = location.latitude;
                        longitude = location.longitude;

                        latitudeText.setText(latitude + location.northHemi);
                        longitudeText.setText(longitude + location.eastHemi);
                        time.setText(location.utc);
                        satellite.setText(location.nSat + "");

                        try {
                            Thread.sleep(7000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            display(new Alert("Error", e.getMessage(), null,
                                    AlertType.ERROR), mainForm);
                            active = false;
                        }
                    } else {
                        gpsState.setText("Disconnected");
                        active = false;
                    }
                    break;
                }
            }
        }
    }
}
