package ict373.assignment2.utils;

import ict373.assignment2.customers.Customer;
import ict373.assignment2.publications.Publication;

/**
 * <p><strong>CustomerSubscription class</strong></p>
 * 
 * <p>Represents a subscription of a customer to a publication.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename CustomerSubscription.java
 */
public class CustomerSubscription{
  /**
   * Stores the customer who has the subscription.
   */
  private final Customer customer;
  
  /**
   * Stores the publication that the customer is subscribed to.
   */
  private final Publication subscription;
  
  /**
   * Default constructor for CustomerSubscription.
   * 
   * @param customer The customer who has the subscription.
   * @param subscription The publication that the customer is subscribed to.
   */
  public CustomerSubscription(Customer customer, Publication subscription){
    this.customer = customer;
    this.subscription = subscription;
  }

  /**
   * Get the customer who has the subscription.
   * 
   * @return The customer with the subscription.
   */
  public Customer getCustomer(){
    return customer;
  }
  
  /**
   * Get the publication that the customer is subscribed to.
   * 
   * @return The publication that the customer is subscribed to.
   */
  public Publication getSubscription(){
    return subscription;
  }
  
  /**
   * Get the cost of the subscription.
   * 
   * @return The cost of the subscription.
   */
  public double getCost(){
    return subscription.getCost();
  }

  /**
   * Get the type of subscription.
   * 
   * @return The type of subscription (e.g., magazine, supplement).
   */
  public String getSubscriptionType(){
    return subscription.getSubscriptionType();
  }
}
