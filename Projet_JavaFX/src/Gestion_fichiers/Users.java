/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lenovo
 */
public class Users {
    private String id,nom,prenom,Adresse_mail,MDP,type,question,reponse;
    private int nbrFichiers;

    public Users(String id, String nom, String prenom, String Adresse_mail, String MDP, String type, String question, String reponse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.Adresse_mail = Adresse_mail;
        this.MDP = MDP;
        this.type = type;
        this.question = question;
        this.reponse = reponse;
    }
    
    public Users(String id,String MDP){
        this.MDP = MDP;
        this.id = id;
    }

    public Users(String id, String question, String reponse) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse_mail() {
        return Adresse_mail;
    }

    public void setAdresse_mail(String Adresse_mail) {
        this.Adresse_mail = Adresse_mail;
    }

    public String getMDP() {
        return MDP;
    }

    public void setMDP(String MDP) {
        this.MDP = MDP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setNbrFichiers(int nbrFichiers) {
        this.nbrFichiers = nbrFichiers;
    }

    public int getNbrFichiers() {
        return nbrFichiers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    
    
    
    public void insererBD_SignUp() throws SQLException{
        Connection con =null;
        PreparedStatement ps=null;
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="INSERT INTO users (Id,nom,prenom,adresse_mail,MDP,type,question,reponse)VALUES(?,?,?,?,?,?,?,?)";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            if (!"".equals(Adresse_mail))
            ps.setString(4, Adresse_mail);
            else
                ps.setString(4, null);
            ps.setString(5, MDP);
            ps.setString(6, type);
            ps.setString(7,question);
            ps.setString(8, reponse);
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally{//on ferme notre Base de donnés pour sa sécurité
            if (ps !=null){
                ps.close();
            }
            if (con!=null)
                con.close();
        }
    }
    
    
    
    public ObservableList<Users> afficherUsersBD_InfoG() throws SQLException{
        ObservableList<Users> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT COUNT(DISTINCT t.numFichier), u.*  FROM users u\n" +
                                "LEFT JOIN tags t\n" +
                                "on u.Id=t.Id\n" +
                                "group by u.Id");
            while(rs.next())
            {   
                Users u = new Users(rs.getString("Id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse_mail"),rs.getString("MDP"),rs.getString("type"), null, null);
                u.setNbrFichiers(rs.getInt(1));
                util.add(u);
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
        return util;
    }
    
    
    
}
