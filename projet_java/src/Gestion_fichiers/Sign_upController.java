/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.LoginController.signUpStage;
import static Gestion_fichiers.Main.loginStage;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author lenovo
 */
public class Sign_upController implements Initializable{
    @FXML
    private JFXComboBox<String> typeCompte;

    @FXML
    private JFXTextField MDP;
    @FXML
    private JFXTextField Adress;
    
    @FXML
    private JFXSlider nbrChar;
    
    @FXML
    private JFXTextField user;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXPasswordField confirmation;
    
    @FXML
    private Label labeluser;

    @FXML
    private Label labelprenom;

    @FXML
    private Label labelnom;

    @FXML
    private Label labelmdp;

    @FXML
    private Label labelconf;
    
    
    @FXML
    void Generer(Event e){
        MDP.setText(Generateur((int) nbrChar.getValue()));
        
    }
    
    @FXML
    void confirmer(Event e) throws SQLException{//fonction pour le bouton confirmation.
     /**
     * un controleur des champs 
     */
        if (("".equals(user.getText()))||("".equals(prenom.getText()))||("".equals(nom.getText()))||("".equals(confirmation.getText()))||("".equals(MDP.getText()))||("".equals(typeCompte.getValue()))){
            if("".equals(user.getText())){
                    labeluser.setText("Vous devez mettre votre nom d'utilisateur!");
            }else if ("".equals(prenom.getText())){
                labelprenom.setText("Vous devez mettre votre Prénom!");
            }else if ("".equals(nom.getText())){
                labelnom.setText("Vous devez mettre votre nom!");
            }else if ("".equals(confirmation.getText())){
                labelconf.setText("Vous devez confirmer votre Mot de Passe!");
            }else if ("".equals(MDP.getText())){
                labelmdp.setText("Vous devez entrer un mot de passe!");
            }
        }else
        if (!confirmation.getText().equals(MDP.getText()))
                labelconf.setText("Vérifiez votre Mot de Passe!");
        else{
        
        Alert a = new Alert(AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
        a.setTitle("Confirmation formulaire");
        a.setHeaderText("Choix de vos reponses:\n Nom d'utilisateur: "
                + user.getText() +"\nNom: "+nom.getText()+"\nPrénom: "+prenom.getText()+"\nAdresse mail: "+Adress.getText()
                +"\n Mot de Passe: "+MDP.getText()+"\nType d'accés: "+typeCompte.getValue());
        a.setContentText("Vous etes sur?");

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            Users u1=new Users(user.getText(),nom.getText(),prenom.getText(),Adress.getText(),MDP.getText(),typeCompte.getValue());
            u1.insererBD_SignUp();
            retour();
        } 
        }
    }
    
    @FXML
    private void retour(){ //retour a la page de l'identification
        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            signUpStage.close();//ceci ferme la fenetre de login.
            loginStage=stage;//on ecrase la valeur loginStage initialisée dans le main par sa nouvelle valeur
    }
    
    
    
    
    
    @FXML
    private void free(Event e){//fonction pour liberer les champs 
    MDP.setText("");
    
    Adress.setText("");
    
    user.setText("");

    prenom.setText("");

    nom.setText("");

    confirmation.setText("");
    }
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCompte.getItems().addAll("Utilisateur", "Administrateur");
        typeCompte.getSelectionModel().select("Utilisateur");
    }
    
    
    
    private String Generateur (int length) {
    //créer des tableaux de caractères
    final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
    final char[] numbers = "0123456789".toCharArray();
    final char[] symbols = "^$?!@#%&".toCharArray();
    final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^$?!@#%&".toCharArray();

    //Utiliser une alea cryptographically generateur de nombres securisé
    Random random = new SecureRandom();

    StringBuilder password = new StringBuilder(); //créer un builder de chaine de caractères

    for (int i = 0; i < length-4; i++) {
        password.append(allAllowed[random.nextInt(allAllowed.length)]);
    }

    //etre sur de mettre des caractères, des entiers et des symboles
    password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
    password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
    password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
    password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);
    return password.toString();
    }
    
}
