/*
 * $Id: UIController.java 79 2007-08-31 06:56:15Z khanh.lnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/UIController.java $
 * $Author: khanh.lnq $
 * $Revision: 79 $
 * $Date: 2007-08-31 13:56:15 +0700 (Fri, 31 Aug 2007) $
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
 * @Date Created: 22 Jun 2007
 */

package org.bouncycastle.util.encoders;

/**
 * Convert binary data to and from UrlBase64 encoding. This is identical to
 * Base64 encoding, except that the padding character is "." and the other
 * non-alphanumeric characters are "-" and "_" instead of "+" and "/".
 * <p>
 * The purpose of UrlBase64 encoding is to provide a compact encoding of binary
 * data that is safe for use as an URL parameter. Base64 encoding does not
 * produce encoded values that are safe for use in URLs, since "/" can be
 * interpreted as a path delimiter; "+" is the encoded form of a space; and "="
 * is used to separate a name from the corresponding value in an URL parameter.
 */
public class UrlBase64Encoder extends Base64Encoder {
    public UrlBase64Encoder() {
        encodingTable[encodingTable.length - 2] = (byte) '-';
        encodingTable[encodingTable.length - 1] = (byte) '_';
        padding = (byte) '.';
        // we must re-create the decoding table with the new encoded values.
        initialiseDecodingTable();
    }
}
