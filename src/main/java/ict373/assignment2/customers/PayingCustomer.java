package ict373.assignment2.customers;

import java.util.ArrayList;

import ict373.assignment2.payment.Method;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * <p><strong>PayingCustomer class</strong></p>
 * 
 * <p>Represents a paying customer in the system.</p>
 * <p>A paying customer is a type of customer that can pay subscriptions for themselves or for associate customers.</p>
 * <p>They can also have a payment method associated with them, such as a credit card or debit card.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename PayingCustomer.java
 */
public class PayingCustomer extends Customer{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 6L;

  /**
   * Customer's default payment method. It can be a credit card, debit card, etc.
   */
  private Method payment_method;

	/**
	 * List of associates that is tied to the paying customer
	 */
	private ArrayList<AssociateCustomer> associates = new ArrayList<>();

	/** Initializes the customer with default values.
	 */
	public PayingCustomer() {
		super();
	}

	/**
	 * Constructor for PayingCustomer class without payment method.
	 * @param id Unique identifier for the customer.
	 * @param name Name of the customer.
	 * @param email Email address of the customer.
	 */
	public PayingCustomer(int id, String name, String email){
		super(id, name, email);
	}

	/**
	 * Constructor for PayingCustomer class with payment method.
	 * @param id Unique identifier for the customer.
	 * @param name Name of the customer.
	 * @param email Email address of the customer.
	 * @param payment_method Payment method which the customer is using.
	 */
	public PayingCustomer(int id, String name, String email, Method payment_Method){
		super(id, name, email);
		this.payment_method = payment_Method;
	}

	/**
	 * Add associate to the paying customer.
	 * @param customer An associate customer.
	 * @return True if associate does not have a payer, otherwise false.
	 */
	public boolean addAssociate(AssociateCustomer customer){
		if(customer.getPayer() != null) 
			return false;

		customer.setPayer(this);
		associates.add(customer);

		return true;
	}

	/**
	 * Remove customer from the list of associates.
	 * @param customer An associate customer.
	 * @return True if associate customer exists in the associate list, otherwise false.
	 */
	public boolean removeAssociate(AssociateCustomer customer){
		if(!associates.contains(customer))
			return false;

		associates.remove(customer);
		customer.setPayer(null);

		return true;
	}

	/**
	 * Remove all the associates. 
	 */
	public void removeAssociate(){
		for(AssociateCustomer cust : associates){
			cust.setPayer(null);
		}
		
		associates.removeAll(associates);
	}

	/**
   * Set the payment method used by the paying customer
   * @param payment_method Payment method to be set for the customer
   */
  public void setPaymentMethod(Method payment_method){
    this.payment_method = payment_method;
  }

	/**
   * Get the payment method used by the customer
   * @return Payment method used by the customer
   */
  public Method getPaymentMethod(){
    return payment_method;
  }
  
	/**
	 * Get the associates as an observable list.
	 * @return An observable list of associate customers.
	 */
  public ObservableList<AssociateCustomer> getObservableAssociates(){
    return FXCollections.observableList(associates);
  }

	/**
	 * Get the associates as an array list.
	 * @return An array list of associate customers.
	 */
	public ArrayList<AssociateCustomer> getAssociates(){
		return associates;
	}

	@Override
	/**
	 * Get the total cost of the subscription inclusive of the associates' subscriptions.
	 */
	public double getTotalCost(){
		double total = super.getTotalCost();

		for(AssociateCustomer associate : associates)
			total+= associate.getTotalCost();

		return total;
	}

	/**
	 * Get the size of the associates
	 * @return Size of the associates
	 */
	public int getAssociateSize(){
		return associates.size();
	}
}