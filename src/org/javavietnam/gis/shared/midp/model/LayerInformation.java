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
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;

import java.util.Hashtable;

/**
 * In dieser Klasse werden Informationen zu eiem Layer bereit gehalten
 */
public class LayerInformation {

    private Hashtable data;
    private ServerInformation server;
    private LayerInformation parent;
    /**
     */
    private Float[] latLonBoundingBox;

    /**
     * Erstellt ein neues Objekt mit Refferenz auf den Server, der diesen Layer
     * anbietet.
     * 
     * @param server
     */
    public LayerInformation(ServerInformation server) {
        this.server = server;
        this.data = new Hashtable();
    }

    /**
     * Setzt die Refferenz auf den �bergeordneten Layer <br>
     * Diese kann ben�tigt werden, da die Layer sich ihre Eigenschaften
     * teilweise vererben
     * 
     * @param parent
     */
    public void setParentLayer(LayerInformation parent) {
        this.parent = parent;
    }

    /**
     * Gibt die Refferenz auf den �bergeordneten Layer <br>
     * Diese kann ben�tigt werden, da die Layer sich ihre Eigenschaften
     * teilweise vererben
     * 
     * @return Refferenz auf �bergeordneten Layer, oder null f�r den Root-Layer
     */
    public LayerInformation getParentLayer() {
        return parent;
    }

    /**
     * Setzt die den Bereich in dem dieser Layer verf�gbar ist
     * 
     * @param maxy
     * @param maxx
     */
    public void setLatLonBoundingBox(Float minx, Float miny, Float maxx,
            Float maxy) {
        latLonBoundingBox = new Float[] { minx, miny, maxx, maxy };
    }

    /**
     * Gibt die den Bereich in dem dieser Layer verf�gbar ist
     * 
     * @return floatArray mit { minx, miny, maxx, maxy }
     */
    public Float[] getLatLonBoundingBox() {
        if (latLonBoundingBox == null && parent != null)
            return parent.getLatLonBoundingBox();
        return latLonBoundingBox;
    }

    /**
     * Setzt eine beliebige Eigenschaft des Layers <br>
     * <br>
     * <br>
     * Von dem GetCapabilities-Parser werden im Moment folgende gesetzt: <br>
     * queryable, srs, title, name, abstract <br>
     * <br>
     * Beachte: Die Keys sollten LowerCase sein. <br>
     * <br>
     * Die Eigenschaft srs ist einfach als Text gespeichert, wie im XML-Dokument
     * <br>
     * Eine Funktion getSRSList gibt es noch nicht
     * 
     * @param value
     * @param key
     */
    public void setField(String key, String value) {
        data.put(key, value);
    }

    /**
     * Liefert eine beliebige Eigenschaft des Layers <br>
     * <br>
     * <br>
     * Von dem GetCapabilities-Parser werden im Moment folgende gesetzt: <br>
     * queryable, srs, title, name, abstract <br>
     * <br>
     * Beachte: Die Keys sollten LowerCase sein. <br>
     * <br>
     * Die Eigenschaft srs ist einfach als Text gespeichert, wie im XML-Dokument
     * <br>
     * Eine Funktion getSRSList gibt es noch nicht. Kann aber bei bedarf hinzu
     * kommen.
     * 
     * @param key
     */
    public String getField(String key) {
        return (String) data.get(key);
    }

    /**
     * Liefert eine Refferenz auf den Server, der diesen Layer anbietet.
     */
    public ServerInformation getServerInformation() {
        return server;
    }

    /**
     * Liefert den Titel des Layers
     */
    public String toString() {
        if (getField("title") != null)
            return getField("title");
        else
            return super.toString();
    }

}
