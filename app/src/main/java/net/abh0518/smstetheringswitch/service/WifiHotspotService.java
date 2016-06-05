package net.abh0518.smstetheringswitch.service;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import net.abh0518.smstetheringswitch.receiver.AlarmReceiver;

import java.lang.reflect.Method;

/**
 * Created by abh0518 on 2016. 6. 5..
 */

public class WifiHotspotService {
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
}
