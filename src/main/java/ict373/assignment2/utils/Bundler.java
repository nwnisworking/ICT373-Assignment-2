package ict373.assignment2.utils;

import ict373.assignment2.customers.Customer;
import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.models.PublicationModel;
import ict373.assignment2.publications.Publication;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p><strong>Bundler class</strong></p>
 * 
 * <p>Utility class for bundling publications and customers together for serialization.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename Bundler.java
 */
public class Bundler implements Serializable{
  /**
   * Serial version UID for serialization.
   */
  private static final long serialVersionUID = 99L;
  
  /**
   * List of publications.
   */
  private final ArrayList<Publication> publications;
  
  /**
   * List of customers.
   */
  private final ArrayList<Customer> customers;

  /**
   * Default constructor for Bundler.
   * Initializes the publications and customers lists.
   * @param publications The PublicationModel instance containing publications.
   * @param customers The CustomerModel instance containing customers.
   */
  public Bundler(PublicationModel publications, CustomerModel customers){
    this.publications = new ArrayList<>(publications.getData());
    this.customers = new ArrayList<>(customers.getData());
  }
  
  /**
   * Update the publication model with the bundled publications.
   * @param pm The PublicationModel instance to update.
   */
  public void updatePublication(PublicationModel pm){
    pm.data.clear();
    pm.data.addAll(publications);
  }
  
  /**
   * Update the customer model with the bundled customers.
   * @param cm The CustomerModel instance to update.
   */
  public void updateCustomer(CustomerModel cm){
    cm.data.clear();
    cm.data.addAll(customers);
  }
}
