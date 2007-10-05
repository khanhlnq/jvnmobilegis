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
 * @Date Created: 17 Aug 2007
 */

package org.javavietnam.gis.shared.midp.model;

/**
 * @author khanhlnq
 */
public class Credentials {
    /**
     * @uml.property name="credentials"
     */
    protected String credentials = null;
    /**
     * @uml.property name="username"
     */
    protected String username = null;
    /**
     * @uml.property name="password"
     */
    protected String password = null;

    public Credentials() {
    }

    public Credentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     * @return
     * @uml.property name="credentials"
     */
    public String getCredentials() {
        return credentials;
    }

    /**
     * @param credentials
     * @uml.property name="credentials"
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     * @return
     * @uml.property name="password"
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     * @uml.property name="password"
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     * @uml.property name="username"
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     * @uml.property name="username"
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
