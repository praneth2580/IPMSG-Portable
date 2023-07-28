package com.example.ipmsg20.udp;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class send {


    public static void send(String ip, String msg) {
        int port = 2425;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try (DatagramSocket serverSocket = new DatagramSocket()) {
            for (int i = 0; i < 5; i++) {

                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length,inetAddress,port);

                try {
                    serverSocket.send(msgPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Log.i("DEBUG", "Server sent packet with msg: " + msg);

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

}
