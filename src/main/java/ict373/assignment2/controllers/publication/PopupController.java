package ict373.assignment2.controllers.publication;

import ict373.assignment2.App;
import ict373.assignment2.models.PublicationModel;
import ict373.assignment2.publications.*;
import ict373.assignment2.utils.BaseController;
import ict373.assignment2.utils.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * <p><strong>PopupController class</strong></p>
 * 
 * <p>Controller for managing the popup window for adding or editing publications.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename PopupController.java
 */
class PopupController extends BaseController implements Initializable{
  /**
   * The model for managing publications.
   */
  private PublicationModel model;

  /**
   * The text field for entering the name of a publication.
   */
  @FXML
  private TextField name_field;

  /**
   * The text field for entering the cost of a publication.
   */
   @FXML
   private TextField cost_field;

  /**
   * The combo box for selecting the type of publication.
   */
  @FXML
  private ComboBox<String> type_field;

  /**
   * The combo box for selecting a magazine. This is used when adding a new publication.
   */
  @FXML
  private ComboBox<Publication> mag_field;

  /**
   * The button for adding a new publication or editing an existing one.
   */
  @FXML
  private Button btn;
  
  /**
   * Tab for displaying customers who have subscribed to the publication.
   */
  @FXML
  private Tab customer_tab;
  
  /**
   * Publication object representing the publication being added or edited.
   */
  private final Publication publication;
  
  /**
   * Mode of the popup, either "edit" or "view". This determines the behavior of the button.
   */
  private final String mode;

  /**
   * Constructor for PopupController.
   * @param publication the publication to be added or edited
   * @param mode the mode of the popup, either "edit" or "view"
   */
  public PopupController(Publication publication, String mode){
    this.publication = publication;
    this.mode = mode;
  }
  
  /**
   * Initializes the controller by setting up the model and configuring the ComboBox and other UI elements.
   * @param location the location used to resolve relative paths
   * @param resources the resources used to localize the root object
   */
  @Override
  public void initialize(URL location, ResourceBundle resources){
    model = PublicationModel.getInstance();
    FilteredList<Publication> filter_by_magazine = new FilteredList<>(
      model.getData(),
      e->e instanceof Magazine
    );
    
    // Default values
    mag_field.setDisable(true);
    mag_field.setItems(filter_by_magazine);
    type_field.setOnAction(e->mag_field.setDisable(isPublication(Magazine.class)));

    if(publication != null){
      
      name_field.setText(publication.getName());
      cost_field.setText(publication.getCost() + "");
      type_field.setValue(publication.getSubscriptionType());
      type_field.setDisable(true);

      if(publication instanceof Supplement){
        mag_field.setDisable(false);
        mag_field.setValue(publication.getMagazine());
      }

      if("edit".equals(mode)){
        btn.setText("Edit");
        btn.setOnAction(e->editPublication(publication));      
      }
      else{
        disableAllFields();
        btn.setText("Close");
        btn.setOnAction(e->{
          Stage stage = (Stage) btn.getScene().getWindow();
          stage.close();
        });
      }

    }
    else{
      btn.setText("Create");
      btn.setOnAction(e->addPublication());
    }
    
    try{
      FXMLLoader loader = App.FXMLLoader("customer");
      
      loader.setController(new CustomerController(publication));
      customer_tab.setContent(loader.load());
    }
    catch(IOException ex){
      throw new RuntimeException(ex);
    }
  }
  
  /**
   * Checks if the given class is a publication type based on the selected type in the ComboBox.
   * @param <T> the type of publication to check
   * @param cls the class to check against 
   * @return true if the class matches the selected type, false otherwise
   */
  private <T> boolean isPublication(Class<T> cls){
    if(cls.equals(Magazine.class)){
      return "Magazine".equals(type_field.getValue());
    }
    else if(cls.equals(Supplement.class)){
      return "Supplement".equals(type_field.getValue());
    }
    else{
      return false;
    }
  }
  
  /**
   * Adds a new publication based on the input fields and selected type.
   */
  public void addPublication(){
    Stage stage = (Stage) btn.getScene().getWindow();
    String name = name_field.getText();
    String cost = cost_field.getText();
    Magazine mag = (Magazine) mag_field.getValue();
    int id = model.maxID() + 1;

    if(!validate()){
      return;
    }

    if(isPublication(Magazine.class)){
      model.getData().add(new Magazine(id, name, Double.parseDouble(cost)));
    }
    else{
      model.getData().add(new Supplement(id, name, Double.parseDouble(cost), mag));
    }

    stage.close();
  }
  
  /**
   * Edits an existing publication based on the input fields and selected type.
   * @param pub
   */
  public void editPublication(Publication pub){
    Stage stage = (Stage) btn.getScene().getWindow();
    String name = name_field.getText();
    String cost = cost_field.getText();
    Magazine mag = (Magazine) mag_field.getValue();

    if(!validate()){
      return;
    }
    
    pub.setName(name);
    pub.setCost(Double.parseDouble(cost));

    if(pub instanceof Supplement){
      pub.setMagazine(mag);
    }
    
    model.getData().set(model.getData().indexOf(pub), pub);
    stage.close();
  }
  
  /**
   * Validates the input fields for creating or editing a publication.
   * @return true if all fields are valid, false otherwise
   */
  private boolean validate(){
    String name = name_field.getText();
    String cost = cost_field.getText();
    Magazine mag = (Magazine) mag_field.getValue();
    
    if(!Validator.match("[a-zA-Z0-9 @!#]+").validate(name)){
      alert("Invalid Field", "Name can only contain alpha-numeric characters, spacing, exclaimation point, @ symbol, and hashtag.");
      return false;
    }
    
    if(!Validator.isPositive().validate(cost)){
      alert("Invalid Field", "Cost cannot be negative or empty");
      return false;
    }
    
    if(isPublication(Supplement.class)&& mag == null){
      alert("Invalid Field", "A magazine needs to be selected");
      return false;
    }
    
    return true;
  }
  
  /**
   * Disables all input fields in the popup to prevent editing.
   * This is typically used when the popup is in "view" mode.
   */
  private void disableAllFields(){
    disableInputElement(name_field, true);
    disableInputElement(cost_field, true);
    disableInputElement(type_field, true);
    disableInputElement(mag_field, true);
  }
}
