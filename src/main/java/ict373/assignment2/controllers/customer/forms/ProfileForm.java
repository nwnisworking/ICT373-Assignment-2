package ict373.assignment2.controllers.customer.forms;

import ict373.assignment2.FormRecord;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.utils.Validator;
import ict373.assignment2.utils.Validator.ValidatorResult;
import ict373.assignment2.models.customers.*;

import java.util.List;

/**
 * <strong>ProfileForm record</strong>
 * 
 * <p>The ProfileForm records holds the input fields for the customer's profile information, including name,
 * email, customer type, and payer (if the customer is an associate).</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename ProfileForm.java
 */
public record ProfileForm(
  TextInputField name,
  TextInputField email,
  SelectInputField<String> type,
  SelectInputField<Customer> payer
) implements FormRecord<Customer>{
  /**
   * Returns a list of all input fields in the profile form.
   * 
   * @return A list of input fields in the profile form.
   */
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(name, email, type, payer);
  }

  /**
   * Save the data from the input fields into the given customer object.
   * 
   * @param customer The customer object to save the data into.
   */
  @Override
  public void save(Customer customer){
    if(customer == null) return;

    if(customer instanceof AssociateCustomer ac){
      if(payer.getValue() != null){
        ((PayingCustomer) payer.getValue()).addAssociate(ac);
      }
    }
    
    customer.setName(name.getValue());
    customer.setEmail(email.getValue());
  }

  /**
   * Load the data from the given customer object into the input fields.
   * 
   * @param customer The customer object to load the data from.
   */
  @Override
  public void load(Customer customer){
    if(customer == null) return;
    
    if(customer instanceof AssociateCustomer ac){
      if(ac.getPayer() != null){
        payer.setValue(ac.getPayer());
      }
      
      type.setValue("Associate Customer");
    }
    else{
      type.setValue("Paying Customer");
    }
    
    name.setValue(customer.getName());
    email.setValue(customer.getEmail());
  }

  /**
   * Validate the input fields in the profile form. The validation rules are as follows:
   * <ol>
   *   <li>Name must not be empty.</li>
   *   <li>Name must only contain alphabets, spaces, hyphens, and apostrophes</li>
   *   <li>Name must be at least 2 characters long.</li>
   *   <li>Email must not be empty.</li>
   *   <li>Email must be a valid email address.</li>
   * </ol>
   * 
   * @return A ValidatorResult object containing the validation result and message.
   */
  @Override
  public ValidatorResult validate(){
    ValidatorResult result;

    // Name validation
    result = Validator
      .create(name)
      .notEmpty("Name must not be empty")
      .match("^[a-zA-Z' \\-]+$", "Name can only contain alphabets, space, hyphen, and apostrophe")
      .min(2, "Name is too short")
      .validate();

    if(!result.valid()) return result;
    
    // Email validation
    result = Validator
      .create(email)
      .notEmpty("Email must not be empty")
      .email("Email is invalid")
      .validate();

    if(!result.valid()) return result;

    return ValidatorResult.ok();
  }
}