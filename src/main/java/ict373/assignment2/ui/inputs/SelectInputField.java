package ict373.assignment2.ui.inputs;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * <strong>SelectInputField class</strong>
 * 
 * <p>The SelectInputField class is a custom input field that extends the InputField class to provide a combo box input for selecting items from a list.</p>
 * @author nwnisworking
 * @date 26/3/2026
 * @filename SelectInputField.java
 */
public class SelectInputField<T> extends InputField<T, ComboBox<T>>{
  private final ListProperty<T> items = new SimpleListProperty<>(FXCollections.observableArrayList());
  
  /**
   * Constructs a new SelectInputField with a ComboBox as the input control. 
   */
  public SelectInputField(){
    super(new ComboBox<>());

    input.valueProperty().bindBidirectional(value);
  }
  
  /**
   * Set the items to be displayed in the combo box. This method updates the items in the combo box with the provided list of items.
   * @param items The list of items to be displayed in the combo box.
   */
  public void setItems(ObservableList<T> items){
    if(items != null){
      this.items.setAll(items);
    }

    input.setItems(this.items);
  }

  /**
   * Get the items currently displayed in the combo box.
   * @return An ObservableList of items currently displayed in the combo box.
   */
  public ObservableList<T> getItems(){
    return items.get();
  }

  /**
   * Get the ListProperty of items for the combo box. This property can be used for binding or observing changes to the list of items.
   * @return The ListProperty of items for the combo box.
   */
  public ListProperty<T> itemsProperty(){
    return items;
  }

  @Override
  public void reset(){
    setDisable(false);
    input.getSelectionModel().clearAndSelect(0);
  }
}
