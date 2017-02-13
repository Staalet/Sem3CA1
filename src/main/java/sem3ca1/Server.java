/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christian
 */
public class Server {
    
    private boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String myIP;
    private int myPort;
    ArrayList<ClientHandling> clients = new ArrayList<>();
    public static void main(String[] args) {
        
    }
    
    private void RunServer(String ip, int port){
            myIP = ip;
            myPort = port;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(myIP, myPort));
            
            while(keepRunning){
                serverSocket.accept();
                
            }
                
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void stopServer(){
        keepRunning = false;
    }
   
}
