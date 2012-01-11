/**
 * 
 */

package org.itea.neotiq.devicemodel;

import org.itea.neotiq.application.CustomApplication;
import org.itea.neotiq.devicemodel.DeviceManager.EventType;

import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hbriand
 */
public class VDToggle extends VirtualDevice implements RequestableDevice {

    @SuppressWarnings("unused")
    private static final String uiElementName = "Toggle Button";

    private static Collection<VDToggle> instantiatedButtons = new ArrayList<VDToggle>();

    private View.OnClickListener ocl;

    /**
     * @param name
     */
    public VDToggle() {
        this.uiElement = new ToggleButton(CustomApplication.getAppContext());
        ((ToggleButton)this.uiElement).setOnClickListener(this.getOcl());
        instantiatedButtons.add(this);
    }

    public View.OnClickListener getOcl() {
        if (ocl == null) {
            ocl = new View.OnClickListener() {
                public void onClick(View arg0) {
                    VDToggle.onClickOccured(arg0);
                }
            };
        }
        return ocl;
    }

    protected static void onClickOccured(View arg0) {
        for (VDToggle btn : VDToggle.instantiatedButtons) {
            if (btn.getView() == arg0) { // Comparison on the memory address
                DeviceEvent de = new DeviceEvent(btn, EventType.EVT_ON_TOGGLE);
                de.setParameter(
                        "from_value",
                        (!((ToggleButton)btn.uiElement).isChecked() ? ((ToggleButton)btn.uiElement)
                                .getTextOn() : ((ToggleButton)btn.uiElement).getTextOff()));
                de.setParameter(
                        "to_value",
                        (((ToggleButton)btn.uiElement).isChecked() ? ((ToggleButton)btn.uiElement)
                                .getTextOn() : ((ToggleButton)btn.uiElement).getTextOff()));
                btn.sendEvent(de);
                return;
            }
        }
        Log.e("VDToggle", "View not found onClickOccured");
    }

    public String getCurrentValue() {
        return (String)(((ToggleButton)this.uiElement).isChecked() ? ((ToggleButton)this.uiElement)
                .getTextOn() : ((ToggleButton)this.uiElement).getTextOff());
    }

    @Override
    public String getSemanticAnnotation() {
        return "Switch";
    }

}
