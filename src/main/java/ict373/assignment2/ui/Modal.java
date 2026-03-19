package ict373.assignment2.ui;

import ict373.assignment2.App;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * <strong>Modal class</strong>
 * 
 * <p>The Modal class is designed to provide a reusable modal component to display
 * additional content in a pop-style display. It consists of an overlay and a content area which uses fade and translate transitions.
 * 
 * @author nwnisworking
 * @date 19/3/2026
 * @filename Modal.java
 */
public class Modal extends StackPane{
  /**
   * The overlay pane that covers the background when the modal is open.
   */
  @FXML
  private Pane overlay;
  
  /**
   * The content area of the modal where additional content can be displayed.
   */
  @FXML
  private StackPane content;
  
  /**
   * Constructor for the Modal class. 
   */
  public Modal(){
    super();
    App.loadFXML("ui/Modal", this);
    
    visibleProperty().bind(managedProperty());
    content.prefWidthProperty().bind(widthProperty().multiply(.8));
    content.maxWidthProperty().bind(widthProperty().multiply(.8));
    overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, this::overlayClicked);
  }

  /**
   * Event handler for when the overlay is clicked, triggering the close method to hide the modal.
   * 
   * @param event The MouseEvent triggered when the overlay is clicked.
   */
  private void overlayClicked(MouseEvent event){
    close();
  }
  
  /**
   * Open the modal and display the provided node as content.
   * @param node The Node to be displayed in the modal's content area. If null, the modal will not open.
   */
  public void open(Node node){
    setManaged(true);
    
    FadeTransition overlay_transition = new FadeTransition(Duration.millis(400), overlay);
    TranslateTransition content_transition = new TranslateTransition(Duration.millis(400), content);

    if(node == null) return;
    
    content.getChildren().add(node);
    
    overlay_transition.setFromValue(0);
    overlay_transition.setToValue(1);
    overlay_transition.play();
    
    content.setTranslateY(300);
    content_transition.setFromY(300);
    content_transition.setToY(0);
    overlay_transition.setOnFinished(e -> content_transition.play());
  }
  
  /**
   * Close the modal by removing all content and hiding the modal.
   */
  public void close(){
    content.getChildren().removeAll();
    setManaged(false);
  }
}
