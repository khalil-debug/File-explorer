/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.LoginController.mdpStage;
import static Gestion_fichiers.LoginController.signUpStage;
import static Gestion_fichiers.Main.loginStage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
public class MDP_OubliéController implements Initializable {

    @FXML
    private JFXTextField rep;

    @FXML
    private Label quest;

    @FXML
    private JFXTextField id;

    @FXML
    private Label label;
    
    @FXML
    private JFXButton btnM;
    
    String idUser;
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    private boolean verifier_util(String id) throws SQLException{
        boolean b = false;
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="select * from users where Id=?";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            
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
    
    private boolean verifier_rep(String reponse) throws SQLException{
        boolean b = false;
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="select * from users where reponse=?";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, reponse);
            
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
    
    private String Question(String id) throws SQLException{
        String s=null;
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="select * from users where Id=?";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            
            rs= ps.executeQuery();
            while (rs.next())
                s=rs.getString("question");
            
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
        return s;
    }
    
    @FXML
    void entrer_id() throws SQLException{
        if (verifier_util(id.getText())){
            idUser=id.getText();
            label.setVisible(true);
            quest.setVisible(true);
            rep.setVisible(true);
            btnM.setDisable(false);
            quest.setText(Question(id.getText()));
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Verifiez votre nom d'utilisateur!");
        }
    }
    
    
    
    @FXML
    void changerMDP() throws SQLException, IOException{
        if (verifier_rep(rep.getText())){
            String nouveau_MDP=JOptionPane.showInputDialog("Quel sera votre nouveau mot de passe?");
            if ("".equals(nouveau_MDP))
                JOptionPane.showMessageDialog(null, "Votre mot de passe ne peut pas être nul!");
            else{
            modifierMDP(idUser,nouveau_MDP);
            JOptionPane.showMessageDialog(null, "Votre mot de passe a été modifié!");
            Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        } catch (IOException ex) {
            System.out.print(ex);
        }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            mdpStage.close();
            loginStage=stage;
        }
        }
        else {
            JOptionPane.showMessageDialog(null, "Verifiez vos données!");
        }
    }
    
    void modifierMDP(String id, String mdp) throws SQLException{
        try{
            //creation de la base de donnés
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            //creation d'une chaine de caractères 
            String sql="update users set MDP=? where Id=?";
            //preparer les quaries
            ps=con.prepareStatement(sql);
            ps.setString(1, mdp);
            ps.setString(2, id);
            
            ps.executeUpdate();
            
            
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
    }
    
    
    
    @FXML
    void retour() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            mdpStage.close();
        
        loginStage=stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
