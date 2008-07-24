/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp;

import henson.midp.Float;

/**
 *
 * @author taina
 */
public class CQLFilter {

    /**
     * Operator "Like"
     */
    public static String like(String attribute, String value) {
        String result = attribute + " LIKE '%" + value + "%'";

        return result;
    }

    /**
     * Operator "BBOX"
     */
    public static String bbox(String attribute, Float[] bbox) {
        String result = "BBOX(" + attribute + "," + bbox[0] + "," + bbox[1] + "," + bbox[2] + "," + bbox[3] + ")";

        return result;
    }

    /**
     * Operator "And"
     */
    public static String and(String[] filters) {
        if (null == filters || 0 == filters.length) {
            return null;
        }

        StringBuffer result = new StringBuffer("(" + filters[0] + ")");

        for (int i = 1; i < filters.length; i++) {
            result.append(" AND (" + filters[i] + ")");
        }

        return result.toString();
    }
}
