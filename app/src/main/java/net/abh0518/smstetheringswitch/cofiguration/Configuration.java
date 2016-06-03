package net.abh0518.smstetheringswitch.cofiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Configuration {
	
	private static final String PREFS_NAME = "setting";
	private SharedPreferences sharedPreferences;
	
	public Configuration(Context context){
		this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
	}

	public void setEnableWifiHotspotKeyword(String switchOnText){
		Editor editor = this.sharedPreferences.edit();
		editor.putString("hotspotEnableKeyword", switchOnText);
		editor.commit();
	}

	public String getEnableWifiHotspotKeyword(){
		return this.sharedPreferences.getString("hotspotEnableKeyword", "");
	}

	public void setDisableWifiHotspotKeyword(String switchOffText){
		Editor editor = this.sharedPreferences.edit();
		editor.putString("hotspotDisableKeyword", switchOffText);
		editor.commit();
	}

	public String getDisableWifiHotspotKeyword(){
		return this.sharedPreferences.getString("hotspotDisableKeyword", "");
	}

	public void setReportSMS(boolean flag){
		Editor editor = this.sharedPreferences.edit();
		editor.putBoolean("reportSMS", flag);
		editor.commit();
	}

	public boolean getReportSMS(){
		return this.sharedPreferences.getBoolean("reportSMS", false);
	}

	public void setKeepAliveTime(int minutes){
		Editor editor = this.sharedPreferences.edit();
		editor.putInt("keepAlive", minutes);
		editor.commit();
	}

	public int getKeepAliveTime(){
		return this.sharedPreferences.getInt("keepAlive", 0);
	}


}
