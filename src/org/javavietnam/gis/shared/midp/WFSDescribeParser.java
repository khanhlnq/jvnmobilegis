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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.xml.sax.AttributeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import uk.co.wilson.xml.MinML;

/**
 * @author: taina
 */
public class WFSDescribeParser extends MinML {

    private InputStream input;
    private StringBuffer thisText = new StringBuffer();
    private Vector attributeList;

    public WFSDescribeParser(InputStream input) {
        this.input = input;
    }

    public WFSDescribeParser(String result) {
        try {
            this.input = new ByteArrayInputStream(result.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.input = new ByteArrayInputStream(result.getBytes());
        }
    }

    public Vector constructDataTree() {
        try {
            parse(new InputSource(input));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
            return null;
        }

        return attributeList;
    }

    public void startDocument() {
        attributeList = new Vector();
    }

    public void endDocument() {
    }

    public void startElement(String name, AttributeList attributes) {
        if (name.toLowerCase().equals("xs:element")) {
            String attribute = attributes.getValue("name");
            attributeList.addElement(attribute);
        }
    }

    public void endElement(String name) {
    }

    public void characters(char ch[], int start, int length) {
        thisText.append(ch, start, length);
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Error: " + e);
        throw e;
    }
}
