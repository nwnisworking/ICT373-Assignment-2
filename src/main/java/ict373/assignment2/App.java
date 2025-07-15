package ict373.assignment2;

import ict373.assignment2.controllers.MainController;
import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.models.PublicationModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * <p><strong>App class</strong></p>
 * 
 * <p>The main application class for the Magazine Subscription Management (MSM) system.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename App.java
 */
public class App extends Application {
  /**
   * The scene for the application.
   */
  private static Scene scene;

  /**
   * Starts the application.
   * @param stage Stage to display nodes.
   * @throws IOException 
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader controller = FXMLLoader("primary");
    controller.setController(new MainController());
    scene = new Scene(controller.load(), 800, 500);

    stage.setTitle("MSM - Magazine Subscription Managment");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Loads FXML from App path.
   * @param fxml A path containing the FXML.
   * @return The root of the loaded FXML file, or a node with an error message if loading fails.
   */
  public static FXMLLoader FXMLLoader(String fxml){
    try{
      FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
      return loader;
    }
    catch(Exception ex){ // Captures all the exceptions
      return null;
    }
  }
  
  public static URL getResourcePath(String path){
    return App.class.getResource(path);
  }
  
  /**
   * Cancels the application and saves all the incoming changes.
   */
  @Override
  public void stop(){
    PublicationModel.getInstance().save();
    CustomerModel.getInstance().save();
  }
  
  /**
   * Launch the program.
   * @param args 
   */
  public static void main(String[] args){
    launch();
  }
}