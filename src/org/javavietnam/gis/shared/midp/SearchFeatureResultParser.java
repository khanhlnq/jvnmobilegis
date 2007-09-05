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

package org.javavietnam.gis.shared.midp;

import henson.midp.Float;
import org.javavietnam.gis.shared.midp.model.MapFeature;
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
 * @author khanhlnq
 */
public class SearchFeatureResultParser extends MinML {

    private InputStream inputStream;

    private Vector features;

    private boolean featureIsParent;

    private boolean bookmarkIsParent;

    /**
     */
    private int numResult = 0;

    private StringBuffer thisText = new StringBuffer();

    /**
     * Creates a new instance of FindPathResultParser
     * 
     * @param result
     */
    public SearchFeatureResultParser(String result) {
        try {
            this.inputStream = new ByteArrayInputStream(result
                    .getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.inputStream = new ByteArrayInputStream(result.getBytes());
        }
    }

    public SearchFeatureResultParser(InputStream result) {
        this.inputStream = result;
    }

    public Vector parseFeatures() {
        numResult = 0;
        try {
            parse(new InputSource(inputStream));
        } catch (Exception e) {
            System.out.println("Exeption: ");
            e.printStackTrace();
            return null;
        }

        return features;
    }

    public void startDocument() {
        features = new Vector();
    }

    public void endDocument() {
        // TODO: What will I do here?
    }

    public void startElement(String name, AttributeList attributes) {

        if (name.toLowerCase().equals("feature")) {
            MapFeature feature = new MapFeature();
            features.addElement(feature);
            featureIsParent = true;
        } else if (name.toLowerCase().equals("bookmark")) {
            bookmarkIsParent = true;
        }

    }

    public void endElement(String name) {
        if (name.toLowerCase().equals("feature")) {
            featureIsParent = false;
        } else if (featureIsParent) {
            if (name.toLowerCase().equals("id")) {
                if (thisText.length() > 0) {
                    String featureId = thisText.toString().trim();
                    ((MapFeature) features.lastElement()).setId(featureId);
                }
            } else if (name.toLowerCase().equals("name")) {
                if (thisText.length() > 0) {
                    String featureName = thisText.toString().trim();
                    ((MapFeature) features.lastElement()).setName(featureName);
                }
            } else if (name.toLowerCase().equals("x")) {
                if (thisText.length() > 0) {
                    String xStr = thisText.toString().trim();
                    Float featureX = Float.parse(xStr, 10);
                    ((MapFeature) features.lastElement()).setX(featureX);
                }
            } else if (name.toLowerCase().equals("y")) {
                if (thisText.length() > 0) {
                    String yStr = thisText.toString().trim();
                    Float featureY = Float.parse(yStr, 10);
                    ((MapFeature) features.lastElement()).setY(featureY);
                }
            }

        } else if (bookmarkIsParent && name.toLowerCase().equals("bookmark")) {
            if (thisText.length() > 0) {
                String bookmarkStr = thisText.toString().trim();
                numResult = Integer.parseInt(bookmarkStr);
                bookmarkIsParent = false;
            }

        }

        thisText.delete(0, thisText.length());

    }

    public void characters(final char ch[], final int start, final int length) {
        thisText.append(ch, start, length);
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Error: " + e);
        throw e;
    }

    /**
     * @return Returns the notice.
     */
    public int getNumResult() {
        return numResult;
    }

}
