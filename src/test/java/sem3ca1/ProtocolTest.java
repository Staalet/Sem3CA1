/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Staal
 */
public class ProtocolTest {

    public ProtocolTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //This test show the result when a client send a msg to all
    @Test
    public void sendMessageToAllProtocol() throws IOException {
        new Thread(() -> {
            Server server = new Server();
            server.RunServer("localhost", 8080);
            Client client1 = new Client();
            Client client2 = new Client();
            Socket socket = new Socket();
            String result = new String();
            String expResult = "MSG#Thomas#hej";
            //Connects with the clienthandler so we can use methods
            ClientHandling ch = new ClientHandling(socket, server);
            ch.run();
            try {
                //Clients connects to server
                client1.connectToServer("localhost", 8080);
                client2.connectToServer("localhost", 8080);
                client1.send("LOGIN#Christian");
                client2.send("LOGIN#Thomas");
                //client 1 sends a msg to all
                client1.send("msg#all#hej");
                result = client2.receive("");
            } catch (IOException ex) {
                Logger.getLogger(ProtocolTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertEquals(expResult, result);
        }).start();

    }

    @Test
    public void sendMessageToSpecificProtocol() {
        new Thread(() -> {
            Server server = new Server();
            server.RunServer("localhost", 8080);
            Socket socket = new Socket();
            Client client1 = new Client();
            Client client2 = new Client();
            ClientHandling ch = new ClientHandling(socket, server);
            ch.run();
            String expResult = "hej thomas";
            String result;

            try {
                client1.connectToServer("localhost", 8080);
                client2.connectToServer("localhost", 8080);
                client1.send("LOGIN#Christian");
                client2.send("LOGIN#Thomas");
                client1.send("MSG#Thomas#hej thomas");
                result = client2.receive("");
            } catch (IOException ex) {
                Logger.getLogger(ProtocolTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertEquals(expResult, result);
        }).start();
    }

    @Test
    public void loginProtcol() {
        new Thread(() -> {
            Server server = new Server();
            server.RunServer("localhost", 8080);
            Client client = new Client();
            Socket socket = new Socket();
            ClientHandling ch = new ClientHandling(socket, server);
            ch.run();
            String expResult = "#UPDATE THOMAS/n OK:THOMAS";
            String result;
            try {
                client.connectToServer("localhost", 8080);
                client.send("LOGIN#Thomas");
            } catch (IOException ex) {
                Logger.getLogger(ProtocolTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = client.receive("");
            assertEquals(expResult, result);
        }).start();
    }

//    @Test
//    public void failProtocol() {
//        new Thread(() -> {
//            Server server = new Server();
//            server.RunServer("localhost", 8080);
//            Client client1 = new Client();
//            Client client2 = new Client();
//            Socket socket = new Socket();
//            ClientHandling ch = new ClientHandling(socket, server);
//            ch.run();
//            String expResult = "FAIL";
//            String result;
//
//            try {
//                client1.connectToServer("localhost", 8080);
//                client2.connectToServer("localhost", 8080);
//                client1.send("LOGIN#Thomas");
//                client2.send("LOGIN#Thomas");
//                result = client1.receive("");
//            } catch (IOException ex) {
//                Logger.getLogger(UnitTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            assertEquals(expResult, result);
//        }).start();
//    }

    @Test
    public void clientLogOutProtocol() {
        new Thread(() -> {
            Server server = new Server();
            server.RunServer("localhost", 8080);
            Client client1 = new Client();
            Client client2 = new Client();
            Socket socket = new Socket();
            ClientHandling ch = new ClientHandling(socket, server);
            ch.run();
            String expResult = "DELETE#Thomas";
            String result;

            try {
                client1.connectToServer("localhost", 8080);
                client2.connectToServer("localhost", 8080);
                client1.send("LOGIN#Thomas");
                client2.send("LOGIN#Christian");
                client1.socket.close();
                result = client2.receive("");
            } catch (IOException ex) {
                Logger.getLogger(ProtocolTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertEquals(expResult, result);

        }).start();
    }
}
