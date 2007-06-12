/*
 * FindPathResultParser.java
 *
 * Created on April 24, 2006, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp;

import henson.midp.Float;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import org.javavietnam.gis.shared.midp.model.MapFeature;
import org.xml.sax.AttributeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import uk.co.wilson.xml.MinML;


/**
 * @author  Khanh Parsing SearchFeature result: <searchfeatures> <features> <feature> <id></id> <name></name> <x></x>  <y></y> </feature> ...... </features> <bookmark></bookmark> </searchfeatures>
 */

public class SearchFeatureResultParser extends MinML {

    private InputStream inputStream;

    private Vector features;

    boolean featureIsParent;

    boolean bookmarkIsParent;

    private int numResult = 0;

    StringBuffer thisText = new StringBuffer();

    /** Creates a new instance of FindPathResultParser */
    public SearchFeatureResultParser(String result) {
        try {
            this.inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.inputStream = new ByteArrayInputStream(result.getBytes());
        }
    }

    public SearchFeatureResultParser(InputStream result) {
        this.inputStream = inputStream;
    }

    public Vector parseFeatures() {
        numResult = 0;
        try {
            parse(new InputSource(inputStream));
        }
        catch (Exception e) {
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
        }
        else if (name.toLowerCase().equals("bookmark")) {
            bookmarkIsParent = true;
        }

    }

    public void endElement(String name) {
        if (name.toLowerCase().equals("feature")) {
            featureIsParent = false;
        }
        else if (featureIsParent) {
            if (name.toLowerCase().equals("id")) {
                if (thisText.length() > 0) {
                    String featureId = thisText.toString().trim();
                    ((MapFeature) features.lastElement()).setId(featureId);
                }
            }

            else if (name.toLowerCase().equals("name")) {
                if (thisText.length() > 0) {
                    String featureName = thisText.toString().trim();
                    ((MapFeature) features.lastElement()).setName(featureName);
                }
            }

            else if (name.toLowerCase().equals("x")) {
                if (thisText.length() > 0) {
                    String xStr = thisText.toString().trim();
                    Float featureX = Float.parse(xStr, 10);
                    ((MapFeature) features.lastElement()).setX(featureX);
                }
            }

            else if (name.toLowerCase().equals("y")) {
                if (thisText.length() > 0) {
                    String yStr = thisText.toString().trim();
                    Float featureY = Float.parse(yStr, 10);
                    ((MapFeature) features.lastElement()).setY(featureY);
                }
            }

        }
        else if (bookmarkIsParent && name.toLowerCase().equals("bookmark")) {
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
     * @return  Returns the notice.
     * @uml.property  name="numResult"
     */
    public int getNumResult() {
        return numResult;
    }

}
