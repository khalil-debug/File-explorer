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
import java.util.regex.Pattern;
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
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

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
    private JFXTextField rep;
    
    @FXML
    private Label labelrep;
    
    @FXML
    private JFXComboBox<String> quest;

    @FXML
    private Label labelmdp;

    @FXML
    private Label labelconf;
    
    
    @FXML
    void Generer(Event e){
        MDP.setText(Generateur((int) nbrChar.getValue()));
        
    }
    
    @FXML
    void confirmer(Event e) throws SQLException, IOException{//fonction pour le bouton confirmation.
     /**
     * un controleur des champs 
     */
        if (("".equals(user.getText()))||("".equals(prenom.getText()))||("".equals(nom.getText()))||("".equals(confirmation.getText()))||("".equals(MDP.getText()))||("".equals(typeCompte.getValue())||("".equals(rep.getText()))||(quest.getSelectionModel().isEmpty()))){
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
            else if (quest.getSelectionModel().isEmpty()){
                labelrep.setText("Vous devez choisir une question!");
            }
            else if ("".equals(rep.getText())){
                labelrep.setText("Vous devez entrer une Réponse a votre question!");
            }
        }else
        if (!confirmation.getText().equals(MDP.getText()))
                labelconf.setText("Vérifiez votre Mot de Passe!");
        else{
            if ((Valide(Adress.getText()))||("".equals(Adress.getText()))){
                
        Alert a = new Alert(AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
        a.setTitle("Confirmation formulaire.");
        a.setHeaderText("Choix de vos reponses:\n Nom d'utilisateur: "
                + user.getText() +"\nNom: "+nom.getText()+"\nPrénom: "+prenom.getText()+"\nAdresse mail: "+Adress.getText()
                +"\n Mot de Passe: "+MDP.getText()+"\nType d'accés: "+typeCompte.getValue()+"\nQuestion à repondre: "+quest.getSelectionModel().getSelectedItem()
        +"\nRéponse: "+rep.getText());
        a.setContentText("Vous etes sur?");

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            Users u1=new Users(user.getText(),nom.getText(),prenom.getText(),Adress.getText(),MDP.getText(),typeCompte.getValue(),quest.getSelectionModel().getSelectedItem(),rep.getText());
            u1.insererBD_SignUp();
            retour();
        }
        }else 
                JOptionPane.showMessageDialog(null, "Vérifiez votre E-mail!"
                        + "\nCeci doit être de style: 'exemple@exemple.com'.");
        }
    }
    
    Stage primaryStage = null;
        
    @FXML
    private void retour() throws IOException{ //retour a la page de l'identification
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            signUpStage.close();
        
        loginStage=stage;
    }
    
    
    
    
    
    @FXML
    private void free(Event e){//fonction pour liberer les champs 
    MDP.setText("");
    
    Adress.setText("");
    
    user.setText("");

    prenom.setText("");

    nom.setText("");

    confirmation.setText("");
    
    rep.setText("");
    }
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCompte.getItems().addAll("Utilisateur", "Administrateur");
        typeCompte.getSelectionModel().select("Utilisateur");
        quest.getItems().addAll("Quel est le nom de votre chien?","Quel est votre école?","Etat civile?");
    }
    
    
    
    private String Generateur (int l ) {
    //créer des tableaux de caractères
    final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
    final char[] numbers = "0123456789".toCharArray();
    final char[] symbols = "^$?!@#%&".toCharArray();
    final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^$?!@#%&".toCharArray();

    //Utiliser une alea cryptés comme generateur de nombres securisé
    Random random = new SecureRandom();

    StringBuilder password = new StringBuilder(); //créer un builder de chaine de caractères pour le mot de passe
    //l est la langueur voulue de la chaine de caractère
    for (int i = 0; i < l-4; i++) {
        password.append(allAllowed[random.nextInt(allAllowed.length)]);
    }

    //être sur de mettre des caractères, des entiers et des symboles
    password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
    password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
    password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
    password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);
    return password.toString();
    }
    
    private boolean Valide(String email)
    {
        //l'expression Régulière
        String emailStructure = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        //compiler l'expression pour avoir le pattern                       
        Pattern p = Pattern.compile(emailStructure);
        //créer l'instance de matcher qui permet de voir si ça matche l'expression ou non et retourner une valeur bouléenne
        return p.matcher(email).matches();
    }
}