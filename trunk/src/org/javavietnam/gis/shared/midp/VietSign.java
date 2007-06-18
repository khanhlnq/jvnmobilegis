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
 * @author: Khanh Le
 */

package org.javavietnam.gis.shared.midp;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;


public class VietSign implements CommandListener, ItemCommandListener {

    public static final Command signCommand = new Command("B\u1ecf d\u1ea5u", Command.SCREEN, 0);

    private static final String signA = "a\u00e1\u00e0\u1ea3\u00e3\u1ea1"
            + "\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7"
            + "\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead"
            + "A\u00c1\u00c0\u1ea2\u00c3\u1ea0"
            + "\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6"
            + "\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac";
    private static final String signE = "e\u00e9\u00e8\u1ebb\u1ebd\u1eb9"
            + "\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7"
            + "E\u00c9\u00c8\u1eba\u1ebc\u1eb8"
            + "\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6";
    private static final String signI = "i\u00ed\u00ec\u1ec9\u0129\u1ecb" + "I\u00cd\u00cc\u1ec8\u0128\u1eca";
    private static final String signO = "o\u00f3\u00f2\u1ecf\u00f5\u1ecd"
            + "\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9"
            + "\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3"
            + "O\u00d3\u00d2\u1ece\u00d5\u1ecc"
            + "\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8"
            + "\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2";
    private static final String signU = "u\u00fa\u00f9\u1ee7\u0169\u1ee5"
            + "\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1"
            + "U\u00da\u00d9\u1ee6\u0168\u1ee4"
            + "\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0";
    private static final String signY = "y\u00fd\u1ef3\u1ef7\u1ef9\u1ef5" + "Y\u00dd\u1ef2\u1ef6\u1ef8\u1ef4";
    private static final String signD = "d\u0111D\u0110";

    private final MIDlet midlet;
    private Displayable previous;
    private List listSign = null;

    private boolean possible = true;
    private TextField textField;
    private String currentString;
    private char currentChar;
    private static final Command backCommand = new Command("Tr\u1edf l\u1ea1i", Command.BACK, 1);

    public VietSign(MIDlet midlet) {
        // super("B\u1ecf d\u1ea5u ti\u1ebfng Vi\u1ec7t", List.IMPLICIT);
        this.midlet = midlet;

        //
        // addCommand(backCommand);
        // setCommandListener(this);
    }

    public void addCommands(Item item) {
        item.addCommand(VietSign.signCommand);
        item.setItemCommandListener(this);
    }

    void initList(String signString) {
        listSign = null;
        listSign = new List("B\u1ecf d\u1ea5u ti\u1ebfng Vi\u1ec7t", List.IMPLICIT);
        listSign.setTicker(new Ticker("B\u1ea1n h\u00e3y ch\u1ecdn k\u00fd t\u1ef1 c\u00f3 d\u1ea5u ph\u00f9 h\u1ee3p"));
        listSign.addCommand(backCommand);
        listSign.setCommandListener(this);
        char ch;
        for (int i = 0; i < signString.length(); i++) {
            ch = signString.charAt(i);
            listSign.append(String.valueOf(ch), null);
            if (ch == currentChar) {
                listSign.setSelectedIndex(i, true);
            }
        }
        if (possible) Display.getDisplay(midlet).setCurrent(listSign);
    }

    void setError() {
        Alert errorAlert = new Alert("\u263b L\u1ed7i x\u1ea3y ra. ",
                "Kh\u00f4ng b\u1ecf d\u1ea5u \u0111\u01b0\u1ee3c!\nB\u1ecf d\u1ea5u kh\u00f4ng \u0111\u00fang.",
                null,
                AlertType.ERROR);
        errorAlert.setTimeout(Alert.FOREVER);
        Display.getDisplay(midlet).setCurrent(errorAlert, previous);
        deInitialize(false);
    }

    public void commandAction(Command c, Item i) {
        if (c == signCommand && i instanceof TextField) {
            possible = true;
            previous = Display.getDisplay(midlet).getCurrent();
            textField = (TextField) i;
            currentString = textField.getString();
            if (currentString.length() > 0) {
                currentChar = currentString.charAt(currentString.length() - 1);
                if (signA.indexOf(currentChar) != -1) initList(signA);
                else if (signE.indexOf(currentChar) != -1) initList(signE);
                else if (signI.indexOf(currentChar) != -1) initList(signI);
                else if (signO.indexOf(currentChar) != -1) initList(signO);
                else if (signU.indexOf(currentChar) != -1) initList(signU);
                else if (signY.indexOf(currentChar) != -1) initList(signY);
                else if (signD.indexOf(currentChar) != -1) initList(signD);
                else {
                    possible = false;
                    setError();
                }
            } else {
                possible = false;
                setError();
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND && possible) {
            String str;
            str = currentString.substring(0, currentString.length() - 1);
            str = str + listSign.getString(listSign.getSelectedIndex());
            textField.setString(str);
            Display.getDisplay(midlet).setCurrent(previous);
            deInitialize(false);
        } else if (c == backCommand) {
            Display.getDisplay(midlet).setCurrent(previous);
            deInitialize(false);
        }
    }

    void deInitialize(boolean all) {
        previous = null;
        possible = true;
        textField = null;
        currentString = null;
        listSign = null;
    }
}
