package ict373.assignment2.models.payments;

/**
 * <strong>DirectDebit class</strong>
 * <p>The DirectDebit class represents a direct debit payment method containing account number and bank name.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename DirectDebit.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 */
public class DirectDebit implements Method{
  /**
   * The account number for the direct debit.
   */
  private String account_number;

  /**
   * The bank name associated with the direct debit.
   */
  private String bank_name;

  /**
   * Default constructor for DirectDebit.
   */
  public DirectDebit(){}

  /**
   * Parameterized constructor for DirectDebit.
   * @param account_number The account number for the direct debit.
   * @param bank_name The bank name associated with the direct debit.
   */
  public DirectDebit(String account_number, String bank_name){
    this.account_number = account_number;
    this.bank_name = bank_name;
  }

  /**
   * Set the account number for the direct debit.
   * @param account_number The account number to be set.
   */
  public void setAccountNumber(String account_number){
    this.account_number = account_number;
  }

  /**
   * Get the account number for the direct debit.
   * @return The account number.
   */
  public String getAccountNumber(){
    return account_number;
  }

  /**
   * Set the bank name associated with the direct debit.
   * @param bank_name The bank name to be set.
   */
  public void setBankName(String bank_name){
    this.bank_name = bank_name;
  }

  /**
   * Get the bank name associated with the direct debit.
   * @return The bank name.
   */
  public String getBankName(){
    return bank_name;
  }
}
