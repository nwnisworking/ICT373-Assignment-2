package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ButtonEvent<T> extends Event{
  public static final EventType<ButtonEvent> BUTTON_EVENT = new EventType<>(ANY, "BUTTON_EVENT");

  private final String type;
  
  private T item;
  
  public ButtonEvent(T item, String type){
    super(BUTTON_EVENT);
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
