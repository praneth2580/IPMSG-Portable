package com.example.ipmsg20.customobjects;

import com.example.ipmsg20.room.Database;
import com.example.ipmsg20.room.MessageDao;

import java.net.InetAddress;

public class Message {

    public int first = 0;
    public int index = 0;
    public String sender = null;
    public String sender_group = null;
    public String msg = null;
    public InetAddress address = null;

    public Message() {}

    public Message parse(String msg_string) {

        String[] msg_parts = msg_string.split(":");
        this.first = Integer.parseInt(msg_parts[0]);
        this.index = Integer.parseInt(msg_parts[1]);
        this.sender = msg_parts[2];
        this.sender_group = msg_parts[3];
        this.msg = msg_parts[4];
        return this;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void save(Database db) {
        MessageDao dao = db.messageDao();
        dao.addMessage( new com.example.ipmsg20.room.Message(
                        0,
                        String.valueOf(first),
                        sender,
                        sender_group,
                        address.toString()
                )
            );
    }

}
