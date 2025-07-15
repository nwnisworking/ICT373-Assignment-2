package ict373.assignment2.utils;

import java.io.Serializable;

/**
 * <p><strong>Address class</strong></p>
 * 
 * <p>Represents an address with street number, postal code, name, and suburb.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename Address.java
 */
public class Address implements Serializable{
  /**
   * Serial version UID for serialization.
   */
  private static final long serialVersionUID = 9L;

  /**
   * Street number of the address.
   */
  private int street_number;
  
  /**
   * Postal code of the address.
   */
  private int postal_code;
  
  /**
   * Name associated with the address.
   */
  private String name;
  
  /**
   * Suburb of the address.
   */
  private String suburb;
  
  /**
   * Sets the street number of the address.
   * @param street_number The street number to set.
   */
  public void setStreetNumber(int street_number){
    this.street_number = street_number;
  }
  
  /**
   * Get the street number of the address.
   * @return The street number of the address.
   */
  public int getStreetNumber(){
    return street_number;
  }
  
  /**
   * Sets the postal code of the address.
   * @param postal_code The postal code to set.
   * @return The postal code of the address.
   */
  public void setPostalCode(int postal_code){
    this.postal_code = postal_code;
  }
  
  /**
   * Get the postal code of the address.
   * @return The postal code of the address.
   */
  public int getPostalCode(){
    return postal_code;
  }
  
  /**
   * Sets the name associated with the address.
   * @param name The name to set.
   */
  public void setName(String name){
    this.name = name;
  }
  
  /**
   * Get the name associated with the address.
   * @return The name associated with the address.
   */
  public String getName(){
    return name;
  }
  
  /**
   * Sets the suburb of the address.
   * @param suburb The suburb to set.
   */
  public void setSuburb(String suburb){
    this.suburb = suburb;
  }
  
  /**
   * Get the suburb of the address.
   * @return The suburb of the address.
   */
  public String getSuburb(){
    return suburb;
  }
}
