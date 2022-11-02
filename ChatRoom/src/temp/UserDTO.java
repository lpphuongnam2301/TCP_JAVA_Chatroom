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
public class UserDTO implements Serializable {
    private String user_email, user_password, user_name, user_birthday, user_phone, user_gender, user_createday, user_status, group_member_joinday;
    private int user_id, contact_status;

    public UserDTO(String user_email, String user_password, String user_name, String user_birthday, String user_phone, String user_gender, String user_createday, int user_id, int user_status) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
        this.user_createday = user_createday;
        this.user_id = user_id;
        if(user_status == 1)
        {
            this.user_status = "Online";
        } else { this.user_status = "Offline"; }
    }
    public UserDTO(String user_email, String user_password, String user_name, String user_birthday, String user_phone, String user_gender, String user_createday, int user_id, int user_status, String group_member_joinday) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
        this.user_createday = user_createday;
        this.user_id = user_id;
        this.group_member_joinday = group_member_joinday;
        if(user_status == 1)
        {
            this.user_status = "Online";
        } else { this.user_status = "Offline"; }
    }
    public UserDTO(String user_email, String user_password, String user_name, String user_birthday, String user_phone, String user_gender, String user_createday, int user_id, int user_status,int contact_status) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
        this.user_createday = user_createday;
        this.user_id = user_id;
        this.contact_status = contact_status;
        if(user_status == 1)
        {
            this.user_status = "ON";
        } else { this.user_status = "OFF"; }
    }

    public UserDTO(String user_email, String user_name, String user_birthday, String user_phone, String user_gender) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
    }
    
    public String getUser_email() {
        return user_email;
    }

    public String getGroup_member_joinday() {
        return group_member_joinday;
    }

    public void setGroup_member_joinday(String group_member_joinday) {
        this.group_member_joinday = group_member_joinday;
    }

    public int getContact_status() {
        return contact_status;
    }

    public void setContact_status(int contact_status) {
        this.contact_status = contact_status;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_createday() {
        return user_createday;
    }

    public void setUser_createday(String user_createday) {
        this.user_createday = user_createday;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }
    
}
