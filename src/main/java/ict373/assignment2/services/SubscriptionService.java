package ict373.assignment2.services;

import java.util.ArrayList;

import ict373.assignment2.models.customers.Customer;
import ict373.assignment2.models.publications.Publication;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * <strong>SubscriptionService class</strong>
 * 
 * <p>Service class for managing subscriptions, which are associations between customers and publications.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename SubscriptionService.java
 * 
 * @version 1.0
 */
public class SubscriptionService extends Service<Customer, ArrayList<Publication>>{
	/**
	 * Singleton instance of SubscriptionService.
	 */
	private static SubscriptionService instance = null;

	/**
	 * Default constructor for SubscriptionService.
	 */
	public SubscriptionService(){
		init();
	}

	/**
	 * Add a subscription for a customer to a publication.
	 * @param customer The customer who is subscribing.
	 * @param publication The publication to which the customer is subscribing.
	 */
	public void add(Customer customer, Publication publication){
		ArrayList<Publication> publications = super.get(customer);

		if(publications == null){
			publications = new ArrayList<>();
			super.add(customer, publications);
		}

		publications.add(publication);
	}

	/**
	 * Remove a subscription for a customer from a publication.
	 * @param customer The customer who is unsubscribing.
	 * @param publication The publication from which the customer is unsubscribing.
	 */
	@Override
	public void remove(Customer customer){
		super.remove(customer);
	}

	/**
	 * Get the list of publications to which a customer is subscribed.
	 * @param customer The customer whose subscriptions are being retrieved.
	 * @return An ArrayList of publications to which the customer is subscribed, or null if the customer has no subscriptions.
	 */
	public ArrayList<Publication> get(Customer customer){
		return super.get(customer);
	}
	
	/**
	 * Get the singleton instance of SubscriptionService.
	 * @return The singleton instance of SubscriptionService.
	 */
	public static SubscriptionService getInstance(){
		return instance == null ? new SubscriptionService() : instance;
	}

	/**
	 * Initialize the SubscriptionService instance.
	 */
	@Override
	public final void init(){
		instance = this;
	}
	
	/**
	 * Read the SubscriptionService instance from an ObjectInputStream.
	 */
	@Override
	public void read(ObjectInputStream input){
		try{
			instance = (SubscriptionService) input.readObject();
		}
		catch(ClassNotFoundException | IOException ex){}
	}
}
