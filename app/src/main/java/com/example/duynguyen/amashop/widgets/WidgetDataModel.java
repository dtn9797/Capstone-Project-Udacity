package com.example.duynguyen.amashop.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.duynguyen.amashop.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WidgetDataModel {


    public static String TYPE_KEY = "type";
    public static String USER_ID_KEY = "id";
    public static String PRODUCTS_KEY = "p";

    public static void saveType (Context context, String type){

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(type);
        prefsEditor.putString(TYPE_KEY, json);
        prefsEditor.commit();
    }

    public static void saveUserId (Context context, String userId){

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userId);
        prefsEditor.putString(USER_ID_KEY, json);
        prefsEditor.commit();
    }
    public static void saveProducts (Context context, List<Product> products){

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(products);
        prefsEditor.putString(PRODUCTS_KEY, json);
        prefsEditor.commit();
    }

    public static String getType (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(TYPE_KEY, null);
        Type type = new TypeToken<String>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static List<Product> getProducts (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(PRODUCTS_KEY, null);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String getUserId (Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(USER_ID_KEY, null);
        Type type = new TypeToken<String>() {}.getType();
        return gson.fromJson(json, type);
    }
}
