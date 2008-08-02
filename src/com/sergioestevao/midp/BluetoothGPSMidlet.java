/**
 * S�rgio Est�v�o
 * MIDP Adventures
 * www.sergioestevao.com/midp
 */

package com.sergioestevao.midp;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.Ticker;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.javavietnam.gis.client.midp.util.BTManager;
import org.javavietnam.gis.client.midp.util.GpsBt;
import org.javavietnam.gis.client.midp.util.Location;

public class BluetoothGPSMidlet extends MIDlet implements CommandListener,
        Runnable {

    private Command cmdBack;
    private Form mainForm;
    private Command cmdSearchGps;
    private Form gpsForm;
    private Command cmdSearch;
    private Command cmdSelect;
    private Command cmdExit;
    private ChoiceGroup choiceGps;
    private StringItem gpsState;
    private StringItem quality;
    private StringItem latitude;
    private StringItem longitude;
    private StringItem time;
    private StringItem satellite;

    private static final int STATE_IDLE = 0;
    private static final int STATE_SEARCH = 1;
    private static final int STATE_READING = 2;

    private boolean active = false;
    private int state = STATE_IDLE;

    private Thread thread;

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        exit();
    }

    protected void pauseApp() {

    }

    protected void startApp() throws MIDletStateChangeException {
        display(initMainForm());
    }

    public void commandAction(Command cmd, Displayable display) {
        if (display == mainForm) {
            if (cmd == cmdSearchGps) {
                display(initGPSForm());
            } else if (cmd == cmdExit) {
                exit();
            }
        } else if (display == gpsForm) {
            if (cmd == cmdSearch) {
                doAction(STATE_SEARCH);
            }
            if (cmd == cmdSelect) {
                int option = choiceGps.getSelectedIndex();
                // any device selected?
                if (option != -1) {
                    // set gps reader to selected device
                    GpsBt.instance().setDevice(
                            BTManager.instance().getServiceURL(option),
                            BTManager.instance().getDeviceName(option));
                    doAction(STATE_READING);
                    // start reading value;
                    GpsBt.instance().start();
                    display(initMainForm());
                }
            } else if (cmd == cmdBack) {
                display(initMainForm());
            }
        }
    }

    public void exit() {
        notifyDestroyed();
    }

    public void display(Displayable display) {
        // shows the display.
        Display.getDisplay(this).setCurrent(display);
    }

    public void display(Alert alert, Displayable next) {
        // shows the alert screen.
        Display.getDisplay(this).setCurrent(alert, next);
    }

    public Command initBackCommand() {
        if (cmdBack == null) {
            cmdBack = new Command("Back", Command.BACK, 1);
        }
        return cmdBack;
    }

    public Displayable initMainForm() {
        if (mainForm == null) {
            mainForm = new Form("Gps Info");
            mainForm.setCommandListener(this);
            cmdSearchGps = new Command("Search", Command.ITEM, 1);
            cmdExit = new Command("Exit", Command.EXIT, 1);
            mainForm.addCommand(cmdSearchGps);
            mainForm.addCommand(cmdExit);

            gpsState = new StringItem("Status", "No Gps Found");
            quality = new StringItem("Quality", "No Gps Found");
            latitude = new StringItem("Latitude", "No Gps Found");
            longitude = new StringItem("Longitude", "No Gps Found");
            time = new StringItem("Time", "NA");
            satellite = new StringItem("Satellite", "No Gps Found");

            mainForm.append(gpsState);
            mainForm.append(quality);
            mainForm.append(latitude);
            mainForm.append(longitude);
            mainForm.append(time);
            mainForm.append(satellite);

        }
        return mainForm;
    }

    public Displayable initGPSForm() {
        if (gpsForm == null) {
            gpsForm = new Form("Gps Search");
            gpsForm.setCommandListener(this);
            cmdSearch = new Command("Search", Command.ITEM, 1);
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
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        state = action;
    }

    public void run() {
        active = true;
        while (active) {
            switch (state) {
            case (STATE_IDLE):
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
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
                    display(new Alert("No devices found"));
                }
                doAction(STATE_IDLE);
                break;
            case (STATE_READING):
                GpsBt gpsBt = GpsBt.instance();
                if (gpsBt.isConnected()) {
                    gpsState.setText("Connected");
                    Location location = gpsBt.getLocation();
                    quality.setText(location.quality + "");
                    latitude.setText(location.latitude + location.northHemi);
                    longitude.setText(location.longitude + location.eastHemi);
                    time.setText(location.utc);
                    satellite.setText(location.nSat + "");
                } else {
                    gpsState.setText("Disconnected");
                }
                break;
            }
        }
    }
}
