/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.ArrayList;
import server.MyDBConnection;
import temp.MessageDTO;
import java.sql.Blob;
import java.util.logging.Level;
import javax.swing.JFileChooser;


/**
 *
 * @author lpphu
 */
public class test {
    MyDBConnection con = new MyDBConnection();
    public String fileName = "";
    
    public byte[] readMessageFromUser()
    {
        InputStream input = null;
        FileOutputStream output = null;
        byte[] byteArr = null;
        try
        {
            String query = "SELECT * FROM `message` WHERE message_id = 152";
            ResultSet rs = con.executeQuery(query);
        while (rs.next())
            {
                fileName = rs.getString("message_file");
                Blob blob = rs.getBlob("message_fileByte");
                int blobLength = (int) blob.length();  
                byteArr = blob.getBytes(1, blobLength);
                blob.free();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return byteArr;
    }
    
//    public static void main(String[] args) {
//        try {
//            FileOutputStream os = null;
//            test a = new test();
//            byte[] arr = a.readMessageFromUser();
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            int result = fileChooser.showSaveDialog(null);
//            if (result == JFileChooser.APPROVE_OPTION) 
//            {
//                File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + a.fileName);
//                if (!file.exists()) {
//                    os = new FileOutputStream(file);
//                    os.write(arr);
//                    os.close();
//                } else {
//                    int ran = (int)(Math.random() * ((200 - 2) + 1)) + 2;
//                    String temp = a.fileName.split("\\.")[0] + Integer.toString(ran) +"."+ a.fileName.split("\\.")[1];
//                    File file1 = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + temp);
//                    os = new FileOutputStream(file1);
//                    os.write(arr);
//                    os.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public String md5(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] mdByte = md.digest(msg.getBytes());
        BigInteger no = new BigInteger(1, mdByte);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    public static void main(String[] args) throws Exception {
        test a = new test();
        System.out.println(a.md5("123456"));
    }
}
