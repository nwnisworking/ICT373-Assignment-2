package ict373.assignment2.ui.inputs;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 * <strong>DateInputField class</strong>
 * 
 * <p>The DateInputField class is a custom input field that extends the InputField class to provide a date picker input for selecting dates.</p>
 * @author nwnisworking
 * @date 26/3/2026
 * @filename DateInputField.java
 */
public class DateInputField extends InputField<LocalDate, DatePicker>{
  /**
   * Constructs a new DateInputField with a DatePicker as the input control.   */
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
