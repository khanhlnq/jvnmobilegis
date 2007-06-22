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

package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;


/**
 * @author khanhlnq
 */
public class MapFeature {

    private String id;
    private Float x;
    private Float y;
    private String name;

    /**
    * Creates a new instance of PathStreet
     */
    public MapFeature() {

    }

    /**
    * @return the id
    * @uml.property name="id"
     */
    public String getId() {
        return id;
    }

    /**
    * @param id the id to set
    * @uml.property name="id"
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
    * @return the x
    * @uml.property name="x"
     */
    public Float getX() {
        return x;
    }

    /**
    * @param x the x to set
    * @uml.property name="x"
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
    * @return the y
    * @uml.property name="y"
     */
    public Float getY() {
        return y;
    }

    /**
    * @param y the y to set
    * @uml.property name="y"
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
    * @return the name
    * @uml.property name="name"
     */
    public String getName() {
        return name;
    }

    /**
    * @param name the name to set
    * @uml.property name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

}
