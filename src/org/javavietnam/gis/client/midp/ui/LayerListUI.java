/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;


/**
 */
public class LayerListUI extends List implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command getMapCommand;

    private Vector layerList;
    private boolean[] layerFlags;

    public LayerListUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_LIST_TITLE), List.MULTIPLE);
        this.uiController = uiController;
        setLayerList(new Vector());

        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);
        getMapCommand = new Command(uiController.getString(UIConstants.GETMAP), Command.SCREEN, 0);
        addCommand(backCommand);
        addCommand(getMapCommand);
        setCommandListener(this);
    }

    public void init(Vector treeNode) {
        deleteAll();
        setTitle(treeNode + "");
        layerList.removeAllElements();
        for (int i = 0; i < treeNode.size(); i++) {
            // System.out.println("**** treeNode class name: " + treeNode.getClass().getName() + ". Element class name:
            // " +
            // treeNode.elementAt(i).getClass().getName());
            LayerInformation layerInfo = ((TreeNode) treeNode.elementAt(i)).getLayerInformation();
            layerList.addElement(layerInfo);
            append(layerInfo.getField("name"), null);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.mapServerRequested();
        }
        else if (command == getMapCommand) {
            uiController.getMapRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return  Returns the layerList.
     * @uml.property  name="layerList"
     */
    public Vector getLayerList() {
        return layerList;
    }

    /**
     * @param layerList  The layerList to set.
     * @uml.property  name="layerList"
     */
    private void setLayerList(Vector layerList) {
        this.layerList = layerList;
    }

    public Vector getSelectedLayerList() {
        layerFlags = null;
        layerFlags = new boolean[size()];
        Vector selectLayerList = new Vector();

        getSelectedFlags(layerFlags);
        for (int i = 0; i < layerList.size(); i++) {
            if (layerFlags[i]) {
                LayerInformation layerInfo = (LayerInformation) layerList.elementAt(i);
                selectLayerList.addElement(layerInfo);
            }
        }
        return selectLayerList;
    }

}
