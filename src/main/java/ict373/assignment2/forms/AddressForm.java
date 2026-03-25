package ict373.assignment2.forms;

import ict373.assignment2.models.Address;
import ict373.assignment2.ui.inputs.InputField;
import ict373.assignment2.ui.inputs.TextInputField;
import ict373.assignment2.utils.Validator;
import ict373.assignment2.utils.Validator.ValidatorResult;

import java.util.List;

/**
 * <strong>AddressForm record</strong>
 * 
 * <p>The AddressForm records holds the input fields for the customer's address information, including street name,
 * block number, postal code, and unit number.</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename AddressForm.java
 */
public record AddressForm(
  TextInputField street,
  TextInputField block,
  TextInputField postal,
  TextInputField unit
) implements FormRecord<Address>{
  /**
   * Returns a list of all input fields in the address form.
   * 
   * @return A list of input fields in the address form.
   */
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(street, block, postal, unit);
  }

  /**
   * Save the data from the input fields into the given address object.
   * 
   * @param data The address object to save the data into.
   */
  @Override
  public void save(Address data){
    if(data == null) return;

    data.setStreetName(street.getValue());
    data.setBlockNumber(Integer.parseInt(block.getValue()));
    data.setPostalCode(Integer.parseInt(postal.getValue()));
    data.setUnitNumber(unit.getValue());
  }

  /**
   * Load the data from the given address object into the input fields.
   * 
   * @param data The address object to load the data from.
   */
  @Override
  public void load(Address data){
    if(data == null) return;
    
    street.setValue(data.getStreetName());
    block.setValue(data.getBlockNumber() + "");
    postal.setValue(data.getPostalCode() + "");
    unit.setValue(data.getUnitNumber());
  }

  /**
   * Validates the input fields in the address form. The validation rules are as follows:
   * <ol>
   *  <li>Street name cannot be empty.</li>
   *  <li>Block number cannot be empty.</li>
   *  <li>Block number must be a number.</li>
   *  <li>Block number must be 3 to 4 characters long.</li>
   *  <li>Postal code cannot be empty.</li>
   *  <li>Postal code must be a number.</li>
   *  <li>Postal code must be exactly 6 numbers.</li>
   *  <li>Unit number cannot be empty.</li>
   *  <li>Unit number must be in the format #XX-XXXO </li>
   * </ol>
   * 
   * @return A ValidatorResult object indicating whether the validation was successful and any error messages.
   */
  @Override
  public ValidatorResult validate(){
    ValidatorResult result;

    result = Validator
    .create(street)
    .notEmpty("Street name cannot be empty")
    .validate();

    if(!result.valid()) return result;

    result = Validator
    .create(block)
    .notEmpty("Block number cannot be empty")
    .number("Block number must be a number")
    .range(3, 4, "Block number must be 3 to 4 characters")
    .validate();

    if(!result.valid()) return result;

    result = Validator
    .create(postal)
    .notEmpty("Postal code cannot be empty")
    .number("Postal code must be a number")
    .exact(6, "Postal code must be 6 numbers")
    .validate();

    if(!result.valid()) return result;

    result = Validator
    .create(unit)
    .notEmpty("Unit number cannot be empty")
    .match("^#\\d{2}-\\d{3,4}$", "Unit number must be in the format #XX-XXX(X)")
    .validate();

    if(!result.valid()) return result;
    
    return ValidatorResult.ok();
  }
}