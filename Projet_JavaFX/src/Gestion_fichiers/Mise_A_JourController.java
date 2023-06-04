/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.HomeController.stageMAJ;
import static Gestion_fichiers.LoginController.InUse;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class Mise_A_JourController implements Initializable {

    @FXML
    private TableView<Xfichiers> tableModif;

    @FXML
    private TableColumn<Xfichiers, String> tag;

    @FXML
    private TableColumn<Xfichiers, String> titre;

    @FXML
    private TableColumn<Xfichiers, String> auteur;

    @FXML
    private TableColumn<Xfichiers, String> res;

    @FXML
    private TableColumn<Xfichiers, String> com;

    @FXML
    private TextArea url;
    
    private String oldRes;
    private String newRes;
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
    public void changerTag(CellEditEvent e){
    Xfichiers t=tableModif.getSelectionModel().getSelectedItem();
    oldRes=(String) e.getOldValue();
    if ("".equals((String) e.getNewValue())){
        JOptionPane.showMessageDialog(null, "Le fichier doit avoir le Tag.");
        t.setTag(oldRes);
        oldRes=null;
    }
    else
    {
        t.setTag((String) e.getNewValue());
        oldRes=null;
    }
    }
    
    @FXML
    public void changerTitre(CellEditEvent e){
    Xfichiers t=tableModif.getSelectionModel().getSelectedItem();
    oldRes=(String) e.getOldValue();
    if ("".equals((String) e.getNewValue())){
        JOptionPane.showMessageDialog(null, "Le fichier doit avoir un titre.");
    }else{
    t.setTitre((String) e.getNewValue());
    newRes=(String) e.getNewValue();
    //cette methode nous permet de modifier l'affichage des titres
    ObservableList<Xfichiers> fch=FXCollections.observableArrayList();
        tableModif.getItems().forEach( (Xfichiers f) -> {
        if (oldRes == null ? f.getTitre() == null : oldRes.equals(f.getTitre()))
        {
            f.setTitre(newRes);
            System.out.println(f.getTitre());
        }
        fch.add(f);
    });
        oldRes=null;
        newRes=null;
        tableModif.getItems().clear();
        tableModif.setItems(fch);
    }
    }
    
    
    @FXML
    public void changerAuteur(CellEditEvent e){
    Xfichiers t=tableModif.getSelectionModel().getSelectedItem();
    oldRes=(String) e.getOldValue();
    
    t.setAuteur((String) e.getNewValue());
    newRes=(String) e.getNewValue();
    //cette methode nous permet de modifier l'affichage des 
    ObservableList<Xfichiers> fch=FXCollections.observableArrayList();
        tableModif.getItems().forEach( (Xfichiers f) -> {
        if (oldRes == null ? f.getAuteur() == null : oldRes.equals(f.getAuteur()))
        {
            f.setAuteur(newRes);
            System.out.println(f.getAuteur());
        }
        fch.add(f);
    });
        oldRes=null;
        newRes=null;
        tableModif.getItems().clear();
        tableModif.setItems(fch);
    }
    
    @FXML
    public void changerRes(CellEditEvent e){
    Xfichiers t=tableModif.getSelectionModel().getSelectedItem();
    oldRes=(String) e.getOldValue();
    
    t.setResume((String) e.getNewValue());
    newRes=(String) e.getNewValue();
    //cette methode nous permet de modifier l'affichage des 
    ObservableList<Xfichiers> fch=FXCollections.observableArrayList();
        tableModif.getItems().forEach( (Xfichiers f) -> {
        if (oldRes == null ? f.getResume() == null : oldRes.equals(f.getResume()))
        {
            f.setResume(newRes);
            System.out.println(f.getResume());
        }
        fch.add(f);
    });
        oldRes=null;
        newRes=null;
        tableModif.getItems().clear();
        tableModif.setItems(fch);
    
    }
    
    @FXML
    public void changerComm(CellEditEvent e){
    Xfichiers t=tableModif.getSelectionModel().getSelectedItem();
    oldRes=(String) e.getOldValue();
    
    t.setCommentaire((String) e.getNewValue());
    newRes=(String) e.getNewValue();
    //cette methode nous permet de modifier l'affichage des 
    ObservableList<Xfichiers> fch=FXCollections.observableArrayList();
        tableModif.getItems().forEach( (Xfichiers f) -> {
        if (oldRes == null ? f.getCommentaire() == null : oldRes.equals(f.getCommentaire()))
        {
            f.setCommentaire(newRes);
            System.out.println(f.getResume());
        }
        fch.add(f);
    });
        oldRes=null;
        newRes=null;
        tableModif.getItems().clear();
        tableModif.setItems(fch);
    }
    
    @FXML
    void enregistrer(ActionEvent event) {//enregistrer les modifications des fichiers 
        ObservableList <Xfichiers> eng=tableModif.getItems();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
        a.setTitle("Modifier.");
        a.setHeaderText("Si vous continuez, vos fichiers et tags seront modifiés!");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            eng.forEach(f->{
                try {
                    f.ModifierFichiers(f);
                } catch (SQLException ex) {
                    System.err.println("Error" + ex);
                }
            });
            stageMAJ.close();
        }
    }
    int i=0;
    private boolean b= false;
    @FXML
    void supprimer(ActionEvent event) throws SQLException {//supprimer un tag d'un fichier jusqu'a la suppression du fichier
        Xfichiers f= tableModif.getSelectionModel().getSelectedItem();
        
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
        a.setTitle("Supprimer.");
        a.setHeaderText("Si vous continuez, ce tag/Fichier sera supprimé!");
        a.setContentText("Ce fichier perdera un tag s'il y a plusieur copies. \nVous allez perdre votre fichier s'il n'y a plus de copies.\nCette action est permanante");
        b=false;
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            tableModif.getItems().remove(f);
            tableModif.getItems().forEach(l1->{
                if (f.getNumFichier()==l1.getNumFichier())
                    b=true;
            });
            if(b){
                f.SupprimerTagFichiers(f);
            }else
            {
                f.SupprimerTagFichiers(f);
                f.SupprimerFichier(f);
            }
        }
    }
    
    @FXML
    void AffichageURL(ActionEvent e){//affichage du path du fichier
        url.setText(tableModif.getSelectionModel().getSelectedItem().getURL());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        res.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("resume"));
        titre.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("titre"));
        auteur.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("auteur"));
        tag.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("tag"));
        com.setCellValueFactory(new PropertyValueFactory<Xfichiers, String>("commentaire"));
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        try {
            tableModif.setItems(f.get_user_files(InUse.getId()));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        tableModif.setEditable(true);
        tag.setCellFactory(TextFieldTableCell.forTableColumn());
        res.setCellFactory(TextFieldTableCell.forTableColumn());
        titre.setCellFactory(TextFieldTableCell.forTableColumn());
        auteur.setCellFactory(TextFieldTableCell.forTableColumn());
        com.setCellFactory(TextFieldTableCell.forTableColumn());
        url.setEditable(false);
    }  
    
}
