package ict373.assignment2.ui.inputs;

import javafx.scene.control.TextField;

public class TextInputField extends InputField<String, TextField>{
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
