package ict373.assignment2.controllers.customer;

import java.net.URL;
import java.util.ResourceBundle;

import ict373.assignment2.controllers.customer.tabs.*;
import ict373.assignment2.events.ButtonEvent;
import ict373.assignment2.models.Address;
import ict373.assignment2.models.customers.*;
import ict373.assignment2.models.payments.*;
import ict373.assignment2.services.CustomerService;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.ui.table.DataTableView;
import java.time.LocalDate;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class CustomerDetailController implements Initializable{
  @FXML
  private Button back_btn;
  
  @FXML
  private Button save_btn;
  
  @FXML
  private Label detail_title;
  
	@FXML
	private BorderPane detail_panel;
  
  @FXML
  private TabPane detail_tabpane;

  @FXML
  private Tab profile_tab;
  
	@FXML
	private TextInputField name_field;

	@FXML
	private TextInputField email_field;

	@FXML
	private SelectInputField<String> type_field;

	@FXML
	private SelectInputField<Customer> payer_field;

  @FXML
  private Tab payment_tab;
  
  @FXML
  private SelectInputField<String> payment_method_field;
  
  @FXML
  private TextInputField card_number_field;
  
  @FXML
  private DateInputField expiry_date_field;
  
  @FXML
  private TextInputField account_number_field;
  
  @FXML
  private TextInputField bank_name_field;
  
  @FXML
  private Tab address_tab;
  
  @FXML
  private TextInputField street_name_field;
  
  @FXML
  private TextInputField block_field;
  
  @FXML
  private TextInputField postal_field;
  
  @FXML
  private TextInputField unit_number_field;
  
  @FXML
  private Tab associate_tab;
  
  @FXML
  private DataTableView<AssociateCustomer> associate_table;

  private ProfileTab profile;

  private AddressTab address;

  private PaymentTab payment;
  
  private Customer customer = null;
  
	@Override
	public void initialize(URL url, ResourceBundle rb){
    profile = new ProfileTab(name_field, email_field, type_field, payer_field);
    address = new AddressTab(street_name_field, block_field, postal_field, unit_number_field);
    payment = new PaymentTab(payment_method_field, card_number_field, expiry_date_field, account_number_field, bank_name_field);

    profile.type().valueProperty().addListener(this::toggleCustomerType);
    payment.method().valueProperty().addListener(this::togglePaymentMethod);
    save_btn.visibleProperty().bind(save_btn.managedProperty());
    
    toggleCustomerType(null, "", profile.type().getValue());
    togglePaymentMethod(null, "", payment.method().getValue());
	}
  
	public void load(Customer customer, String action){
    detail_title.setText(action + " Customer");
    // Very important to populate the data here.
    profile.payer().setItems(
      CustomerService
      .getInstance()
      .getObservableList()
      .filtered(PayingCustomer.class::isInstance)
    );
    
    boolean disabled = false;
    customer = this.customer;
    
    switch(action){
      case "Add" : return;
      case "View" : disabled = true;
      case "Edit" : 
        boolean d = disabled;
        associate_table.setShow(disabled ? DataTableView.Button.NONE : DataTableView.Button.DELETE);
        save_btn.setManaged(!disabled);
        
        profile.load(customer);
        address.load(customer.getAddress());
        
        if(customer instanceof PayingCustomer pc){
          payment.load(pc.getMethod());
          payment.fields().forEach(e -> e.setDisable(d));
          associate_table.setItems(FXCollections.observableArrayList(pc.getAssociates()));
        }
        
        profile.fields().forEach(e -> e.setDisable(d));
        address.fields().forEach(e -> e.setDisable(d));
        profile.type().setDisable(true);
      break;
    }
	}
  
  @FXML
  public void back(){
    CustomerController controller = (CustomerController) detail_panel.getUserData();
    controller.displayDetail(false);
    controller.updatePagination();

    save_btn.setManaged(true);

    detail_tabpane.getSelectionModel().selectFirst();
    
    customer = null;
    
    clear();
  }

  @FXML
  public void save(){    
    SingleSelectionModel<Tab> selection_model = detail_tabpane.getSelectionModel();
    Method method = null;
    boolean is_new = customer == null;
    
    if(!validateField(profile_tab, profile.name(), "Name field must not be empty")) return;
    if(!validateField(profile_tab, profile.email(), "Email field must not be empty")) return;
        
    if(!validateField(address_tab, address.street(), "Street name must not be empty")) return;
    if(!validateField(address_tab, address.block(), "Address block must not be empty")) return;
    if(!validateNumberField(address_tab, address.block(), "Address block must be a number")) return;
    if(!validateField(address_tab, address.postal(), "Postal code must not be empty")) return;
    if(!validateNumberField(address_tab, address.postal(), "Postal code must be a number")) return;
    if(!validateField(address_tab, address.unit(), "Unit number must not be empty")) return;

    if("Paying Customer".equals(profile.type().getValue())){
      switch(payment.method().getValue()){
        case "Credit Card" -> {
          if(!validateField(payment_tab, payment.card_number(), "Card number must not be empty")) return;
          if(!validateField(payment_tab, payment.expiry_date(), "Expiry date must not be empty")) return;
          if(LocalDate.now().isAfter(payment.expiry_date().getValue())){ 
            selection_model.select(payment_tab);
            payment.expiry_date().displayTooltip("Credit card has already expired"); 
            return;
          }
          
          method = new CreditCard();
        }
        case "Direct Debit" -> {
          if(!validateField(payment_tab, payment.account_number(), "Account number must not be empty")) return;
          if(!validateField(payment_tab, payment.bank_name(), "Bank name must not be empty")) return;

          method = new DirectDebit();
        }
      }
    }
    
    if(customer == null){
      customer = switch(profile.type().getValue()){
        case "Paying Customer" -> new PayingCustomer();
        case "Associate Customer" -> new AssociateCustomer();
        default -> new AssociateCustomer();
      };
      
      customer.setAddress(new Address());

      CustomerService.getInstance().add(customer);
    }

    profile.save(customer);
    address.save(customer.getAddress());
    
    if(customer instanceof PayingCustomer pc){
      pc.setMethod(method);
      payment.save(pc.getMethod());
    }
    
    if(is_new)
      System.out.println("[Customer]: Customer " + customer.getName() +  " added to the service");
    else
      System.out.println("[Customer]: Customer " + customer.getName() + " data modified");

    back();
  }
  
  @FXML
  public void deleteAssociateCustomer(ButtonEvent<Customer> event){
    if(customer == null || customer instanceof AssociateCustomer) return;
    
    PayingCustomer pc = (PayingCustomer) customer;
    AssociateCustomer ac = (AssociateCustomer) event.getItem();
    
    pc.removeAssociate(ac);
    
    // The table uses an ObservableList that is created upon loading.
    // This means the item needs to be manually deleted.
    associate_table.getItems().remove(ac);
    
    System.out.println("[Customer]: Customer " + pc.getName() + " deleted associate " + ac.getName());
  }
  
  public void clear(){
    for(InputField<?, ?> field : profile.fields()){
      field.reset();
    }
    
    for(InputField<?, ?> field : address.fields()){
      field.reset();
    }
    
    for(InputField<?, ?> field : payment.fields()){
      field.reset();
    }
    
    // These are the outlier which requires manual handling
    profile.type().setValue("Paying Customer");
    profile.payer().setItems(FXCollections.observableArrayList());
    payment.method().setValue("Credit Card");
    
    associate_table.setItems(FXCollections.observableArrayList());
  }
  
  private void togglePaymentMethod(Observable obs, String old_value, String new_value){
    switch(new_value){
      case "Credit Card" -> {
        payment.card_number().setManaged(true);
        payment.expiry_date().setManaged(true);
        payment.account_number().setManaged(false);
        payment.bank_name().setManaged(false);
      }
      case "Direct Debit" -> {
        payment.card_number().setManaged(false);
        payment.expiry_date().setManaged(false);
        payment.account_number().setManaged(true);
        payment.bank_name().setManaged(true);
      }
    } 
  }
  
  private void toggleCustomerType(Observable obs, String old_value, String new_value){
    boolean is_paying = "Paying Customer".equals(new_value);
    ObservableList<Tab> tabs = detail_tabpane.getTabs();
     
     payer_field.setManaged(!is_paying);
      
     if(is_paying){
       if(!tabs.contains(payment_tab))
         tabs.add(2, payment_tab);
       
       if(!tabs.contains(associate_tab))
         tabs.add(3, associate_tab);
     }
     else{
       tabs.removeAll(payment_tab, associate_tab);
     }
  }

  private <T, C extends Control> boolean validateField(Tab tab, InputField<T, C> field, String message){
    if(field.isEmpty()){
      detail_tabpane.getSelectionModel().select(tab);
      field.displayTooltip(message); 
      System.out.println("[Customer]: Validation failed");

      return false;
    }

    return true;
  }

  private <T, C extends Control> boolean validateNumberField(Tab tab, InputField<T, C> field, String message){
    if(!field.isNumber()){
      detail_tabpane.getSelectionModel().select(tab);
      field.displayTooltip(message); 
      
      System.out.println("[Customer]: Validation failed");
      
      return false;
    }

    return true;
  }
}