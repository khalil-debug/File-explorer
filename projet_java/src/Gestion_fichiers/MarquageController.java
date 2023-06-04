/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

/**
 *
 * @author lenovo
 */
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MarquageController implements Initializable {

    List<String> lstFile;//une liste de strings pour definir et specifier les types des fichiers a trouver
    
    @FXML
    private Button btnFileChooser;
    

    @FXML
    private Label LabelFile, Oblig1, Oblig2;

    @FXML//configure the table
    private TableView<Tags> tagTable;
    
    @FXML
    private TableColumn<Tags, String> tag;
    
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
    Tags t=tagTable.getSelectionModel().getSelectedItem();
    t.setTag((String) e.getNewValue());
}
    
    @FXML
    void ajoutTag(ActionEvent e){//cette fontion permettra d ajouter les tags au tableau après que l'utilisateur cliquera sur ajouter tag
        if (!"".equals(txtTag.getText())){
        Tags t= new Tags(txtTag.getText());
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
        ObservableList<Tags> SR, tags; //une pour les items selectionnés (SR) et l'autre pour tous les tags
        tags= tagTable.getItems();//on met tous les tags du tableau dans la liste tags
        SR= tagTable.getSelectionModel().getSelectedItems();//ceci nous donnera les items selectionnés.
        
        //faire une boucle for sur les tags selectionnés et les supprimer.
        for (Tags t: SR){
            tags.remove(t);
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
        //setting up the value of the column tag
        tag.setCellValueFactory(new PropertyValueFactory<Tags,String>("Tag"));
        tagTable.setItems(getTags());
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
    
    public ObservableList<Tags> getTags(){
        ObservableList<Tags> tagdata = FXCollections.observableArrayList();
        tagdata.add(new Tags("khalil"));
        tagdata.add(new Tags("basma"));
        tagdata.add(new Tags("omar"));
        tagdata.add(new Tags("zied"));
        return tagdata;
    }
    
}
