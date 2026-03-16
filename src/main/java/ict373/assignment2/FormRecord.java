package ict373.assignment2;

import ict373.assignment2.ui.inputs.InputField;
import java.util.List;

public interface FormRecord<T>{
  List<InputField<?, ?>> fields();
  
  void save(T data);
  
  void load(T data);
}
