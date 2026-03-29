package ict373.assignment2.ui.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <strong>PropertyColumn class</strong>
 * 
 * <p>The PropertyColumn class is a custom TableColumn that allows setting the property name for the cell value factory using a StringProperty. </p>
 * @author nwnisworking
 * @date 26/3/2026
 * @filename PropertyColumn.java
 */
public class PropertyColumn<S, T> extends TableColumn<S, T>{
  /**
   * The property name for the cell value factory. This property is used to set the property name for the PropertyValueFactory, which determines how the cell values are retrieved from the data items in the table.
   */
  private final StringProperty property = new SimpleStringProperty();
  
  /**
   * Constructs a new PropertyColumn and sets up a listener on the property StringProperty to update the cell value factory whenever the property name changes. 
   */
  public PropertyColumn(){
    super();

    property.addListener((obs, old_value, new_value) -> {
      if(new_value != null && !new_value.isEmpty())
        setCellValueFactory(new PropertyValueFactory<>(new_value));
    });
  }
  
  /**
   * Set the property name for the cell value factory. This method updates the property StringProperty, which in turn updates the cell value factory for the column.
   * @param property The property name to be used for the cell value factory.
   */
  public void setProperty(String property){
    this.property.setValue(property);
  }
  
  /**
   * Get the current property name used for the cell value factory.
   * @return The current property name used for the cell value factory.
   */
  public String getProperty(){
    return property.getValue();
  }
  
  /**
   * Get the StringProperty for the property name. This property can be used for binding or observing changes to the property name.
   * @return The StringProperty for the property name.
   */
  public StringProperty propertyProperty(){
    return property;
  }
}
