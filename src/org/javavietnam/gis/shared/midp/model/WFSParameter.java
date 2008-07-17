/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp.model;

/**
 *
 * @author taina
 */
public class WFSParameter {

    protected String url;
    protected String service = "WFS";
    protected String version = "1.1.1";

    public WFSParameter() {
    }

    public WFSParameter(String url) {
        this.url = url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        if (null == url || "".equals(url)) {
            return null;
        }

        StringBuffer result = new StringBuffer(url);
        result.append("?service=").append(service);
        result.append("&version=").append(version);

        return result.toString();
    }
}
