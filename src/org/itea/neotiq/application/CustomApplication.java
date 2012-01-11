/**
 * 
 */
package org.itea.neotiq.application;

import org.itea.neotiq.devicemodel.DeviceDelegate;
import org.itea.neotiq.xmpp.XMPPComm;

import android.app.Application;
import android.content.Context;

/**
 * @author hbriand
 *
 */
public class CustomApplication extends Application {

        private static Context context;
        private static DeviceDelegate defaultDelegate;
        
        @Override
        public void onCreate() {
            super.onCreate();
            CustomApplication.context = getApplicationContext();
            CustomApplication.defaultDelegate = XMPPComm.getInstance();
//            CustomApplication.defaultDelegate = new LogDeviceDelegate();
        }
        
        public static Context getAppContext() {
            return CustomApplication.context;
        }

        public static DeviceDelegate getDefaultDelegate() {
            return defaultDelegate;
        }

}
