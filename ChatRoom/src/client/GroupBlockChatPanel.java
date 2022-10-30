/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import temp.EmojiMenu;
import temp.GroupMessageDTO;
import temp.ObjectSend;

/**
 *
 * @author lpphu
 */
public class GroupBlockChatPanel extends javax.swing.JPanel implements MouseListener{
    public static ArrayList<GroupMessageDTO> arrMess = new ArrayList<>();
    public static String currentGroupId = "";
    public static String currentGroupName = "";
    private ArrayList<EmojiMenu> navObj = new ArrayList<>();
    String arrIcon[] = {"icons8-facebook-like-30.png","icons8-anime-emoji-30.png",
        "icons8-smiling-30.png", "icons8-loudly-crying-face-30.png", "icons8-smiling-face-with-heart-30.png", "icons8-smiling-face-with-heart-eyes-30.png"
    , "icons8-two-hearts-30.png", "icons8-confused-30.png", "icons8-thinking-face-30.png", "icons8-angry-30.png", "icons8-vietnam-flag-30.png"};
    /**
     * Creates new form singlePanel
     */
    public GroupBlockChatPanel() {
        initComponents();
        
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        appendToPane(textPane, "<div class='clear' style='background-color:white; padding-bottom: 7px;'></div>");
        
        nameLb.setText(currentGroupName.toUpperCase());
        readChat();
        lastCroll();
    }


    public void lastCroll()
    {
        int height = (int)textPane.getPreferredSize().getHeight();
        Rectangle rect = new Rectangle(0,height,0,0);
        textPane.scrollRectToVisible(rect);
    }
    public void readChat()
    {
        //System.out.println(OverrallFrame.userEmail);
        if(!arrMess.isEmpty())
        {
            for(GroupMessageDTO mes : arrMess)
            {
                //System.out.println(mes.getUser_name() + ":" + mes.getMessage_content());
                if(!OverrallFrame.userEmail.equals(mes.getUser_sender()))
                {
                    if(!mes.getMessage_content().equals("null"))
                    {
                        updateChat_receive(mes.getMessage_content(), mes.getMessage_time(), mes.getUser_name());
                    }
                    else if (!mes.getMessage_emoji().equals("null"))
                    {
                        String msg = "<img src='" + getClass().getResource("/emoji/" + mes.getMessage_emoji()) + "'></img>";
                        updateChat_receive(msg, mes.getMessage_time(), mes.getUser_name());
                    } else if (!mes.getMessage_file().equals("null"))
                    {
                        String msg = "<span style='font-weight:bold;font-size:12px;'>FILE:  </span>" + mes.getMessage_file();
                        updateChat_receive(msg, mes.getMessage_time(), mes.getUser_name());
                    }
                } else {
                    if(!mes.getMessage_content().equals("null"))
                    {
                        updateChat_send(mes.getMessage_content(), mes.getMessage_time());
                
                    } else if (!mes.getMessage_emoji().equals("null"))
                    {
                        String msg = "<img src='" + getClass().getResource("/emoji/" + mes.getMessage_emoji()) + "'></img>";
                        updateChat_send(msg, mes.getMessage_time());
                    } else if (!mes.getMessage_file().equals("null"))
                    {
                        String msg = "<span style='font-weight:bold;font-size:12px;'>FILE:  </span>" + mes.getMessage_file();
                        updateChat_send(msg, mes.getMessage_time());
                    }
                }
            }
        }
    }
    public void updateChat_receive(String msg, String time, String username) 
    {
        appendToPane(textPane, "<div class='left' style='padding: 10px; border: solid 3px white; width: 53%; background-color: #f1f0f0;'>" 
                + "<p style='font-style: italic; margin-top:-10px;'>" + time + "</p>" 
                + "<p style='font-weight: bold; margin-top:-5px;'>" + username + "</p>" 
                + "<p style='font-size:12px; margin-top: 3px;'>" + msg + "</p>" +
                "</div>");
    }

    public void updateChat_send(String msg, String time) 
    {
        appendToPane(textPane,
                "<table class='bang' style='color: black; clear:both; width: 100%; margin-left: 3%;'>" + "<tr align='right'>"
                + "<td style='width: 60%; '></td>" + "<td style='padding: 6px; width: 60%; background-color: rgb(51,204,255);'>"
                + "<p style='font-weight:bold; font-size:11px;'>" + time + "</p>" + "<p style='font-size:12px;'>" + msg + "</p>" + "</td> </tr>" + "</table>");
    }
    private void appendToPane(JTextPane tp, String msg) 
    {
        HTMLDocument doc = (HTMLDocument) tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
        try {

            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            tp.setCaretPosition(doc.getLength());

        } catch (Exception e) {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nameLb = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-join-20.png"))); // NOI18N
        jButton2.setText("Bỏ chặn");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 110, 40));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-team-30.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 60));

        nameLb.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        nameLb.setForeground(new java.awt.Color(51, 0, 204));
        nameLb.setText("GROUP NAME");
        jPanel2.add(nameLb, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 210, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 60));

        jPanel3.setBackground(new java.awt.Color(51, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 500, 60));

        textPane.setEditable(false);
        textPane.setBackground(new java.awt.Color(255, 255, 255));
        textPane.setBorder(null);
        textPane.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(textPane);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 500, 480));

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ObjectSend obj = new ObjectSend("unblock_group", OverrallFrame.userEmail, currentGroupId);
        OverrallFrame.write(obj);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLb;
    public javax.swing.JTextPane textPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
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
