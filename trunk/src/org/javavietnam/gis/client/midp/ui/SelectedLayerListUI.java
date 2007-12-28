/**
 * @version: 1.0
 * @author: An Nguyen
 * @Date Created: 27 Dec 2007
 */
package org.javavietnam.gis.client.midp.ui;

import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 * @author anntv
 * 
 */
// FIXME VanAn: Refactor "SelectedLayerListUI" class to "SortLayerListUI"
public class SelectedLayerListUI extends List implements CommandListener {

    private final UIController uiController;
    private final Command moveUpCommand;
    private final Command moveDownCommand;
    private final Command moveTopCommand;
    private final Command moveBottomCommand;
    private final Command getMapCommand;
    private Vector selectedLayerList;

    public SelectedLayerListUI(UIController uiController) {
        // TODO VanAn: Add ticker "Mark a layer and select moving action. NOTE: Moving action is not performed on highlighted layer" to SelectedLayerListUI
        super(uiController.getString(UIConstants.LAYER_SORT), Choice.EXCLUSIVE);

        this.uiController = uiController;
        // FIXME VanAn: This will only happen at the first time.
        // Should use an init() method
        setSelectedLayerList(uiController.getSelectedLayerList());

        // FIXME VanAn: Remove this
        display(selectedLayerList);

        getMapCommand = new Command(uiController.getString(UIConstants.GETMAP),
            Command.SCREEN, 0);
        // FIXME VanAn: Menu should be follow this order:
        //    "Move to top"
        //    "Move up"
        //    "Move down"
        //    "Move to bottom"
        moveUpCommand = new Command(
            uiController.getString(UIConstants.MOVE_UP), Command.SCREEN, 1);
        moveDownCommand = new Command(uiController.getString(UIConstants.MOVE_DOWN), Command.SCREEN, 2);
        moveTopCommand = new Command(uiController.getString(UIConstants.MOVE_TOP), Command.SCREEN, 3);
        moveBottomCommand = new Command(uiController.getString(UIConstants.MOVE_BOTTOM), Command.SCREEN, 4);

        addCommand(getMapCommand);
        addCommand(moveUpCommand);
        addCommand(moveDownCommand);
        addCommand(moveTopCommand);
        addCommand(moveBottomCommand);

        setCommandListener(this);
    }

    // FIXME VanAn: Should not re-draw the whole list. Remove this method.
    public void display(Vector selectedLayerList) {
        deleteAll();
        for (int i = 0; i < selectedLayerList.size(); i++) {
            append(selectedLayerList.elementAt(i).toString(), null);
        }
    }

    /**
     * @return the selectedLayerList
     */
    public Vector getSelectedLayerList() {
        return selectedLayerList;
    }

    /**
     * @param selectedLayerList
     *            the selectedLayerList to set
     */
    // FIXME VanAn: Should change to an init() method
    public void setSelectedLayerList(Vector selectedLayerList) {
        this.selectedLayerList = selectedLayerList;
    // FIXME VanAn: Delete all and re-initialize the List items here
    }

    public void moveUp() {
        // FIXME VanAn: The list should be circled for Move Up & Move Down
        if (getSelectedIndex() > 0) {
            int selectedIndex = getSelectedIndex();
            Object selectedItem = selectedLayerList.elementAt(selectedIndex);
            selectedLayerList.removeElementAt(selectedIndex);
            selectedLayerList.insertElementAt(selectedItem, selectedIndex - 1);

            // FIXME VanAn: Use List.delete, List.insert instead of re-draw the whole List
            display(selectedLayerList);
            setSelectedIndex(selectedIndex - 1, true);
        }
    }

    public void moveDown() {
        if (getSelectedIndex() < selectedLayerList.size() - 1) {
            int selectedIndex = getSelectedIndex();

            Object selectedItem = selectedLayerList.elementAt(selectedIndex);
            selectedLayerList.removeElementAt(selectedIndex);
            selectedLayerList.insertElementAt(selectedItem, selectedIndex + 1);

            // FIXME VanAn: Use List.delete, List.insert instead of re-draw the whole List
            display(selectedLayerList);
            setSelectedIndex(selectedIndex + 1, true);
        }
    }

    public void moveTop() {
        if (getSelectedIndex() > 0) {
            int selectedIndex = getSelectedIndex();

            Object selectedItem = selectedLayerList.elementAt(selectedIndex);
            selectedLayerList.removeElementAt(selectedIndex);
            selectedLayerList.insertElementAt(selectedItem, 0);

            // FIXME VanAn: Use List.delete, List.insert instead of re-draw the whole List
            display(selectedLayerList);
            setSelectedIndex(0, true);
        }
    }

    public void moveBottom() {
        if (getSelectedIndex() < selectedLayerList.size() - 1) {
            int selectedIndex = getSelectedIndex();

            Object selectedItem = selectedLayerList.elementAt(selectedIndex);
            selectedLayerList.removeElementAt(selectedIndex);
            selectedLayerList.insertElementAt(selectedItem, selectedLayerList.size());

            // FIXME VanAn: Use List.delete, List.insert instead of re-draw the whole List
            display(selectedLayerList);
            setSelectedIndex(selectedLayerList.size() - 1, true);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == getMapCommand) {
            uiController.getMapRequested();
        } else if (command == moveUpCommand) {
            moveUp();
        } else if (command == moveDownCommand) {
            moveDown();
        } else if (command == moveTopCommand) {
            moveTop();
        } else if (command == moveBottomCommand) {
            moveBottom();
        } else {
            uiController.commandAction(command, displayable);
        }
    }
}
