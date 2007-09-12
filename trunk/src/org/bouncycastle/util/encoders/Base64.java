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
 * @Date Created: 31 Aug 2007
 */

package org.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author       khanhlnq
 */
public class Base64 {
    private static final Encoder encoder = new Base64Encoder();

    /**
     * encode the input data producing a base 64 encoded byte array.
     * 
     * @return a byte array containing the base 64 encoded data.
     */
    public static byte[] encode(byte[] data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        try {
            encoder.encode(data, 0, data.length, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * Encode the byte data to base 64 writing it to the given output stream.
     * 
     * @return the number of bytes produced.
     */
    public static int encode(byte[] data, OutputStream out) throws IOException {
        return encoder.encode(data, 0, data.length, out);
    }

    /**
     * Encode the byte data to base 64 writing it to the given output stream.
     * 
     * @return the number of bytes produced.
     */
    public static int encode(byte[] data, int off, int length, OutputStream out)
            throws IOException {
        return encoder.encode(data, off, length, out);
    }

    /**
     * decode the base 64 encoded input data. It is assumed the input data is
     * valid.
     * 
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(byte[] data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        try {
            encoder.decode(data, 0, data.length, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * decode the base 64 encoded String data - whitespace will be ignored.
     * 
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(String data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        try {
            encoder.decode(data, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * decode the base 64 encoded String data writing it to the given output
     * stream, whitespace characters will be ignored.
     * 
     * @return the number of bytes produced.
     */
    public static int decode(String data, OutputStream out) throws IOException {
        return encoder.decode(data, out);
    }
}
