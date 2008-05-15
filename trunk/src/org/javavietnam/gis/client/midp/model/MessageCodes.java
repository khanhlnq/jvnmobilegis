/*
 * Copyright 2001, 2002, 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Except for any files in PNG format (which are marked with the filename
 * extension ".png"), GIF format (which are marked with the filename
 * extension ".gif"), or JPEG format (which are marked with the filename
 * extension ".jpg"), redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 * - Redistribution of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * You may compile, use, perform and display the following files with
 * original Java Smart Ticket Sample Application code obtained from Sun
 * Microsystems, Inc. only:
 * - files in PNG format and having the ".png" extension
 * - files in GIF format and having the ".gif" extension
 * - files in JPEG format and having the ".jpg" extension
 * You may not modify or redistribute .png, .gif, or .jpg files in any
 * form, in whole or in part, by any means without prior written
 * authorization from Sun Microsystems, Inc. and its licensors, if any.
 * Neither the name of Sun Microsystems, Inc., the 'Java Smart Ticket
 * Sample Application', 'Java', 'Java'-based names, or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or maintenance
 * of any nuclear facility.
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
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.model;

public final class MessageCodes {

    public static final int ERROR_GENERAL = 0;
    public static final int ERROR_CANNOT_CONNECT = 1;
    public static final int NO_SELECTED_LAYER = 2;
    // public static final int NO_SELECTED_POINT = 3;
    public static final int ERROR_UNAUTHORIZED = 4;
    public static final int MISSING_CHALLENGE = 5;
    public static final int AUTH_SCHEME_NOT_BASIC = 6;
    public static final int AUTH_REALM_SYNTAX_ERROR = 7;
    public static final int ERROR_CERTIFICATE = 8;

    public static final int ERROR_BROKEN_HANDLER_CHAIN = 50;
    public static final int ERROR_OPERATION_INTERRUPTED = 52;

    // Confirm messages
    public static final int CONFIRM_SAVE_PREFERENCES = 9;
    
    // File System Browser
    public static final int ERROR_DOESNT_SUPPORT_JSR_75 = 10;
    public static final int ERROR_CAN_NOT_ACCESS_FILE = 11;
    public static final int ERROR_CAN_NOT_ACCESS_DIR = 12;
    public static final int ERROR_CAN_NOT_CREATE_FILE = 13;
    public static final int ERROR_CAN_NOT_CREATE_DIR = 14;
    public static final int ERROR_CAN_NOT_DELETE_NON_EMPTY_DIR = 15;
    public static final int ERROR_CAN_NOT_DELETE_FILE = 16;
    public static final int ERROR_CAN_NOT_DELETE_DIR = 17;
    public static final int ERROR_CAN_NOT_DELETE_UP_DIR = 18;
    public static final int ERROR_CAN_NOT_VIEW_DIR = 19;
    public static final int ERROR_CAN_NOT_VIEW_CUR_DIR = 20;
    public static final int ERROR_CAN_NOT_VIEW_PROPERTIES = 21;
    public static final int ERROR_CAN_NOT_VIEW_CONTENT = 22;
    public static final int ERROR_WRONG_FORMAT = 23;
    public static final int ERROR_CAN_NOT_SAVE_MAP_TO_DIR = 24;
    public static final int MISSING_NAME_INPUT = 25;
    public static final int ERROR_CAN_NOT_VIEW_UP_DIR_PROPS = 26;
    // private ErrorMessageCodes() {
    // }

}
