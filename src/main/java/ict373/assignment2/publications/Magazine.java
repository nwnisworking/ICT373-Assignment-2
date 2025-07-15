package ict373.assignment2.publications;

/**
 * <p><strong>Magazine class</strong></p>
 * 
 * <p>Represents a magazine.</p>
 * <p>A paying customer is a type of customer that can pay subscriptions for themselves or for associate customers.
 * They can also have a payment method associated with them, such as a credit card or debit card.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename Magazine.java
 */
public class Magazine extends Publication{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 3L;

	/**
	 * Default constructor for Magazine class.
	 * Initializes the magazine with default values.
	 */
	public Magazine() {
		super();
	}
	
	/**
	 * Constructor for Magazine class.
	 * @param id Unique identifier for the magazine
	 * @param name Name of the magazine
	 * @param cost Cost of the magazine
	 */
	public Magazine(int id, String name, double cost) {
		super(id, name, cost, null);
	}
}