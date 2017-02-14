package sem3ca1;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author madsr
 */
public class UserService extends Observable implements Runnable
{
    private int usersOnline = 0;
    
    @Override
    public void run()
    {
        if (usersOnline != Server.clients.size())
        {
            usersOnline = Server.clients.size();
            
            for (int i = 0; i < Server.clients.size(); i++)
            {
                System.out.println(Server.clients.get(i));
            }
        }
    }
}
