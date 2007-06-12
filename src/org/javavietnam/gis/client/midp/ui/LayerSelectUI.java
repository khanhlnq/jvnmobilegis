/*
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
 *
 *
 * =====================================================
 *
 */

package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import org.javavietnam.gis.shared.midp.model.LayerInformation;


/**
 * @author  Khanh
 */
public class LayerSelectUI extends Form implements CommandListener {

    private UIController uiController;
    private Command backCommand;
    private Command confirmCommand;
    private ChoiceGroup choiceLayer;
    private ChoiceGroup choiceAskNextTime;
    private boolean askNextTime = true;
    String[] askNextTimeArr;

    public LayerSelectUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_SELECT_TITLE));
        this.uiController = uiController;
        askNextTimeArr = new String[2];
        askNextTimeArr[0] = uiController.getString(UIConstants.YES);
        askNextTimeArr[1] = uiController.getString(UIConstants.NO);
        choiceLayer = new ChoiceGroup(uiController.getString(UIConstants.CHOOSE_LAYER_FOR_INFO), ChoiceGroup.EXCLUSIVE);
        choiceAskNextTime = new ChoiceGroup(uiController.getString(UIConstants.ASK_NEXT_TIME), ChoiceGroup.EXCLUSIVE, askNextTimeArr, null);
        choiceAskNextTime.setSelectedIndex(0, true);
        append(choiceLayer);
        append(choiceAskNextTime);

        confirmCommand = new Command(uiController.getString(UIConstants.CONFIRM), Command.OK, 0);
        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);

        addCommand(confirmCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void init(Vector layerList) {
        choiceLayer.deleteAll();
        for (int i = 0; i < layerList.size(); i++) {
            choiceLayer.append(((LayerInformation) layerList.elementAt(i)).getField("name"), null);
        }
        askNextTime = true;
        // choiceAskNextTime.setSelectedIndex(0, true);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        }
        else if (command == confirmCommand) {
            askNextTime = (0 == choiceAskNextTime.getSelectedIndex());
            uiController.getFeatureInfoRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return  Returns the askNextTime.
     * @uml.property  name="askNextTime"
     */
    public boolean isAskNextTime() {
        return askNextTime;
    }

    public String getInfoLayerName() {
        return choiceLayer.getString(choiceLayer.getSelectedIndex());
    }

}
