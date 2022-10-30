/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.awt.Color;
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
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;


public class EmojiMenu extends JPanel implements MouseListener{
    private JLabel lb,icon;
    private Color hover = new Color(100, 113, 140);
    //private Color normal = new Color(102,153,255);
    //private Color normal = Color.WHITE;
    private Color normal = new Color(51,204,255);
    private boolean active ;
    public String iconPath;
    private String name,img,imgActive,imgHover;
    private Dimension size = new Dimension();
    public EmojiMenu(String name, Dimension size, String icon)
    {
        //LineBorder line = new LineBorder(Color.blue, 5, true);
        this.setLayout(null);
        lb = new JLabel(name);
        lb.setBounds(10, 1, 40, 40);
        lb.setFont(new Font("Time New Roman", Font.BOLD, 12));
        
        JLabel lbIcon = new JLabel();
        Icon Icon = new ImageIcon(this.getClass().getResource("/emoji/" + icon));
        lbIcon.setIcon(Icon);
        lbIcon.setBounds(5, 0, 40, 40);
        this.iconPath = icon;
        this.add(lb);
        this.add(lbIcon);
        this.setPreferredSize(size);
        //this.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
        this.addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //lbIcon.setBorder(line);
        init();
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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
        setBackground(new Color(0,102,153));
    }
    public void noActive()
    {
        active = false;
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
