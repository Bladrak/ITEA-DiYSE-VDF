
package org.itea.neotiq.xmpp;

import org.itea.neotiq.devicemodel.Device;
import org.itea.neotiq.devicemodel.DeviceManager;
import org.itea.neotiq.devicemodel.DeviceNotFoundException;
import org.itea.neotiq.devicemodel.RequestableDevice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.lang.reflect.Method;

public class RestDevices {

    private static final String TAG = "RestDevices";

    @SuppressWarnings("rawtypes")
    public static XmppRest treatResponse(XmppRest xPacket) {
        try {
            Class c = Class.forName(RestDevices.class.getName());
            String restMethod = xPacket.getMethod().toLowerCase()
                    + toCamelCaseStripSlashes(xPacket.getResource());
            Class[] parTypes = new Class[1];
            parTypes[0] = XmppRest.class;
            Method m = c.getDeclaredMethod(restMethod, parTypes);
            XmppRest iq = (XmppRest)m.invoke(null, xPacket);
            return iq;
        } catch (Exception e) {
//            e.printStackTrace();
            try {
                return RestDevices.otherServiceMethod(xPacket);
            } catch (Exception e1) {
                e1.printStackTrace();
                return unavailableMethodIq(xPacket);
            }
        }
    }

    private static String toCamelCaseStripSlashes(String s) {
        String[] parts = s.split("/");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        if (!s.equals("")) {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return "";
    }

    private static XmppRest unavailableMethodIq(XmppRest xPacket) {
        XmppRest iq = new XmppRest();
        iq.setTo(xPacket.getFrom());
        iq.setPacketID(xPacket.getPacketID());
        iq.setResult(501, "Not implemented");
        return iq;
    }

    private static XmppRest otherServiceMethod(XmppRest xPacket) throws Exception {
        String[] parts = xPacket.getResource().split("/");
        try {
            Device d = DeviceManager.getDeviceNamed(parts[1]);
            if (xPacket.getMethod().equals("GET")) {
                return getDevice(d, xPacket);
            } else if (xPacket.getMethod().equals("POST")) {
                return postDevice(d, xPacket);
            } else {
                return unavailableMethodIq(xPacket);
            }
        } catch (DeviceNotFoundException e) {
            return deviceNotFoundIq(xPacket, parts[1]);
        }
    }

    private static XmppRest deviceNotFoundIq(XmppRest xPacket, String string) {
        XmppRest iq = new XmppRest();
        iq.setTo(xPacket.getFrom());
        iq.setPacketID(xPacket.getPacketID());
        iq.setResult(404, "Device " + string + " not found");
        return iq;
    }

    private static XmppRest postDevice(Device d, XmppRest xPacket) {
        return unavailableMethodIq(xPacket);
    }

    private static XmppRest getDevice(Device d, XmppRest xPacket) throws Exception {
        String[] parts = xPacket.getResource().split("/");
        if (parts[2].toLowerCase().equals("currentvalue") && (d instanceof RequestableDevice)) {
            XmppRest iq = getDefaultOkIq(xPacket);
            iq.getHeaders().put("Content-Type", "text/plain");
            iq.setBody(((RequestableDevice)d).getCurrentValue());
            return iq;
        }
        return unavailableMethodIq(xPacket);
    }

    @SuppressWarnings("unused")
    private static XmppRest getUriTest(XmppRest xPacket) {
        XmppRest iq = new XmppRest();
        iq.getHeaders().put("Content-Type", "text-plain");
        iq.setBody("This is the list of commands...");
        return iq;
    }

    @SuppressWarnings("unused")
    private static XmppRest getVdfList(XmppRest xPacket) {
        Log.d(TAG, "Requesting the list of devices");
        XmppRest iq = getDefaultOkIq(xPacket);
        if (DeviceManager.getDevices().size() == 0) {
            iq.getHeaders().put("Content-Type", "text/plain");
            iq.setBody("No device created on the VDF.");
        } else {
            if ("XML".equals(xPacket.getBody())) {
//                StringBuilder dev_l = new StringBuilder();
//                dev_l.append("<![CDATA[");
//                dev_l.append("<devices>");
//                for (Device d : DeviceManager.getDevices()) {
//                    dev_l.append("<device name='");
//                    dev_l.append(d.getName());
//                    dev_l.append("' type='");
//                    dev_l.append(d.getClass().getName());
//                    dev_l.append("' eventNode='");
//                    dev_l.append(XMPPComm.PREFIX + d.getName());
//                    dev_l.append("' jid='");
//                    dev_l.append(XMPPComm.PREFIX + d.getName());
//                    dev_l.append("'/>");
//                }
//                dev_l.append("</devices>");
//                dev_l.append("]]>");
                iq.getHeaders().put("Content-Type", "text/xml");
                iq.setBody(XmlDescription.generateXML()/*dev_l.toString()*/);
            } else {
                JSONArray jsArray = new JSONArray();
                for (Device d : DeviceManager.getDevices()) {
                    JSONObject device = new JSONObject();
                    try {
                        device.put("name", d.getName());
                        device.put("type", d.getClass().getName());
                        device.put("eventNode", XMPPComm.PREFIX + d.getName());
                        device.put("jid", XMPPComm.PREFIX + d.getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsArray.put(device);
                }
                iq.getHeaders().put("Content-Type", "application/json");
                iq.setBody(jsArray.toString());
            }
        }
        return iq;
    }

    private static XmppRest getDefaultOkIq(XmppRest xPacket) {
        XmppRest iq = new XmppRest();
        iq.setTo(xPacket.getFrom());
        iq.setPacketID(xPacket.getPacketID());
        iq.setResult(200, "OK");
        return iq;
    }
}
