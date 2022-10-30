/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.awt.Color;
import static java.awt.Color.white;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;


public class FriendMenu extends JPanel implements MouseListener{
    private JLabel lb,icon;
    private Color hover = new Color(100, 113, 140);
    private Color normal = new Color(0,102,153);
    private String chatId, target, current;
    //private Color normal = new Color(100, 113, 140);
    private boolean active ;
    private String name,img,imgActive,imgHover;
    private Dimension size = new Dimension();
    public FriendMenu(String name, Dimension size, String icon, String chatId, String target)
    {
        this.target = target;
        this.setLayout(null);
        this.chatId = chatId;
        lb = new JLabel(name);
        lb.setBounds(50, 1, 220, 50);
        lb.setFont(new Font("Time New Roman", Font.BOLD, 12));
        
        JLabel lbIcon = new JLabel();
        Icon Icon = new ImageIcon(this.getClass().getResource("/img/" + icon));
        lbIcon.setIcon(Icon);
        lbIcon.setBounds(20, 0, 30, 55);
        lb.setForeground(white);
        this.add(lb);
        this.add(lbIcon);
        this.setPreferredSize(size);
        //this.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
        this.addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        init();
    }
    public FriendMenu(String name, Dimension size, String icon, String chatId, String target, String current)
    {
        this.current = current;
        this.target = target;
        this.setLayout(null);
        this.chatId = chatId;
        lb = new JLabel(name);
        lb.setBounds(50, 1, 220, 50);
        lb.setFont(new Font("Time New Roman", Font.BOLD, 12));
        
        JLabel lbIcon = new JLabel();
        Icon Icon = new ImageIcon(this.getClass().getResource("/img/" + icon));
        lbIcon.setIcon(Icon);
        lbIcon.setBounds(20, 0, 30, 55);
        lb.setForeground(white);
        this.add(lb);
        this.add(lbIcon);
        this.setPreferredSize(size);
        //this.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
        this.addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        init();
    }
    public String getTarget() {
        return target;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
        
    public void init()
    {
        if(active)
        {
            setBackground(new Color(51,204,255));
        }
        else
        {
            setBackground(normal);
        } 
    }
    public void setColorNormal(Color color)
    {
        this.normal = color;
        setBackground(normal);
        repaint();   
    }
   
    
    public void doActive()
    {
        active = true;
        lb.setForeground(new Color(0,0,0));
        setBackground(new Color(51,204,255));
    }
    public void noActive()
    {
        active = false;
        lb.setForeground(new Color(255,255,255));
        setBackground(normal);
    }
    
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
        if(!active)
        {
            setBackground(hover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e){
        if(!active)
        {
            setBackground(normal);
        }
    }
}
