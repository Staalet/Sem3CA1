/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christian
 */
public class ClientHandling extends Thread {

    Socket link;
    private static String username;
    private Server server = new Server();

    public ClientHandling(Socket link, Server serv) {
        this.server = serv;
        this.link = link;
    }

    @Override
    public void run() {

        try {

            PrintWriter pw = new PrintWriter(link.getOutputStream());
            Scanner scan = new Scanner(link.getInputStream());
            String[] inputFromClients = scan.nextLine().split("#");
            String msg = "";
            String tmpMsg = "";
            String output = "";

            while (!msg.equals("")) {
                msg = scan.nextLine();
                tmpMsg = msg;
                inputFromClients[0] = inputFromClients[0].toUpperCase();
                switch (inputFromClients[0]) {

                    //Setting username when logging in
                    case "LOGIN":
                        ClientHandling.setUsername(inputFromClients[1]);

                        if (Server.clients.contains(this)) {
                            Server.addClient(this);
                            pw.println("UPDATE#" + this.getName());
                            break;
                        } else {
                            pw.println("FAIL");
                            break;
                        }

                    //Sends message to a specific user    
                    case "MSG":

                        if (inputFromClients[1] == username) {

                            for (ClientHandling client : Server.clients) {
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
                        if (msg.startsWith("ALL")) {
                            scan.nextLine();
                            pw.println(Arrays.toString(inputFromClients));
                            pw.flush();
                            break;
                        } else {
                            pw.println("typo try again");
                        }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                deleteUser(link, this);

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
    public static String getUsername() {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    public void deleteUser(Socket link, ClientHandling client) throws IOException {
        PrintWriter pw;
        String clientName = client.getName();
        pw = new PrintWriter(link.getOutputStream());
        Server.removeClient(client);
        pw.println("DELETE#" + clientName);

    }

}
