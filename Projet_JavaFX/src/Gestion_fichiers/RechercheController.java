/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.LoginController.InUse;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class RechercheController implements Initializable {

    @FXML
    ComboBox <String> comboBox ;
    
    @FXML
    private Label err;
    
    @FXML
    private TextField rech;
    
    @FXML
    private TableView<Xfichiers> rechercheTab;

    @FXML
    private TableColumn<Xfichiers, String> titre;

    @FXML
    private TableColumn<Xfichiers, String> nomF;

    @FXML
    private TableColumn<Xfichiers, String> emplac;
    
    ObservableList <Xfichiers> l;
    
    @FXML
    void recherche(ActionEvent e) throws SQLException{
        Xfichiers x= new Xfichiers(null);
        if (null == comboBox.getSelectionModel().getSelectedItem()){
            rechercheTab.setItems(x.get_fichiersRechTag(InUse.getId(), rech.getText()));
        }else
        switch (comboBox.getSelectionModel().getSelectedItem()) {
            case "Auteur":
                rechercheTab.setItems(x.get_fichiersRechAuteur(InUse.getId(), rech.getText()));
                break;
            case "Titre":
                rechercheTab.setItems(x.get_fichiersRechTitre(InUse.getId(), rech.getText()));
                break;
            case "Tag":
                rechercheTab.setItems(x.get_fichiersRechTag(InUse.getId(), rech.getText()));
                break;
        }
        
}
    
    @FXML// cette fonction permet a l'utilisateur d'ouvrir son fichier.
    void ouvrirFichier() throws IOException{
        Desktop.getDesktop().open(new File(rechercheTab.getSelectionModel().getSelectedItem().getURL()));
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emplac.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("URL"));
        titre.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("titre"));
        nomF.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("fichier"));
        comboBox.getItems().addAll("Auteur", "Titre", "Tag");
        comboBox.getSelectionModel().select("Tag");
        
        
    }
    
}
