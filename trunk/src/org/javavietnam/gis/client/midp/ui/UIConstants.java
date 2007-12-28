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
package org.javavietnam.gis.client.midp.ui;

import henson.midp.Float;

/**
 * @author khanhlnq
 */
public final class UIConstants {

    // Contants value used among the app
    public static final int SPLASH_TIMEOUT = 2000;
    
    // Icon index
    public static final byte ICON_IDX_SPLASH = 0;

    // Unit of these values is pixel
    // Scale = 20%
    public static final Float SCALE = new Float(20).Div(new Float(100));
    public static final Float BEST_SCALE = new Float(30000);
    public static final int CURSOR_MOVEMENT = 5;
    // Message constants
    public static final int MOBILEGIS_CLIENT = 0;
    public static final int MY_SETTINGS_TITLE = 1;
    public static final int MAIN_MENU = 2;
    public static final int EXIT = 3;
    public static final int ERROR = 4;
    public static final int BACK = 5;
    public static final int NEXT = 6;
    public static final int CANCEL = 7;
    public static final int WHATS_NEW = 8;
    public static final int ABOUT = 9;
    public static final int CONFIRM = 10;
    public static final int DONE = 11;
    public static final int SAVE = 12;
    public static final int PROCESSING = 13;
    public static final int STOP = 14;
    public static final int VIEW_MAP_TITLE = 15;
    public static final int GET_MAP_WMS_ERROR = 16;
    public static final int REFRESH = 17;
    public static final int GET_CAPABILITIES_TITLE = 18;
    public static final int GET_CAPABILITIES_WMS_ERROR = 19;
    public static final int LAYER_LIST_TITLE = 20;
    public static final int GETMAP = 21;
    // public static final int FIND_PATH_TITLE = 22;
    public static final int SERVER_UI_TITLE = 23;
    public static final int FEATUREINFO_UI_TITLE = 24;
    public static final int FEATUREINFO_RESULT_STR = 25;
    // public static final int FINDPATH_RESULT_STR = 26;
    // public static final int VIEW_PATH = 27;
    // public static final int PATH_THROUGH = 28;
    public static final int STREETS = 29;
    public static final int NOTICE = 30;
    // public static final int FINDPATH_ERROR = 31;
    public static final int HELP_TITLE = 32;
    public static final int KEYPADS = 33;
    public static final int HELP_STRING = 34;
    public static final int COPYRIGHT = 35;
    public static final int LAYER_SELECT_TITLE = 36;
    public static final int CHOOSE_LAYER_FOR_INFO = 37;
    public static final int ASK_NEXT_TIME = 38;
    public static final int YES = 39;
    public static final int NO = 40;
    // public static final int VIEWPATH_ERROR = 41;
    public static final int UNKNOWN_ERROR = 42;
    public static final int ZOOM_IN_CMD = 43;
    public static final int ZOOM_OUT_CMD = 44;
    public static final int RESET_VIEW_CMD = 45;
    public static final int RECENTER_CMD = 46;
    // public static final int FIND_PATH_CMD = 47;
    public static final int HELP_CMD = 48;
    public static final int SCALE_STR = 49;
    public static final int GET_CAPABILITIES_CMD = 50;
    // public static final int SEARCH_FEATURE_UI_TITLE = 51;
    public static final int KEYWORD = 52;
//    public static final int SEARCH = 53;
//    public static final int SEARCH_FEATURE_RESULT_UI_TITLE = 54;
//    public static final int VIEW_ON_MAP = 55;
//    public static final int NO_FEATURE_FOUND = 56;
    public static final int LANGUAGE = 57;
    public static final int ENGLISH = 58;
    public static final int VIETNAMESE = 59;
    public static final int PREFERENCES = 60;
    public static final int WEBGIS_URL = 61;
    // public static final int FINDPATH_LAYER = 62;
    public static final int CHECK_UPDATE = 63;
    public static final int UPDATE_AVAILABE = 64;
    public static final int NO_UPDATE_AVAILABE = 65;
    public static final int MAPSERVER_01_NAME = 66;
    public static final int MAPSERVER_01_URL = 67;
    public static final int MAPSERVER_02_NAME = 68;
    public static final int MAPSERVER_02_URL = 69;
    public static final int MAPSERVER_03_NAME = 70;
    public static final int MAPSERVER_03_URL = 71;
    public static final int HAS_TOUCHSCREEN = 72;
    public static final int AUTH_REQUIRED = 73;
    public static final int USER = 74;
    public static final int PASSWORD = 75;
    public static final int MUST_GIVE_USER_PWD = 76;
    public static final int ENTER_LOGIN_INFO = 77;
    public static final int OK = 78;
    public static final int GET_FEATURE_INFO = 79;
    public static final int CONF_WEBGIS_URL = 80;
    public static final int CONF_UPDATE_URL = 81;
    public static final int CONF_WMS_SERVER_URL = 82;
    public static final int DUTCH = 83;
    public static final int DOWNLOADED_DATA_SIZE = 84;

    private UIConstants() {
    }
}
