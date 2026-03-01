package ict373.assignment2.models.customers;

import java.util.ArrayList;

import ict373.assignment2.models.payments.Method;

/**
 * <strong>PayingCustomer class</strong>
 * <p>PayingCustomer class represents a customer who pays for associated AssociateCustomers. It contains information about the payment method and manages the list of associated customers.</p>
 * 
 * <p>Assumptions: </p>
 * <p> - A paying customer can have multiple associate customers.</p>
 * <p> - The payment method is represented by the Method class which can be of various types such as credit card and direct debit.</p>
 * <p> - The paying customer can add or remove associate customers.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename PayingCustomer.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 */
public class PayingCustomer extends Customer{
  /**
   * The payment method used by this PayingCustomer.
   */
  private Method method;

  /**
   * List of AssociateCustomers associated with this PayingCustomer.
   */
  private ArrayList<AssociateCustomer> associates = new ArrayList<>();

  /**
   * Set the payment method for this PayingCustomer.
   * @param method The payment method to be set.
   */
  public void setMethod(Method method){
    this.method = method;
  }

  /**
   * Get the payment method for this PayingCustomer.
   * @return The payment method used by this PayingCustomer.
   */
  public Method getMethod(){
    return method;
  }

  /**
   * Add an AssociateCustomer to this PayingCustomer.
   * @param customer The AssociateCustomer to be added.
   */
  public void addAssociate(AssociateCustomer customer){
    // Do not add customer if they already have a payer.
    if(customer.getPayer() != null) return;

    customer.setPayer(this);
    associates.add(customer);
  }

  /**
   * Remove an AssociateCustomer from this PayingCustomer.
   * @param customer The AssociateCustomer to be removed.
   */
  public void removeAssociate(AssociateCustomer customer){
    // Customer is not associated with this PayingCustomer.
    if(!associates.contains(customer)) return;

    associates.remove(customer);
    customer.setPayer(null);
  }

  /**
   * Remove all AssociateCustomers from this PayingCustomer.
   */
  public void removeAllAssociates(){
    associates.forEach(e->e.setPayer(null));
    associates.clear();
  }

  /**
   * Get a list of all AssociateCustomers associated with this PayingCustomer.
   * @return A new list containing all AssociateCustomers associated with this PayingCustomer.
   */
  public ArrayList<AssociateCustomer> getAssociates(){
    return new ArrayList<>(associates);
  }
}
