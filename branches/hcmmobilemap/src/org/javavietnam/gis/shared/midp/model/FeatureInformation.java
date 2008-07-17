/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javavietnam.gis.shared.midp.model;

import henson.midp.Float;
import java.util.Hashtable;

/**
 *
 * @author taina
 */
public class FeatureInformation {

    protected String layerName;
    protected Hashtable data = new Hashtable();
    protected Float[] bbox;

    public FeatureInformation() {
    }

    public Float[] getBbox() {
        return bbox;
    }

    public void setBbox(Float[] bbox) {
        this.bbox = bbox;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public void setField(String key, String value) {
        data.put(key, value);
    }

    public String getField(String key) {
        return (String) data.get(key);
    }
}
