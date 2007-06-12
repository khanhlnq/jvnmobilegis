/*
 * MapServersCmd.java
 *
 * Created on 25 August 2006, 11:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;


/**
 * This class use to add pre-defined WMS mapserver to TextField. It's just a temporary solution. Will find a better way in future.
 * @author  khanhlnq
 */
public class MapServersCmd implements ItemCommandListener {

    UIController uiController;

    public final Command mapServer01;
    public final Command mapServer02;
    public final Command mapServer03;
    public final Command mapServer04;

    /** Creates a new instance of MapServersCmd */
    public MapServersCmd(UIController uiController) {
        this.uiController = uiController;

        mapServer01 = new Command("> " + uiController.getString(UIConstants.MAPSERVER_01_NAME), Command.SCREEN, 1);
        mapServer02 = new Command("> " + uiController.getString(UIConstants.MAPSERVER_02_NAME), Command.SCREEN, 1);
        mapServer03 = new Command("> " + uiController.getString(UIConstants.MAPSERVER_03_NAME), Command.SCREEN, 1);
        mapServer04 = new Command("> " + uiController.getString(UIConstants.MAPSERVER_04_NAME), Command.SCREEN, 1);
    }

    public void addCommands(TextField item) {
        item.addCommand(mapServer01);
        item.addCommand(mapServer02);
        item.addCommand(mapServer03);
        item.addCommand(mapServer04);

        item.setItemCommandListener(this);
    }

    public void commandAction(Command command, Item item) {
        if (item instanceof TextField) {
            TextField text = (TextField) item;
            if (mapServer01 == command) {
                text.setString(uiController.getString(UIConstants.MAPSERVER_01_URL));
            }
            else if (mapServer02 == command) {
                text.setString(uiController.getString(UIConstants.MAPSERVER_02_URL));
            }
            else if (mapServer03 == command) {
                text.setString(uiController.getString(UIConstants.MAPSERVER_03_URL));
            }
            else if (mapServer04 == command) {
                text.setString(uiController.getString(UIConstants.MAPSERVER_04_URL));
            }
        }
    }

}
