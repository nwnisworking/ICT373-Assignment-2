package ict373.assignment2.controllers.customer;

import ict373.assignment2.customers.*;
import ict373.assignment2.publications.Publication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import ict373.assignment2.utils.BaseController;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <p><strong>SubscriptionController class</strong></p>
 * 
 * <p>Controller for managing the subscription of publications, allowing users to view and manage their subscriptions.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename SubscriptionController.java
 */
public class SubscriptionController extends BaseController implements Initializable{
  /**
   * The search field for filtering publications.
   */
  @FXML
  private TextField search_field;
  
  /**
   * The table view that displays the list of publications.
   */
  @FXML
  private TableView<Publication> table_view;
  
  /**
   * The table column for displaying publication ids.
   */
  @FXML
  private TableColumn<Publication, Integer> id_column;
  
  /**
   * The table column for displaying publication names.
   */
  @FXML
  private TableColumn<Publication, String> name_column;
  
  /**
   * The table column for displaying publication costs.
   */
  @FXML
  private TableColumn<Publication, Double> cost_column;
  
  /**
   * The table column for displaying publication types.
   */
  @FXML
  private TableColumn<Publication, String> type_column;
  
  /**
   * The table column for displaying action buttons (e.g., delete).
   */
  @FXML
  private TableColumn<Publication, Void> action_column;
  
  /**
   * Button to add a new publication (magazine or supplement).
   */
  @FXML
  private Button add_btn;
  
  /**
   * Subscription that holds the customer's publications.
   */
  private final ObservableList<Publication> subscriptions;
  
  private final Customer customer;

  private final String mode;
  
  public SubscriptionController(Customer customer, String mode){
    this.mode = mode;
    this.customer = customer;
    
    if(customer != null){
      subscriptions = customer.getObservablePublications();
    }
    else{
      subscriptions = FXCollections.observableList(new ArrayList<>());
    }
  }
  
  /**
   * Initializes the controller by setting up the table view and filtering functionality.
   * @param url the location used to resolve relative paths.
   * @param resources the resources used to localize the root object.
   */
  @Override
  public void initialize(URL url, ResourceBundle resources){
    FilteredList<Publication> pub = new FilteredList<>(subscriptions);
    TableColumn<Publication, String> status_column = new TableColumn<>();
    
    search_field.textProperty().addListener((obs, o, n)->{
      pub.setPredicate(p->{
        if(n == null || n.isEmpty()){
          return true;
        }

        return p.getName().toLowerCase().contains(n);
      });
    });
    
    id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
    name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
    cost_column.setCellValueFactory(new PropertyValueFactory<>("cost"));
    type_column.setCellValueFactory(new PropertyValueFactory<>("subscriptionType"));
        
    // This column is only available in customer popup.
    status_column.setText("Status");
    status_column.setCellFactory(e->new TableCell<>(){
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
        if(empty){
          setText(null);
        }
        else{
          if(customer instanceof AssociateCustomer){
            AssociateCustomer ac = (AssociateCustomer) customer;
            
            setText(ac.getPayer() == null ? "Inactive" : "Active");
          }
          else{
            setText("Active");
          }
        }
      }
      
    });
    
    table_view.getColumns().add(4, status_column);
    
    if("edit".equals(mode)){
      add_btn.setText("Add Magazine / Supplement");
      add_btn.setOnAction(e->addPublicationPage());

      action_column.setCellFactory(param->new TableCell<>(){
        private final Button delete_btn = new Button("Delete");
        {
          delete_btn.setOnAction(e->deleteSubscription(getTableRow().getItem()));
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
          super.updateItem(item, empty);

          if(empty){
            setGraphic(null);
          }
          else{
            HBox box = new HBox(10, delete_btn);
            box.setAlignment(Pos.CENTER);
            setGraphic(box);
          }
        }
      });
    }
    else{
      action_column.setVisible(false);
      add_btn.setVisible(false);
    }
  
    table_view.setItems(pub);
  }
  
  /**
   * Opens a popup for adding a new publication (magazine or supplement).
   */
  public void addPublicationPage(){
    popup("Magazine / Supplement selection", "popups/selection.fxml", new SelectionController(subscriptions));
  }
  
  /**
   * Deletes a publication from the user's subscriptions.
   * @param pub the publication to delete
   */
  private void deleteSubscription(Publication pub){
    subscriptions.removeIf(e->e!= null && (e.equals(pub) || (e.isSupplement() && pub.equals(e.getMagazine()))));
    table_view.refresh();
  }
}