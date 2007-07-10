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

package org.javavietnam.gis.shared.midp;

import henson.midp.Float;
import org.javavietnam.gis.shared.midp.model.InnerTreeNode;
import org.javavietnam.gis.shared.midp.model.LayerInformation;
import org.javavietnam.gis.shared.midp.model.ServerInformation;
import org.javavietnam.gis.shared.midp.model.TreeNode;
import org.xml.sax.AttributeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import uk.co.wilson.xml.MinML;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;


/**
 * Klasse zu Parsen eines XML-Streams von einer GetCapabilities Anfrage.
 */
public class CapabilitiesParser extends MinML {

    private InputStream input;

    private ServerInformation server;
    private Vector currPath;
    private boolean layerIsParent;
    private boolean getMapIsParent;
    private boolean serviceIsParent;
    private boolean mapIsParent;

    private StringBuffer thisText = new StringBuffer();

    public CapabilitiesParser(InputStream input) {
        this.input = input;
    }

    public CapabilitiesParser(String result) {
        try {
            this.input = new ByteArrayInputStream(result.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.input = new ByteArrayInputStream(result.getBytes());
        }
    }

    /**
     * Gibt eine Baumstruktur zur�ck, die die Layer repr�sentiert
     *
     * @return
     */
    public Vector constructDataTree() {
        try {
            parse(new InputSource(input));
        }
        catch (Exception e) {
            System.out.println("Exeption aufgetreten: " + e);
            e.printStackTrace();
            return null;
        }

        return (Vector) currPath.elementAt(0);
        // return (Vector)currPath;
    }

    public void startDocument() {
        server = new ServerInformation();

        currPath = new Vector();
        currPath.addElement(server);
    }

    public void endDocument() {
        if (((Vector) currPath.elementAt(0)).size() > 0) eliminateInnerNodes((Vector) currPath.elementAt(0));
    }

    private void eliminateInnerNodes(Vector node) {
        for (int i = 0; i < node.size(); i++) {
            InnerTreeNode currChild = (InnerTreeNode) node.elementAt(i);
            if (currChild.size() > 0) eliminateInnerNodes(currChild);
            else node.setElementAt(new TreeNode(currChild.getLayerInformation()), i);
        }

    }

    public void startElement(String name, AttributeList attributes) {

        if (name.toLowerCase().equals("layer")) {
            LayerInformation layerInfo = new LayerInformation(server);
            if (attributes.getValue("queryable") == null) layerInfo.setField("queryable", "0");
            else layerInfo.setField("queryable", attributes.getValue("queryable"));

            if (currPath.size() > 1)
                layerInfo.setParentLayer(((InnerTreeNode) currPath.lastElement()).getLayerInformation());
            currPath.addElement(new InnerTreeNode(layerInfo));
            layerIsParent = true;
        } else if (name.toLowerCase().equals("srs")
                || name.toLowerCase().equals("title")
                || name.toLowerCase().equals("name")
                || name.toLowerCase().equals("abstract")) {
            // nothing
        } else if (layerIsParent && name.toLowerCase().equals("latlonboundingbox")) {

            ((InnerTreeNode) currPath.lastElement()).getLayerInformation()
                    .setLatLonBoundingBox(Float.parse(attributes.getValue("minx"), 10),
                            Float.parse(attributes.getValue("miny"), 10),
                            Float.parse(attributes.getValue("maxx"), 10),
                            Float.parse(attributes.getValue("maxy"), 10));
        } else {

            if (name.toLowerCase().equals("style")) layerIsParent = false;

            if (name.toLowerCase().equals("getmap")) {
                getMapIsParent = true;
            } else if (name.toLowerCase().equals("service")) {
                serviceIsParent = true;
            } else if (name.toLowerCase().equals("map")) {
                mapIsParent = true;
            }

        }

        if (getMapIsParent && name.toLowerCase().equals("onlineresource"))
            server.setGetMapURL(attributes.getValue("xlink:href"));

        if (mapIsParent && name.toLowerCase().equals("get")) server.setGetMapURL(attributes.getValue("onlineResource"));

        if (mapIsParent && name.toLowerCase().equals("jpeg")) server.addGetMapFormat("image/jpeg");
        else if (mapIsParent && name.toLowerCase().equals("png")) server.addGetMapFormat("image/png");
        else if (mapIsParent && name.toLowerCase().equals("gif")) server.addGetMapFormat("image/gif");

    }

    public void endElement(String name) {
        if (name.toLowerCase().equals("layer")) {
            // Start edit by Khanh
            Object child = currPath.elementAt(currPath.size() - 1);
            currPath.removeElementAt(currPath.size() - 1);
            ((Vector) currPath.lastElement()).addElement(child);
            // End edit by Khanh
        } else if (layerIsParent
                && currPath.size() > 1
                && (name.toLowerCase().equals("srs") || name.toLowerCase().equals("title") || name.toLowerCase().equals("name") || name.toLowerCase()
                .equals("abstract"))) {
            if (thisText.length() > 0) {
                ((InnerTreeNode) currPath.lastElement()).getLayerInformation().setField(name.toLowerCase(), thisText.toString().trim());
            }
        } else if (name.toLowerCase().equals("getmap")) {
            getMapIsParent = false;
        } else if (name.toLowerCase().equals("service")) {
            serviceIsParent = false;
        } else if (name.toLowerCase().equals("map")) {
            mapIsParent = false;
        }

        if (name.toLowerCase().equals("style")) layerIsParent = true;

        if (getMapIsParent && name.toLowerCase().equals("format")) {
            if (thisText.length() > 0) {
                server.addGetMapFormat(thisText.toString().trim());
            }
        }

        if (serviceIsParent && name.toLowerCase().equals("title")) {
            if (thisText.length() > 0) {
                server.setName(thisText.toString().trim());
            }
        }

        thisText.delete(0, thisText.length());
    }

    public void characters(char ch[], int start, int length) {
        thisText.append(ch, start, length);
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Error: " + e);
        throw e;
    }
}
