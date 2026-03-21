package ict373.assignment2.controllers.customer;
import ict373.assignment2.App;
import ict373.assignment2.models.Address;
import ict373.assignment2.models.CustomerSubscription;
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
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
  
  /**
   * The button for adding an associate customer to a paying customer's list of associates. 
   */
  @FXML
  private Button add_associate_btn;
  
  /**
   * The associate table that holds the list of associate customers linked to a paying customer.
   */
  @FXML
  private DataTableView<AssociateCustomer> associate_table;

  /**
   * The button for adding a publication to the customer's subscription list.
   */
  @FXML
  private Button add_subscription_btn;
  
  /**
   * The subscription table that holds the list of publications that the customer is currently subscribed to.
   */
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
   * The billing tab that contains the billing information for a paying customer, including the list of subscriptions and the total amount due.
   */
  @FXML
  private Tab billing_tab;

  /**
   * The billing table that displays the list of subscriptions for the paying customer and their associates.
   */
  @FXML
  private TableView<CustomerSubscription> billing_table;

  /**
   * The text field for the total billing amount for the payer. 
   */
  @FXML
  private TextField billing_amount_field;
  
  /**
   * The text field for the weekly billing amount for the payer.
   */
  @FXML
  private TextField billing_week_field;
  
  /**
   * The text field for the total billing amount for the payer, which is calculated based on the subscriptions of the paying customer and their associates.
   */
  @FXML
  private TextField billing_total_field;
  
  /**
   * The date input field for the next billing date for the payer.
   */
  @FXML
  private DateInputField billing_date_field;
  
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
   * The SubscriptionService instance used to manage subscription data.
   */
  private SubscriptionService subscription_service = SubscriptionService.getInstance();
  
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
    
    // Disable billing fields as they are used for display purposes.
    billing_amount_field.setDisable(true);
    billing_total_field.setDisable(true);
    billing_week_field.setDisable(true);
    billing_date_field.setDisable(true);
    billing_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
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

    this.customer = customer;
    
    profile.payer().setItems(
      CustomerService
      .getInstance()
      .getObservableList()
      .filtered(PayingCustomer.class::isInstance)
    );

    ArrayList<Publication> subs = subscription_service.get(customer);
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
    profile.type().setDisable(!PageEvent.ADD.equals(event.getEventType()));
    payment.method().setDisable(!PageEvent.ADD.equals(event.getEventType()));

    if(customer instanceof PayingCustomer pc){
      payment.load(pc.getMethod());
      payment.fields().forEach(e -> e.setDisable(disabled));
      loadBillingInfo();
    }
      
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
    boolean is_new = customer == null;
    
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
          PayingCustomer pc = new PayingCustomer();
          customer = pc;
          Method method = switch(payment.method().getValue()){
            case "Credit Card" -> new CreditCard();
            case "Direct Debit" -> new DirectDebit();
            default -> new DirectDebit();
          };
          
          pc.setMethod(method);
        break;
        case "Associate Customer" : customer = new AssociateCustomer();
      }
    }

    customer.setAddress(new Address());

    profile.save(customer);
    address.save(customer.getAddress());

    if(customer instanceof PayingCustomer pc){
      payment.save(pc.getMethod());
      
      pc.removeAllAssociates();
      
      associates.forEach(pc::addAssociate);
      pc.getAssociates().removeIf(e -> !associates.contains(e));
    }
    
    ArrayList<Publication> user_subscription = subscription_service.get(customer);

    if(user_subscription != null){
      user_subscription.clear();
      user_subscription.addAll(subscriptions);
    }
    else{
      subscriptions.forEach(pub -> subscription_service.add(customer, pub));
    }
    
    if(is_new){
      detail_panel.fireEvent(new CustomerEvent(CustomerEvent.CUSTOMER_CREATED, customer));
    }
    else{
      detail_panel.fireEvent(new CustomerEvent(CustomerEvent.CUSTOMER_EDITED, customer));
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
    tabs.removeAll(profile_tab, payment_tab, address_tab, associate_tab, subscription_tab, billing_tab);

    if(is_paying){
      tabs.addAll(profile_tab, address_tab, payment_tab, associate_tab, subscription_tab, billing_tab);
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
    
    if(c == null || !(customer instanceof PayingCustomer)) return;
    
    associates.remove(c);
    loadBillingInfo();
    
    detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.INFO, c + " deleted from paying customer"));
  }
  
  /**
   * Delete a publication from the customer's subscription list.
   * 
   * @param event The TableEvent that contains the publication to be deleted from the customer's subscription list.
   */
  private void deleteSubscription(TableEvent<?> event){
    event.consume();
    
    Publication p = (Publication) event.getData();
    subscriptions.remove(p);
    
    if(p instanceof Magazine m){
      if(!subscriptions.contains(m)){
        subscriptions.removeIf(e -> e instanceof Supplement s && m.equals(s.getMagazine()));
      }
      
    }
    
    loadBillingInfo();
    
    detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.INFO, p + " removed from customer's subscription"));
  }
  
  /**
   * Trigger the selection of associate customers via a modal.
   */
  @FXML
  private void triggerAssociateSelection(){
    if(customer == null){
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.WARN, "Customer needs to be created before associate can be added."));
      return;
    }
    
    ObservableList<Customer> master_list = CustomerService
    .getInstance()
    .getObservableList();
    FilteredList<Customer> filtered_list = master_list.filtered(e -> {
      if(!(e instanceof AssociateCustomer ac)) return false;
      
      return !associates.contains(ac) && 
      (ac.getPayer() == null || ac.getPayer().equals(customer));  
    });
    
    if(filtered_list.isEmpty()){
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.WARN, "No available associates found."));
      return;
    }
    
    DataTableView<Customer> table = App.loadFXML("controllers/customer/Table", null);

    table.setItems(filtered_list);
    table.setShow(DataTableView.Button.ADD);
    
    // The lambda expression requires master_list, hence, it cannot be turned
    // into a method itself.
    table.addEventHandler(TableEvent.ADD, e -> {
      AssociateCustomer ac = (AssociateCustomer) e.getData();
      
      associates.add(ac);
      loadBillingInfo();
      detail_panel.fireEvent(new ModalEvent(ModalEvent.CLOSE, null));
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.INFO, "Associate added to " + customer));
    });
    
    detail_panel.fireEvent(new ModalEvent(ModalEvent.OPEN, table));
  }
  
  /**
   * Trigger the selection of publications for the customer's subscription list via a modal.
   */
  @FXML
  private void triggerSubscriptionSelection(){
    if(customer instanceof AssociateCustomer ac && ac.getPayer() == null){
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.WARN, "Associate needs a payer."));
      return;
    }
    
    if(customer == null){
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.WARN, "Customer needs to be created before subscription can be added."));
      return;
    }
    
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

    if(filtered_list.isEmpty()){
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.WARN, "No available publications found."));
      return;
    }
    
    DataTableView<Publication> table =  App.loadFXML("controllers/publication/Table", null);

    table.setItems(filtered_list);
    table.setShow(DataTableView.Button.ADD);
    
    // The lambda expression requires master_list, hence, it cannot be turned
    // into a method itself.
    table.addEventHandler(TableEvent.ADD, e -> {
      Publication p = (Publication) e.getData();
      
      subscriptions.add(p);
      loadBillingInfo();
      detail_panel.fireEvent(new ModalEvent(ModalEvent.CLOSE, null));
      detail_panel.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.INFO, "Publication added to " + customer + "'s subscription"));
    });
    
    detail_panel.fireEvent(new ModalEvent(ModalEvent.OPEN, table));
  }
  
  /**
   * Load the billing information for the paying customer, including the list of subscriptions for the customer and their associates, the total amount due, and the next billing date. 
   */
  private void loadBillingInfo(){
    if(customer == null || !(customer instanceof PayingCustomer pc)) return;

    ObservableList<CustomerSubscription> billing_info = FXCollections.observableArrayList();
    ArrayList<Customer> customers = new ArrayList<>(associates);
    LocalDate current_date = LocalDate
    .now()
    .with(TemporalAdjusters.lastDayOfMonth());
    LocalDate payment_date = LocalDate
    .now()
    .plusMonths(1)
    .with(TemporalAdjusters.firstDayOfMonth())
    ;
    double[] total = {0};
    int total_week = (int) Math.ceil(current_date.getDayOfMonth() / 7.0);
    
    // This subscription is a pending subscription for the current user.
    subscriptions.forEach(pub -> {
      billing_info.add(new CustomerSubscription(customer, pub));
      total[0] += pub.getCost();
    });
        
    for(Customer c : customers){
      ArrayList<Publication> subs = subscription_service.get(c);
      
      if(subs != null){
        subs.forEach(pub -> {
          billing_info.add(new CustomerSubscription(c, pub));
          total[0] += pub.getCost();
        });
      }

      
    }
    
    billing_table.setItems(billing_info);
    billing_amount_field.setText("$" + total[0] + "");
    billing_week_field.setText(total_week + "");
    billing_total_field.setText("$" + (total[0] * total_week) + "");
    billing_date_field.setValue(payment_date);
  }
}