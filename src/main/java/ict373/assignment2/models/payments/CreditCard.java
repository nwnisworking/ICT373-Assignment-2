package ict373.assignment2.models.payments;

import java.time.LocalDate;

/**
 * <strong>CreditCard class</strong>
 * <p>The CreditCard class represents a credit card payment method containing card number and expiry date.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename CreditCard.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 */
public class CreditCard implements Method{
  /**
   * The credit card number.
   */
  private String card_number;

  /**
   * The expiry date of the credit card.
   */
  private LocalDate expiry_date;

  /**
   * Default constructor for CreditCard.
   */
  public CreditCard(){}

  /**
   * Parameterized constructor for CreditCard.
   * @param card_number The credit card number.
   * @param expiry_date The expiry date of the credit card.
   */
  public CreditCard(String card_number, LocalDate expiry_date){
    this.card_number = card_number;
    this.expiry_date = expiry_date;
  }

  /**
   * Set the credit card number.
   * @param card_number The credit card number to be set.
   */
  public void setCardNumber(String card_number){
    this.card_number = card_number;
  }

  /**
   * Get the credit card number.
   * @return The credit card number.
   */
  public String getCardNumber(){
    return card_number;
  }

  /**
   * Set the expiry date of the credit card.
   * @param expiry_date The expiry date to be set.
   */
  public void setExpiryDate(LocalDate expiry_date){
    this.expiry_date = expiry_date;
  }

  /**
   * Get the expiry date of the credit card.
   * @return The expiry date.
   */
  public LocalDate getExpiryDate(){
    return expiry_date;
  }
}
