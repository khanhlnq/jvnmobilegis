// Copyright 2003 Nokia Corporation.
//
// THIS SOURCE CODE IS PROVIDED 'AS IS', WITH NO WARRANTIES WHATSOEVER,
// EXPRESS OR IMPLIED, INCLUDING ANY WARRANTY OF MERCHANTABILITY, FITNESS
// FOR ANY PARTICULAR PURPOSE, OR ARISING FROM A COURSE OF DEALING, USAGE
// OR TRADE PRACTICE, RELATING TO THE SOURCE CODE OR ANY WARRANTY OTHERWISE
// ARISING OUT OF ANY PROPOSAL, SPECIFICATION, OR SAMPLE AND WITH NO
// OBLIGATION OF NOKIA TO PROVIDE THE LICENSEE WITH ANY MAINTENANCE OR
// SUPPORT. FURTHERMORE, NOKIA MAKES NO WARRANTY THAT EXERCISE OF THE
// RIGHTS GRANTED HEREUNDER DOES NOT INFRINGE OR MAY NOT CAUSE INFRINGEMENT
// OF ANY PATENT OR OTHER INTELLECTUAL PROPERTY RIGHTS OWNED OR CONTROLLED
// BY THIRD PARTIES
//
// Furthermore, information provided in this source code is preliminary,
// and may be changed substantially prior to final release. Nokia Corporation
// retains the right to make changes to this source code at
// any time, without notice. This source code is provided for informational
// purposes only.
//
// Nokia and Nokia Connecting People are registered trademarks of Nokia
// Corporation.
// Java and all Java-based marks are trademarks or registered trademarks of
// Sun Microsystems, Inc.
// Other product and company names mentioned herein may be trademarks or
// trade names of their respective owners.
//
// A non-exclusive, non-transferable, worldwide, limited license is hereby
// granted to the Licensee to download, print, reproduce and modify the
// source code. The licensee has the right to market, sell, distribute and
// make available the source code in original or modified form only when
// incorporated into the programs developed by the Licensee. No other
// license, express or implied, by estoppel or otherwise, to any other
// intellectual property rights is granted herein.

/*
 * $Id: UIController.java 37 2007-07-24 01:51:17Z khanh.lnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/UIController.java $
 * $Author: khanh.lnq $
 * $Revision: 37 $
 * $Date: 2007-07-24 08:51:17 +0700 (Tue, 24 Jul 2007) $
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
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 17 Aug 2007
 */

package org.javavietnam.gis.client.midp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class HttpUtils {
    private final static char[] hexdigits = new char[16];

    // Base-64 encoding is defined in http://RFC.net/rfc1521.html
    private final static char[] alphabet = new char[64];

    static {
        // fill hexdigits with {0123456789ABCDEF}
        for (int c = '0', i = 0; c <= '9'; c++, i++) {
            hexdigits[i] = (char) c;
        }
        for (int c = 'A', i = 10; c <= 'F'; c++, i++) {
            hexdigits[i] = (char) c;
        }
        // fill base64 alphabet
        for (int c = 'A', i = 0; c <= 'Z'; c++, i++) {
            alphabet[i] = (char) c;
        }
        for (int c = 'a', i = 26; c <= 'z'; c++, i++) {
            alphabet[i] = (char) c;
        }
        for (int c = '0', i = 52; c <= '9'; c++, i++) {
            alphabet[i] = (char) c;
        }
        alphabet[62] = (char) '+';
        alphabet[63] = (char) '/';
    }


    private HttpUtils() {
    }


    // encodes the string in URL format as indicated in RFC 1738
    static String encodeURL(String url) {
        int maxBytesPerChar = 10;
        StringBuffer result = new StringBuffer();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(maxBytesPerChar);
        OutputStreamWriter writer = null;
        // UTF-8 encoding is recommended by W3C
        try {
            writer = new OutputStreamWriter(buffer, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            // use default encoding then
            writer = new OutputStreamWriter(buffer);
        }
        int size = url.length();

        for (int i = 0; i < size; i++) {
            int current = (int) url.charAt(i);
            // print non changed chars as normal
            if ((current >= 'a' && current <= 'z')
                    || (current >= 'A' && current <= 'Z')
                    || (current >= '0' && current <= '9')
                    || current == '.' || current == '_' || current == '-' || current == '*') {
                result.append((char) current);
            } else if (current == ' ') {
                // space is a special case
                result.append('+');
            } else {
                // try to write to the ByteArrayStream
                try {
                    writer.write(current);
                    writer.flush();
                }
                catch (IOException e) {
                    buffer.reset();
                    continue;
                }
                byte[] array = buffer.toByteArray();
                for (int j = 0; j < array.length; j++) {
                    byte currentByte = array[j];
                    int low = (int) (currentByte & 0x0F);
                    int high = (int) ((currentByte & 0xF0) >> 4);

                    result.append('%');
                    result.append(hexdigits[high]);
                    result.append(hexdigits[low]);
                }
                buffer.reset();
            }
        }
        return result.toString();
    }


    // Each 3 chars of input is encoded as 4 chars of output from above
    // 64-char alphabet. It's assumed that chars are 8-bit values.
    // If length is multiple of 3, no problem.
    // If length is multiple of 3 + 1, last output char is zero-completed
    // and two '=' characters are appended.
    // If length is multiple of 3 + 2, last output char is zero-completed
    // and one '=' character is appended
    public static String base64Encode(String str) {
        StringBuffer buf = new StringBuffer((str.length() + 2) / 3 * 4);
        int completeGroupChars = (str.length() / 3) * 3;
        int extraChars = str.length() % 3;

        // first write complete groups of 3 chars
        int i;
        for (i = 0; i < completeGroupChars; i += 3) {
            int group = ((((int) str.charAt(i)) & 0xFF) << 16) |
                    ((((int) str.charAt(i + 1)) & 0xFF) << 8) |
                    (((int) str.charAt(i + 2)) & 0xFF);

            buf.append(alphabet[(group >> 18) & 63]);
            buf.append(alphabet[(group >> 12) & 63]);
            buf.append(alphabet[(group >> 6) & 63]);
            buf.append(alphabet[group & 63]);
        }

        if (extraChars == 2) {
            int group = ((((int) str.charAt(i)) & 0xFF) << 16) |
                    ((((int) str.charAt(i + 1)) & 0xFF) << 8);
            buf.append(alphabet[(group >> 18) & 63]);
            buf.append(alphabet[(group >> 12) & 63]);
            buf.append(alphabet[(group >> 6) & 63]);
            buf.append('=');
        } else if (extraChars == 1) {
            int group = (((int) str.charAt(i)) & 0xFF) << 16;
            buf.append(alphabet[(group >> 18) & 63]);
            buf.append(alphabet[(group >> 12) & 63]);
            buf.append('=');
            buf.append('=');
        }
        return buf.toString();
    }

}

