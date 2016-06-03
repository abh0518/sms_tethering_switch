package net.abh0518.smstetheringswitch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import net.abh0518.smstetheringswitch.R;
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
                String from = msgs[i].getOriginatingAddress();

                if(message != null && !message.equals("")){
                    Configuration configuration = new Configuration(context);

                    boolean tetheringEnabled = false;
                    if(configuration.getDisableWifiHotspotKeyword().equals(message)){
                        enableWifiHotspot(context, false, configuration.getKeepAliveTime());
                        tetheringEnabled = false;
                    }
                    else if(configuration.getEnableWifiHotspotKeyword().equals(message)){
                        enableWifiHotspot(context, true);
                        tetheringEnabled = true;
                    }

                    if(configuration.getReportSMS()){
                        sendSms(from, tetheringEnabled ? context.getResources().getString(R.string.tethering_switch_on)
                                 + " Keep alive time is " + configuration.getKeepAliveTime() + " minutes."
                                : context.getResources().getString(R.string.tethering_switch_off));
                    }
                }
            }
        }
	}

    public static void enableWifiHotspot(Context context, boolean flag, int keepAliveMinutes){
        enableWifiHotspot(context, flag);
        AlarmReceiver.setHotspotKeepAliveTimer(context, keepAliveMinutes);
    }

    private static void enableWifiHotspot(Context context, boolean flag) {
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

    public static boolean getWifiHotspotEnabled(Context context){
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getMethod("isWifiApEnabled");
            return (Boolean)method.invoke(wifimanager);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void sendSms(String phoneNumber, String msg){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
    }

}
