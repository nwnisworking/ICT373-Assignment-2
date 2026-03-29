package ict373.assignment2.ui.inputs;

import javafx.scene.control.TextField;

/**
 * <strong>TextInputField class</strong>
 * 
 * <p>The TextInputField class is a custom input field that extends the InputField class to provide a text input for entering string values.</p>
 * @author nwnisworking
 * @date 26/3/2026
 * @filename TextInputField.java
 */
public class TextInputField extends InputField<String, TextField>{
  /**
   * Constructs a new TextInputField with a TextField as the input control. 
   */
  public TextInputField(){
    super(new TextField());

    input.textProperty().bindBidirectional(value);
  }

  @Override
  public void reset(){
    setDisable(false);
    input.clear();
  }
}
