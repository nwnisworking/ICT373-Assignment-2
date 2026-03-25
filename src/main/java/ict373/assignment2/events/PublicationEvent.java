package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;
import ict373.assignment2.models.publications.Publication;

/**
 * <strong>PublicationEvent class</strong>
 * 
 * <p>The PublicationEvent class represents events related to publication actions, such as creating, editing, or deleting a publication. </p>
 */
public class PublicationEvent extends Event{
  /**
   * The event type for any publication-related event.
   */
  public static final EventType<PublicationEvent> ANY = new EventType<>(Event.ANY, "PUBLICATION_EVENT");
  
  /**
   * The event type for when a publication is created.
   */
  public static final EventType<PublicationEvent> PUBLICATION_CREATED = new EventType<>(ANY, "PUBLICATION_CREATED");
  
  /**
   * The event type for when a publication is deleted.
   */
  public static final EventType<PublicationEvent> PUBLICATION_DELETED = new EventType<>(ANY, "PUBLICATION_DELETED");
  
  /**
   * The event type for when a publication is edited.
   */
  public static final EventType<PublicationEvent> PUBLICATION_EDITED = new EventType<>(ANY, "PUBLICATION_EDITED");
  
  /**
   * The publication associated with the event.
   */
  private final Publication publication;
  
  /**
   * Constructs a new PublicationEvent with the specified event type and publication.
   * 
   * @param event The type of the event.
   * @param publication The publication associated with the event.
   */
  public PublicationEvent(EventType<PublicationEvent> event, Publication publication){
    super(event);
    this.publication = publication;
  }
  
  /**
   * Return the publication associated with the event.
   * 
   * @return The publication associated with the event.
   */
  public Publication getPublication(){
    return publication;
  }
}
