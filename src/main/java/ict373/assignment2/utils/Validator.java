package ict373.assignment2.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ict373.assignment2.ui.inputs.InputField;

/**
 * <strong>Validator class</strong>
 * 
 * <p>The validator class provides an easy way to validate input fields with various rules. </p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename Validator.java
 * @param <T> The type of data being validated, such as String, Number, or LocalDate.
 */
public final class Validator<T>{
  /**
   * The input field being validated. The validator will check the value of this field against the rules.
   */
  private InputField<T, ?> field;

  /**
   * The list of validation rules to apply to the input field.
   */
  private List<Rule<T>> rules = new ArrayList<>();

  /**
   * A record representing a validation rule, consisting of a predicate and an error message.
   */
  private record Rule<T>(
    Predicate<InputField<T, ?>> rule,
    String message
  ){}

  /**
   * A record representing the result after validating an input field.
   */
  public record ValidatorResult(boolean valid, String message, InputField<?, ?> field){
    /**
     * Returns a ValidatorResult indicating that the validation passed successfully.
     * 
     * @return A ValidatorResult with valid set to true and an empty message.
     */
    public static ValidatorResult ok(){
      return new ValidatorResult(true, "", null);
    }

    /**
     * Returns a ValidatorResult indicating that the validation failed, along with an error message and the field that failed validation.
     * 
     * @param message The error message describing why the validation failed.
     * @param field The input field that failed validation.
     * @return A ValidatorResult with valid set to false, containing the error message and the field that failed validation.
     */
    public static ValidatorResult fail(String message, InputField<?, ?> field){
      return new ValidatorResult(false, message, field);
    }
  }

  /**
   * Constructs a new Validator for the given input field.
   * 
   * @param field The input field to be validated by this Validator.
   */
  private Validator(InputField<T, ?> field){
    this.field = field;
  }

  /**
   * Add a validation rules that checks if the value of the input field is at least a certain length or value.
   * @param min The minimum length (for strings) or value (for numbers) that the input field must satisfy.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> min(double min, String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        return s.length() >= min;
      }
      else if(value instanceof Number n){
        return n.doubleValue() >= min;
      }

      return false;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is at most a certain length or value.
   * @param max The maximum length (for strings) or value (for numbers) that the input field must satisfy.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> max(double max, String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        return s.length() <= max;
      }
      else if(value instanceof Number n){
        return n.doubleValue() <= max;
      }

      return false;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is within a specified range of lengths or values.
   * @param min The minimum length (for strings) or value (for numbers) that the input field must satisfy.
   * @param max The maximum length (for strings) or value (for numbers) that the input field must satisfy.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> range(double min, double max, String message){
    return min(min, message).max(max, message);
  }

  /**
   * Add a validation rule that checks if the value of the input field is exactly a certain length or value.
   * @param length The exact length (for strings) or value (for numbers) that the input field must satisfy.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> exact(double length, String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        return s.length() == length;
      }
      else if(value instanceof Number n){
        return n.doubleValue() == length;
      }

      return false;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field matches a specified regular expression pattern.
   * @param regex The regular expression pattern that the input field's value must match.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> match(String regex, String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        return s.matches(regex);
      }

      return true;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is not empty.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> notEmpty(String message){
    rules.add(new Rule<>(e -> !e.isEmpty(), message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is a valid number.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> number(String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        for(char c : s.toCharArray()){
          if(c < '0' || c > '9'){
            return false;
          }
        }
      }

      return true;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is a valid decimal number.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> decimal(String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        boolean dot = false;

        for(int i = 0; i < s.length(); i++){
          char c = s.charAt(i);

          // 10.5, -5.3, +3.14 are all valid doubles
          if(i == 0 && (c == '+' || c == '-')) continue;

          if(c == '.'){
            if(!dot){
              dot = true;
              continue;
            }

            return false;
          }

          if(c < '0' || c > '9'){
            return false;
          }
        }
        
        return true;
      }
      else if(value instanceof Number){
        return true;
      }
      
      return false;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is a positive number or a string representing a positive number.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> positive(String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();
      
      if(value instanceof String s){
        boolean dot = false;

        for(int i = 0; i < s.length(); i++){
          char c = s.charAt(i);

          if(i == 0 && (c == '-')) return false;

          if(c == '.'){
            if(!dot){
              dot = true;
              continue;
            }

            return false;
          }

          if(c < '0' || c > '9'){
            return false;
          }
        }
      }
      else if(value instanceof Number n){
        return n.doubleValue() > 0;
      }
      
      return false;
    }, message));
    
    return this;
  }
  
  /**
   * Add a validation rule that checks if the value of the input field is a date in the future.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> futureDate(String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();
      
      if(value instanceof LocalDate d){
        return d.isAfter(LocalDate.now());
      }
      
      return false;
    }, message));

    return this;
  }

  /**
   * Add a validation rule that checks if the value of the input field is a valid email address.
   * @param message The error message to display if the validation fails.
   * @return This Validator instance for method chaining.
   */
  public Validator<T> email(String message){
    rules.add(new Rule<>(e -> {
      T value = e.getValue();

      if(value instanceof String s){
        return s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
      }

      return true;
    }, message));

    return this;
  }

  /**
   * Validate the input field against all the added validation rules and return a result.
   * @return A ValidatorResult object indicating whether the validation was successful and any error messages.
   */
  public ValidatorResult validate(){
    for(Rule<T> rule : rules){
      if(!rule.rule().test(field)){
        return ValidatorResult.fail(rule.message(), field);
      }
    }

    return ValidatorResult.ok();
  }

  /**
   * Static factory method to create a Validator for a given input field.
   * @param field The input field to be validated by the returned Validator.
   * @param <T> The type of data being validated, such as String, Number, or LocalDate.
   * @return A new Validator instance for the specified input field.
   */
  public static <T> Validator<T> create(InputField<T, ?> field){
    return new Validator<>(field);
  }
}
