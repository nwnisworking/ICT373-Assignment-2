package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

public class ModalEvent extends Event{
  public static final EventType<ModalEvent> ANY = new EventType<>(Event.ANY, "MODAL_EVENT");
  
  public static final EventType<ModalEvent> OPEN = new EventType<>(ANY, "MODAL_OPEN");
  
  public static final EventType<ModalEvent> CLOSE = new EventType<>(ANY, "MODAL_CLOSE");
  
  private final Node node;
  
  public ModalEvent(EventType<ModalEvent> event, Node node){
    super(event);
    
    this.node = node;
  }
  
  public Node getNode(){
    return node;
  }
}
