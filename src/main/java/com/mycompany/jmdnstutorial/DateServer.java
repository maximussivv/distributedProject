package com.mycompany.jmdnstutorial;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * A TCP server that runs on port 9090. When a client connects, it sends the
 * client the current date and time, then closes the connection with that
 * client. Arguably just about the simplest server you can write.
 */
public class DateServer {

    /**
     * Runs the server.
     */
    public static void main(String[] args) {
        try {
            ServerSocket listener = new ServerSocket(9090);
            try {
                
                // Create a JmDNS instance
                JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
                
                // Register a service
                ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "DateServer", 9090, "can't be empty?");
                jmdns.registerService(serviceInfo);
                System.out.println("Service is registered");
                while (true) {
                    Socket socket = listener.accept();
                    try {
                        PrintWriter out
                                = new PrintWriter(socket.getOutputStream(), true);
                        out.println(new Date().toString());
                        
                    } finally {
                        socket.close();
                    }
                }
            } finally {
                listener.close();
            }
        }   catch (IOException ex) {
            Logger.getLogger(DateServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
