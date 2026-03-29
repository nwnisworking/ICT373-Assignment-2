package ict373.assignment2.forms;

import ict373.assignment2.ui.inputs.InputField;
import ict373.assignment2.utils.Validator.ValidatorResult;

import java.util.List;

/**
 * <strong>FormRecord interface</strong>
 * 
 * <p>The FormRecord interface defines the structure for form records, which are used to manage the data and validation of forms in the application. It includes methods for retrieving input fields, saving data, loading data, and validating the form.</p>
 * 
 * @author nwnisworking
 * @date 26/3/2026
 * @filename FormRecord.java
 */
public interface FormRecord<T>{
  /**
   * Returns a list of input fields associated with the form record. Each input field represents a specific piece of data that can be entered or edited in the form.
   * 
   * @return A list of input fields associated with the form record.
   */
  List<InputField<?, ?>> fields();
  
  /**
   * Saves the data from the form record. This method is responsible for persisting the data entered in the form, which may involve updating existing records or creating new records in the underlying data storage.
   * 
   * @param data The data to be saved from the form record.
   */
  void save(T data);
  
  /**
   * Loads data into the form record. This method is responsible for populating the form fields with existing data, which may involve retrieving data from the underlying data storage and setting the values of the input fields accordingly.
   * 
   * @param data The data to be loaded into the form record.
   */
  void load(T data);

  /**
   * Validates the data in the form record. This method checks the values entered in the input fields against defined validation rules and returns a ValidatorResult indicating whether the data is valid or if there are any validation errors that need to be addressed.
   * 
   * @return A ValidatorResult indicating the outcome of the validation process for the form record.
   */
  ValidatorResult validate();
}
