/*
 * Copyright 2001, 2002, 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Except for any files in PNG format (which are marked with the filename
 * extension ".png"), GIF format (which are marked with the filename
 * extension ".gif"), or JPEG format (which are marked with the filename
 * extension ".jpg"), redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 * - Redistribution of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * You may compile, use, perform and display the following files with
 * original Java Smart Ticket Sample Application code obtained from Sun
 * Microsystems, Inc. only:
 * - files in PNG format and having the ".png" extension
 * - files in GIF format and having the ".gif" extension
 * - files in JPEG format and having the ".jpg" extension
 * You may not modify or redistribute .png, .gif, or .jpg files in any
 * form, in whole or in part, by any means without prior written
 * authorization from Sun Microsystems, Inc. and its licensors, if any.
 * Neither the name of Sun Microsystems, Inc., the 'Java Smart Ticket
 * Sample Application', 'Java', 'Java'-based names, or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or maintenance
 * of any nuclear facility.
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
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.model;

import henson.midp.Float;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Image;
import javax.microedition.pki.CertificateException;

import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.ModelException;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;

import com.tinyline.util.GZIPInputStream;

/**
 * @author khanhlnq
 */
public class HTTPCommunicationHandler extends RemoteModelRequestHandler {

    // store the last valid credentials

    /**
     * @uml.property name="wwwAuthenticate"
     */
    private String wwwAuthenticate = null;
    private String credentials = null;

    public HTTPCommunicationHandler(RemoteModelRequestHandler nextHandler) {
        super(nextHandler);
    }

    /**
     * Get image from WMS server @
     */
    public Image getMapWMS(WMSRequestParameter requestParam, Vector layerList)
            throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        Image img;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        if (0 == layerList.size()) {
            throw new ApplicationException(MessageCodes.NO_SELECTED_LAYER);
        }
        LayerInformation layerInfo = (LayerInformation) layerList.elementAt(0);

