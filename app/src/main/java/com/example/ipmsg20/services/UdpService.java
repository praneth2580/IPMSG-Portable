package com.example.ipmsg20.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import androidx.annotation.Nullable;

import com.example.ipmsg20.udp.listener;

public class UdpService extends IntentService {

    listener ul;

    public UdpService(String name) {
        super(name);
    }
    public UdpService() {
        super("praneth");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        String type = bundle.getString("type");
        if (Objects.equals(type, "listener")) {
            Log.d("UDP LISTENER : ", "onHandleIntent: ");
            ul = new listener(2425,this);
            ul.run();
        }

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        if (ul != null) {
            ul.setRunning(false);
            ul = null;
        }
        return super.stopService(name);
    }
}