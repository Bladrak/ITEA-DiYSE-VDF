/**
 * 
 */
package org.itea.neotiq.devicemodel;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hbriand
 *
 */
public class DeviceManager {
    
    public enum EventType {
        EVT_ON_CLICK, EVT_TXT_CHANGED, EVT_ON_TOGGLE,
    }

    public static Collection<Device> getDevices() {
        Collection<Device> dev = new ArrayList<Device>();
        dev.addAll(VirtualDevice.getInstantiatedVirtualDevices());
        dev.addAll(InternalDevice.getInstantiatedInternalDevices());
        return dev;
    }
    
    public static Collection<String> getDeviceTypes() {
        Collection<String> dTypes = new ArrayList<String>();
        
        dTypes.addAll(VirtualDevice.getVirtualDeviceTypes());
        dTypes.addAll(InternalDevice.getInternalDeviceTypes());
        
        return dTypes;
    }

    public static void addDevice(String text, String name) {
        Log.d("DeviceManager", "Adding device "+text+" named "+name);
        for (String s : VirtualDevice.getVirtualDeviceTypes()) {
            if (s.equals(text)) {
                VirtualDevice.addDevice(text, name);
                return;
            }
        }
        for (String s : InternalDevice.getInternalDeviceTypes()) {
            if (s.equals(text)) {
                InternalDevice.addDevice(text, name);
                return;
            }
        }
    }

    public static Device getDeviceNamed(String string) throws DeviceNotFoundException {
        for (Device d : getDevices()) {
            if (d.getName().equals(string)) {
                return d;
            }
        }
        throw new DeviceNotFoundException();
    }
    
}
