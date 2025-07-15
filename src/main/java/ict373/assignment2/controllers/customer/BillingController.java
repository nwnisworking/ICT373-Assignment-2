package ict373.assignment2.controllers.customer;

import ict373.assignment2.customers.*;
import ict373.assignment2.publications.Publication;
import ict373.assignment2.utils.BaseController;
import ict373.assignment2.utils.CustomerSubscription;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <p><strong>BillingController class</strong></p>
 * 
 * <p>Displays the billing information for a paying customer, including their subscriptions and associated costs.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename BillingController.java
 */
class BillingController extends BaseController implements Initializable{
  /**
   * Table view to display the list of publications associated with the paying customer.
   */
  @FXML
  private TableView<Publication> payer_table_view;
  
  /**
   * Table columns for displaying the name of the publication.
   */
  @FXML
  private TableColumn<Publication, String> payer_subscription_column;
  
  /**
   * Table columns for displaying the cost of the publication.
   */
  @FXML
  private TableColumn<Publication, Double> payer_cost_column;
  
  /**
   * Table columns for displaying the type of subscription (e.g., Magazine, Supplement).
   */
  @FXML
  private TableColumn<Publication, String> payer_type_column;
  
  /**
   * Table view to display the billing information for customer subscriptions, including associated customers and their publications.
   */
  @FXML
  private TableView<CustomerSubscription> billing_table_view;
  
  /**
   * Table columns for displaying the associated customer.
   */
  @FXML
  private TableColumn<CustomerSubscription, Customer> customer_column;
  
  /**
   * Table columns for displaying the subscription associated with the customer.
   */
  @FXML
  private TableColumn<CustomerSubscription, Publication> subscription_column;
  
  /**
   * Table columns for displaying the cost of the subscription.
   */
  @FXML
  private TableColumn<CustomerSubscription, Double> cost_column;
  
  /**
   * Table columns for displaying the type of subscription.
   */
  @FXML
  private TableColumn<CustomerSubscription, String> type_column;
  
  /**
   * Label to display the total cost of all subscriptions.
   */
  @FXML
  private Label total_cost_label;

  /**
   * The customer for whom the billing information is displayed.
   */
  private final Customer customer;
  
  /**
   * Constructor for BillingController.
   * @param customer The customer whose billing information is to be displayed.
   */
  public BillingController(Customer customer){
    this.customer = customer;
  }
  
  /**
   * Initializes the controller by setting up the table view and loading billing data for the paying customer.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<Publication> payer_sub = FXCollections.observableArrayList();
    ObservableList<CustomerSubscription> payer_assoc = FXCollections.observableArrayList();
    
    loadBillingData((PayingCustomer) customer, payer_sub, payer_assoc);

    payer_subscription_column.setCellValueFactory(new PropertyValueFactory<>("name"));
    payer_cost_column.setCellValueFactory(new PropertyValueFactory<>("cost"));
    payer_type_column.setCellValueFactory(new PropertyValueFactory<>("subscriptionType"));
    payer_table_view.setItems(payer_sub);

    customer_column.setCellValueFactory(new PropertyValueFactory<>("customer"));
    subscription_column.setCellValueFactory(new PropertyValueFactory<>("subscription"));
    cost_column.setCellValueFactory(new PropertyValueFactory<>("cost"));
    type_column.setCellValueFactory(new PropertyValueFactory<>("subscriptionType"));
    billing_table_view.setItems(payer_assoc);
  }
  
  /**
   * Loads the billing data for the paying customer and their associates.
   * @param payer The paying customer whose billing data is to be loaded.
   * @param payer_sub The observable list to hold the publications of the paying customer.
   * @param payer_assoc The observable list to hold the subscriptions associated with the paying customer's associates.
   */
  private void loadBillingData(PayingCustomer payer, ObservableList<Publication> payer_sub, ObservableList<CustomerSubscription> payer_assoc){
    payer_sub.clear();
    payer_assoc.clear();

    Task<Void> task = new Task<>(){
      @Override
      protected Void call() throws Exception{
        double[] total = {0};
        ArrayList<CustomerSubscription> assoc_sub = new ArrayList<>();
        ArrayList<Publication> payer_pub = new ArrayList<>(payer.getPublications());
        
        payer.getPublications().forEach(e->total[0]+= e.getCost());
        
        payer.getAssociates().forEach(assoc->{
          assoc.getPublications().forEach(pub->{
            total[0]+= pub.getCost();
            assoc_sub.add(new CustomerSubscription(assoc, pub));
          });
        });
        
        
        Platform.runLater(() -> {
          total_cost_label.setText("Cost of all the subscriptions: $" + total[0]);
          payer_sub.setAll(payer_pub);
          payer_assoc.setAll(assoc_sub);
        });
         
        return null;
      }
    };
    
    new Thread(task).start();
  }
}
