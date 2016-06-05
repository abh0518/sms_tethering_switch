package net.abh0518.smstetheringswitch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import net.abh0518.smstetheringswitch.R;
import net.abh0518.smstetheringswitch.cofiguration.Configuration;
import net.abh0518.smstetheringswitch.service.WifiHotspotService;


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
                        WifiHotspotService.enableWifiHotspot(context, false, configuration.getKeepAliveTime());
                        tetheringEnabled = false;
                    }
                    else if(configuration.getEnableWifiHotspotKeyword().equals(message)){
                        WifiHotspotService.enableWifiHotspot(context, true, configuration.getKeepAliveTime());
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

    private static void sendSms(String phoneNumber, String msg){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
    }

}
