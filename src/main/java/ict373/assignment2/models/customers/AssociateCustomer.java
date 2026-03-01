package ict373.assignment2.models.customers;

/**
 * <strong>AssociateCustomer class</strong>
 * 
 * <p>AssociateCustomer represents a customer who is associated with a PayingCustomer.</p>
 * 
 * <p>Assumptions: </p>
 * <p> - An associate customer may or may not have a payer assigned.</p>
 * <p> - The payer must be a paying customer.</p>
 * <p> - An associate customer can only have one payer at a time.</p>
 * <p> - Associate without a payer cannot manage subscriptions.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename AssociateCustomer.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 */
public class AssociateCustomer extends Customer{
  /**
   * The PayingCustomer who is the payer for this AssociateCustomer.
   */
  private PayingCustomer payer = null;

  /**
   * Set the payer for this AssociateCustomer.
   * @param payer The PayingCustomer to be set as the payer.
   */
  public void setPayer(PayingCustomer payer){
    this.payer = payer;
  }

  /**
   * Get the payer for this AssociateCustomer.
   * @return The PayingCustomer who is the payer.
   */
  public PayingCustomer getPayer(){
    return payer;
  }

  /**
   * Remove the payer for this AssociateCustomer.
   */
  public void removePayer(){
    // Only remove if there is a payer assigned.
    if(payer == null) return;

    payer.removeAssociate(this);
  }
}
