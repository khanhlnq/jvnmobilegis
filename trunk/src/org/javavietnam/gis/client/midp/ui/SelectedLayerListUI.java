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
public class SelectedLayerListUI extends List implements CommandListener {

	private final UIController uiController;

	private final Command moveUpCommand;

	private final Command moveDownCommand;

	private final Command moveTopCommand;

	private final Command moveBottomCommand;

	private final Command getMapCommand;

	private Vector selectedLayerList;

	public SelectedLayerListUI(UIController uiController) {
		super(uiController.getString(UIConstants.LAYER_SORT), Choice.EXCLUSIVE);

		this.uiController = uiController;
		setSelectedLayerList(uiController.getSelectedLayerList());

		display(selectedLayerList);

		getMapCommand = new Command(uiController.getString(UIConstants.GETMAP),
				Command.SCREEN, 0);
		moveUpCommand = new Command(
				uiController.getString(UIConstants.MOVE_UP), Command.SCREEN, 1);
		moveDownCommand = new Command(uiController
				.getString(UIConstants.MOVE_DOWN), Command.SCREEN, 2);
		moveTopCommand = new Command(uiController
				.getString(UIConstants.MOVE_TOP), Command.SCREEN, 3);
		moveBottomCommand = new Command(uiController
				.getString(UIConstants.MOVE_BOTTOM), Command.SCREEN, 4);

		addCommand(getMapCommand);
		addCommand(moveUpCommand);
		addCommand(moveDownCommand);
		addCommand(moveTopCommand);
		addCommand(moveBottomCommand);

		setCommandListener(this);
	}

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
	public void setSelectedLayerList(Vector selectedLayerList) {
		this.selectedLayerList = selectedLayerList;
	}

	public void moveUp() {
		if (getSelectedIndex() > 0) {
			int selectedIndex = getSelectedIndex();
			Object selectedItem = selectedLayerList.elementAt(selectedIndex);
			selectedLayerList.removeElementAt(selectedIndex);
			selectedLayerList.insertElementAt(selectedItem, selectedIndex - 1);
			display(selectedLayerList);
			setSelectedIndex(selectedIndex - 1, true);
		}
	}

	public void moveDown() {
		if (getSelectedIndex() < selectedLayerList.size()-1) {
			int selectedIndex = getSelectedIndex();

			Object selectedItem = selectedLayerList.elementAt(selectedIndex);
			selectedLayerList.removeElementAt(selectedIndex);
			selectedLayerList.insertElementAt(selectedItem, selectedIndex + 1);

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

			display(selectedLayerList);
			setSelectedIndex(0, true);
		}
	}

	public void moveBottom() {
		if (getSelectedIndex() < selectedLayerList.size()-1) {
			int selectedIndex = getSelectedIndex();

			Object selectedItem = selectedLayerList.elementAt(selectedIndex);
			selectedLayerList.removeElementAt(selectedIndex);
			selectedLayerList.insertElementAt(selectedItem, selectedLayerList
					.size());

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