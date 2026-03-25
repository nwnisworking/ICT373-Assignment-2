package ict373.assignment2.ui.table;

import ict373.assignment2.events.TableEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * <strong>ActionCell class</strong>
 * 
 * <p>The ActionCell class is a custom TableCell that contains Edit and Delete buttons for performing actions on items in a table view. 
 * The visibility of the buttons can be controlled based on the context of the table.</p>
 * 
 * @author nwnisworking
 * @date 13/3/2026
 * @filename ActionCell.java
 * @param <T> The type of data associated with the table row that this cell belongs to.
 */
public class ActionCell<T> extends TableCell<T, Void>{
  /**
   * The edit button for performing edit actions on the table item.
   */
  private final Button edit_btn = new Button("Edit");
    
  /**
   * The delete button for performing delete actions on the table item.
   */
  private final Button delete_btn = new Button("Delete");
  
  /**
   * The add button for performing add actions on the table item.
   */
  private final Button add_btn = new Button("Add");
    
  /**
   * The container holding the edit and delete buttons, arranged horizontally with spacing.
   */
  private final HBox container = new HBox(10, edit_btn, delete_btn, add_btn);

  /**
   * An ObjectProperty that controls which buttons are shown in the cell. It can be set to show Edit, Delete, both, or neither button.
   */
  private final ObjectProperty<DataTableView.Button> show = new SimpleObjectProperty<>(DataTableView.Button.ALL);
  
  /**
   * Constructs a new ActionCell and initializes the buttons and their visibility based on the show property.
   */
  public ActionCell(){
    container.setAlignment(Pos.CENTER);

    edit_btn.setMinWidth(USE_PREF_SIZE);
    delete_btn.setMinWidth(USE_PREF_SIZE);
    add_btn.setMinWidth(USE_PREF_SIZE);

    edit_btn.getStyleClass().addAll("btn", "blue");
    delete_btn.getStyleClass().addAll("btn", "red");
    add_btn.getStyleClass().addAll("btn", "blue");
    
    edit_btn.visibleProperty().bind(edit_btn.managedProperty());
    delete_btn.visibleProperty().bind(delete_btn.managedProperty());
    add_btn.visibleProperty().bind(add_btn.managedProperty());
    
    show.addListener((obs, oldVal, newVal) -> displayButton(newVal));
  }
  
  /**
   * Updates the visibility of the edit and delete buttons based on the specified button configuration.
   * @param button The button configuration that determines which buttons to show (Edit, Delete, both, or neither).
   */
  private void displayButton(DataTableView.Button button){
    // Edit is the most significant bit, Delete is the least significant bit.
    int bit = 0;

    switch(button){
      case DataTableView.Button.NONE -> bit = 0b000;
      case DataTableView.Button.DELETE -> bit = 0b010;
      case DataTableView.Button.EDIT -> bit = 0b100;
      case DataTableView.Button.ADD -> bit = 0b001;
      case DataTableView.Button.ALL -> bit = 0b110;
    }

    edit_btn.setManaged((bit & 0b100) > 0);
    delete_btn.setManaged((bit & 0b010) > 0);
    add_btn.setManaged((bit & 0b001) > 0);
  }
  
  /**
   * Sets the button configuration for this ActionCell, determining which buttons are visible.
   * @param button The button configuration to set (Edit, Delete, both, or neither).
   */
  public void setShow(DataTableView.Button button){
    show.setValue(button);
  }
  
  /**
   * Returns the current button configuration for this ActionCell, indicating which buttons are visible.
   * @return The current button configuration (Edit, Delete, both, or neither).
   */
  public DataTableView.Button getShow(){
    return show.getValue();
  }
  
  /**
   * Returns the ObjectProperty that controls the button configuration for this ActionCell, allowing for binding and observation of changes to the button visibility.
   * @return The ObjectProperty representing the button configuration for this ActionCell.
   */
  public ObjectProperty<DataTableView.Button> showProperty(){
    return show;
  }
    
  /**
   * Overrides the updateItem method to update the cell's content based on the current item.
   * @param item The item associated with this cell, which is not used in this implementation.
   * @param empty A boolean indicating whether this cell is empty.
   */
  @Override
  protected void updateItem(Void item, boolean empty){
    super.updateItem(item, empty);
        
    T data = getTableRow().getItem();
        
    if(data != null){
      edit_btn.setOnAction(e -> {
        e.consume();
        fireEvent(new TableEvent<>(TableEvent.EDIT, data));
      });
      delete_btn.setOnAction(e -> {
        e.consume();
        fireEvent(new TableEvent<>(TableEvent.DELETE, data));
      });
      add_btn.setOnAction(e -> {
        e.consume();
        fireEvent(new TableEvent<>(TableEvent.ADD, data));
      });
    }
    
    displayButton(show.getValue());
    setGraphic(empty ? null : container);
  }
}