        String wmsUrl = layerInfo.getServerInformation().getGetMapURL();
        // TODO Khanh: Will find a better way later.
        // requestParam.initParam(f, wmsUrl);
        StringBuffer url = new StringBuffer(wmsUrl);
        url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
        url.append("request=GetMap");
        url.append("&service=wms");
        url.append("&styles="); // 7.2.3.4; MUST be present and
        // may be a null value for default
        // or must one from the list.
        url.append("&layers=");
        for (int i = 0; i < layerList.size(); i++) {
            url.append(((LayerInformation) layerList.elementAt(i))
                    .getField("name"));
            url.append(",");
        }
        // Delete the last comma
        url.deleteCharAt(url.length() - 1);
        url.append("&bbox=").append(
                new Float(requestParam.getBoundingX1()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingY1()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingX2()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=").append(requestParam.getSRS());
        url.append("&width=").append(requestParam.getPixelWidth()).append(
                "&height=").append(requestParam.getPixelHeight());
        url.append("&format=").append(requestParam.getImageFormat());
        url.append("&exceptions=").append(requestParam.getTextFormat());
        url.append("&version=").append(requestParam.getVersion());

        wmsUrl = url.toString();

        try {
            connection = openGETConnection(wmsUrl);

            updateProgress();

            inputStream = openConnectionInputStream(connection);

            updateProgress();

            // Check for content type
            String contentType = connection.getHeaderField("content-type");
            if (!contentType.equals(requestParam.getImageFormat())) {
                StringBuffer msgBuf = new StringBuffer();
                int ch;
                while ((ch = inputStream.read()) != -1) {
                    msgBuf.append((char) ch);
                }
                throw new ApplicationException(msgBuf.toString());
            }

            // Read input stream into byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                baos.write(ch);
            }

            img = Image.createImage(baos.toByteArray(), 0, baos.size());

            updateProgress();
        } catch (IOException ioe) {
            int ch;
            try {
                while ((ch = inputStream.read()) != -1) {
                    System.out.print((char) ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(
                    MessageCodes.ERROR_CANNOT_CONNECT, ioe.getMessage());
        } finally {
            closeConnection(connection, inputStream);
        }

        return img;
    }

    /*
     * public String findPathWMS(WMSRequestParameter requestParam) throws
     * ModelException, ApplicationException { HttpConnection connection = null;
     * InputStream inputStream = null; if (null == requestParam.getStartPoint() ||
     * null == requestParam.getEndPoint()) { throw new
     * ApplicationException(ErrorMessageCodes.NO_SELECTED_POINT); } StringBuffer
     * resultBuf = new StringBuffer(); String wmsUrl =
     * requestParam.getGetMapURL(); StringBuffer url = new StringBuffer(wmsUrl);
     * url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
     * url.append("request=FindPath"); url.append("&service=wms");
     * url.append("&styles="); // 7.2.3.4; MUST be present and // may be a null
     * value for default // or must one from the list. url.append("&layers=");
     * url.append(requestParam.getFindPathLayer());
     * url.append("&spoint=").append(requestParam.getStartPoint()[0]).append(",").append(requestParam.getStartPoint()[1]);
     * url.append("&epoint=").append(requestParam.getEndPoint()[0]).append(",").append(requestParam.getEndPoint()[1]);
     * url.append("&bbox=").append(new
     * Float(requestParam.getBoundingX1()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingY1()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingX2()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingY2()).toString());
     * url.append("&SRS=").append(requestParam.getSRS());
     * url.append("&width=").append(requestParam.getPixelWidth()).append("&height=").append(requestParam.getPixelHeight());
     * url.append("&format=").append(requestParam.getXmlFormat());
     * url.append("&exceptions=").append(requestParam.getTextFormat());
     * url.append("&version=").append(requestParam.getVersion()); wmsUrl =
     * url.toString(); // wmsUrl = //
     * "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=text/xml&version=1.1.1";
     * try { connection = openGETConnection(wmsUrl); //
     * System.out.println("******** Reading stream: "); updateProgress();
     * inputStream = openConnectionInputStream(connection); updateProgress();
     * int ch; while ((ch = inputStream.read()) != -1) { resultBuf.append((char)
     * ch); // System.out.print((char) ch); } } catch (IOException ioe) { // int
     * ch; // try { // while ((ch = inputStream.read()) != -1) { //
     * System.out.print((char)ch); // } // } catch (IOException e) { //
     * e.printStackTrace(); // } System.out.println(); ioe.printStackTrace();
     * throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT); }
     * finally { closeConnection(connection, inputStream); } return
     * toUTF8(resultBuf.toString()); }
     */

    public String searchFeature(SearchFeatureParameter searchParam)
            throws ModelException, ApplicationException {
        HttpConnection connection = null;

        String results = new String("");

        String webGISURL = searchParam.getWebGISURL();
        StringBuffer url = new StringBuffer(webGISURL);
        url
                .append((webGISURL.lastIndexOf('/') != (webGISURL.length() - 1)) ? "/searchfeatures?"
                        : "searchfeatures?");
        url.append("minx=").append(searchParam.getBoundingBox()[0].toString());
        url.append("&miny=").append(searchParam.getBoundingBox()[1].toString());
        url.append("&maxx=").append(searchParam.getBoundingBox()[2].toString());
        url.append("&maxy=").append(searchParam.getBoundingBox()[3].toString());
        url.append("&word=").append(searchParam.getKeyWord());
        url.append("&start=").append(searchParam.getStart());
        url.append("&SRS=EPSG:4326");

        webGISURL = url.toString();

        // For testing only
        // webGISURL =
        // "http://khanhlnq:8080/jvnwebgis/searchfeatures?minx=617420&miny=1144670&maxx=752520&maxy=1238000&word=n&start=0";
        try {
            connection = openGETConnection(webGISURL);

            updateProgress();

            results = readStringContent(connection);
        } catch (IOException ioe) {
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(
                    MessageCodes.ERROR_CANNOT_CONNECT, ioe.getMessage());
        }

        return results;
    }

    public String getFeatureInfo(WMSRequestParameter requestParam,
            Vector layerList, String infoLayer) throws ModelException,
            ApplicationException {
        HttpConnection connection = null;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        String results = new String("");

        String wmsUrl = requestParam.getGetMapURL();
        StringBuffer url = new StringBuffer(wmsUrl);
        url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
        url.append("request=GetFeatureInfo");
        url.append("&service=wms");
        url.append("&styles="); // 7.2.3.4; MUST be present and
        // may be a null value for default
        // or must one from the list.
        url.append("&layers=");
        for (int i = 0; i < layerList.size(); i++) {
            url.append(((LayerInformation) layerList.elementAt(i))
                    .getField("name"));
            url.append(",");
        }
        // Delete the last comma
        url.deleteCharAt(url.length() - 1);
        url.append("&bbox=").append(
                new Float(requestParam.getBoundingX1()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingY1()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingX2()).toString());
        url.append(",").append(
                new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=").append(requestParam.getSRS());
        url.append("&width=").append(requestParam.getPixelWidth()).append(
                "&height=").append(requestParam.getPixelHeight());
        url.append("&format=").append(requestParam.getImageFormat());
        url.append("&query_layers=").append(infoLayer);
        url.append("&info_format=").append(requestParam.getTextFormat());
        url.append("&x=").append(requestParam.getX());
        url.append("&y=").append(requestParam.getY());
        url.append("&feature_count=1");
        url.append("&exceptions=").append(requestParam.getTextFormat());
        url.append("&version=").append(requestParam.getVersion());

        wmsUrl = url.toString();
        // wmsUrl =
        // "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=text/xml&version=1.1.1";
        try {
            connection = openGETConnection(wmsUrl);
            results = readStringContent(connection);
        } catch (IOException ioe) {
            // int ch;
            // try {
            // while ((ch = inputStream.read()) != -1) {
            // System.out.print((char)ch);
            // }
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(
                    MessageCodes.ERROR_CANNOT_CONNECT, ioe.getMessage());
        }

        return results;
    }

    public String checkUpdate(String updateURL) throws ModelException,
            ApplicationException {
        HttpConnection connection = null;

        String results = new String("");

        try {
            connection = openGETConnection(updateURL);

            results = readStringContent(connection);
        } catch (IOException ioe) {
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(ioe);
        }

        return results;
    }

    /*
     * public Image viewPathWMS(WMSRequestParameter requestParam) throws
     * ModelException, ApplicationException { HttpConnection connection = null;
     * InputStream inputStream = null; Image path; // Try to free-up memory
     * first System.gc(); updateProgress(); String wmsUrl =
     * requestParam.getGetMapURL(); StringBuffer url = new StringBuffer(wmsUrl);
     * url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
     * url.append("request=FindPath"); url.append("&service=wms");
     * url.append("&styles="); // 7.2.3.4; MUST be present and // may be a null
     * value for default // or must one from the list. url.append("&layers=");
     * url.append(requestParam.getFindPathLayer());
     * url.append("&spoint=").append(requestParam.getStartPoint()[0]).append(",").append(requestParam.getStartPoint()[1]);
     * url.append("&epoint=").append(requestParam.getEndPoint()[0]).append(",").append(requestParam.getEndPoint()[1]);
     * url.append("&bbox=").append(new
     * Float(requestParam.getBoundingX1()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingY1()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingX2()).toString());
     * url.append(",").append(new
     * Float(requestParam.getBoundingY2()).toString());
     * url.append("&SRS=").append(requestParam.getSRS());
     * url.append("&width=").append(requestParam.getPixelWidth()).append("&height=").append(requestParam.getPixelHeight());
     * url.append("&format=").append(requestParam.getPNGFormat());
     * url.append("&exceptions=").append(requestParam.getTextFormat());
     * url.append("&version=").append(requestParam.getVersion()); wmsUrl =
     * url.toString(); // wmsUrl = //
     * "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=image/png&version=1.1.1";
     * try { connection = openGETConnection(wmsUrl); updateProgress();
     * inputStream = openConnectionInputStream(connection); updateProgress(); //
     * Check for content type String contentType =
     * connection.getHeaderField("content-type"); if
     * (!contentType.equals(requestParam.getImageFormat())) { StringBuffer
     * msgBuf = new StringBuffer(); int ch; while ((ch = inputStream.read()) !=
     * -1) { msgBuf.append((char) ch); } throw new
     * ApplicationException(msgBuf.toString()); } // Read input stream into
     * byte[] ByteArrayOutputStream baos = new ByteArrayOutputStream(); int ch;
     * while ((ch = inputStream.read()) != -1) { baos.write(ch); } path =
     * Image.createImage(baos.toByteArray(), 0, baos.size()); } catch
     * (IOException ioe) { System.out.println(); ioe.printStackTrace(); throw
     * new ApplicationException(ioe); } finally { closeConnection(connection,
     * inputStream); } return path; }
     */

    public String getCapabilitiesWMS(String serviceURL) throws ModelException,
            ApplicationException {
        HttpConnection connection;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        String results = new String("");

        try {
            connection = openGETConnection(serviceURL);

            results = readStringContent(connection);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ApplicationException(ioe);
        }
        // Do not close input stream now
        // finally {
        // closeGETConnection(connection, inputStream);
        // }
        return results;
    }

    private String replace(String source, char oldChar, String dest) {

        String ret = "";
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) != oldChar) {
                ret += source.charAt(i);
            } else {
                ret += dest;
            }
        }
        return ret;
    }

    private String toUTF8(String s) {
        s = s.trim();
        try {
            byte[] bytes = s.getBytes("ISO-8859-1");
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("*********** Unsupported Encoding!");
            return s;
        }
    }

    private String encodeURL(String URL) {
        URL = replace(URL, '\u00e0', "%E0");
        URL = replace(URL, '\u00e8', "%E8");
        URL = replace(URL, '\u00e9', "%E9");
        URL = replace(URL, '\u00ec', "%EC");
        URL = replace(URL, '\u00f2', "%F2");
        URL = replace(URL, '\u00f9', "%F9");
        URL = replace(URL, '\u0024', "%24");
        URL = replace(URL, '\u0023', "%23");
        URL = replace(URL, '\u00a3', "%A3");
        URL = replace(URL, '\u0040', "%40");
        URL = replace(URL, '\'', "%27");
        URL = replace(URL, '\u0020', "%20");

        return URL;
    }

    private HttpConnection openGETConnection(String serverURL)
            throws IOException, CertificateException {
        try {
            HttpConnection connection;
            serverURL = encodeURL(serverURL.trim());

            System.out.println("********** GET serverURL: " + serverURL);

            connection = (HttpConnection) Connector.open(serverURL);

            connection.setRequestProperty("User-Agent", System
                    .getProperty("microedition.profiles"));
            connection.setRequestProperty("Accept-Encoding", "gzip");

            if (0 == serverURL.indexOf("https://")) {
                if (null != credentials && !"".equals(credentials)) {
                    connection.setRequestProperty("Authorization", "Basic "
                            + credentials);
                }
                // connection.setRequestMethod(HttpConnection.POST);
                connection.setRequestMethod(HttpConnection.GET);

                // SecurityInfo si = ((HttpsConnection) connection)
                // .getSecurityInfo();
                // Certificate c = si.getServerCertificate();
                // String subject = c.getSubject();
                // String cipherSuite = si.getCipherSuite();
                //
                // System.out.println("Server certificate subject: \n" +
                // subject);
                //
                // System.out.println("Server Cipher Suite: \n" + cipherSuite);
            } else {
                connection.setRequestMethod(HttpConnection.GET);
            }

            return connection;
        } catch (CertificateException certe) {
            throw certe;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private InputStream openConnectionInputStream(HttpConnection connection)
            throws IOException, CertificateException, ApplicationException {
        InputStream inputStream = null;
        int responseCode = connection.getResponseCode();
        try {
            String reponseMessage = connection.getResponseMessage();

            if (responseCode == HttpConnection.HTTP_OK
                    || responseCode == HttpConnection.HTTP_CREATED) {
                inputStream = connection.openInputStream();

                if (null == inputStream) {
                    throw new ApplicationException(
                            MessageCodes.ERROR_CANNOT_CONNECT);
                }                                              
                
                if ((connection.getEncoding() != null) && (connection.getHeaderField("Content-Encoding").indexOf("gzip") != -1)) {
                	System.out.println(":::: server support gzip");
                	inputStream = new GZIPInputStream(inputStream);                	
                }

                return inputStream;
            } else if (responseCode == HttpConnection.HTTP_UNAUTHORIZED) {
                // missing credentials when server requires
                // them, or credentials sent but invalid
                wwwAuthenticate = connection.getHeaderField("WWW-Authenticate");
                // System.out.println("*********** WWW-Authenticate: "
                // + wwwAuthenticate);
                // closeConnection(connection, inputStream);
                throw new ApplicationException(
                        MessageCodes.ERROR_UNAUTHORIZED);
                // open again, this time with credentials
            } else {
                // System.out.println(" ******* Response Code = " +
                // responseCode);
                // throw new
                // ApplicationException(ioe);
                throw new ApplicationException("HTTP_"
                        + String.valueOf(responseCode) + ": " + reponseMessage);
            }
        } catch (CertificateException certe) {
            throw new ApplicationException(MessageCodes.ERROR_CERTIFICATE);
        } catch (IOException ioe) {
            throw ioe;
        } // finally {
        // closeConnection(connection, inputStream);
        // }
    }

    private String readStringContent(HttpConnection connection)
            throws ApplicationException {
        InputStream inputStream = null;
        boolean isUTF8 = false;
        try {
            if (connection.getResponseCode() == HttpConnection.HTTP_NO_CONTENT) {
                return "";
            }

            if ("UTF-8".equals(connection.getEncoding())
                    || "UTF-8".equals(connection
                            .getHeaderField("Content-Encoding"))) {
                isUTF8 = true;
            }

            updateProgress();
            // Try to free-up memory first
            System.gc();

            StringBuffer resultBuf = new StringBuffer();

            inputStream = openConnectionInputStream(connection);
            updateProgress();
            // long length = connection.getLength();

            int ch;
            // int i = 0;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
                // i++;
                // if (i > (length / 5)) {
                // updateProgress();
                // i = 0;
                // }
            }

            if (isUTF8) {
                return toUTF8(resultBuf.toString());
            } else {
                return resultBuf.toString().trim();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ApplicationException(ioe);
        } finally {
            closeConnection(connection, inputStream);
        }
    }

    private void closeConnection(HttpConnection connection,
            InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ioe) {
            } // Ignored
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ioe) {
            } // Ignored
        }
    }

    /**
     * @return
     * @uml.property name="wwwAuthenticate"
     */
    public String getWwwAuthenticate() {
        return wwwAuthenticate;
    }

    /**
     * @param credentials
     * @uml.property name="credentials"
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

}
