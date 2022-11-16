/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import temp.LeftMenu;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import server.cipher;
import temp.ObjectSend;
import temp.UserDTO;
import java.lang.reflect.Type;
import temp.GroupDTO;
import temp.GroupMessageDTO;
import temp.MessageDTO;

/**
 *
 * @author Nam
 */
public class OverrallFrame extends javax.swing.JFrame implements MouseListener {
    public static String userEmail;
    public static ArrayList<String> userOnline = new ArrayList<>();
    private static ObjectOutputStream out;
    private ObjectInputStream in;
    public ArrayList<String> arrAlert = new ArrayList<>();
    private Socket socket = null;
    String arrIcon[] = {"icons8-speech-balloon-20.png","icons8-management-20.png",
        "icons8-gear-20.png", "icons8-list-20.png"};
    private ArrayList<LeftMenu> navObj = new ArrayList<>();
    FormatTable formatTable = new FormatTable();
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modelAlert = new DefaultTableModel();
    static Gson gson = new Gson();
    private static String key;
    private int i;
    /**
     * Creates new form OverrallFrame
     */
    public OverrallFrame(Socket socket, String key) {
        try
        {
        initComponents();
        setSize(1055, 638);
        leftmenu();
        alertTableFormat();
        userTableFormat();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            WindowListener exitListener = new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    logOut("ok");
                }
            };
            addWindowListener(exitListener);
        
        this.socket = socket;
        this.key = key;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
               
        listen();
        navObj.get(0).doActive();
        changeMainInfo(1);
        loadUserTable();
        
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void logOut(String status)
    {
        ObjectSend obj = new ObjectSend("log_out", userEmail, status);
        write(obj);
        close(socket, in, out);
        this.dispose();
    }
    
