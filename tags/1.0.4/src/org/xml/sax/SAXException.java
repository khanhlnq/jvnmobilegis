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
 * Encapsulate a general SAX error or warning.
 * <p>
 * This class can contain basic error or warning information from either the XML
 * parser or the application: a parser writer or application writer can subclass
 * it to provide additional functionality. SAX handlers may throw this exception
 * or any exception subclassed from it.
 * </p>
 * <p>
 * If the application needs to pass through other types of exceptions, it must
 * wrap those exceptions in a SAXException or an exception derived from a
 * SAXException.
 * </p>
 * <p>
 * If the parser or application needs to include information about a specific
 * location in an XML document, it should use the SAXParseException subclass.
 * </p>
 * 
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.SAXParseException
 */
public class SAXException extends Exception {

    /**
     * Create a new SAXException.
     * 
     * @param message
     *            The error or warning message.
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXException(String message) {
        // Khanh edit
        super();
        this.message = message;
        this.exception = null;
    }

    /**
     * Create a new SAXException wrapping an existing exception. <p/> The
     * existing exception will be embedded in the new one, and its message will
     * become the default message for the SAXException. </p>
     * 
     * @param e
     *            The exception to be wrapped in a SAXException.
     */
    public SAXException(Exception e) {
        super();
        this.message = null;
        this.exception = e;
    }

    /**
     * Create a new SAXException from an existing exception. <p/> The existing
     * exception will be embedded in the new one, but the new exception will
     * have its own message. </p>
     * 
     * @param message
     *            The detail message.
     * @param e
     *            The exception to be wrapped in a SAXException.
     * @see org.xml.sax.Parser#setLocale
     */
    public SAXException(String message, Exception e) {
        super();
        this.message = message;
        this.exception = e;
    }

    /**
     * Return a detail message for this exception.
     * <p>
     * If there is a embedded exception, and if the SAXException has no detail
     * message of its own, this method will return the detail message from the
     * embedded exception.
     * </p>
     * 
     * @return The error or warning message.
     * @see org.xml.sax.Parser#setLocale
     * @uml.property name="message"
     */
    public String getMessage() {
        if (message == null && exception != null) {
            return exception.getMessage();
        } else {
            return this.message;
        }
    }

    /**
     * Return the embedded exception, if any.
     * 
     * @return The embedded exception, or null if there is none.
     * @uml.property name="exception"
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Convert this exception to a string.
     * 
     * @return A string version of this exception.
     */
    public String toString() {
        return getMessage();
    }

    // ////////////////////////////////////////////////////////////////////
    // Internal state.
    // ////////////////////////////////////////////////////////////////////

    /**
     * @uml.property name="message"
     */
    private String message;
    /**
     * @uml.property name="exception"
     */
    private Exception exception;

}
