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
            String[] inputFromClients = scan.nextLine().split("#");
                //Prevents upper/lowercase typos
                inputFromClients[0] = inputFromClients[0].toUpperCase();
                
                switch (inputFromClients[0]) {
                    //Setting username when logging in
                    case "LOGIN":
                        setUsername(inputFromClients[1]);
                        
                       // if (Server.clients.) {
                            server.addClient(this);
                            pw.println("UPDATE#" + getUsername());
                            pw.flush();
                            break;
//                        } else {
//                            pw.println("FAIL");
//                            break;
//                        }

                    //Sends message to a specific user    
                    case "MSG":

                        if (inputFromClients[1].equals(username)) {
                            for (ClientHandling client : server.clients) {
                                if (client.equals(username)) {
                                    pw.println(inputFromClients[2]);
                                    pw.flush();
                                    break;
                                } else{
                                    pw.println("typo try again");
                                }
                            }
                        }
                        //Sends a message to all the users online    
                        if (inputFromClients[1].equals("ALL")) {
                            pw.println("MSG#" + getUsername() + "#" + inputFromClients[2]);
                            pw.flush();
                            break;
                        } else {
                            pw.println("typo try again");
                            pw.flush();
                        }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                pw.println("DELETE#" + getUsername());
                pw.flush();
                server.removeClient(this);
                link.close();

            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//    private String sendMessageToUser(){
//        
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
