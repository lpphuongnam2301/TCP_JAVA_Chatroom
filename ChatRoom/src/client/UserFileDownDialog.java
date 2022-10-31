/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import temp.MessageDTO;

/**
 *
 * @author lpphu
 */
public class UserFileDownDialog extends javax.swing.JFrame {
    FormatTable formatTable = new FormatTable();
    DefaultTableModel model;
    public static ArrayList<MessageDTO> arrMess;
    /**
     * Creates new form UserFileDownDialog
     */
    public UserFileDownDialog() {
        initComponents();
        tableFormat();
        loadTable();
    }
    public void tableFormat()
    {
        formatTable.formatTablenoIcon(table);
        model =new DefaultTableModel();
        Vector header = new Vector();
        header.add("ID");
        header.add("Tên file");
        header.add("Ngày gửi");
        header.add("Người gửi");
        header.add("");
        if (model.getRowCount()==0)
        { 
                model=new DefaultTableModel(header,0){
                @Override//No edit
                public boolean isCellEditable(int row, int column) 
                {       
                    if(column == 4)
                    {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
        } 
        table.setModel(model);
        table.getColumnModel().getColumn(4).setCellEditor(new downloadBtn());
        table.getColumnModel().getColumn(4).setCellRenderer(new downloadBtn());
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
    }
    public void loadTable()
    {
        if(!arrMess.isEmpty())
        {
            try {
                int rowCount = model.getRowCount();//remove all row
                for (int i = rowCount - 1; i >= 0; i--) 
                {
                    model.removeRow(i);
                }
                for (MessageDTO str : arrMess) {
                    
                        Vector row = new Vector();
                        row.add(str.getMessage_id());
                        row.add(str.getMessage_file());
                        row.add(str.getMessage_time());
                        row.add(str.getUser_sender());
                        model.addRow(row);

                }
                table.setModel(model);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    }
    class downloadBtn extends AbstractCellEditor implements TableCellRenderer,TableCellEditor,ActionListener{
    JButton addbtn;
    Icon addIcon = new ImageIcon(this.getClass().getResource("/img/icons8-downloads-20.png"));
    public downloadBtn(){
        addbtn = new JButton();
        addbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addbtn.setIcon(addIcon);
        addbtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try
                {
                    stopCellEditing();
                    
                    int i = table.getSelectedRow();
                    byte[] arrB = null;
                    for(MessageDTO nam : arrMess)
                    {
                        if(nam.getMessage_id() == Integer.parseInt(table.getValueAt(i, 0).toString()))
                        {
                            arrB = nam.getArrByte();
                            break;
                        }
                    }
                    try {
                        String fileName  = table.getValueAt(i, 1).toString();
                        FileOutputStream os = null;
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int result = fileChooser.showSaveDialog(jPanel1);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + fileName);
                            if (!file.exists()) {
                                os = new FileOutputStream(file);
                                os.write(arrB);
                                os.close();
                            } else {
                                int ran = (int) (Math.random() * ((200 - 2) + 1)) + 2;
                                String temp = fileName.split("\\.")[0] + Integer.toString(ran) + "." + fileName.split("\\.")[1];
                                File file1 = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + temp);
                                os = new FileOutputStream(file1);
                                os.write(arrB);
                                os.close();
                            }
                        }
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(jPanel1, "Download file hoàn tất!","THÀNH CÔNG", JOptionPane.PLAIN_MESSAGE);
                    stopCellEditing();
                } catch(Exception err) {
                    System.out.println(err);}
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
    
    @Override 
    public boolean stopCellEditing(){
        return super.stopCellEditing();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Icon) addbtn.setIcon((Icon)value);
        return addbtn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value instanceof Icon) addbtn.setIcon((Icon)value);
        return addbtn;
    }
    
    @Override
    protected void fireEditingStopped(){
        super.fireEditingStopped();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH FILES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 590, 40));

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 60, 570, 290));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFileDownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFileDownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFileDownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFileDownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserFileDownDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
