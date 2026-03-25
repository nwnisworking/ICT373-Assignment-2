package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ToastEvent extends Event{
  public enum Status{
    SUCCESS,
    WARN,
    ERROR,
    INFO;
  };
  
  public static final EventType<ToastEvent> ANY = new EventType<>(Event.ANY, "TOAST_EVENT");
  
  private final Status status;
  
  private final String message;
  
  public ToastEvent(EventType<ToastEvent> event, Status status, String message){
    super(event);
    this.status = status;
    this.message = message;
  }
  
  public Status getStatus(){
    return status;
  }
  
  public String getMessage(){
    return message;
  }
}
