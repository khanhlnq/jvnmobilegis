/*
 * Copyright 2001, 2002, 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Except for any files in PNG format (which are marked with the filename
 * extension ".png"), GIF format (which are marked with the filename
 * extension ".gif"), or JPEG format (which are marked with the filename
 * extension ".jpg"), redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 * - Redistribution of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * You may compile, use, perform and display the following files with
 * original Java Smart Ticket Sample Application code obtained from Sun
 * Microsystems, Inc. only:
 * - files in PNG format and having the ".png" extension
 * - files in GIF format and having the ".gif" extension
 * - files in JPEG format and having the ".jpg" extension
 * You may not modify or redistribute .png, .gif, or .jpg files in any
 * form, in whole or in part, by any means without prior written
 * authorization from Sun Microsystems, Inc. and its licensors, if any.
 * Neither the name of Sun Microsystems, Inc., the 'Java Smart Ticket
 * Sample Application', 'Java', 'Java'-based names, or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or maintenance
 * of any nuclear facility.
 * $Id$
 */
/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;


/**
 */
public class MainMenuUI extends List implements CommandListener {

    private UIController uiController;
    private Command exitCommand;

    public MainMenuUI(UIController uiController) {
        super(uiController.getString(UIConstants.MOBILEGIS_CLIENT), List.IMPLICIT);
        try {
            this.uiController = uiController;

            append(uiController.getString(UIConstants.SERVER_UI_TITLE), null);
            append(uiController.getString(UIConstants.PREFERENCES), null);
            append(uiController.getString(UIConstants.CHECK_UPDATE), null);
            exitCommand = new Command(uiController.getString(UIConstants.EXIT), Command.EXIT, 1);

            addCommand(exitCommand);
            setCommandListener(this);
        }
        catch (Exception e) {
            System.out.println("*************** Exception: ");
            e.printStackTrace();
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (List.SELECT_COMMAND == command) {
            String label = getString(getSelectedIndex());

            if (label.equals(uiController.getString(UIConstants.SERVER_UI_TITLE))) {
                uiController.mapServerRequested();
            }
            else if (label.equals(uiController.getString(UIConstants.PREFERENCES))) {
                uiController.preferencesUIRequested();
            }
            else if (label.equals(uiController.getString(UIConstants.CHECK_UPDATE))) {
                uiController.checkUpdateRequested();
            }
        }
        else {
            if (command == exitCommand) {
                uiController.exitRequested();
            }
            else {
                uiController.commandAction(command, displayable);
            }
        }
    }

}
