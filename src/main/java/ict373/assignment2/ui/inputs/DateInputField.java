package ict373.assignment2.ui.inputs;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;

public class DateInputField extends InputField<LocalDate, DatePicker>{
  public DateInputField(){
    super(new DatePicker());

    input.valueProperty().bindBidirectional(value);
  }

  @Override
  public void reset(){
    setDisable(false);
    input.setValue(null);
  }
}
