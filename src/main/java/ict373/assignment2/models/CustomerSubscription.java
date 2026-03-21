package ict373.assignment2.models;

import ict373.assignment2.models.customers.Customer;
import ict373.assignment2.models.publications.Publication;

/**
 * <strong>CustomerSubscription class</strong>
 * 
 * <p>The CustomerSubscription class represents a subscription of a customer to a publication. It contains references to both the customer and the publication, allowing access to their details.</p>
 * 
 * @author nwnisworking
 * @date 21/3/2026
 * @filename CustomerSubscription.java
 */
public class CustomerSubscription{
  /**
   * The customer associated with this subscription.
   */
  private final Customer customer;

  /**
   * The publication associated with this subscription.
   */
  private final Publication publication;

  /**
   * Constructor for CustomerSubscription.
   * 
   * @param customer The customer associated with this subscription.
   * @param publication The publication associated with this subscription.
   */
  public CustomerSubscription(Customer customer, Publication publication) {
    this.customer = customer;
    this.publication = publication;
  }

  /**
   * Get the name of the customer associated with this subscription.
   * @return The name of the customer.
   */
  public String getName(){
    return customer.getName();
  }

  /**
   * Get the cost of the publication associated with this subscription.
   * @return The cost of the publication.
   */
  public double getCost(){
    return publication.getCost();
  }

  /**
   * Get the title of the publication associated with this subscription.
   * @return The title of the publication.
   */

  public String getPublicationTitle(){
    return publication.getName();
  }
}
