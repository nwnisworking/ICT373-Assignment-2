package ict373.assignment2.controllers;

import ict373.assignment2.App;
import ict373.assignment2.events.*;
import ict373.assignment2.services.*;
import ict373.assignment2.ui.Modal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * <strong>HomeController class</strong>
 * 
 * <p>The home controller displays the content for customers and publications.</p>
 * 
 * @author nwnisworking
 * @date 1/3/2026
 * @filename HomeController.java
 */
public class HomeController implements Initializable{
  /** The list of navigation items */
	@FXML
	private ListView<String> nav_items;

  @FXML
  private StackPane main;
  
  /** The content area to display different pages */
	@FXML
	private BorderPane content;
  
  /**
   * The modal component used for displaying additional content.
   */
  @FXML
  private Modal modal;
  
  @FXML
  private VBox toast_container;
  
  /**
   * Initialize the controller by setting up the navigation listener and loading the first page.
   * 
   * @param url The location for the root object.
   * @param rb The resources used to localize the root object.
   */
	@Override
	public void initialize(URL url, ResourceBundle rb){
    nav_items.getSelectionModel().selectedItemProperty().addListener(this::loadPage);
		nav_items.getSelectionModel().selectFirst();
    
    content.addEventHandler(ModalEvent.OPEN, this::openModal);
    content.addEventHandler(ModalEvent.CLOSE, this::closeModal);
    content.addEventHandler(ToastEvent.ANY, this::displayToast);
    
    toast_container.setMaxHeight(Region.USE_PREF_SIZE);
    toast_container.setMaxWidth(Region.USE_PREF_SIZE);
	}
  
  /**
   * Load the selected page into the content area.
   * @param obs The observable value that triggered the change
   * @param old_value The previously selected navigation item
   * @param new_value The newly selected navigation item
   */
  private void loadPage(Observable obs, String old_value, String new_value){
    // Ignore null values and same value selection
    if(new_value == null || new_value.equals(old_value)) return;

    Node node = null;

    switch(new_value){
      case "Customers" -> node = App.loadFXML("controllers/customer/Index", null);
      case "Publications" -> node = App.loadFXML("controllers/publication/Index", null);
      case "Save As" -> {
        saveAs();
        nav_items.getSelectionModel().select(old_value);
      }
      case "Load" -> {
        loadFile();
        nav_items.getSelectionModel().select(old_value);
      }
    }

    if(node != null)
      content.setCenter(node);
    
    System.out.println("[Page]: " + new_value + " page loaded.");
  }
  
  /**
   * Event handler to open the modal with the provided content node.
   * 
   * @param event The ModalEvent containing the node to be displayed in the modal. 
   */
  private void openModal(ModalEvent event){
    event.consume();
    modal.open(event.getNode());
  }
  
  /**
   * Event handler to close the modal when a close event is triggered.
   * 
   * @param event The ModalEvent triggered to close the modal.
   */
  private void closeModal(ModalEvent event){
    event.consume();
    modal.close();
  }
  
  /**
   * Event handler to display a toast notification when a ToastEvent is triggered.
   * 
   * @param event The ToastEvent containing the message and status for the toast notification.
   */
  private void displayToast(ToastEvent event){
    Label label = new Label(event.getMessage());
    label.getStyleClass().addAll("toast-notification", event.getStatus().name().toLowerCase());
    
    FadeTransition fade_in = new FadeTransition(Duration.millis(600), label);
    fade_in.setFromValue(0);
    fade_in.setToValue(1);
    
    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    
    TranslateTransition slide_in = new TranslateTransition(Duration.millis(600), label);
    slide_in.setFromY(40);
    slide_in.setToY(0);
    
    FadeTransition fade_out = new FadeTransition(Duration.millis(600), label);
    fade_out.setFromValue(1);
    fade_out.setToValue(0);
    
    ParallelTransition parallel = new ParallelTransition(fade_in, slide_in);
    SequentialTransition seq = new SequentialTransition(parallel, pause, fade_out);
    
    seq.play();
    seq.setOnFinished(e -> toast_container.getChildren().remove(label));
    
    toast_container.getChildren().add(label);
  }
  
  /**
   * Save the current state of the system to a file using serialization.
   */
  private void saveAs(){
    FileChooser fc = new FileChooser();
    URL resource = App.class.getResource("");
    
    fc.setTitle("Save Masterlist");
    fc.setInitialFileName("masterlist-" + LocalDate.now());
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ser File", "*.ser"));
    fc.setInitialDirectory(new File(resource.getPath()));
    
    File file = fc.showSaveDialog(content.getScene().getWindow());
    
    if(file == null) return;
    
    try{
      ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
      
      CustomerService.getInstance().write(output);
      PublicationService.getInstance().write(output);
      SubscriptionService.getInstance().write(output);
      
      output.flush();
      output.close();
      System.out.println("[Loader]: Write to file.");
    }
    catch(IOException ex){
      System.out.println("[Loader]: Unable to write to file.");
    }
  }

  /**
   * Load the state of the system from a file using deserialization.
   */
  private void loadFile(){
    FileChooser fc = new FileChooser();
    URL resource = App.class.getResource("");
    
    fc.setTitle("Load Masterlist");
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ser File", "*.ser"));
    fc.setInitialDirectory(new File(resource.getPath()));
    
    File file = fc.showOpenDialog(content.getScene().getWindow());

    if(file == null) return;

    try{
      ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

      CustomerService.getInstance().read(input);
      PublicationService.getInstance().read(input);
      SubscriptionService.getInstance().read(input);
    }
    catch(IOException ex){
      System.out.println("[Loader]: Unable to read from the system.");
    }
  }
}