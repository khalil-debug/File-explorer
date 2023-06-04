/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import DataBase.Connexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lenovo
 */
public class Main extends Application {

    public static Stage loginStage;
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));//on crée un loader pour acceder au fichier FXML dans le package .Home
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("File Manager.");
        primaryStage.setResizable(false);
        primaryStage.show();
        
        loginStage=primaryStage;//enregistrer le Stage pour la fermer après.
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
    
    
}
