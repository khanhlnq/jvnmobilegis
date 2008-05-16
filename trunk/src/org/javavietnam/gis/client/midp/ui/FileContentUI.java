/*
 * $Id$
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * Copyright (C) 2006-2007 by JVNGIS
 *
 * All copyright notices regarding JVNMobileGIS MUST remain
 * intact in the Java codes and resource files.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Support can be obtained from project homepage at:
 * http://code.google.com/p/jvnmobilegis/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0.3
 * @author: Binh Pham
 * @Date Created: 09 May 2008
 */
package org.javavietnam.gis.client.midp.ui;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

public class FileContentUI extends Form implements CommandListener {

    private UIController uiController;
    private FileSystemBrowserUI fileSystemBrowserUI;
    private FileConnection fileConnection;
    private DataInputStream dis;
    private InputStream is;
    private Image imageContent;
    private Command back;

    public FileContentUI(UIController uiController,
            FileSystemBrowserUI fileSystemBrowserUI) {
        super("");
        this.uiController = uiController;
        this.fileSystemBrowserUI = fileSystemBrowserUI;

        back = new Command(uiController.getString(UIConstants.BACK),
                Command.BACK, 1);

        addCommand(back);
        setCommandListener(this);
    }

    public void getFileContent(String fileName) throws IOException {
        this.deleteAll();
        fileConnection = (FileConnection) Connector.open("file://localhost/" + fileSystemBrowserUI.getCurrPath() + fileName);
        setTitle("View file: " + fileName);

        if (!fileConnection.exists()) {
            throw new IOException("File does not exists");
        }

        int fileSize = (int) fileConnection.fileSize();

        if (fileSize > 0) {
            if (fileName.endsWith(".png")) {
                dis = fileConnection.openDataInputStream();
                byte[] data = new byte[fileSize];

                dis.read(data);
                imageContent = Image.createImage(data, 0, data.length);
                append(imageContent);
            } else {
                is = fileConnection.openInputStream();
                byte[] data = new byte[fileSize];

                is.read(data);
                append(new String(data, 0, data.length));
            }
        }

        if (is != null) {
            is.close();
        }
        if (dis != null) {
            dis.close();
        }
        fileConnection.close();
    }

    public void commandAction(Command command, Displayable display) {
        if (command == back) {
            uiController.viewFileSystemBrowserUIRequested();
        }
    }
}
