/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp.model;

/**
 *
 * @author taina
 */
public class WFSGetCapabilitiesParameter extends WFSParameter {

    protected String request = "GetCapabilities";

    public WFSGetCapabilitiesParameter() {
    }

    public WFSGetCapabilitiesParameter(String url) {
        super(url);
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String toString() {
        String superStr = super.toString();
        if (null == superStr || "".equals(superStr)) {
            return null;
        }

        StringBuffer result = new StringBuffer(superStr);
        result.append("&request=").append(request);

        return result.toString();
    }
}
