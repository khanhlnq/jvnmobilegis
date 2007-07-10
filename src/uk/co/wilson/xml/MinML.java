// Copyright (c) 2000, 2001 The Wilson Partnership.
// All Rights Reserved.
// @(#)MinML.java, 1.7, 18th November 2001
// Author: John Wilson - tug@wilson.co.uk

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
package uk.co.wilson.xml;

/*
 * Copyright (c) 2000, 2001 John Wilson (tug@wilson.co.uk). All rights reserved. Redistribution and use in source and
 * binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution. All advertising
 * materials mentioning features or use of this software must display the following acknowledgement: This product
 * includes software developed by John Wilson. The name of John Wilson may not be used to endorse or promote products
 * derived from this software without specific prior written permission. THIS SOFTWARE IS PROVIDED BY JOHN WILSON ``AS
 * IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JOHN WILSON BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 */

import org.xml.sax.*;
import uk.org.xml.sax.DocumentHandler;
import uk.org.xml.sax.Parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;


/**
 */
public class MinML implements Parser, Locator, DocumentHandler, ErrorHandler {

    private static final int endStartName = 0;
    private static final int emitStartElement = 1;
    private static final int emitEndElement = 2;
    private static final int possiblyEmitCharacters = 3;
    private static final int emitCharacters = 4;
    private static final int emitCharactersSave = 5;
    private static final int saveAttributeName = 6;
    private static final int saveAttributeValue = 7;
    private static final int startComment = 8;
    private static final int endComment = 9;
    private static final int incLevel = 10;
    private static final int decLevel = 11;
    private static final int startCDATA = 12;
    private static final int endCDATA = 13;
    private static final int processCharRef = 14;
    private static final int writeCdata = 15;
    private static final int exitParser = 16;
    private static final int parseError = 17;
    private static final int discardAndChange = 18;
    private static final int discardSaveAndChange = 19;
    private static final int saveAndChange = 20;
    private static final int change = 21;

    private static final int inSkipping = 0;
    public static final int inSTag = 1;
    public static final int inPossiblyAttribute = 2;
    public static final int inNextAttribute = 3;
    public static final int inAttribute = 4;
    public static final int inAttribute1 = 5;
    public static final int inAttributeValue = 6;
    public static final int inAttributeQuoteValue = 7;
    public static final int inAttributeQuotesValue = 8;
    public static final int inETag = 9;
    public static final int inETag1 = 10;
    public static final int inMTTag = 11;
    public static final int inTag = 12;
    public static final int inTag1 = 13;
    public static final int inPI = 14;
    public static final int inPI1 = 15;
    public static final int inPossiblySkipping = 16;
    public static final int inCharData = 17;
    public static final int inCDATA = 18;
    public static final int inCDATA1 = 19;
    public static final int inComment = 20;
    public static final int inDTD = 21;

    public MinML(final int initialBufferSize, final int bufferIncrement) {
        this.initialBufferSize = initialBufferSize;
        this.bufferIncrement = bufferIncrement;
    }

    public MinML() {
        this(256, 128);
    }

