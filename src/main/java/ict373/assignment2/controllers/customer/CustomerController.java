package ict373.assignment2.controllers.customer;

import ict373.assignment2.events.ButtonEvent;
import ict373.assignment2.events.RowEvent;
import ict373.assignment2.services.CustomerService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ict373.assignment2.models.customers.*;
import ict373.assignment2.ui.table.DataTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class CustomerController implements Initializable{
  private static final int TOTAL_ROWS = 10;
 
  @FXML
	private HBox header;
  
  @FXML
	private Pagination pagination;
	
	@FXML
	private BorderPane detail;

	@FXML
	private DataTableView<Customer> table;
  
  @FXML
	private CustomerDetailController detailController;
  
  private final CustomerService customer_service = CustomerService.getInstance(); 
  
  private final FilteredList<Customer> filtered_list = new FilteredList<>(customer_service.getObservableList());

	private final ObservableList<Customer> page_items = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb){
    table.setItems(page_items);
    
    pagination
    .currentPageIndexProperty()
    .addListener((obs, o, n) -> updateTableView(n.intValue()));
    
    detail.setUserData(this);
    
    updatePagination();
  }
  
  @FXML
  private void addCustomer(){
    displayDetail(true);
    loadDetail(null, "Add");
  }
  
  @FXML
  private void searchCustomer(KeyEvent event){
    TextField search_field = (TextField) event.getTarget();
		String text = search_field.getText();

    filtered_list.setPredicate(e -> text.isEmpty() || (!text.isEmpty() && e.getName().contains(text)));
		updatePagination();
  }
  
  @FXML
  private void buttonClicked(ButtonEvent<Customer> event){
    Customer customer = event.getItem();
    
    if(event.getType().equals("Delete")){
      customer_service.remove(customer.getId());
      updateTableView(pagination.getCurrentPageIndex());

      System.out.println("[Customer]: Customer " + customer + " deleted");

      return;
    }
    
    loadDetail(customer, event.getType());
  }
  
  @FXML
  private void rowClicked(RowEvent<Customer> event){
    loadDetail(event.getItem(), event.getType());
  }
  
  public void displayDetail(boolean show){
		header.setVisible(!show);
		header.setManaged(!show);
		
		table.setVisible(!show);
		table.setManaged(!show);
		
		pagination.setVisible(!show);
		pagination.setManaged(!show);
		
		detail.setVisible(show);
		detail.setManaged(show);
	}
  
  public void loadDetail(Customer customer, String mode){
    displayDetail(true);
		detailController.loadDetail(customer, mode);
	}
  
	public void updatePagination(){
		// At least 1 page should be shown even if there is no data. 
		// Also, any remainder should be rounded up to the next page. 
		int page_count = Math.max(1, (int) Math.ceil((double) filtered_list.size() / TOTAL_ROWS));
		
		pagination.setPageCount(page_count);
		pagination.setCurrentPageIndex(0);
		pagination.setMaxPageIndicatorCount(5);
		
		updateTableView(0);
	}
  
	private void updateTableView(int index){
		int from = index * TOTAL_ROWS;
		int to = Math.min(from + TOTAL_ROWS, filtered_list.size());

		page_items.setAll(filtered_list.subList(from, to));
	}
}