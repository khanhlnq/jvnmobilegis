/*
 * PathStreet.java
 *
 * Created on May 5, 2006, 5:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp.model;

/**
 * @author  khanhlnq
 */
public class PathStreet {

    private String idProperty;
    private int idValue;
    private String name;

    /** Creates a new instance of PathStreet */
    public PathStreet() {

    }

    /**
     * @return  Returns the idProperty.
     * @uml.property  name="idProperty"
     */
    public String getIdProperty() {
        return idProperty;
    }

    /**
     * @param idProperty  The idProperty to set.
     * @uml.property  name="idProperty"
     */
    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    /**
     * @return  Returns the idValue.
     * @uml.property  name="idValue"
     */
    public int getIdValue() {
        return idValue;
    }

    /**
     * @param idValue  The idValue to set.
     * @uml.property  name="idValue"
     */
    public void setIdValue(int idValue) {
        this.idValue = idValue;
    }

    /**
     * @return  Returns the name.
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  The name to set.
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

}
