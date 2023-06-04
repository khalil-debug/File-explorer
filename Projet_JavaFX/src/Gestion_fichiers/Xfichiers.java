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
public class Xfichiers {
    private String URL,auteur,titre,tag,commentaire,resume,fichier;
    private int numFichier, numtag;

    //les constructeur

    public Xfichiers(String URL, String titre, String fichier) {
        this.URL = URL;
        this.titre = titre;
        this.fichier = fichier;
    }
    
    
    public Xfichiers(String URL, String Auteur, String Titre, String commentaire, String resume, int numFichier) {
        this.URL = URL;
        this.auteur = Auteur;
        this.titre = Titre;
        this.numFichier=numFichier;
        this.commentaire = commentaire;
        this.resume = resume;
    }

    public Xfichiers(String tag) {
        this.tag = tag;
    }
    
    public Xfichiers(String tag, String url) {
        this.tag = tag;
        this.URL=url;
    }

    public Xfichiers(String URL, String auteur, String titre, String tag, String commentaire, String resume, int numFichier, int numtag) {
        this.URL = URL;
        this.auteur = auteur;
        this.titre = titre;
        this.tag = tag;
        this.commentaire = commentaire;
        this.resume = resume;
        this.numFichier = numFichier;
        this.numtag=numtag;
    }
    
    //les getters et setters des variables
    public int getNumtag() {
        return numtag;
    }

