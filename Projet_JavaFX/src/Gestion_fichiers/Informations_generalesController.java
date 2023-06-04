/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_fichiers;

import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class Informations_generalesController implements Initializable {

    @FXML
    private TableColumn<Users, String> type;

    @FXML
    private TableView<Users> tableUser;

    @FXML
    private TableColumn<Users, String> user;

    @FXML
    private TableColumn<Users, String> nom;

    @FXML
    private TableColumn<Users, String> prenom;

    @FXML
    private TableColumn<Users, String> adresse;

    @FXML
    private TableColumn<Users, String> MDP;

    @FXML
    private TableColumn<Users, Integer> nbrFichier;
    
    @FXML
    private JFXTextField Utilisateur_centreInteret;
    @FXML private Label label_utilCI;
    @FXML
    private TableView<Xfichiers> tableCI;

    @FXML
    private TableColumn<Xfichiers, String> fichiersCI;

    @FXML
    private TableColumn<Xfichiers, String> tagsCI;
    
    @FXML
    private PieChart tagsPop;
        
    
    @FXML
    private TableView<Tags> taginfoTable;

    @FXML
    private TableColumn<Tags, String> tag_taginfo;

    @FXML
    private TableColumn<Tags, Integer> NU_tag_info;
    
    @FXML
    private PieChart tagChart;
    
    @FXML
    private void rechercheCIU(ActionEvent e) throws SQLException{
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        if (!"".equals(Utilisateur_centreInteret.getText()))
        {
            label_utilCI.setText("");
            tableCI.setItems(f.afficher_CIU(Utilisateur_centreInteret.getText()));
        }
        else if ("".equals(Utilisateur_centreInteret.getText()))
            label_utilCI.setText("Veuillez entrer un nom d'utilisateur!");
        tagsPop.setData(afficher_CIU_chart(Utilisateur_centreInteret.getText()));
    }
    
    
    //fonction pour les données des tags
    private void rechercheInfoTag() throws SQLException{
        Tags t= new Tags(null, 0);
        try {
            taginfoTable.setItems(t.afficherTagInfo());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        tagChart.setData(afficher_Tag_chart());
    }
    
    
    
    @FXML
    private TableView<Tags> lastTab;

    @FXML
    private TableColumn<Tags, String> lastColumn;
    
    @FXML
    private JFXTextField rechTag;
    
    @FXML
    private Label lastLabel;
    
    @FXML
    private void rechercheUtilParTag() throws SQLException{
        Tags t= new Tags(null, 0);
        if (!"".equals(rechTag.getText()))
        {
            lastLabel.setText("");
            lastTab.setItems(t.afficherRechercheTag(rechTag.getText()));
        }else if ("".equals(rechTag.getText()))
        {
            lastLabel.setText("Veuillez mettre un nom de tag!");
        }
    }
    
    
    @FXML
    private void affichageTXTUtil() throws IOException, SQLException, InterruptedException{
        String path="C:\\Users\\lenovo\\Documents\\2LNIG2 khalil trigui\\2EME SEMESTRE BIS\\prog JAVA avencée\\Projet Java\\Projet_JavaRécuperation\\src\\FichierTXT\\info.txt";
        File f= new File(path);
        try (FileWriter fw = new FileWriter(f); 
            BufferedWriter bw = new BufferedWriter(fw)) {
            Users u = new Users(null,null,null,null,null,null,null,null);
            u.afficherUsersBD_InfoG().forEach(i->{
                try {
                    bw.write("Utilisateur: "+i.getId()+"\nNom: "+i.getNom()+"\nPrénom: "+i.getPrenom()+"\nAdresse Mail: "+i.getAdresse_mail()+"\n"
                            + "Mot De Passe: "+i.getMDP()+"\nType d'Accés: "+i.getType()+"\nNombre de fichiers Favoris: "+i.getNbrFichiers()
                    +"\n-------------------------------------------------------------------------------------------------------------------------\n");
                } catch (IOException ex) {
                    System.err.print(ex);
                }
            });
        }
        Desktop.getDesktop().open(f);
    }
    
    @FXML
    private void affichageTXT_Tag() throws IOException, SQLException{
        String path="C:\\Users\\lenovo\\Documents\\2LNIG2 khalil trigui\\2EME SEMESTRE BIS\\prog JAVA avencée\\Projet Java\\Projet_JavaRécuperation\\src\\FichierTXT\\info.txt";
        File f= new File(path);
        try (FileWriter fw = new FileWriter(f); 
            BufferedWriter bw = new BufferedWriter(fw)) {
            Tags t= new Tags(null, 0);
            t.afficherTagInfo().forEach(i->{
                try {
                    bw.write("Nom Du Tag: "+i.getTagUtil()+"\nNombre d'utilisateurs: "+i.getNum()
                    +"\n-------------------------------------------------------------------------------------------------------------------------\n");
                } catch (IOException ex) {
                    System.err.print(ex);
                }
            });
        }
        Desktop.getDesktop().open(f);
    }
    
            
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //configurer les colonnes du tableau de vision des utilisateurs
        user.setCellValueFactory(new PropertyValueFactory<Users,String>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Users,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Users,String>("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory<Users,String>("Adresse_mail"));
        MDP.setCellValueFactory(new PropertyValueFactory<Users,String>("MDP"));
        type.setCellValueFactory(new PropertyValueFactory<Users,String>("type"));
        nbrFichier.setCellValueFactory(new PropertyValueFactory<Users,Integer>("nbrFichiers"));
        
        Users u = new Users(null,null,null,null,null,null,null,null);
        try {
            tableUser.setItems(u.afficherUsersBD_InfoG());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        //initialiser les colonnes du tableau de centre d'interet d'utilisateur
        fichiersCI.setCellValueFactory(new PropertyValueFactory<Xfichiers,String>("URL"));
        tagsCI.setCellValueFactory(new PropertyValueFactory<Xfichiers,String>("tag"));
        Xfichiers f = new Xfichiers(null,null,null,null,null,0);
        try {
            tableCI.setItems(f.afficher_CIU(Utilisateur_centreInteret.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(Informations_generalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //initialiser les colonnes du tableau de info tags
        tag_taginfo.setCellValueFactory(new PropertyValueFactory<Tags,String>("tagUtil"));
        NU_tag_info.setCellValueFactory(new PropertyValueFactory<Tags,Integer>("num"));
        
        try {
            rechercheInfoTag();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        lastColumn.setCellValueFactory(new PropertyValueFactory<Tags,String>("tagUtil"));
        
    }
    
    
    public ObservableList<PieChart.Data> afficher_CIU_chart(String user) throws SQLException{
        ObservableList<PieChart.Data>pcd= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT count(tag), tag FROM tags t where  `Id`='"+user+"' group by tag");
            while(rs.next())
            {   
                
                pcd.add(new PieChart.Data(rs.getString(2),rs.getInt(1)));
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally
        {
            if( con!= null)
                con.close();
            if (s!=null)
                s.close();
            if (rs!=null)
                rs.close();
        }
        return pcd;
    }
    
    
    public ObservableList<PieChart.Data> afficher_Tag_chart() throws SQLException{
        ObservableList<PieChart.Data>pcd= FXCollections.observableArrayList();
        Connection con= null;
        Statement s=null;
        
        ResultSet rs=null;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfichiers", "root","");
            s=con.createStatement();
            rs= s.executeQuery("SELECT tag, count(id) from tags group by tag");
            while(rs.next())
            {   
                
                pcd.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally
        {
            if( con!= null)
                con.close();
            if (s!=null)
                s.close();
            if (rs!=null)
                rs.close();
        }
        return pcd;
    }    
    
}
