package net.abh0518.smstetheringswitch.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.abh0518.smstetheringswitch.R;
import net.abh0518.smstetheringswitch.cofiguration.Configuration;


public class MainActivity extends Activity {

    EditText mSwitchOnText;
    EditText mSwitchOffText;

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
    }

}
