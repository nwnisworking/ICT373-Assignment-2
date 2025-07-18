package ict373.assignment2.customers;

import ict373.assignment2.publications.Publication;
import ict373.assignment2.utils.Address;
import ict373.assignment2.utils.Identity;
import java.io.Serializable;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <p><strong>Customer class</strong></p>
 * 
 * <p>Represents a customer in the system.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename Customer.java
 */
public abstract class Customer implements Identity, Serializable{
  /**
   * Serial version UID for serialization.
   */
  private static final long serialVersionUID = 4L;

  /**
   * Unique identifier for the customer.
   */
  private int id;

  /**
   * Name of the customer.
   */
  private String name;

  /**
   * Customer's email address.
   */
  private String email;

  /**
   * Customer's address.
   */
  private Address address;
  
  /**
   * List of available publications. 
   */
  private ArrayList<Publication> publications = new ArrayList<>();

  /**
   * Default constructor for Customer class
   */
  public Customer(){
    this.id = 0;
    this.name = "";
    this.email = "";
    this.address = new Address();
  }

  /**
   * Constructor for Customer class.
   * @param id Unique identifier for the customer.
   * @param name Name of the customer.
   * @param email Email address of the customer.
   */
  public Customer(int id, String name, String email){
    this.id = id;
    this.name = name;
    this.email = email;
  }

  /**
   * Get the unique identifier of the customer.
   * @return Unique identifier of the customer.
   */
  public int getId(){
    return id;
  }

  /**
   * Set the unique identifier of the customer.
   * @param id A unique identifier.
   */
  public void setId(int id){
    this.id = id;
  }

  /**
   * Set the name of the customer.
   * @param name Name of the customer.
   */
  public void setName(String name){
    this.name = name;
  }
    
  /**
   * Get the name of the customer.
   * @return Name of the customer.
   */
  public String getName(){
    return name;
  }
    
  /**
   * Set customer's email address.
   * @param email Email address of the customer.
   */
  public void setEmail(String email){
    this.email = email;
  }

  /**
   * Get the email of the customer.
   * @return Email address of the customer.
   */
  public String getEmail(){
    return email;
  }
  
  /**
   * Set the address of the customer.
   * @param address Address of the customer.
   */
  public void setAddress(Address address){
    this.address = address;
  }
  
  /**
   * Get the address of the customer.
   * @return Address of the customer.
   */
  public Address getAddress(){
    return address;
  }

  /**
   * Get the list of subscriptions for the customer.
   * @return ArrayList of Subscription objects.
   */
  public ObservableList<Publication> getObservablePublications(){
    return FXCollections.observableList(publications);
  }
  
  public void setPublications(ArrayList<Publication> pubs){
    publications = pubs;
  }
  
  public ArrayList<Publication> getPublications(){
    return publications;
  }

  /**
   * Remove a subscription from the list of subscriptions.
   * 
   * If the subscription is a magazine, supplements associated with the magazine will be deleted alongside.
   * @param publication Subscription to be removed.
   */
  public void removeSubscription(Publication publication){
    publications.remove(publication);

    publications.removeIf(e->!publications.contains(publication) && e.isSupplement() && e.getMagazine().getId() == publication.getId());
  }

  /**
   * Get the total cost of all the subscriptions.
   * @return Cost of subscriptions.
   */
  public double getTotalCost(){
    double total = 0;

    for(Publication pub : publications){
      total+= pub.getCost();
    }

    return total;
  }
  
  /**
   * Get the type of customer.
   * @return Type of customer as a string.
   */
  public String getCustomerType(){
    return getClass().getSimpleName();
  }
  
  /**
   * Returns a string representation of the customer.
   * @return String representation of the customer.
   */
  public String toString(){
    return name;
  }
}