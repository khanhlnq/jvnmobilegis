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
 */

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
 * @Date Created: 22 Jun 2007
 */
package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.StringItem;

import org.javavietnam.gis.client.midp.util.ProgressObserver;

/**
 * @author khanhlnq
 */
public class ProgressObserverUI extends Form implements ProgressObserver,
        CommandListener {

    private UIController uiController;
    private static final int GAUGE_MAX = 10;
    private static final int GAUGE_LEVELS = 2;
    private int current = 0;
    private Gauge gauge;
    private StringItem note;
    private Command stopCommand;
    /**
     * @uml.property name="stoppable"
     */
    private boolean stoppable;
    /**
     * @uml.property name="stopped"
     */
    private boolean stopped;

    public ProgressObserverUI(UIController uiController) {
        super("");

        this.uiController = uiController;

        gauge = new Gauge("", false, GAUGE_MAX, 0);
        note = new StringItem("", "");
        stopCommand = new Command(uiController.getString(UIConstants.STOP),
                Command.STOP, 1);

        append(gauge);
        append(note);
        setCommandListener(this);
    }

    public void init(String note, boolean stoppable) {
        gauge.setValue(0);
        setNote(note);
        setStoppable(stoppable);

        stopped = false;
    }

    public void setNote(String note) {
        setTitle(note);
        this.note.setText(note);
    }

    /**
     * @return Returns the stoppable.
     * @uml.property name="stoppable"
     */
    public boolean isStoppable() {
        return stoppable;
    }

    /**
     * @param stoppable
     *            The stoppable to set.
     * @uml.property name="stoppable"
     */
    public void setStoppable(boolean stoppable) {
        this.stoppable = stoppable;

        if (stoppable) {
            addCommand(stopCommand);
        } else {
            removeCommand(stopCommand);
        }
    }

    /**
     * Indicates whether the user has stopped the progress. This message should
     * be called before calling update.
     * 
     * @uml.property name="stopped"
     */
    public boolean isStopped() {
        return stopped;
    }

    public void updateProgress() {
        current = (current + 1) % GAUGE_LEVELS;

        gauge.setValue(current * GAUGE_MAX / GAUGE_LEVELS);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == stopCommand) {
            stopped = true;
            uiController.stopRequested();
        }
    }
}
