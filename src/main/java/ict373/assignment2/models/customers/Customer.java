package ict373.assignment2.models.customers;

import ict373.assignment2.models.Address;
import java.io.Serializable;

/**
 * <strong>Customer class</strong>
 * <p>Abstract base class representing a customer.</p>
 * 
 * <p>Assumptions: </p>
 * <p> - A customer has a unique ID to identify them.</p>
 * <p> - A customer has a name, email, and address.</p>
 * <p> - There are two types of customers: PayingCustomer and AssociateCustomer.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Customer.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 * - Remove Tabular interface implementation as it is no longer necessary with the new structure.
 * - Add getType method to return the type of customer as a string for display purposes.
 * - Add serialVersionUID for serialization compatibility.
 */
public abstract class Customer implements Serializable{
    private static final long serialVersionUID = 2L;

    /**
     * Unique identifier for the customer.
     */
    protected int id;
  
    /**
     * Name of the customer.
     */
    protected String name;

    /**
     * Email of the customer.
     */
    protected String email;

    /**
     * Address of the customer.
     */
    protected Address address;

    /**
     * Set the unique identifier for the customer.
     * @param id The unique identifier to be set.
     */
    public void setId(int id){
      this.id = id;
    }

    /**
     * Get the unique identifier of the customer.
     * @return The unique identifier of the customer.
     */
    public int getId(){
      return id;
    }

    /**
     * Set the name of the customer.
     * @param name The name to be set.
     */
    public void setName(String name){
      this.name = name;
    }

    /**
     * Get the name of the customer.
     * @return The name of the customer.
     */
    public String getName(){
      return name;
    }

    /**
     * Set the email of the customer.
     * @param email The email to be set.
     */
    public void setEmail(String email){
      this.email = email;
    }

    /**
     * Get the email of the customer.
     * @return The email of the customer.
     */
    public String getEmail(){
      return email;
    }

    /**
     * Set the address of the customer.
     * @param address The address to be set.
     */
    public void setAddress(Address address){
      this.address = address;
    }

    /**
     * Get the address of the customer.
     * @return The address of the customer.
     */
    public Address getAddress(){
      return address;
    }

    /**
     * Get the type of customer as a string for display purposes.
     * @return The type of customer ("Paying" or "Associate").
     */
    public String getType(){
      return this instanceof PayingCustomer ? "Paying" : "Associate";
    }
    
    /**
     * Returns a string representation of the customer, which is the customer's name.
     * @return The name of the customer as a string.
     */
    @Override
    public String toString(){
      return name;
    }
}
