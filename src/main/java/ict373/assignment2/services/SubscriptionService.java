package ict373.assignment2.services;

import java.util.ArrayList;

import ict373.assignment2.models.customers.Customer;
import ict373.assignment2.models.publications.*;

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
	 * Remove subscriptions for a customer from a publication.
	 * @param customer The customer who is unsubscribing.
	 */
	@Override
	public void remove(Customer customer){
		super.remove(customer);
	}

	/**
	 * Remove a subscription for a customer from a publication.
	 * @param customer The customer who is unsubscribing.
	 * @param publication The publication from which the customer is unsubscribing.
	 */
  public void remove(Customer customer, Publication publication){
    ArrayList<Publication> subscriptions = super.get(customer);
    
    if(publication instanceof Supplement s){
      subscriptions.remove(s);
    }
    else if(publication instanceof Magazine m){
      subscriptions.remove(m);
      
      if(!subscriptions.contains(m))
        subscriptions.removeIf(e -> e instanceof Supplement s && m.equals(s.getMagazine()));
    }
  }
  
	/**
	 * Remove a subscription from all customers for a publication.
	 * @param publication The publication for which all subscriptions are to be removed.
	 */
  public void remove(Publication publication){
    for(ArrayList<Publication> subscriptions : items.values()){
      if(subscriptions.contains(publication)){
        if(publication instanceof Supplement s){
          subscriptions.remove(s);
        }
        else if(publication instanceof Magazine m){
          subscriptions.remove(m);
          
          if(!subscriptions.contains(m)){
            subscriptions.removeIf(e -> e instanceof Supplement s && m.equals(s.getMagazine()));
          }
        }
      }
    }
  }

	/**
	 * Remove all subscriptions for a publication. This method is used when a publication is deleted,
	 * @param publication The publication for which all subscriptions are to be removed.
	 */
  public void removeAll(Publication publication){
    for(ArrayList<Publication> subscriptions : items.values()){
      if(subscriptions.contains(publication)){
        if(publication instanceof Supplement s){
          subscriptions.remove(s);
        }
        else if(publication instanceof Magazine m){
          subscriptions.removeIf(e -> e instanceof Supplement s && m.equals(s.getMagazine()) || e.equals(m));
        }
      }
    }
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

	public static void setInstance(SubscriptionService service){
		if(service != null){
      instance = service;
      service.init();
    }
	}

	/**
	 * Initialize the SubscriptionService instance.
	 */
	@Override
	public final void init(){
		instance = this;
	}
}