    public void alertTableFormat()
    {
        formatTable.formatTablenoIcon(alertTable);
        Vector header = new Vector();
        header.add("");
        if (modelAlert.getRowCount()==0)
        { 
                modelAlert=new DefaultTableModel(header,0){
                @Override//No edit
                public boolean isCellEditable(int row, int column) 
                {       
                    return false;
                }
            };
        } 
        alertTable.setModel(modelAlert);
    }
    public void loadAlertTable(String alert)
    {
        arrAlert.add(alert);
        try
        {
            int rowCount = modelAlert.getRowCount();//remove all row
            for (int i = rowCount - 1; i >= 0; i--) 
            {
                modelAlert.removeRow(i);
            }
            for (int i = arrAlert.size() - 1; i >= 0; i--) 
            {
                Vector row = new Vector();
                row.add(arrAlert.get(i));
                modelAlert.addRow(row);
            }
           
            alertTable.setModel(modelAlert);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void userTableFormat()
    {
        formatTable.formatTablenoIcon(userTable);
        Vector header = new Vector();
        header.add("Email");
        header.add("");
        if (model.getRowCount()==0)
        { 
                model=new DefaultTableModel(header,0){
                @Override//No edit
                public boolean isCellEditable(int row, int column) 
                {       
                    if(column == 1)
                    {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
        } 
        userTable.setModel(model);
        
        userTable.getColumnModel().getColumn(1).setCellEditor(new AddFriendButtonRender(userTable));
        userTable.getColumnModel().getColumn(1).setCellRenderer(new AddFriendButtonRender(userTable));
        userTable.getColumnModel().getColumn(0).setPreferredWidth(260);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    public void loadUserTable()
    {
        try
        {
            int rowCount = model.getRowCount();//remove all row
            for (int i = rowCount - 1; i >= 0; i--) 
            {
                model.removeRow(i);
            }
            for(String str : userOnline)
            {
                if(!str.equals(userEmail))
                {
                    Vector row = new Vector();
                    row.add(str);
                    model.addRow(row);
                }
            }
            userTable.setModel(model);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void leftmenu() {
        for(int i = 0; i< arrIcon.length; i++)
        {  
            navObj.add(new LeftMenu("", new Dimension(60, 60), arrIcon[i]));
            navObj.get(i).addMouseListener(this);
            //duyet mang menu
            for (LeftMenu n : navObj) 
            {
                LeftPanel.add(n);//add vao thanh menu
            }
            LeftPanel.repaint();
            LeftPanel.revalidate();
        }
        
    }
    private void listen() 
    {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (socket.isConnected()) 
                        {
                            String decrypStr = cipher.decrypt(in.readObject().toString(), key);
                            ObjectSend obj = gson.fromJson(decrypStr , ObjectSend.class);

                            if (obj.getTag().equals("send_online_list")) 
                            {
                                loadAlertTable(obj.getObject().toString());
                                System.out.println(obj.getObject());
                                if(i == 1)
                                {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<UserDTO>>() {}.getType();
                                ChatPanel.arrayUser = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                content.removeAll();
                                JPanel p1 = new ChatPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();                               
                                if(!SingleChatPanel.currentEmail.equals(""))
                                {
                                    ChatPanel.loadChat();
                                }
                                }
                            } 
                            if (obj.getTag().equals("user_online_in_server")) 
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                                userOnline = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                loadUserTable();
                            } 
                            if(obj.getTag().equals("get_friend_list"))
                            {

                                java.lang.reflect.Type listType = new TypeToken<ArrayList<UserDTO>>() {}.getType();
                                ChatPanel.arrayUser = gson.fromJson(gson.toJson(obj.getArr()), listType);

                                resetStatic();
                                content.removeAll();
                                JPanel p1 = new ChatPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();
                            }
                            if (obj.getTag().equals("get_chat_panel")) 
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<MessageDTO>>() {}.getType();
                                SingleChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                String userEmail = (String) obj.getObjectTemp();
                                String userName =(String) obj.getObject();
                                SingleChatPanel.currentEmail = userEmail;
                                SingleChatPanel.currentUsername = userName;
                                ChatPanel.loadChat();
                            }
                            if (obj.getTag().equals("alert_join_group")) 
                            {
                                loadAlertTable(obj.getObject().toString());
                            }
                            if (obj.getTag().equals("get_block_user_chat_panel")) 
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<MessageDTO>>() {}.getType();
                                UserBlockChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                String userEmail = (String) obj.getObjectTemp();
                                String userName =(String) obj.getObject();
                                UserBlockChatPanel.currentEmail = userEmail;
                                UserBlockChatPanel.currentUsername = userName;
                                BlockPanel.loadChatUser();
                            }
                            if (obj.getTag().equals("get_block_group_chat_panel")) 
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<GroupMessageDTO>>() {}.getType();
                                GroupBlockChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType);

                                GroupBlockChatPanel.currentGroupId = (String)obj.getObject();
                                GroupBlockChatPanel.currentGroupName =  (String)obj.getObjectTemp();
                                BlockPanel.loadChatGroup();
                            }  
                            if(obj.getTag().equals("get_chat_panel_update"))
                            {
                                resetStatic();
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<MessageDTO>>() {}.getType();
                                SingleChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                java.lang.reflect.Type listType1 = new TypeToken<ArrayList<UserDTO>>() {}.getType();
                                ChatPanel.arrayUser = gson.fromJson(gson.toJson(obj.getArrTemp()), listType1);
                                
                                String userEmail = (String) obj.getObjectTemp();
                                String userName =(String) obj.getObject();
                                SingleChatPanel.currentEmail = userEmail;
                                SingleChatPanel.currentUsername = userName;
                                content.removeAll();
                                JPanel p1 = new ChatPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();                               

                                ChatPanel.loadChat();
                            }
                            if(obj.getTag().equals("get_group_list"))
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<GroupDTO>>() {}.getType();
                                GroupPanel.arrayGroup = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                resetStatic();
                                content.removeAll();
                                JPanel p1 = new GroupPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();
                            }
                            if (obj.getTag().equals("get_group_chat_panel")) 
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<GroupMessageDTO>>() {}.getType();
                                GroupChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType);

                                GroupChatPanel.currentGroupId = (String)obj.getObject();
                                GroupChatPanel.currentGroupName =  (String)obj.getObjectTemp();
                                GroupPanel.loadChat();
                            }  
                            if (obj.getTag().equals("get_group_chat_panel_update")) 
                            {
                                resetStatic();
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<GroupDTO>>() {}.getType();
                                GroupPanel.arrayGroup = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                java.lang.reflect.Type listType1 = new TypeToken<ArrayList<GroupMessageDTO>>() {}.getType();
                                GroupChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArrTemp()), listType1);

                                GroupChatPanel.currentGroupId = (String)obj.getObject();
                                GroupChatPanel.currentGroupName =  (String)obj.getObjectTemp();
                                
                                content.removeAll();
                                JPanel p1 = new GroupPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();
                                GroupPanel.loadChat();
                            }  
                            if (obj.getTag().equals("send_mess_to_user")) 
                            {                                
                                String senderEmail = (String) obj.getObjectTemp();
                                if(SingleChatPanel.currentEmail.equals(senderEmail))
                                {
                                    java.lang.reflect.Type listType1 = new TypeToken<ArrayList<MessageDTO>>() {}.getType();
                                    SingleChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType1);

                                    ChatPanel.loadChat();
                                } else {
                                    //alert
                                    loadAlertTable(obj.getObject().toString());
                                }
                            }
                            if (obj.getTag().equals("send_mess_to_group")) 
                            {                                
                                String groupId = (String) obj.getObjectTemp();
                                if(GroupChatPanel.currentGroupId.equals(groupId))
                                {
                                    java.lang.reflect.Type listType1 = new TypeToken<ArrayList<GroupMessageDTO>>() {}.getType();
                                    GroupChatPanel.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType1);
                                    GroupPanel.loadChat();
                                } else {
                                    //alert
                                    loadAlertTable(obj.getObject().toString());
                                }
                            }
                            if (obj.getTag().equals("out_group")) 
                            {                                
                                loadAlertTable(obj.getObject().toString());
                            }
                            if (obj.getTag().equals("add_friend_request")) 
                            {                                
                                String user = (String)obj.getObject();
                                String friend = (String)obj.getObjectTemp();
                                if (JOptionPane.showConfirmDialog(content, user + " muốn kết nối với bạn?", "WARNING",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
                                {
                                    ObjectSend objSend = new ObjectSend("add_contact", user, friend);
                                    write(objSend);                                   
                                } else {
                                    ObjectSend objSend = new ObjectSend("add_friend_request_NO", user, friend);
                                    write(objSend);
                                }
                            }
                            if (obj.getTag().equals("add_friend_request_NO")) 
                            {                
                                //alert
                                //String alert = (String)obj.getObject();
                                loadAlertTable(obj.getObject().toString());
                            }
                            if (obj.getTag().equals("get_file_mess_list")) 
                            {          
                                java.lang.reflect.Type listType1 = new TypeToken<ArrayList<MessageDTO>>() {}.getType();
                                UserFileDownDialog.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType1);
                                UserFileDownDialog nam = new UserFileDownDialog();
                                nam.setVisible(true);
                                nam.setLocationRelativeTo(content);
                            }
                            if (obj.getTag().equals("get_file_group_mess_list")) 
                            {                
                                java.lang.reflect.Type listType1 = new TypeToken<ArrayList<GroupMessageDTO>>() {}.getType();
                                GroupFileDownDialog.arrMess = gson.fromJson(gson.toJson(obj.getArr()), listType1);
                                GroupFileDownDialog nam = new GroupFileDownDialog();
                                nam.setVisible(true);
                                nam.setLocationRelativeTo(content);
                            }
                            if (obj.getTag().equals("get_edit_group_info")) 
                            {      
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<UserDTO>>() {}.getType();
                                EditGroupDialog.arrMember = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                EditGroupDialog.arrFriend = gson.fromJson(gson.toJson(obj.getArrTemp()), listType);

                                EditGroupDialog.groupName = (String) obj.getObject();
                                EditGroupDialog nam = new EditGroupDialog();
                                nam.setVisible(true);
                                nam.setLocationRelativeTo(content);
                            }
                            if(obj.getTag().equals("get_block_list"))
                            {
                                java.lang.reflect.Type listType = new TypeToken<ArrayList<UserDTO>>() {}.getType();
                                BlockPanel.arrayUser = gson.fromJson(gson.toJson(obj.getArr()), listType);
                                java.lang.reflect.Type listType1 = new TypeToken<ArrayList<GroupDTO>>() {}.getType();
                                BlockPanel.arrayGroup = gson.fromJson(gson.toJson(obj.getArrTemp()), listType1);

                                resetStatic();
                                content.removeAll();
                                JPanel p1 = new BlockPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();
                            }
                            if(obj.getTag().equals("get_edit_user_info"))
                            {
                                UserDTO user = gson.fromJson( gson.toJson(obj.getObject()), UserDTO.class);
                                resetStatic();
                                content.removeAll();
                                EditUserPanel.currentUser = user;
                                JPanel p1 = new EditUserPanel();
                                p1.setSize(720,600);
                                content.add(p1);
                                content.repaint();
                                content.revalidate();
                            }
                            if (obj.getTag().equals("server_broadcast")) 
                            {
                                loadAlertTable(obj.getObject().toString());
                            }
                            if (obj.getTag().equals("this_is_block_user")) 
                            {
                                JOptionPane.showMessageDialog(content, "Bạn đã block user này!");
                            }
                            if (obj.getTag().equals("login_duplicate")) 
                            {
                                JOptionPane.showMessageDialog(content, "Tài khoản này được đăng nhập ở nơi khác");
                                logOut("ok");
                                dispose();
//                                loginForm login = new loginForm();
//                                login.setVisible(true);
                            }
                            if (obj.getTag().equals("ban")) 
                            {
                                JOptionPane.showMessageDialog(content, "Tài khoản của bạn đã bị ban!");
                                logOut("koOk");
                                dispose();
//                                loginForm login = new loginForm();
//                                login.setVisible(true);
                            }
                        }
                    } catch (Exception e) {
                        //close(socket, in, out);
                        e.printStackTrace();
                    }
                }
            }).start();
    }
    public static void resetStatic()
    {
        SingleChatPanel.currentEmail = "";
        SingleChatPanel.currentUsername = "";
        GroupChatPanel.currentGroupId = "";
        GroupChatPanel.currentGroupName = "";
    }
    public static void write(ObjectSend object)
    {
        try {
            String json = gson.toJson(object);
            String obj = cipher.encrypt(json, key);
            
            out.writeObject(obj);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        LeftPanel = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        alertTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(1020, 670));
        jPanel2.setMinimumSize(new java.awt.Dimension(1020, 670));
        jPanel2.setPreferredSize(new java.awt.Dimension(1020, 670));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LeftPanel.setBackground(new java.awt.Color(51, 204, 255));
        LeftPanel.setAlignmentX(0.0F);
        LeftPanel.setAlignmentY(0.0F);
        LeftPanel.setMaximumSize(new java.awt.Dimension(210, 580));
        LeftPanel.setMinimumSize(new java.awt.Dimension(210, 40));
        LeftPanel.setPreferredSize(new java.awt.Dimension(210, 580));
        LeftPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));
        jPanel2.add(LeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 60, 410));

        content.setBackground(new java.awt.Color(0, 0, 0));
        content.setMaximumSize(new java.awt.Dimension(800, 600));
        content.setMinimumSize(new java.awt.Dimension(800, 600));
        content.setPreferredSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel2.add(content, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 720, -1));

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(51, 204, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("THÔNG BÁO");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 260, 30));

        alertTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        alertTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(alertTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 260, 220));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 260));

        jPanel5.setBackground(new java.awt.Color(0, 102, 153));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("USERS ONLINE IN SERVER ");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 260, 30));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        userTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(userTable);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 260, 290));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 260, 340));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, 260, 600));

        jPanel3.setBackground(new java.awt.Color(51, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-exit1-20.png"))); // NOI18N
        logoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        jPanel3.add(logoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 60));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 190));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        logOut("ok");
    }//GEN-LAST:event_logoutBtnActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(OverrallFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(OverrallFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(OverrallFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(OverrallFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new OverrallFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JTable alertTable;
    public static javax.swing.JPanel content;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JButton logoutBtn;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i< navObj.size(); i++)
            {
                LeftMenu item = navObj.get(i);
                if (e.getSource().equals(item)) 
                {
                    item.doActive(); // Active NavItem đc chọn 
                    changeMainInfo(i + 1);//truyền xuong de thay doi content
                } else 
                {
                    item.noActive();
                }
            }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    private void changeMainInfo(int i)
    {
        ObjectSend ob;
        switch(i)
        {
            case 1: //chat   
                this.i = 1;
                ob = new ObjectSend("get_friend_list", userEmail);
                write(ob);                
                break;
            case 2:  // chat group     
                //content.removeAll();  
                this.i = 2;
                ob = new ObjectSend("get_group_list", userEmail);
                write(ob);                
                break;
            case 3:  // sua thong tin    
                this.i = 3;
                ob = new ObjectSend("get_edit_user_info", userEmail);
                write(ob);                
                break;
            case 4:  // danh sach block 
                this.i = 4;
                ob = new ObjectSend("get_block_list", userEmail);
                write(ob);                
                break;
        }
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
