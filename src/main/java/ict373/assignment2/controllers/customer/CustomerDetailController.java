package ict373.assignment2.controllers.customer;
import ict373.assignment2.App;
import ict373.assignment2.models.Address;
import ict373.assignment2.models.customers.*;
import ict373.assignment2.models.publications.*;
import ict373.assignment2.models.payments.*;
import ict373.assignment2.FormRecord;
import ict373.assignment2.controllers.customer.forms.*;
import ict373.assignment2.events.*;
import ict373.assignment2.services.*;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.ui.table.DataTableView;
import ict373.assignment2.utils.Validator.ValidatorResult;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * <strong>CustomerDetailController class</strong>
 * 
 * <p>The CustomerDetailController class is responsible for managing the customer detail view,
 * which displays detailed information about a selected customer and allows for editing or adding new customers.</p>
 * 
 * @author nwnisworking
 * @date 15/3/2026
 * @filename CustomerDetailController.java
 */
public class CustomerDetailController implements Initializable{
  /**
   * The save button for the customer detail view.
   */
  @FXML
  private Button save_btn;
  
  /**
   * The title label for the customer detail view which indicates whether the user is adding, editing, or viewing a customer.
   */
  @FXML
  private Label detail_title;

  /**
   * The detail panel that contains the customer information fields. This panel is updated based on 
   * the type of event (add, edit, view) triggered and the customer data passed in the event.
   */
  @FXML
  private BorderPane detail_panel;
  
  /**
   * The text field for the customer's name.
   */
  @FXML
	private TextInputField name_field;

  /**
   * The text field for the customer's email address.
   */
	@FXML
	private TextInputField email_field;

  /**
   * The select input field for the customer's type (e.g., Paying Customer, Associate Customer). 
   */
	@FXML
	private SelectInputField<String> type_field;

  /**
   * The payer field for the customer's payer. This field is only relevant for associate customers.
   */
	@FXML
	private SelectInputField<Customer> payer_field;

  /**
   * The select input field for the customer's payment method (e.g., Credit Card, Direct Debit). This field is only relevant for paying customers.
   */
  @FXML
  private SelectInputField<String> payment_method_field;
  
  /**
   * The text field for the customer's credit card number. This field is only relevant if the payment method is Credit Card.
   */
  @FXML
  private TextInputField card_number_field;
  
  /**
   * The date input field for the customer's credit card expiry date. This field is only relevant if the payment method is Credit Card.
   */
  @FXML
  private DateInputField expiry_date_field;
  
  /**
   * The text field for the customer's bank account number. This field is only relevant if the payment method is Direct Debit.
   */
  @FXML
  private TextInputField account_number_field;
  
  /**
   * The text field for the customer's bank name. This field is only relevant if the payment method is Direct Debit.
   */
  @FXML
  private TextInputField bank_name_field;

  /**
   * The text field for the customer's street name.
   */
  @FXML
  private TextInputField street_name_field;
  
  /**
   * The text field for the customer's address block.
   */
  @FXML
  private TextInputField block_field;
  
  /**
   * The text field for the customer's postal code.
   */
  @FXML
  private TextInputField postal_field;
  
  /**
   * The text field for the customer's unit number.
   */
  @FXML
  private TextInputField unit_number_field;
  
  @FXML
  private Button add_associate_btn;
  
  /**
   * The associate table that holds the list of associate customers linked to a paying customer.
   */
  @FXML
  private DataTableView<AssociateCustomer> associate_table;

  
  @FXML
  private Button add_subscription_btn;
  
  @FXML
  private DataTableView<Publication> subscription_table;
  
  /**
   * The tab pane that contains different tabs for profile, payment, and address information.
   */
  @FXML
  private TabPane detail_tabpane;

  /**
   * The profile tab that contains the customer's profile information fields.
   */
  @FXML
  private Tab profile_tab;

  /**
   * The payment tab that contains the customer's payment information fields.
   */
  @FXML
  private Tab payment_tab;

  /**
   * The address tab that contains the customer's address information fields.
   */
  @FXML
  private Tab address_tab;
  
  /**
   * The associate tab that contains the table view of associate customers linked to a paying customer.
   */
  @FXML
  private Tab associate_tab;

