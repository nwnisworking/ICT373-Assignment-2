package ict373.assignment2.forms;

import ict373.assignment2.models.payments.CreditCard;
import ict373.assignment2.models.payments.DirectDebit;
import ict373.assignment2.models.payments.Method;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.utils.Validator;
import ict373.assignment2.utils.Validator.ValidatorResult;

import java.util.List;

/**
 * <strong>PaymentForm record</strong>
 * 
 * <p>The PaymentForm records holds the input fields for the customer's payment information, 
 * including payment method, card details, and bank details.</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename PaymentForm.java
 */
public record PaymentForm(
  SelectInputField<String> method,
  TextInputField card_number,
  DateInputField expiry_date,
  TextInputField account_number,
  TextInputField bank_name
) implements FormRecord<Method>{
  /**
   * Returns a list of all input fields in the payment form.
   * 
   * @return A list of input fields in the payment form.
   */
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(method, card_number, expiry_date, account_number, bank_name);
  }

  /**
   * Save the data from the input fields into the given payment method object.
   * 
   * @param data The payment method object to save the data into.
   */
  @Override
  public void save(Method data){
    switch(data){
      case CreditCard cc -> {
        cc.setCardNumber(card_number.getValue());
        cc.setExpiryDate(expiry_date.getValue());
      }
      case DirectDebit dd -> {
        dd.setAccountNumber(account_number.getValue());
        dd.setBankName(bank_name.getValue());
      }
      default -> {}
    }
  }

  /**
   * Load the data from the given payment method object into the input fields.
   * 
   * @param data The payment method object to load the data from.
   */
  @Override
  public void load(Method data){
    switch(data){
      case CreditCard cc -> {
        method.setValue("Credit Card");
        card_number.setValue(cc.getCardNumber());
        expiry_date.setValue(cc.getExpiryDate());
      }
      case DirectDebit dd ->{
        method.setValue("Direct Debit");
        account_number.setValue(dd.getAccountNumber());
        bank_name.setValue(dd.getBankName());
      }
      default -> {}
    }
  }

  /**
   * Validate the input fields in the payment form. The validation rules are as follows:
   * <p><strong>Credit Card: </strong></p>
   * <ol>
   *  <li>Card number must not be empty.</li>
   *  <li>Card number must be 16 digits.</li>
   *  <li>Expiry date must not be empty.</li>
   *  <li>Expiry date must be in the future.</li>
   * </ol>
   * <p><strong>Direct Debit: </strong></p>
   * <ol>
   *  <li>Account number must be between 10 and 12 digits.</li>
   *  <li>Bank name must be between 2 and 50 characters.</li>
   * </ol>
   * 
   * @return A ValidatorResult object containing the validation result and message.
   */
  @Override
  public ValidatorResult validate(){
    ValidatorResult result;

    if(method.getValue().equals("Credit Card")){
      result = Validator
      .create(card_number)
      .notEmpty("Card number should not be empty")
      .exact(16, "Card number must be 16 digits")
      .number("Card number must be numeric")
      .validate();

      if(!result.valid()) return result;

      result = Validator
      .create(expiry_date)
      .notEmpty("Expiry date should not be empty")
      .futureDate("Expiry date must be in the future")
      .validate();

      if(!result.valid()) return result;
    }
    else{
      result = Validator
      .create(account_number)
      .notEmpty("Account number should not be empty")
      .range(10, 12, "Account number must be between 10 and 12 digits")
      .number("Account number must be numeric")
      .validate();

      if(!result.valid()) return result;

      result = Validator
      .create(bank_name)
      .notEmpty("bank name should not be empty")
      .min(2, "Bank name is too short")
      .max(50, "Bank name is too long")
      .validate();

      if(!result.valid()) return result;
    }

    return ValidatorResult.ok();
  }
}