    private void parse(final Reader in) throws SAXException, IOException {
        final Vector attributeNames = new Vector();
        final Vector attributeValues = new Vector();

        final AttributeList attrs = new AttributeList() {

            public int getLength() {
                return attributeNames.size();
            }

            public String getName(final int i) {
                return (String) attributeNames.elementAt(i);
            }

            public String getType(final int i) {
                return "CDATA";
            }

            public String getValue(final int i) {
                return (String) attributeValues.elementAt(i);
            }

            public String getType(final String name) {
                return "CDATA";
            }

            public String getValue(final String name) {
                final int index = attributeNames.indexOf(name);

                return (index == -1) ? null : (String) attributeValues.elementAt(index);
            }
        };

        final MinMLBuffer buffer = new MinMLBuffer(in);
        int currentChar, charCount = 0;
        int level = 0;
        int mixedContentLevel = -1;
        String elementName = null;
        String state = operands[inSkipping];

        this.lineNumber = 1;
        this.columnNumber = 0;

        try {
            while (true) {
                charCount++;

                //
                // this is to try and make the loop a bit faster
                // currentChar = buffer.read(); is simpler but is a bit slower.
                //
                currentChar = (buffer.nextIn == buffer.lastIn) ? buffer.read() : buffer.chars[buffer.nextIn++];

                final int transition;

                if (currentChar > ']') {
                    transition = state.charAt(14);
                } else {
                    final int charClass = charClasses[currentChar + 1];

                    if (charClass == -1)
                        fatalError("Document contains illegal control character with value " + currentChar,
                                this.lineNumber,
                                this.columnNumber);

                    if (charClass == 12) {
                        if (currentChar == '\r') {
                            currentChar = '\n';
                            charCount = -1;
                        }

                        if (currentChar == '\n') {
                            if (charCount == 0) continue; // preceeded by '\r' so ignore

                            if (charCount != -1) charCount = 0;

                            this.lineNumber++;
                            this.columnNumber = 0;
                        }
                    }

                    transition = state.charAt(charClass);
                }

                this.columnNumber++;

                final String operand = operands[transition >>> 8];

                switch (transition & 0XFF) {
                    case endStartName:
                        // end of start element name
                        elementName = buffer.getString();
                        if (currentChar != '>' && currentChar != '/') break; // change state to operand
                        // drop through to emit start element (we have no attributes)

                    case emitStartElement:
                        // emit start element

                        final Writer newWriter = this.extDocumentHandler.startElement(elementName,
                                attrs,
                                (this.tags.empty()) ? this.extDocumentHandler.startDocument(buffer)
                                        : buffer.getWriter());

                        buffer.pushWriter(newWriter);
                        this.tags.push(elementName);

                        attributeValues.removeAllElements();
                        attributeNames.removeAllElements();

                        if (mixedContentLevel != -1) mixedContentLevel++;

                        if (currentChar != '/') break; // change state to operand

                        // <element/> drop through

                    case emitEndElement:
                        // emit end element

                        try {
                            final String begin = (String) this.tags.pop();

                            buffer.popWriter();
                            elementName = buffer.getString();

                            if (currentChar != '/' && !elementName.equals(begin)) {
                                fatalError("end tag </" + elementName + "> does not match begin tag <" + begin + ">",
                                        this.lineNumber,
                                        this.columnNumber);
                            } else {
                                this.documentHandler.endElement(begin);

                                if (this.tags.empty()) {
                                    this.documentHandler.endDocument();
                                    return;
                                }
                            }
                        }
                        catch (final EmptyStackException e) {
                            fatalError("end tag at begining of document", this.lineNumber, this.columnNumber);
                        }

                        if (mixedContentLevel != -1) --mixedContentLevel;

                        break; // change state to operand

                    case emitCharacters:
                        // emit characters

                        buffer.flush();
                        break; // change state to operand

                    case emitCharactersSave:
                        // emit characters and save current character

                        if (mixedContentLevel == -1) mixedContentLevel = 0;

                        buffer.flush();

                        buffer.saveChar((char) currentChar);

                        break; // change state to operand

                    case possiblyEmitCharacters:
                        // write any skipped whitespace if in mixed content

                        if (mixedContentLevel != -1) buffer.flush();
                        break; // change state to operand

                    case saveAttributeName:
                        // save attribute name

                        attributeNames.addElement(buffer.getString());
                        break; // change state to operand

                    case saveAttributeValue:
                        // save attribute value

                        attributeValues.addElement(buffer.getString());
                        break; // change state to operand

                    case startComment:
                        // change state if we have found "<!--"

                        if (buffer.read() != '-') continue; // not "<!--"

                        break; // change state to operand

                    case endComment:
                        // change state if we find "-->"

                        if ((currentChar = buffer.read()) == '-') {
                            // deal with the case where we might have "------->"
                            while ((currentChar = buffer.read()) == '-')
                                ;

                            if (currentChar == '>') break; // end of comment, change state to operand
                        }

                        continue; // not end of comment, don't change state

                    case incLevel:

                        level++;

                        break;

                    case decLevel:

                        if (level == 0) break; // outer level <> change state

                        level--;

                        continue; // in nested <>, don't change state

                    case startCDATA:
                        // change state if we have found "<![CDATA["

                        if (buffer.read() != 'C') continue; // don't change state
                        if (buffer.read() != 'D') continue; // don't change state
                        if (buffer.read() != 'A') continue; // don't change state
                        if (buffer.read() != 'T') continue; // don't change state
                        if (buffer.read() != 'A') continue; // don't change state
                        if (buffer.read() != '[') continue; // don't change state
                        break; // change state to operand

                    case endCDATA:
                        // change state if we find "]]>"

                        if ((currentChar = buffer.read()) == ']') {
                            // deal with the case where we might have "]]]]]]]>"
                            while ((currentChar = buffer.read()) == ']')
                                buffer.write(']');

                            if (currentChar == '>') break; // end of CDATA section, change state to operand

                            buffer.write(']');
                        }

                        buffer.write(']');
                        buffer.write(currentChar);
                        continue; // not end of CDATA section, don't change state

                    case processCharRef:
                        // process character entity

                        int crefState = 0;

                        currentChar = buffer.read();

                        while (true) {
                            if ("#amp;&pos;'quot;\"gt;>lt;<".charAt(crefState) == currentChar) {
                                crefState++;

                                if (currentChar == ';') {
                                    buffer.write("#amp;&pos;'quot;\"gt;>lt;<".charAt(crefState));
                                    break;

                                } else if (currentChar == '#') {
                                    final int radix;

                                    currentChar = buffer.read();

                                    if (currentChar == 'x') {
                                        radix = 16;
                                        currentChar = buffer.read();
                                    } else {
                                        radix = 10;
                                    }

                                    int charRef = Character.digit((char) currentChar, radix);

                                    while (true) {
                                        currentChar = buffer.read();

                                        final int digit = Character.digit((char) currentChar, radix);

                                        if (digit == -1) break;

                                        charRef = (char) ((charRef * radix) + digit);
                                    }

                                    if (currentChar == ';' && charRef != -1) {
                                        buffer.write(charRef);
                                        break;
                                    }

                                    fatalError("invalid Character Entitiy", this.lineNumber, this.columnNumber);
                                } else {
                                    currentChar = buffer.read();
                                }
                            } else {
                                crefState = ("\u0001\u000b\u0006\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff" +
                                        // # a m p ; & p o s ; '
                                        // 0 1 2 3 4 5 6 7 8 9 a
                                        "\u0011\u00ff\u00ff\u00ff\u00ff\u00ff\u0015\u00ff\u00ff\u00ff"
                                        +
                                        // q u o t ; " g t ; >
                                        // b b d e f 10 11 12 13 14
                                        "\u00ff\u00ff\u00ff").charAt(crefState);
                                // l t ;
                                // 15 16 17

                                if (crefState == 255)
                                    fatalError("invalid Character Entitiy", this.lineNumber, this.columnNumber);
                            }
                        }

                        break;

                    case parseError:
                        // report fatal error

                        fatalError(operand, this.lineNumber, this.columnNumber);
                        // drop through to exit parser

                    case exitParser:
                        // exit parser

                        return;

                    case writeCdata:
                        // write character data
                        // this will also write any skipped whitespace

                        buffer.write(currentChar);
                        break; // change state to operand

                    case discardAndChange:
                        // throw saved characters away and change state

                        buffer.reset();
                        break; // change state to operand

                    case discardSaveAndChange:
                        // throw saved characters away, save character and change state

                        buffer.reset();
                        // drop through to save character and change state

                    case saveAndChange:
                        // save character and change state

                        buffer.saveChar((char) currentChar);
                        break; // change state to operand

                    case change:
                        // change state to operand

                        break; // change state to operand
                }

                state = operand;
            }
        }
        catch (final IOException e) {
            this.errorHandler.fatalError(new SAXParseException(e.getMessage(), null, null, this.lineNumber, this.columnNumber, e));
        }
        finally {
            this.errorHandler = this;
            this.documentHandler = this.extDocumentHandler = this;
            this.tags.removeAllElements();
        }
    }

