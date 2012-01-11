
package org.itea.neotiq.activities;

import org.itea.neotiq.R;
import org.itea.neotiq.devicemodel.Device;
import org.itea.neotiq.devicemodel.DeviceManager;
import org.itea.neotiq.devicemodel.VirtualDevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DevicesView extends Activity {
    public static final String TAG = "SmartDevice";

    private Button mAddDeviceBtn;

    // private Device mTglBtn;
    private TableLayout mDevicesView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAddDeviceBtn = (Button)findViewById(R.id.btnAddDevice);
        mAddDeviceBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i(TAG, "btnAddDevice clicked");
                launchAdder();
            }
        });

        mDevicesView = (TableLayout)findViewById(R.id.devicesView);
        this.populateDevices();
    }

    private void populateDevices() {
        mDevicesView.removeAllViews();
        for (Device d : DeviceManager.getDevices()) {
            TableRow tmp = (TableRow)d.getView().getParent();
            if (tmp != null)
                tmp.removeAllViews();
            TableRow tr = new TableRow(getBaseContext());
            tr.addView(d.getView());
            Button delBtn = new Button(getBaseContext());
            delBtn.setTag(d);
            delBtn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Log.i(TAG, "btnClicked");
                    sendDeleteToDevice(v);
                }
            });
            delBtn.setText("Delete");
            delBtn.setPadding(30, 0, 30, 0);
            tr.addView(delBtn);
            mDevicesView.addView(tr);
        }
    }

    protected void sendDeleteToDevice(View v) {
        // ((VirtualDevice) v.getTag()).sendDelete();
        // DeviceManager.removeDevice(((Device) v.getTag()));
        // ((TableRow) ((Device) v.getTag()).getView().getParent())
        // .removeAllViews(); // Removes the device and button from the row
    }

    // /* (non-Javadoc)
    // * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
    // */
    // @Override
    // protected void onSaveInstanceState(Bundle outState) {
    // outState.putParcelableArrayList("devicesList",
    // DeviceManager.getDevicesList());
    //
    //
    // super.onSaveInstanceState(outState);
    // }
    //
    private void launchAdder() {
        Intent i = new Intent(this, DeviceAdder.class);
        startActivity(i);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        // for (Device d : DeviceManager.getDevices()) {
        // d.sendDelete();
        // }
        super.finish();
    }

}
