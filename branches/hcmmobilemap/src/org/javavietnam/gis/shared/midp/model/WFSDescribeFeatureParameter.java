/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp.model;

/**
 *
 * @author taina
 */
public class WFSDescribeFeatureParameter extends WFSParameter {

    protected String request = "DescribeFeatureType";
    protected String typeName;

    public WFSDescribeFeatureParameter() {
    }

    public WFSDescribeFeatureParameter(String url) {
        super(url);
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        String superStr = super.toString();
        if (null == superStr || "".equals(superStr)) {
            return null;
        }

        if (null == typeName || "".equals(typeName)) {
            return null;
        }

        StringBuffer result = new StringBuffer(superStr);
        result.append("&request=").append(request);
        result.append("&typeName=").append(typeName);

        return result.toString();
    }
}
