/*
 * FindPathResultParser.java
 *
 * Created on April 24, 2006, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import org.javavietnam.gis.shared.midp.model.PathStreet;
import org.xml.sax.AttributeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import uk.co.wilson.xml.MinML;


/**
 * @author  Khanh Parse FindPath result: <path> <street idproperty='streetid' idvalue='3'> <name>Nguyen Thi Minh Khai</name>  </street> <street idproperty='streetid' idvalue='10'> <name>Pastuer</name> </street> </path>
 */
public class FindPathResultParser extends MinML {

    private InputStream inputStream;

    private Vector path;

    boolean streetIsParent;

    boolean noticeIsParent;

    private String notice;

    StringBuffer thisText = new StringBuffer();

    /** Creates a new instance of FindPathResultParser */
    public FindPathResultParser(String result) {
        try {
            this.inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.inputStream = new ByteArrayInputStream(result.getBytes());
        }
    }

    public FindPathResultParser(InputStream result) {
        this.inputStream = inputStream;
    }

    public Vector parsePath() {
        try {
            parse(new InputSource(inputStream));
        }
        catch (Exception e) {
            System.out.println("Exeption: ");
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public void startDocument() {
        path = new Vector();
    }

    public void endDocument() {
        // TODO: What will I do here?
    }

    public void startElement(String name, AttributeList attributes) {

        if (name.toLowerCase().equals("street")) {
            PathStreet street = new PathStreet();

            if (attributes.getValue("idproperty") != null) {
                street.setIdProperty(attributes.getValue("idproperty"));
            }

            path.addElement(street);
            streetIsParent = true;
        }
        else if (name.toLowerCase().equals("notice")) {
            noticeIsParent = true;
        }

    }

    public void endElement(String name) {
        if (name.toLowerCase().equals("street")) {
            streetIsParent = false;
        }
        else if (streetIsParent && name.toLowerCase().equals("name")) {
            if (thisText.length() > 0) {
                String streetName = thisText.toString().trim();
                ((PathStreet) path.lastElement()).setName(streetName);
            }

        }
        else if (noticeIsParent && name.toLowerCase().equals("notice")) {
            if (thisText.length() > 0) {
                notice = thisText.toString().trim();
                noticeIsParent = false;
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
     * @uml.property  name="notice"
     */
    public String getNotice() {
        return notice;
    }

}
