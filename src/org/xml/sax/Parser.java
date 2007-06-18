// SAX parser interface.
// No warranty; no copyright -- use this as you will.
// $Id$

/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */
package org.xml.sax;

import java.io.IOException;


/**
 * Basic interface for SAX (Simple API for XML) parsers.
 * <p/>
 * All SAX parsers must implement this basic interface: it allows applications to register handlers for different types
 * of events and to initiate a parse from a URI, or a character stream.
 * </p>
 * <p/>
 * All SAX parsers must also implement a zero-argument constructor (though other constructors are also allowed).
 * </p>
 * <p/>
 * SAX parsers are reusable but not re-entrant: the application may reuse a parser object (possibly with a different
 * input source) once the first parse has completed successfully, but it may not invoke the parse() methods recursively
 * within a parse.
 * </p>
 *
 * @author David Megginson (ak117@freenet.carleton.ca)
 * @version 1.0
 * @see org.xml.sax.EntityResolver
 * @see org.xml.sax.DTDHandler
 * @see org.xml.sax.DocumentHandler
 * @see org.xml.sax.ErrorHandler
 * @see org.xml.sax.HandlerBase
 * @see org.xml.sax.InputSource
 */
public interface Parser {

    /**
     * Allow an application to register a custom entity resolver.
     * <p/>
     * If the application does not register an entity resolver, the SAX parser will resolve system identifiers and open
     * connections to entities itself (this is the default behaviour implemented in HandlerBase).
     * </p>
     * <p/>
     * Applications may register a new or different entity resolver in the middle of a parse, and the SAX parser must
     * begin using the new resolver immediately.
     * </p>
     *
     * @param resolver The object for resolving entities.
     * @see EntityResolver
     * @see HandlerBase
     */
    public abstract void setEntityResolver(EntityResolver resolver);

    /**
     * Allow an application to register a DTD event handler.
     * <p/>
     * If the application does not register a DTD handler, all DTD events reported by the SAX parser will be silently
     * ignored (this is the default behaviour implemented by HandlerBase).
     * </p>
     * <p/>
     * Applications may register a new or different handler in the middle of a parse, and the SAX parser must begin
     * using the new handler immediately.
     * </p>
     *
     * @param handler The DTD handler.
     * @see DTDHandler
     * @see HandlerBase
     */
    public abstract void setDTDHandler(DTDHandler handler);

    /**
     * Allow an application to register a document event handler.
     * <p/>
     * If the application does not register a document handler, all document events reported by the SAX parser will be
     * silently ignored (this is the default behaviour implemented by HandlerBase).
     * </p>
     * <p/>
     * Applications may register a new or different handler in the middle of a parse, and the SAX parser must begin
     * using the new handler immediately.
     * </p>
     *
     * @param handler The document handler.
     * @see DocumentHandler
     * @see HandlerBase
     */
    public abstract void setDocumentHandler(DocumentHandler handler);

    /**
     * Allow an application to register an error event handler.
     * <p/>
     * If the application does not register an error event handler, all error events reported by the SAX parser will be
     * silently ignored, except for fatalError, which will throw a SAXException (this is the default behaviour
     * implemented by HandlerBase).
     * </p>
     * <p/>
     * Applications may register a new or different handler in the middle of a parse, and the SAX parser must begin
     * using the new handler immediately.
     * </p>
     *
     * @param handler The error handler.
     * @see ErrorHandler
     * @see SAXException
     * @see HandlerBase
     */
    public abstract void setErrorHandler(ErrorHandler handler);

    /**
     * Parse an XML document.
     * <p/>
     * The application can use this method to instruct the SAX parser to begin parsing an XML document from any valid
     * input source (a character stream, a byte stream, or a URI).
     * </p>
     * <p/>
     * Applications may not invoke this method while a parse is in progress (they should create a new Parser instead for
     * each additional XML document). Once a parse is complete, an application may reuse the same Parser object,
     * possibly with a different input source.
     * </p>
     *
     * @param source The input source for the top-level of the XML document.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly wrapping another exception.
     * @throws java.io.IOException      An IO exception from the parser, possibly from a byte stream or character stream supplied by the
     *                                  application.
     * @see org.xml.sax.InputSource
     * @see #parse(java.lang.String)
     * @see #setEntityResolver
     * @see #setDTDHandler
     * @see #setDocumentHandler
     * @see #setErrorHandler
     */
    public abstract void parse(InputSource source) throws SAXException, IOException;

    /**
     * Parse an XML document from a system identifier (URI).
     * <p>
     * This method is a shortcut for the common case of reading a document from a system identifier. It is the exact
     * equivalent of the following:
     * </p>
     *
     * <pre>
     * parse(new InputSource(systemId));
     * </pre>
     *
     * <p>
     * If the system identifier is a URL, it must be fully resolved by the application before it is passed to the
     * parser.
     * </p>
     *
     * @param systemId
     *            The system identifier (URI).
     * @exception org.xml.sax.SAXException
     *                Any SAX exception, possibly wrapping another exception.
     * @exception java.io.IOException
     *                An IO exception from the parser, possibly from a byte stream or character stream supplied by the
     *                application.
     * @see #parse(org.xml.sax.InputSource)
     */
    // public abstract void parse (String systemId)
    // throws SAXException, IOException;
}
