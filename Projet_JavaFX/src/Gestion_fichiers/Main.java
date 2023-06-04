/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author lenovo
 */
public class Main extends Application {
    
    private double xOffset = 0;
    private double yOffset = 0;
    public static Stage loginStage;
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));//on crée un loader pour acceder au fichier FXML dans le package .Home
        BorderPane root = new BorderPane(loader.load());

        //ceci permet la scene d'être draggable 
        root.setOnMousePressed((MouseEvent event) -> {//setOnMousePressed prend la valeur de la position de la fenêtre 
            xOffset = event.getSceneX();//la position de X
            yOffset = event.getSceneY();//la position de Y
        });
        root.setOnMouseDragged((MouseEvent event) -> {//setOnMouseDragged permet de modifier la nouvelle valeur des coordonnées de la nouvelle position
            primaryStage.setX(event.getScreenX() - xOffset);//on modifie X pour avoir la nouvelle valeur
            primaryStage.setY(event.getScreenY() - yOffset);//de même pour Y
        });
        
        //le set de la scene dans le stage
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);//ceci permet de cacher la barre blanche 
        
        
        primaryStage.show();
        
        loginStage=primaryStage;//enregistrer le Stage pour la fermer après.
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
   
    }
    
}
