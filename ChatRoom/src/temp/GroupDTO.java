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
public class GroupDTO implements Serializable{
    private String group_name, group_createday, user_email, group_member_joinday;
    private int group_id, group_member_id, group_member_status;

    public GroupDTO(String group_name, String group_createday, String user_email, String group_member_joinday, int group_id, int group_member_id, int group_member_status) {
        this.group_name = group_name;
        this.group_createday = group_createday;
        this.user_email = user_email;
        this.group_member_joinday = group_member_joinday;
        this.group_id = group_id;
        this.group_member_id = group_member_id;
        this.group_member_status = group_member_status;
    }

    public GroupDTO(String user_email, String group_member_joinday, int group_id, int group_member_id, int group_member_status) {
        this.user_email = user_email;
        this.group_member_joinday = group_member_joinday;
        this.group_id = group_id;
        this.group_member_id = group_member_id;
        this.group_member_status = group_member_status;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_createday() {
        return group_createday;
    }

    public void setGroup_createday(String group_createday) {
        this.group_createday = group_createday;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getGroup_member_joinday() {
        return group_member_joinday;
    }

    public void setGroup_member_joinday(String group_member_joinday) {
        this.group_member_joinday = group_member_joinday;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_member_id() {
        return group_member_id;
    }

    public void setGroup_member_id(int group_member_id) {
        this.group_member_id = group_member_id;
    }

    public int getGroup_member_status() {
        return group_member_status;
    }

    public void setGroup_member_status(int group_member_status) {
        this.group_member_status = group_member_status;
    }
    
    
}
