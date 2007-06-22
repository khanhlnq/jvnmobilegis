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

package org.javavietnam.gis.shared.midp;

import java.io.*;
import java.util.Vector;


/**
 */
public class IndexedResourceBundle {

    private static IndexedResourceBundle instance = null;
    private String locale;
    private String[] resources;

    public IndexedResourceBundle(String locale, String[] resources) {
        this.locale = locale;
        this.resources = resources;

        return;
    }

    public String getString(int resourceId) {
        return (resourceId >= 0 && resourceId < resources.length) ? resources[resourceId] : null;
    }

    /**
    * @return Returns the locale.
    * @uml.property name="locale"
     */
    public String getLocale() {
        return locale;
    }

    public int size() {
        return resources.length;
    }

    public void serialize(DataOutputStream out) throws ApplicationException {
        try {
            out.writeUTF(locale);
            out.writeInt(resources.length);

            for (int i = 0; i != resources.length; i++) {
                out.writeUTF(resources[i]);
            }
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
    }

    public static IndexedResourceBundle deserialize(DataInputStream in) throws ApplicationException {
        try {
            String locale = in.readUTF();
            String[] resources = new String[in.readInt()];

            for (int i = 0; i != resources.length; i++) {
                resources[i] = in.readUTF();
            }

            return new IndexedResourceBundle(locale, resources);
        }
        catch (IOException ioe) {
            throw new ApplicationException(ioe);
        }
    }

    public static IndexedResourceBundle getBundleFromPropertyFile(String locale, InputStream in) throws IOException {
        Vector resourcesVector = new Vector();
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();

            int c;
            int index = 0;

            while ((c = in.read()) != -1) {
                if (c == '\n' || c == '\r') {
                    byte[] bytes = out.toByteArray();
                    String s = new String(bytes, "UTF-8");
                    int i = s.indexOf('=');

                    if (i != -1) {
                        if (s.substring(0, i).equals(String.valueOf(index))) {
                            resourcesVector.addElement(s.substring(i + 1));

                            index++;
                        } else {
                            // FIXME - Throw an exception.
                        }
                    }

                    out.reset();
                }
                // ^ is new line symbol
                else if (c == '^') {
                    out.write('\n');
                } else {
                    out.write(c);
                }
            }
        }
        finally {
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }
        }

        String[] resources = new String[resourcesVector.size()];

        resourcesVector.copyInto(resources);

        return new IndexedResourceBundle(locale, resources);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Contents of resource bundle: " + locale + "\n");

        for (int i = 0; i != resources.length; i++) {
            buffer.append(i);
            buffer.append('=');
            buffer.append(resources[i]);
            buffer.append('\n');
        }

        return buffer.toString();
    }

}
