/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 *
 * @author lenovo
 */
public class RechercheController implements Initializable{

    @FXML
    ComboBox <String> comboBox = new ComboBox<>();
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll("Auteur", "Titre", "Tag");
        comboBox.getSelectionModel().select("Tag");
    }
    
}
