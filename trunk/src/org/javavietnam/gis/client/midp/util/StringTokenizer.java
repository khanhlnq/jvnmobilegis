/**
 * S�rgio Est�v�o
 * MIDP Adventures
 * www.sergioestevao.com/midp
 */

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
 * @Date Created: 17 July 2007
 */
package org.javavietnam.gis.client.midp.util;

public class StringTokenizer {

    private String msg;

    private String delimiters;

    public StringTokenizer(String aMsg, String aDelimiters) {
        setString(aMsg);
        setTokens(aDelimiters);
    }

    public void setString(String aMsg) {
        msg = aMsg;
    }

    public void setTokens(String aDelimiters) {
        delimiters = aDelimiters;
    }

    public boolean hasMoreTokens() {
        return msg != null;
    }

    // return next token (filters delimiter)
    public String nextToken() {
        String t = msg.substring(0, getPos());
        if (!t.equals(msg)) {
            msg = msg.substring(getPos() + 1);
        } else {
            msg = null;
        }
        return t;
    }

    // return position of next delimiter, -1 means no more delimiters
    // available
    private int getPos() {

        int currentPos = msg.length();
        int newPos = -1;
        for (int i = 0; i < delimiters.length(); i++) {
            newPos = msg.indexOf(delimiters.charAt(i));
            if (newPos != -1) {
                if (currentPos != -1) {
                    currentPos = Math.min(currentPos, newPos);
                } else {
                    currentPos = newPos;
                }
            }
        }
        return currentPos;
    }

}
