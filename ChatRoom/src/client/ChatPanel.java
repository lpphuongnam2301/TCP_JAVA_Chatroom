/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package client;

import com.mysql.cj.protocol.Message;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import temp.LeftMenu;
import temp.FriendMenu;
import temp.MessageDTO;
import temp.ObjectSend;
import temp.UserDTO;

/**
 *
 * @author lpphu
 */
public class ChatPanel extends javax.swing.JPanel implements MouseListener{
    public static ArrayList<UserDTO> arrayUser = new ArrayList<>();
    public String currentActive;
    public ArrayList<FriendMenu> navObj = new ArrayList<>();
    /**
     * Creates new form ChatPanel
     */
    public ChatPanel() {
        initComponents();
        leftmenu();
        user_emailLB.setText(OverrallFrame.userEmail);
        //active
        if(!SingleChatPanel.currentEmail.equals(""))
        {
            for(FriendMenu a : navObj)
            {
                //System.out.println(a.getCurrent() + ":" + SingleChatPanel.currentEmail);
                if(a.getCurrent().equals(SingleChatPanel.currentEmail))
                {
                    a.doActive();
                    break;
                }
            }
        }
        friendPanel.setPreferredSize(new Dimension(220, arrayUser.size()*55));
    }

    public void leftmenu() {
        if(!arrayUser.isEmpty())
        {
        for(int i = 0; i< arrayUser.size(); i++)
        {  
            navObj.add(new FriendMenu(arrayUser.get(i).getUser_name() + " - " + arrayUser.get(i).getUser_status(), new Dimension(220, 50), "icons8-businessman-20.png", 
                    arrayUser.get(i).getUser_email()+","+OverrallFrame.userEmail, arrayUser.get(i).getUser_name() + "," + arrayUser.get(i).getUser_email(), arrayUser.get(i).getUser_email()));
            navObj.get(i).addMouseListener(this);
            //duyet mang menu
            for (FriendMenu n : navObj) 
            {
                friendPanel.add(n);//add vao thanh menu
            }
            friendPanel.repaint();
            friendPanel.revalidate();
        }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        user_emailLB = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendPanel = new javax.swing.JPanel();
        chatPanel = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchPanel.setBackground(new java.awt.Color(51, 204, 255));

        user_emailLB.setBackground(new java.awt.Color(255, 255, 255));
        user_emailLB.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        user_emailLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_emailLB.setText("FRIENDS LIST");
        user_emailLB.setToolTipText("");

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(user_emailLB, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(user_emailLB, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 40));

        jScrollPane1.setBorder(null);

        friendPanel.setBackground(new java.awt.Color(0, 102, 153));
        jScrollPane1.setViewportView(friendPanel);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 220, 560));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 600));

        chatPanel.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel1.add(chatPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 500, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel chatPanel;
    private javax.swing.JPanel friendPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JLabel user_emailLB;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i< navObj.size(); i++)
            {
                FriendMenu item = navObj.get(i);
                if(e.getSource().equals(item))
                    {
                        item.doActive(); // Active NavItem đc chọn 
                        ObjectSend obj = new ObjectSend("get_chat_panel", item.getChatId(), item.getTarget());
                        OverrallFrame.write(obj);
                    }
                else
                    {
                        item.noActive();
                    }
            }
    }
    public static void loadChat()
    {
        chatPanel.removeAll();
        JPanel p = new SingleChatPanel();        
        p.setSize(500,600);
        chatPanel.add(p);
        chatPanel.repaint();
        chatPanel.revalidate();
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
}