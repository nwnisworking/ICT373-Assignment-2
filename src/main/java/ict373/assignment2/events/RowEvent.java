package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author nwnis
 */
public class RowEvent<T> extends Event{
  public static final EventType<RowEvent> ROW_EVENT = new EventType<>(ANY, "ROW_EVENT");
  
  private final String type;
  
  private final T item;
  
  public RowEvent(T item, String type){
    super(ROW_EVENT);
    this.type = type;
    this.item = item;
  }
  
  public String getType(){
    return type;
  }
  
  public T getItem(){
    return item;
  }
}
