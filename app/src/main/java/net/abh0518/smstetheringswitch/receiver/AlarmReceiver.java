package net.abh0518.smstetheringswitch.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TYPE_HOTSPOT_KEEP_ALIVE_TIMER = "TYPE_HOTSPOT_KEEP_ALIVE_TIMER";
    private static final int REQUEST_CODE_HOTSPOT_KEEP_ALIVE_TIMER = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(TYPE_HOTSPOT_KEEP_ALIVE_TIMER)){
            SmsReceiver.enableWifiHotspot(context, false, 0);
        }
	}

    public static void setHotspotKeepAliveTimer(Context context, int minutes){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TYPE_HOTSPOT_KEEP_ALIVE_TIMER, TYPE_HOTSPOT_KEEP_ALIVE_TIMER);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE_HOTSPOT_KEEP_ALIVE_TIMER, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Service.ALARM_SERVICE);
        if(minutes == 0){
            alarmManager.cancel(sender);
        }
        else{
            long time = System.currentTimeMillis() + minutes * 60 * 1000;
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, sender);
        }
    }
}
