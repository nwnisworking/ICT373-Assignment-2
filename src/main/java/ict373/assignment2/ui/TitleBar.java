package ict373.assignment2.ui;

import ict373.assignment2.App;
import ict373.assignment2.events.WindowEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * <strong>TitleBar class</strong>
 * 
 * <p>The TitleBar class represents a custom title bar for the application, allowing users to move, minimize, maximize, and close the window.</p>
 * 
 * @author nwnisworking
 * @date 4/3/2026
 * @filename TitleBar.java
 */
public class TitleBar extends BorderPane{
  /**
   * The label to display the title of the window.
   */
  @FXML
  private Label title_label;
  
  /**
   * The button to minimize the window.
   */
  @FXML
  private Button minimize_btn;
  
  /**
   * The button to maximize or restore the window.
   */
  @FXML
  private Button maximize_btn;
  
  /**
   * The button to close the window.
   */
  @FXML
  private Button close_btn;
  
  /**
   * The x offset for dragging the window.
   */
  private double x_offset;
  
  /**
   * The y offset for dragging the window.
   */
  private double y_offset;
  
  /**
   * The title property for the title bar.
   */
  private final StringProperty title = new SimpleStringProperty("Title");
  
  /**
   * Constructor for the TitleBar class. Initalizes the title bar by loading the FXML layout and setting up event handlers for dragging and button actions.
   */
  public TitleBar(){
    App.loadFXML("ui/TitleBar", this);
  
    title_label.textProperty().bind(title);
    
    setOnMousePressed(this::titleBarClicked);
    setOnMouseDragged(this::titleBarDragged);
    
    minimize_btn.setOnMouseClicked(this::minimizeWindow);
    maximize_btn.setOnMouseClicked(this::maximizeWindow);
    close_btn.setOnMouseClicked(this::closeWindow);
  }
  
  /**
   * Set the title of the title bar.
   */
  public void setTitle(String title){
    this.title.setValue(title);
  }
  
  /**
   * Get the current title of the title bar.
   */
  public String getTitle(){
    return title.getValue();
  }
  
  /**
   * Event handler for when the title bar is clicked. Records the initial mouse position for dragging the window.
   */
  private void titleBarClicked(MouseEvent event){
    x_offset = event.getSceneX();
    y_offset = event.getSceneY();
  }
  
  /**
   * Event handler for when the title bar is dragged. Updates the position of the window based on the mouse movement.
   */
  private void titleBarDragged(MouseEvent event){
    Stage stage = getStage();
		stage.setX(event.getScreenX()- x_offset);
		stage.setY(event.getScreenY()- y_offset);
  }
  
  /**
   * Event handler for when the minimize button is clicked. Minimizes the window.
   */
  private void minimizeWindow(MouseEvent event){
    fireEvent(new WindowEvent(WindowEvent.MINIMIZE));
    getStage().setIconified(true);
  }
  
  /**
   * Event handler for when the maximize button is clicked. Maximizes or restores the window.
   */
  private void maximizeWindow(MouseEvent event){
    fireEvent(new WindowEvent(WindowEvent.MAXIMIZE));
    getStage().setMaximized(!getStage().isMaximized());
  }
  
  /**
   * Event handler for when the close button is clicked. Closes the window.
   */
  private void closeWindow(MouseEvent event){
    fireEvent(new WindowEvent(WindowEvent.CLOSE));
    getStage().close();
  }
  
  /**
   * Helper method to get the current stage (window) that the title bar is in.
   */
  private Stage getStage(){
		return (Stage) getScene().getWindow();
	}
}
