/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem3ca1;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author madsr
 */
class InputThread extends Thread
{

    final JTextArea myJTextArea;
    final JTextArea myJTextArea2;
    final Client client;

    public InputThread(JTextArea myJTextArea, JTextArea myJTextArea2, Client client)
    {
        this.myJTextArea = myJTextArea;
        this.myJTextArea2 = myJTextArea2;
        this.client = client;

    }

    @Override
    public void run()
    {
        String tmp;
        ArrayList<String> users = new ArrayList();

        while (true)
        {
            try
            {

                tmp = client.receive();

                System.out.println(tmp);

                String[] args = tmp.split("#");
                String command = args[0];

                switch (command)
                {
                    case "MSG":

                        myJTextArea.append(tmp + "\n");

                        break;
                    case "OK":

                        String userList = "";

                        for (int i = 1; i < args.length; i++)
                        {
                            String username = args[i];
                            users.add(username);
                            myJTextArea2.append(username + "\n");
                            userList += args[i] + ", ";

                        }

                        myJTextArea.append("Users in room " + userList + "\n");

                        break;
                    case "UPDATE":

                        if (!users.contains(args[1]))
                        {
                            users.add(args[1]);
                            myJTextArea2.append(args[1] + "\n");
                            myJTextArea.append(args[1] + " Has Joined \n");
                        }

                        break;

                    case "DELETE":
                        
                        users.remove(args[1]);
                        myJTextArea2.setText("");
                        
                        for (int i = 1; i < args.length; i++)
                        {
                            String username = args[i];
                            myJTextArea2.append(username + "\n");
                        }
                        
                        myJTextArea.append(args[1] + "Has left the room \n");
                        
                        break;
                        
                    case "FAIL":
                        users.remove(args[1]);
                        myJTextArea.append("Username already taken, you have been disconnected \n");
                        break; 
                }
            } catch (Exception ex)
            {
                System.out.println(ex);
                throw ex;
            }
        }
    }

}
