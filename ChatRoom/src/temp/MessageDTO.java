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
public class MessageDTO implements Serializable{
    private String user_sender, user_receive, message_time, message_content, message_file, message_status, message_emoji;
    private int message_id;
    private String user_name, user_status, user_email;
    private byte[] arrByte;
    public MessageDTO() {}
    public MessageDTO(String user_sender, String user_receive, String message_time, String message_content, String message_file, String message_emoji, int message_status, int message_id) {
        this.user_sender = user_sender;
        this.user_receive = user_receive;
        this.message_time = message_time;
        this.message_content = message_content;
        this.message_file = message_file;
        this.message_id = message_id;
        this.message_emoji = message_emoji;
        if(message_status == 1)
        {
            this.message_status = "Đã gửi";
        } else {
            this.message_status = "Đã xem";
        }
    }

    public MessageDTO(String user_sender, String user_receive, String message_time, String message_content, String message_status, String message_file, String message_emoji) {
        this.user_sender = user_sender;
        this.user_receive = user_receive;
        this.message_time = message_time;
        this.message_content = message_content;
        this.message_status = message_status;
        this.message_file = message_file;
        this.message_emoji = message_emoji;
    }
    public MessageDTO(String user_sender, String user_receive, String message_time, String message_content, String message_status, String message_file, String message_emoji, byte[] arrByte) {
        this.user_sender = user_sender;
        this.user_receive = user_receive;
        this.message_time = message_time;
        this.message_content = message_content;
        this.message_status = message_status;
        this.message_file = message_file;
        this.message_emoji = message_emoji;
        this.arrByte = arrByte;
    }

    public byte[] getArrByte() {
        return arrByte;
    }

    public void setArrByte(byte[] arrByte) {
        this.arrByte = arrByte;
    }
    
    public String getMessage_emoji() {
        return message_emoji;
    }

    public void setMessage_emoji(String message_emoji) {
        this.message_emoji = message_emoji;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_sender() {
        return user_sender;
    }

    public void setUser_sender(String user_sender) {
        this.user_sender = user_sender;
    }

    public String getUser_receive() {
        return user_receive;
    }

    public void setUser_receive(String user_receive) {
        this.user_receive = user_receive;
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

    public String getMessage_status() {
        return message_status;
    }

    public void setMessage_status(String message_status) {
        this.message_status = message_status;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    
    
}
