/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.GroupDTO;
import temp.GroupMessageDTO;
import temp.MessageDTO;
import temp.ObjectSend;
import temp.UserDTO;

/**
 *
 * @author Admin
 */
public class ServerThread implements Runnable {
    public static volatile ServerThreadBus serverThreadBus = new ServerThreadBus();;
    private Socket socket;
    private String email;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ServerDAL dal = new ServerDAL();
    private ObjectSend obj;

    public ServerThread(Socket socket) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.email = (String) in.readObject();
            
            serverThreadBus.add(this);
            
            System.out.println("User co email: " + email + " da connect");
            System.out.println("So luong user hien tai: " + serverThreadBus.getLength());
            
            //send alert online
            dal.updateUserStatus(email, 1);
            ObjectSend objectSend;
            ArrayList<UserDTO> list = dal.readUserFriend(email);
            for(UserDTO a : list)
            {
                objectSend = new ObjectSend(dal.readUserFriend(a.getUser_email()),"send_online_list",ObjectSend.getOnlyTime() + ": "+ dal.readUserInfo(email).getUser_name() + " đã online");
                serverThreadBus.sendOneUser(objectSend, a.getUser_email());
            }
            //send ol user for all
            objectSend = new ObjectSend(serverThreadBus.getEmailList(), "user_online_in_server");
            serverThreadBus.sendAll(objectSend);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {        
        while (socket.isConnected()) 
        {
            try 
            {
                obj = (ObjectSend) in.readObject();
                if(obj.getTag().equals("get_friend_list"))
                {
                    String user_email = (String) obj.getObject();
                    ArrayList<UserDTO> arr = dal.readUserFriend(user_email);
                    obj = new ObjectSend(arr, "get_friend_list");
                    write(obj);
                }
                if(obj.getTag().equals("get_chat_panel"))
                {
                    String sender = (String) obj.getObject();
                    String target = (String) obj.getObjectTemp();
                    ArrayList<MessageDTO> arr = dal.readMessageFromUser(sender, target);
                    String userName = target.split(",")[0];
                    String userEmail = target.split(",")[1];
                    obj = new ObjectSend(arr, "get_chat_panel", userName, userEmail);
                    write(obj);
                }
                if(obj.getTag().equals("get_block_user_chat_panel"))
                {
                    String sender = (String) obj.getObject();
                    String target = (String) obj.getObjectTemp();
                    ArrayList<MessageDTO> arr = dal.readMessageFromUser(sender, target);
                    String userName = target.split(",")[0];
                    String userEmail = target.split(",")[1];
                    obj = new ObjectSend(arr, "get_block_user_chat_panel", userName, userEmail);
                    write(obj);
                }
                if(obj.getTag().equals("get_block_group_chat_panel"))
                {
                    String groupId = (String) obj.getObject();
                    String groupName = (String) obj.getObjectTemp();
                    ArrayList<GroupMessageDTO> arr = dal.readMessageFromGroup(groupId, groupName);
                    obj = new ObjectSend(arr, "get_block_group_chat_panel", groupId, groupName);
                    write(obj);
                }
                if(obj.getTag().equals("get_group_list"))
                {
                    String user_email = (String) obj.getObject();
                    ArrayList<GroupDTO> arr = dal.readGroupList(user_email);
                    obj = new ObjectSend(arr, "get_group_list");
                    write(obj);
                }
                if(obj.getTag().equals("get_group_chat_panel"))
                {
                    String groupId = (String) obj.getObject();
                    String groupName = (String) obj.getObjectTemp();
                    ArrayList<GroupMessageDTO> arr = dal.readMessageFromGroup(groupId, groupName);
                    obj = new ObjectSend(arr, "get_group_chat_panel", groupId, groupName);
                    write(obj);
                }
                if(obj.getTag().equals("send_mess_to_user"))
                {
                    MessageDTO dto = (MessageDTO) obj.getObject();
                    dal.addMess(dto);
                    if(dal.readStatusContact(dto.getUser_receive(), dto.getUser_sender()) == 1)//ko bi block
                    {
                        String username = dal.readUserInfo(dto.getUser_sender()).getUser_name();
                        ArrayList arr = dal.readMessageFromUser(dto.getUser_sender() + "," + dto.getUser_receive(), username + "," + dto.getUser_sender());

                        ObjectSend mess = new ObjectSend(arr, "send_mess_to_user",ObjectSend.getOnlyTime() + ": "+ username + " gửi tin cho bạn!", dto.getUser_sender());
                        serverThreadBus.sendOneUser(mess, dto.getUser_receive());//alert
                    }
                }
                if(obj.getTag().equals("send_mess_to_group"))
                {
                    GroupMessageDTO dto = (GroupMessageDTO) obj.getObject();
                    dal.addGroupMess(dto);
                    
                    ArrayList arr = dal.readMessageFromGroup(Integer.toString(dto.getGroup_id()), dto.getGroup_name());
                    ObjectSend mess = new ObjectSend(arr, "send_mess_to_group",ObjectSend.getOnlyTime() + ": "+ "Nhóm " + dto.getGroup_name() + "có tin nhắn mới!", Integer.toString(dto.getGroup_id()));
                    serverThreadBus.sendGroupMember(mess, dto.getUser_sender(), dal.readGroupMember(Integer.toString(dto.getGroup_id())));//alert
                }
                if(obj.getTag().equals("update_mess_status"))
                {
                    String object = (String) obj.getObject();
                    dal.updateMessStatus(object.split(",")[0],object.split(",")[1], (int) obj.getObjectTemp());
                    
                }
                if(obj.getTag().equals("block_user"))
                {
                    String user = (String) obj.getObject();
                    String friend = (String) obj.getObjectTemp();
                    dal.updateContactStatus(user, friend, 2);
                    
                    ArrayList<UserDTO> arr = dal.readUserFriend(user);
                    obj = new ObjectSend(arr, "get_friend_list");
                    write(obj);
                }
                if(obj.getTag().equals("block_group"))
                {
                    String user = (String) obj.getObject();
                    String group = (String) obj.getObjectTemp();
                    dal.updateGroupMemberStatus(user, group, 2);
                    
                    ArrayList<GroupDTO> arr = dal.readGroupList(user);
                    obj = new ObjectSend(arr, "get_group_list");
                    write(obj);
                }
                if(obj.getTag().equals("out_group"))
                {
                    String user = (String) obj.getObject();
                    String str = (String) obj.getObjectTemp();
                    String groupId = str.split(",")[0];
                    String groupName = str.split(",")[1];
                    
                    dal.outGroup(user, groupId);
                    
                    ArrayList<GroupDTO> arr = dal.readGroupList(user);
                    obj = new ObjectSend(arr, "get_group_list");
                    write(obj);
                    
                    //alert 
                    ObjectSend mess = new ObjectSend("out_group",ObjectSend.getOnlyTime() + ": "+ user + " rời nhóm '" + groupName +"'");
                    serverThreadBus.sendGroupMember(mess, user, dal.readGroupMember(groupId));//alert
                }
                if(obj.getTag().equals("add_friend_request"))
                {
                    String user = (String)obj.getObject();
                    String friend = (String)obj.getObjectTemp();
                    UserDTO userDTO = dal.readInfoUserFriend(user, friend);
                    if(userDTO == null)//chua ket ban
                    {
                        ObjectSend objSend = new ObjectSend("add_friend_request", user, friend);
                        serverThreadBus.sendOneUser(objSend, friend); 
                    } else {
                        if(userDTO.getContact_status() == 1)// ko block
                        {
                            String username = dal.readUserInfo(friend).getUser_name();
                            ArrayList arr = dal.readMessageFromUser(user + "," +friend, username + "," + friend);
                            ArrayList arrFriend = dal.readUserFriend(user);
                            ObjectSend objSend = new ObjectSend(arr,arrFriend,"get_chat_panel_update", username, friend);
                            write(objSend);
                        } else {
                            System.out.println("bi block");
                        }
                    }              
                }
                if(obj.getTag().equals("add_friend_request_NO"))
                {            
                    String friend = (String)obj.getObject();
                    String user = (String)obj.getObjectTemp();
                    
                    //ObjectSend objSend = new ObjectSend("phuongnam2301@gmail.com từ chối kết bạn!");
                    ObjectSend objSend = new ObjectSend("add_friend_request_NO", user + " từ chối kết bạn!");
                    serverThreadBus.sendOneUser(objSend, friend);
                }
                if(obj.getTag().equals("add_contact"))
                {            
                    String friend = (String)obj.getObject();
                    String user = (String)obj.getObjectTemp();
                    dal.addContact(friend, user);
                    //me
                    String username = dal.readUserInfo(friend).getUser_name();
                    ArrayList arr = dal.readMessageFromUser(user + "," +friend, username + "," + friend);
                    ArrayList arrFriend = dal.readUserFriend(user);
                    ObjectSend objSend;
                    objSend = new ObjectSend(arr,arrFriend,"get_chat_panel_update", username, friend);
                    write(objSend);
                    
//                    //friend
                    String username1 = dal.readUserInfo(user).getUser_name();
                    ArrayList arr1 = dal.readMessageFromUser(friend + "," +user, username + "," + user);
                    ArrayList arrFriend1 = dal.readUserFriend(friend);
                    objSend = new ObjectSend(arr1,arrFriend1,"get_chat_panel_update", username1, user);
                    serverThreadBus.sendOneUser(objSend, friend);
                }
                if(obj.getTag().equals("get_edit_group_info"))
                {            
                    String groupId = (String)obj.getObject();
                    String groupName = (String)obj.getObjectTemp();
                    String user = obj.getAlert();
                    
                    ArrayList arrGroupMember = dal.readInfoGroupMember(Integer.parseInt(groupId));
                    ArrayList arrFriend = dal.readFriendNotInGroup(user, Integer.parseInt(groupId));
                    obj = new ObjectSend(arrGroupMember, arrFriend, "get_edit_group_info", groupName);                    
                    write(obj);
                }
                if(obj.getTag().equals("edit_group_info"))
                {            
                    String groupId = (String)obj.getObjectTemp();
                    String groupName = (String)obj.getObject();
                    ArrayList<String> userList = obj.getArr();
                    String time = ObjectSend.getCurrentTime();
                    if(!userList.isEmpty())
                    {
                        //co the alert cho thanh vien khac o day
                        for (String a : userList) 
                        {
                            dal.addGroupMember(a, groupId, time);
                            ObjectSend nam = new ObjectSend("alert_join_group","Bạn được thêm vào nhóm " + groupName);
                            serverThreadBus.sendOneUser(nam, a);
                        }
                    }
                    dal.updateGroupInfo(groupId, groupName);    
                    
                    ObjectSend objSend = new ObjectSend(dal.readGroupList(email),dal.readMessageFromGroup(groupId, groupName),"get_group_chat_panel_update", groupId, groupName);
                    write(objSend);
                }
                if(obj.getTag().equals("add_new_group"))
                {            
                    String groupName = (String)obj.getObjectTemp();
                    String userEmail = (String)obj.getObject();
                    int groupId = dal.getGroupId();
                    String time = ObjectSend.getCurrentTime();
                    
                    dal.addNewGroup(groupId, groupName, time);
                    dal.addGroupMember(userEmail, Integer.toString(groupId), time);
                    
                    ObjectSend objSend = new ObjectSend(dal.readGroupList(userEmail),"get_group_list");
                    write(objSend);
                }
                if(obj.getTag().equals("get_block_list"))
                {            
                    String userEmail = (String)obj.getObject();
                    
                    ObjectSend objSend = new ObjectSend(dal.readUserBlockList(userEmail),dal.readBlockGroupList(userEmail),"get_block_list");
                    write(objSend);
                }
                if(obj.getTag().equals("unblock_group"))
                {            
                    String userEmail = (String)obj.getObject();
                    String groupId = (String)obj.getObjectTemp();
                    dal.updateGroupMemberStatus(userEmail, groupId, 1);
                    
                    ObjectSend objSend = new ObjectSend(dal.readUserBlockList(userEmail),dal.readBlockGroupList(userEmail),"get_block_list");
                    write(objSend);
                }
                if(obj.getTag().equals("unblock_user"))
                {            
                    String userEmail = (String)obj.getObject();
                    String friend = (String)obj.getObjectTemp();
                    dal.updateContactStatus(userEmail, friend, 1);
                    
                    ObjectSend objSend = new ObjectSend(dal.readUserBlockList(userEmail),dal.readBlockGroupList(userEmail),"get_block_list");
                    write(objSend);
                }
                if(obj.getTag().equals("get_file_mess_list"))
                {            
                    String sender = (String)obj.getObject();
                    String receive = (String)obj.getObjectTemp();
                    
                    
                    ObjectSend objSend = new ObjectSend(dal.readFileListUser(sender, receive),"get_file_mess_list");
                    write(objSend);
                }
                if(obj.getTag().equals("get_file_group_mess_list"))
                {            
                    String groupId = (String)obj.getObject();
                    
                    
                    ObjectSend objSend = new ObjectSend(dal.readFileListGroup(groupId),"get_file_group_mess_list");
                    write(objSend);
                }
                if(obj.getTag().equals("get_edit_user_info"))
                {            
                    String emailUser = (String)obj.getObject();
                    
                    
                    ObjectSend objSend = new ObjectSend("get_edit_user_info", dal.readUserInfo(emailUser));
                    write(objSend);
                }
                if(obj.getTag().equals("edit_user_info"))
                {            
                    UserDTO user = (UserDTO)obj.getObject();
                    dal.updateUserInfo(user);
                    
//                    ObjectSend objSend = new ObjectSend("get_edit_user_info", dal.readUserInfo(user.getUser_email()));
//                    write(objSend);
                }
                if(obj.getTag().equals("log_out"))
                {            
                    String userEmail = (String)obj.getObject();
                    dal.updateUserStatus(userEmail, 2);
                    
                    ArrayList<UserDTO> list = dal.readUserFriend(email);
                    for(UserDTO a : list)
                    {
                        ObjectSend objectSend = new ObjectSend(dal.readUserFriend(a.getUser_email()),"send_online_list", ObjectSend.getOnlyTime() + ": "+ dal.readUserInfo(email).getUser_name() + " đã offline");
                        serverThreadBus.sendOneUser(objectSend, a.getUser_email());
                    }
                    
                    for(ServerThread a : serverThreadBus.getListServerThreads())
                    {
                         if(a.getEmail().equals(userEmail)) 
                         {
                             serverThreadBus.getListServerThreads().remove(a);
                             break;
                         }
                    }
                    break;
                }
            } catch (IOException ex) 
            {
                close(socket, in, out);
                ex.printStackTrace();
                
            } catch (ClassNotFoundException ex) 
            {
                close(socket, in, out);
                ex.printStackTrace();
            } 
        }
    }
    public void write(ObjectSend object) throws IOException
    {        
        out.writeObject(object);
        out.flush();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void close(Socket socket, ObjectInputStream in, ObjectOutputStream out)
    {
        try {
            if(socket != null)
            {
                socket.close();
            } 
            if(in != null)
            {
                in.close();
            }
            if(out != null)
            {
                out.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
