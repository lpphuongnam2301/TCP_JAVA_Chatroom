/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nam
 */
public class MyDBConnection {
    String host = "";
    String userName = "";
    String passWord = "";
    String database = "";
    String url = "";
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public MyDBConnection()
            //constr ket noi db 
    {
        host = "localhost";
        userName = "root";
        passWord = "";
        database = "chat_room";
        //url = "jdbc:mysql://"+host+":"+"3306"+"/"+database;
        this.getConnect();
    }
    public MyDBConnection(String host, String userName, String passWord, String database)
    {
        this.host = host;
        this.userName = userName;
        this.passWord = passWord;
        this.database = database;
    }
    protected void driverTest()
            //ham kiem tra ket noi driver 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("driver da ket noi");
        } catch(Exception e)
        {
            System.out.println("driver that bai");
            System.out.println(e);
        }
    }
    protected Connection getConnect() 
    {
        //neu conn = null thi tao moi
        if (conn == null)
        {
            try {
                this.url = "jdbc:mysql://"+this.host+":"+"3306"+"/"+this.database
                        + "?useUnicode=true&characterEncoding=UTF-8";
                //Class.forName("com.mysql.jdbc.Driver");
                //goi ham test ket noi Driver
                driverTest();
                //tao phien ket noi
                this.conn = DriverManager.getConnection(
                        this.url, this.userName, this.passWord);
                //System.out.println("ket noi thanh cong");
            } catch (Exception e) {
                System.out.println("ket noi database that bai");
                System.out.println(e);
//            } finally {
//                try {
//                    close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(MyDBConnection.class.getName()).log(Level.SEVERE, null, ex);
//                }
////            }
            }
        }
        return this.conn;
    }
    //ham thuc thi cau lenh query
    protected Statement getStatement() throws Exception
    {
        //kiem tra statement null hoac close 
        if (this.stmt == null || this.stmt.isClosed())
        {
            //tao moi
            this.stmt = conn.createStatement();
        }
        return this.stmt;
    }
    public ResultSet executeQuery(String query)
    {
        try
        {
        this.rs = this.getStatement().executeQuery(query);
        } catch(Exception e)
        {
            System.out.println("execute query that bai");
            //System.out.println(e);
        }
        return this.rs;
    }
    public int executeUpdate(String query) throws Exception
    {
        int res = Integer.MIN_VALUE;
        try
        {
            res = this.getStatement().executeUpdate(query);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
//        finally
//        {
//            conn.close();
//        }
        return res;
    }
    public void close() throws SQLException
    {
        //dong rs
        if (this.rs != null && !this.rs.isClosed())
        {
            this.rs.close();
            this.rs = null;
        }
        //dong stmt
        if(this.stmt != null && !this.stmt.isClosed())
        {
            this.stmt.close();
            this.stmt = null;
        } 
        //dong conn
        if(this.conn != null && !this.conn.isClosed())
        {
            this.conn.close();
            this.conn = null;
        } 
        
    }
}
