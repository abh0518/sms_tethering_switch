package net.abh0518.smstetheringswitch.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import net.abh0518.smstetheringswitch.R;
import net.abh0518.smstetheringswitch.cofiguration.Configuration;
import net.abh0518.smstetheringswitch.receiver.AlarmReceiver;
import net.abh0518.smstetheringswitch.receiver.SmsReceiver;

public class MainActivity extends Activity {

    ToggleButton mTetheringSwitch;
    EditText mSwitchOnText;
    EditText mSwitchOffText;
    EditText mKeepAliveText;
    ToggleButton mReportSmsToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Configuration configuration = new Configuration(this);

        mTetheringSwitch = (ToggleButton)findViewById(R.id.tethering_toggle);
        mTetheringSwitch.setChecked(SmsReceiver.getWifiHotspotEnabled(this));
        mTetheringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SmsReceiver.enableWifiHotspot(MainActivity.this, true, configuration.getKeepAliveTime());
                }
                else{
                    SmsReceiver.enableWifiHotspot(MainActivity.this, false, configuration.getKeepAliveTime());
                }
            }
        });

        mSwitchOnText = (EditText)findViewById(R.id.switch_on_text);
        mSwitchOnText.setText(configuration.getEnableWifiHotspotKeyword()+"");
        mSwitchOffText = (EditText)findViewById(R.id.switch_off_text);
        mSwitchOffText.setText(configuration.getDisableWifiHotspotKeyword()+"");
        mKeepAliveText = (EditText)findViewById(R.id.turn_keep_alive_time_text);
        mKeepAliveText.setText(configuration.getKeepAliveTime()+"");

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration.setEnableWifiHotspotKeyword(mSwitchOnText.getText().toString());
                configuration.setDisableWifiHotspotKeyword(mSwitchOffText.getText().toString());
                int keepAliveTime = Integer.parseInt(mKeepAliveText.getText().toString());
                configuration.setKeepAliveTime(keepAliveTime);
                AlarmReceiver.setHotspotKeepAliveTimer(MainActivity.this, keepAliveTime);
            }
        });

        mReportSmsToggle = (ToggleButton)findViewById(R.id.report_sms_toggle);
        mReportSmsToggle.setChecked(configuration.getReportSMS());
        mReportSmsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                configuration.setReportSMS(isChecked);
            }
        });

    }

}
