package ict373.assignment2.ui.table;

import ict373.assignment2.events.TableEvent;
import ict373.assignment2.ui.ActionCell;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DataTableView<S> extends TableView<S>{
  public enum Button{
    NONE,
    EDIT,
    DELETE,
    ADD,
    ALL;
  };
  
  private final TableColumn<S, Void> action_column = new TableColumn<>();

  private final ObjectProperty<Button> show = new SimpleObjectProperty<>(Button.ALL);
  
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
  
  public void setShow(Button button){
    show.setValue(button);
  }
  
  public Button getShow(){
    return show.getValue();
  }
}
