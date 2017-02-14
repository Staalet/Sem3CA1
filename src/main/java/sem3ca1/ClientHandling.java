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
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author christian
 */
public class ClientHandling extends Thread
{
    Socket link; 
    private static String username;
    private Server server = new Server();

    public ClientHandling(Socket link, Server serv)
    {
        this.server = serv; 
        this.link = link;
    }

    public static void handleClient(Socket s) throws IOException
    {
        Scanner scan = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        String[] inputFromClients = scan.nextLine().split("#");
        String msg = "";
        String tmpMsg = "";
        String output = "";

        while (!msg.equals(""))
        {
            msg = scan.nextLine();
            tmpMsg = msg;
            inputFromClients[0] = inputFromClients[0].toUpperCase();
            switch (inputFromClients[0])
            {

                //Setting username when logging in
                case "LOGIN":
                    ClientHandling.setUsername(inputFromClients[1]);

                //Checks if you write to specific user    
                case "MSG#":
                    if (!ClientHandling.username.isEmpty())
                    {
                        pw.println(Arrays.toString(inputFromClients));
                        pw.flush();
                    }

                //Sends a message to all the users online    
                case "MSG#ALL":
                    scan.nextLine();
                    for (ClientHandling clients : server.clients)
                    {

                        pw.println(Arrays.toString(inputFromClients));

                    }

            }

        }

    }

//    private String sendMessageToUser(){
//        
//    }
    /**
     * @return the username
     */
    public static String getUsername()
    {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public static void setUsername(String aUsername)
    {
        username = aUsername;
    }
}
