package ict373.assignment2.ui;

import ict373.assignment2.events.ButtonEvent;
import ict373.assignment2.ui.table.DataTableView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ActionCell<T> extends TableCell<T, Void>{
  private final Button edit_btn = new Button("Edit");
    
  private final Button delete_btn = new Button("Delete");
    
  private final HBox container = new HBox(10, edit_btn, delete_btn);

  private final ObjectProperty<DataTableView.Button> show = new SimpleObjectProperty<>(DataTableView.Button.ALL);
  
  public ActionCell(){
    container.setAlignment(Pos.CENTER);

    edit_btn.setMinWidth(USE_PREF_SIZE);
    delete_btn.setMinWidth(USE_PREF_SIZE);

    edit_btn.getStyleClass().addAll("btn", "blue");
    delete_btn.getStyleClass().addAll("btn", "red");
    
    
    edit_btn.visibleProperty().bind(edit_btn.managedProperty());
    delete_btn.visibleProperty().bind(delete_btn.managedProperty());
  }
  
  private void displayButton(DataTableView.Button button){
    edit_btn.setManaged(false);
    delete_btn.setManaged(false);
    
    switch(button){
      case DataTableView.Button.DELETE -> delete_btn.setManaged(true);
      case DataTableView.Button.EDIT -> edit_btn.setManaged(true);
      case DataTableView.Button.ALL -> {
        edit_btn.setManaged(true);
        delete_btn.setManaged(true);
      }
    }
  }
  
  public void setShow(DataTableView.Button button){
    show.setValue(button);
  }
  
  public DataTableView.Button getShow(){
    return show.getValue();
  }
  
  public ObjectProperty<DataTableView.Button> showProperty(){
    return show;
  }
    
  @Override
  protected void updateItem(Void item, boolean empty){
    super.updateItem(item, empty);
        
    T data = getTableRow().getItem();
        
    if(data != null){
      edit_btn.setOnAction(e -> {
        e.consume();
        fireEvent(new ButtonEvent<>(data, "Edit"));
      });
      delete_btn.setOnAction(e -> {
        e.consume();
        fireEvent(new ButtonEvent<>(data, "Delete"));
      });
    }
    
    if(!empty) displayButton(show.getValue());
    setGraphic(empty ? null : container);
  }
}
