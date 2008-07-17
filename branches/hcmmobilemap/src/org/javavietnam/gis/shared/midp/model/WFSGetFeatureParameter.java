/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;

/**
 *
 * @author taina
 */
public class WFSGetFeatureParameter extends WFSParameter {

    protected String request = "GetFeature";
    protected String[] typeName;
    protected String[] propertyName;
    protected Float[] bbox;
    protected String srs = "EPSG:4326";

    public WFSGetFeatureParameter() {
    }

    public WFSGetFeatureParameter(String url) {
        super(url);
    }

    public Float[] getBbox() {
        return bbox;
    }

    public void setBbox(Float[] bbox) {
        this.bbox = bbox;
    }

    public String[] getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String[] propertyName) {
        this.propertyName = propertyName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSrs() {
        return srs;
    }

    public void setSrs(String srs) {
        this.srs = srs;
    }

    public String[] getTypeName() {
        return typeName;
    }

    public void setTypeName(String[] typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        String superStr = super.toString();
        if (null == superStr || "".equals(superStr)) {
            return null;
        }

        if (null == typeName || null == propertyName || null == bbox) {
            return null;
        }

        StringBuffer types = new StringBuffer(typeName[0]);
        for (int i = 1; i < typeName.length; i++) {
            types.append(",").append(typeName[i]);
        }

        StringBuffer properties = new StringBuffer(propertyName[0]);
        for (int i = 1; i < propertyName.length; i++) {
            properties.append(",").append(propertyName[i]);
        }

        StringBuffer coors = new StringBuffer(bbox[0].toString());
        for (int i = 1; i < bbox.length; i++) {
            coors.append(",").append(bbox[i].toString());
        }

        StringBuffer result = new StringBuffer(superStr);
        result.append("&request=").append(request);
        result.append("&typeName=").append(types.toString());
        result.append("&propertyName=").append(properties.toString());
        result.append("&BBOX=").append(coors.toString());
        result.append("&SRS=").append(srs);

        return result.toString();
    }
}
