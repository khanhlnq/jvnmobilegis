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

package org.javavietnam.gis.shared.midp.model;

/**
 * @author khanhlnq
 */
public class Credentials {
    protected String credentials = null;
    protected String username = null, password = null;

    public Credentials() {
    }

    public Credentials(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
