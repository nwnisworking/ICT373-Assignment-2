package ict373.assignment2.payment;

/**
 * <p><strong>DirectDebit class</strong></p>
 * <p>Represents a Direct Debit payment method.</p>
 * 
 * @author nwnisworking
 * @date 9/6/2025
 * @filename DirectDebit.java
 */
public class DirectDebit implements Method{
	/**
	 * Serial version UID for serialization.
	 */
  private static final long serialVersionUID = 8L;

	/**
	 * The bank account number for the direct debit.
	 */
	private String account_number;

	/**
	 * The name of the bank associated with this direct debit.
	 */
	private String bank_name;

	/**
	 * Default constructor for creating a DirectDebit instance.
	 */
	public DirectDebit() {
		account_number = "";
		bank_name = "";
	}

	/**
	 * Constructor to create a DirectDebit instance.
	 * @param account_number The bank account number for the direct debit.
	 * @param bank_name The name of the bank associated with this direct debit.
	 */
	public DirectDebit(String account_number, String bank_name){
		this.account_number = account_number;
		this.bank_name = bank_name;
	}

	/**
	 * Get the bank account number for this direct debit.
	 * @return The bank account number.
	 */
	public String getAccountNumber() {
		return account_number;
	}

	/**
	 * Set the bank account number for this direct debit.
	 * @param account_number the bank account number
	 */
	public void setAccountNumber(String account_number){
		this.account_number = account_number;
	}

	/**
	 * Get the bank name.
	 * @return Bank name associated with this direct debit.
	 */
	public String getBankName() {
		return bank_name;
	}

	/**
	 * Set the bank name.
	 * @param bank_name The bank name 
	 */
	public void setBankName(String bank_name){
		this.bank_name = bank_name;
	}
}