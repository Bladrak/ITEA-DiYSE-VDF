package org.itea.neotiq.application;

import org.itea.neotiq.devicemodel.Device;
import org.itea.neotiq.devicemodel.DeviceDelegate;
import org.itea.neotiq.devicemodel.DeviceEvent;

import android.util.Log;

public class LogDeviceDelegate implements DeviceDelegate {

    public void eventOccured(DeviceEvent de) {
        Log.d("LogDeviceDelegate", "Event occured "+de.toString());
    }

    public void deviceCreated(Device d) {
        Log.d("LogDeviceDelegate", "Device created : "+d.getName());
    }

}