    public void parse(final InputSource source) throws SAXException, IOException {
        if (source.getCharacterStream() != null) parse(source.getCharacterStream());
        else if (source.getByteStream() != null) parse(new InputStreamReader(source.getByteStream(), "UTF-8"));
        // else
        // parse(new InputStreamReader(new URL(source.getSystemId()).openStream()));
    }

    public void parse(final String systemId) throws SAXException, IOException {
        parse(new InputSource(systemId));
    }

    // public void setLocale(final Locale locale) throws SAXException {
    // throw new SAXException("Not supported");
    // }

    public void setEntityResolver(final EntityResolver resolver) {
        // not supported
    }

    public void setDTDHandler(final DTDHandler handler) {
        // not supported
    }

    /**
     * @param handler the documentHandler to set
     * @uml.property name="documentHandler"
     */
    public void setDocumentHandler(final org.xml.sax.DocumentHandler handler) {
        this.documentHandler = (handler == null) ? this : handler;
        this.extDocumentHandler = this;
    }

    /**
     * @param handler The documentHandler to set.
     */
    public void setDocumentHandler(final DocumentHandler handler) {
        this.documentHandler = this.extDocumentHandler = (handler == null) ? this : handler;
        this.documentHandler.setDocumentLocator(this);
    }

    /**
     * @param handler The errorHandler to set.
     * @uml.property name="errorHandler"
     */
    public void setErrorHandler(final ErrorHandler handler) {
        this.errorHandler = (handler == null) ? this : handler;
    }

