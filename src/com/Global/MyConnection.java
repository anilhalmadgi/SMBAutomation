/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Global;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author Anil.Kumar
 */
public class MyConnection {
	  
    String f1,f2,f3,f4,url,userName,password;
    
    String dbName = "content_editor_google";
    String driver = "com.mysql.jdbc.Driver";
    public Connection conn = null;
    Statement stmt;
    
    public MyConnection(){
    }
    
    public MyConnection(String host,String user,String pass) throws Exception{            
        
	  url = "jdbc:mysql://"+host+":3306/";
	  userName = user; 
	  password = pass;
          Class.forName(driver).newInstance();
          conn = DriverManager.getConnection(url+dbName,userName,password);
          Logger.writeToLog("Connecting to Database server");          
    }
	    
      public ResultSet queryExec(String query) throws Exception {        
          
            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);            
            return rs;            
     } 	
      
     public void addTobatchnExecute(String query1,String query2)throws Exception {
         
         stmt = conn.createStatement();
         stmt.addBatch(query1);
         stmt.addBatch(query2);
         stmt.executeBatch();
          
     }
     
    public void closeConn() throws Exception
    {
            conn.close();
            Logger.writeToLog("Disconnected from database");
    }
}
