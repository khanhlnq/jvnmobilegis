/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp.model;

import java.util.Vector;


/**
 * Klasse zur Datenhaltung innerhalb des LayerPanel
 */
public class InnerTreeNode extends Vector implements TreeNodeInterface {

    LayerInformation content;

    public InnerTreeNode(LayerInformation content) {
        this.content = content;
    }

    public String toString() {
        return content.toString();
    }

    public LayerInformation getLayerInformation() {
        return content;
    }
}
