/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author lenovo
 */
public class Consultation_FichiersController implements Initializable{

    @FXML private TableView<Xfichiers> listTab; 
    @FXML private TableColumn<Xfichiers, String> fichier;
    @FXML private TableColumn<Xfichiers, String> auteur; 
    @FXML private TableColumn<Xfichiers, String> titre; 
    @FXML private TableView<Xfichiers> tabTag; 
    @FXML private TableColumn<Xfichiers, String> tags; 
 
    @FXML private TextArea commentaire; 
    @FXML private TextArea resume;
    
    @FXML
    private void selection(CellEditEvent e){
        String t = listTab.getSelectionModel().getSelectedItem().getTitre();//on prend le titre selectionné dans la table
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        try {
            commentaire.setText(f.getCommentaire(LoginController.InUse.getId(), t));
        } catch (SQLException ex) {
            Logger.getLogger(Consultation_FichiersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            resume.setText(f.getCommentaire(LoginController.InUse.getId(), t));
        } catch (SQLException ex) {
            Logger.getLogger(Consultation_FichiersController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            System.err.println(ex.getMessage());
        }
        
        
        try {
            tabTag.setItems(f.get_tags_Consultation(s));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        
    }
    
}
