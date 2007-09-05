// Copyright 2003 Nokia Corporation.
//
// THIS SOURCE CODE IS PROVIDED 'AS IS', WITH NO WARRANTIES WHATSOEVER,
// EXPRESS OR IMPLIED, INCLUDING ANY WARRANTY OF MERCHANTABILITY, FITNESS
// FOR ANY PARTICULAR PURPOSE, OR ARISING FROM A COURSE OF DEALING, USAGE
// OR TRADE PRACTICE, RELATING TO THE SOURCE CODE OR ANY WARRANTY OTHERWISE
// ARISING OUT OF ANY PROPOSAL, SPECIFICATION, OR SAMPLE AND WITH NO
// OBLIGATION OF NOKIA TO PROVIDE THE LICENSEE WITH ANY MAINTENANCE OR
// SUPPORT. FURTHERMORE, NOKIA MAKES NO WARRANTY THAT EXERCISE OF THE
// RIGHTS GRANTED HEREUNDER DOES NOT INFRINGE OR MAY NOT CAUSE INFRINGEMENT
// OF ANY PATENT OR OTHER INTELLECTUAL PROPERTY RIGHTS OWNED OR CONTROLLED
// BY THIRD PARTIES
//
// Furthermore, information provided in this source code is preliminary,
// and may be changed substantially prior to final release. Nokia Corporation
// retains the right to make changes to this source code at
// any time, without notice. This source code is provided for informational
// purposes only.
//
// Nokia and Nokia Connecting People are registered trademarks of Nokia
// Corporation.
// Java and all Java-based marks are trademarks or registered trademarks of
// Sun Microsystems, Inc.
// Other product and company names mentioned herein may be trademarks or
// trade names of their respective owners.
//
// A non-exclusive, non-transferable, worldwide, limited license is hereby
// granted to the Licensee to download, print, reproduce and modify the
// source code. The licensee has the right to market, sell, distribute and
// make available the source code in original or modified form only when
// incorporated into the programs developed by the Licensee. No other
// license, express or implied, by estoppel or otherwise, to any other
// intellectual property rights is granted herein.
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
import javax.microedition.lcdui.TextField;

/**
 * @author khanhlnq
 */
public class PromptDialog extends Form implements CommandListener {

    private UIController uiController;

    private final StringItem instructItem;
    private final TextField userField;
    private final TextField passwordField;
    private final Command okCommand;
    private final Command cancelCommand;
    private boolean wasCancelled;

    public PromptDialog(UIController uiController) {
        super("");
        this.uiController = uiController;

        instructItem = new StringItem(uiController
                .getString(UIConstants.AUTH_REQUIRED), uiController
                .getString(UIConstants.ENTER_LOGIN_INFO));
        append(instructItem);
        userField = new TextField(uiController.getString(UIConstants.USER), "",
                16, TextField.ANY);
        append(userField);
        passwordField = new TextField(uiController
                .getString(UIConstants.PASSWORD), "", 16, TextField.PASSWORD);
        append(passwordField);
        okCommand = new Command(uiController.getString(UIConstants.OK),
                Command.OK, 2);
        addCommand(okCommand);
        cancelCommand = new Command(uiController.getString(UIConstants.CANCEL),
                Command.CANCEL, 2);
        addCommand(cancelCommand);

        setCommandListener(this);
    }

    // public void promptForInput(String realm) {
    // this.setTitle(realm);

    // synchronized (this) {
    // try {
    // wait();
    // // make caller wait on our monitor
    // }
    // catch (InterruptedException e) {
    // // never thrown in MIDP
    //
    // }
    // }
    // }

    public void commandAction(Command c, Displayable d) {
        if (c == okCommand) {
            wasCancelled = false;
            uiController.calculateCredentials();
            // synchronized (this) {
            // notify();
            // wake up caller
            // }
            // synchronized (this) {
            // notify();
            // wake up caller
            // }
        } else if (c == cancelCommand) {
            wasCancelled = true;
            uiController.mainMenuRequested();
            // synchronized (this) {
            // notify();
            // wake up caller
            // }
        }
    }

    public String getUsername() {
        String username = null;
        if (!wasCancelled) {
            username = userField.getString().trim();
        }
        return username;
    }

    public String getPassword() {
        String password = null;
        if (!wasCancelled) {
            password = passwordField.getString().trim();
        }
        return password;
    }
}
