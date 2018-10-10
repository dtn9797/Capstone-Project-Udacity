package com.example.duynguyen.amashop.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.duynguyen.amashop.R;
import com.example.duynguyen.amashop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class WidgetUpdateService extends IntentService {
    public static final String ACTION_UPDATE_LIST_VIEW = "update_app_widget_list";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_LIST_VIEW.equals(action)) {
                String userId = intent.getStringExtra(WidgetDataModel.USER_ID_KEY);
                String productType = intent.getStringExtra(WidgetDataModel.TYPE_KEY);
                List<Product> products = intent.getParcelableArrayListExtra(WidgetDataModel.PRODUCTS_KEY);
                handleActionUpdateListView(products, userId, productType);
            }
        }
    }

    private void handleActionUpdateListView(List<Product> products, String userId, String productType) {
        if (products != null) {
            WidgetDataModel.saveProducts(this, products);
        }
        if (userId != null) {
            WidgetDataModel.saveUserId(this, userId);
        }
        if (productType != null) {
            WidgetDataModel.saveType(this, productType);

        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));

        NewAppWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.products_list);
    }

    public static void startActionUpdateListView(Context context, List<Product> products, String productType, String userId) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(WidgetUpdateService.ACTION_UPDATE_LIST_VIEW);
        intent.putExtra(WidgetDataModel.USER_ID_KEY, userId);
        intent.putExtra(WidgetDataModel.TYPE_KEY, productType);
        intent.putParcelableArrayListExtra(WidgetDataModel.PRODUCTS_KEY, (ArrayList<? extends Parcelable>) products);
        context.startService(intent);
    }
}
