package ict373.assignment2.controllers;

import ict373.assignment2.controllers.publication.PublicationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import ict373.assignment2.App;
import javafx.fxml.FXMLLoader;
import ict373.assignment2.controllers.customer.CustomerController;
import ict373.assignment2.models.*;
import ict373.assignment2.utils.BaseController;
import ict373.assignment2.utils.Bundler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

/**
 * <p><strong>MainController class</strong></p>
 * 
 * <p>Controller for the main application, managing navigation between different pages such as publication and customer management.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename MainController.java
 */
public class MainController extends BaseController implements Initializable{
  /**
   * Navigation group for the main application.
   */
  @FXML
  private ToggleGroup nav_group;
  
  /**
   * Main layout grid pane.
   */
  @FXML
  private GridPane main;
  
  /**
   * Stack pane for the content area.
   */
  @FXML
  private StackPane content_page;
  
  /**
   * Button to save the current state to a dat file.
   */
  @FXML
  private ToggleButton save_dat_btn;

  /**
   * Button to load the state from a dat file.
   */
  @FXML
  private ToggleButton load_dat_btn;
  
  /**
   * A method that loads the publication page.
   * @throws IOException
   */
  @FXML
  public void publicationPage() throws IOException{
    clearContentPage();
    FXMLLoader loader = App.FXMLLoader("publication");
    loader.setController(new PublicationController());
    
    content_page.getChildren().add(loader.load());
  }
  
  /**
   * A method that loads the customer page.
   * @throws IOException
   */
  @FXML
  public void customerPage() throws IOException{
    clearContentPage();
    FXMLLoader loader = App.FXMLLoader("customer");
    loader.setController(new CustomerController());
    
    content_page.getChildren().add(loader.load());
  }
  
  /**
   * A method that saves the current state to a .dat file.
   */
  @FXML
  public void saveDAT(){
    FileChooser fc = new FileChooser();
    fc.setTitle("Save Masterlist");
    fc.setInitialFileName("masterlist-" + LocalDate.now());
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data File", "*.dat"));
    fc.setInitialDirectory(new File(App.getResourcePath("").getPath()));
    File file = fc.showSaveDialog(main.getScene().getWindow());
    
    if(file == null) return;
    
    Bundler bundler = new Bundler(
      PublicationModel.getInstance(), 
      CustomerModel.getInstance()
    );
    
    try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))){
        output.writeObject(bundler);
    }
    catch(IOException e){
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Warning");
      alert.setContentText("Failed to save masterlist to file.");
      
      alert.showAndWait();
    }
  }
  
  /**
   * A method that loads the state from a .dat file.
   */
  @FXML
  public void loadDAT(){
    FileChooser fc = new FileChooser();
    fc.setTitle("Load Masterlist");
    fc.setInitialDirectory(new File(App.getResourcePath("").getPath()));
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data File", "*.dat"));
    File file = fc.showOpenDialog(main.getScene().getWindow());
    
    if(file == null) return;
    
    try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))){
      Bundler bundler = (Bundler) input.readObject();
      bundler.updateCustomer(CustomerModel.getInstance());
      bundler.updatePublication(PublicationModel.getInstance());
    }
    catch(IOException | ClassNotFoundException | ClassCastException e){
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Warning");
      alert.setContentText("Failed to load masterlist from file.");

      alert.showAndWait();
    }
  }
  
  /**
   * Initializes FXML page
   * @param url the location used to resolve relative paths for the root object, or null if the location is not known
   * @param resources the resources used to localize the root object, or null if the root object is not localized
   */
  @Override
  public void initialize(URL url, ResourceBundle resources){
    try{
      publicationPage();
      
      nav_group.selectedToggleProperty().addListener((ob, o, n)->{
        if(n == null || n.equals(load_dat_btn) || n.equals(save_dat_btn)){
          nav_group.selectToggle(o);
        }
      });
    }
    catch(IOException ex){ 
      throw new RuntimeException("Unable to initialize " + getClass().getSimpleName());
    }
  }
   
  /**
   * Clears the content page by removing all children from the StackPane.
   */
  private void clearContentPage(){
    content_page.getChildren().removeAll(content_page.getChildren());
  }
}
