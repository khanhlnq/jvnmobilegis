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
public interface WMSRequestParameter {

    // Added by Khanh Le
    public void initParam(Float[] latLonBoundingBox, String getMapURL, String srs);

    public int getPixelWidth();

    public int getPixelHeight();

    /** X-Koordinate der Linken, oberen Ecke */
    public Float getBoundingX1();

    /** Y-Koordinate der Linken, oberen Ecke */
    public Float getBoundingY1();

    public Float getBoundingX2();

    public Float getBoundingY2();

    // Get X, Y in pixel for getFeatureInfo
    public int getX();

    public int getY();

    public String getImageFormat();

    public String getXmlFormat();

    public String getTextFormat();

    public String getPNGFormat();

    public String getSRS();

    public String getVersion();

    public Float getCurrentScale();

    public Float[] getStartPoint();

    public Float[] getEndPoint();

    public String getFindPathLayer();

    public String getGetMapURL();

}
