/* * $URL$ * $Author$ * $Revision$ *$Date$ * * *===================================================== * */package org.javavietnam.gis.shared.midp.model;

/**
 * Klasse zur Datenhaltung innerhalb des LayerPanel
 */
public class TreeNode implements TreeNodeInterface {

    LayerInformation content;

    public TreeNode(LayerInformation content) {
        this.content = content;
    }

    public String toString() {
        return content.toString();
    }

    public LayerInformation getLayerInformation() {
        return content;
    }
}
