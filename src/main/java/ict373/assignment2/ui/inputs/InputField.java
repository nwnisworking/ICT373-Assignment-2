package ict373.assignment2.ui.inputs;

import ict373.assignment2.App;
import javafx.animation.PauseTransition;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * <strong>InputField abstract class</strong>
 */
public abstract class InputField<T, C extends Control> extends HBox{
  /**
   * Text label for the input field
   */
  @FXML
  protected Label text_label;

  /**
   * The input for the field. This is set by the child class
   */
  protected C input;

  protected final StringProperty label = new SimpleStringProperty();

  protected final ObjectProperty<T> value = new SimpleObjectProperty<>();
  
  protected Tooltip tooltip = new Tooltip();
  
  public InputField(C input){
    App.loadFXML("ui/InputField", this);

    this.input = input;
    
    input.getStyleClass().add("input-field");

    visibleProperty().bind(managedProperty());
    text_label.textProperty().bind(label);

    getChildren().add(input);
  }

  public void setLabel(String text){
    this.label.setValue(text);
  }

  public String getLabel(){
    return label.getValue();
  }

  public void setValue(T value){
    this.value.setValue(value);
  }

  public T getValue(){
    return value.getValue();
  }

  public boolean isEmpty(){
    T val = getValue();
    
    return val == null || (val instanceof String s && s.isBlank());
  }
  
  public void displayTooltip(String text){
    tooltip.setText(text);
    
    Bounds input_bound = input.localToScreen(input.getBoundsInLocal());
    PauseTransition transition = new PauseTransition(Duration.seconds(2));

    input.requestFocus();
    input.getStyleClass().add("highlight");
    
    tooltip.show(
      input,
      input_bound.getMinX(),
      input_bound.getMaxY()
    );
    
    
    transition.setOnFinished(e -> {
      tooltip.hide();
      input.getStyleClass().remove("highlight");
    });
    
    transition.play();
  }
  
  public boolean isNumber(){
    // I was thinking of using try-catch but this solution is more efficient since it does not involve using exceptions.
    // https://stackoverflow.com/a/29331473
    
    String num = (String) getValue();
    
    if(num == null || num.isEmpty())
      return false;
    
    for(int i = 0; i < num.length(); i++){
      char c = num.charAt(i);

      if(c < '0' || c > '9')
        return false;
    }
    
    return true;
  }
  
  public ObjectProperty<T> valueProperty(){
    return value;
  }

  public abstract void reset();
}