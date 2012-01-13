/**
 * 
 */

package org.itea.neotiq.activities;

import org.itea.neotiq.R;
import org.itea.neotiq.devicemodel.DeviceManager;
import org.itea.neotiq.devicemodel.DeviceNotSupportedException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @author hbriand
 */
public class DeviceAdder extends Activity {
    public static final String TAG = "SmartDeviceAdder";

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Activity State: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_adder);

        Spinner v = (Spinner)findViewById(R.id.spnDevice);
        String[] devices = (String[])DeviceManager.getDeviceTypes().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, devices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v.setAdapter(adapter);

        Button add = (Button)findViewById(R.id.btnOk);
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                btnAddClicked();
            }
        });
    }

    protected void btnAddClicked() {
        Spinner v = (Spinner)findViewById(R.id.spnDevice);
        addDevice((String)v.getSelectedItem(), ((EditText)findViewById(R.id.txtDevice)).getText().toString());
    }

    protected void addDevice(String text, String name) {
        try {
            DeviceManager.addDevice(text, name);
        } catch (DeviceNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Intent i = new Intent(this, DevicesView.class);
        startActivity(i);
    }

}
