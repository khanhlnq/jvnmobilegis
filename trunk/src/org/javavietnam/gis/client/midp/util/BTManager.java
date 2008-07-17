/**
 * S�rgio Est�v�o
 * MIDP Adventures
 * www.sergioestevao.com/midp
 */

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
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 17 July 2007
 */
package org.javavietnam.gis.client.midp.util;

import java.util.Vector;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class BTManager implements DiscoveryListener {

    public static final int BLUETOOTH_TIMEOUT = 30000;

    public Vector btDevicesFound;
    public Vector btServicesFound;

    private boolean isBTSearchComplete;

    static BTManager btManager = null;

    public static BTManager instance() {
        if (btManager == null) {
            btManager = new BTManager();
        }

        return btManager;
    }

    /** Creates a new instance of BTManager */
    private BTManager() {
        btDevicesFound = new Vector();
        btServicesFound = new Vector();
    }

    public static UUID[] getRFCOMM_UUID() {
        UUID[] uuidSet;
        UUID RFCOMM_UUID = new UUID(0x1101); // RFCOMM service
        uuidSet = new UUID[1];
        uuidSet[0] = RFCOMM_UUID;

        return uuidSet;
    }

    /**
     * Finds bluetooth devices
     * 
     * @param aServices
     *            , an array with the service UUID identifiers you want to
     *            search
     * @returns the number of devices found
     */
    public int find(UUID[] aServices) {
        findDevices();
        findServices(aServices);
        return btDevicesFound.size();
    }

    public int findDevices() {
        try {
            // cleans previous elements
            btDevicesFound.removeAllElements();
            isBTSearchComplete = false;
            LocalDevice local = LocalDevice.getLocalDevice();
            DiscoveryAgent discoveryAgent = local.getDiscoveryAgent();
            // discover new devices
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
            while ((!isBTSearchComplete)) {
                // waits for a fixed time, to avoid long search
                synchronized (this) {
                    this.wait(BTManager.BLUETOOTH_TIMEOUT);
                }
                // check if search is completed
                if (!isBTSearchComplete) {
                    // search no yet completed so let's cancel it
                    discoveryAgent.cancelInquiry(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return btDevicesFound.size();
    }

    public void deviceDiscovered(RemoteDevice remoteDevice,
            DeviceClass deviceClass) {
        btDevicesFound.addElement(remoteDevice);
    }

    public void inquiryCompleted(int param) {
        isBTSearchComplete = true;
        // notifies and wake main thread that device search is completed
        synchronized (this) {
            this.notify();
        }
    }

    public void findServices(UUID[] aServices) {
        // cleans previous elements
        btServicesFound.removeAllElements();
        try {
            LocalDevice local = LocalDevice.getLocalDevice();
            DiscoveryAgent discoveryAgent = local.getDiscoveryAgent();
            // discover services
            if (btDevicesFound.size() > 0) {
                for (int i = 0; i < btDevicesFound.size(); i++) {
                    isBTSearchComplete = false;
                    // adds a null element in case we don't found service
                    btServicesFound.addElement(null);
                    int transID = discoveryAgent.searchServices(null,
                            aServices, (RemoteDevice) (btDevicesFound
                                    .elementAt(i)), this);
                    // wait for service discovery ends
                    synchronized (this) {
                        this.wait(BTManager.BLUETOOTH_TIMEOUT);
                    }
                    if (!isBTSearchComplete) {
                        discoveryAgent.cancelServiceSearch(transID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void servicesDiscovered(int param, ServiceRecord[] serviceRecord) {
        int index = btServicesFound.size() - 1;
        for (int i = 0; i < serviceRecord.length; i++) {
            btServicesFound.setElementAt(serviceRecord[i], index);
        }
    }

    public void serviceSearchCompleted(int transID, int respCode) {
        isBTSearchComplete = true;
        // notifies and wake mains thread that service search is completed
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Get a human readable name of the BT device.
     * 
     * @param deviceID
     * @return the friendly name for the BT device
     */
    public String getDeviceName(int deviceID) {
        try {
            return ((RemoteDevice) btDevicesFound.elementAt(deviceID))
                    .getFriendlyName(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * Gets the URL address of the the service you want to connect
     * 
     * @param deviceID
     * @return the Url address for the device and service found
     */
    public String getServiceURL(int deviceID) {
        try {
            return ((ServiceRecord) btServicesFound.elementAt(deviceID))
                    .getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,
                            false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

}
