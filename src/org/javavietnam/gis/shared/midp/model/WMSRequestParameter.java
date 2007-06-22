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
 * @author Khanh
 */
public interface WMSRequestParameter {

    // Added by Khanh Le
    public void initParam(Float[] latLonBoundingBox, String getMapURL, String srs);

    public int getPixelWidth();

    public int getPixelHeight();

    /**
    * X-Koordinate der Linken, oberen Ecke
     */
    public Float getBoundingX1();

    /**
    * Y-Koordinate der Linken, oberen Ecke
     */
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
