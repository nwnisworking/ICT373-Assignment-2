package ict373.assignment2.controllers.publication;

import ict373.assignment2.models.CustomerModel;
import ict373.assignment2.models.PublicationModel;
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

/**
 * <p><strong>PublicationController class</strong></p>
 * 
 * <p>Controller for managing the publication of various types, allowing users to view, add, edit, and delete publications.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename PublicationController.java
 */
public class PublicationController extends BaseController implements Initializable{
  /**
   * Stores a reference to the PublicationModel instance.
   */
  private PublicationModel model;
  
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
   * The table column for displaying action buttons (e.g., delete, view, edit).
   */
  @FXML
  private TableColumn<Publication, Void> action_column;
  
  /**
   * Button to add a new publication (magazine or supplement).
   */
  @FXML
  private Button add_btn;
  
  /**
   * Initializes the controller by setting up the table view and filtering functionality.
   * @param url test
   * @param resources test
   */
  @Override
  public void initialize(URL url, ResourceBundle resources){
    model = PublicationModel.getInstance();
    
    FilteredList<Publication> pub = new FilteredList<>(model.getData());
    
    add_btn.setOnAction(e->addPublicationPage());
    
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
    action_column.setCellFactory(param->new TableCell<>(){
      private final Button edit_btn = new Button("Edit");
      
      private final Button view_btn = new Button("View");
      
      private final Button delete_btn = new Button("Delete");
      {
        edit_btn.setOnAction(e->editPublicationPage(getTableRow().getItem()));
        delete_btn.setOnAction(e->deletePublication(getTableRow().getItem()));
        view_btn.setOnAction(e->viewPublicationPage(getTableRow().getItem()));
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
    
    table_view.setItems(pub);
  }

  /**
   * Opens a popup for adding a new publication.
   */
  public void addPublicationPage(){
    popup("Add Publication", "popups/publication.fxml", new PopupController(null, null));
  }
  
  /**
   * Opens a popup for editing an existing publication.
   * @param pub the publication to be edited
   */
  public void editPublicationPage(Publication pub){
    popup("Edit Publication", "popups/publication.fxml", new PopupController(pub, "edit"));
  }
  
  /**
   * Opens a popup for viewing a publication.
   * @param pub the publication to be viewed
   */
  public void viewPublicationPage(Publication pub){
    popup("View Publication", "popups/publication.fxml", new PopupController(pub, "view"));
  }
  
  /**
   * Deletes a publication from the model and updates the customer subscriptions.
   * @param pub the publication to be deleted
   */
  public void deletePublication(Publication pub){
    model.getData().removeIf(e->e.equals(pub) || (e.isSupplement() && pub.equals(e.getMagazine())));
    CustomerModel.getInstance().getData().forEach(e->e.removeSubscription(pub));
  }
}