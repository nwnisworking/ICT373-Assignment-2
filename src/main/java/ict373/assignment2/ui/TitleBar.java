package ict373.assignment2.ui;

import ict373.assignment2.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TitleBar extends BorderPane{
  @FXML
  private Label title_label;
  
  @FXML
  private Button minimize_btn;
  
  @FXML
  private Button maximize_btn;
  
  @FXML
  private Button close_btn;
  
  private double x_offset;
  
  private double y_offset;
  
  private final StringProperty title = new SimpleStringProperty("Title");
  
  public TitleBar(){
    App.loadFXML("ui/TitleBar", this);
  
    title_label.textProperty().bind(title);
    
    setOnMousePressed(this::titleBarClicked);
    setOnMouseDragged(this::titleBarDragged);
    
    minimize_btn.setOnMouseClicked(this::minimizeWindow);
    maximize_btn.setOnMouseClicked(this::maximizeWindow);
    close_btn.setOnMouseClicked(this::closeWindow);
  }
  
  public void setTitle(String title){
    this.title.setValue(title);
  }
  
  public String getTitle(){
    return title.getValue();
  }
  
  private void titleBarClicked(MouseEvent event){
    x_offset = event.getSceneX();
    y_offset = event.getSceneY();
  }
  
  private void titleBarDragged(MouseEvent event){
    Stage stage = getStage();
		stage.setX(event.getScreenX()- x_offset);
		stage.setY(event.getScreenY()- y_offset);
  }
  
  private void minimizeWindow(MouseEvent event){
    getStage().setIconified(true);
  }
  
  private void maximizeWindow(MouseEvent event){
    getStage().setMaximized(!getStage().isMaximized());
  }
  
  private void closeWindow(MouseEvent event){
    getStage().close();
  }
  
  private Stage getStage(){
		return (Stage) getScene().getWindow();
	}
}
