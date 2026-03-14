package ict373.assignment2.ui.inputs;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SelectInputField<T> extends InputField<T, ComboBox<T>>{
  private final ListProperty<T> items = new SimpleListProperty<>(FXCollections.observableArrayList());
  
  public SelectInputField(){
    super(new ComboBox<>());

    input.valueProperty().bindBidirectional(value);
  }
  
  public void setItems(ObservableList<T> items){
    if(items != null){
      this.items.setAll(items);
    }

    input.setItems(this.items);
  }

  public ObservableList<T> getItems(){
    return items.get();
  }

  public ListProperty<T> itemsProperty(){
    return items;
  }

  @Override
  public void reset(){
    setDisable(false);
    input.getSelectionModel().clearAndSelect(0);
  }
}
