/**
 * 
 */

package org.itea.neotiq.devicemodel;

import org.itea.neotiq.application.CustomApplication;
import org.itea.neotiq.devicemodel.DeviceManager.EventType;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hbriand
 */
public class VDEditText extends VirtualDevice implements RequestableDevice {

    @SuppressWarnings("unused")
    private static final String uiElementName = "Edit Text";
    private static Collection<VDEditText> instantiatedButtons = new ArrayList<VDEditText>();

    private TextWatcher tw;
    private boolean isInt = false;

    /**
     * @param name
     */
    public VDEditText() {
        this.uiElement = new EditText(CustomApplication.getAppContext());
        ((EditText)this.uiElement).addTextChangedListener(this.getTextWatcher());
        instantiatedButtons.add(this);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        if(this.isInt()){
            ((EditText)this.uiElement).setText("00000");
        } else {
            ((EditText)this.uiElement).setText(name);
        }
    }


    /**
     * @param isInt the isInt to set
     */
    public void setInt(boolean isInt) {
        this.isInt = isInt;
        ((EditText)this.uiElement).setInputType(InputType.TYPE_CLASS_NUMBER);
        ((EditText)this.uiElement).setText("00000");
    }

    /**
     * @return the isInt
     */
    public boolean isInt() {
        return isInt;
    }

    private TextWatcher getTextWatcher() {
        if (tw == null) {
            tw = new TextWatcher() {
                
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }
                
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }
                
                public void afterTextChanged(Editable arg0) {
                    VDEditText.afterTextChanged(arg0);
                }
            };
        }
        return tw;
    }

    protected static void afterTextChanged(Editable arg0) {
        for (VDEditText btn : VDEditText.instantiatedButtons) {
            EditText view = (EditText)btn.getView();
            
            if (view.getText() == arg0) { // Comparison on the memory address
                DeviceEvent de = new DeviceEvent(btn, EventType.EVT_TXT_CHANGED);
                de.setParameter("new_value", arg0.toString());
                btn.sendEvent(de);
                return;
            }
        }
        Log.e("VDEditText", "View not found onClickOccured");
    }

    public String getCurrentValue() {
        return ((EditText)this.uiElement).getText().toString();
    }

    @Override
    public String getSemanticAnnotation() {
        return this.isInt() ? "Int" : "Text";
    }

}
