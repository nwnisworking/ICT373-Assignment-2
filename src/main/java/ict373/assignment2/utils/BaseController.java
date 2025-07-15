package ict373.assignment2.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ict373.assignment2.App;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;
import javafx.stage.Modality;

/**
 * <p><strong>BaseController class</strong></p>
 * 
 * <p>Base controller class for handling common functionalities across controllers.</p>
 * 
 * <p>This class provides methods for displaying popups, alerts, and managing UI elements.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename BaseController.java
 */
public abstract class BaseController{
  /**
   * Popup a new window with the specified title and path.
   * @param title The title of the popup window.
   * @param path The path to the FXML file for the popup.
   * @param controller The controller instance to be used for the popup.
   */
  public void popup(String title, String path, BaseController controller){
    try{
      FXMLLoader loader = new FXMLLoader(App.getResourcePath(path));
      loader.setController(controller);
      Parent root = loader.load();
      Stage stage = new Stage();
      Scene scene = new Scene(root);

      stage.setTitle(title);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);
      stage.showAndWait();
    }
    catch(IOException ex){
      System.out.println("Could not load popup: " + path);
      ex.printStackTrace();
    }
  }
  
  /**
   * Display an alert with the specified header and content. 
   * @param header The header text for the alert.
   * @param content The content text for the alert.
   */
  public void alert(String header, String content){
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(header);
    alert.setContentText(content);
    
    alert.showAndWait();
  }
  
  /**
   * Display an element in the UI based on the specified value.
   * @param node The UI element to display.
   * @param value The boolean value indicating whether to display the element.
   */
  public void displayElement(Node node, boolean value){
    node.setManaged(value);
    node.setVisible(value);
  }
  
  /**
   * Disable input element based on the specified value.
   * @param control The input control to disable.
   * @param value The boolean value indicating whether to disable the input.
   */
  public void disableInputElement(TextInputControl control, boolean value){
    control.setMouseTransparent(value);
    control.setEditable(!value);
  }
  
  /**
   * Disable input element based on the specified value.
   * @param combobox The ComboBoxBase element to disable.
   * @param value The boolean value indicating whether to disable the input.
   */
  public void disableInputElement(ComboBoxBase<?> combobox, boolean value){
    combobox.setMouseTransparent(value);
    combobox.setEditable(value);
  }
}
