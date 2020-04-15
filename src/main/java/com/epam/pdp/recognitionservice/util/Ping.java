package com.epam.pdp.recognitionservice.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Ping {
    public static boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port));
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
