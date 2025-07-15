package ict373.assignment2.controllers.customer;

import ict373.assignment2.customers.*;
import ict373.assignment2.utils.BaseController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <p><strong>AssociateController class</strong></p>
 * 
 * <p>Displays the associates of a paying customer.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename AssociateController.java
 */
class AssociateController extends BaseController implements Initializable{
  /**
   * Search field for filtering associates by name.
   */
  @FXML
  private TextField search_field;
  
  /**
   * Table view to display the list of associates by their ID, name, email, and type.
   */
  @FXML
  private TableView<AssociateCustomer> table_view;
  
  /**
   * Table columns for displaying associate ID.
   */
  @FXML
  private TableColumn<AssociateCustomer, Integer> id_column;

  /**
   * Table columns for displaying associate name.
   */
  @FXML
  private TableColumn<AssociateCustomer, String> name_column;
  
  /**
   * Table columns for displaying associate email.
   */
  @FXML
  private TableColumn<AssociateCustomer, String> email_column;
  
  /**
   * Table column for displaying action buttons (not used in this mode).
   * 
   */
  @FXML
  private TableColumn<AssociateCustomer, Void> action_column;
  
  /**
   * Table column for displaying the type of customer (Associate or Paying).
   */
  @FXML
  private TableColumn<AssociateCustomer, String> type_column;
  
  /**
   * Observable list of associates, which can be empty if the customer is null or not a PayingCustomer.
   */
  private final ObservableList<AssociateCustomer> associates;
  
  /**
   * A button for performing actions on the associates (not used in this mode).
   */
  @FXML
  private Button btn;

  /**
   * Constructor for AssociateController.
   * @param customer The customer whose associates are to be managed.
   */
  public AssociateController(Customer customer){
    if(customer == null || customer instanceof AssociateCustomer){
      associates = FXCollections.observableList(new ArrayList<>());
    }
    else{
      associates = ((PayingCustomer) customer).getObservableAssociates();
    }
  }
  
  /**
   * Initializes the controller by setting up the table view and search functionality.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources){
    FilteredList<AssociateCustomer> assocs = new FilteredList<>(associates);

    search_field.textProperty().addListener((obs, o, n)->{
      assocs.setPredicate(p->{
        if(n == null || n.isEmpty()){
          return true;
        }

        return p.getName().toLowerCase().contains(n);
      });
    });
    
    id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
    name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
    email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
    type_column.setCellValueFactory(new PropertyValueFactory<>("customerType"));
    action_column.setVisible(false);
    btn.setVisible(false);
    table_view.setItems(assocs);
  }
}
