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
 * khanh.lnq at javavietnam.org
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.model;

import org.javavietnam.gis.shared.midp.ApplicationException;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import java.io.*;


public class RMSAdapter {

    public static final String RECORD_STORE_INDEX = "INDEX";
    public static final String RECORD_STORE_LOCAL_DATA = "LOCAL_DATA";
    private RecordStore indexRecordStore;
    private RecordStore localDataRecordStore;

    /**
    * @link dependency
     */
    /* # IndexEntry lnkIndexEntry; */
    private static class IndexEntryFilter implements RecordFilter {

        private String key;
        private int type;
        private int mode;

        public IndexEntryFilter(String key, int type) {
            this(key, type, IndexEntry.MODE_ANY);
        }

        public IndexEntryFilter(String key, int type, int mode) {
            this.key = key;
            this.type = type;
            this.mode = mode;

            return;
        }

        public boolean matches(byte[] candidate) {
            try {
                return IndexEntry.matches(candidate, key, type, mode);
            }
            catch (IOException ioe) {
                ioe.printStackTrace();

                return false;
            }
        }

    }

    public RMSAdapter() throws ApplicationException {
        try {
            indexRecordStore = RecordStore.openRecordStore(RECORD_STORE_INDEX, true);
            localDataRecordStore = RecordStore.openRecordStore(RECORD_STORE_LOCAL_DATA, true);

            return;
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public void closeRecordStores() throws ApplicationException {
        try {
            indexRecordStore.closeRecordStore();
            localDataRecordStore.closeRecordStore();

            return;
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public void addIndexEntry(IndexEntry indexEntry) throws ApplicationException {
        try {
            byte[] data = indexEntry.serialize();

            if (indexRecordStore.getNumRecords() > 0) {
                RecordEnumeration records = indexRecordStore.enumerateRecords(new IndexEntryFilter(indexEntry.getKey(),
                        indexEntry.getType()), null, false);

                if (records.hasNextElement()) {
                    indexRecordStore.setRecord(records.nextRecordId(), data, 0, data.length);
                } else {
                    indexRecordStore.addRecord(data, 0, data.length);
                }
            } else {
                indexRecordStore.addRecord(data, 0, data.length);
            }

            return;
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public IndexEntry[] getIndexEntries(int type, int mode) throws ApplicationException {
        try {
            if (indexRecordStore.getNumRecords() > 0) {
                RecordEnumeration records = indexRecordStore.enumerateRecords(new IndexEntryFilter(null, type, mode), null, false);
                IndexEntry[] indexEntries = new IndexEntry[records.numRecords()];

                for (int i = 0; i < indexEntries.length; i++) {
                    byte[] data = indexRecordStore.getRecord(records.nextRecordId());

                    indexEntries[i] = IndexEntry.deserialize(data);
                }

                return indexEntries;
            } else {
                return new IndexEntry[0];
            }
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public IndexEntry getIndexEntry(String key, int type, int mode) throws ApplicationException {
        try {
            if (indexRecordStore.getNumRecords() > 0) {
                RecordEnumeration records = indexRecordStore.enumerateRecords(new IndexEntryFilter(key, type, mode), null, false);

                if (records.hasNextElement()) {
                    byte[] data = indexRecordStore.getRecord(records.nextRecordId());

                    return IndexEntry.deserialize(data);
                }
            }

            return null;
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public void deleteIndexEntry(String key, int type) throws ApplicationException {
        try {
            if (indexRecordStore.getNumRecords() > 0) {
                RecordEnumeration records = indexRecordStore.enumerateRecords(new IndexEntryFilter(key, type), null, false);

                while (records.hasNextElement()) {
                    indexRecordStore.deleteRecord(records.nextRecordId());
                }
            }

            return;
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    private void delete(RecordStore recordStore, int recordId) throws ApplicationException {
        try {
            recordStore.deleteRecord(recordId);

            return;
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public Preferences loadPreferences(int recordId) throws ApplicationException {
        try {
            byte[] data = localDataRecordStore.getRecord(recordId);

            return Preferences.deserialize(new DataInputStream(new ByteArrayInputStream(data)));
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

    public int storePreferences(Preferences preferences, int recordId) throws ApplicationException {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(stream);

            preferences.serialize(dataStream);
            dataStream.flush();

            if (recordId > 0) {
                localDataRecordStore.setRecord(recordId, stream.toByteArray(), 0, stream.size());
            } else {
                recordId = localDataRecordStore.addRecord(stream.toByteArray(), 0, stream.size());
            }

            return recordId;
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
        catch (RecordStoreException rse) {
            rse.printStackTrace();

            throw new ApplicationException(rse);
        }
    }

}
