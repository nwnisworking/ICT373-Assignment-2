package ict373.assignment2.controllers.customer;

import ict373.assignment2.payment.*;
import ict373.assignment2.customers.*;
import ict373.assignment2.App;
import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.utils.Address;
import ict373.assignment2.utils.BaseController;
import ict373.assignment2.utils.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * <p><strong>PopupController class</strong></p>
 * 
 * <p>Controller for managing customer-related popups, including adding, editing, and viewing customer details.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename PopupController.java
 */
class PopupController extends BaseController implements Initializable{
  /**
   * Model instance for managing customer data.
   */
  private CustomerModel model;

  /**
   * Customer object being managed by this controller.
   */
  private Customer customer;
  
  /**
   * Name field for entering or displaying the customer's name.
   */
  @FXML
  private TextField name_field;
  
  /**
   * Email field for entering or displaying the customer's email.
   */
  @FXML
  private TextField email_field;
  
  /**
   * Type field for selecting the type of customer (Paying or Associate). If the customer already has a type, this field is disabled.
   */
  @FXML
  private ComboBox<String> type_field;
  
  /**
   * Paying customer field for selecting the payer of an associate customer. This field is only visible when the customer is an associate.
   */
  @FXML
  private ComboBox<Customer> paying_customer_field;
  
  /**
   * Card type field for selecting the payment method (Credit Card or Direct Debit). This field is only visible for paying customers.
   */
  @FXML
  private ComboBox<String> card_type_field;
  
  /**
   * Card number field for entering the credit card number. This field is only visible when the card type is Credit Card.
   */
  @FXML
  private TextField card_no_field;
  
  /**
   * Expiry date field for entering the credit card expiry date. This field is only visible when the card type is Credit Card.
   */
  @FXML
  private TextField exp_date_field;
  
  /**
   * Account number field for entering the direct debit account number. This field is only visible when the card type is Direct Debit.
   */
  @FXML
  private TextField acc_no_field;
  
  /**
   * Bank name field for entering the bank name associated with the direct debit. This field is only visible when the card type is Direct Debit.
   */
  @FXML
  private TextField bank_name_field;
  
  /**
   * Street number field for entering the street number of the customer's address.
   */
  @FXML
  private TextField street_no_field;
  
  /**
   * Postal code field for entering the postal code of the customer's address.
   */
  @FXML
  private TextField postal_code_field;
  
  /**
   * Street name field for entering the street name of the customer's address.
   */
  @FXML
  private TextField street_name_field;
  
  /**
   * Suburb field for entering the suburb of the customer's address.
   */
  @FXML
  private TextField suburb_field;
  
  /**
   * Button for submitting the form to create or edit a customer. The button text changes based on the mode (Create, Edit, View).
   */
  @FXML
  private Button btn;

  /**
   * HBox for displaying the paying customer row, which is only visible for associate customers.
   */
  @FXML
  private HBox paying_customer_row;
  
  /**
   * HBox for displaying the card number row, which is only visible for paying customers using a credit card.
   */
  @FXML
  private HBox card_no_row;
  
  /**
   * HBox for displaying the expiry date row, which is only visible for paying customers using a credit card.
   */
  @FXML
  private HBox exp_date_row;
  
  /**
   * HBox for displaying the account number row, which is only visible for paying customers using direct debit.
   */
  @FXML
  private HBox acc_no_row;
  
  /**
   * HBox for displaying the bank name row, which is only visible for paying customers using direct debit.
   */
  @FXML
  private HBox bank_name_row;

  /**
   * Tab for display basic customer information.
   */
  @FXML
  private Tab profile_tab;
  
  /**
   * Tab for displaying address.
   */
  @FXML
  private Tab address_tab;
  
  /**
   * Tab for displaying payment information, which is only visible for paying customers.
   */
  @FXML
  private Tab payment_info_tab;
  
  /**
   * Tab for displaying subscription information, which is only visible for paying customers.
   */
  @FXML
  private Tab subscription_tab;
  
  /**
   * Tab for displaying associates of a paying customer, which is only visible for paying customers.
   */
  @FXML
  private Tab associate_tab;
  
  /**
   * Tab for displaying billing information, which is only visible for paying customers.
   */
  @FXML
  private Tab billing_tab;
  
  /**
   * TabPane that contains all the tabs for this popup, allowing dynamic addition and removal of tabs based on the customer's type and mode.
   */
  @FXML
  private TabPane tab_pane;
  
  /**
   * Mode of the popup, which can be "edit", "view", or empty. This determines the behavior of the popup and the actions available to the user.
   */
  private final String mode;
  
  /**
   * Constructor for PopupController.
   * @param customer The customer object to be managed by this controller, can be null for creating a new customer.
   * @param mode The mode of the popup, which can be "edit", "view", or empty. This determines the behavior of the popup.
   */
  public PopupController(Customer customer, String mode){
    this.customer = customer;
    this.mode = mode;
  }
  
