/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.HomeController.stageMAJ;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author lenovo
 */
public class Mise_A_JourController {
    @FXML
    void annuler(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
        a.setTitle("Annuler.");
        a.setHeaderText("");
        a.setContentText("Si vous continuez, vos modifications seront toutes supprimées!");

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            stageMAJ.close();
        }
    }
    @FXML
    void enregistrer(ActionEvent event) {//enregistrer les modifications des fichiers 
        
    }

    @FXML
    void supprimer(ActionEvent event) {
        
    }
}