    public void setDocumentLocator(final Locator locator) {
    }

    public void startDocument() throws SAXException {
    }

    public Writer startDocument(final Writer writer) throws SAXException {
        this.documentHandler.startDocument();
        return writer;
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(final String name, final AttributeList attributes) throws SAXException {
    }

    public Writer startElement(final String name, final AttributeList attributes, final Writer writer) throws SAXException {
        this.documentHandler.startElement(name, attributes);
        return writer;
    }

    public void endElement(final String name) throws SAXException {
    }

    public void characters(final char ch[], final int start, final int length) throws SAXException {
    }

    public void ignorableWhitespace(final char ch[], final int start, final int length) throws SAXException {
    }

    public void processingInstruction(final String target, final String data) throws SAXException {
    }

    public void warning(final SAXParseException e) throws SAXException {
    }

    public void error(final SAXParseException e) throws SAXException {
    }

    public void fatalError(final SAXParseException e) throws SAXException {
        throw e;
    }

    public String getPublicId() {
        return "";
    }

    public String getSystemId() {
        return "";
    }

    /**
     * @return Returns the lineNumber.
     * @uml.property name="lineNumber"
     */
    public int getLineNumber() {
        return this.lineNumber;
    }

    /**
     * @return Returns the columnNumber.
     * @uml.property name="columnNumber"
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    private void fatalError(final String msg, final int lineNumber, final int columnNumber) throws SAXException {
        this.errorHandler.fatalError(new SAXParseException(msg, null, null, lineNumber, columnNumber));
    }

    /**
     */
    private class MinMLBuffer extends Writer {

        public MinMLBuffer(final Reader in) {
            this.in = in;
        }

        public void close() throws IOException {
            flush();
        }

        public void flush() throws IOException {
            try {
                _flush();
                if (writer != this) writer.flush();
            }
            finally {
                flushed = true;
            }
        }

        public void write(final int c) throws IOException {
            written = true;
            chars[count++] = (char) c;
        }

        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            written = true;
            System.arraycopy(cbuf, off, chars, count, len);
            count += len;
        }

        public void saveChar(final char c) {
            written = false;
            chars[count++] = c;
        }

        public void pushWriter(final Writer writer) {
            MinML.this.tags.push(this.writer);

            this.writer = (writer == null) ? this : writer;

            flushed = written = false;
        }

        /**
         * @return Returns the writer.
         * @uml.property name="writer"
         */
        public Writer getWriter() {
            return writer;
        }

        public void popWriter() throws IOException {
            try {
                if (!flushed && writer != this) writer.flush();
            }
            finally {
                writer = (Writer) MinML.this.tags.pop();
                flushed = written = false;
            }
        }

        public String getString() {
            final String result = new String(chars, 0, count);

            count = 0;
            return result;
        }

        public void reset() {
            count = 0;
        }

        public int read() throws IOException {
            if (nextIn == lastIn) {
                if (count != 0) {
                    if (written) {
                        _flush();
                    } else if (count >= (chars.length - MinML.this.bufferIncrement)) {
                        final char[] newChars = new char[chars.length + MinML.this.bufferIncrement];

                        System.arraycopy(chars, 0, newChars, 0, count);
                        chars = newChars;
                    }
                }

                final int numRead = in.read(chars, count, chars.length - count);

                if (numRead == -1) return -1;

                nextIn = count;
                lastIn = count + numRead;
            }

            return chars[nextIn++];
        }

        private void _flush() throws IOException {
            if (count != 0) {
                try {
                    if (writer == this) {
                        try {
                            MinML.this.documentHandler.characters(chars, 0, count);
                        }
                        catch (final SAXException e) {
                            throw new IOException(e.toString());
                        }
                    } else {
                        writer.write(chars, 0, count);
                    }
                }
                finally {
                    count = 0;
                }
            }
        }

        private int nextIn = 0;
        private int lastIn = 0;
        private char[] chars = new char[MinML.this.initialBufferSize];
        private final Reader in;
        private int count = 0;
        private Writer writer = this;
        private boolean flushed = false;
        private boolean written = false;
    }