  /**
   * Initializes the controller by setting up the UI components and loading customer data if available.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources){
    model = CustomerModel.getInstance();
    FilteredList<Customer> filter_by_paying_customer = new FilteredList<>(
      model.getData(),
      e->e instanceof PayingCustomer
    );
    
    // Paying customer can be de-selected.
    paying_customer_field.getItems().add(null);
    paying_customer_field.getItems().addAll(filter_by_paying_customer);

    displayCreditRow(true);
    displayDebitRow(false);
    displayPayingCustomerRow(false);
    
    type_field.setOnAction(e->{
      boolean result = isCustomer(PayingCustomer.class);

      displayPayingCustomerRow(!result);
      displayPaymentInfoTab(result);
      displayAssociateTab(result);
      displayBillingTab(result);
    });
      
    card_type_field.setOnAction(e->{
      boolean result = isMethod(CreditCard.class);

      displayCreditRow(result);
      displayDebitRow(!result);
    });
    
    if(customer == null){
      btn.setText("Create");
      btn.setOnAction(e->addCustomer());
      displayAssociateTab(false);
      displaySubscriptionTab(false);
    }
    else{
      Address addr = customer.getAddress();
      
      if("edit".equals(mode)){
        btn.setText("Edit");
        btn.setOnAction(e->editCustomer());
        type_field.setDisable(true);
      }
      else{
        disableAllFields();
        btn.setText("Close");
        btn.setOnAction(e->{
          Stage stage = (Stage) btn.getScene().getWindow();
          stage.close();
        });
      }
      
      // Set basic information of the customer
      name_field.setText(customer.getName());
      email_field.setText(customer.getEmail());

      // Set address of the customer
      if(addr != null){
        street_no_field.setText(addr.getStreetNumber() + "");
        postal_code_field.setText(addr.getPostalCode() + "");
        street_name_field.setText(addr.getName());
        suburb_field.setText(addr.getSuburb());
      }
      
      if(customer instanceof PayingCustomer){
        PayingCustomer pc = (PayingCustomer) customer;
        type_field.setValue("Paying Customer");

        // Paying customer does not need a payer
        displayPayingCustomerRow(false);
        
        if(pc.getPaymentMethod() instanceof CreditCard){
          CreditCard cc = (CreditCard) pc.getPaymentMethod();
          card_type_field.setValue("Credit Card");
          card_no_field.setText(cc.getCardNumber());
          exp_date_field.setText(cc.getExpiryDate());
          displayCreditRow(true);
          displayDebitRow(false);
        }
        else{
          DirectDebit dd = (DirectDebit) pc.getPaymentMethod();
          card_type_field.setValue("Direct Debit");
          acc_no_field.setText(dd.getAccountNumber());
          bank_name_field.setText(dd.getBankName());
          displayCreditRow(false);
          displayDebitRow(true);          
        }
      }
      else{
        AssociateCustomer ac = (AssociateCustomer) customer;

        type_field.setValue("Associate Customer");
        paying_customer_field.setValue(ac.getPayer());
        
        displayPayingCustomerRow(true);
        displayPaymentInfoTab(false);
        displayAssociateTab(false);
        displayBillingTab(false);

      }
    }
    
    try{
      FXMLLoader loader = App.FXMLLoader("publication");

      loader.setController(new SubscriptionController(customer, mode));
      subscription_tab.setContent(loader.load());

      if(customer instanceof PayingCustomer){
        loader = App.FXMLLoader("customer");

        loader.setController(new AssociateController(customer));
        associate_tab.setContent(loader.load());

        loader = App.FXMLLoader("billing");
        loader.setController(new BillingController(customer));
        billing_tab.setContent(loader.load());
      }
      
    }
    catch(IOException ex){
      System.out.println("Error loading FXML: " + ex.getMessage());
    }
  }
  
  /**
   * Adds a new customer to the model and closes the popup.
   */
  private void addCustomer(){
    Stage stage = (Stage) btn.getScene().getWindow();
    String name = name_field.getText();
    String email = email_field.getText();
    String street_no = street_no_field.getText();
    String postal_code = postal_code_field.getText();
    String street_name = street_name_field.getText();
    String suburb = suburb_field.getText();
    PayingCustomer payer = (PayingCustomer) paying_customer_field.getValue();
    boolean result = confirm("Add Customer", "Do you want to add a new customer?");

    int id = model.maxID() + 1;

    if(!result || !validate()){
      return;
    }
    
    if(isCustomer(PayingCustomer.class)){
      PayingCustomer pc = new PayingCustomer(id, name, email);
      customer = pc;
      
      if(isMethod(CreditCard.class)){
        pc.setPaymentMethod(new CreditCard(
          card_no_field.getText(), 
          exp_date_field.getText()
        ));
      }
      else{
        pc.setPaymentMethod(new DirectDebit(
            acc_no_field.getText(), 
            bank_name_field.getText()
        ));
      }
    }
    else{
      AssociateCustomer ac = new AssociateCustomer(id, name, email);
      customer = ac;

      if(payer != null){
          payer.addAssociate(ac);
      }
    }
    
    Address addr = new Address();
    
    addr.setPostalCode(Integer.parseInt(postal_code));
    addr.setStreetNumber(Integer.parseInt(street_no));
    addr.setName(street_name);
    addr.setSuburb(suburb);
    
    customer.setAddress(addr);
    
    model.getData().add(customer);
    
    stage.close();
  }
  
