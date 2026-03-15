/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ict373.assignment2.controllers.publication;

import ict373.assignment2.controllers.publication.tabs.DetailTab;
import ict373.assignment2.models.publications.*;
import ict373.assignment2.services.CustomerService;
import ict373.assignment2.services.PublicationService;
import ict373.assignment2.ui.inputs.InputField;
import ict373.assignment2.ui.inputs.SelectInputField;
import ict373.assignment2.ui.inputs.TextInputField;
import ict373.assignment2.ui.table.DataTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class PublicationDetailController implements Initializable{
  @FXML
  private Button back_btn;
  
  @FXML
  private Button save_btn;
  
  @FXML
  private Label detail_title;
  
  @FXML
  private BorderPane detail_panel;
  
  @FXML
  private TextInputField name_field;
  
  @FXML
  private TextInputField cost_field;
  
  @FXML
  private SelectInputField<String> type_field;
  
  @FXML
  private SelectInputField<Publication> magazine_field;
  
  private DetailTab detail;
  
  private Publication publication = null;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    detail = new DetailTab(name_field, cost_field, type_field, magazine_field);
    
    detail.type().valueProperty().addListener(this::togglePublicationType);
    
    togglePublicationType(null, "", detail.type().getValue());
  }
  
  @FXML
  public void load(Publication publication, String action){
    detail_title.setText(action + " Publication");
    // Very important to populate the data here.
    detail.magazine().setItems(
      PublicationService
      .getInstance()
      .getObservableList()
      .filtered(Magazine.class::isInstance)
    );
    
    boolean disabled = false;
    this.publication = publication;
    
    switch(action){
      case "Add" : return;
      case "View" : disabled = true;
      case "Edit" : 
        boolean d = disabled;
        save_btn.setManaged(!disabled);

        detail.load(publication);
        detail.fields().forEach(e -> e.setDisable(d));
        detail.type().setDisable(true);
      break;
    }
  }

  @FXML
  public void back(){
    PublicationController controller = (PublicationController) detail_panel.getUserData();
    controller.displayDetail(false);
    controller.updatePagination();
    
    save_btn.setManaged(true);
    
    publication = null;
    
    clear();
  }
  
  @FXML
  public void save(){
    boolean is_new = publication == null;
    
    if(!validateField(detail.name(), "Name field must not be empty")) return;
    if(!validateField(detail.cost(), "Cost field must not be empty")) return;
    if(!validateDoubleField(detail.cost(), "Cost field is not a valid number")) return;
    
    if(publication instanceof Supplement){
      if(!validateField(detail.magazine(), "Magazine field must not be empty")) return;
    }
    
    if(is_new){
      publication = switch(detail.type().getValue()){
        case "Magazine" -> new Magazine();
        case "Supplement" -> new Supplement();
        default -> new Supplement();
      };
      
      PublicationService.getInstance().add(publication);
    }
    
    detail.save(publication);
    
    if(is_new)
      System.out.println("[Publication]: Publication " + publication.getName() +  " added to the service");
    else
      System.out.println("[Publication]: Publication" + publication.getName() + " data modified");

    back();
  }
  
  public void clear(){
    for(InputField<?, ?> field : detail.fields()){
      field.reset();
    }
  }
  
  private <T, C extends Control> boolean validateField(InputField<T, C> field, String message){
    if(field.isEmpty()){
      field.displayTooltip(message); 
      System.out.println("[Publication]: Validation failed");

      return false;
    }

    return true;
  }

  private <T, C extends Control> boolean validateNumberField(InputField<T, C> field, String message){
    if(!field.isNumber()){
      field.displayTooltip(message); 
      System.out.println("[Publication]: Validation failed");
      
      return false;
    }

    return true;
  }
  
  private <T, C extends Control> boolean validateDoubleField(InputField<T, C> field, String message){
    if(!field.isDouble()){
      field.displayTooltip(message); 
      System.out.println("[Publication]: Validation failed");
      
      return false;
    }

    return true;
  }
  
  private void togglePublicationType(Observable obs, String old_value, String new_value){
    detail.magazine().setManaged("Supplement".equals(new_value));
  }
}