  /**
   * The subscription tab that contains the subscription information for a customer.
   */
  @FXML
  private Tab subscription_tab;

  /**
   * The ProfileForm instance that manages the profile information fields such as name, email, type, and payer.
   */
  private ProfileForm profile;

  /**
   * The PaymentForm instance that manages the payment information fields such as payment method, card number, expiry date, account number, and bank name.
   */
  private PaymentForm payment;
  
  /**
   * The AddressForm instance that manages the address information fields such as street name, block, postal code, and unit number.
   */
  private AddressForm address;
  
  /**
   * The customer instance that is currently being viewed or edited in the detail view.
   */
  private Customer customer;

  /**
   * The ObservableList of publications that represents the customer's current subscriptions. 
   */
  private ObservableList<Publication> subscriptions;

  /**
   * The ObservableList of associate customers that are linked to the paying customer being viewed or edited. 
   */
  private ObservableList<AssociateCustomer> associates;
  
  /**
   * Initializes the controller class.
   * 
   * @param url The location for the root object.
   * @param rb The resources used to localize the root object.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb){
    profile = new ProfileForm(name_field, email_field, type_field, payer_field);
    payment = new PaymentForm(payment_method_field, card_number_field, expiry_date_field, account_number_field, bank_name_field);
    address = new AddressForm(street_name_field, block_field, postal_field, unit_number_field);
    
    detail_panel.addEventHandler(PageEvent.ANY, this::load);
    associate_table.addEventHandler(TableEvent.DELETE, this::deleteAssociateCustomer);
    subscription_table.addEventHandler(TableEvent.DELETE, this::deleteSubscription);
    
    // Disable TableEvent.VIEW from propagating to the top.
    subscription_table.addEventHandler(TableEvent.VIEW, e->e.consume()); 

    // Toggle the visibility of fields based on the selected payment method and customer type.
    profile.type().valueProperty().addListener(this::toggleCustomerType);
    payment.method().valueProperty().addListener(this::togglePaymentMethod);

    // Bind the visibility of buttons to their managed property to ensure they are hidden when not needed.
    save_btn.visibleProperty().bind(save_btn.managedProperty());
    add_associate_btn.visibleProperty().bind(add_associate_btn.managedProperty());
    add_subscription_btn.visibleProperty().bind(add_subscription_btn.managedProperty());

    // The initial state of the detail view which contains the delete button.
    associate_table.setShow(DataTableView.Button.DELETE);
    subscription_table.setShow(DataTableView.Button.DELETE);
    
    // Toggle the fields to the correct state based on the default values set in the FXML.
    toggleCustomerType(null, "", profile.type().getValue());
    togglePaymentMethod(null, "", payment.method().getValue());
  }
  
  /**
   * Load the customer data into the detail view based on the type of event triggered (add, edit, view).
   * 
   * @param event The PageEvent that contains the customer data and the information about the event.
   */
  private void load(PageEvent<?> event){
    boolean disabled = PageEvent.VIEW.equals(event.getEventType());
    Customer customer = (Customer) event.getData();
    Address addr = customer != null ? customer.getAddress() : null;
    String type = switch(event.getEventType().getName()){
      case "PAGE_ADD" -> "Add";
      case "PAGE_EDIT" -> "Edit";
      case "PAGE_VIEW" -> "View";
      default -> "View";
    };

    detail_title.setText(type + " Customer");
    
    profile.payer().setItems(
      CustomerService
      .getInstance()
      .getObservableList()
      .filtered(PayingCustomer.class::isInstance)
    );

    ArrayList<Publication> subs = SubscriptionService.getInstance().get(customer);
    subscriptions = FXCollections.observableArrayList(subs != null ? subs : new ArrayList<>());
    subscription_table.setItems(subscriptions);

    ArrayList<AssociateCustomer> assocs = customer instanceof PayingCustomer pc ? pc.getAssociates() : new ArrayList<>();
    associates = FXCollections.observableArrayList(assocs);
    associate_table.setItems(associates);

    save_btn.setManaged(!disabled);
    add_associate_btn.setManaged(!disabled);
    add_subscription_btn.setManaged(!disabled);

    profile.load(customer);
    address.load(addr);
        
    profile.fields().forEach(e -> e.setDisable(disabled));
    address.fields().forEach(e -> e.setDisable(disabled));
    
    // For Edit and View, type and method SHOULD BE disabled.
    profile.type().setDisable(true);
    payment.method().setDisable(true);

    if(customer instanceof PayingCustomer pc){
      payment.load(pc.getMethod());
      payment.fields().forEach(e -> e.setDisable(disabled));
    }

    this.customer = customer;
      
    associate_table.setShow(disabled ? DataTableView.Button.NONE : DataTableView.Button.DELETE);
    subscription_table.setShow(disabled ? DataTableView.Button.NONE : DataTableView.Button.DELETE);

    System.out.println("[Customer]: " + (customer == null ? "New Customer" : customer) + " loaded in " + type + " mode");
  }

