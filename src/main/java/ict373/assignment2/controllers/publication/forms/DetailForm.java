package ict373.assignment2.controllers.publication.forms;

import ict373.assignment2.FormRecord;
import ict373.assignment2.models.publications.*;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.utils.Validator;
import ict373.assignment2.utils.Validator.ValidatorResult;
import java.util.List;

/**
 * <strong>DetailForm record</strong>
 * 
 * <p>The DetailForm records holds the input fields for the publication detail view.</p>
 * 
 * @author nwnisworking
 * @date 19/3/2026
 * @filename DetailForm.java
 */
public record DetailForm(
  TextInputField name,
  TextInputField cost,
  SelectInputField<String> type,
  SelectInputField<Publication> magazine
) implements FormRecord<Publication>{
  /**
   * Returns a list of all input fields in the detail form.
   * 
   * @return A list of input fields in the detail form.
   */
  public List<InputField<?, ?>> fields(){
    return List.of(name, cost, type, magazine);
  }

  /**
   * Save the data from the input fields into the given publication object.
   * 
   * @param publication The publication object to save the data into.
   */
  @Override
  public void save(Publication data){
    data.setName(name.getValue());
    data.setCost(Double.parseDouble(cost.getValue()));
    
    if(data instanceof Supplement s){
      s.setMagazine((Magazine) magazine.getValue());
    }
  }

  /**
   * Load the data from the given publication object into the input fields.
   * 
   * @param publication The publication object to load the data from.
   */
  @Override
  public void load(Publication data){
    name.setValue(data.getName());
    cost.setValue(data.getCost() + "");
    type.setValue(data.getType());
    
    if(data instanceof Supplement s){
      magazine.setValue(s.getMagazine());
    }
  }

  /**
   * Validate the input fields in the detail form to ensure that they contain valid data before saving.
   * 
   * @return A ValidatorResult indicating whether the validation passed or failed, along with any error messages.
   */
  @Override
  public ValidatorResult validate(){
    ValidatorResult result;
    
    result = Validator
    .create(name)
    .notEmpty("Name must not be empty")
    .validate();
    
    if(!result.valid()) return result;
    
    result = Validator
    .create(cost)
    .notEmpty("Cost must not be empty")
    .decimal("Cost must be a decimal number")
    .validate();
    
    if(!result.valid()) return result;
    
    return ValidatorResult.ok();
  }
}
