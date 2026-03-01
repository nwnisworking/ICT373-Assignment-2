package ict373.assignment2.models;

import java.io.Serializable;

/**
 * <strong>Address class</strong>
 * 
 * <p>Address class represents a physical address. It uses Singapore address format.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Address.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 * - Add serialVersionUID for serialization purposes.
 */
public class Address implements Serializable{
  private static final long serialVersionUID = 3L;

  /**
   * The street name of the address.
   */
  private String street_name;
  
  /**
   * The block number of the address.
   */
  private int block_number;

  /**
   * The postal code of the address.
   */
  private int postal_code;

  /**
   * The unit number of the address.
   */
  private String unit_number;

  /**
   * Set the street name.
   * @param street_name The street name to be set.
   */
  public void setStreetName(String street_name){
    this.street_name = street_name;
  }

  /**
   * Get the street name.
   * @return The street name.
   */
  public String getStreetName(){
    return street_name;
  }

  /**
   * Set the block number.
   * @param block_number The block number to be set.
   */
  public void setBlockNumber(int block_number){
    this.block_number = block_number;
  }

  /**
   * Get the block number.
   * @return The block number.
   */
  public int getBlockNumber(){
    return block_number;
  }

  /**
   * Set the postal code.
   * @param postal_code The postal code to be set.
   */
  public void setPostalCode(int postal_code){
    this.postal_code = postal_code;
  }

  /**
   * Get the postal code.
   * @return The postal code.
   */
  public int getPostalCode(){
    return postal_code;
  }

  /**
   * Set the unit number.
   * @param unit_number The unit number to be set.
   */
  public void setUnitNumber(String unit_number){
    this.unit_number = unit_number;
  }

  /**
   * Get the unit number.
   * @return The unit number.
   */
  public String getUnitNumber(){
    return unit_number;
  }
}
