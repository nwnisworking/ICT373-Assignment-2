package ict373.assignment2.controllers.customer;

import ict373.assignment2.events.*;
import ict373.assignment2.services.CustomerService;
import ict373.assignment2.models.customers.*;
import ict373.assignment2.services.SubscriptionService;
import ict373.assignment2.ui.table.DataTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Pagination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * <strong>CustomerController class</strong>
 * 
 * <p>The CustomerController class is responsible for managing the customer view, including displaying a list
 * of customers, handling search functionality, and managing the display of customer details.</p>
 */
public class CustomerController implements Initializable{
  /**
   * The total number of rows to display per page in the customer table. 
   */
  private static final int TOTAL_ROWS = 10;

  /**
   * The maximum page to display.
   */
  private static final int MAX_PAGE = 5;
  
  /**
   * The content holding the header, table view, and detail.
   */
  @FXML
  private BorderPane content;
 
  /**
   * The header section of the customer view, which contain a search bar and an add button. 
   */
  @FXML
	private HBox header;
  
  /**
   * Pagination control for navigating through pages of customers in the table view.
   */
  @FXML
	private Pagination pagination;
	
  /**
   * The detail section of the customer view, which displays detailed information about a selected customer and allows
   * for editing or adding new customers.
   */
	@FXML
	private BorderPane detail;

  /**
   * The table view that displays the list of customers in a table format. 
   */
	@FXML
	private DataTableView<Customer> table;

  /**
   * The CustomerService instance used to manage customer data, including retrieving, adding, and removing customers.
   */
  private final CustomerService customer_service = CustomerService.getInstance(); 
  
  /**
   * The filtered list of customers. This list is used to implement the search functionality, allowing the table 
   * to match customer based on the search criteria.
   */
  private final FilteredList<Customer> filtered_list = new FilteredList<>(customer_service.getObservableList());

  /**
   * The page item to be displayed in the table view. This list is updated based on the current index and the search criteria, 
   * ensuring that only the relevant customers are shown in the table view for the current page.
   */
	private final ObservableList<Customer> page_items = FXCollections.observableArrayList();
  
  /**
   * The SubscriptionService instance used to manage subscription data, including retrieving, adding, and removing subscriptions related to customers.
   */
  private final SubscriptionService subscription_service = SubscriptionService.getInstance();
  
  /**
   * Initialize the controller by setting up the table view, pagination, and event handlers for the customer view. 
   * 
   * @param url The location for the root object.
   * @param rb The resources used to localize the root object.
   */
	@Override
	public void initialize(URL url, ResourceBundle rb){
    table.setItems(page_items);
    pagination
    .currentPageIndexProperty()
    .addListener(this::paginationHandler);
    
    // Lazy way to toggle visibleProperty by combining managedProperty
    header.visibleProperty().bind(header.managedProperty());
    table.visibleProperty().bind(table.managedProperty());
    pagination.visibleProperty().bind(pagination.managedProperty());
    detail.visibleProperty().bind(detail.managedProperty());

    // Add event listener.
    content.addEventHandler(TableEvent.ANY, this::viewPage);
    content.addEventHandler(PageEvent.ANY, this::triggerAction);
    content.addEventHandler(CustomerEvent.ANY, this::handleCustomer);
    
    // Page count must be set up before the table view is updated.
    updatePageCount();
    updateTableView(0);
  }
  
  /**
   * Handle table events such as editing, viewing, or deleting a customer.
   * 
   * @param event The TableEvent triggered by an action in the table view.
   */
  private void viewPage(TableEvent<?> event){
    event.consume();
    String type = event.getEventType().getName();
    Customer customer = (Customer) event.getData();

    switch(type){
      case "TABLE_EDIT" -> detail.fireEvent(new PageEvent<>(PageEvent.EDIT, customer));
      case "TABLE_VIEW" -> detail.fireEvent(new PageEvent<>(PageEvent.VIEW, customer));
      case "TABLE_DELETE" -> content.fireEvent(new CustomerEvent(CustomerEvent.CUSTOMER_DELETED, customer));
    }    
  }
  
  /**
   * Handle page events such as navigating back from the detail view or showing the detail view for editing or adding a customer.
   * 
   * @param event The PageEvent triggered by an action in the detail view.
   */
  private void triggerAction(PageEvent<?> event) {
    event.consume();
    
    if(PageEvent.BACK.equals(event.getEventType())){
      displayDetail(false);
    }
    else{
      displayDetail(true);
    }
  }

  /**
   * Handle customer events such as creating or deleting a customer.
   * 
   * @param event The CustomerEvent triggered by an action related to customer management.
   */
  private void handleCustomer(CustomerEvent event){
    event.consume();
    
    Customer customer = event.getCustomer();
    EventType<? extends Event> event_type = event.getEventType();
    
    if(customer == null){
      System.out.println("[Customer]: Invalid customer event data passed");
      return;
    }
    
    if(event_type.equals(CustomerEvent.CUSTOMER_CREATED)){
      customer_service.add(customer);
      content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, customer + " added"));
      updatePageCount();
      updateTableView(0);
    }
    else if(event_type.equals(CustomerEvent.CUSTOMER_DELETED)){
      if(customer instanceof PayingCustomer pc){
        for(AssociateCustomer ac : pc.getAssociates()){
          subscription_service.remove(ac);
        }
      }

      customer_service.remove(customer);      
      content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, customer + " deleted"));
      updatePageCount();
      updateTableView(pagination.getCurrentPageIndex());
    }
    else{
      content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, customer + " data modified"));
    }
  }
  
  /**
   * Handle pagination changes by updating the table view to display the correct set of customers.
   */
  private void paginationHandler(Observable obs, Number old_value, Number new_value){
    updateTableView(new_value.intValue());
  }

  /**
   * Update the customer data inside the table. 
   * @param new_value The new page index to display in the table view.
   */
  private void updateTableView(int new_value){
    System.out.println("[Customer]: Navigated to page " + (new_value + 1));

    int from = new_value * TOTAL_ROWS;
		int to = Math.min(from + TOTAL_ROWS, filtered_list.size());

		page_items.setAll(filtered_list.subList(from, to));
  }
  
  /**
   * Handle the action of adding a new customer by firing a PageEvent to show the detail view for adding a customer.
   */
  @FXML
  private void addCustomer(){
    detail.fireEvent(new PageEvent<>(PageEvent.ADD, null));
  }
  
  /**
   * Handle the search action by filtering the customer list based on the search input and updating the table view accordingly.
   * 
   * @param event The KeyEvent triggered by typing in the search field.
   */
  @FXML
  private void searchCustomer(KeyEvent event){
    TextField search_field = (TextField) event.getTarget();
		String text = search_field.getText();

    filtered_list.setPredicate(e -> text.isEmpty() || (!text.isEmpty() && e.getName().contains(text)));
		updatePageCount();
    updateTableView(0);
  }
  
  /**
   * Display the detail view for a customer, hiding the main table view and header when the detail view is shown, and showing them again when the detail view is hidden.
   * 
   * @param show A boolean indicating whether to show the detail view (true) or hide it (false).
   */
  public void displayDetail(boolean show){
		header.setManaged(!show);
		table.setManaged(!show);
		pagination.setManaged(!show);
		detail.setManaged(show);
	}
  
  /**
   * Update the page count for the pagination control based on the size of the filtered customer list and the total number of rows per page.
   */
  private void updatePageCount(){
    int page_count = Math.max(1, (int) Math.ceil((double) filtered_list.size() / TOTAL_ROWS));
    pagination.setPageCount(page_count);
    pagination.setMaxPageIndicatorCount(MAX_PAGE);
  }
}