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
public class ServerTest {

    public ServerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(() -> {
            Server server = new Server();
            server.RunServer("localhost", 8080);

            Socket socket = new Socket();
            ClientHandling ch = new ClientHandling(socket, server);
            ch.run();

        }).start();
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

    @Test
    public void client() throws IOException {
        new Thread(() -> {
            Client client = new Client();
            String expResult = "#UPDATE THOMAS/n OK:THOMAS";
            String result;
            try {
                client.connectToServer("localhost", 8080);
            } catch (IOException ex) {
                Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            client.send("LOGIN#Thomas");
            result = client.receive("");
            assertEquals(expResult, result);
        }).start();

    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
