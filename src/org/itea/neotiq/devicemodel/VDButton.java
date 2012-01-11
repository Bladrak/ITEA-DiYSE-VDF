/**
 * 
 */
package org.itea.neotiq.devicemodel;

import org.itea.neotiq.application.CustomApplication;
import org.itea.neotiq.devicemodel.DeviceManager.EventType;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hbriand
 *
 */
public class VDButton extends VirtualDevice { // VDButton is event-only : no need to implement RequestableDevice
    
    @SuppressWarnings("unused")
    private static final String uiElementName = "Button";
    private static Collection<VDButton> instantiatedButtons = new ArrayList<VDButton>();
    private View.OnClickListener ocl;

    /**
     * @param name
     */
    public VDButton() {
        this.uiElement = new Button(CustomApplication.getAppContext());
        ((Button)this.uiElement).setOnClickListener(this.getOcl());
        instantiatedButtons.add(this);
    }

    /* (non-Javadoc)
     * @see org.itea.neotiq.devicemodel.Device#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        super.setName(name);
        ((Button)this.uiElement).setText(name);
    }

    public View.OnClickListener getOcl() {
        if (ocl == null) {
            ocl = new View.OnClickListener() {
                public void onClick(View arg0) {
                    VDButton.onClickOccured(arg0);
                }
            };
        }
        return ocl;
    }

    protected static void onClickOccured(View arg0) {
        for(VDButton btn : VDButton.instantiatedButtons ) {
            if (btn.getView() == arg0) { // Comparison on the memory address
                DeviceEvent de = new DeviceEvent(btn, EventType.EVT_ON_CLICK);
                btn.sendEvent(de);
                return;
            }
        }
        Log.e("VDButton", "View not found onClickOccured");
    }

    @Override
    public String getSemanticAnnotation() {
        return "Button";
    }

}
