/**
 * 
 */
package org.itea.neotiq.devicemodel;

import org.itea.neotiq.application.CustomApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hbriand
 *
 */
public abstract class VirtualDevice extends Device {

    
    protected static Map<String, String> subclasses;
    private static Collection<VirtualDevice> instantiatedVirtualDevices = new ArrayList<VirtualDevice>();
    protected static String uiElementName;


    public static Collection<String> getVirtualDeviceTypes() {
        if (VirtualDevice.subclasses == null) {
            VirtualDevice.subclasses = new HashMap<String, String>();
            VirtualDevice.subclasses.put("Button", "org.itea.neotiq.devicemodel.VDButton");
            VirtualDevice.subclasses.put("Toggle Button", "org.itea.neotiq.devicemodel.VDToggle");
            VirtualDevice.subclasses.put("Edit Text", "org.itea.neotiq.devicemodel.VDEditText");
            VirtualDevice.subclasses.put("Edit Int", "org.itea.neotiq.devicemodel.VDEditText");
        }
        return VirtualDevice.subclasses.keySet();
    }
    
    public static Collection<VirtualDevice> getInstantiatedVirtualDevices() {
        return VirtualDevice.instantiatedVirtualDevices;
    }

    public static void addDevice(String text, String name) {
        String className = VirtualDevice.subclasses.get(text);
        try {
            VirtualDevice vd = (VirtualDevice)Class.forName(className).newInstance();
            vd.setName(name);
            if ("Edit Int".equals(text)) {
                ((VDEditText)vd).setInt(true);
            }
            vd.setDelegate(CustomApplication.getDefaultDelegate());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * 
     */
    protected VirtualDevice() {
        super();
        instantiatedVirtualDevices.add(this);
    }
    
    /**
     * @return the uielementname
     */
    public static String getUiElementName() {
        return uiElementName;
    }

}
