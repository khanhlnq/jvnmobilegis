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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Encode and decode byte arrays (typically from binary to 7-bit ASCII
 * encodings).
 */
public interface Encoder {
    int encode(byte[] data, int off, int length, OutputStream out)
            throws IOException;

    int decode(byte[] data, int off, int length, OutputStream out)
            throws IOException;

    int decode(String data, OutputStream out) throws IOException;
}
