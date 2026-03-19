package ict373.assignment2.controllers;

import ict373.assignment2.App;
import ict373.assignment2.events.ModalEvent;
import ict373.assignment2.ui.Modal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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
      case "Subscriptions" -> System.out.println("Subscription");
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
}