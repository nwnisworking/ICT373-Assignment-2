package ict373.assignment2.ui.table;

import ict373.assignment2.events.TableEvent;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * <strong>DataTableView class</strong>
 * 
 * <p>The DataTableView class is a custom TableView that includes an action column with buttons for editing, deleting, and adding items. It also supports firing events when rows are clicked.</p>
 * @author nwnisworking
 * @date 26/3/2026
 * @filename DataTableView.java
 */
public class DataTableView<S> extends TableView<S>{
  /**
   * Enumeration for the types of buttons to show in the action column.
   */
  public enum Button{
    NONE,
    EDIT,
    DELETE,
    ADD,
    ALL;
  };
  
  /**
   * The action column that contains buttons for editing, deleting, and adding items. The visibility of the buttons is controlled by the show property.
   */
  private final TableColumn<S, Void> action_column = new TableColumn<>();

  /**
   * The show property controls which buttons are displayed in the action column. It can be set to show edit, delete, add, or all buttons.
   */
  private final ObjectProperty<Button> show = new SimpleObjectProperty<>(Button.ALL);
  
  /**
   * Constructs a new DataTableView with an action column and sets up the cell factory for the action column. It also sets up a row factory to fire events when rows are clicked.
   */
  public DataTableView(){
    super();
    
    action_column.setText("Action");
    action_column.setCellFactory(param -> {
      ActionCell<S> cell = new ActionCell<>();
      
      cell.showProperty().bind(show);
      
      return cell;
    });

    setRowFactory(param -> {
      TableRow<S> row = new TableRow<>();
      
      row.setOnMouseClicked(e -> {
        if(!row.isEmpty()){
          fireEvent(new TableEvent<>(TableEvent.VIEW, row.getItem()));
        }
      });

      return row;
    });

    Platform.runLater(() -> getColumns().addLast(action_column));

    setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
  }
  
  /**
   * Set which buttons to show in the action column. This method updates the show property, which controls the visibility of the buttons in the action column.
   * @param button The type of buttons to show in the action column.
   */
  public void setShow(Button button){
    show.setValue(button);
  }
  
  /**
   * Get the type of buttons currently shown in the action column.
   * @return The type of buttons currently shown in the action column.
   */
  public Button getShow(){
    return show.getValue();
  }
}
