package com.example.ipmsg20.udp;


import android.content.Context;
import android.util.Log;

import com.example.ipmsg20.utils.Data;
import com.example.ipmsg20.customobjects.Message;
import com.example.ipmsg20.utils.StoredData;

import org.json.JSONException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class listener extends Thread {

    int port = 2425;
    DatagramSocket socket;
    StoredData storedData;

    boolean running = true;
    Context context;
    Data data;

    public listener(int port, Context context) {
        super();
        this.port = port;
        this.context = context;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void updateState(String state) {

    }

    private void updatePrompt(String state) {

    }

    @Override
    public void run() {

        running = true;

        try {
            updateState("Starting UDP Server");
            socket = new DatagramSocket(port);

            updateState("UDP Server is Running");
            Log.d("UDP LISTENER : ","UDP Server is Running");

            storedData = new StoredData(context);
            data = Data.intitate(context);

            while(running) {
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int listen_port = packet.getPort();
                String body = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    body = Base64.getEncoder().encodeToString(packet.getData());
//                } else {
                body = new String(packet.getData(), packet.getOffset(), packet.getLength());
//                }

                Message msg = new Message().parse(body);
                msg.setAddress(address);

                updatePrompt("Request from: " + address + ":" + listen_port + " - " + body +  "\n");
                Log.d("UDP LISTENER : ","Request from: " + address + ":" + listen_port + " - " + body + "\n");

//                String dString = new Date().toString() + "\n"
//                        + "Your address " + address.toString() + ":" + String.valueOf(listen_port);
                String dString = getAcknowlegement(address.toString(),listen_port);
                buf = dString.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);

                Log.e("UDP LISTENER : ", "UDP Server send Packet - " + dString);

            }

            Log.e("UDP LISTENER : ", "UDP Server ended");

        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
                Log.d("UDP LISTENER : ", "socket.close()");
            }
        }

    }

    private String getAcknowlegement(String ip, int port) throws JSONException {
        String msg_body = "1";
        msg_body += ":" + storedData.getMsgIndex();
        msg_body += ":" + storedData.user_name;
        msg_body += ":" + data.device_manufactorer + "_" + data.device_model + "_" + data.device_id;
        msg_body += ":" + data.types.getString("fetch_user_response");
        msg_body += ":" + storedData.user_name;
        return msg_body;
    }

}