    public void setNumtag(int num) {
        this.numtag = num;
    }
    
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String Auteur) {
        this.auteur = Auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String Titre) {
        this.titre = Titre;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String Tag) {
        this.tag = Tag;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getNumFichier() {
        return numFichier;
    }

    public void setNumFichier(int numFichier) {
        this.numFichier = numFichier;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
    
    

    
    //les fonctions des controlleurs
    
    //fonction pour avoir le commentaire sur le fichier selectionné dans le controller de consultation
    String getCommentaire(String id, String t) throws SQLException {
        String st=null;
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT f.commentaire from fichiersfavoris f, tags t where f.titre='"+t+"' and f.numFichier=t.numFichier and "
                    + "t.Id='"+id+"' group by f.commentaire");
            while (rs.next())
                st=rs.getString("commentaire");
            
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
        return st;
    }

    //fonction pour avoir le résumé du fichier dans le controller de consultation
    String getRes(String id, String t) throws SQLException {
        String st=null;
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT f.resumé from fichiersfavoris f, tags t where f.titre='"+t+"' and f.numFichier=t.numFichier and "
                    + "t.Id='"+id+"' group by f.resumé");
            while (rs.next())
                st=rs.getString("resumé");
            
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
        return st;
    }

    //fonction qui retourne une observable list pour insérer ses elements dans le tableView de visualisation des fichiers dans consultation
    ObservableList<Xfichiers> get_user_filesConsultation(String t) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT f.* from fichiersfavoris f, tags t where t.Id='"+t+"' and f.numFichier=t.numFichier group by f.numFichier");
            while(rs.next())
            {   
                
                Xfichiers u= new Xfichiers(rs.getString("URL"),rs.getString("auteur"),rs.getString("titre"),rs.getString("commentaire"),rs.getString("resumé"),rs.getInt("numFichier"));
                
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

    //fonction qui retourne une observable list pour insérer ses elements dans le tableView des tags dans consultation
    ObservableList<Xfichiers> get_tags_Consultation(String t) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT tag from tags  where Id='"+t+"'");
            while(rs.next())
            {   
                
                Xfichiers u= new Xfichiers(rs.getString("tag"));
                
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

    ObservableList<Xfichiers> afficher_CIU(String text) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT f.URL, t.tag from fichiersfavoris f, tags t where t.Id='"+text+"' and f.numFichier=t.numFichier"
                    + " group by t.tag");
            while(rs.next())
            {   
                
                Xfichiers u= new Xfichiers(rs.getString("tag"),rs.getString("URL"));
                
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

    ObservableList<Xfichiers> get_user_files(String id) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("select f.*, t.tag , t.numTag from fichiersfavoris f, tags t where t.numFichier=f.numFichier and t.Id='"+id+"' group by t.tag");
            while(rs.next())
            {
                Xfichiers u= new Xfichiers(rs.getString("URL"),rs.getString("auteur"),rs.getString("titre"),rs.getString("tag"),rs.getString("commentaire"),rs.getString("resumé"),rs.getInt("numFichier"),rs.getInt("numTag"));
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

    void SupprimerFichier(Xfichiers f) throws SQLException {
        Connection con =null;
        Statement ps=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="delete from fichiersfavoris where numFichier="+f.getNumFichier();
            ps=con.prepareStatement(sql);
            ps.executeUpdate(sql);
            
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

    void SupprimerTagFichiers(Xfichiers f) throws SQLException {
        Connection con =null;
        Statement ps=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="delete from tags where numFichier="+f.getNumFichier()+" and tag='"+f.getTag()+"'";
            ps=con.prepareStatement(sql);
            ps.executeUpdate(sql);
            
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

    void ModifierFichiers(Xfichiers f) throws SQLException {
        Connection con =null;
        PreparedStatement ps=null;
        PreparedStatement p=null;
        
        
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
      
            String sql="UPDATE fichiersfavoris set resumé=? , titre=?, auteur=?, commentaire=? where numFichier="+f.getNumFichier()+".";
            ps=con.prepareStatement(sql);
            ps.setString(1, f.getResume());
            ps.setString(2, f.getTitre());
            ps.setString(3, f.getAuteur());
            ps.setString(4, f.getCommentaire());
            
            ps.executeUpdate();
            String t="Update tags set tag='"+f.getTag()+"' where numTag="+f.getNumtag();
            p=con.prepareStatement(t);
            p.executeUpdate();
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally{
            if (ps !=null){
                ps.close();
            }
            if (con!=null)
                con.close();
        }
    }

    ObservableList<Xfichiers> get_fichiersRechTag(String id, String text) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("select substring_index(f.URL,'\\\\',-1) as fich, f.URL, f.titre from fichiersfavoris f, tags t where "
                    + "t.tag like '"+text+"%' and t.numFichier=f.numFichier and t.Id='"+id+"' group by f.URL");
            while(rs.next())
            {
                Xfichiers u= new Xfichiers(rs.getString("URL"),rs.getString("titre"), rs.getString("fich"));
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

    ObservableList<Xfichiers> get_fichiersRechAuteur(String id, String text) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("select substring_index(f.URL,'\\\\',-1) as fich, f.URL, f.titre from fichiersfavoris f, tags t where "
                    + "f.auteur like '"+text+"%' and t.numFichier=f.numFichier and t.Id='"+id+"' group by f.URL");
            while(rs.next())
            {
                Xfichiers u= new Xfichiers(rs.getString("URL"),rs.getString("titre"), rs.getString("fich"));
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

    ObservableList<Xfichiers> get_fichiersRechTitre(String id, String text) throws SQLException {
        ObservableList<Xfichiers> util= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("select substring_index(f.URL,'\\\\',-1) as fich, f.URL, f.titre from fichiersfavoris f, tags t where \n" +
                "f.titre like '"+text+"%' and t.numFichier=f.numFichier and t.Id='"+id+"' group by f.URL");
            while(rs.next())
            {
                Xfichiers u= new Xfichiers(rs.getString("URL"),rs.getString("titre"), rs.getString("fich"));
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

    int add_file(Xfichiers f) throws SQLException {
        int n=0;
        Connection con =null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        Statement s;
        try{

            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");

            String sql="INSERT INTO fichiersfavoris (`numFichier`, `URL`, `titre`, `auteur`, `resumé`, `commentaire`) VALUES(NULL,?,?,?,?,?) ";
            ps=con.prepareStatement(sql);
            ps.setString(1, f.getURL());
            ps.setString(2, f.getTitre());
            if (!"".equals(f.getAuteur()))
            ps.setString(3, f.getAuteur());
            else
                ps.setString(3, null);
            if (!"".equals(f.getResume()))
            ps.setString(4, f.getResume());
            else
                ps.setString(4, null);
            if (!"".equals(f.getCommentaire()))
            ps.setString(5, f.getCommentaire());
            else
                ps.setString(5, null);
            ps.executeUpdate();
            
            
            
            s=con.createStatement();
            rs=s.executeQuery("select max(numFichier) as n from fichiersfavoris ");
            while(rs.next())
                n=rs.getInt("n");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally{//on ferme notre Base de donnés pour sa sécurité
            if (ps !=null){
                ps.close();
            }
            if (con!=null)
                con.close();
            if (rs!=null)
                rs.close();
        }
        return n;
    }

    void add_tags(Xfichiers f, String user) throws SQLException {
        Connection con =null;
        PreparedStatement ps=null;
        
        try{

            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");

            String sql="INSERT INTO `tags` (numTag,`numFichier`, `tag`, `Id`) VALUES(NULL,?,?,?) ";
            ps=con.prepareStatement(sql);
            ps.setInt(1, f.getNumFichier());
            ps.setString(2, f.getTag());
            ps.setString(3, user);
            
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
    
    
    
}
