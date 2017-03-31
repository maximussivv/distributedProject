/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Mark
 */
public class DoorServer {
    
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
                ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "DoorServer", 9090, "can't be empty?");
                jmdns.registerService(serviceInfo);
                System.out.println("Service is registered");
                while (true) {
                    Socket socket = listener.accept();
                    try {
                        PrintWriter out
                                = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Door status");
                        
                    } finally {
                        socket.close();
                    }
                }
            } finally {
                listener.close();
            }
        }   catch (IOException ex) {
            Logger.getLogger(Fanserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    
    
    

