/*
 * WMSRequestParameter.java
 *
 * Created on April 11, 2006, 11:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;


/**
 * @author Khanh
 */
public interface SearchFeatureParameter {

    public void initParam(Float[] latLonBoundingBox, String webGISURL);

    public Float[] getBoundingBox();

    public String getWebGISURL();

    public String getKeyWord();

    public int getStart();

}
