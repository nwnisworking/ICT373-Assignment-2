package ict373.assignment2.forms;

import ict373.assignment2.ui.inputs.InputField;
import ict373.assignment2.utils.Validator.ValidatorResult;

import java.util.List;

public interface FormRecord<T>{
  List<InputField<?, ?>> fields();
  
  void save(T data);
  
  void load(T data);

  ValidatorResult validate();
}
