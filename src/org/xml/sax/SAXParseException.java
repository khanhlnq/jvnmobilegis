// SAX exception class.
// No warranty; no copyright -- use this as you will.

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

package org.xml.sax;

/**
 * Encapsulate an XML parse error or warning.
 * <p>
 * This exception will include information for locating the error in the
 * original XML document. Note that although the application will receive a
 * SAXParseException as the argument to the handlers in the ErrorHandler
 * interface, the application is not actually required to throw the exception;
 * instead, it can simply read the information in it and take a different
 * action.
 * </p>
 * <p>
 * Since this exception is a subclass of SAXException, it inherits the ability
 * to wrap another exception.
 * </p>
 * 
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.SAXException
 * @see org.xml.sax.Locator
 * @see org.xml.sax.ErrorHandler
 */
public class SAXParseException extends SAXException {

    // ////////////////////////////////////////////////////////////////////
    // Constructors.
    // ////////////////////////////////////////////////////////////////////

    /**
     * Create a new SAXParseException from a message and a Locator. <p/> This
     * constructor is especially useful when an application is creating its own
     * exception from within a DocumentHandler callback. </p>
     * 
     * @param message
     *            The error or warning message.
     * @param locator
     *            The locator object for the error or warning.
     * @see org.xml.sax.Locator
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXParseException(String message, Locator locator) {
        super(message);
        this.publicId = locator.getPublicId();
        this.systemId = locator.getSystemId();
        this.lineNumber = locator.getLineNumber();
        this.columnNumber = locator.getColumnNumber();
    }

    /**
     * Wrap an existing exception in a SAXParseException. <p/> This constructor
     * is especially useful when an application is creating its own exception
     * from within a DocumentHandler callback, and needs to wrap an existing
     * exception that is not a subclass of SAXException. </p>
     * 
     * @param message
     *            The error or warning message, or null to use the message from
     *            the embedded exception.
     * @param locator
     *            The locator object for the error or warning.
     * @param e
     *            Any exception
     * @see org.xml.sax.Locator
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXParseException(String message, Locator locator, Exception e) {
        super(message, e);
        this.publicId = locator.getPublicId();
        this.systemId = locator.getSystemId();
        this.lineNumber = locator.getLineNumber();
        this.columnNumber = locator.getColumnNumber();
    }

    /**
     * Create a new SAXParseException. <p/> This constructor is most useful for
     * parser writers. </p> <p/> If the system identifier is a URL, the parser
     * must resolve it fully before creating the exception. </p>
     * 
     * @param message
     *            The error or warning message.
     * @param publicId
     *            The public identifer of the entity that generated the error or
     *            warning.
     * @param systemId
     *            The system identifer of the entity that generated the error or
     *            warning.
     * @param lineNumber
     *            The line number of the end of the text that caused the error
     *            or warning.
     * @param columnNumber
     *            The column number of the end of the text that cause the error
     *            or warning.
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXParseException(String message, String publicId, String systemId,
            int lineNumber, int columnNumber) {
        super(message);
        this.publicId = publicId;
        this.systemId = systemId;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Create a new SAXParseException with an embedded exception. <p/> This
     * constructor is most useful for parser writers who need to wrap an
     * exception that is not a subclass of SAXException. </p> <p/> If the system
     * identifier is a URL, the parser must resolve it fully before creating the
     * exception. </p>
     * 
     * @param message
     *            The error or warning message, or null to use the message from
     *            the embedded exception.
     * @param publicId
     *            The public identifer of the entity that generated the error or
     *            warning.
     * @param systemId
     *            The system identifer of the entity that generated the error or
     *            warning.
     * @param lineNumber
     *            The line number of the end of the text that caused the error
     *            or warning.
     * @param columnNumber
     *            The column number of the end of the text that cause the error
     *            or warning.
     * @param e
     *            Another exception to embed in this one.
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXParseException(String message, String publicId, String systemId,
            int lineNumber, int columnNumber, Exception e) {
        super(message, e);
        this.publicId = publicId;
        this.systemId = systemId;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Get the public identifier of the entity where the exception occurred.
     * 
     * @return A string containing the public identifier, or null if none is
     *         available.
     * @see org.xml.sax.Locator#getPublicId
     * @uml.property name="publicId"
     */
    public String getPublicId() {
        return this.publicId;
    }

    /**
     * Get the system identifier of the entity where the exception occurred.
     * <p>
     * If the system identifier is a URL, it will be resolved fully.
     * </p>
     * 
     * @return A string containing the system identifier, or null if none is
     *         available.
     * @see org.xml.sax.Locator#getSystemId
     * @uml.property name="systemId"
     */
    public String getSystemId() {
        return this.systemId;
    }

    /**
     * The line number of the end of the text where the exception occurred.
     * 
     * @return An integer representing the line number, or -1 if none is
     *         available.
     * @see org.xml.sax.Locator#getLineNumber
     * @uml.property name="lineNumber"
     */
    public int getLineNumber() {
        return this.lineNumber;
    }

    /**
     * The column number of the end of the text where the exception occurred.
     * <p>
     * The first column in a line is position 1.
     * </p>
     * 
     * @return An integer representing the column number, or -1 if none is
     *         available.
     * @see org.xml.sax.Locator#getColumnNumber
     * @uml.property name="columnNumber"
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    // ////////////////////////////////////////////////////////////////////
    // Internal state.
    // ////////////////////////////////////////////////////////////////////

    /**
     * @uml.property name="publicId"
     */
    private String publicId;
    /**
     * @uml.property name="systemId"
     */
    private String systemId;
    /**
     * @uml.property name="lineNumber"
     */
    private int lineNumber;
    /**
     * @uml.property name="columnNumber"
     */
    private int columnNumber;

}
