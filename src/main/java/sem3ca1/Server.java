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
public class Server
{

    private boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String myIP;
    private int myPort;
    public static ArrayList<ClientHandling> clients = new ArrayList<>();
    private String removedClient = "";

    public static void main(String[] args)
    {

        Server server = new Server();
        server.RunServer("localhost", 8080);
    }

    private void RunServer(String ip, int port)
    {
        myIP = ip;
        myPort = port;
        try
        {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(myIP, myPort));

            while (keepRunning)
            {
                serverSocket.accept();

            }

        } catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void stopServer()
    {
        keepRunning = false;
    }

    //Adds client from the list of clients. and prints the name of the
    //added user
    private String addClient(ClientHandling client)
    {
        clients.add(client);
        return "UPDATE#" + client.getName(); 

    }

    //Removes client from the list of clients. and prints the deleted client.
    private String removeClient(ClientHandling client)
    {
        removedClient = client.getName();
        clients.remove(client); 
        return "DELETE#" + client.getName(); 
    }

//    public void notifyServer()
//    {
//        getAddedUser();
//    }

    public static String getClientList()
    {
        String clientList = "CLIENTS:";
        for (int i = 0; i < clients.size() - 1; i++)
        {
            clientList += clients.get(i).getUsername() + "#";
        }
        clientList += clients.get(clients.size() - 1).getUsername();
        return clientList;

    }

    public String getSuccessMsg(String toUser)
    {
        return "OK#" + getClientList();
    }

}
