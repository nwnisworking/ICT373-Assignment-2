package ict373.assignment2.ui.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertyColumn<S, T> extends TableColumn<S, T>{
  private final StringProperty property = new SimpleStringProperty();
  
  public PropertyColumn(){
    super();

    property.addListener((obs, old_value, new_value) -> {
      if(new_value != null && !new_value.isEmpty())
        setCellValueFactory(new PropertyValueFactory<>(new_value));
    });
  }
  
  public void setProperty(String property){
    this.property.setValue(property);
  }
  
  public String getProperty(){
    return property.getValue();
  }
  
  public StringProperty propertyProperty(){
    return property;
  }
}