  /**
   * Save the customer data from the detail view when the save button is clicked, and fire a CustomerEvent to notify other components of the change.
   */
  @FXML
  private void save(){
    List<FormRecord<?>> forms = List.of(profile, address, payment);
    // Validate all forms and display the first error encountered.
    for(FormRecord<?> form : forms){
      ValidatorResult result = form.validate();
      Tab selected_tab = null;

      // PaymentForm may be invalid if the customer is an associate customer.
      if(result.valid() || form instanceof PaymentForm && profile.type().getValue().equals("Associate Customer")) continue;

      if(form instanceof ProfileForm) selected_tab = profile_tab;
      else if(form instanceof AddressForm) selected_tab = address_tab;
      else if(form instanceof PaymentForm) selected_tab = payment_tab;

      result.field().displayTooltip(result.message());
      detail_tabpane.getSelectionModel().select(selected_tab);
      return;
    }

    if(customer == null){
      switch(profile.type().getValue()){
        case "Paying Customer" : 
          customer = new PayingCustomer();

          ((PayingCustomer) customer).setMethod(
            payment
            .method()
            .getValue()
            .equals("Credit Card") ? new CreditCard() : new DirectDebit()
          );
        break;
        case "Associate Customer" : customer = new AssociateCustomer();
      }
      
      customer.setAddress(new Address());

      detail_panel.fireEvent(new CustomerEvent(CustomerEvent.CUSTOMER_CREATED, customer));
    }
    else{
      detail_panel.fireEvent(new CustomerEvent(CustomerEvent.CUSTOMER_EDITED, customer));
    }

    profile.save(customer);
    address.save(customer.getAddress());

    if(customer instanceof PayingCustomer pc){
      payment.save(pc.getMethod());
    }

    System.out.println("[Customer]: Customer " + customer +  " saved");
    back();
  }
  
  /**
   * Go back to the previous view by firing a page event and resetting the detail view fields and state.
   */
  @FXML
  private void back(){
    add_associate_btn.setManaged(true);
    add_subscription_btn.setManaged(true);
    save_btn.setManaged(true);
    associate_table.setShow(DataTableView.Button.DELETE);
    subscription_table.setShow(DataTableView.Button.DELETE);
    detail_tabpane.getSelectionModel().selectFirst();

    customer = null;
    
    profile.fields().forEach(e -> e.reset());
    address.fields().forEach(e -> e.reset());
    payment.fields().forEach(e -> e.reset());
    
    profile.type().setValue("Paying Customer");
    profile.payer().setItems(FXCollections.observableArrayList());
    payment.method().setValue("Credit Card");

    associate_table.setItems(FXCollections.observableArrayList());
    
    detail_panel.getParent().fireEvent(new PageEvent<>(PageEvent.BACK, null));
  }

  /**
   * Toggle payment method for different types of payment.
   * 
   * @param obs The observable that triggered the event.
   * @param old_value The previous value of the payment method.
   * @param new_value The new value of the payment method that determines which payment fields to display.
   */
  private void togglePaymentMethod(Observable obs, String old_value, String new_value){
    boolean is_credit_card = "Credit Card".equals(new_value);

    payment.card_number().setManaged(is_credit_card);
    payment.expiry_date().setManaged(is_credit_card);
    payment.account_number().setManaged(!is_credit_card);
    payment.bank_name().setManaged(!is_credit_card);
  }
  
