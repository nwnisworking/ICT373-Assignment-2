package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * <strong>ToastEvent class</strong>
 * 
 * <p>The ToastEvent class represents events related to displaying toast notifications to the user. It includes information about the status of the toast (e.g., success, warning, error, info) and the message to be displayed.</p>
 * 
 * @author nwnisworking
 * @date 26/3/2026
 * @filename ToastEvent.java
 */
public class ToastEvent extends Event{
  /**
   * The status of the toast notification, indicating the type of message being displayed (e.g., success, warning, error, info).
   */
  public enum Status{
    SUCCESS,
    WARN,
    ERROR,
    INFO;
  };
  
  /**
   * The event type for any toast-related event.
   */
  public static final EventType<ToastEvent> ANY = new EventType<>(Event.ANY, "TOAST_EVENT");
  
  /**
   * The event type for when a toast notification is shown.
   */
  private final Status status;
  
  /**
   * The message to be displayed in the toast notification.
   */
  private final String message;
  
  /**
   * Constructs a new ToastEvent with the specified event type, status, and message.
   * 
   * @param event The type of the event.
   * @param status The status of the toast notification.
   * @param message The message to be displayed in the toast notification.
   */
  public ToastEvent(EventType<ToastEvent> event, Status status, String message){
    super(event);
    this.status = status;
    this.message = message;
  }
  
  /**
   * Returns the status of the toast notification.
   * 
   * @return The status of the toast notification.
   */
  public Status getStatus(){
    return status;
  }
  
  /**
   * Returns the message to be displayed in the toast notification.
   * 
   * @return The message to be displayed in the toast notification.
   */
  public String getMessage(){
    return message;
  }
}
