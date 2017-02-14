/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christian
 */
public class Server {

    private boolean keepRunning = true;
    private ServerSocket serverSocket;
    private Socket socket;
    private String myIP;
    private int myPort;
    public ArrayList<ClientHandling> clients = new ArrayList<>();
    private String removedClient = "";

    public static void main(String[] args) {

        Server server = new Server();
        server.RunServer("localhost", 8081);
    }

    private void RunServer(String ip, int port) {
        myIP = ip;
        myPort = port;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(myIP, myPort));

            System.out.println("Server listens on " + myPort + " at " + myIP);
            
            while (keepRunning) {
                socket = serverSocket.accept();
                ClientHandling cl = new ClientHandling(socket, this);
                cl.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void stopServer() {
        keepRunning = false;
    }

    //Adds client from the list of clients. and prints the name of the
    //added user
    public String addClient(ClientHandling client) {
        clients.add(client);
        return "UPDATE#" + client.getName();

    }

    //Removes client from the list of clients. and prints the deleted client.
    public void removeClient(ClientHandling client) {
        removedClient = client.getName();
        clients.remove(client);
    }

//    public void notifyServer()
//    {
//        getAddedUser();
//    }
    public String getClientList() {
        String clientList = "CLIENTS:";
        for (int i = 0; i < clients.size() - 1; i++) {
            clientList += clients.get(i).getUsername() + "#";
        }
        clientList += clients.get(clients.size() - 1).getUsername();
        return clientList;

    }

    public String getSuccessMsg(String toUser) {
        return "OK#" + getClientList();
    }

}