  /**
   * Toggle the customer type between paying and associate customers.
   * 
   * @param obs The observable that triggered the event.
   * @param old_value The previous value of the customer type.
   * @param new_value The new value of the customer type.
   */
  private void toggleCustomerType(Observable obs, String old_value, String new_value){
    boolean is_paying = "Paying Customer".equals(new_value);
    ObservableList<Tab> tabs = detail_tabpane.getTabs();

    profile.payer().setManaged(!is_paying);
    tabs.removeAll(profile_tab, payment_tab, address_tab, associate_tab, subscription_tab);

    if(is_paying){
      tabs.addAll(profile_tab, address_tab, payment_tab, associate_tab, subscription_tab);
    }
    else{
      tabs.addAll(profile_tab, address_tab, subscription_tab);
    }
  }
  
  /**
   * Delete an associate customer from the paying customer's list of associates.
   * 
   * @param event The TableEvent that contains the associate customer to be deleted.
   */
  private void deleteAssociateCustomer(TableEvent<?> event){
    event.consume();
    
    Customer c = (Customer) event.getData();
    
    if(c == null || !(c instanceof AssociateCustomer associate_customer) || !(customer instanceof PayingCustomer)) return;
    
    PayingCustomer paying_customer = (PayingCustomer) customer;
    
    paying_customer.removeAssociate(associate_customer);

    // The table uses an ObservableList that is created upon loading.
    // This means the item needs to be manually deleted.
    associate_table.getItems().remove(associate_customer);
    
    System.out.println("[Customer]: Customer " + associate_customer + " removed from " + paying_customer);
  }
  
  private void deleteSubscription(TableEvent<?> event){
    event.consume();
    
    Publication p = (Publication) event.getData();
    SubscriptionService subscription_service = SubscriptionService.getInstance();
    
    subscription_service.remove(customer, p);

    subscriptions.setAll(subscription_service.get(customer));
    
    System.out.println("[Customer]: " + p + " removed from " + customer + " subscription");
  }
  
  @FXML
  private void triggerAssociateSelection(){
    ObservableList<Customer> master_list = CustomerService
    .getInstance()
    .getObservableList();
    FilteredList<Customer> filtered_list = master_list.filtered(e -> {
      return e instanceof AssociateCustomer ac && ac.getPayer() == null;
    });
    
    if(filtered_list.isEmpty()) return;
    
    @SuppressWarnings("unchecked")
    DataTableView<Customer> table = (DataTableView<Customer>) App.loadFXML("controllers/customer/Table", null);

    table.setItems(filtered_list);
    table.setShow(DataTableView.Button.ADD);
    
    // The lambda expression requires master_list, hence, it cannot be turned
    // into a method itself.
    table.addEventHandler(TableEvent.ADD, e -> {
      PayingCustomer pc = (PayingCustomer) customer;
      AssociateCustomer ac = (AssociateCustomer) e.getData();
      
      pc.addAssociate(ac);
      associates.add(ac);
      
      detail_panel.fireEvent(new ModalEvent(ModalEvent.CLOSE, null));
    });
    
    detail_panel.fireEvent(new ModalEvent(ModalEvent.OPEN, table));
  }
  
  @FXML
  private void triggerSubscriptionSelection(){
    ObservableList<Publication> master_list = PublicationService
    .getInstance()
    .getObservableList();
    FilteredList<Publication> filtered_list = master_list.filtered(e -> {
      if(e instanceof Supplement s){
        return s.getMagazine() != null && // Supplement without magazine should not be added.
        subscriptions.contains(s.getMagazine()) && // Supplement whose magazine is not subscribed should not be added.
        !subscriptions.contains(s); // Supplement that is already subscribed should not be added.
      }
      
      return true;
    });

    @SuppressWarnings("unchecked")
    DataTableView<Publication> table = (DataTableView<Publication>) App.loadFXML("controllers/publication/Table", null);

    table.setItems(filtered_list);
    table.setShow(DataTableView.Button.ADD);
    
    // The lambda expression requires master_list, hence, it cannot be turned
    // into a method itself.
    table.addEventHandler(TableEvent.ADD, e -> {
      Publication p = (Publication) e.getData();
      
      SubscriptionService.getInstance().add(customer, p);
      subscriptions.add(p);
      
      detail_panel.fireEvent(new ModalEvent(ModalEvent.CLOSE, null));
    });
    
    detail_panel.fireEvent(new ModalEvent(ModalEvent.OPEN, table));
  }
  
}