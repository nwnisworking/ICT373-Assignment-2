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
 * 
 * <p>The InputField class is an abstract base class for various types of input fields in the application. 
 * It merges both the label and input control into a single component to form a unit for user input.</p>
 * 
 * @author nwnisworking
 * @date 9/3/2026
 * @filename InputField.java
 * @param <T> The type of the value that the input field holds.
 * @param <C> The type of the input control used for this input field, which must extend Control.
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

  /**
   * The label property for the input field, which is used to bind the text of the label.
   */
  protected final StringProperty label = new SimpleStringProperty();

  /**
   * The value property for the input field, which is used to bind the value of the input.
   */
  protected final ObjectProperty<T> value = new SimpleObjectProperty<>();
  
  /**
   * Tooltip for displaying validation messages or additional information about the input field.
   */
  protected Tooltip tooltip = new Tooltip();
  
  /**
   * Constructs an InputField with the specified input control.
   * 
   * @param input The input control to be used for this input field.
   */
  public InputField(C input){
    App.loadFXML("ui/InputField", this);

    this.input = input;
    
    input.getStyleClass().add("input-field");

    visibleProperty().bind(managedProperty());
    text_label.textProperty().bind(label);

    getChildren().add(input);
  }

  /**
   * Set the label text for the input field.
   * 
   * @param text The text to set as the label for the input field.
   */
  public void setLabel(String text){
    this.label.setValue(text);
  }

  /**
   * Get the label text of the input field.
   * 
   * @return The label text of the input field.
   */
  public String getLabel(){
    return label.getValue();
  }

  /**
   * Set the value of the input field.
   * 
   * @param value The value to set for the input field.
   */
  public void setValue(T value){
    this.value.setValue(value);
  }

  /**
   * Get the value of the input field.
   * 
   * @return The value of the input field.
   */
  public T getValue(){
    return value.getValue();
  }

  /**
   * Check if the input field is empty. An input field is considered empty if its value is null or, in the case of a string, blank.
   * 
   * @return true if the input field is empty, false otherwise.
   */
  public boolean isEmpty(){
    T val = getValue();
    
    return val == null || (val instanceof String s && s.isBlank());
  }
  
  /**
   * Display a tooltip with the provided text near the input field.
   * 
   * @param text The text to display in the tooltip.
   */
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
  
  /**
   * Get the value property of the input field.
   * @return The value property of the input field.
   */
  public ObjectProperty<T> valueProperty(){
    return value;
  }

  /**
   * Reset the input field to its default state.
   */
  public abstract void reset();
}