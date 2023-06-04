/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import static Gestion_fichiers.HomeController.stageMarq;
import static Gestion_fichiers.LoginController.InUse;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class MarquageController implements Initializable {

    List<String> lstFile;//une liste de strings pour definir et specifier les types des fichiers a trouver
    
    String T[];
    int i=0;
    
    @FXML
    private Label LabelFile, Oblig1, Oblig2;

    @FXML//configure the table
    private TableView<Xfichiers> tagTable;
    
    @FXML
    private TableColumn<Xfichiers, String> tag;
    
    @FXML
    private TextField txtTag;
    
    @FXML
    void FileChooser(ActionEvent event) { //cette fonction est associée avec le bouton file chooser, elle permet a l'utilisateur d'importer un fichier de sa machine.
        FileChooser fc= new FileChooser(); //on crée un file chooser de type objet
        fc.getExtensionFilters().add(new ExtensionFilter("Text Files",lstFile));//ceci permet a l'application de faire un tri des fichiers a montrer.
        File f= fc.showOpenDialog(null);//cette fonction affiche la fenetre de l'explorateur ou on choisi notre fichier.
        if (f!= null)//si aucun fichier n'est choisi, le label restera le meme.
        {
            LabelFile.setText(f.getAbsolutePath()); //afficher le lien du fichier dans le pc
        }
    }

    
    //cette methode permettra a l'utilisateur de double click sur un tag pour le modifier
    // dans la table
    
    @FXML
    public void changerTag(CellEditEvent e){
    Xfichiers t=tagTable.getSelectionModel().getSelectedItem();
    t.setTag((String) e.getNewValue());
}
    
    @FXML
    void ajoutTag(ActionEvent e){//cette fontion permettra d ajouter les tags au tableau après que l'utilisateur cliquera sur ajouter tag
        if (!"".equals(txtTag.getText())){
        Xfichiers t= new Xfichiers(txtTag.getText());
        //on retourne une liste du contenu du tableau pour ajouter l'element au tableau 
        tagTable.getItems().add(t);
        }
        else
        {
            Oblig2.setVisible(true);
        }
        txtTag.clear(); //on vide le champ texte pour l'utilisateur
    }
    
    @FXML
    void supprimerTag(ActionEvent e){//cette fontion permettera de supprimer des tags que l'utilisateur n'en veut pas
        ObservableList<Xfichiers> SR, tags; //une pour les items selectionnés (SR) et l'autre pour tous les tags
        tags= tagTable.getItems();//on met tous les tags du tableau dans la liste tags
        SR= tagTable.getSelectionModel().getSelectedItems();//ceci nous donnera les items selectionnés.
        //faire une boucle for sur les tags selectionnés et les supprimer.
        SR.forEach(t -> {
            tags.remove(t);
        });
    }
    
    @FXML
    private TextArea res;

    @FXML
    private TextArea com;
    
    @FXML
    private TextField auteur;

    @FXML
    private TextField titre;
    
    
    
    @FXML
    void marquerFichier(Event e) throws SQLException{//fonction pour marquer un fichier en ajoutant un fichier et ses tags correspondants
        if("".equals(titre.getText())){
            Oblig1.setVisible(true);
        }else if ("Pas de fichier choisi.".equals(LabelFile.getText()))
            JOptionPane.showMessageDialog(null,"Pas de fichier choisi!");
        else if(tagTable.getItems().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Veuillez entrer au moin un tag!");
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);//creation d'un dialogue de confirmation
            a.setTitle("Confirmation formulaire");
            a.setHeaderText("Choix de vos reponses:\n Emplacement du Fichier: "
                + LabelFile.getText() +"\nTitre: "+titre.getText()+"\nAuteur: "+auteur.getText()+"\nRésumé: "+res.getText()
                +"\n Commentaire: "+com.getText() +"\nTags: "+getTags() );
            a.setContentText("Vous êtes sur?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK){
                String user= InUse.getId();
                Xfichiers f= new Xfichiers(LabelFile.getText(),auteur.getText(),titre.getText(),com.getText(),res.getText(),0);
                f.setNumFichier(f.add_file(f));
                tagTable.getItems().forEach((Xfichiers t) -> {
                    f.setTag(t.getTag());
                    try {
                        f.add_tags(f, user);
                    } catch (SQLException ex) {
                        Logger.getLogger(MarquageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stageMarq.close();
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { //initialise la liste des types de fichiers
        lstFile= new ArrayList<>();
        lstFile.add("*.doc");//fichiers doc
        lstFile.add("*.docx"); //fichiers docx
        lstFile.add("*.DOC");
        lstFile.add("*.DOCX");
        lstFile.add("*.pdf");//fichiers pdf
        lstFile.add("*.PDF");
        lstFile.add("*.pptx");
        lstFile.add("*.PPTX");
        //setting up the value of the column tag
        tag.setCellValueFactory(new PropertyValueFactory<Xfichiers,String>("tag"));
        
        /**
         * permettra a la table d'etre editable.
         */
        tagTable.setEditable(true);
        tag.setCellFactory(TextFieldTableCell.forTableColumn());
        /**
         * ceci permettra a l'utilisateur de selectionner des tags multiples pour les supprimer.
         */
        tagTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }
    
    String s="";
    
    String getTags(){
        
        tagTable.getItems().forEach(f->{
            
               s=s+f.getTag()+"|";
        });
        return s;
    }
}
