package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * <strong>TableEvent class</strong>
 * 
 * <p>The TableEvent class represents events related to table actions, such as editing, deleting, or viewing items in the table.</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename TableEvent.java
 * @param <T> The type of data associated with the table event.
 */
public class TableEvent<T> extends Event{
  /**
   * The base event type for all table events.
   */
  public static final EventType<TableEvent<?>> ANY = new EventType<>(Event.ANY, "TABLE_EVENT");

  /**
   * The event type for editing an item in the table.
   */
  public static final EventType<TableEvent<?>> EDIT = new EventType<>(ANY, "TABLE_EDIT");

  /**
   * The event type for deleting an item from the table.
   */
  public static final EventType<TableEvent<?>> DELETE = new EventType<>(ANY, "TABLE_DELETE");

  /**
   * The event type for viewing an item in the table.
   */
  public static final EventType<TableEvent<?>> VIEW = new EventType<>(ANY, "TABLE_VIEW");

  /**
   * The event type for adding an item in the table.
   */
  public static final EventType<TableEvent<?>> ADD = new EventType<>(ANY, "TABLE_ADD");

  /**
   * The data associated with the table event, such as the item being edited, deleted, or viewed.
   */
  private T data;
  
  /**
   * Constructs a new TableEvent with the specified event type and associated data.
   * 
   * @param type The type of the table event.
   * @param data The data associated with the event.
   */
  public TableEvent(EventType<TableEvent<?>> type, T data){
    super(type);
    this.data = data;
  }
  
  /**
   * Return the data associated with the table event.
   * 
   * @return The data associated with the event.
   */
  public T getData(){
    return data;
  }
}
