/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/

package Gestion_fichiers;
/**
 *
 * @author lenovo
 */


import static Gestion_fichiers.LoginController.HomeStage;
import static Gestion_fichiers.Main.loginStage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;





public class HomeController implements Initializable{

    private String T;
    public static Stage stageMAJ;
    private Stage stageConsul,stageMarq,stageRech,stageInfo;
    @FXML
    private Button INFO;
    @FXML
    private void aller_Consultations(Event e){
        if (stageConsul==null){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Consultation_Fichiers.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stageConsul=stage; 
        }else{
            if (!stageConsul.isShowing())
            {
            stageConsul=null;
            }
        }
        
    }
    @FXML
    private void aller_marquage(Event e){
        if (stageMarq==null){
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("Marquage_fichiers.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stageMarq=stage;
            }else{
            if (!stageMarq.isShowing())
            {
            stageMarq=null;
            }
        }
    }
    @FXML
    private void aller_recherche(Event e){
        if (stageRech==null){
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("Recherche.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stageRech=stage;
            }else{
            if (!stageRech.isShowing())
            {
            stageRech=null;
            }
        }
}
    @FXML
    private void aller_MAJ(Event e){
        if (stageMAJ==null){
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("Mise_A_Jour_Fichiers.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stageMAJ = stage;
            }else{
            if (!stageMAJ.isShowing())
            {
            stageMAJ=null;
            }
        }
}
    
    @FXML
    private void aller_InfosG(Event e){
        if (stageInfo==null){
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("Informations_generales.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stageInfo=stage;
        }else{
            if (!stageInfo.isShowing())
            {
            stageInfo=null;
            }
        }
}
    
    @FXML
    void info_app(){
        Alert a = new Alert(Alert.AlertType.INFORMATION);//creation d'un dialogue de confirmation
        a.setTitle("Informations sur l'application!");
        a.setHeaderText("Bienvenu dans votre application de gestion de fichiers personelle! :)");
        a.setContentText("Cette application a été faite par deux étudiants de la 2ème année en licence de BIS Ameni et Khalil! \n"
                + "On éspère que vous aimeriez notre travail! :) \n"
                + "Bonne exploration!");
        a.showAndWait();
    }
    
    @FXML
    private void Logout(Event e){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            LoginController.InUse.setId(null);
            LoginController.InUse.setMDP(null);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            HomeStage.close();
            loginStage=stage;
    }

    @FXML
    private Label labelWelcome;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWelcome.setText("Bienvenu dans vos fichiers favoris "+LoginController.InUse.getId()+".");
    }
    
    public void determiner(String c){
        T=c;
        if ("Administrateur".equals(T))
        {
            INFO.setDisable(false);
        }else
            INFO.setDisable(true);
    }
    
    
}