  /**
   * Edits the details of an existing customer and updates the model. Closes the popup after editing.
   */
  private void editCustomer(){
    Stage stage = (Stage) btn.getScene().getWindow();
    String name = name_field.getText();
    String email = email_field.getText();
    String street_no = street_no_field.getText();
    String postal_code = postal_code_field.getText();
    String street_name = street_name_field.getText();
    String suburb = suburb_field.getText();
    PayingCustomer payer = (PayingCustomer) paying_customer_field.getValue();
    boolean result = confirm("Edit Customer", "Do you want to edit the customer detail?");

    if(!result || !validate()){
      return;
    }
    
    if(isCustomer(PayingCustomer.class)){
      PayingCustomer pc = (PayingCustomer) customer;
      
      if(isMethod(CreditCard.class)){
        pc.setPaymentMethod(new CreditCard(
          card_no_field.getText(), 
          exp_date_field.getText()
        ));
      }
      else{
        pc.setPaymentMethod(new DirectDebit(
          bank_name_field.getText(), 
          acc_no_field.getText()
        ));
      }
    }
    else{
      AssociateCustomer ac = (AssociateCustomer) customer;
      
      if(ac.getPayer() != null){
        ac.getPayer().removeAssociate(ac);
      }
      
      if(payer != null){
        payer.addAssociate(ac);
      }
      else{
        ac.setPayer(null);
      }
    }
    customer.setName(name);
    customer.setEmail(email);
    
    if(customer.getAddress() == null){
      customer.setAddress(new Address());
    }
    
    customer.getAddress().setName(street_name);
    customer.getAddress().setPostalCode(Integer.parseInt(postal_code));
    customer.getAddress().setStreetNumber(Integer.parseInt(street_no));
    customer.getAddress().setSuburb(suburb);
    
    model.getData().set(model.getData().indexOf(customer), customer);
    stage.close();
  }

  /**
   * Validates the input fields for creating or editing a customer.
   * @return true if all fields are valid, false otherwise.
   */
  private boolean validate(){
    String name = name_field.getText();
    String email = email_field.getText();
    String street_no = street_no_field.getText();
    String postal_code = postal_code_field.getText();
    String street_name = street_name_field.getText();
    String suburb = suburb_field.getText();

    if(!Validator.match("^[a-zA-Z\'\\- ]+$").validate(name) || Validator.blank().validate(name)){
      tab_pane.getSelectionModel().select(profile_tab);
      name_field.requestFocus();
      
      alert("Invalid Field", "Name can only contain alphabet, apostrophe, spaces, or hyphen characters and cannot be blank");
      return false;
    }
    
    if(!Validator.isEmail().validate(email)){
      tab_pane.getSelectionModel().select(profile_tab);
      email_field.requestFocus();
      alert("Invalid Field", "The email provided is invalid.");
      return false;
    }

    if(Validator.blank().validate(street_name)){
      tab_pane.getSelectionModel().select(address_tab);
      street_name_field.requestFocus();
      alert("Invalid Field", "Street name is empty");
      return false;
    }
    
    if(Validator.blank().validate(suburb)){
      tab_pane.getSelectionModel().select(address_tab);
      suburb_field.requestFocus();
      alert("Invalid Field", "Suburb is empty");
      return false;
    }
    
    if(!Validator.isNumber().validate(street_no)){
      tab_pane.getSelectionModel().select(address_tab);
      street_no_field.requestFocus();
      alert("Invalid Field", "Street number must be a number");
      return false;
    }
    
    if(!Validator.isNumber().validate(postal_code)){
      tab_pane.getSelectionModel().select(address_tab);
      postal_code_field.requestFocus();
      alert("Invalid Field", "Postal code must be a number");
      return false;
    }
    
    if(isCustomer(PayingCustomer.class)){     
      if(isMethod(CreditCard.class)){
        String cardno = card_no_field.getText();
        String exp_date = exp_date_field.getText();
        
        if(Validator.blank().validate(cardno)){
          tab_pane.getSelectionModel().select(payment_info_tab);
          card_no_field.requestFocus();
          alert("Invalid Field", "Card number is empty");
          return false;
        }
        
        if(Validator.blank().validate(exp_date)){
          tab_pane.getSelectionModel().select(payment_info_tab);
          exp_date_field.requestFocus();
          alert("Invalid Field", "Expiry date is empty");
          return false;
        }
      }
      else{
        String bnk_name = bank_name_field.getText();
        String accno = acc_no_field.getText();
        
        if(Validator.blank().validate(accno)){
          tab_pane.getSelectionModel().select(payment_info_tab);
          acc_no_field.requestFocus();
          alert("Invalid Field", "Account number is empty");
          return false;
        }
        
        if(Validator.blank().validate(bnk_name)){
          tab_pane.getSelectionModel().select(payment_info_tab);
          bank_name_field.requestFocus();
          alert("Invalid Field", "Bank name is empty");
          return false;
        }
      }
    }
    
    return true;
  }
  
