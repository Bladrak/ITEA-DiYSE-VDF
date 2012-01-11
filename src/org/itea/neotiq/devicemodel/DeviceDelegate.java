/**
 * 
 */
package org.itea.neotiq.devicemodel;

/**
 * @author hbriand
 *
 */
public interface DeviceDelegate {
    
    public void eventOccured(DeviceEvent de);
    public void deviceCreated(Device d);

}
