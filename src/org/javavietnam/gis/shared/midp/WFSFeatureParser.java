/*
 * $Id: CapabilitiesParser.java 140 2007-10-05 07:17:58Z khanhlnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/shared/midp/WFSParser.java $
 * $Author: khanhlnq $
 * $Revision: 140 $
 * $Date: 2007-10-05 14:17:58 +0700 (Fri, 05 Oct 2007) $
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
package org.javavietnam.gis.shared.midp;

import henson.midp.Float;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.javavietnam.gis.shared.midp.model.FeatureInformation;
import org.javavietnam.gis.shared.midp.model.InnerTreeNode;
import org.javavietnam.gis.shared.midp.model.TreeNode;
import org.xml.sax.AttributeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import uk.co.wilson.xml.MinML;

/**
 * @author: taina
 */
public class WFSFeatureParser extends MinML {

    private InputStream input;
    private Vector currPath;
    private Vector finalPath = null;
    private StringBuffer thisText = new StringBuffer();
    private Vector typeName;
    private Vector propertyName;
    private int coorPos = 0;

    public WFSFeatureParser(InputStream input) {
        this.input = input;
    }

    public WFSFeatureParser(String result) {
        try {
            this.input = new ByteArrayInputStream(result.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
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
        finalPath = null;
        try {
            parse(new InputSource(input));
        } catch (Exception e) {
            System.out.println("Exeption aufgetreten: " + e);
            e.printStackTrace();
            return null;
        }

        return currPath;
    }

    public void startDocument() {
        currPath = new Vector();
    }

    public void endDocument() {
        finalPath = new Vector();

        if (currPath.size() > 0) {
            eliminateInnerNodes(currPath);
        }
        Vector firstNode = currPath;
        firstNode.removeAllElements();
        for (int i = 0; i < finalPath.size(); i++) {
            firstNode.addElement(finalPath.elementAt(i));
        }
    }

    private void eliminateInnerNodes(Vector node) {
        for (int i = 0; i < node.size(); i++) {
            InnerTreeNode currChild = (InnerTreeNode) node.elementAt(i);
            if (currChild.size() > 0) {
                eliminateInnerNodes(currChild);
            } else {
                TreeNode treeNode = new TreeNode();
                treeNode.setFeatureInformation(currChild.getFeatureInformation());
                finalPath.addElement(treeNode);
            }
        }
    }

    public void startElement(String name, AttributeList attributes) {
        if (typeName.contains(name)) {
            FeatureInformation feature = new FeatureInformation();
            feature.setLayerName(name);

            InnerTreeNode node = new InnerTreeNode();
            node.setFeatureInformation(feature);
            currPath.addElement(node);
        }
    }

    public void endElement(String name) {
        if (typeName.contains(name)) {
            // TODO
        } else if (name.toLowerCase().equals("gml:coordinates")) {
            if (coorPos == 0) {
                coorPos++;
            } else if (thisText.length() > 0) {
                String content = thisText.toString().trim().replace(' ', ',');
                String[] coors = split(content, ",");
                Float[] bbox = new Float[]{
                    Float.parse(coors[0], 10),
                    Float.parse(coors[1], 10),
                    Float.parse(coors[2], 10),
                    Float.parse(coors[3], 10)
                };

                ((InnerTreeNode) currPath.lastElement()).getFeatureInformation().setBbox(bbox);
            }
        } else {
            String[] elemName = split(name, ":");
            String attName = null;
            if (elemName.length == 2) {
                attName = elemName[1];
            } else {
                attName = name;
            }

            if (propertyName.contains(attName)) {
                if (thisText.length() > 0) {
                    String content = thisText.toString().trim();
                    ((InnerTreeNode) currPath.lastElement()).getFeatureInformation().setField(attName, content);
                }
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

    public Vector getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(Vector propertyName) {
        this.propertyName = propertyName;
    }

    public Vector getTypeName() {
        return typeName;
    }

    public void setTypeName(Vector typeName) {
        this.typeName = typeName;
    }

    public String[] split(String content, String character) {
        Vector textList = new Vector();
        String tmp = content;
        int pos = tmp.indexOf(character);

        while (pos != -1) {
            textList.addElement(tmp.substring(0, pos));
            tmp = tmp.substring(pos + 1);
            pos = tmp.indexOf(character);
        }

        textList.addElement(tmp);

        if (textList.size() == 0) {
            return null;
        }

        String[] result = new String[textList.size()];
        for (int i = 0; i < textList.size(); i++) {
            result[i] = (String) textList.elementAt(i);
        }

        return result;
    }
}
