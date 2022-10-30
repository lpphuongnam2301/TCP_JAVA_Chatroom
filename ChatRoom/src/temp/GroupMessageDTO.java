/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package temp;

import java.io.Serializable;

/**
 *
 * @author lpphu
 */
public class GroupMessageDTO implements Serializable{
    private String group_name, user_sender, message_time, message_content, message_file, user_name, message_emoji;
    private int group_id, group_message_id;
    byte[] arrByte;
    public GroupMessageDTO(){}
    public GroupMessageDTO(String user_sender, String message_time, String message_content, String message_file, String message_emoji, String user_name, int group_id, int group_message_id) {
        this.user_sender = user_sender;
        this.message_time = message_time;
        this.message_content = message_content;
        this.message_file = message_file;
        this.user_name = user_name;
        this.group_id = group_id;
        this.group_message_id = group_message_id;
        this.message_emoji = message_emoji;
    }

    public GroupMessageDTO(String user_sender, String message_time, String message_content, int group_id, String message_file, String message_emoji) {
        this.user_sender = user_sender;
        this.message_time = message_time;
        this.message_content = message_content;
        this.group_id = group_id;
        this.message_file = message_file;
        this.message_emoji = message_emoji;
    }
    public GroupMessageDTO(String user_sender, String message_time, String message_content, int group_id, String message_file, String message_emoji, byte[] arrByte) {
        this.user_sender = user_sender;
        this.message_time = message_time;
        this.message_content = message_content;
        this.group_id = group_id;
        this.message_file = message_file;
        this.message_emoji = message_emoji;
        this.arrByte = arrByte;
    }
    public String getMessage_emoji() {
        return message_emoji;
    }

    public byte[] getArrByte() {
        return arrByte;
    }

    public void setArrByte(byte[] arrByte) {
        this.arrByte = arrByte;
    }

    public void setMessage_emoji(String message_emoji) {
        this.message_emoji = message_emoji;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getUser_sender() {
        return user_sender;
    }

    public void setUser_sender(String user_sender) {
        this.user_sender = user_sender;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_file() {
        return message_file;
    }

    public void setMessage_file(String message_file) {
        this.message_file = message_file;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_message_id() {
        return group_message_id;
    }

    public void setGroup_message_id(int group_message_id) {
        this.group_message_id = group_message_id;
    }
    
    
}
