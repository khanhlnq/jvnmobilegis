/**
 * $Id: UIController.java 180 2007-12-27 08:58:48Z khanh.lnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/UIController.java $
 * $Author: khanh.lnq $
 * $Revision: 180 $
 * $Date: 2007-12-27 15:58:48 +0700 (Thu, 27 Dec 2007) $
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
import javax.microedition.lcdui.Ticker;

/**
 * @author anntv
 * 
 */
public class SortLayerListUI extends List implements CommandListener {

    private final UIController uiController;
    private final Command moveUpCommand;
    private final Command moveDownCommand;
    private final Command moveTopCommand;
    private final Command moveBottomCommand;
    private final Command getMapCommand;
    private Vector sortLayerList;

    public SortLayerListUI(UIController uiController) {
        super(uiController.getString(UIConstants.LAYER_SORT), Choice.EXCLUSIVE);

        setTicker(new Ticker(uiController.getString(UIConstants.SORT_LAYER_TICKER)));

        this.uiController = uiController;
        this.sortLayerList = uiController.getSelectedLayerList();
        
        init(sortLayerList);    
        
        getMapCommand = new Command(uiController.getString(UIConstants.GETMAP), Command.SCREEN, 0);        
        moveTopCommand = new Command(uiController.getString(UIConstants.MOVE_TO_TOP), Command.SCREEN, 1);
        moveUpCommand = new Command(uiController.getString(UIConstants.MOVE_UP), Command.SCREEN, 2);
        moveDownCommand = new Command(uiController.getString(UIConstants.MOVE_DOWN), Command.SCREEN, 3);        
        moveBottomCommand = new Command(uiController.getString(UIConstants.MOVE_TO_BOTTOM), Command.SCREEN, 4);

        addCommand(getMapCommand);
        if (sortLayerList.size() > 1) {
        	addCommand(moveTopCommand);
            addCommand(moveUpCommand);
            addCommand(moveDownCommand);        
            addCommand(moveBottomCommand);
        }
        
        setCommandListener(this);
    }
    
    public void init(Vector sortLayerList) {
    	this.sortLayerList = sortLayerList;
    	deleteAll();    	
    	for (int i = 0; i < sortLayerList.size(); i++) {
            append(sortLayerList.elementAt(i).toString(), null);
        }
    }
    
    /**
     * @return the sortLayerList
     */
    public Vector getSortLayerList() {
        return sortLayerList;
    }
    
    public void moveToTop() {
        if (getSelectedIndex() > 0) {
            int selectedIndex = getSelectedIndex();

            Object selectedItem = sortLayerList.elementAt(selectedIndex);
            sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);
            insert(0, selectedItem.toString(), null);                        
            sortLayerList.insertElementAt(selectedItem, 0);           
            
            setSelectedIndex(0, true);
        }
    }
    
    public void moveUp() {
    	int selectedIndex = getSelectedIndex();
    	Object selectedItem = sortLayerList.elementAt(selectedIndex);

    	if (selectedIndex > 0) {
    		sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);
            
            insert(selectedIndex-1, selectedItem.toString(), null);                        
            sortLayerList.insertElementAt(selectedItem, selectedIndex - 1);            
            
            setSelectedIndex(selectedIndex - 1, true);
        } else {
        	sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);
            
            sortLayerList.insertElementAt(selectedItem, sortLayerList.size());
            insert(sortLayerList.size()-1, selectedItem.toString(), null);                        
                        
            setSelectedIndex(sortLayerList.size()-1, true);
        }
    }

    public void moveDown() {    	
    	int selectedIndex = getSelectedIndex();
        Object selectedItem = sortLayerList.elementAt(selectedIndex);

    	if (getSelectedIndex() < sortLayerList.size()-1) {
    		sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);

            sortLayerList.insertElementAt(selectedItem, selectedIndex + 1);            
            insert(selectedIndex+1, selectedItem.toString(), null);
            
            setSelectedIndex(selectedIndex + 1, true);
        } else {
        	sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);
                    	                        
            sortLayerList.insertElementAt(selectedItem, 0);            
            insert(0, selectedItem.toString(), null);
            
            setSelectedIndex(0, true);
        }
    }

    public void moveToBottom() {
        if (getSelectedIndex() < sortLayerList.size() - 1) {
            int selectedIndex = getSelectedIndex();

            Object selectedItem = sortLayerList.elementAt(selectedIndex);
            sortLayerList.removeElementAt(selectedIndex);
            delete(selectedIndex);                                    
            sortLayerList.insertElementAt(selectedItem, sortLayerList.size());
            insert(sortLayerList.size()-1, selectedItem.toString(), null);
            
            setSelectedIndex(sortLayerList.size() - 1, true);
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == getMapCommand) {
            uiController.getMapRequested();
        } else if (command == moveTopCommand) {
            moveToTop();
        } else if (command == moveUpCommand) {
            moveUp();
        } else if (command == moveDownCommand) {
            moveDown();        
        } else if (command == moveBottomCommand) {
            moveToBottom();
        } else {
            uiController.commandAction(command, displayable);
        }
    }
}