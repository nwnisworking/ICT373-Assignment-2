package ict373.assignment2.controllers.publication;

import ict373.assignment2.publications.Publication;
import ict373.assignment2.customers.Customer;
import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.utils.BaseController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <p><strong>CustomerController class</strong></p>
 * 
 * <p>Controller for displaying customers who have subscribed to a specific publication.</p>
 */
class CustomerController extends BaseController implements Initializable{
  /**
   * Search field for filtering customers by name.
   */
  @FXML
  private TextField search_field;
  
  /**
   * TableView for displaying the list of customers.
   */
  @FXML
  private TableView<Customer> table_view;
  
  /**
   * TableColumn for displaying customer IDs.
   */
  @FXML
  private TableColumn<Customer, Integer> id_column;

  /**
   * TableColumn for displaying customer names.
   */
  @FXML
  private TableColumn<Customer, String> name_column;
  
  /**
   * TableColumn for displaying customer email addresses.
   */
  @FXML
  private TableColumn<Customer, String> email_column;
  
  /**
   * TableColumn for displaying customer actions. This is not used in this context.
   */
  @FXML
  private TableColumn<Customer, Void> action_column;
  
  /**
   * TableColumn for displaying customer types.
   */
  @FXML
  private TableColumn<Customer, String> type_column;
  
  /**
   * Button to perform an action (not used in this context).
   */
  @FXML
  private Button btn;
  
  /**
   * Publication for which customers are being displayed.
   */
  private final Publication publication;

  /**
   * Constructor for CustomerController.
   * @param publication the publication for which customers are being displayed
   */
  public CustomerController(Publication publication){
    this.publication = publication;
  }

  /**
   * Initializes the controller by setting up the customer list filtered by the publication.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources){
    CustomerModel model = CustomerModel.getInstance();
    FilteredList<Customer> custs = new FilteredList<>(
      model.getData(),
      e->e.getPublications().contains(publication)
    );

    search_field.textProperty().addListener((obs, o, n)->{
      custs.setPredicate(p->{
        if(!p.getPublications().contains(publication)){
          return false;
        }
        
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
    btn.setVisible(false);
    action_column.setVisible(false);
    
    table_view.setItems(custs);
  }
}
