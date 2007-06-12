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
package org.javavietnam.gis.client.midp.model;

import org.javavietnam.gis.client.midp.util.ProgressObserver;
import org.javavietnam.gis.shared.midp.ApplicationException;


/**
 */
public class LocalModel {

    /**
     * @link aggregation
     */
    private RMSAdapter rmsAdapter;
    private Preferences preferences = null;
    protected static ProgressObserver progressObserver;

    public LocalModel() throws ApplicationException {
        rmsAdapter = new RMSAdapter();
    }

    /**
     * @param progressObserver  the progressObserver to set
     * @uml.property  name="progressObserver"
     */
    public static void setProgressObserver(ProgressObserver progressObserver) {
        progressObserver = progressObserver;
    }

    public void init() throws ApplicationException {
    }

    public void destroy() throws ApplicationException {
        // cleanup();
        rmsAdapter.closeRecordStores();
    }

    /*
     * This is used as cache and is maintained up-to-date, getting the preferences always returns the same instance,
     * setting new preferences does not replace this instance but updates the individual attributes/properties of this
     * instance
     */
    /**
     * @return  the preferences
     * @uml.property  name="preferences"
     */
    public Preferences getPreferences() throws ApplicationException {
        if (preferences == null) {
            IndexEntry indexEntry = rmsAdapter.getIndexEntry("*", IndexEntry.TYPE_PREFERENCES, IndexEntry.MODE_ANY);

            if (indexEntry != null) {
                preferences = rmsAdapter.loadPreferences(indexEntry.getRecordId());
            }
            else {
                preferences = new Preferences();

                int recordId = rmsAdapter.storePreferences(preferences, indexEntry != null ? indexEntry.getRecordId() : -1);

                indexEntry = new IndexEntry(recordId, IndexEntry.TYPE_PREFERENCES, "*", IndexEntry.MODE_PERSISTING);

                rmsAdapter.addIndexEntry(indexEntry);
            }
        }

        return preferences;
    }

    // public void setPreferences() throws ApplicationException {
    /**
     * @param preferences  the preferences to set
     * @uml.property  name="preferences"
     */
    public void setPreferences(Preferences preferences) throws ApplicationException {
        this.preferences.copy(preferences);

        IndexEntry indexEntry = rmsAdapter.getIndexEntry("*", IndexEntry.TYPE_PREFERENCES, IndexEntry.MODE_ANY);
        int recordId = rmsAdapter.storePreferences(preferences, indexEntry != null ? indexEntry.getRecordId() : -1);

        if (indexEntry == null) {
            indexEntry = new IndexEntry(recordId, IndexEntry.TYPE_PREFERENCES, "*", IndexEntry.MODE_PERSISTING);

            rmsAdapter.addIndexEntry(indexEntry);
        }

        return;
    }

}
