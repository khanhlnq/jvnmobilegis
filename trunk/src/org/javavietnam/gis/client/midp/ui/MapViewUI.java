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
 * You may contents not modify or redistribute .png, .gif, or .jpg files in any
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
package org.javavietnam.gis.client.midp.ui;

import henson.midp.Float;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import org.javavietnam.gis.shared.midp.model.MapFeature;
import org.javavietnam.gis.shared.midp.model.WMSRequestParameter;

/**
 * @author khanhlnq
 */
public class MapViewUI extends GameCanvas implements CommandListener,
    WMSRequestParameter {

    private static final int NO_ACTION = 0;
    private static final int MOVE_DOWN = 1;
    private static final int MOVE_LEFT = 2;
    private static final int MOVE_RIGHT = 3;
    private static final int MOVE_UP = 4;
    private static final int ZOOM_IN = 5;
    private static final int ZOOM_OUT = 6;

    private final UIController uiController;
    private final Command backCommand;
    private final Command zoomInCommand;
    private final Command zoomOutCommand;
    private final Command resetCommand;
    private final Command recenterCommand;
    // private Command findPathCommand;
    private final Command getFeatureInfoCommand;
    private final Command searchFeatureCommand;
    private final Command helpCommand;
    private final Command refreshCommand;
    private final Font smallFont = Font.getFont(Font.FACE_PROPORTIONAL,
        Font.STYLE_ITALIC, Font.SIZE_SMALL);
    /**
     * @uml.property name="cursorX"
     */
    private int cursorX;
    /**
     * @uml.property name="cursorY"
     */
    private int cursorY;
    // private boolean startPointSelected = false;
    // private boolean endPointSelected = false;
    private boolean isDragging = false;
    private int pointerStartX;
    private int pointerStartY;
    private int pointerEndX;
    private int pointerEndY;
    /**
     * @uml.property name="startPoint"
     * @uml.associationEnd multiplicity="(0 -1)"
     */
    // private Float[] startPoint = new Float[2];
    /**
     */
    // private Float[] endPoint = new Float[2];
    // private boolean isViewPath = false;
    private boolean isViewFeature = false;
    /**
     * @uml.property name="boundingBox"
     * @uml.associationEnd multiplicity="(0 -1)"
     */
    private Float[] boundingBox = new Float[4];
    private Float[] previousBoundingBox = new Float[4];

    private int previousAction = NO_ACTION;
    /**
     * @uml.property name="getMapURL"
     */
    private String getMapURL = "";
    private Image wmsImg;
    private final int cursorSize;

    public MapViewUI(UIController uiController, boolean suppressKeyEvents) {
        super(suppressKeyEvents);
        // Only set FullScreen for Nokia devices
        try {
            String platform = System.getProperty("microedition.platform");
            System.out.println("******* Platform: " + platform);
            if ((platform != null) && (platform.toLowerCase().trim().indexOf("nokia") >= 0)) {
                this.setFullScreenMode(true);
            }
        } catch (Exception e) {
        // Do nothing
        }

        this.uiController = uiController;

        cursorX = getWidth() / 2;
        cursorY = getHeight() / 2;

        cursorSize = Font.getDefaultFont().charWidth('+');

        backCommand = new Command(uiController.getString(UIConstants.BACK),
            Command.BACK, 5);
        zoomInCommand = new Command(uiController.getString(UIConstants.ZOOM_IN_CMD), Command.SCREEN, 1);
        zoomOutCommand = new Command(uiController.getString(UIConstants.ZOOM_OUT_CMD), Command.SCREEN, 2);
        resetCommand = new Command(uiController.getString(UIConstants.RESET_VIEW_CMD), Command.SCREEN, 3);
        recenterCommand = new Command(uiController.getString(UIConstants.RECENTER_CMD), Command.SCREEN, 4);
        refreshCommand = new Command(uiController.getString(UIConstants.REFRESH), Command.SCREEN, 5);
        // findPathCommand = new
        // Command(uiController.getString(UIConstants.FIND_PATH_CMD),
        // Command.SCREEN, 5);
        getFeatureInfoCommand = new Command(uiController.getString(UIConstants.GET_FEATURE_INFO), Command.SCREEN, 6);
        searchFeatureCommand = new Command(uiController
                .getString(UIConstants.SEARCH_FEATURE_UI_TITLE),
                Command.SCREEN, 7);
        helpCommand = new Command(uiController.getString(UIConstants.HELP_CMD),
            Command.SCREEN, 8);

        addCommand(zoomInCommand);
        addCommand(zoomOutCommand);
        addCommand(resetCommand);
        addCommand(recenterCommand);
        addCommand(backCommand);
        addCommand(refreshCommand);
        // addCommand(findPathCommand);
        addCommand(getFeatureInfoCommand);
        addCommand(searchFeatureCommand);
        addCommand(helpCommand);

        previousAction = NO_ACTION;

        uiController.setCommands(this);

        setCommandListener(this);
    }

    public void init(Image img) {
        this.wmsImg = img;
        repaint();
    }

    public void initParam(Float[] latLonBoundingBox, String getMapURL,
        String srs) {
        System.arraycopy(latLonBoundingBox, 0, boundingBox, 0, 4);
        this.getMapURL = getMapURL;

        // Resize bounding box to device dimension
        Float boxWidth = (getBoxHeight().Mul(getWidth())).Div(getHeight());
        if (boxWidth.Less(getBoxWidth())) {
            boxWidth = getBoxWidth();
        }

        Float boxHeight = (getBoxWidth().Mul(getHeight())).Div(getWidth());
        if (boxHeight.Less(getBoxHeight())) {
            boxHeight = getBoxHeight();
        }

        // Note, the coordinate of map is decart coordinate
        boundingBox[0] = boundingBox[2].Sub(boxWidth);
        boundingBox[1] = boundingBox[3].Sub(boxHeight);

        previousAction = NO_ACTION;

    // startPointSelected = false;
    // endPointSelected = false;
    }

    /**
     * @param latLonBoundingBox
     *            the boundingBox to set
     * @uml.property name="boundingBox"
     */
    public void setBoundingBox(Float[] latLonBoundingBox) {
        System.arraycopy(latLonBoundingBox, 0, boundingBox, 0, 4);

        // Resize bounding box to device dimension
        Float boxWidth = (getBoxHeight().Mul(getWidth())).Div(getHeight());
        if (boxWidth.Less(getBoxWidth())) {
            boxWidth = getBoxWidth();
        }

        Float boxHeight = (getBoxWidth().Mul(getHeight())).Div(getWidth());
        if (boxHeight.Less(getBoxHeight())) {
            boxHeight = getBoxHeight();
        }

        // Note, the coordinate of map is decart coordinate
        boundingBox[0] = boundingBox[2].Sub(boxWidth);
        boundingBox[1] = boundingBox[3].Sub(boxHeight);

        previousAction = NO_ACTION;

    // startPointSelected = false;
    // endPointSelected = false;
    }

    /**
     * @return the boundingBox
     * @uml.property name="boundingBox"
     */
    public Float[] getBoundingBox() {
        return boundingBox;
    }

    public Float getBoundingX1() {
        return boundingBox[0];
    }

    public Float getBoundingY1() {
        return boundingBox[1];
    }

    public Float getBoundingX2() {
        return boundingBox[2];
    }

    public Float getBoundingY2() {
        return boundingBox[3];
    }

    public String getImageFormat() {
        return ("image/png");
    }

    public String getXmlFormat() {
        return ("text/xml");
    }

    public String getTextFormat() {
        return ("text/plain");
    }

    public String getPNGFormat() {
        return ("image/png");
    }

    /*
     * public void setIsViewPath(boolean viewPath) { this.isViewPath = viewPath; }
     */
    public void setIsViewFeature(boolean viewFeature) {
        this.isViewFeature = viewFeature;
    }

    public String getSRS() {
        return "EPSG:4326";
    // return srs;
    }

    public String getVersion() {
        return "1.0.0";
    }

    /**
     * @return Returns the getMapURL.
     * @uml.property name="getMapURL"
     */
    public String getGetMapURL() {
        return getMapURL;
    }

    public int getPixelWidth() {
        // int pw;
        // // pw = getHeight() * getBoxWidth() / getBoxHeight();
        // pw =
        // (int)(((getBoxWidth().Mul(getHeight())).Div(getBoxHeight())).toLong());
        // if (pw > getWidth()) {
        // return getWidth();
        // }
        // return pw;
        return getWidth();
    }

    public int getPixelHeight() {
        // int ph;
        // // ph = getWidth() * getBoxHeight() / getBoxWidth();
        // ph =
        // (int)(((getBoxHeight().Mul(getWidth())).Div(getBoxWidth())).toLong());
        // if (ph > getHeight()) {
        // return getHeight();
        // }
        // return ph;
        return getHeight();
    }

    // Transform from cursor coordinate to world coordinate
    private Float[] transformFromView(int[] source) {
        if (2 != source.length) {
            return null;
        }

        Float[] dest = new Float[2];
        // dest[0] = boundingBox[0] + source[0]/getCurrentScale();
        dest[0] = boundingBox[0].Add(new Float(source[0]).Div(getCurrentScale()));
        // dest[3] = boundingBox[3] - source[1]/getCurrentScale();
        dest[1] = boundingBox[1].Add(new Float(getHeight() - source[1]).Div(getCurrentScale()));
        return dest;
    }

    // Transform from world coordinate to cursor coordinate
    private int[] transformFromReal(Float[] source) {
        if (2 != source.length || null == source[0] || null == source[1]) {
            return null;
        }

        int[] dest = new int[2];
        dest[0] = (int) (((source[0].Sub(boundingBox[0])).Mul(getCurrentScale())).toLong());
        dest[1] = getHeight() - (int) (((source[1].Sub(boundingBox[1])).Mul(getCurrentScale())).toLong());
        return dest;
    }

    public Float getCurrentScale() {
        // return (getPixelWidth() / getBoxWidth());
        return new Float(getPixelWidth()).Div(getBoxWidth());
    }

    // Center at the cursor
    private void reCenter(int[] curPoint) {
        if (curPoint.length == 2) {
            Float[] point;
            point = transformFromView(curPoint);
            reCenter(point);
        }

    }

    // Center at the specific point
    private void reCenter(Float[] point) {
        if (point.length == 2) {
            Float boxWidth = getBoxWidth();
            Float boxHeight = getBoxHeight();
            // boundingBox[0] = point[0] - (Float)(bboxWidth / 2);
            boundingBox[0] = point[0].Sub(boxWidth.Div(2));
            // boundingBox[1] = point[1] - (Float)(bboxHeight / 2);
            boundingBox[1] = point[1].Sub(boxHeight.Div(2));
            // boundingBox[2] = point[0] + (Float)(bboxWidth / 2);
            boundingBox[2] = point[0].Add(boxWidth.Div(2));
            // boundingBox[3] = point[1] + (Float)(bboxHeight / 2);
            boundingBox[3] = point[1].Add(boxHeight.Div(2));
            cursorX = getWidth() / 2;
            cursorY = getHeight() / 2;
        }

    }

    // Center at the specific point
    public void reCenterAtFeature(MapFeature feature) {
        previousAction = NO_ACTION;

        // zoom to best scale first
        zoomToScale(UIConstants.BEST_SCALE);
        // Recenter to feature
        Float boxWidth = getBoxWidth();
        Float boxHeight = getBoxHeight();
        boundingBox[0] = feature.getX().Sub(boxWidth.Div(2));
        boundingBox[1] = feature.getY().Sub(boxHeight.Div(2));
        boundingBox[2] = feature.getX().Add(boxWidth.Div(2));
        boundingBox[3] = feature.getY().Add(boxHeight.Div(2));
        cursorX = getWidth() / 2;
        cursorY = getHeight() / 2;

    }

    private void zoomIn() {
        if (previousAction == ZOOM_OUT) {
           restorePreviousAction(ZOOM_IN);
        } else {
            saveCurrentAction(ZOOM_IN);

            // Recenter at cursor first
            int[] cursors = {getCursorX(), getCursorY()};
            reCenter(cursors);

            // then zoom in
            Float oldScale = getBoxWidth().Div(getBoxHeight());
            Float oldWidth = getBoxWidth();
            Float oldHeight = getBoxHeight();

            boundingBox[0] = boundingBox[0].Add(UIConstants.SCALE.Mul(oldWidth));
            boundingBox[1] = boundingBox[1].Add(UIConstants.SCALE.Mul(oldHeight));
            boundingBox[2] = boundingBox[2].Sub(UIConstants.SCALE.Mul(oldWidth));

            boundingBox[3] = boundingBox[1].Add(getBoxWidth().Div(oldScale));
        }
    }

    private void zoomOut() {
        if (previousAction == ZOOM_IN) {
           restorePreviousAction(ZOOM_OUT);
        } else {
            saveCurrentAction(ZOOM_OUT);

            // Recenter at cursor first
            int[] cursors = {getCursorX(), getCursorY()};
            reCenter(cursors);

            // then zoom out
            Float oldScale = getBoxWidth().Div(getBoxHeight());
            Float oldWidth = getBoxWidth();
            Float oldHeight = getBoxHeight();

            boundingBox[0] = boundingBox[0].Sub(UIConstants.SCALE.Mul(oldWidth));
            boundingBox[1] = boundingBox[1].Sub(UIConstants.SCALE.Mul(oldHeight));
            boundingBox[2] = boundingBox[2].Add(UIConstants.SCALE.Mul(oldWidth));

            boundingBox[3] = boundingBox[1].Add(getBoxWidth().Div(oldScale));
        }
    }

    private void zoomToScale(Float scale) {
        previousAction = NO_ACTION;

        Float oldWidth = getBoxWidth();
        Float oldHeight = getBoxHeight();

        Float newWidth = (new Float(getPixelWidth())).Div(scale);
        Float newHeight = (new Float(getPixelHeight())).Div(scale);

        boundingBox[0] = boundingBox[0].Sub((newWidth.Sub(oldWidth)).Div(2));
        boundingBox[1] = boundingBox[1].Sub((newHeight.Sub(oldHeight)).Div(2));
        boundingBox[2] = boundingBox[2].Add((newWidth.Sub(oldWidth)).Div(2));
        boundingBox[3] = boundingBox[3].Add((newHeight.Sub(oldHeight)).Div(2));
    }

    private void restorePreviousAction(int currentAction) {
        Float[] tmp = new Float[4];
        System.arraycopy(previousBoundingBox, 0, tmp, 0, 4);
        System.arraycopy(boundingBox, 0, previousBoundingBox, 0, 4);
        System.arraycopy(tmp, 0, boundingBox, 0, 4);

        previousAction = currentAction;
    }

    private void saveCurrentAction(int currentAction) {
        System.arraycopy(boundingBox, 0, previousBoundingBox, 0, 4);
        previousAction = currentAction;
    }

    private void moveUp() {
        if (previousAction == MOVE_DOWN) {
           restorePreviousAction(MOVE_UP);
        } else {
            saveCurrentAction(MOVE_UP);

            Float oldHeight = getBoxHeight();

            boundingBox[1] = boundingBox[1].Add(UIConstants.SCALE.Mul(oldHeight));
            boundingBox[3] = boundingBox[3].Add(UIConstants.SCALE.Mul(oldHeight));
        }
    }

    private void moveDown() {
        if (previousAction == MOVE_UP) {
           restorePreviousAction(MOVE_DOWN);
        } else {
            saveCurrentAction(MOVE_DOWN);

            Float oldHeight = getBoxHeight();

            boundingBox[1] = boundingBox[1].Sub(UIConstants.SCALE.Mul(oldHeight));
            boundingBox[3] = boundingBox[3].Sub(UIConstants.SCALE.Mul(oldHeight));
        }
    }

    private void moveLeft() {
        if (previousAction == MOVE_RIGHT) {
           restorePreviousAction(MOVE_LEFT);
        } else {
            saveCurrentAction(MOVE_LEFT);

            Float oldWidth = getBoxWidth();

            boundingBox[0] = boundingBox[0].Sub(UIConstants.SCALE.Mul(oldWidth));
            boundingBox[2] = boundingBox[2].Sub(UIConstants.SCALE.Mul(oldWidth));
        }
    }

    private void moveRight() {
        if (previousAction == MOVE_LEFT) {
           restorePreviousAction(MOVE_RIGHT);
        } else {
            saveCurrentAction(MOVE_RIGHT);

            Float oldWidth = getBoxWidth();

            boundingBox[0] = boundingBox[0].Add(UIConstants.SCALE.Mul(oldWidth));
            boundingBox[2] = boundingBox[2].Add(UIConstants.SCALE.Mul(oldWidth));
        }
    }

    private void repaintCursor() {
        repaint(cursorX - (2 * cursorSize), cursorY - (2 * cursorSize),
            4 * cursorSize, 4 * cursorSize);
    }

    private void repaintCursor(int x, int y) {
        repaint(x - (2 * cursorSize), y - (2 * cursorSize), 4 * cursorSize,
            4 * cursorSize);
    }

    private void repaintStatus() {
        repaint(0, getHeight() - (2 * cursorSize), getWidth(), 2 * cursorSize);
    }

    private void moveCursorLeft() {
        if (cursorX - UIConstants.CURSOR_MOVEMENT > 0) {
            cursorX -= UIConstants.CURSOR_MOVEMENT;
            repaintCursor();
            repaintStatus();
        }
    }

    private void moveCursorRight() {
        if (cursorX + UIConstants.CURSOR_MOVEMENT < getWidth()) {
            cursorX += UIConstants.CURSOR_MOVEMENT;
            repaintCursor();
            repaintStatus();
        }
    }

    private void moveCursorUp() {
        if (cursorY - UIConstants.CURSOR_MOVEMENT > 0) {
            cursorY -= UIConstants.CURSOR_MOVEMENT;
            repaintCursor();
            repaintStatus();
        }
    }

    private void moveCursorDown() {
        if (cursorY + UIConstants.CURSOR_MOVEMENT < getHeight()) {
            cursorY += UIConstants.CURSOR_MOVEMENT;
            repaintCursor();
            repaintStatus();
        }
    }

    /*
     * private void setStartPoint() { int[] cursors = {getCursorX(),
     * getCursorY()}; int[] oldCursors = transformFromReal(startPoint);
     * startPoint = transformFromView(cursors); startPointSelected = true; //
     * Erase old points if (null != oldCursors) { repaint(oldCursors[0] -
     * cursorSize, oldCursors[1] - cursorSize, 2 * cursorSize, 2 * cursorSize); } //
     * Paint new points repaint(cursors[0] - cursorSize, cursors[1] -
     * cursorSize, 2 * cursorSize, 2 * cursorSize); }
     *
     * private void setEndPoint() { int[] cursors = {getCursorX(),
     * getCursorY()}; int[] oldCursors = transformFromReal(endPoint); endPoint =
     * transformFromView(cursors); endPointSelected = true; if (null !=
     * oldCursors) { repaint(oldCursors[0] - cursorSize, oldCursors[1] -
     * cursorSize, 2 * cursorSize, 2 * cursorSize); } repaint(cursors[0] -
     * cursorSize, cursors[1] - cursorSize, 2 * cursorSize, 2 * cursorSize); }
     */
    private static String floatToString(Float f, int num) {
        String floatValue = f.toString();
        if (floatValue.indexOf('.') >= 0 && (floatValue.indexOf('.') + num + 1 < floatValue.length())) {
            return floatValue.substring(0, floatValue.indexOf('.') + num + 1);
        } else {
            return floatValue;
        }
    }

    public void paint(Graphics g) {
        int oldColor = g.getColor();
        // Clear screen
        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(oldColor);
        if (null != wmsImg) {
            // System.out.println("************ Current scale: " +
            // getCurrentScale());
            // System.out.println("****** Cursor (x, y) = (" + cursorX + ", " +
            // cursorY + ")");
            if (isDragging) {
                g.drawImage(wmsImg, pointerEndX - pointerStartX, pointerEndY - pointerStartY, Graphics.TOP | Graphics.LEFT);
            } else {
                g.drawImage(wmsImg, 0, 0, Graphics.TOP | Graphics.LEFT);
            }

            g.setFont(smallFont);
            g.setColor(0x0000FF);
            g.drawString("1/" + floatToString(getCurrentScale(), 3), 0,
                getHeight(), Graphics.BOTTOM | Graphics.LEFT);
            int[] cursors = {getCursorX(), getCursorY()};
            Float[] real = transformFromView(cursors);
            g.drawString("lon:" + floatToString(real[0], 3) + " lat:" + floatToString(real[1], 3), getWidth(), getHeight(),
                Graphics.BOTTOM + Graphics.RIGHT);
            // Reset to default Font and color
            g.setFont(Font.getDefaultFont());
            g.setColor(oldColor);

            /*
             * // Draw startpoint, end point if (startPointSelected) {
             * g.setColor(0x00FF00); int[] startCursor =
             * transformFromReal(getStartPoint()); g.fillArc(startCursor[0] -
             * cursorSize / 2, startCursor[1] - cursorSize / 2, cursorSize,
             * cursorSize, 0, 360); g.setColor(oldColor); } if
             * (endPointSelected) { g.setColor(0xFF0000); int[] endCursor =
             * transformFromReal(getEndPoint()); g.fillArc(endCursor[0] -
             * cursorSize / 2, endCursor[1] - cursorSize / 2, cursorSize,
             * cursorSize, 0, 360); g.setColor(oldColor); }
             */

            g.setColor(0x0000FF);
            g.drawChar('+', cursorX, cursorY + (cursorSize / 2),
                Graphics.BASELINE | Graphics.HCENTER);
            g.setColor(oldColor);
        } else {
            uiController.getMapRequested();
        }
    }

    public void pointerDragged(int x, int y) {
        cursorX = x;
        cursorY = y;

        if (isDragging) {
            pointerEndX = x;
            pointerEndY = y;
        } else {
            isDragging = true;
            pointerStartX = x;
            pointerStartY = y;
        }
        repaint();
    }

    public void pointerReleased(int x, int y) {
        cursorX = x;
        cursorY = y;

        if (isDragging) {
            pointerEndX = x;
            pointerEndY = y;

            int intDx = pointerEndX - pointerStartX;
            int intDy = pointerEndY - pointerStartY;

            // if distance is more than 1 pixel, then update map
            if (Math.abs(intDx) > 0 || Math.abs(intDy) > 0) {
                previousAction = NO_ACTION;

                int[] pointersStart = {pointerStartX, pointerStartY};
                int[] pointersEnd = {pointerEndX, pointerEndY};

                Float[] pointersStartReal = transformFromView(pointersStart);
                Float[] pointersEndReal = transformFromView(pointersEnd);

                Float dx = pointersEndReal[0].Sub(pointersStartReal[0]);
                Float dy = pointersEndReal[1].Sub(pointersStartReal[1]);
                // update bouding box
                boundingBox[0] = boundingBox[0].Sub(dx);
                boundingBox[1] = boundingBox[1].Sub(dy);
                boundingBox[2] = boundingBox[2].Sub(dx);
                boundingBox[3] = boundingBox[3].Sub(dy);

                /*
                 * if (isViewPath) { uiController.viewPathRequested(); } else {
                 */
                uiController.updateMapRequested();
            // }
            } else {
                // if distance is less than 1, then consider pointer press
                pointerPressed(x, y);
            }
        }
        isDragging = false;
    }

    public void pointerPressed(int x, int y) {
        int oldCursorX = cursorX;
        int oldCursorY = cursorY;

        isDragging = false;

        cursorX = x;
        cursorY = y;

        repaintCursor(oldCursorX, oldCursorY);
        repaintCursor();
    }

    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KEY_NUM2:
                moveUp();
                /*
                 * if (isViewPath) { uiController.viewPathRequested(); } else {
                 */
                uiController.updateMapRequested();
                // }
                break;
            case KEY_NUM8:
                moveDown();
                // if (isViewPath) {
                // uiController.viewPathRequested();
                // } else {
                uiController.updateMapRequested();
                // }
                break;
            case KEY_NUM4:
                moveLeft();
                // if (isViewPath) {
                // uiController.viewPathRequested();
                // } else {
                uiController.updateMapRequested();
                // }
                break;
            case KEY_NUM6:
                moveRight();
                // if (isViewPath) {
                // uiController.viewPathRequested();
                // } else {
                uiController.updateMapRequested();
                // }
                break;
            /*
             * case KEY_NUM1: // isViewPath = false; setStartPoint(); break; case
             * KEY_NUM3: // isViewPath = false; setEndPoint(); break;
             */
            case KEY_NUM5:
                uiController.selectInfoLayerRequested();
                break;
            default:
                int action = getGameAction(keyCode);
                switch (action) {
                    case LEFT:
                        moveCursorLeft();
                        break;
                    case RIGHT:
                        moveCursorRight();
                        break;
                    case UP:
                        moveCursorUp();
                        break;
                    case DOWN:
                        moveCursorDown();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    protected void keyRepeated(int keyCode) {
        keyPressed(keyCode);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == refreshCommand) {
            // if (isViewPath) {
            // uiController.viewPathRequested();
            // } else {
            uiController.updateMapRequested();
        // }
        } else if (command == zoomInCommand) {
            zoomIn();
            // if (isViewPath) {
            // uiController.viewPathRequested();
            // } else {
            uiController.updateMapRequested();
        // }
        } else if (command == zoomOutCommand) {
            zoomOut();
            // if (isViewPath) {
            // uiController.viewPathRequested();
            // } else {
            uiController.updateMapRequested();
        // }
        } else if (command == resetCommand) {
            // isViewPath = false;
            uiController.getMapRequested();
        } else if (command == recenterCommand) {
            previousAction = NO_ACTION;
            int[] cursors = {getCursorX(), getCursorY()};
            reCenter(cursors);
            // if (isViewPath) {
            // uiController.viewPathRequested();
            // } else {
            uiController.updateMapRequested();
        // }
        } else if (command == backCommand) {
            // isViewPath = false;
            isViewFeature = false;
            //uiController.layerListRequested();
            uiController.backToSortLayerListUI();
        } /*
         * else if (command == findPathCommand) { isViewPath = false;
         * isViewFeature = false; uiController.findPathRequested(); }
         */ else if (command == getFeatureInfoCommand) {
            uiController.selectInfoLayerRequested();
        }         else if (command == searchFeatureCommand) {
                    if (isViewFeature) {
                        uiController.searchResultUIRequested();
                    } else {
                        isViewFeature = false;
                        uiController.searchUIRequested();
                    }
                }
        else if (command == helpCommand) {
            uiController.helpRequested();
        } else {
            // isViewPath = false;
            isViewFeature = false;
            uiController.commandAction(command, displayable);
        }
    }

    private Float getBoxWidth() {
        return Float.abs(boundingBox[2].Sub(boundingBox[0]));
    }

    private Float getBoxHeight() {
        return Float.abs(boundingBox[3].Sub(boundingBox[1]));
    }

    /**
     * @return Returns the cursorX.
     * @uml.property name="cursorX"
     */
    private int getCursorX() {
        return cursorX;
    }

    /**
     * @return Returns the cursorY.
     * @uml.property name="cursorY"
     */
    private int getCursorY() {
        return cursorY;
    }

    // Get X, Y in pixel for GetFeatureInfo
    public int getX() {
        return getCursorX();
    }

    public int getY() {
        return (getHeight() - getCursorY());
    }
    /**
     * @return Returns the startPoint.
     * @uml.property name="startPoint"
     */
    /*
     * public Float[] getStartPoint() { if (!startPointSelected) return null;
     * return startPoint; }
     */
    /**
     * @return Returns the endPoint.
     * @uml.property name="endPoint"
     */
    /*
     * public Float[] getEndPoint() { if (!endPointSelected) return null; return
     * endPoint; }
     */

    /*
     * public String getFindPathLayer() { try { return
     * (uiController.getModel().getPreferences().getFindPathLayer()); } catch
     * (ApplicationException e) { e.printStackTrace(); return ""; } }
     */
}