    private DocumentHandler extDocumentHandler = this;
    private org.xml.sax.DocumentHandler documentHandler = this;
    private ErrorHandler errorHandler = this;
    private final Stack tags = new Stack();
    private int lineNumber = 1;
    private int columnNumber = 0;
    private final int initialBufferSize;
    private final int bufferIncrement;

    private static final byte[] charClasses = {
            // EOF
            13,
            // \t \n \r
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            12,
            12,
            -1,
            -1,
            12,
            -1,
            -1,
            //
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            // SP ! " # $ % & ' ( ) * + , - . /
            12,
            8,
            7,
            14,
            14,
            14,
            3,
            6,
            14,
            14,
            14,
            14,
            14,
            11,
            14,
            2,
            // 0 1 2 3 4 5 6 7 8 9 : ; < = > ?
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            0,
            5,
            1,
            4,
            //
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            // [ \ ]
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            14,
            9,
            14,
            10};

    private static final String[] operands = {
            "\u0d15\u1611\u1611\u1611\u1611\u1611\u1611\u1611\u1611\u1611\u1611\u1611\u0015\u0010\u1611",
            "\u1711\u1000\u0b00\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u0114\u0200\u1811\u0114",
            "\u1711\u1001\u0b01\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u0215\u1811\u0414",
            "\u1711\u1001\u0b01\u1711\u1911\u1911\u1911\u1911\u1911\u1911\u1911\u1911\u0315\u1811\u0414",
            "\u1911\u1911\u1911\u1911\u1911\u0606\u1911\u1911\u1911\u1911\u1911\u0414\u0515\u1811\u0414",
            "\u1911\u1911\u1911\u1911\u1911\u0606\u1911\u1911\u1911\u1911\u1911\u1911\u0515\u1811\u1911",
            "\u1a11\u1a11\u1a11\u1a11\u1a11\u1a11\u0715\u0815\u1a11\u1a11\u1a11\u1a11\u0615\u1811\u1a11",
            "\u0714\u0714\u0714\u070e\u0714\u0714\u0307\u0714\u0714\u0714\u0714\u0714\u0714\u1811\u0714",
            "\u0814\u0814\u0814\u080e\u0814\u0814\u0814\u0307\u0814\u0814\u0814\u0814\u0814\u1811\u0814",
            "\u1711\u1002\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u0914\u0915\u1811\u0914",
            "\u1b11\u1b11\u0904\u1b11\u1b11\u1b11\u1b11\u1b11\u1215\u1b11\u1b11\u1b11\u1b11\u1811\u0105",
            "\u1711\u1012\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1711\u1811\u1711",
            "\u1711\u1c11\u0912\u1711\u0e12\u1711\u1711\u1711\u1212\u1711\u1711\u1711\u1711\u1811\u0113",
            "\u1711\u1c11\u0912\u1711\u0e12\u1711\u1711\u1711\u1212\u1711\u1711\u1711\u1711\u1811\u0113",
            "\u0e15\u0e15\u0e15\u0e15\u0f15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u1811\u0e15",
            "\u0e15\u0015\u0e15\u0e15\u0f15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u0e15\u1811\u0e15",
            "\u0c03\u110f\u110f\u110e\u110f\u110f\u110f\u110f\u110f\u110f\u110f\u110f\u1014\u1811\u110f",
            "\u0a15\u110f\u110f\u110e\u110f\u110f\u110f\u110f\u110f\u110f\u110f\u110f\u110f\u1811\u110f",
            "\u1d11\u1d11\u1d11\u1d11\u1d11\u1d11\u1d11\u1d11\u1d11\u130c\u1d11\u1408\u1d11\u1811\u1515",
            "\u130f\u130f\u130f\u130f\u130f\u130f\u130f\u130f\u130f\u130f\u110d\u130f\u130f\u1811\u130f",
            "\u1415\u1415\u1415\u1415\u1415\u1415\u1415\u1415\u1415\u1415\u1415\u0009\u1415\u1811\u1415",
            "\u150a\u000b\u1515\u1515\u1515\u1515\u1515\u1515\u1515\u1515\u1515\u1515\u1515\u1811\u1515",
            "expected Element",
            "unexpected character in tag",
            "unexpected end of file found",
            "attribute name not followed by '='",
            "invalid attribute value",
            "expecting end tag",
            "empty tag",
            "unexpected character after <!"};
}
