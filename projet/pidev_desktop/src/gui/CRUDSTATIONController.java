/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.Ligne;
import entity.MoyTran;
import entity.Station;
import entity.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
//import org.webjars.npm.leaflet.L;
//import org.
import javafx.stage.Stage;
import org.openstreetmap.josm.gui.MapView;
import service.MoyTranService;
import service.StationService;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import service.LigneService;
import service.UserService;



public class CRUDSTATIONController implements  Initializable  {
    private Stage stage;
    private Scene scene;
    private Parent root1;
       

private Map map;
private JMapViewer mapViewer;
    @FXML
    private TextField txtlonalt;
    private ComboBox<Integer> txtmoy;
    @FXML
    private TableView<Station> tab_station;
    @FXML
    private TableColumn<Station, Integer> idstation;
    @FXML
    private TableColumn<Station, String> lon_alt;
    private TableColumn<Station, Integer> idmoy;
    @FXML
    private ComboBox<String> txttp;
    @FXML
    private TextField txtch;
    @FXML
    private Button btnMap;
    private WebView webView;
    
    private WebEngine webEngine;
    private User u;
    UserService us = new UserService();
    /**
     * Initializes the controller class.
     */
    
    public void getUser(User u){
        this.u=u;
    }
     @FXML
    private void UpdateTable() {
        List<Station> list=new ArrayList<>();
        
        StationService ss=new StationService();
        if (txtch.getText().length() == 0)
        list=ss.getAll();
        else{
        list.add(ss.getOneById(Integer.parseInt(txtch.getText())));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Recherche avec succés");
                alert.showAndWait();
        }
        
        ObservableList<Station> obs=FXCollections.observableArrayList(list);
        idstation.setCellValueFactory(new PropertyValueFactory<Station ,Integer>("id_station"));
        lon_alt.setCellValueFactory(new PropertyValueFactory<Station ,String>("lang_alt"));

       
        tab_station.setItems(obs);
    }
private LigneService ligneService = new LigneService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    LigneService LigneService = new LigneService();

    List<Ligne> lignes = ligneService.readAll();

    // Créez une liste de chaînes à partir des objets Ligne
    List<String> ligneNames = lignes.stream()
            .map(Ligne::getNom_ligne)
            .collect(Collectors.toList());

    // Créez une ObservableList à partir de la liste des noms de lignes
    ObservableList<String> observableLines = FXCollections.observableArrayList(ligneNames);
    

    // Définissez la ComboBox pour afficher les lignes
    txttp.setItems(observableLines);

        UpdateTable();
    }    

@FXML
private void ajouterstation(ActionEvent event) {
    String langAlt = txtlonalt.getText();
    String selectedLine = txttp.getValue();

    if (langAlt.isEmpty() || selectedLine == null) {
        afficherErreur("Vous devez remplir tous les champs, y compris la ligne");
    } else {
        try {
            // Récupérez l'ID de la ligne sélectionnée
            int ligneId = getIdFromSelectedLine(selectedLine);

            // Créez une nouvelle station avec l'altitude/longitude et l'ID de la ligne
            Station t = new Station(langAlt, ligneId);

            StationService s = new StationService();
            s.ajouter(t);

            afficherMessage("Insertion avec succès");
            UpdateTable();
        } catch (Exception e) {
            afficherErreur("Erreur lors de l'insertion de la station : " + e.getMessage());
        }
    }
}

// Méthode pour obtenir l'ID de la ligne à partir de son nom
private int getIdFromSelectedLine(String selectedLineName) {
    List<Ligne> lignes = ligneService.readAll();
    Optional<Ligne> selectedLigne = lignes.stream()
            .filter(ligne -> ligne.getNom_ligne().equals(selectedLineName))
            .findFirst();

    if (selectedLigne.isPresent()) {
        return selectedLigne.get().getId_ligne();
    } else {
        // Gérer le cas où la ligne sélectionnée n'est pas trouvée
        throw new IllegalArgumentException("Ligne sélectionnée introuvable.");
    }
}






// Méthode pour afficher une boîte de dialogue d'erreur
private void afficherErreur(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.showAndWait();
}

// Méthode pour afficher une boîte de dialogue d'information
private void afficherMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText(message);
    alert.showAndWait();
}



    @FXML
private void modifierstation(ActionEvent event) {
    if (txtlonalt.getText().isEmpty() ) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
        a.showAndWait();
    } else {
        StationService sr = new StationService();
        Station r;

        // Get the selected row from the TableView
        Station selectedStation = tab_station.getSelectionModel().getSelectedItem();

        // Get the id_station value from the selected row
        int id_station = selectedStation.getId_station();

        r = new Station(id_station, txtlonalt.getText());
        sr.modifier(r);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "station modifiée", ButtonType.OK);
        alert.show();
        UpdateTable();
        
    }
}


    @FXML
    private void supprimerstation(ActionEvent event) {
               StationService is=new StationService();
        Station selected_it =  tab_station.getSelectionModel().getSelectedItem();
        is.delete(selected_it.getId_station());
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Suppression avec succés");
                alert.showAndWait();
        UpdateTable();
    }

    private void switchtmp(ActionEvent event) throws IOException {
         root1 = FXMLLoader.load(getClass().getResource("CRUDCOM.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openMap(ActionEvent event) {
    WebView mapWebView = new WebView();
    WebEngine mapEngine = mapWebView.getEngine();
    mapEngine.load(getClass().getResource("map.html").toExternalForm());
    Scene mapScene = new Scene(mapWebView);
    Stage mapStage = new Stage();
    mapStage.setScene(mapScene);
    mapStage.show();
    }

    @FXML
    private void generateQRCode(ActionEvent event) throws WriterException {
          // Get the selected station from the table
    Station selectedStation = tab_station.getSelectionModel().getSelectedItem();
    
    // Create a string containing the station information
    String stationInfo =selectedStation.getLang_alt();
    
    // Generate a QR code from the station information
    QRCodeWriter qrCodeWriter = new QRCodeWriter();

BitMatrix bitMatrix = qrCodeWriter.encode(stationInfo, BarcodeFormat.QR_CODE, 200, 200);
    
    // Convert the QR code to an image
    BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
    
    // Display the image in a new window
    ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
    Stage stage = new Stage();
    stage.setScene(new Scene(new BorderPane(imageView), 300, 300));
    stage.show();
    }
 
private void ajoutmark(ActionEvent event) {
   Station station = tab_station.getSelectionModel().getSelectedItem();
if (station != null && mapViewer != null) {
String[] latLng = station.getLang_alt().split(",");
if (latLng.length == 2) {
double latitude = Double.parseDouble(latLng[0]);
double longitude = Double.parseDouble(latLng[1]);
   MapMarker marker = new MapMarkerDot(latitude, longitude);
        mapViewer.addMapMarker(marker);
        if (!webView.isVisible()) {
    webView.setVisible(true);
}
    }
}
}

    @FXML
    private void BackHome(ActionEvent event) throws IOException {
        if(u.getRole().getId_role()==1){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePageAdmin.fxml"));
        Parent root = loader.load();          
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        HomePageAdminController controller = loader.getController();
        controller.setFields(u);
            
        }else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePageAdminStation.fxml"));
        Parent root = loader.load();          
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        HomePageAdminStationController controller = loader.getController();
        controller.setFields(u);
        }
    }

   
    }
    

