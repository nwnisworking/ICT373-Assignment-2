package ict373.assignment2;

import ict373.assignment2.models.customers.Customer;
import ict373.assignment2.ui.inputs.InputField;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface TabRecord<T>{
  List<InputField<?, ?>> fields();
  
  void save(T data);
  
  void load(T data);
}
