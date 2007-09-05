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

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;

/**
 * @author khanhlnq
 */
public class SearchFeatureUI extends Form implements CommandListener,
        SearchFeatureParameter {

    private UIController uiController;
    private Command backCommand;
    private Command searchCommand;
    private TextField fWord;
    /**
     */
    private String webGISURL;
    /**
     */
    private int start = 0;

    /**
     */
    private Float[] boundingBox = new Float[4];

    /**
     * Creates a new instance of SearchUI
     * 
     * @param uiController
     */
    public SearchFeatureUI(UIController uiController) {
        super(uiController.getString(UIConstants.SEARCH_FEATURE_UI_TITLE));
        this.uiController = uiController;

        fWord = new TextField(uiController.getString(UIConstants.KEYWORD), "",
                50, TextField.ANY);
        uiController.vietSign.addCommands(fWord);
        append(fWord);

        searchCommand = new Command(uiController.getString(UIConstants.SEARCH),
                Command.SCREEN, 0);
        backCommand = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 5);
        addCommand(searchCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void initParam(Float[] latLonBoundingBox, String webGISURL) {
        System.arraycopy(latLonBoundingBox, 0, boundingBox, 0, 4);
        this.webGISURL = webGISURL;
        fWord.setString("");
        start = 0;
    }

    public String getKeyWord() {
        return fWord.getString();
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        } else if (command == searchCommand) {
            start = 0;
            uiController.searchFeatureRequested();
        } else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return the boundingBox
     */
    public Float[] getBoundingBox() {
        return boundingBox;
    }

    /**
     * @return the webGISURL
     */
    public String getWebGISURL() {
        return webGISURL;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

}
