package ict373.assignment2.publications;

import ict373.assignment2.utils.Identity;
import java.io.Serializable;

/**
 * <p><strong>Publication class</strong></p>
 * 
 * <p>Represents a subscription to a magazine or a supplement.</p>
 * <p>This class is abstract and should be extended by subscription types: magazine or supplement.</p>
 * 
 * <p>Assumptions:</p>
 * <p>- Publication can either be a magazine or a supplement</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename Subscription.java
 */
public abstract class Publication implements Serializable, Identity{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 1L;
	/**
	 * Unique identifier for a subscription.
	 */
	private int id;

	/**
	 * If this subscription is a supplement to a magazine, magazine will be set.
	 * Otherwise, it results in null
	 */
	private Magazine magazine;

	/**
	 * The name of the subscription.
	 */
	private String name;

	/**
	 * The cost of the subscription.
	 */
	private double cost;

	/**
	 * Default constructor for creating a subscription.
	 */
	public Publication(){
		id = 0;
		magazine = null;
		cost = 0;
		name = "";
	}

	/**
	 * Constructor for creating a subscription.
	 * 
	 * @param name The name of the subscription.
	 * @param cost The cost of the subscription.
	 * @param id The unique identifier for the subscription.
	 * @param magazine Magazine of the subscription.
	 */
	public Publication(int id, String name, double cost, Magazine magazine){
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.magazine = magazine == null ? null : magazine;
	}

	/**
	 * Check if this subscription is a supplement.
	 * @return true if this subscription is a supplement, false if it is a magazine.
	 */
	public boolean isSupplement(){
		return this instanceof Supplement;
	}

	/**
	 * Check if this subscription is a magazine.
	 * @return true if this subscription is a magazine, false if it is a supplement.
	 */
	public boolean isMagazine(){
		return this instanceof Magazine;
	}


	/**
	 * Get the unique identifier for this subscription.
	 * @return the unique identifier for this subscription.
	 */
	public int getId(){
		return id;
	}

	/**
	 * Set the unique identifier for this subscription.
	 * @param id the unique identifier.
	 */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * Get the magazine.
	 * @return the magazine attached to this subscription.
	 */
	public Magazine getMagazine(){
		return magazine;
	}

	/**
	 * Set the magazine.
	 * @param magazine magazine object attached to this subscription.
	 */
	public void setMagazine(Magazine magazine){
		if(magazine == null)
			throw new RuntimeException("Magazine cannot be null");

		this.magazine = magazine;
	}

	/**
	 * Get the name of the subscription.
	 * @return the name of the subscription.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Set the name of the subscription
	 * @param name name of the subscription
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Get the cost of the subscription.
	 * @return the cost of the subscription.
	 */
	public double getCost(){
		return cost;
	}

	/**
	 * Set the cost of the subscription.
	 * @param cost the cost of the subscription 
	 */
	public void setCost(double cost){
		this.cost = cost;
	}

	/**
	 * Get the type of subscription.
	 * @return the type of subscription as a string.
	 */
  public String getSubscriptionType(){
    return getClass().getSimpleName();
  }

	/**
	 * Get the representation of the publication as a string.
	 * @return A string representation of the publication.
	 */
  @Override
  public String toString(){
    return getName()+ "";
  }
  
	/**
	 * Check if two publications are equal based on their unique identifier.
	 * @param obj The object to compare with.
	 */
  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }

    if(obj == null || getClass() != obj.getClass()){
       return false;
    }

    Publication other = (Publication) obj;
    return this.id == other.id;
  }

	/**
	 * Get the hash code for the publication based on its unique identifier.
	 * @return The hash code for the publication.
	 */
  @Override
  public int hashCode() {
    return Integer.hashCode(id);
  }
}