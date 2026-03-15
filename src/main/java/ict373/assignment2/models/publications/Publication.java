package ict373.assignment2.models.publications;

import java.io.Serializable;

/**
 * <strong>Publication class</strong>
 * <p>Publication class represents a generic publication, which can be a Magazine or a Supplement.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Publication.java
 * 
 * @version 1.1
 * - Repurpose toString method to only return the name of the publication.
 * - Remove Tabular interface implementation as it is no longer necessary with the new structure.
 * - Add serialVersionUID for serialization purposes.
 */
public abstract class Publication implements Serializable{
  private static final long serialVersionUID = 5L;

  /**
   * The unique identifier for the publication.
   */
  private int id;

  /**
   * The name of the publication.
   */
  private String name;

  /**
   * The cost of the publication.
   */
  private double cost;

  /**
   * Default constructor for Publication.
   */
  public Publication(){}

  /**
   * Parameterized constructor for Publication.
   * @param id The unique identifier for the publication.
   * @param name The name of the publication.
   * @param cost The cost of the publication.
   */
  public Publication(int id, String name, double cost){
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  /**
   * Set the unique identifier for the publication.
   * @param id The unique identifier to be set.
   */
  public void setId(int id){
    this.id = id;
  }

  /**
   * Get the unique identifier for the publication.
   * @return The unique identifier.
   */
  public int getId(){
    return id;
  }

  /**
   * Set the name of the publication.
   * @param name The name to be set.
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Get the name of the publication.
   * @return The name.
   */
  public String getName(){
    return name;
  }

  /**
   * Set the cost of the publication.
   * @param cost The cost to be set.
   */
  public void setCost(double cost){
    this.cost = cost;
  }

  /**
   * Get the cost of the publication.
   * @return The cost.
   */
  public double getCost(){
    return cost;
  }
  
  public String getType(){
    return getClass().getSimpleName();
  }

  /**
   * Return the name of the publication for display purposes.
   * @return The name of the publication.
   */
  public String toString(){
    return name;
  }
}
