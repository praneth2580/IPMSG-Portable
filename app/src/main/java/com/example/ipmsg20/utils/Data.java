package com.example.ipmsg20.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ResourceBundle;

public class Data {
    public String device_manufactorer,device_model,device_id;
    public JSONObject types;
    private static Context context = null;
    private static Data data = null;
    public RoomDatabase db;

    private static void Data() {}

    public static Data intitate(Context context1) {
        if (data == null) {
            data = new Data();
            data.run();
        }
        context = context1;
        return data;
    }

    public void setDB(RoomDatabase db) {
        this.db = db;
    }

    private void run() {
        device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        device_model = Build.MODEL;
        device_manufactorer = Build.MANUFACTURER;
    }

}
