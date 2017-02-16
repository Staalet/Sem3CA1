/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Staal
 */
public class Client {

    Socket socket;
    private Scanner scan;
    private PrintWriter pw;
    private InetAddress adress;
    int port;

    public boolean connectToServer(String ip, int port) {
        try {
            this.port = port;
            adress = InetAddress.getByName(ip);
            socket = new Socket(adress, port);
            pw = new PrintWriter(socket.getOutputStream());
            scan = new Scanner(socket.getInputStream());
            return true;
        } catch (UnknownHostException ue) {
            System.out.println("Couldn't connect");
            return false;
        } catch (IOException ie) {
            System.out.println("IOException");
            return false;
        }
    }

    public String receive() {
        String msg;
        msg = scan.nextLine();
        return msg;
    }

    public void send(String msg) {

        pw.println(msg);
        pw.flush();
    }

    public static void main(String[] args) {
        int port = 8081;
        String ip = "localhost";
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        Client tester = new Client();
        tester.connectToServer(ip, port);
        tester.send("login#Thomas");
        tester.send("MSG#ALL#hej");

    }
}
