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

import java.util.Vector;

/**
 * In dieser Klasse werden Informationen �ber eiem Server bereit gehalten
 */
public class ServerInformation extends Vector {

	private String name;
	private Vector getMapFormats = new Vector();
	private String getMapURL;

	/**
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setzt die URL des WMS, an die eine GetMap-Anfrage gestellt werden kann
	 * 
	 * @param url
	 * @uml.property name="getMapURL"
	 */
	public void setGetMapURL(String url) {
		this.getMapURL = url;
	}

	/**
	 * Liefert die URL des WMS, an die eine GetMap-Anfrage gestellt werden kann
	 * 
	 * @return
	 * @uml.property name="getMapURL"
	 */
	public String getGetMapURL() {
		return this.getMapURL;
	}

	public void addGetMapFormat(String format) {
		this.getMapFormats.addElement(format);
	}

	/**
	 * Liefert einen Vector von String Objekten, mit Formaten, in denen der WMS
	 * eine Map liefern kann
	 * 
	 * @return
	 * @uml.property name="getMapFormats"
	 */
	public Vector getGetMapFormats() {
		return this.getMapFormats;
	}

	/**
	 * Gibt zur�ck, ob ein bestimmtes Format interst�tzt wird
	 * 
	 * @param format
	 * @return
	 */
	public boolean supportsGetMapFormat(String format) {
		return this.getMapFormats.contains(format);
	}

	/**
	 * Liefert den Namen des Servers
	 * 
	 * @return
	 * @uml.property name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Liefert den Namen des Servers
	 */
	public String toString() {
		return name;
	}

}
