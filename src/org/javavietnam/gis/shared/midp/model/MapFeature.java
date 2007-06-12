/*
 * PathStreet.java
 *
 * Created on May 5, 2006, 5:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;


/**
 * @author  khanhlnq
 */
public class MapFeature {

    private String id;
    private Float x;
    private Float y;
    private String name;

    /** Creates a new instance of PathStreet */
    public MapFeature() {

    }

    /**
     * @return  the id
     * @uml.property  name="id"
     */
    public String getId() {
        return id;
    }

    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return  the x
     * @uml.property  name="x"
     */
    public Float getX() {
        return x;
    }

    /**
     * @param x  the x to set
     * @uml.property  name="x"
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * @return  the y
     * @uml.property  name="y"
     */
    public Float getY() {
        return y;
    }

    /**
     * @param y  the y to set
     * @uml.property  name="y"
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * @return  the name
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  the name to set
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

}
