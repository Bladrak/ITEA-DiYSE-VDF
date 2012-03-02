/**
 * 
 */
package org.itea.neotiq.devicemodel;

import org.itea.neotiq.devicemodel.DeviceManager.EventType;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hbriand
 *
 */
public class DeviceEvent extends EventObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7289213491531502300L;
    
    private DeviceManager.EventType eventType;
    private Map<String, Object> parameters;

    /**
     * @param source
     */
    public DeviceEvent(Device source, DeviceManager.EventType eventType) {
        super(source);
        this.setEventType(eventType);
        this.parameters = new HashMap<String, Object>();
    }
    
    public void setParameter(String key, Object value) {
        this.parameters.put(key, value);
    }


    /**
     * @param The key to the parameter
     * @return the parameter associated to the key
     */
    public Object getParameters(String key) {
        return parameters.get(key);
    }

    public void setEventType(DeviceManager.EventType eventType) {
        this.eventType = eventType;
    }

    public DeviceManager.EventType getEventType() {
        return eventType;
    }

    /* (non-Javadoc)
     * @see java.util.EventObject#toString()
     */
    @Override
    public String toString() {
        String params = "{";
        for (String key : parameters.keySet()) {
            params += key+" : "+parameters.get(key)+" ; ";
        }
        params += "}";
        return "DeviceEvent on "+this.source.getClass().toString()+": Type : "+this.eventType+" ; Parameters : "+params;
    }

    public String asXML() {
        StringBuilder l_sb = new StringBuilder();
        l_sb.append("<data type='");
        if (this.source instanceof VDEditText && ((VDEditText)this.source).isInt()) {
            l_sb.append("int");
        } else {
            l_sb.append("string");
        }
        l_sb.append("' value='");
        if (this.eventType == EventType.EVT_ON_CLICK) {
           l_sb.append("clicked");
        } else {
            l_sb.append(((RequestableDevice)this.source).getCurrentValue().toString());
        }
        l_sb.append("' xmlns='urn:xmpp:value:0'/>");
        return l_sb.toString();
    }


}
