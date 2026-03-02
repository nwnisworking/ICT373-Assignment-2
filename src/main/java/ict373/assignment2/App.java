package ict373.assignment2;

import ict373.assignment2.controllers.LoaderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import javafx.scene.Node;
import javafx.stage.StageStyle;

public class App extends Application{

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException{
    stage.initStyle(StageStyle.TRANSPARENT);
    scene = new Scene(loadFXML("ui/Window", new LoaderController()), 840, 480);

    stage.setScene(scene);
    stage.show();
  }

  public static Parent loadFXML(String fxml, Node node){
    try{
      URL url = App.class.getResource(fxml + ".fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(url);

      // This is used for custom elements
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
    
  public static void main(String[] args){
    launch();
  }
}