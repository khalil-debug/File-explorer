/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty; //c'est la classe permettant d'interagir avec les tables
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lenovo
 */
public class Tags {
    private SimpleStringProperty Tag;
    private String tagUtil;
    private int num;

    

    public Tags(String Tag) {
        this.Tag = new SimpleStringProperty(Tag);
    }
    
    
    public String getTag() {
        return Tag.get();
    }

    public void setTag(String Tag) {
        this.Tag = new SimpleStringProperty(Tag);
    }
    
    public String getTagUtil() {
        return tagUtil;
    }

    public void setTagUtil(String tagUtil) {
        this.tagUtil = tagUtil;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Tags(String tagUtil, int num) {
        this.tagUtil = tagUtil;
        this.num = num;
    }
    
    public ObservableList<Tags> afficherTagInfo() throws SQLException{
        ObservableList<Tags> t= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT tag, count(id) from tags group by tag");
            while(rs.next())
            {   
                
                Tags tg=new Tags(rs.getString(1),rs.getInt(2));
                t.add(tg);
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally
        {
            if( con!= null)
                con.close();
            if (s!=null)
                s.close();
            if (rs!=null)
                rs.close();
        }
        return t;
    }
    
    
    public ObservableList<Tags> afficherRechercheTag(String u) throws SQLException{
        ObservableList<Tags> t= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT Id from tags where tag='"+u+"'");
            while(rs.next())
            {   
                Tags tg=new Tags(rs.getString(1),0);
                t.add(tg);
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally
        {
            if( con!= null)
                con.close();
            if (s!=null)
                s.close();
            if (rs!=null)
                rs.close();
        }
        return t;
    }
    
    
}
