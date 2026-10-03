package com.example.solarcloudwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_main);
        Button a=findViewById(R.id.accessibility), o=findViewById(R.id.openSolar); TextView s=findViewById(R.id.status);
        a.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        o.setOnClickListener(v -> { Intent i=getPackageManager().getLaunchIntentForPackage("com.solarcloud"); if(i!=null) startActivity(i); });
        s.setText("Status: enable the service, then open SolarCloud Overview. The widget will update from visible values.");
    }
}
