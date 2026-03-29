package ict373.assignment2.services;

import ict373.assignment2.models.customers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <strong>CustomerService class</strong>
 * 
 * <p>CustomerService class manages customer data and operations.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename CustomerService.java
 * 
 * @version 1.1
 * - Let the Service class handle storing the customers in a HashMap while the CustomerService class maintains an ObservableList for UI purposes.
 * - Update the add and remove methods to ensure both the HashMap and ObservableList are updated accordingly.
 * - Delete the containsCustomer method as it is no longer necessary with the new structure.
 * - Add getObservableList method to provide access to the ObservableList of customers for UI components.
 * - Add Singleton pattern to CustomerService.
 */
public class CustomerService extends Service<Integer, Customer>{
	/**
	 * Singleton instance of CustomerService.
	 */
	private static CustomerService instance = null;
	
	/**
	 * Unique identifier for customers which increments with each new customer added.
	 */
	private int id = 0;

	/**
	 * ObservableList of customers for UI purposes. This list is kept in sync with the HashMap in the Service class.
	 */
	private transient ObservableList<Customer> customers;

	/**
	 * Default constructor for CustomerService.
	 */
	public CustomerService(){
		init();
	}

	/**
	 * Add a new customer to the service.
	 * @param customer The customer to be added.
	 */
	public void add(Customer customer){
		customer.setId(id);
		customers.add(customer);
		super.add(id++, customer);
	}

	/**
	 * Remove a customer from the service by their unique ID.
	 * @param customer_id The unique ID of the customer to be removed.
	 */
	@Override
	public void remove(Integer customer_id){
		remove(get(customer_id));
	}

	/**
	 * Remove a customer from the service.
	 * @param customer The customer to be removed.
	 */
  public void remove(Customer customer){
    if(customer == null) return;
    
    if(customer instanceof PayingCustomer){
      PayingCustomer pc = (PayingCustomer) customer;
      pc.removeAllAssociates();
		}
		else{
      AssociateCustomer ac = (AssociateCustomer) customer;
      ac.removePayer();
		}

		customers.remove(customer);
		super.remove(customer.getId());
  }
  
	/**
	 * Get a customer by their unique ID.
	 * @param customer_id The unique ID of the customer.
	 * @return The Customer object if found, null otherwise.
	 */
	public Customer get(int customer_id){
		return super.get(customer_id);
	}

	/**
	 * Get the ObservableList of customers for UI purposes.
	 * @return The ObservableList of customers.
	 */
	public ObservableList<Customer> getObservableList(){
		return customers;
	}
	
	/**
	 * Get the singleton instance of CustomerService.
	 * @return The singleton instance of CustomerService.
	 */
	public static CustomerService getInstance(){
		if(instance == null) instance = new CustomerService();
		return instance;
	}

	/**
	 * Set the singleton instance of CustomerService. This method allows for dependency injection of a custom CustomerService instance, which can be useful for testing or if there is a need to replace the default implementation with a different one.
	 * @param service The CustomerService instance to be set as the singleton instance.
	 */
	public static void setInstance(CustomerService service){
		if(service != null){
      instance = service;
      service.init();
    }
	}

	/**
	 * Initialize the Customerserivce instance and the ObservableList of customers.
	 */
	@Override
	public final void init(){
		instance = this;
		customers = FXCollections.observableArrayList(items.values());
	}
}