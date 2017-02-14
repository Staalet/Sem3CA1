/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author christian
 */
public class ClientHandling extends Thread {

    private static String username;

    public static void handleClient(Socket s) throws IOException {
        Scanner scan = new Scanner(s.getInputStream());
        PrintWriter pw = new PrintWriter(s.getOutputStream());

        String msg = "";
        String tmpMsg = "";
        String output = "";

        while (!msg.equals("")) {
            msg = scan.nextLine();
            tmpMsg = msg;

            switch (msg) {
                case 1: msg.substring(0, 4).startsWith("MSG#");
            }

        }
    }


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
}
