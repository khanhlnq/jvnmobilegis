/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp;

import java.util.Vector;

/**
 *
 * @author taina
 */
public class StringUtil {

    public static String[] split(String content, String character) {
        Vector textList = new Vector();
        String tmp = content;
        int pos = tmp.indexOf(character);

        while (pos != -1) {
            textList.addElement(tmp.substring(0, pos));
            tmp = tmp.substring(pos + 1);
            pos = tmp.indexOf(character);
        }

        textList.addElement(tmp);

        if (textList.size() == 0) {
            return null;
        }

        String[] result = new String[textList.size()];
        for (int i = 0; i < textList.size(); i++) {
            result[i] = (String) textList.elementAt(i);
        }

        return result;
    }
}
