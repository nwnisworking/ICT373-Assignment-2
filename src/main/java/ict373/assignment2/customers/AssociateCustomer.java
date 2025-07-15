package ict373.assignment2.customers;

/**
 * <p><strong>AssociateCustomer class</strong></p>
 * <p>Represents an associate customer.</p>
 * <p>An associate customer is a type of customer that does not pay for subscriptions. </p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename AssociateCustomer.java
 */
public class AssociateCustomer extends Customer{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 5L;

	/**
	 * Customer that is paying for associate's subscription.
	 */
	private PayingCustomer payer;

	/**
	 * Default constructor for AssociateCustomer.
	 * Initializes the customer with default values.
	 */
	public AssociateCustomer() {
		super();
	}

	/**
	 * Constructor for AssociateCustomer
	 * @param id Unique identifier for the customer.
	 * @param name Name of the customer.
	 * @param email Email address of the customer.
	 */
	public AssociateCustomer(int id, String name, String email){
		super(id, name, email);
	}

	/**
	 * Get the paying customer.
	 * @return Paying customer for this associate
	 */
	public PayingCustomer getPayer(){
		return payer;
	}

	/**
	 * Set the Paying customer. 
	 * @param payer Customer paying for the associate.
	 */
	public void setPayer(PayingCustomer payer){
		this.payer = payer;
	}
}
