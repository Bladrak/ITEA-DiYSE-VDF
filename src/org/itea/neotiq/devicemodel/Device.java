package org.itea.neotiq.devicemodel;

import org.itea.neotiq.application.CustomApplication;

import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Device {
    
    protected String name;
    protected Collection<DeviceEvent> eventsOccured = new ArrayList<DeviceEvent>();
    protected DeviceDelegate delegate;
    protected View uiElement;
    
    protected Device() {
//        this.setDelegate(CustomApplication.getDefaultDelegate());
    }
    
    
    /**
     * @return the name of the Device
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the events that have occured
     */
    public Collection<DeviceEvent> getEventsOccured() {
        return eventsOccured;
    }

    /**
     * @param delegate the delegate to set
     */
    public void setDelegate(DeviceDelegate delegate) {
        this.delegate = delegate;
        this.getDelegate().deviceCreated(this);
    }
    
    protected void sendEvent(DeviceEvent de) {
        this.eventsOccured.add(de);
        this.getDelegate().eventOccured(de);
    }

    /**
     * @return the delegate
     */
    public DeviceDelegate getDelegate() {
        if (delegate == null) {
            delegate = CustomApplication.getDefaultDelegate();
        }
        return delegate;
    }
    
    public View getView() {
        return uiElement;
    }




    public abstract String getSemanticAnnotation();

    
}
