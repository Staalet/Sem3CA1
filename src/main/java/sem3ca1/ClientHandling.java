/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;    

/**
 *
 * @author christian
 */
public class ClientHandling extends Thread {

    Socket link;
    private String username;
    private Server server;
    PrintWriter pw;
    Scanner scan;

    public ClientHandling(Socket link, Server server) {
        this.link = link;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            //creates an inputstream.
            pw = new PrintWriter(link.getOutputStream());
            //creates a new inputstream.
            scan = new Scanner(link.getInputStream());

            while (true) {
                String[] inputFromClients = scan.nextLine().split("#");//Læser input fra client og deler inputtet op. 
                inputFromClients[0] = inputFromClients[0].toUpperCase();//Prevents upper/lowercase typos.
                inputFromClients[1] = inputFromClients[1].toUpperCase();//writes all names in uppercase.
                switch (inputFromClients[0]) {
                    case "LOGIN":
                        setUsername(inputFromClients[1]); //set username
                        server.addClient(this); //add client to list on server.
                        sendMessage(server.getClientList()); //prints the clientlist th the 
                        break;

                    case "MSG":
                           
                        if (inputFromClients[1].equals("ALL")) {
                        server.sendToAll(inputFromClients[2]); //Sends a message to all the users online 
                            break;
                        } else {
                            server.sendSpecific(inputFromClients[1], inputFromClients[2], getUsername());
                        }
                }
            }
        } catch (IOException ex) {
            System.out.println("io exception in clienthandler switch" + ex);
        } catch (Exception e2) {
            server.removeClient(this);
            try {
                link.close();
                System.out.println("A client has disconnected");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    //Writes the message, flushes and print to Output "Send message"
    public void sendMessage(String msg) {
        pw.println(msg);
        pw.flush();
    }
//    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public void setUsername(String aUsername) {
        username = aUsername;
    }

//    public void deleteUser(Socket link, ClientHandling client) throws IOException {
//        PrintWriter pw;
//        String clientName = client.getName();
//        pw = new PrintWriter(link.getOutputStream());
//        Server.removeClient();
//        pw.println("DELETE#" + clientName);
//    }
}
