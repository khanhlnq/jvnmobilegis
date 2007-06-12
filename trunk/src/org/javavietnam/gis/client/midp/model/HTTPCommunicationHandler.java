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
 * $Id$
 */
/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.client.midp.model;

import henson.midp.Float;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Image;

import org.javavietnam.gis.shared.midp.ApplicationException;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.ModelException;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;


public class HTTPCommunicationHandler extends RemoteModelRequestHandler {

    public HTTPCommunicationHandler(RemoteModelRequestHandler nextHandler) {
        super(nextHandler);

        return;
    }

    /**
     * Get image from WMS server @
     */
    public Image getMapWMS(WMSRequestParameter requestParam, Vector layerList) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        Image img;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        if (0 == layerList.size()) {
            throw new ApplicationException(ErrorMessageCodes.NO_SELECTED_LAYER);
        }
        LayerInformation layerInfo = (LayerInformation) layerList.elementAt(0);

        String wmsUrl = new String(layerInfo.getServerInformation().getGetMapURL());
        Float f[] = layerInfo.getLatLonBoundingBox();
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
            url.append(((LayerInformation) layerList.elementAt(i)).getField("name"));
            url.append(",");
        }
        // Delete the last comma
        url.deleteCharAt(url.length() - 1);
        url.append("&bbox=" + new Float(requestParam.getBoundingX1()).toString());
        url.append("," + new Float(requestParam.getBoundingY1()).toString());
        url.append("," + new Float(requestParam.getBoundingX2()).toString());
        url.append("," + new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=" + requestParam.getSRS());
        url.append("&width=" + requestParam.getPixelWidth() + "&height=" + requestParam.getPixelHeight());
        url.append("&format=" + requestParam.getImageFormat());
        url.append("&exceptions=" + requestParam.getTextFormat());
        url.append("&version=" + requestParam.getVersion());

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

        }
        catch (IOException ioe) {
            int ch;
            try {
                while ((ch = inputStream.read()) != -1) {
                    System.out.print((char) ch);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return img;
    }

    public String findPathWMS(WMSRequestParameter requestParam) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        if (null == requestParam.getStartPoint() || null == requestParam.getEndPoint()) {
            throw new ApplicationException(ErrorMessageCodes.NO_SELECTED_POINT);
        }

        StringBuffer resultBuf = new StringBuffer();

        String wmsUrl = new String(requestParam.getGetMapURL());
        StringBuffer url = new StringBuffer(wmsUrl);
        url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
        url.append("request=FindPath");
        url.append("&service=wms");
        url.append("&styles="); // 7.2.3.4; MUST be present and
        // may be a null value for default
        // or must one from the list.
        url.append("&layers=");
        url.append(requestParam.getFindPathLayer());
        url.append("&spoint=").append(requestParam.getStartPoint()[0]).append(",").append(requestParam.getStartPoint()[1]);
        url.append("&epoint=").append(requestParam.getEndPoint()[0]).append(",").append(requestParam.getEndPoint()[1]);
        url.append("&bbox=" + new Float(requestParam.getBoundingX1()).toString());
        url.append("," + new Float(requestParam.getBoundingY1()).toString());
        url.append("," + new Float(requestParam.getBoundingX2()).toString());
        url.append("," + new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=" + requestParam.getSRS());
        url.append("&width=" + requestParam.getPixelWidth() + "&height=" + requestParam.getPixelHeight());
        url.append("&format=" + requestParam.getXmlFormat());
        url.append("&exceptions=" + requestParam.getTextFormat());
        url.append("&version=" + requestParam.getVersion());

        wmsUrl = url.toString();
        // wmsUrl =
        // "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=text/xml&version=1.1.1";

        try {
            connection = openGETConnection(wmsUrl);

            // System.out.println("******** Reading stream: ");

            updateProgress();

            inputStream = openConnectionInputStream(connection);

            updateProgress();

            int ch;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
                // System.out.print((char) ch);
            }

        }
        catch (IOException ioe) {
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
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return toUTF8(resultBuf.toString());
    }

    public String searchFeature(SearchFeatureParameter searchParam) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        StringBuffer resultBuf = new StringBuffer();

        String webGISURL = new String(searchParam.getWebGISURL());
        StringBuffer url = new StringBuffer(webGISURL);
        url.append((webGISURL.lastIndexOf('/') != (webGISURL.length() - 1)) ? "/searchfeatures?" : "searchfeatures?");
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

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpConnection.HTTP_NO_CONTENT) {
                return "";
            }

            inputStream = openConnectionInputStream(connection);

            updateProgress();

            int ch;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
            }

        }
        catch (IOException ioe) {
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return toUTF8(resultBuf.toString());
    }

    public String getFeatureInfo(WMSRequestParameter requestParam, Vector layerList, String infoLayer) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        StringBuffer resultBuf = new StringBuffer();

        String wmsUrl = new String(requestParam.getGetMapURL());
        StringBuffer url = new StringBuffer(wmsUrl);
        url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
        url.append("request=GetFeatureInfo");
        url.append("&service=wms");
        url.append("&styles="); // 7.2.3.4; MUST be present and
        // may be a null value for default
        // or must one from the list.
        url.append("&layers=");
        for (int i = 0; i < layerList.size(); i++) {
            url.append(((LayerInformation) layerList.elementAt(i)).getField("name"));
            url.append(",");
        }
        // Delete the last comma
        url.deleteCharAt(url.length() - 1);
        url.append("&bbox=" + new Float(requestParam.getBoundingX1()).toString());
        url.append("," + new Float(requestParam.getBoundingY1()).toString());
        url.append("," + new Float(requestParam.getBoundingX2()).toString());
        url.append("," + new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=" + requestParam.getSRS());
        url.append("&width=" + requestParam.getPixelWidth() + "&height=" + requestParam.getPixelHeight());
        url.append("&format=" + requestParam.getImageFormat());
        url.append("&query_layers=" + infoLayer);
        url.append("&info_format=" + requestParam.getTextFormat());
        url.append("&x=" + requestParam.getX());
        url.append("&y=" + requestParam.getY());
        url.append("&feature_count=1");
        url.append("&exceptions=" + requestParam.getTextFormat());
        url.append("&version=" + requestParam.getVersion());

        wmsUrl = url.toString();
        // wmsUrl =
        // "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=text/xml&version=1.1.1";

        try {
            connection = openGETConnection(wmsUrl);

            // System.out.println("******** Reading stream: ");

            updateProgress();

            inputStream = openConnectionInputStream(connection);

            updateProgress();

            int ch;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
                System.out.print((char) ch);
            }

        }
        catch (IOException ioe) {
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
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return toUTF8(resultBuf.toString());
    }

    public String checkUpdate(String updateURL) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        StringBuffer resultBuf = new StringBuffer();

        try {
            connection = openGETConnection(updateURL);

            updateProgress();

            inputStream = openConnectionInputStream(connection);

            updateProgress();

            int ch;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
                System.out.print((char) ch);
            }

        }
        catch (IOException ioe) {
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return toUTF8(resultBuf.toString());
    }

    public Image viewPathWMS(WMSRequestParameter requestParam) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        Image path;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        String wmsUrl = new String(requestParam.getGetMapURL());
        StringBuffer url = new StringBuffer(wmsUrl);
        url.append(wmsUrl.indexOf("?") < 0 ? "?" : "&");
        url.append("request=FindPath");
        url.append("&service=wms");
        url.append("&styles="); // 7.2.3.4; MUST be present and
        // may be a null value for default
        // or must one from the list.
        url.append("&layers=");
        url.append(requestParam.getFindPathLayer());
        url.append("&spoint=").append(requestParam.getStartPoint()[0]).append(",").append(requestParam.getStartPoint()[1]);
        url.append("&epoint=").append(requestParam.getEndPoint()[0]).append(",").append(requestParam.getEndPoint()[1]);
        url.append("&bbox=" + new Float(requestParam.getBoundingX1()).toString());
        url.append("," + new Float(requestParam.getBoundingY1()).toString());
        url.append("," + new Float(requestParam.getBoundingX2()).toString());
        url.append("," + new Float(requestParam.getBoundingY2()).toString());
        url.append("&SRS=" + requestParam.getSRS());
        url.append("&width=" + requestParam.getPixelWidth() + "&height=" + requestParam.getPixelHeight());
        url.append("&format=" + requestParam.getPNGFormat());
        url.append("&exceptions=" + requestParam.getTextFormat());
        url.append("&version=" + requestParam.getVersion());

        wmsUrl = url.toString();
        // wmsUrl =
        // "http://localhost:8080/geoserver/wms?&request=FindPath&service=wms&styles=&LAYERS=jvn:roads_topo&spoint=105.94586,10.78163&epoint=105.94065,10.79906&bbox=105.93026,10.75845,105.95795,10.80074&SRS=EPSG:4326&width=240&height=367&format=image/png&version=1.1.1";

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

            path = Image.createImage(baos.toByteArray(), 0, baos.size());
        }
        catch (IOException ioe) {
            System.out.println();
            ioe.printStackTrace();
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        finally {
            closeConnection(connection, inputStream);
        }

        return path;
    }

    public String getCapabilitiesWMS(String serviceURL) throws ModelException, ApplicationException {
        HttpConnection connection = null;
        InputStream inputStream = null;

        // Try to free-up memory first
        System.gc();
        updateProgress();

        StringBuffer resultBuf = new StringBuffer();

        try {
            connection = openGETConnection(serviceURL);

            updateProgress();

            inputStream = openConnectionInputStream(connection);

            int ch;
            while ((ch = inputStream.read()) != -1) {
                resultBuf.append((char) ch);
            }

            return resultBuf.toString();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        // Do not close input stream now
        // finally {
        // closeGETConnection(connection, inputStream);
        // }
    }

    private String replace(String source, char oldChar, String dest) {

        String ret = "";
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) != oldChar) ret += source.charAt(i);
            else ret += dest;

        }
        return ret;
    }

    private String toUTF8(String s) {
        s = s.trim();
        try {
            byte[] bytes = s.getBytes("ISO-8859-1");
            String str = new String(bytes, "UTF-8");
            return str;
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("*********** Unsupported Encoding!");
            return s;
        }
    }

    private String encodeURL(String URL) {
        URL = replace(URL, 'à', "%E0");
        URL = replace(URL, 'è', "%E8");
        URL = replace(URL, 'é', "%E9");
        URL = replace(URL, 'ì', "%EC");
        URL = replace(URL, 'ò', "%F2");
        URL = replace(URL, 'ù', "%F9");
        URL = replace(URL, '$', "%24");
        URL = replace(URL, '#', "%23");
        URL = replace(URL, '£', "%A3");
        URL = replace(URL, '@', "%40");
        URL = replace(URL, '\'', "%27");
        URL = replace(URL, ' ', "%20");

        return URL;
    }

    private HttpConnection openGETConnection(String serverURL) throws IOException {
        try {
            System.out.println("********** GET serverURL: " + encodeURL(serverURL));
            HttpConnection connection = (HttpConnection) Connector.open(encodeURL(serverURL));

            connection.setRequestProperty("User-Agent", System.getProperty("microedition.profiles"));
            connection.setRequestMethod(HttpConnection.GET);

            return connection;
        }
        catch (IOException ioe) {
            throw ioe;
        }
    }

    private InputStream openConnectionInputStream(HttpConnection connection) throws IOException, ModelException, ApplicationException {
        try {
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpConnection.HTTP_OK || responseCode == HttpConnection.HTTP_CREATED) {
                InputStream inputStream = connection.openInputStream();

                if (null == inputStream) {
                    throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
                }

                return inputStream;
            }
            throw new ApplicationException(ErrorMessageCodes.ERROR_CANNOT_CONNECT);
        }
        catch (IOException ioe) {
            throw ioe;
        }
    }

    void closeConnection(HttpConnection connection, InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            }
            catch (IOException ioe) {
            } // Ignored
        }

        if (connection != null) {
            try {
                connection.close();
            }
            catch (IOException ioe) {
            } // Ignored
        }

        return;
    }

}
