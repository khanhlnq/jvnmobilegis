// SAX error handler.
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
 * Basic interface for SAX error handlers. <p/> If a SAX application needs to
 * implement customized error handling, it must implement this interface and
 * then register an instance with the SAX parser using the parser's
 * setErrorHandler method. The parser will then report all errors and warnings
 * through this interface. </p> <p/> The parser shall use this interface instead
 * of throwing an exception: it is up to the application whether to throw an
 * exception for different types of errors and warnings. Note, however, that
 * there is no requirement that the parser continue to provide useful
 * information after a call to fatalError (in other words, a SAX driver class
 * could catch an exception and report a fatalError). </p> <p/> The HandlerBase
 * class provides a default implementation of this interface, ignoring warnings
 * and recoverable errors and throwing a SAXParseException for fatal errors. An
 * application may extend that class rather than implementing the complete
 * interface itself. </p>
 * 
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.Parser#setErrorHandler
 * @see org.xml.sax.SAXParseException
 * @see org.xml.sax.HandlerBase
 */
public interface ErrorHandler {

    /**
     * Receive notification of a warning. <p/> SAX parsers will use this method
     * to report conditions that are not errors or fatal errors as defined by
     * the XML 1.0 recommendation. The default behaviour is to take no action.
     * </p> <p/> The SAX parser must continue to provide normal parsing events
     * after invoking this method: it should still be possible for the
     * application to process the document through to the end. </p>
     * 
     * @param exception
     *            The warning information encapsulated in a SAX parse exception.
     * @throws org.xml.sax.SAXException
     *             Any SAX exception, possibly wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void warning(SAXParseException exception)
            throws SAXException;

    /**
     * Receive notification of a recoverable error. <p/> This corresponds to the
     * definition of "error" in section 1.2 of the W3C XML 1.0 Recommendation.
     * For example, a validating parser would use this callback to report the
     * violation of a validity constraint. The default behaviour is to take no
     * action. </p> <p/> The SAX parser must continue to provide normal parsing
     * events after invoking this method: it should still be possible for the
     * application to process the document through to the end. If the
     * application cannot do so, then the parser should report a fatal error
     * even if the XML 1.0 recommendation does not require it to do so. </p>
     * 
     * @param exception
     *            The error information encapsulated in a SAX parse exception.
     * @throws org.xml.sax.SAXException
     *             Any SAX exception, possibly wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void error(SAXParseException exception) throws SAXException;

    /**
     * Receive notification of a non-recoverable error. <p/> This corresponds to
     * the definition of "fatal error" in section 1.2 of the W3C XML 1.0
     * Recommendation. For example, a parser would use this callback to report
     * the violation of a well-formedness constraint. </p> <p/> The application
     * must assume that the document is unusable after the parser has invoked
     * this method, and should continue (if at all) only for the sake of
     * collecting addition error messages: in fact, SAX parsers are free to stop
     * reporting any other events once this method has been invoked. </p>
     * 
     * @param exception
     *            The error information encapsulated in a SAX parse exception.
     * @throws org.xml.sax.SAXException
     *             Any SAX exception, possibly wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void fatalError(SAXParseException exception)
            throws SAXException;

}
