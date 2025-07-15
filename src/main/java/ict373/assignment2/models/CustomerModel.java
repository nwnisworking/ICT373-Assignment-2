package ict373.assignment2.models;

import ict373.assignment2.publications.Publication;
import ict373.assignment2.customers.Customer;
import ict373.assignment2.utils.BaseModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p><strong>CustomerModel class</strong></p>
 * 
 * <p>Model for managing customers in the system.</p>
 * 
 * <p>This class uses singleton pattern to ensure only one instance of CustomerModel exists.</p>
 */
public class CustomerModel extends BaseModel<Customer>{
  /**
   * Stores an instance of PublicationModel.
   */
  private static CustomerModel instance;

  /**
   * Default constructor for MagazineModel.
   * @throws FileNotFoundException 
   */
  private CustomerModel() throws FileNotFoundException {
    super("customer.dat");
    
    PublicationModel p_model = PublicationModel.getInstance();

    // Refresh the publications for each customer so that modification to publications 
    // are reflected in the customer subscriptions.
    for(Customer cust : getData()){
      List<Publication> refreshed_data = cust
              .getPublications()
              .stream()
              .map(e->p_model.getId(e.getId()))
              .filter(e->e != null)
              .collect(Collectors.toList());
      
      cust.setPublications(new ArrayList<>(refreshed_data));
    }
  }
  
  /**
   * Get the instance of PublicationModel.
   * @return An instance of magazine model or a new PublicationModel.
   */
  public static CustomerModel getInstance(){
    if(instance == null) {
      try {
        instance = new CustomerModel();
      } 
      catch(FileNotFoundException ex){
        throw new RuntimeException("Unable to load Customer.dat", ex);
      }
    }

    return instance;
  }
}

