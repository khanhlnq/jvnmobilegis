// SAX input source.
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

import java.io.InputStream;
import java.io.Reader;

/**
 * A single input source for an XML entity.
 * <p>
 * This class allows a SAX application to encapsulate information about an input
 * source in a single object, which may include a public identifier, a system
 * identifier, a byte stream (possibly with a specified encoding), and/or a
 * character stream.
 * </p>
 * <p>
 * There are two places that the application will deliver this input source to
 * the parser: as the argument to the Parser.parse method, or as the return
 * value of the EntityResolver.resolveEntity method.
 * </p>
 * <p>
 * The SAX parser will use the InputSource object to determine how to read XML
 * input. If there is a character stream available, the parser will read that
 * stream directly; if not, the parser will use a byte stream, if available; if
 * neither a character stream nor a byte stream is available, the parser will
 * attempt to open a URI connection to the resource identified by the system
 * identifier.
 * </p>
 * <p>
 * An InputSource object belongs to the application: the SAX parser shall never
 * modify it in any way (it may modify a copy if necessary).
 * </p>
 * 
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.Parser#parse
 * @see org.xml.sax.EntityResolver#resolveEntity
 * @see java.io.InputStream
 * @see java.io.Reader
 */
public class InputSource {

    /**
     * Zero-argument default constructor.
     * 
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setCharacterStream
     * @see #setEncoding
     */
    public InputSource() {
    }

    /**
     * Create a new input source with a system identifier. <p/> Applications may
     * use setPublicId to include a public identifier as well, or setEncoding to
     * specify the character encoding, if known. </p> <p/> If the system
     * identifier is a URL, it must be full resolved. </p>
     * 
     * @param systemId
     *            The system identifier (URI).
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setEncoding
     * @see #setCharacterStream
     */
    public InputSource(String systemId) {
        setSystemId(systemId);
    }

    /**
     * Create a new input source with a byte stream. <p/> Application writers
     * may use setSystemId to provide a base for resolving relative URIs,
     * setPublicId to include a public identifier, and/or setEncoding to specify
     * the object's character encoding. </p>
     * 
     * @param byteStream
     *            The raw byte stream containing the document.
     * @see #setPublicId
     * @see #setSystemId
     * @see #setEncoding
     * @see #setByteStream
     * @see #setCharacterStream
     */
    public InputSource(InputStream byteStream) {
        setByteStream(byteStream);
    }

    /**
     * Create a new input source with a character stream. <p/> Application
     * writers may use setSystemId() to provide a base for resolving relative
     * URIs, and setPublicId to include a public identifier. </p> <p/> The
     * character stream shall not include a byte order mark. </p>
     * 
     * @param characterStream
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setCharacterStream
     */
    public InputSource(Reader characterStream) {
        setCharacterStream(characterStream);
    }

    /**
     * Set the public identifier for this input source.
     * <p>
     * The public identifier is always optional: if the application writer
     * includes one, it will be provided as part of the location information.
     * </p>
     * 
     * @param publicId
     *            The public identifier as a string.
     * @see #getPublicId
     * @see org.xml.sax.Locator#getPublicId
     * @see org.xml.sax.SAXParseException#getPublicId
     * @uml.property name="publicId"
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * Get the public identifier for this input source.
     * 
     * @return The public identifier, or null if none was supplied.
     * @see #setPublicId
     * @uml.property name="publicId"
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * Set the system identifier for this input source.
     * <p>
     * The system identifier is optional if there is a byte stream or a
     * character stream, but it is still useful to provide one, since the
     * application can use it to resolve relative URIs and can include it in
     * error messages and warnings (the parser will attempt to open a connection
     * to the URI only if there is no byte stream or character stream
     * specified).
     * </p>
     * <p>
     * If the application knows the character encoding of the object pointed to
     * by the system identifier, it can register the encoding using the
     * setEncoding method.
     * </p>
     * <p>
     * If the system ID is a URL, it must be fully resolved.
     * </p>
     * 
     * @param systemId
     *            The system identifier as a string.
     * @see #setEncoding
     * @see #getSystemId
     * @see org.xml.sax.Locator#getSystemId
     * @see org.xml.sax.SAXParseException#getSystemId
     * @uml.property name="systemId"
     */
    private void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Get the system identifier for this input source.
     * <p>
     * The getEncoding method will return the character encoding of the object
     * pointed to, or null if unknown.
     * </p>
     * <p>
     * If the system ID is a URL, it will be fully resolved.
     * </p>
     * 
     * @return The system identifier.
     * @see #setSystemId
     * @see #getEncoding
     * @uml.property name="systemId"
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Set the byte stream for this input source.
     * <p>
     * The SAX parser will ignore this if there is also a character stream
     * specified, but it will use a byte stream in preference to opening a URI
     * connection itself.
     * </p>
     * <p>
     * If the application knows the character encoding of the byte stream, it
     * should set it with the setEncoding method.
     * </p>
     * 
     * @param byteStream
     *            A byte stream containing an XML document or other entity.
     * @see #setEncoding
     * @see #getByteStream
     * @see #getEncoding
     * @see java.io.InputStream
     * @uml.property name="byteStream"
     */
    private void setByteStream(InputStream byteStream) {
        this.byteStream = byteStream;
    }

    /**
     * Get the byte stream for this input source.
     * <p>
     * The getEncoding method will return the character encoding for this byte
     * stream, or null if unknown.
     * </p>
     * 
     * @return The byte stream, or null if none was supplied.
     * @see #getEncoding
     * @see #setByteStream
     * @uml.property name="byteStream"
     */
    public InputStream getByteStream() {
        return byteStream;
    }

    /**
     * Set the character encoding, if known.
     * <p>
     * The encoding must be a string acceptable for an XML encoding declaration
     * (see section 4.3.3 of the XML 1.0 recommendation).
     * </p>
     * <p>
     * This method has no effect when the application provides a character
     * stream.
     * </p>
     * 
     * @param encoding
     *            A string describing the character encoding.
     * @see #setSystemId
     * @see #setByteStream
     * @see #getEncoding
     * @uml.property name="encoding"
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Get the character encoding for a byte stream or URI.
     * 
     * @return The encoding, or null if none was supplied.
     * @see #setByteStream
     * @see #getSystemId
     * @see #getByteStream
     * @uml.property name="encoding"
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Set the character stream for this input source.
     * <p>
     * If there is a character stream specified, the SAX parser will ignore any
     * byte stream and will not attempt to open a URI connection to the system
     * identifier.
     * </p>
     * 
     * @param characterStream
     *            The character stream containing the XML document or other
     *            entity.
     * @see #getCharacterStream
     * @see java.io.Reader
     * @uml.property name="characterStream"
     */
    private void setCharacterStream(Reader characterStream) {
        this.characterStream = characterStream;
    }

    /**
     * Get the character stream for this input source.
     * 
     * @return The character stream, or null if none was supplied.
     * @see #setCharacterStream
     * @uml.property name="characterStream"
     */
    public Reader getCharacterStream() {
        return characterStream;
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
     * @uml.property name="byteStream"
     */
    private InputStream byteStream;
    /**
     * @uml.property name="encoding"
     */
    private String encoding = "UTF-8";
    /**
     * @uml.property name="characterStream"
     */
    private Reader characterStream;

}
