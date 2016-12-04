package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;

/**
 * Created by sagar on 3/12/16.
 */

public class WidgetProvider extends AppWidgetProvider {

    public static final String INTENT_ACTION = "com.udacity.stockhawk.widget.WidgetProvider.INTENT_ACTION";
    public static final String EXTRA_SYMBOL = "com.udacity.stockhawk.widget.WidgetProvider.EXTRA_SYMBOL";
    public static final String EXTRA_PRICE = "com.udacity.stockhawk.widget.WidgetProvider.EXTRA_PRICE";
    public static final String EXTRA_CURRENCY = "com.udacity.stockhawk.widget.WidgetProvider.EXTRA_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i< appWidgetIds.length ; i++){

            /**
             * Intent that defines service to perform for widgets.
             */

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            /**
             * generating RemoteViews for the widget. Equivalent to "View".
             */
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.stock_widget_layout);

            //This method is deprecated but, the other method with only two parameters doesn't seem to work!
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.lv_stock_widget_layout, intent);
            remoteViews.setEmptyView(R.id.lv_stock_widget_layout, R.id.tv_empty_stocks_widget_layout);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
