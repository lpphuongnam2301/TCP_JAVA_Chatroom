/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import temp.GroupDTO;
import temp.GroupMessageDTO;
import temp.MessageDTO;
import temp.UserDTO;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author lpphu
 */
public class ServerDAL {
    MyDBConnection con = new MyDBConnection();
    
    public ArrayList readUserFriend(String user_email)
    {
        ArrayList<UserDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM contact INNER JOIN user ON user.user_email = contact.friend_email where contact.user_email = '" +user_email+"' and contact.contact_status = 1";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                UserDTO user = new UserDTO(rs.getString("friend_email"), rs.getString("user_password")
                , rs.getString("user_name"), rs.getDate("user_birthday").toString(), rs.getString("user_phone")
                , rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"));
                list.add(user);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readUserBlockList(String user_email)
    {
        ArrayList<UserDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM contact INNER JOIN user ON user.user_email = contact.friend_email where contact.user_email = '" +user_email+"' and contact.contact_status = 2";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                UserDTO user = new UserDTO(rs.getString("friend_email"), rs.getString("user_password")
                , rs.getString("user_name"), rs.getDate("user_birthday").toString(), rs.getString("user_phone")
                , rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"));
                list.add(user);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readFriendNotInGroup(String user_email, int group_id)
    {
        ArrayList<UserDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM contact INNER JOIN user ON user.user_email = contact.friend_email where contact.user_email = '"+user_email+"' AND contact.friend_email NOT IN (SELECT user_email FROM group_member WHERE group_id = '"+group_id+"')";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                UserDTO user = new UserDTO(rs.getString("friend_email"), rs.getString("user_password")
                , rs.getString("user_name"), rs.getDate("user_birthday").toString(), rs.getString("user_phone")
                , rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"));
                list.add(user);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readInfoGroupMember(int group_id)
    {
        ArrayList<UserDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM `group_member` INNER JOIN user ON user.user_email = group_member.user_email WHERE group_id = '"+group_id+"'";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                UserDTO user = new UserDTO(rs.getString("group_member.user_email"), rs.getString("user_password")
                , rs.getString("user_name"), rs.getDate("user_birthday").toString(), rs.getString("user_phone")
                , rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"), rs.getTimestamp("group_member_joinday").toString());
                list.add(user);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public UserDTO readInfoUserFriend(String user_email, String friend)
    {
        UserDTO dto = null;
        try
        {
            String query = "SELECT * FROM contact INNER JOIN user ON user.user_email = contact.friend_email where contact.user_email = '" +user_email+"' and contact.friend_email = '"+friend+"'";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                dto = new UserDTO(rs.getString("friend_email"), rs.getString("user_password")
                , rs.getString("user_name"), rs.getDate("user_birthday").toString(), rs.getString("user_phone")
                , rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"), rs.getInt("contact_status"));
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return dto;
    }
    public ArrayList readGroupList(String user_email)
    {
        ArrayList<GroupDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM groupchat INNER JOIN group_member ON groupchat.group_id = group_member.group_id WHERE group_member.user_email = '" +user_email+ "' and group_member.group_member_status = 1";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                GroupDTO group = new GroupDTO(rs.getString("group_name"), rs.getString("group_createday"),
                rs.getString("user_email"), rs.getString("group_member_joinday"), rs.getInt("group_member.group_id"),
                rs.getInt("group_member_id"), rs.getInt("group_member_status"));
                list.add(group);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readBlockGroupList(String user_email)
    {
        ArrayList<GroupDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM groupchat INNER JOIN group_member ON groupchat.group_id = group_member.group_id WHERE group_member.user_email = '" +user_email+ "' and group_member.group_member_status = 2";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                GroupDTO group = new GroupDTO(rs.getString("group_name"), rs.getString("group_createday"),
                rs.getString("user_email"), rs.getString("group_member_joinday"), rs.getInt("group_member.group_id"),
                rs.getInt("group_member_id"), rs.getInt("group_member_status"));
                list.add(group);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readGroupMember(String groupId)
    {
        ArrayList<GroupDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM `group_member` WHERE group_id = '"+groupId+"' AND group_member_status = 1";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                GroupDTO group = new GroupDTO(rs.getString("user_email"), rs.getString("group_member_joinday"),
                rs.getInt("group_id"), rs.getInt("group_member_id"), rs.getInt("group_member_status"));
                list.add(group);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public int getGroupId()
    {
        int size = 0;
        try
        {
            String query = "SELECT MAX(group_id) as size FROM `groupchat`";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                size = rs.getInt("size");
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return size + 1;
    }
    public ArrayList readMessageFromUser(String str, String target)
    {
        ArrayList<MessageDTO> list = new ArrayList<>();
        String sender = str.split(",")[0];
        String receive = str.split(",")[1];
        try
        {
            String query = "SELECT * FROM `message` WHERE (user_sender = '"+sender+"' OR user_receive = '"+sender+"') AND (user_receive = '"+receive+"' OR user_sender = '"+receive+"')";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                MessageDTO message = new MessageDTO(rs.getString("user_sender"),rs.getString("user_receive"), rs.getTimestamp("message_time").toString()
                , rs.getString("message_content"), rs.getString("message_file"), rs.getString("message_emoji"), rs.getInt("message_status"), rs.getInt("message_id"));
                message.setUser_name(target.split(",")[0]);
                message.setUser_email(target.split(",")[1]);
                list.add(message);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readFileListUser(String sender, String receive)
    {
        ArrayList<MessageDTO> list = new ArrayList<>();
        byte[] byteArr;

        try
        {
            String query = "SELECT * FROM `message` WHERE (user_sender = '"+sender+"' OR user_receive = '"+sender+"') AND (user_receive = '"+receive+"' OR user_sender = '"+receive+"') and message_fileByte is not null";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                Blob blob = rs.getBlob("message_fileByte");
                int blobLength = (int) blob.length();  
                byteArr = blob.getBytes(1, blobLength);
                blob.free();
                MessageDTO message = new MessageDTO();
                message.setArrByte(byteArr);
                message.setMessage_file(rs.getString("message_file"));
                message.setMessage_time(rs.getTimestamp("message_time").toString());
                message.setUser_sender(rs.getString("user_sender"));
                message.setMessage_id(rs.getInt("message_id"));
                list.add(message);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public ArrayList readFileListGroup(String groupId)
    {
        ArrayList<GroupMessageDTO> list = new ArrayList<>();
        byte[] byteArr;

        try
        {
            String query = "select * from group_message where group_id = '"+groupId+"' and message_fileByte is not null";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                Blob blob = rs.getBlob("message_fileByte");
                int blobLength = (int) blob.length();  
                byteArr = blob.getBytes(1, blobLength);
                blob.free();
                GroupMessageDTO message = new GroupMessageDTO();
                message.setArrByte(byteArr);
                message.setMessage_file(rs.getString("message_file"));
                message.setMessage_time(rs.getTimestamp("message_time").toString());
                message.setUser_sender(rs.getString("user_sender"));
                message.setGroup_message_id(rs.getInt("group_message_id"));
                list.add(message);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    public int readStatusContact(String userId, String friendId)
    {
        int status = 0;
        try
        {
            String query = "select * from contact where user_email='"+userId+"' and friend_email= '"+friendId+"'";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                status = rs.getInt("contact_status");
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return status;
    }
    public ArrayList readMessageFromGroup(String groupId, String groupName)
    {
        ArrayList<GroupMessageDTO> list = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM `group_message` INNER JOIN user ON user.user_email = group_message.user_sender WHERE group_message.group_id = "+groupId+" ORDER BY group_message.message_time ASC";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                GroupMessageDTO message = new GroupMessageDTO(rs.getString("user_sender"), rs.getString("message_time")
                , rs.getString("message_content"), rs.getString("message_file"), rs.getString("message_emoji"), rs.getString("user_name"), rs.getInt("group_id"), rs.getInt("group_message_id"));
                message.setGroup_name(groupName); 
                list.add(message);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return list;
    }
    
    public UserDTO readUserInfo(String email)
    {
        UserDTO dto = null;
        try
        {
            String query = "select * from user where user_email = '" + email +"'";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                dto = new UserDTO(rs.getString("user_email"), rs.getString("user_password"), rs.getString("user_name"), rs.getString("user_birthday"), rs.getString("user_phone"), rs.getString("user_gender"), rs.getString("user_createday"), rs.getInt("user_id"), rs.getInt("user_status"));
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        } 
        return dto;
    }
    
    public void addMess(MessageDTO object)
    {
        try
        {
            String query = "INSERT INTO `message`(`user_sender`, `user_receive`, `message_time`, `message_content`,`message_file`,`message_emoji`, `message_status`, `message_fileByte`) "
                + "VALUES (?,?,?,?,?,?,?,?)";
            
            PreparedStatement tm = con.getConnect().prepareStatement(query);
            tm.setString(1,object.getUser_sender());
            tm.setString(2,object.getUser_receive());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(object.getMessage_time());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            tm.setTimestamp(3,timestamp);
            tm.setString(4,object.getMessage_content());
            tm.setString(5,object.getMessage_file());
            tm.setString(6,object.getMessage_emoji());
            tm.setInt(7, Integer.parseInt(object.getMessage_status()));
            
            if(object.getArrByte() != null)
            {
                InputStream is = new ByteArrayInputStream(object.getArrByte());
                tm.setBlob(8, is);
            } else {
                tm.setNull(8, 0);
            }
            tm.executeUpdate();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addGroupMess(GroupMessageDTO object)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(object.getMessage_time());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            String query = "INSERT INTO `group_message`(`group_id`, `user_sender`, `message_time`, `message_content`, `message_file`, `message_emoji`, `message_fileByte`) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement tm = con.getConnect().prepareStatement(query);
            tm.setInt(1, object.getGroup_id());
            tm.setString(2, object.getUser_sender());
            tm.setTimestamp(3, timestamp);
            tm.setString(4, object.getMessage_content());
            tm.setString(5, object.getMessage_file());
            tm.setString(6, object.getMessage_emoji());
            if(object.getArrByte() != null)
            {
                InputStream is = new ByteArrayInputStream(object.getArrByte());
                tm.setBlob(7, is);
            } else {
                tm.setNull(7, 0);
            }
            
            tm.executeUpdate();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateMessStatus(String user_sender, String user_receive, int status)
    {
        try
        {
            String query = "UPDATE `message` SET `message_status`='"+status+"' WHERE user_sender = '"+user_sender+"' and user_receive = '"+user_receive+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateUserStatus(String user_email, int status)
    {
        try
        {
            String query = "UPDATE `user` SET `user_status`='"+status+"' WHERE user_email = '"+user_email+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateContactStatus(String user_email, String friend_email, int status)
    {
        try
        {
            String query = "UPDATE `contact` SET `contact_status`='"+status+"' WHERE user_email = '"+user_email+"' AND friend_email = '"+friend_email+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateGroupMemberStatus(String user_email, String group_id, int status)
    {
        try
        {
            String query = "UPDATE `group_member` SET `group_member_status`= '"+status+"' WHERE user_email = '"+user_email+"' and group_id = '"+group_id+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void outGroup(String user_email, String group_id)
    {
        try
        {
            String query = "DELETE FROM `group_member` WHERE group_id = '"+group_id+"' AND user_email = '"+user_email+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addContact(String user, String friend)
    {
        try
        {
            String query = "INSERT INTO `contact`(`user_email`, `friend_email`, `contact_status`) VALUES ('"+user+"','"+friend+"','1')";
            String query1 = "INSERT INTO `contact`(`user_email`, `friend_email`, `contact_status`) VALUES ('"+friend+"','"+user+"','1')";
            con.executeUpdate(query);
            con.executeUpdate(query1);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateGroupInfo(String group_id, String groupName)
    {
        try
        {            
            //sua ten nhom
            String query = "UPDATE `groupchat` SET `group_name`='"+groupName+"' WHERE group_id = '"+group_id+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateUserInfo(UserDTO user)
    {
        try
        {            
            String query = "UPDATE `user` SET `user_name`='"+user.getUser_name()+"',`user_birthday`='"+user.getUser_birthday()+"',`user_phone`='"+user.getUser_phone()+"',`user_gender`='"+user.getUser_gender()+"' WHERE user_email = '"+user.getUser_email()+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updatePassUser(String pass, String userEmail)
    {
        try
        {            
            String query = "UPDATE `user` SET `user_password`='"+pass+"' WHERE user_email = '"+userEmail+"'";
            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addGroupMember(String user_email, String group_id, String time)
    {
        try
        {
            //them thanh vien
            String query = "INSERT INTO `group_member`(`group_id`, `user_email`, `group_member_joinday`, `group_member_status`) "
                    + "VALUES ('"+group_id+"','"+user_email+"','"+time+"','1')";

            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addNewGroup(int groupId, String groupName, String time)
    {
        try
        {
            //them thanh vien
            String query = "INSERT INTO `groupchat`(`group_id`, `group_name`, `group_createday`) "
                    + "VALUES ('"+groupId+"','"+groupName+"','"+time+"')";

            con.executeUpdate(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
