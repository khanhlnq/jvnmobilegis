/**
 * S�rgio Est�v�o
 * MIDP Adventures
 * www.sergioestevao.com/midp
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
 * khanh.lnq AT gmail.com
 *
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 17 July 2007
 */
package org.javavietnam.gis.client.midp.util;

public class Location {

    // NMEA Elements
    public String utc;
    public String latitude;
    public String northHemi;
    public String longitude;
    public String eastHemi;
    String altitude;
    public int quality;
    public int nSat;
    String horDilution;
    String altitudeUnit;
    String geoidalHeight;
    String geoidalHeightUnit;
    String diffCorrection;
    String diffStationId;

    /**
     * Method that parses a NMEA string and returns Location. For more info
     * check this page: http://www.gpsinformation.org/dale/nmea.htm#GGA
     * 
     * @param value
     *            - string that represent NMEA GGA string
     */
    public void parseGPGGA(String value) {
        // Helper class to parse strings
        StringTokenizer tok = new StringTokenizer(value, ",");

        utc = tok.nextToken();
        latitude = tok.nextToken();
        northHemi = tok.nextToken();
        longitude = tok.nextToken();
        eastHemi = tok.nextToken();
        quality = Integer.parseInt(tok.nextToken());
        nSat = Integer.parseInt(tok.nextToken());
        horDilution = tok.nextToken();
        altitude = tok.nextToken();
        altitudeUnit = tok.nextToken();
        geoidalHeight = tok.nextToken();
        geoidalHeightUnit = tok.nextToken();
        diffCorrection = tok.nextToken();
        diffStationId = tok.nextToken();
    }
}
