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

import org.javavietnam.gis.shared.midp.model.PathStreet;
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
 * @author Khanh Parse FindPath result: <path> <street idproperty='streetid' idvalue='3'> <name>Nguyen Thi Minh Khai</name>  </street> <street idproperty='streetid' idvalue='10'> <name>Pastuer</name> </street> </path>
 */
public class FindPathResultParser extends MinML {

    private InputStream inputStream;

    private Vector path;

    private boolean streetIsParent;

    private boolean noticeIsParent;

    private String notice;

    private StringBuffer thisText = new StringBuffer();

    /**
     * Creates a new instance of FindPathResultParser
     *
     * @param result
     */
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
        this.inputStream = result;
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
        } else if (name.toLowerCase().equals("notice")) {
            noticeIsParent = true;
        }

    }

    public void endElement(String name) {
        if (name.toLowerCase().equals("street")) {
            streetIsParent = false;
        } else if (streetIsParent && name.toLowerCase().equals("name")) {
            if (thisText.length() > 0) {
                String streetName = thisText.toString().trim();
                ((PathStreet) path.lastElement()).setName(streetName);
            }

        } else if (noticeIsParent && name.toLowerCase().equals("notice")) {
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
     * @return Returns the notice.
     * @uml.property name="notice"
     */
    public String getNotice() {
        return notice;
    }

}
