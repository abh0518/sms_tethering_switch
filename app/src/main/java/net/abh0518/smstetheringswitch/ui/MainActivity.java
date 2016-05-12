package net.abh0518.smstetheringswitch.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import net.abh0518.smstetheringswitch.R;
import net.abh0518.smstetheringswitch.cofiguration.Configuration;


public class MainActivity extends Activity {

    EditText mSwitchOnText;
    EditText mSwitchOffText;
    ToggleButton mReportSmsToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Configuration configuration = new Configuration(this);

        mSwitchOnText = (EditText)findViewById(R.id.switch_on_text);
        mSwitchOnText.setText(configuration.getEnableWifiHotspotKeyword());
        mSwitchOffText = (EditText)findViewById(R.id.switch_off_text);
        mSwitchOffText.setText(configuration.getDisableWifiHotspotKeyword());

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration.setEnableWifiHotspotKeyword(mSwitchOnText.getText().toString());
                configuration.setDisableWifiHotspotKeyword(mSwitchOffText.getText().toString());
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
