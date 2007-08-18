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
 * http://jvnmobilegis.googlecode.com/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq at javavietnam.org
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.model;

import org.javavietnam.gis.client.midp.util.ProgressObserver;
import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.model.ModelException;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;

import javax.microedition.lcdui.Image;
import java.util.Vector;


/**
 */
abstract public class RemoteModelRequestHandler implements RequestHandler {

    /**
     * @link aggregation
     */
    private RemoteModelRequestHandler nextHandler;
    private static ProgressObserver progressObserver;

    public RemoteModelRequestHandler(RemoteModelRequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * @return Returns the nextHandler.
     * @uml.property name="nextHandler"
     */
    public RequestHandler getNextHandler() {
        return nextHandler;
    }

    public void init() throws ApplicationException {
        if (nextHandler != null) {
            nextHandler.init();
        }

    }

    public void destroy() throws ApplicationException {
        if (nextHandler != null) {
            nextHandler.destroy();
        }

    }

    /**
     * @param progressObserver The progressObserver to set.
     * @uml.property name="progressObserver"
     */
    public static void setProgressObserver(ProgressObserver progressObserver) {
        RemoteModelRequestHandler.progressObserver = progressObserver;
    }

    static void updateProgress() throws ApplicationException {
        if (progressObserver != null) {
            if (!progressObserver.isStopped()) {
                progressObserver.updateProgress();

                return;
            }

            throw new ApplicationException(ErrorMessageCodes.ERROR_OPERATION_INTERRUPTED);
        }
    }

    private RemoteModelRequestHandler getRemoteModelRequestHandler() throws ApplicationException {
        if (nextHandler != null) {
            return nextHandler;
        }

        throw new ApplicationException(ErrorMessageCodes.ERROR_BROKEN_HANDLER_CHAIN);
    }

    public Image getMapWMS(WMSRequestParameter requestParam, Vector layerList) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().getMapWMS(requestParam, layerList);
    }

    public String checkUpdate(String updateURL) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().checkUpdate(updateURL);
    }

    /*public String findPathWMS(WMSRequestParameter requestParam) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().findPathWMS(requestParam);
    }*/

    public String searchFeature(SearchFeatureParameter searchParam) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().searchFeature(searchParam);
    }

    public String getFeatureInfo(WMSRequestParameter requestParam, Vector layerList, String infoLayer) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().getFeatureInfo(requestParam, layerList, infoLayer);
    }

    /*public Image viewPathWMS(WMSRequestParameter requestParam) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().viewPathWMS(requestParam);
    }*/

    public String getCapabilitiesWMS(String serviceURL) throws ModelException, ApplicationException {
        return getRemoteModelRequestHandler().getCapabilitiesWMS(serviceURL);
    }

    public String getWwwAuthenticate() throws ApplicationException {
        return getRemoteModelRequestHandler().getWwwAuthenticate();
    }

    public void setCredentials(String credentials) throws ApplicationException {
        getRemoteModelRequestHandler().setCredentials(credentials);
    }
}
