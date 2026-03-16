package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * <strong>PageEvent class</strong>
 * 
 * <p>The PageEvent class represents events related to page navigation and actions within the application, such as viewing, adding, editing, or going back from a page.</p>
 * 
 */
public class PageEvent<T> extends Event{
  /**
   * The base event type for all page events.
   */
  public static final EventType<PageEvent<?>> ANY = new EventType<>(Event.ANY, "PAGE_EVENT");

  /**
   * Event type for viewing a page.
   */
  public static final EventType<PageEvent<?>> VIEW = new EventType<>(ANY, "PAGE_VIEW");

  /**
   * Event type for adding a new item on a page.
   */
  public static final EventType<PageEvent<?>> ADD = new EventType<>(ANY, "PAGE_ADD");

  /**
   * Event type for editing an existing item on a page.
   */
  public static final EventType<PageEvent<?>> EDIT = new EventType<>(ANY, "PAGE_EDIT");

  /**
   * Event type for going back from a page.
   */
  public static final EventType<PageEvent<?>> BACK = new EventType<>(ANY, "PAGE_BACK");

  /**
   * The data associated with the page event, which can be used to pass relevant information about the event.
   */
  private final T data;

  /**
   * Constructs a new PageEvent with the specified event type and associated data.
   *
   * @param type The type of the page event (e.g., VIEW, ADD, EDIT, BACK).
   * @param data The data associated with the page event.
   */
  public PageEvent(EventType<PageEvent<?>> type, T data) {
    super(type);
    this.data = data;
  }

  /**
   * Return the data associated with the page event.
   *
   * @return The data associated with the page event.
   */
  public T getData(){
    return data;
  }
}
