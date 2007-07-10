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

import java.io.*;


/**
 */
public class IndexEntry {

    private static final int TYPE_ANY = -1;
    public static final int TYPE_PREFERENCES = 5;
    public static final int MODE_ANY = -1;
    public static final int MODE_PERSISTING = 0;
    private static final int MODE_CACHING = 1;
    private int type;
    private String key;
    private long expirationDate;
    private boolean marked;
    private int recordId;
    private int mode = MODE_CACHING;

    public IndexEntry(int recordId, int type, String key) {
        this(recordId, type, key, MODE_CACHING);

    }

    public IndexEntry(int recordId, int type, String key, int mode) {
        this.recordId = recordId;
        this.type = type;
        this.key = key;
        this.mode = mode;

    }

    private IndexEntry() {
    }

    /**
     * @return the type
     * @uml.property name="type"
     */
    public int getType() {
        return type;
    }

    /**
     * @return the key
     * @uml.property name="key"
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the expirationDate
     * @uml.property name="expirationDate"
     */
    public long getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     * @uml.property name="expirationDate"
     */
    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return the marked
     * @uml.property name="marked"
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * @param marked the marked to set
     * @uml.property name="marked"
     */
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    /**
     * @return the recordId
     * @uml.property name="recordId"
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * @return the mode
     * @uml.property name="mode"
     */
    public int getMode() {
        return mode;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        dataStream.writeInt(type);
        dataStream.writeUTF(key);
        dataStream.writeInt(mode);
        dataStream.writeLong(expirationDate);
        dataStream.writeBoolean(marked);
        dataStream.writeInt(recordId);

        return byteStream.toByteArray();
    }

    public static IndexEntry deserialize(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);
        IndexEntry indexEntry = new IndexEntry();

        indexEntry.type = dataStream.readInt();
        indexEntry.key = dataStream.readUTF();
        indexEntry.mode = dataStream.readInt();
        indexEntry.expirationDate = dataStream.readLong();
        indexEntry.marked = dataStream.readBoolean();
        indexEntry.recordId = dataStream.readInt();

        return indexEntry;
    }

    // Optimization to avoid deserializing and instantiating an IndexEntry when matching RMS records
    public static boolean matches(byte[] data, String key, int type, int mode) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);

        return (type == dataStream.readInt() || type == TYPE_ANY)
                && (dataStream.readUTF().equals(key) || key == null)
                && (mode == dataStream.readInt() || mode == MODE_ANY);
    }

}
