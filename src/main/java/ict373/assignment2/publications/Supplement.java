package ict373.assignment2.publications;
/**
 * <p><strong>Supplement class</strong></p>
 * 
 * <p> Represents a supplement to a magazine subscription.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename Supplement.java
 */
public class Supplement extends Publication{
  private static final long serialVersionUID = 2L;

	/**
	 * Default constructor for Magazine class.
	 * Initializes the magazine with default values.
	 */
	public Supplement() {
		super();
	}
	
	/**
	 * Parameterized constructor for Supplement class without magazine.
	 * @param id Unique identifier for the supplement
	 * @param name Name of the supplement
	 * @param cost Cost of the supplement
	 */
	public Supplement(int id, String name, double cost){
		super(id, name, cost, null);
	}

	/**
	 * Parameterized constructor for Supplement class without magazine.
	 * @param id Unique identifier for the supplement
	 * @param name Name of the supplement
	 * @param cost Cost of the supplement
	 * @param magazine Magazine associated with this supplement
	 */
	public Supplement(int id, String name, double cost, Magazine magazine){
		super(id, name, cost, magazine);
	}
}