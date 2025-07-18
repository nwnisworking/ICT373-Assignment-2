package ict373.assignment2.controllers.customer;

import ict373.assignment2.customers.*;
import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.utils.BaseController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * <p><strong>CustomerController class</strong></p>
 * 
 * <p>Controller for managing customer-related actions and views.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename CustomerController.java
 */
public class CustomerController extends BaseController implements Initializable{
  /**
   * Search field for filtering customers by name.
   */
  @FXML
  private TextField search_field;
  
  /**
   * Table view to display the list of customers by their ID, name, email, type, and action.
   */
  @FXML
  private TableView<Customer> table_view;
  
  /**
   * Table columns for displaying customer ID.
   */
  @FXML
  private TableColumn<Customer, Integer> id_column;

  /**
   * Table columns for displaying customer name.
   */
  @FXML
  private TableColumn<Customer, String> name_column;
  
  /**
   * Table columns for displaying customer email.
   */
  @FXML
  private TableColumn<Customer, String> email_column;
  
  /**
   * Table column for displaying action buttons (edit, delete, view).
   */
  @FXML
  private TableColumn<Customer, Void> action_column;
  
  /**
   * Table column for displaying the type of customer (Associate or Paying).
   */
  @FXML
  private TableColumn<Customer, String> type_column;
  
  /**
   * Button to add a new customer.
   */
  @FXML
  private Button btn;
  
  /**
   * Model instance for managing customer data.
   */
  private CustomerModel model;

  /**
   * Initializes the controller by setting up the table view and loading customer data.
   */
  @FXML 
  public void initialize(URL location, ResourceBundle resources){
    model = CustomerModel.getInstance();
    
    FilteredList<Customer> filter_by_name = new FilteredList<>(model.getData());
    
    btn.setOnAction(e->addCustomerPage());
            
    search_field.textProperty().addListener((obs, o, n)->{
      filter_by_name.setPredicate(p->{
        if(n == null || n.isBlank()){
          return true;
        }
        
        return p.getName().toLowerCase().contains(n);
      });
    });
    
    id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
    name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
    email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
    type_column.setCellValueFactory(new PropertyValueFactory<>("customerType"));
    action_column.setCellFactory(param->new TableCell<>(){
      private final Button edit_btn = new Button("Edit");
      
      private final Button delete_btn = new Button("Delete");
      
      private final Button view_btn = new Button("View");
      {
        edit_btn.setOnAction(e->editCustomerPage(getTableRow().getItem()));
        delete_btn.setOnAction(e->deleteCustomer(getTableRow().getItem()));
        view_btn.setOnAction(e->viewCustomerPage(getTableRow().getItem()));
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        
        if(empty){
          setGraphic(null);
        }
        else{
          HBox box = new HBox(10, view_btn, edit_btn, delete_btn);
          box.setAlignment(Pos.CENTER);
          setGraphic(box);
        }
      }
    });

    table_view.setItems(filter_by_name);
  }
  
  /**
   * Opens a popup to add a new customer.
   */
  private void addCustomerPage(){
    popup("Add Customer", "popups/customer.fxml", new PopupController(null, null));
  }
  
  /**
   * Opens a popup to edit the details of a customer.
   * @param cust The customer whose details are to be edited.
   */
  private void editCustomerPage(Customer cust){
    popup("Edit Customer", "popups/customer.fxml", new PopupController(cust, "edit"));
  }
  
  /**
   * Opens a popup to view the details of a customer.
   * @param cust The customer whose details are to be viewed.
   */
  private void viewCustomerPage(Customer cust){
    popup("View Customer", "popups/customer.fxml", new PopupController(cust, "view"));
  }
  
  /**
   * Deletes a customer from the model and updates the associations if necessary.
   * @param cust The customer to be deleted.
   */
  private void deleteCustomer(Customer cust){
    model.getData().remove(cust);
    
    if(cust instanceof AssociateCustomer){
      AssociateCustomer ac = (AssociateCustomer) cust;
      
      if(ac.getPayer() != null){
        ac.getPayer().removeAssociate(ac);
      }
    }
    else{
      PayingCustomer pc = (PayingCustomer) cust;
      pc.getAssociates().forEach(e->e.setPayer(null));
    }
  }
}