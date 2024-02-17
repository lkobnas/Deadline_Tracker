package com.example.deadline_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int appWidgetId : appWidgetIds){
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_display);
            MyCallback c = new MainActivity();
            String taskName = "Development in progress";
            String daysLeft = "10";
            /*
            try {
                taskName =c.getTaskName();
                daysLeft = Integer.toString(c.getDateLeft());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(taskName == null){
                taskName = "No Upcoming Task";
            }
            if(daysLeft == "-1"){
                daysLeft ="";
            }
*/
            views.setTextViewText(R.id.widget_taskName,taskName);
            views.setTextViewText(R.id.widget_daysLeft,daysLeft);
            views.setTextViewText(R.id.widget_text_daysLeft,"day(s) left");

            Intent intent = new Intent(context, WidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_text_daysLeft, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
