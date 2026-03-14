package ict373.assignment2.ui.table;

import ict373.assignment2.events.ButtonEvent;
import ict373.assignment2.events.RowEvent;
import ict373.assignment2.ui.ActionCell;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DataTableView<S> extends TableView<S>{
  public enum Button{
    NONE,
    EDIT,
    DELETE,
    ALL;
  };
  
  private final TableColumn<S, Void> action_column = new TableColumn<>();

  private final ObjectProperty<EventHandler<ButtonEvent<S>>> on_button_clicked = new SimpleObjectProperty<>();

  private final ObjectProperty<EventHandler<RowEvent<S>>> on_row_clicked = new SimpleObjectProperty<>();
  
  private final ObjectProperty<Button> show = new SimpleObjectProperty<>(Button.ALL);
  
  public DataTableView(){
    super();
    
    action_column.setText("Action");
    action_column.setCellFactory(param -> {
      ActionCell<S> cell = new ActionCell<>();
      
      cell.showProperty().bind(show);
      
      return cell;
    });

    addEventHandler(ButtonEvent.BUTTON_EVENT, e -> {
      EventHandler<ButtonEvent<S>> handler = on_button_clicked.getValue();
      
      if(handler != null) handler.handle(e);
    });
    
    setRowFactory(param -> {
      TableRow<S> row = new TableRow<>();
      
      row.setOnMouseClicked(e -> {
        EventHandler<RowEvent<S>> handler = on_row_clicked.getValue();
        
        if(handler != null) handler.handle(new RowEvent<>(row.getItem(), "View"));
      });
      return row;
    });
    Platform.runLater(() -> {
      getColumns().addLast(action_column);
      action_column.prefWidthProperty().bind(widthProperty().multiply(.1));
      action_column.setMinWidth(120);
    });
    
    setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
  }
  
  public void setOnButtonClicked(EventHandler<ButtonEvent<S>> handler){
    on_button_clicked.setValue(handler);
  }
  
  public EventHandler<ButtonEvent<S>> getOnButtonClicked(){
    return on_button_clicked.getValue();
  }
  
  public ObjectProperty<EventHandler<ButtonEvent<S>>> onButtonClickedProperty(){
    return on_button_clicked;
  }
  
  public void setOnRowClicked(EventHandler<RowEvent<S>> handler){
    on_row_clicked.setValue(handler);
  }
  
  public EventHandler<RowEvent<S>> getOnRowClicked(){
    return on_row_clicked.getValue();
  }
  
  public ObjectProperty<EventHandler<RowEvent<S>>> onRowClickedProperty(){
    return on_row_clicked;
  }

  public void setShow(Button button){
    show.setValue(button);
  }
  
  public Button getShow(){
    return show.getValue();
  }
}
