package com.example.ipmsg20.customobjects

import java.net.InetAddress

abstract class Sender {

    abstract var sid: Integer;
    abstract var name: String;
    abstract var group: String;
    abstract var address: InetAddress;

}

