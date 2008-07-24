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
public class FilterEncoding {

    /**
     * Operator "Like"
     */
    public static String like(String attribute, String value) {
        StringBuffer result = new StringBuffer();
        result.append("<ogc:PropertyIsLike wildCard=\"*\" singleChar=\"#\" escapeChar=\"!\">");
        result.append("<ogc:PropertyName>" + attribute + "</ogc:PropertyName>");
        result.append("<ogc:Literal>*" + value + "*</ogc:Literal>");
        result.append("</ogc:PropertyIsLike>");

        return result.toString();
    }

    /**
     * Operator "BBOX"
     */
    public static String bbox(String attribute, Float[] bbox, String SRS) {
        StringBuffer result = new StringBuffer("<ogc:BBOX>");
        String srsTmp = StringUtil.split(SRS, ":")[1];

        result.append("<ogc:PropertyName>" + attribute + "</ogc:PropertyName>");
        result.append("<gml:Box srsName=\"http://www.opengis.net/gml/srs/epsg.xml#" + srsTmp + "\">");
        result.append("<gml:coordinates>" + bbox[0] + "," + bbox[1] + " " + bbox[2] + "," + bbox[3] + "</gml:coordinates>");
        result.append("</gml:Box>");
        result.append("</ogc:BBOX>");

        return result.toString();
    }

    /**
     * Operator "And"
     */
    public static String and(String[] filters) {
        if (null == filters || 0 == filters.length) {
            return null;
        }

        StringBuffer result = new StringBuffer("<ogc:And>");

        for (int i = 0; i < filters.length; i++) {
            result.append(filters[i]);
        }

        result.append("</ogc:And>");

        return result.toString();
    }

    /**
     * Create filter string
     */
    public static String createFilter(String filter) {
        if (null == filter || "".equals(filter)) {
            return null;
        }

        StringBuffer result = new StringBuffer("<ogc:Filter>");
        result.append(filter);
        result.append("</ogc:Filter>");

        return result.toString();
    }
}
