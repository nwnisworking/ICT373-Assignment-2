package ict373.assignment2.controllers.customer;

import ict373.assignment2.models.PublicationModel;
import ict373.assignment2.publications.*;
import ict373.assignment2.utils.BaseController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * <p><strong>SelectionController class</strong></p>
 * 
 * <p>Controller for managing the selection of publications, specifically magazines and their supplements.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename SelectionController.java
 */
class SelectionController extends BaseController implements Initializable{
  /**
   * Model for managing publication data.
   */
  private PublicationModel model;
  
  /**
   * List of subscriptions that the user has selected.
   */
  private final ObservableList<Publication> subscriptions;
  
  /**
   * ComboBox for selecting magazines.
   * It filters the publications to show only those that are magazines.
   */
  @FXML
  private ComboBox<Publication> mag_field;
  
  /**
   * ListView for displaying supplements related to the selected magazine.
   * It filters the publications to show only those that are supplements of the selected magazine and not already subscribed to.
   */
  @FXML
  private ListView<Publication> supp_field;
  
  /**
   * Button to confirm the selection of the magazine and its supplements.
   */
  @FXML
  private Button btn;
  
  /**
   * Constructor for SelectionController.
   * @param subscriptions ObservableList of Publication that holds the user's subscriptions.
   */
  public SelectionController(ObservableList<Publication> subscriptions){
    this.subscriptions = subscriptions;
  }
  
  /**
   * Initializes the controller by setting up the model and configuring the ComboBox and ListView.
   * @param location The location used to resolve relative paths.
   * @param resources The resources used to localize the root object.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    model = PublicationModel.getInstance();
    FilteredList<Publication> filter_by_mag = new FilteredList<>(model.getData(), e->e instanceof Magazine);
    FilteredList<Publication> filter_by_supp = new FilteredList<>(model.getData(), e->false);

    btn.setOnAction(e->addSubscription());
    
    supp_field.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
    mag_field.setOnAction(e->{
      Magazine mag = (Magazine) mag_field.getValue();

      if(mag != null){
        filter_by_supp.setPredicate(p->{
          return p.isSupplement() && 
                  p.getMagazine().equals(mag) && 
                  !subscriptions.contains(p);
        });
        
      }
    });
    
    mag_field.setItems(filter_by_mag);
    supp_field.setItems(filter_by_supp);
  }
  
  /**
   * Adds the selected magazine and its supplements to the user's subscriptions.
   */
  private void addSubscription(){
    Stage stage = (Stage) btn.getScene().getWindow();
    Magazine mag = (Magazine) mag_field.getValue();
    ObservableList<Publication> supps = supp_field.getSelectionModel().getSelectedItems();
    
    if(mag == null){
      return;
    }

    if(!subscriptions.contains(mag)){
      subscriptions.add(mag);
    }
    
    subscriptions.addAll(supps);
    
    stage.close();
  }
}
