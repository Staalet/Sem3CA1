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
import java.util.logging.Level;
import java.util.logging.Logger;

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
                //Læser input fra client og deler inputtet op. 
                String[] inputFromClients = scan.nextLine().split("#");
                //Prevents upper/lowercase typos
                inputFromClients[0] = inputFromClients[0].toUpperCase();

                switch (inputFromClients[0]) {
                    //Setting username when logging in
                    case "LOGIN":
                        setUsername(inputFromClients[1]); //set username
                        sendMessage(server.addClient(this)); //add client to list on server.
                        sendMessage(server.getClientList()); //prints the clientlist th the 
                        break;

                    case "MSG":

                        //Sends a message to all the users online    
                        if (inputFromClients[1].equals("ALL")) {
                            sendMessage("MSG#" + getUsername() + "#" + inputFromClients[2]);
                            break;
                        } else {
                            server.sendSpecific(inputFromClients[1], inputFromClients[2], getUsername());
                        }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                sendMessage("DELETE#" + getUsername());
                server.removeClient(this);
                link.close();

            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//    private String sendMessageToUser(){
//     
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
