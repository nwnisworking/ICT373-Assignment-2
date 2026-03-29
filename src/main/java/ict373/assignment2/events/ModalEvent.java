package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

/**
 * <strong>ModalEvent class</strong>
 * 
 * <p>The ModalEvent class represents events related to the modal component, such as opening or closing the modal. .</p>
 * 
 * @author nwnisworking
 * @date 26/3/2026
 * @filename ModalEvent.java

 */
public class ModalEvent extends Event{
  /* The event type for any modal-related event. */
  public static final EventType<ModalEvent> ANY = new EventType<>(Event.ANY, "MODAL_EVENT");
  
  /* The event type for opening the modal. */
  public static final EventType<ModalEvent> OPEN = new EventType<>(ANY, "MODAL_OPEN");

  /* The event type for closing the modal. */
  public static final EventType<ModalEvent> CLOSE = new EventType<>(ANY, "MODAL_CLOSE");

  /* The node associated with the event, which can be used to pass content to be displayed in the modal. */
  private final Node node;
  
  /**
   * Constructs a new ModalEvent with the specified event type and node.
   * @param event The type of the event.
   * @param node The node associated with the event, which can be used to pass content to be displayed in the modal.
   */
  public ModalEvent(EventType<ModalEvent> event, Node node){
    super(event);
    
    this.node = node;
  }
  
  /**
   * Returns the node associated with the event, which can be used to pass content to be displayed in the modal.
   * @return The node associated with the event.
   */
  public Node getNode(){
    return node;
  }
}
