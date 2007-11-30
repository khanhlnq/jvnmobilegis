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

package org.javavietnam.gis.client.midp.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import org.javavietnam.gis.client.midp.util.ProgressObserver;
import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.IndexedResourceBundle;
import org.javavietnam.gis.shared.midp.model.ModelException;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;

/**
 * @author khanhlnq
 */
public class ModelFacade {

    private static final String BASE_NAME_ERROR_RESOURCES = "ErrorResources";
    private static final String L10N_ROOT_DIR = "/l10n/";
    /**
     * @uml.property name="locale"
     */
    private String locale;
    private static final int[] errorMessageCodeMap = new int[] {
            ErrorMessageCodes.ERROR_GENERAL,
            ErrorMessageCodes.ERROR_CANNOT_CONNECT,
            ErrorMessageCodes.NO_SELECTED_LAYER,
            ErrorMessageCodes.NO_SELECTED_POINT };

    private final RemoteModelProxy remoteModel;

    private final LocalModel localModel;

    public ModelFacade(String locale) throws ApplicationException {
        localModel = new LocalModel();
        remoteModel = new RemoteModelProxy();

        this.setLocale(locale);

    }

    public ModelFacade() throws ApplicationException {
        localModel = new LocalModel();
        remoteModel = new RemoteModelProxy();

    }

    public void setProgressObserver(ProgressObserver progressObserver) {
        localModel.setProgressObserver(progressObserver);
        remoteModel.setProgressObserver(progressObserver);
    }

    public void init() throws ApplicationException {
        localModel.init();
        remoteModel.init();

    }

    public void destroy() throws ApplicationException {
        localModel.destroy();
        remoteModel.destroy();

    }

    /**
     * @param locale
     *            The locale to set.
     * @throws org.javavietnam.gis.shared.midp.ApplicationException
     * @uml.property name="locale"
     */
    public void setLocale(String locale) throws ApplicationException {
        IndexedResourceBundle errorBundle;

        this.locale = locale;

        errorBundle = getResourceBundle(BASE_NAME_ERROR_RESOURCES);

        ApplicationException.setResourceBundle(errorBundle);

    }

    /**
     * @return the locale
     * @uml.property name="locale"
     */
    public String getLocale() {
        return locale;
    }

    public IndexedResourceBundle getResourceBundle(String baseName)
            throws ApplicationException {
        IndexedResourceBundle resourceBundle;

        try {
            InputStream stream = getClass().getResourceAsStream(
                    makeResourceBundleName(baseName, locale));

            resourceBundle = stream != null ? IndexedResourceBundle
                    .getBundleFromPropertyFile(locale, stream) : null;
        } catch (IOException ioe) {
            resourceBundle = null;
        }

        return resourceBundle;
    }

    private String makeResourceBundleName(String baseName, String locale) {
        return new StringBuffer(L10N_ROOT_DIR).append(baseName).append("-")
                .append(locale).append(".properties").toString();
    }

    public Image getMapWMS(WMSRequestParameter requestParam, Vector layerList)
            throws ApplicationException {
        try {
            return remoteModel.getMapWMS(requestParam, layerList);
        } catch (ModelException me) {
            throw new ApplicationException(errorMessageCodeMap[me
                    .getCauseCode()]);
        }
    }

    /*
     * public String findPathWMS(WMSRequestParameter requestParam) throws
     * ApplicationException { try { return
     * remoteModel.findPathWMS(requestParam); } catch (ModelException me) {
     * throw new ApplicationException(errorMessageCodeMap[me.getCauseCode()]); } }
     */

    public String checkUpdate(String updateURL) throws ApplicationException {
        try {
            return remoteModel.checkUpdate(updateURL);
        } catch (ModelException me) {
            throw new ApplicationException(errorMessageCodeMap[me
                    .getCauseCode()]);
        }
    }

    public String searchFeature(SearchFeatureParameter searchParam)
            throws ApplicationException {
        try {
            return remoteModel.searchFeature(searchParam);
        } catch (ModelException me) {
            throw new ApplicationException(errorMessageCodeMap[me
                    .getCauseCode()]);
        }
    }

    public String getFeatureInfo(WMSRequestParameter requestParam,
            Vector layerList, String infoLayer) throws ApplicationException {
        try {
            return remoteModel.getFeatureInfo(requestParam, layerList,
                    infoLayer);
        } catch (ModelException me) {
            throw new ApplicationException(errorMessageCodeMap[me
                    .getCauseCode()]);
        }
    }

    /*
     * public Image viewPathWMS(WMSRequestParameter requestParam) throws
     * ApplicationException { try { return
     * remoteModel.viewPathWMS(requestParam); } catch (ModelException me) {
     * throw new ApplicationException(errorMessageCodeMap[me.getCauseCode()]); } }
     */

    public Vector getCapabilitiesWMS(String serverURL)
            throws ApplicationException {
        try {
            return remoteModel.getCapabilitiesWMS(serverURL);
        } catch (ModelException me) {
            throw new ApplicationException(errorMessageCodeMap[me
                    .getCauseCode()]);
        }
    }

    public Preferences getPreferences() throws ApplicationException {
        return localModel.getPreferences();
    }

    public void setPreferences(Preferences preferences)
            throws ApplicationException {
        localModel.setPreferences(preferences);

    }

    public String getWwwAuthenticate() throws ApplicationException {
        return remoteModel.getWwwAuthenticate();
    }

    public void setCredentials(String credentials) throws ApplicationException {
        remoteModel.setCredentials(credentials);
    }

}
