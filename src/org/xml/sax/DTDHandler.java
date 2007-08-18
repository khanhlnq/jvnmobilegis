// SAX DTD handler.
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
 * http://jvnmobilegis.googlecode.com/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq at javavietnam.org
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.xml.sax;

/**
 * Receive notification of basic DTD-related events.
 * <p/>
 * If a SAX application needs information about notations and unparsed entities, then the application implements this
 * interface and registers an instance with the SAX parser using the parser's setDTDHandler method. The parser uses the
 * instance to report notation and unparsed entity declarations to the application.
 * </p>
 * <p/>
 * The SAX parser may report these events in any order, regardless of the order in which the notations and unparsed
 * entities were declared; however, all DTD events must be reported after the document handler's startDocument event,
 * and before the first startElement event.
 * </p>
 * <p/>
 * It is up to the application to store the information for future use (perhaps in a hash table or object tree). If the
 * application encounters attributes of type "NOTATION", "ENTITY", or "ENTITIES", it can use the information that it
 * obtained through this interface to find the entity and/or notation corresponding with the attribute value.
 * </p>
 * <p/>
 * The HandlerBase class provides a default implementation of this interface, which simply ignores the events.
 * </p>
 *
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.Parser#setDTDHandler
 * @see org.xml.sax.HandlerBase
 */
public interface DTDHandler {

    /**
     * Receive notification of a notation declaration event.
     * <p/>
     * It is up to the application to record the notation for later reference, if necessary.
     * </p>
     * <p/>
     * If a system identifier is present, and it is a URL, the SAX parser must resolve it fully before passing it to the
     * application.
     * </p>
     *
     * @param name     The notation name.
     * @param publicId The notation's public identifier, or null if none was given.
     * @param systemId The notation's system identifier, or null if none was given.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
     * @see #unparsedEntityDecl
     * @see org.xml.sax.AttributeList
     */
    public abstract void notationDecl(String name, String publicId, String systemId) throws SAXException;

    /**
     * Receive notification of an unparsed entity declaration event.
     * <p/>
     * Note that the notation name corresponds to a notation reported by the notationDecl() event. It is up to the
     * application to record the entity for later reference, if necessary.
     * </p>
     * <p/>
     * If the system identifier is a URL, the parser must resolve it fully before passing it to the application.
     * </p>
     *
     * @param name         The unparsed entity's name.
     * @param publicId     The entity's public identifier, or null if none was given.
     * @param systemId     The entity's system identifier (it must always have one).
     * @param notation     name The name of the associated notation.
     * @param notationName
     * @throws org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
     * @see #notationDecl
     * @see org.xml.sax.AttributeList
     */
    public abstract void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException;

}