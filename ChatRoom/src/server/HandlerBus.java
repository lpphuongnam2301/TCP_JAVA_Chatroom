/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import static server.Handler.serverThreadBus;
import temp.GroupDTO;
import temp.ObjectSend;
import temp.UserDTO;

/**
 *
 * @author lpphu
 */
public class HandlerBus {

    public ArrayList<Handler> arrThread;

    public ArrayList<Handler> getListServerThreads() {
        return arrThread;
    }

    public HandlerBus() {
        arrThread = new ArrayList<>();
    }

    public void add(Handler serverThread){
        arrThread.add(serverThread);
    }
    
    public int getLength(){
        return arrThread.size();
    }
    public void sendAll(ObjectSend message)
    {
        for(Handler serverThread : Handler.serverThreadBus.getListServerThreads())
        {
            try 
            {
               serverThread.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void sendAllExceptUser(ObjectSend message, String email)
    {
        for(Handler serverThread : Handler.serverThreadBus.getListServerThreads())
        {
            try 
            {
                if(!serverThread.getEmail().equals(email))
                {
                    serverThread.write(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void sendOneUser(ObjectSend message, String email)
    {
        for(Handler serverThread : Handler.serverThreadBus.getListServerThreads())
        {
            try 
            {
                if(serverThread.getEmail().equals(email))
                {
                    serverThread.write(message);
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void sendGroupMember(ObjectSend message, String email, ArrayList<GroupDTO> arrayGroup)
    {
        try 
        {
            for (GroupDTO user : arrayGroup) 
            {
                for (Handler serverThread : Handler.serverThreadBus.getListServerThreads()) 
                {
                    if (serverThread.getEmail().equals(user.getUser_email()) && !serverThread.getEmail().equals(email)) 
                    {
                        serverThread.write(message);
                        break;
                    }
                }
            }

        } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    public void sendFriendList(ObjectSend message, String email, ArrayList<UserDTO> arrayUser)
    {
        try 
        {
            for (UserDTO user : arrayUser) 
            {
                for (Handler serverThread : Handler.serverThreadBus.getListServerThreads()) 
                {
                    if (serverThread.getEmail().equals(user.getUser_email()) && !serverThread.getEmail().equals(email)) 
                    {
                        serverThread.write(message);
                        break;
                    }
                }
            }

        } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    public ArrayList getEmailList()
    {
        ArrayList<String> arr = new ArrayList<>();
        for (Handler serverThread : Handler.serverThreadBus.getListServerThreads()) 
        {
            arr.add(serverThread.getEmail());
        }
        
        return arr;
    }
}
