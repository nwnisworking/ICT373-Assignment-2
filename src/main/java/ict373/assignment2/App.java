package ict373.assignment2;

import ict373.assignment2.controllers.LoaderController;
import ict373.assignment2.events.WindowEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import javafx.scene.Node;
import javafx.stage.StageStyle;

/**
 * <strong>App class</strong>
 * 
 * <p>The main entry point for the application. It is responsible for loading the main window and 
 * providing a method for loading FXML files with their respective controllers or nodes.</p>
 * 
 * @author nwnisworking
 * @date 2/3/2026
 * @filename App.java
 */
public class App extends Application{
  /**
   * The scene for the application.
   */
  private static Scene scene;

  /**
   * Start the application by loading the main window FXML and setting the stage.
   */
  @Override
  public void start(Stage stage) throws IOException{
    scene = new Scene(loadFXML("ui/Window", new LoaderController()), 840, 480);

    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
    stage.fireEvent(new WindowEvent(WindowEvent.OPEN));
  }

  /**
   * Load an FXML file and set its controller or root node. This is primarily used for <code>&lt;fx:root&gt;</code>.
   * @param fxml The name of the FXML file to load (without the .fxml extension).
   * @param node The node to set as the root and controller of the FXML.
   * @return The loaded parent node from the FXML file, or null if it fails.
   */
  public static Parent loadFXML(String fxml, Node node){
    try{
      URL url = App.class.getResource(fxml + ".fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(url);

      if(node != null){
        fxmlLoader.setRoot(node);
        fxmlLoader.setController(node);
      }

      System.out.println("[App]: Loaded FXML for Node: " + fxml);

      return fxmlLoader.load();
    }
    catch(IOException ex){
      ex.printStackTrace();
      System.out.println("[App]: Unable to load FXML for Node");
    }
    
    return null;
  }
  
  /**
   * Load an FXML file and set its controller.
   * @param fxml The name of the FXML file to load (without the .fxml extension).
   * @param controller The controller to set for the FXML.
   * @return The loaded parent node from the FXML file, or null if it fails.
   */
  public static Parent loadFXML(String fxml, Object controller){
    try{
      URL url = App.class.getResource(fxml + ".fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(url);

      if(controller != null){
        fxmlLoader.setController(controller);
      }

      return fxmlLoader.load();

    }
    catch(IOException ex){
      System.out.println("[App]: Unable to load FXML for Controller");
    }
    
    return null;
  }
 
  /**
   * The main method to launch the application.
   * @param args
   */
  public static void main(String[] args){
    launch();
  }
}