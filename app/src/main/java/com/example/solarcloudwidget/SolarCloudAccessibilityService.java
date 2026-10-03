package com.example.solarcloudwidget;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolarCloudAccessibilityService extends AccessibilityService {
    private static final Pattern KW = Pattern.compile("(-?\\d+(?:\\.\\d+)?)\\s*kW", Pattern.CASE_INSENSITIVE);
    private static final Pattern PCT = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*%?");

    @Override public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!"com.solarcloud".equals(event.getPackageName() == null ? "" : event.getPackageName().toString())) return;
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;
        List<String> texts = new ArrayList<>(); collect(root, texts);
        String all = String.join(" | ", texts);
        parseAndSave(all);
    }
    private void collect(AccessibilityNodeInfo n, List<String> out) {
        if (n == null) return;
        CharSequence t=n.getText(); if(t!=null && t.length()>0) out.add(t.toString().trim());
        CharSequence d=n.getContentDescription(); if(d!=null && d.length()>0) out.add(d.toString().trim());
        for(int i=0;i<n.getChildCount();i++) collect(n.getChild(i),out);
    }
    private void parseAndSave(String s) {
        String pv = valueNear(s,"PV");
        String smart = valueNear(s,"Smart Load");
        String backup = valueNear(s,"Backup Load");
        String grid = valueNear(s,"Grid");
        String battery = valueNear(s,"Battery");
        String soc = percentNear(s,"Battery");
        if (pv!=null) DataStore.put(this,"pv",pv);
        if (smart!=null) DataStore.put(this,"smart",smart);
        if (backup!=null) DataStore.put(this,"backup",backup);
        if (grid!=null) DataStore.put(this,"grid",grid);
        if (battery!=null) DataStore.put(this,"battery",battery);
        if (soc!=null) DataStore.put(this,"soc",soc);
        DataStore.put(this,"updated", "Updated " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        SolarWidgetProvider.updateAll(this);
    }
    private String valueNear(String s, String label) {
        int i=s.toLowerCase(Locale.ROOT).indexOf(label.toLowerCase(Locale.ROOT));
        if(i<0) return null;
        int end=Math.min(s.length(),i+180);
        Matcher m=KW.matcher(s.substring(i,end));
        return m.find()?m.group(1):null;
    }
    private String percentNear(String s, String label) {
        int i=s.toLowerCase(Locale.ROOT).indexOf(label.toLowerCase(Locale.ROOT));
        if(i<0) return null;
        int end=Math.min(s.length(),i+180);
        String x=s.substring(i,end);
        Matcher m=Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*%").matcher(x);
        return m.find()?m.group(1):null;
    }
    @Override public void onInterrupt() { }
}
