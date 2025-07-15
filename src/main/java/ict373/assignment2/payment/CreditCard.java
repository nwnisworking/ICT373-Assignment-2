package ict373.assignment2.payment;
/**
 * <p><strong>Credit Card class</strong></p>
 * <p>Represents a credit card payment method.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename CreditCard.java
 */
public class CreditCard implements Method{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 7L;

	/**
	 * The credit card number.
	 */
	private String card_number;
	
	/**
	 * The expiry date of the credit card in the format "MM/YY".
	 */
	private String expiry_date;

	/**
	 * Default constructor for creating a CreditCard instance.
	 */
	public CreditCard(){
		card_number = "";
		expiry_date = "";
	}

	/**
	 * Constructor to create a CreditCard instance.
	 * @param card_number The credit card number.
	 * @param expiry_date The expiry date of the credit card in the format "MM/YY".
	 */
	public CreditCard(String card_number, String expiry_date){
		this.card_number = card_number;
		this.expiry_date = expiry_date;
	}

	/**
	 * Get the credit card number.
	 * @return The credit card number.
	 */
	public String getCardNumber() {
		return card_number;
	}

	/**
	 * Set the credit card number.
	 * @param card_number The credit card number.
	 */
	public void setCardNumber(String card_number){
		this.card_number = card_number;
	}
	
	/**
	 * Get the expiry date of the credit card.
	 * @return The expiry date in the format "MM/YY".
	 */
	public String getExpiryDate(){
			return expiry_date;
	}

	/**
	 * Set the expiry date of the credit card.
	 * @param expiry_date The expiry date to set in the format "MM/YY".
	 */
	public void setExpiryDate(String expiry_date){
		this.expiry_date = expiry_date;
	}
}