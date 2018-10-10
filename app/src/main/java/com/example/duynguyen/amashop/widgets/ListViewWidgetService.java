package com.example.duynguyen.amashop.widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.duynguyen.amashop.R;
import com.example.duynguyen.amashop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(getApplicationContext());
    }
}
class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    List<Product> mProducts= new ArrayList<>();



    public AppWidgetListView(Context mContext){
        this.mContext = mContext;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mProducts = WidgetDataModel.getProducts(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget);

        remoteViews.setTextViewText(R.id.widget_product_title_tv,mProducts.get(position).getName());
        remoteViews.setTextViewText(R.id.widget_product_price_tv,"$ "+mProducts.get(position).getPrice());

        return  remoteViews;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}