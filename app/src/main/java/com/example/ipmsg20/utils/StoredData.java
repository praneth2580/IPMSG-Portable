package com.example.ipmsg20.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ipmsg20.R;

public class StoredData {
    public String user_name;
    private Context context;
    private int msg_index;
    private SharedPreferences sharedPreferences;

    public StoredData(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.stored_data),Context.MODE_PRIVATE);
        msg_index = this.sharedPreferences.getInt(context.getString(R.string.msg_index),0);
    }

    public String getMsgIndex() {
        msg_index++;
        sharedPreferences.edit().putInt(context.getString(R.string.msg_index),msg_index);
        return String.valueOf(msg_index);
    }
}
