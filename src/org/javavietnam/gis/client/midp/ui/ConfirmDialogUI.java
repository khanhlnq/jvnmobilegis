/*
 * $Id: PromptDialog.java 140 2007-10-05 07:17:58Z khanhlnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/PromptDialog.java $
 * $Author: khanhlnq $
 * $Revision: 140 $
 * $Date: 2007-10-05 14:17:58 +0700 (Fri, 05 Oct 2007) $
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
 * @Date Created: 17 Aug 2007
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

import org.javavietnam.gis.client.midp.model.MessageCodes;
import org.javavietnam.gis.client.midp.model.ModelFacade;
import org.javavietnam.gis.shared.midp.ApplicationException;

public class ConfirmDialogUI extends Form implements CommandListener {
	private UIController uiController;
	private int messageId;

	private StringItem messageItem;
	private Command yesCommand;
	private Command noCommand;
	private static final String BASE_NAME_MESSAGE_RESOURCES = "MessageResources";
	private ModelFacade model;

	public ConfirmDialogUI(UIController uiController) {
		super("");
		this.uiController = uiController;
		messageItem = new StringItem("Message: ", "");
		append(messageItem);
		yesCommand = new Command(this.uiController.getString(UIConstants.YES), Command.OK, 1);
		addCommand(yesCommand);
		noCommand = new Command(this.uiController.getString(UIConstants.NO), Command.CANCEL, 1);
		addCommand(noCommand);
		setCommandListener(this);
	}

	public void showConfirm(int messageId) {
		this.messageId = messageId;
		this.messageItem.setText(this.uiController.getMessage(messageId));
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if(c == yesCommand) {
			this.uiController.confirmAction(messageId, true);
		}
		if(c == noCommand) {
			this.uiController.confirmAction(messageId, false);
		}
	}

}
