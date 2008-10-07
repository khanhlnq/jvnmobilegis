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

package org.javavietnam.gis.shared.midp;

import java.util.Hashtable;

/**
 * Simple cache class to cache raw data
 */
public class SimpleCache {
    Hashtable cacheTable;

    public SimpleCache() {

    }

    Hashtable getCacheTable() {
        if (cacheTable == null) {
            cacheTable = new Hashtable();
        }
        return cacheTable;
    }

    public boolean containsKey(String key) {
        return getCacheTable().containsKey(key);
    }

    /**
     * Put an object in to cache 1: Success -1: Fail
     */
    public int put(String key, Object obj) {
        try {
            getCacheTable().put(key, obj);
            return 1;
        }
        // catch(OutOfMemoryError oe) {
        catch (Throwable e) {
            cacheTable = null;
            return -1;
        }
    }

    public Object get(String key) {
        return getCacheTable().get(key);
    }

    public void remove(String key) {
        getCacheTable().remove(key);
    }

}