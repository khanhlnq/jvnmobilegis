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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class GpsBt implements Runnable {

    public int mode = 0;
    public boolean isActive = false;
    public boolean isConnected = false;
    // current bluetooth device
    public String btUrl = "";
    public String btName = "";
    public String error = "No Error";

    // current connection
    StreamConnection conn = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    StringBuffer sb;
    String currentInfo;

    // state
    public final static byte STATE_SEARCH_SENTENCE_BEGIN = 0;
    public final static byte STATE_READ_DATA_TYPE = 1;
    public final static byte STATE_READ_SENTENCE = 2;

    static GpsBt gpsReader = null;

    public static GpsBt instance() {
        if (gpsReader == null) {
            gpsReader = new GpsBt();
        }

        return gpsReader;
    }

    /** Creates a new instance of GPSReader */
    public GpsBt() {
        currentInfo = null;
        isConnected = false;
        isActive = false;
        sb = new StringBuffer(100);
    }

    /**
     * Sets the bluetooth gps device to connect and read information
     * 
     * @param btUrl
     *            - bluetooth url address
     * @param btName
     *            - bluetooth friendly name
     */
    public void setDevice(String btUrl, String btName) {
        this.btUrl = btUrl;
        this.btName = btName;
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Start connection to device, and main thread
     */
    public void start() {
        if (isActive) {
            stop();
        }
        connect();
        if (isConnected) {
            isActive = true;
            Thread t = new Thread(this);
            t.start();
        }
    }

    /**
     * Stop connection to the device and ends thread
     */
    public void stop() {
        if (isActive) {
            isActive = false;
            try {
                while (isConnected) {
                    close();
                    Thread.sleep(100);
                }
            } catch (Throwable t) {
                error = "stop:" + t.toString();
                close();
            }
        }
    }

    public void run() {
        isActive = true;
        while (isActive) {
            try {
                // check if connection is still open
                if (!isConnected && isActive) {
                    // connect to gps device
                    connect();
                } else {
                    // read NMEA Strings
                    readNMEASentences();
                }
            } catch (Throwable t) {
                error = "run:" + t.toString();
                close();
            }
        }
        close();
        isActive = false;
    }

    /**
     * Read characteres from device until finds a GPGAA NMEA sentence
     */
    public void readNMEASentences() {
        try {
            if (!isConnected) {
                return;
            }
            int size = in.available();
            if (size <= 0) {
                return;
            }
            for (int j = 0; j < size; j++) {
                int i = in.read();
                if (i != -1) {
                    char l = (char) i;
                    switch (mode) {
                    case (STATE_SEARCH_SENTENCE_BEGIN): {
                        // search for the sentence begin
                        if (l == '$') {
                            // found begin of sentence
                            mode = 1;
                            sb.setLength(0);
                        }
                    }
                        break;
                    case (STATE_READ_DATA_TYPE): {
                        // check what kind of sentence we have
                        sb.append(l);
                        if (sb.length() == 6) {
                            if (sb.toString().startsWith("GPGGA")) {
                                mode = STATE_READ_SENTENCE;
                                sb.setLength(0);
                            } else {
                                mode = STATE_SEARCH_SENTENCE_BEGIN;
                                sb.setLength(0);
                            }
                        }
                    }
                        break;
                    case (STATE_READ_SENTENCE): {
                        // read data from sentence
                        sb.append(l);
                        if ((l == 13) || (l == 10) || (l == '$')) {
                            mode = STATE_SEARCH_SENTENCE_BEGIN;
                            synchronized (this) {
                                currentInfo = new String(sb.toString());
                            }
                            Thread.sleep(1000);
                        }
                    }
                        break;
                    }

                } else {
                    close();
                }
            }

        } catch (Exception e) {
            error = "fetch" + e.toString();
            close();
        }
    }

    /**
     * Parses data from current sentence and return a Location.
     * 
     * @return
     */
    public Location getLocation() {
        Location location = new Location();
        try {
            if (isConnected && isActive && currentInfo != null) {
                location.parseGPGGA(currentInfo);
            }
        } catch (Throwable t) {
            error = "get:" + t.toString();
            close();
        }
        return location;
    }

    /**
     * Connect to the bluetooth device
     */
    public void connect() {
        if (btUrl == null || (btUrl.trim().compareTo("") == 0)) {
            isConnected = false;
            return;
        }
        try {
            conn = (StreamConnection) Connector.open(btUrl,
                    Connector.READ_WRITE);
            in = new DataInputStream(conn.openInputStream());
            isConnected = true;
            mode = 0;
        } catch (IOException e) {
            close();
        }
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.close();
            }
            in = null;
            out = null;
            conn = null;
        } catch (Throwable t) {
            error = "close" + t.toString();
        } finally {
            in = null;
            out = null;
            conn = null;
        }
        isConnected = false;
    }

}
