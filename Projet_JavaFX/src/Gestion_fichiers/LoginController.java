/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.Main.loginStage;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class LoginController implements Initializable {

    public static Stage signUpStage;
    public static Stage HomeStage;
    public static Stage mdpStage;
    private int oubli=3;
    
    
    @FXML
    private JFXPasswordField mdp;

    @FXML
    private JFXTextField nom;
    
    @FXML
    private Label label;
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    /**
     * creation d'une variable inUse pour enregistrer l'utilisateur de l'application en cours.
     */
    public static Users InUse;
    
    @FXML
    private void login(ActionEvent e) throws IOException, SQLException{
        if ("".equals(nom.getText())){
            label.setText("Entrez votre nom d'utilisateur!");
        }
        
        else if ("".equals(mdp.getText()))
        {
            label.setText("Entrez votre Mot de Passe! Vous avez "+oubli+" chances restantes.");
            //determiner le nombre de fois l'utilisateur a essayé de se connecter.
            oubli--;
        }
        
        else if (!verifier_util(nom.getText(),mdp.getText()))
        {
            JOptionPane.showMessageDialog(null, "Verifiez vos données!");
        }
        
        else if (verifier_util(nom.getText(),mdp.getText()))
        {   
            LoginController.InUse =new Users(nom.getText(),mdp.getText());
            String s=verifierType(nom.getText(), mdp.getText());
            JOptionPane.showMessageDialog(null, "Bienvenu l'"+s+"!");
            
        
        FXMLLoader l=new FXMLLoader();
        l.setLocation(getClass().getResource("HomePage.fxml"));
        Parent root= l.load();
        //initialiser une variable de classe home controller
        HomeController hc = l.getController();
        //determiner le type d'utilisateur
        hc.determiner(s);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        HomeStage=stage;
        loginStage.close();
        
        }
               
        
    }
    //une nouvelle methode est implémentée pour determiner le type d'utilisateur identifié
    public String verifierType(String id, String m) throws SQLException{
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet res;
        String b = "";
        try{
            //creation de la base de donnés
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="select type from users where Id=? and MDP=?";
            //preparer les quaries
            pst=conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, m);
            res= pst.executeQuery();
            if(res.next()){
                b = res.getString("type");
            }
            
        }catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        finally{//on ferme notre Base de donnés pour sa sécurité
            if (pst !=null){
                pst.close();
            }
            if (conn!=null)
                conn.close();
        }
        return b;
    }
    
    
    private boolean verifier_util(String id, String m) throws SQLException{
        boolean b = false;
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="select * from users where Id=? and MDP=?";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, m);
            rs= ps.executeQuery();
            b = rs.next();
            
        }catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        finally{//on ferme notre Base de donnés pour sa sécurité
            if (ps !=null){
                ps.close();
            }
            if (con!=null)
                con.close();
        }
        return b;
    }
    
    
    
    
    
    
    @FXML
    private void Sign_up(Event e){
        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("Sign_up.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            signUpStage=stage;
            loginStage.close();//ceci ferme la fenetre de login.
    }
    
    @FXML
    private void MDPOubli(Event e){
        Parent root = null; 
        try {
            root = FXMLLoader.load(getClass().getResource("MDP_Oublié.fxml"));
        } catch (IOException ex) {
            System.out.print(ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            mdpStage=stage;
            loginStage.close();//ceci ferme la fenetre de login.
    }
    
    @FXML
    private void Quitter(){
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
       
    
}
