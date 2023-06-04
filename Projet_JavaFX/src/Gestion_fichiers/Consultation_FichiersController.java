/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.LoginController.InUse;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class Consultation_FichiersController implements Initializable {

    @FXML private TableView<Xfichiers> listTab; 
    @FXML private TableColumn<Xfichiers, String> fichier;
    @FXML private TableColumn<Xfichiers, String> auteur; 
    @FXML private TableColumn<Xfichiers, String> titre; 
    @FXML private TableView<Xfichiers> tabTag; 
    @FXML private TableColumn<Xfichiers, String> tags; 
 
    @FXML private TextArea commentaire; 
    @FXML private TextArea resume;
    
    @FXML
    private void comm(Event e) throws SQLException{
        String t = listTab.getSelectionModel().getSelectedItem().getTitre();//on prend le titre selectionné dans la table
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        commentaire.setText(f.getCommentaire(LoginController.InUse.getId(), t));        
    }
    
    @FXML
    private void res(Event e){
        String t = listTab.getSelectionModel().getSelectedItem().getTitre();//on prend le titre selectionné dans la table
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        try {        
            resume.setText(f.getRes(LoginController.InUse.getId(), t));
        } catch (SQLException ex) {
            Logger.getLogger(Consultation_FichiersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void afficherTXT() throws IOException, SQLException{
        String path="C:\\Users\\lenovo\\Documents\\2LNIG2 khalil trigui\\2EME SEMESTRE BIS\\prog JAVA avencée\\Projet Java\\Projet_JavaRécuperation\\src\\FichierTXT\\info.txt";
        File f= new File(path);
        try (FileWriter fw = new FileWriter(f); 
            BufferedWriter bw = new BufferedWriter(fw)) {
            Xfichiers x= new Xfichiers(null);
            x.get_user_filesConsultation(InUse.getId()).forEach(i->{
                try {
                    bw.write("URL du Fichier: "+i.getURL()+"\nTitre: "+i.getTitre()+"\nAuteur: "+i.getAuteur()+"\nRésumé: "+i.getResume()+
                            "\nCommentaire: "+i.getCommentaire()+"\nTags du fichier: |"+getTags(i.getNumFichier(),InUse.getId())
                            +"\n-------------------------------------------------------------------------------------------------------------------------\n");
                } catch (IOException | SQLException ex) {
                    Logger.getLogger(Consultation_FichiersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Desktop.getDesktop().open(f);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fichier.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("URL"));
        titre.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("titre"));
        auteur.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("auteur"));
        tags.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("tag"));
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        String s=LoginController.InUse.getId();
        
        try {
            listTab.setItems(f.get_user_filesConsultation(s));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
        try {
            tabTag.setItems(f.get_tags_Consultation(s));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
    }
    
    //fonction pour avoir les tags d'un fichier
    private String getTags(int numF, String id) throws SQLException{
        String t="";
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT tag from tags  where Id='"+id+"' and numFichier="+numF);
            while(rs.next())
            {   
                t=t+rs.getString("tag")+" |";
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
