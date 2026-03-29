package ict373.assignment2.events;

import javafx.event.Event;
import javafx.event.EventType;
import ict373.assignment2.models.customers.Customer;

/**
 * <strong>CustomerEvent class</strong>
 * 
 * <p>The CustomerEvent class represents events related to customer actions, such as creating, editing, or deleting a customer. </p>
 * 
 * @author nwnisworking
 * @date 26/3/2026
 * @filename CustomerEvent.java
 */
public class CustomerEvent extends Event{
  /**
   * The event type for any customer-related event.
   */
  public static final EventType<CustomerEvent> ANY = new EventType<>(Event.ANY, "CUSTOMER_EVENT");
  
  /**
   * The event type for when a customer is created.
   */
  public static final EventType<CustomerEvent> CUSTOMER_CREATED = new EventType<>(ANY, "CUSTOMER_CREATED");
  
  /**
   * The event type for when a customer is deleted.
   */
  public static final EventType<CustomerEvent> CUSTOMER_DELETED = new EventType<>(ANY, "CUSTOMER_DELETED");
  
  /**
   * The event type for when a customer is edited.
   */
  public static final EventType<CustomerEvent> CUSTOMER_EDITED = new EventType<>(ANY, "CUSTOMER_EDITED");
  
  /**
   * The customer associated with the event.
   */
  private final Customer customer;
  
  /**
   * Constructs a new CustomerEvent with the specified event type and customer.
   * 
   * @param event The type of the event.
   * @param customer The customer associated with the event.
   */
  public CustomerEvent(EventType<CustomerEvent> event, Customer customer){
    super(event);
    this.customer = customer;
  }
  
  /**
   * Return the customer associated with the event.
   * 
   * @return The customer associated with the event.
   */
  public Customer getCustomer(){
    return customer;
  }
}
