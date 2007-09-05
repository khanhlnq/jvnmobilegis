// SAX Attribute List Interface.
// No warranty; no copyright -- use this as you will.

/*
 * $Id:AttributeList.java 48 2007-08-18 01:57:14Z khanh.lnq $
 * $URL:https://jvnmobilegis.googlecode.com/svn/trunk/src/org/xml/sax/AttributeList.java $
 * $Author:khanh.lnq $
 * $Revision:48 $
 * $Date:2007-08-18 08:57:14 +0700 (Sat, 18 Aug 2007) $
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

package org.xml.sax;

/**
 * Interface for an element's attribute specifications. <p/> The SAX parser
 * implements this interface and passes an instance to the SAX application as
 * the second argument of each startElement event.
 * </p>
 * <p/> The instance provided will return valid results only during the scope of
 * the startElement invocation (to save it for future use, the application must
 * make a copy: the AttributeListImpl helper class provides a convenient
 * constructor for doing so).
 * </p>
 * <p/> An AttributeList includes only attributes that have been specified or
 * defaulted: #IMPLIED attributes will not be included.
 * </p>
 * <p/> There are two ways for the SAX application to obtain information from
 * the AttributeList. First, it can iterate through the entire list:
 * </p>
 * <p/>
 * 
 * <pre>
 *          public void startElement (String name, AttributeList atts) {
 *            for (int i = 0; i &lt; atts.getLength(); i++) {
 *              String name = atts.getName(i);
 *              String type = atts.getType(i);
 *              String value = atts.getValue(i);
 *              [...]
 *            }
 *          }
 * </pre>
 * 
 * <p/> <p/> (Note that the result of getLength() will be zero if there are no
 * attributes.) <p/> As an alternative, the application can request the value or
 * type of specific attributes:
 * </p>
 * <p/>
 * 
 * <pre>
 *          public void startElement (String name, AttributeList atts) {
 *            String identifier = atts.getValue(&quot;id&quot;);
 *            String label = atts.getValue(&quot;label&quot;);
 *            [...]
 *          }
 * </pre>
 * 
 * <p/> <p/> The AttributeListImpl helper class provides a convenience
 * implementation for use by parser or application writers.
 * </p>
 * 
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.DocumentHandler#startElement
 * @see org.xml.sax.helpers.AttributeListImpl
 */
public interface AttributeList {

    /**
     * Return the number of attributes in this list. <p/> The SAX parser may
     * provide attributes in any arbitrary order, regardless of the order in
     * which they were declared or specified. The number of attributes may be
     * zero.
     * </p>
     * 
     * @return The number of attributes in the list.
     */
    public abstract int getLength();

    /**
     * Return the name of an attribute in this list (by position). <p/> The
     * names must be unique: the SAX parser shall not include the same attribute
     * twice. Attributes without values (those declared #IMPLIED without a value
     * specified in the start tag) will be omitted from the list.
     * </p>
     * <p/> If the attribute name has a namespace prefix, the prefix will still
     * be attached.
     * </p>
     * 
     * @param i
     *            The index of the attribute in the list (starting at 0).
     * @return The name of the indexed attribute, or null if the index is out of
     *         range.
     * @see #getLength
     */
    public abstract String getName(int i);

    /**
     * Return the type of an attribute in the list (by position). <p/> The
     * attribute type is one of the strings "CDATA", "ID", "IDREF", "IDREFS",
     * "NMTOKEN", "NMTOKENS", "ENTITY", "ENTITIES", or "NOTATION" (always in
     * upper case).
     * </p>
     * <p/> If the parser has not read a declaration for the attribute, or if
     * the parser does not report attribute types, then it must return the value
     * "CDATA" as stated in the XML 1.0 Recommentation (clause 3.3.3,
     * "Attribute-Value Normalization").
     * </p>
     * <p/> For an enumerated attribute that is not a notation, the parser will
     * report the type as "NMTOKEN".
     * </p>
     * 
     * @param i
     *            The index of the attribute in the list (starting at 0).
     * @return The attribute type as a string, or null if the index is out of
     *         range.
     * @see #getLength
     * @see #getType(java.lang.String)
     */
    public abstract String getType(int i);

    /**
     * Return the value of an attribute in the list (by position). <p/> If the
     * attribute value is a list of tokens (IDREFS, ENTITIES, or NMTOKENS), the
     * tokens will be concatenated into a single string separated by whitespace.
     * </p>
     * 
     * @param i
     *            The index of the attribute in the list (starting at 0).
     * @return The attribute value as a string, or null if the index is out of
     *         range.
     * @see #getLength
     * @see #getValue(java.lang.String)
     */
    public abstract String getValue(int i);

    /**
     * Return the type of an attribute in the list (by name). <p/> The return
     * value is the same as the return value for getType(int).
     * </p>
     * <p/> If the attribute name has a namespace prefix in the document, the
     * application must include the prefix here.
     * </p>
     * 
     * @param name
     *            The name of the attribute.
     * @return The attribute type as a string, or null if no such attribute
     *         exists.
     * @see #getType(int)
     */
    public abstract String getType(String name);

    /**
     * Return the value of an attribute in the list (by name). <p/> The return
     * value is the same as the return value for getValue(int).
     * </p>
     * <p/> If the attribute name has a namespace prefix in the document, the
     * application must include the prefix here.
     * </p>
     * 
     * @param i
     *            The index of the attribute in the list.
     * @param name
     * @return The attribute value as a string, or null if no such attribute
     *         exists.
     * @see #getValue(int)
     */
    public abstract String getValue(String name);

}
