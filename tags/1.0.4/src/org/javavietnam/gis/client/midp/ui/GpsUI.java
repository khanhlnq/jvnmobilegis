package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import org.javavietnam.gis.client.midp.util.BTManager;
import org.javavietnam.gis.client.midp.util.GpsBt;

public class GpsUI extends Form implements CommandListener {

    private UIController uiController;
    private ChoiceGroup choiceGps;
    private Command cmdSearch;
    private Command cmdSelect;
    private Command cmdBack;

    public GpsUI(UIController uiController) {
        super(uiController.getString(UIConstants.GPS_SEARCH));
        this.uiController = uiController;

        choiceGps = new ChoiceGroup(uiController.getString(UIConstants.GPS)
                + ":", Choice.EXCLUSIVE);
        append(choiceGps);

        cmdSearch = new Command(uiController.getString(UIConstants.SEARCH),
                Command.ITEM, 2);
        addCommand(cmdSearch);
        cmdSelect = new Command(uiController.getString(UIConstants.SELECT),
                Command.ITEM, 1);
        addCommand(cmdSelect);
        cmdBack = new Command("Back", Command.BACK, 1);
        addCommand(cmdBack);

        setCommandListener(this);
    }

    public int searchDevice() {
        // setTicker(new Ticker(uiController
        // .getString(UIConstants.SEARCHING_DEVICES)));
        choiceGps.deleteAll();

        BTManager.instance().find(BTManager.getRFCOMM_UUID());
        int size = BTManager.instance().btDevicesFound.size();
        for (int i = 0; i < size; i++) {
            choiceGps.append(BTManager.instance().getDeviceName(i), null);
        }

        // setTicker(null);

        return size;
    }

    public void commandAction(Command cmd, Displayable display) {
        if (GpsBt.instance().isActive || GpsBt.instance().isConnected()) {
            GpsBt.instance().stop();
        }

        if (cmd == cmdSearch) {
            uiController.gpsSearchingRequested();
        }
        if (cmd == cmdSelect) {
            int option = choiceGps.getSelectedIndex();
            // any device selected?
            if (option != -1) {
                // set gps reader to selected device
                GpsBt.instance().setDevice(
                        BTManager.instance().getServiceURL(option),
                        BTManager.instance().getDeviceName(option));
                // start reading value;
                GpsBt.instance().start();

                uiController.gpsReadingRequested();
            }
        } else if (cmd == cmdBack) {
            uiController.lbsMainUIRequested();
        }
    }
}
