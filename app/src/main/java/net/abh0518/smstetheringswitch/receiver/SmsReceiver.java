package net.abh0518.smstetheringswitch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import net.abh0518.smstetheringswitch.cofiguration.Configuration;

import java.lang.reflect.Method;


public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String message =  msgs[i].getMessageBody().toString();

                Log.d("tethering", message);
                if(message != null && !message.equals("")){
                    Configuration configuration = new Configuration(context);
                    if(configuration.getDisableWifiHotspotKeyword().equals(message)){
                        enableWifiHotspot(context, false);
                    }
                    else if(configuration.getEnableWifiHotspotKeyword().equals(message)){
                        enableWifiHotspot(context, true);
                    }
                }
            }
        }
	}

    public void enableWifiHotspot(Context context, boolean flag) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            if(flag){
                wifimanager.setWifiEnabled(false);
                method.invoke(wifimanager, wificonfiguration, true);
            }
            else{
                method.invoke(wifimanager, wificonfiguration, false);
                wifimanager.setWifiEnabled(true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
