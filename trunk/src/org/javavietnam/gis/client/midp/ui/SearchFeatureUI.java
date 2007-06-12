/*
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
 *
 *
 * =====================================================
 *
 */

package org.javavietnam.gis.client.midp.ui;

import henson.midp.Float;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import org.javavietnam.gis.shared.midp.model.SearchFeatureParameter;


/**
 * @author  khanhlnq
 */
public class SearchFeatureUI extends Form implements CommandListener, SearchFeatureParameter {

    private UIController uiController;
    private Command backCommand;
    private Command searchCommand;
    private TextField fWord;
    private String webGISURL;
    private int start = 0;

    /**
     * @uml.property  name="boundingBox"
     * @uml.associationEnd  multiplicity="(0 -1)"
     */
    Float[] boundingBox = new Float[4];

    /** Creates a new instance of SearchUI */
    public SearchFeatureUI(UIController uiController) {
        super(uiController.getString(UIConstants.SEARCH_FEATURE_UI_TITLE));
        this.uiController = uiController;

        fWord = new TextField(uiController.getString(UIConstants.KEYWORD), "", 50, TextField.ANY);
        uiController.vietSign.addCommands(fWord);
        append(fWord);

        searchCommand = new Command(uiController.getString(UIConstants.SEARCH), Command.SCREEN, 0);
        backCommand = new Command(uiController.getString(UIConstants.BACK), Command.BACK, 1);
        addCommand(searchCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void initParam(Float[] latLonBoundingBox, String webGISURL) {
        for (int i = 0; i < 4; i++) {
            boundingBox[i] = latLonBoundingBox[i];
        }
        this.webGISURL = webGISURL;
        fWord.setString("");
        start = 0;
    }

    public String getKeyWord() {
        return fWord.getString();
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            uiController.viewMapRequested();
        }
        else if (command == searchCommand) {
            start = 0;
            uiController.searchFeatureRequested();
        }
        else {
            uiController.commandAction(command, displayable);
        }
    }

    /**
     * @return  the boundingBox
     * @uml.property  name="boundingBox"
     */
    public Float[] getBoundingBox() {
        return boundingBox;
    }

    /**
     * @return  the webGISURL
     * @uml.property  name="webGISURL"
     */
    public String getWebGISURL() {
        return webGISURL;
    }

    /**
     * @return  the start
     * @uml.property  name="start"
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start  the start to set
     * @uml.property  name="start"
     */
    public void setStart(int start) {
        this.start = start;
    }

}
