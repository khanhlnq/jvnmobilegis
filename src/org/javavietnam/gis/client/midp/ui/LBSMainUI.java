/*
 * $Id: LBSMainForm.java 283 2008-08-06 02:56:37Z khanh.lnq $
 * $URL: https://jvnmobilegis.googlecode.com/svn/trunk/src/org/javavietnam/gis/client/midp/ui/LBSMainForm.java $
 * $Author: khanh.lnq $
 * $Revision: 283 $
 * $Date: 2008-08-06 09:56:37 +0700 (Wed, 06 Aug 2008) $
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
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package org.javavietnam.gis.client.midp.ui;

import javax.microedition.lcdui.*;

import org.javavietnam.gis.client.midp.util.GpsBt;
import org.javavietnam.gis.client.midp.util.Location;

/**
 * @author Administrator
 * 
 */
public class LBSMainUI extends Form implements CommandListener {

	private String latitude;
	private String longitude;
	private Command cmdBack;
	private Command cmdSearchGps;
	private Command cmdShowMeOnMap;
	private StringItem gpsState;
	private StringItem quality;
	private StringItem latitudeText;
	private StringItem longitudeText;
	private StringItem time;
	private StringItem satellite;

	private final UIController uiController;

	// TODO: Extract gpsForm to a separate UI
	// TODO: Move threads to EventDispatcher
	// TODO: Use UIController to switch and display Forms
	// TODO: Handling Alert and Errors using UIController

	/**
	 * @param title
	 */
	public LBSMainUI(UIController uiController) {
		super("Gps Info");
		this.uiController = uiController;

		setCommandListener(this);
		cmdSearchGps = new Command(uiController.getString(UIConstants.SEARCH),
				Command.ITEM, 1);
		cmdShowMeOnMap = new Command(uiController
				.getString(UIConstants.SHOW_ME_ON_MAP), Command.ITEM, 2);
		cmdBack = new Command(uiController.getString(UIConstants.BACK),
				Command.BACK, 4);
		addCommand(cmdShowMeOnMap);
		addCommand(cmdSearchGps);
		addCommand(cmdBack);

		gpsState = new StringItem(uiController.getString(UIConstants.STATUS),
				uiController.getString(UIConstants.NO_GPS));
		quality = new StringItem(uiController.getString(UIConstants.QUALITY),
				uiController.getString(UIConstants.NO_GPS));
		latitudeText = new StringItem(uiController
				.getString(UIConstants.LATITUDE), uiController
				.getString(UIConstants.NO_GPS));
		longitudeText = new StringItem(uiController
				.getString(UIConstants.LONGITUE), uiController
				.getString(UIConstants.NO_GPS));
		time = new StringItem(uiController.getString(UIConstants.TIME), "NA");
		satellite = new StringItem(uiController
				.getString(UIConstants.SATELLITE), uiController
				.getString(UIConstants.NO_GPS));

		append(gpsState);
		append(quality);
		append(latitudeText);
		append(longitudeText);
		append(time);
		append(satellite);
	}

	public void updateLocation(GpsBt gpsBt) {
		gpsState.setText(uiController.getString(UIConstants.CONNECTED));
		Location location = gpsBt.getLocation();
		quality.setText(location.quality + "");

		latitude = location.latitude;
		longitude = location.longitude;

		latitudeText.setText(latitude + location.northHemi);
		longitudeText.setText(longitude + location.eastHemi);
		time.setText(location.utc);
		satellite.setText(location.nSat + "");
	}

	public void disconnectedAlert() {
		gpsState.setText(uiController.getString(UIConstants.DISCONNECTED));
	}

	public void commandAction(Command cmd, Displayable display) {
		if (cmd == cmdSearchGps) {
			uiController.gpsUIRequested();
			uiController.gpsSearchingRequested();
		} else if (cmd == cmdShowMeOnMap) {
			if (gpsState.getText().trim().equals(
					uiController.getString(UIConstants.CONNECTED))) {
				uiController.getMapViewUI().updateMyNMEALocation(latitude,
						longitude);
			} else {
				uiController.showErrorAlert(uiController
						.getString(UIConstants.NO_GPS), this);
			}
		} else if (cmd == cmdBack) {
			uiController.viewMapRequested();
		}
	}
}