  /**
   * Checks if the current customer is of the specified type.
   * @param <T> The type of customer to check against.
   * @param cls The class of the customer type to check.
   * @return true if the customer is of the specified type, false otherwise.
   */
  private <T> boolean isCustomer(Class<T> cls){
    if(cls.equals(PayingCustomer.class)){
      return "Paying Customer".equals(type_field.getValue());
    }
    else if(cls.equals(AssociateCustomer.class)){
      return "Associate Customer".equals(type_field.getValue());
    }
    else{
      return false;
    }
  }
  
  /**
   * Checks if the current payment method is of the specified type.
   * @param cls The class of the payment method type to check.
   * @param <T> The type of payment method to check against.
   * @return true if the payment method is of the specified type, false otherwise.
   */
  private <T> boolean isMethod(Class<T> cls){
    if(cls.equals(CreditCard.class)){
      return "Credit Card".equals(card_type_field.getValue());
    }
    else if(cls.equals(DirectDebit.class)){
      return "Direct Debit".equals(card_type_field.getValue());
    }
    else{
      return false;
    }
  }

  /**
   * Displays or hides the payment information tab based on the provided value.
   * @param value true to display the tab, false to hide it.
   */
  private void displayPaymentInfoTab(boolean value){
    if(value){
      tab_pane.getTabs().add(1, payment_info_tab);
    }
    else{
      tab_pane.getTabs().remove(payment_info_tab);
    }
  }
  
  /**
   * Displays or hides the associate tab based on the provided value.
   * @param value true to display the tab, false to hide it.
   */
  private void displayAssociateTab(boolean value){
    if(value){
      tab_pane.getTabs().add(tab_pane.getTabs().size(), associate_tab);
    }
    else{
      tab_pane.getTabs().remove(associate_tab);
    }
  }
  
  /**
   * Displays or hides the subscription tab based on the provided value.
   * @param value true to display the tab, false to hide it.
   */
  private void displaySubscriptionTab(boolean value){
    if(value){
      tab_pane.getTabs().add(tab_pane.getTabs().size(), subscription_tab);
    }
    else{
      tab_pane.getTabs().remove(subscription_tab);
    }
  }
  
  /**
   * Displays or hides the billing tab based on the provided value.
   * @param value true to display the tab, false to hide it.
   */
  private void displayBillingTab(boolean value){
    if(value){
      tab_pane.getTabs().add(tab_pane.getTabs().size(), billing_tab);
    }
    else{
      tab_pane.getTabs().remove(billing_tab);
    }
  }
  
  /**
   * Displays or hides the credit card row based on the provided value.
   * @param value true to display the row, false to hide it.
   */
  private void displayCreditRow(boolean value){
    displayElement(card_no_row, value);
    displayElement(exp_date_row, value);
  }
  
  /**
   * Displays or hides the direct debit row based on the provided value.
   * @param value true to display the row, false to hide it.
   */
  private void displayDebitRow(boolean value){
    displayElement(bank_name_row, value);
    displayElement(acc_no_row, value);
  }
  
  /**
   * Displays or hides the paying customer row based on the provided value.
   * @param value true to display the row, false to hide it.
   */
  private void displayPayingCustomerRow(boolean value){
    displayElement(paying_customer_row, value);
  }
  
  /**
   * Disables all input fields in the popup to prevent user interaction.
   */
  private void disableAllFields(){
    disableInputElement(name_field, true);
    disableInputElement(email_field, true);
    disableInputElement(street_no_field, true);
    disableInputElement(postal_code_field, true);
    disableInputElement(street_name_field, true);
    disableInputElement(suburb_field, true);
    disableInputElement(card_type_field, true);
    disableInputElement(exp_date_field, true);
    disableInputElement(acc_no_field, true);
    disableInputElement(bank_name_field, true);
    disableInputElement(card_no_field, true);
    disableInputElement(paying_customer_field, true);
    disableInputElement(type_field, true);
  }
}
