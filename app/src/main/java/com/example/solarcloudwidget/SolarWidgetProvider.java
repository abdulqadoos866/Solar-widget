package com.example.solarcloudwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class SolarWidgetProvider extends AppWidgetProvider {
    public static void updateAll(Context context) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        ComponentName cn = new ComponentName(context, SolarWidgetProvider.class);
        update(context, mgr, mgr.getAppWidgetIds(cn));
    }
    private static void update(Context c, AppWidgetManager mgr, int[] ids) {
        for (int id : ids) {
            RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.widget_layout);
            v.setTextViewText(R.id.pv, "☀ PV     " + DataStore.get(c,"pv","--") + " kW");
            v.setTextViewText(R.id.battery, "🔋 Battery   " + DataStore.get(c,"soc","--") + "%  • " + DataStore.get(c,"battery","--") + " kW");
            v.setTextViewText(R.id.smart, "⚡ Smart Load  " + DataStore.get(c,"smart","--") + " kW");
            v.setTextViewText(R.id.backup, "🏠 Backup    " + DataStore.get(c,"backup","--") + " kW");
            v.setTextViewText(R.id.grid, "🔌 Grid      " + DataStore.get(c,"grid","--") + " kW");
            v.setTextViewText(R.id.updated, DataStore.get(c,"updated","Waiting for SolarCloud…"));
            Intent launch = c.getPackageManager().getLaunchIntentForPackage("com.solarcloud");
            if (launch != null) {
                PendingIntent pi = PendingIntent.getActivity(c, 7, launch, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                v.setOnClickPendingIntent(R.id.title, pi);
            }
            mgr.updateAppWidget(id, v);
        }
    }
    @Override public void onUpdate(Context c, AppWidgetManager mgr, int[] ids) { update(c,mgr,ids); }
    @Override public void onEnabled(Context c) { updateAll(c); }
}
