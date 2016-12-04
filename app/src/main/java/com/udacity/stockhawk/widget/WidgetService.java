package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * Created by sagar on 3/12/16.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            Cursor cursor;
            @Override
            public void onCreate() {
                cursor=getContentResolver().query(
                        Contract.Quote.uri,
                        Contract.Quote.QUOTE_COLUMNS,
                        null,null,null
                );
            }

            @Override
            public void onDataSetChanged() {
                cursor=getContentResolver().query(
                        Contract.Quote.uri,
                        Contract.Quote.QUOTE_COLUMNS,
                        null,null,null
                );
            }

            @Override
            public void onDestroy() {
                if (cursor!=null)
                    cursor.close();
            }

            @Override
            public int getCount() {
                return (this.cursor != null) ? this.cursor.getCount() : 0;
            }

            @Override
            public RemoteViews getViewAt(int i) {
                RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.list_item_quote);
                cursor.moveToPosition(i);
                String symbol=cursor.getString(Contract.Quote.POSITION_SYMBOL);
                String price =String.valueOf(cursor.getFloat(Contract.Quote.POSITION_PRICE));
                String change=String.valueOf(cursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE)/100);
                remoteViews.setTextViewText(R.id.symbol,symbol);
                remoteViews.setTextViewText(R.id.price,price);
                remoteViews.setTextViewText(R.id.change,change);

                Bundle bundle=new Bundle();
                bundle.putString(Contract.Quote.COLUMN_SYMBOL,symbol);
                bundle.putString(Contract.Quote.COLUMN_PRICE,price);
                bundle.putString(Contract.Quote.COLUMN_PERCENTAGE_CHANGE,change);

                Intent in=new Intent();
                in.putExtras(bundle);
                remoteViews.setOnClickFillInIntent(R.id.list_id_item_quote,in);

                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return cursor.getInt(0);
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

}
