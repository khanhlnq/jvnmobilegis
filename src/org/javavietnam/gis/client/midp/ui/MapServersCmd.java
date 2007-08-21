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
 * http://jvnmobilegis.googlecode.com/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;

/**
 * This class use to add pre-defined WMS mapserver to TextField. It's just a
 * temporary solution. Will find a better way in future.
 * 
 * @author khanhlnq
 */
public class MapServersCmd implements ItemCommandListener {

	private UIController uiController;

	private final Command mapServer01;
	private final Command mapServer02;
	private final Command mapServer03;

	/**
	 * Creates a new instance of MapServersCmd
	 * 
	 * @param uiController
	 */
	public MapServersCmd(UIController uiController) {
		this.uiController = uiController;

		mapServer01 = new Command("> "
				+ uiController.getString(UIConstants.MAPSERVER_01_NAME),
				Command.SCREEN, 5);
		mapServer02 = new Command("> "
				+ uiController.getString(UIConstants.MAPSERVER_02_NAME),
				Command.SCREEN, 5);
		mapServer03 = new Command("> "
				+ uiController.getString(UIConstants.MAPSERVER_03_NAME),
				Command.SCREEN, 5);
	}

	public void addCommands(TextField item) {
		item.addCommand(mapServer01);
		item.addCommand(mapServer02);
		item.addCommand(mapServer03);

		item.setItemCommandListener(this);
	}

	public void commandAction(Command command, Item item) {
		if (item instanceof TextField) {
			TextField text = (TextField) item;
			if (mapServer01 == command) {
				text.setString(uiController
						.getString(UIConstants.MAPSERVER_01_URL));
			} else if (mapServer02 == command) {
				text.setString(uiController
						.getString(UIConstants.MAPSERVER_02_URL));
			} else if (mapServer03 == command) {
				text.setString(uiController
						.getString(UIConstants.MAPSERVER_03_URL));
			}
		}
	}

}
