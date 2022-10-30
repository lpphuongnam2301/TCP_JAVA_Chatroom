/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import static server.ServerThread.serverThreadBus;
import temp.GroupDTO;
import temp.ObjectSend;
import temp.UserDTO;

/**
 *
 * @author lpphu
 */
public class ServerThreadBus {

    public ArrayList<ServerThread> arrThread;

    public ArrayList<ServerThread> getListServerThreads() {
        return arrThread;
    }

    public ServerThreadBus() {
        arrThread = new ArrayList<>();
    }

    public void add(ServerThread serverThread){
        arrThread.add(serverThread);
    }
    
    public int getLength(){
        return arrThread.size();
    }
    public void sendAll(ObjectSend message)
    {
        for(ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads())
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
        for(ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads())
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
        for(ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads())
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
                for (ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads()) 
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
                for (ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads()) 
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
        for (ServerThread serverThread : ServerThread.serverThreadBus.getListServerThreads()) 
        {
            arr.add(serverThread.getEmail());
        }
        
        return arr;
    }
}
