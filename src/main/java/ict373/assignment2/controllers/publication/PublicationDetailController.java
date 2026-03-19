package ict373.assignment2.controllers.publication;

import ict373.assignment2.controllers.publication.forms.DetailForm;
import ict373.assignment2.models.publications.*;
import ict373.assignment2.services.PublicationService;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.events.*;
import ict373.assignment2.utils.Validator.ValidatorResult;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * <strong>PublicationDetailController class</strong>
 * 
 * <p>The PublicationDetailController class is responsible for managing the publication detail view, including displaying detailed information about a selected publication, handling the add/edit/view modes, and saving changes to the publication data.</p>
 * 
 * @author nwnisworking
 * @date 19/3/2026
 * @filename PublicationDetailController.java
 */
public class PublicationDetailController implements Initializable{
  /**
   * The save button for the publication detail view.
   */
  @FXML
  private Button save_btn;

  /**
   * The title label for the publication detail view which indicates whether the user is adding, editing, or viewing a publication.
   */
  @FXML
  private Label detail_title;
  
  /**
   * The detail panel that contains the publication information fields. This panel is updated based on 
   * the type of event (add, edit, view) triggered and the publication data passed in the event.
   */
  @FXML
  private BorderPane detail_panel;
  
  /**
   * The text field for the publication's name.
   */
  @FXML
	private TextInputField name_field;
  
  /**
   * The text field for the publication's cost.
   */
  @FXML
  private TextInputField cost_field;

  /**
   * The select input field for the publication's type (e.g., Magazine, Supplement). 
   */
  @FXML
  private SelectInputField<String> type_field;

  /**
   * The magazine field for the supplement. 
   */  
  @FXML
  private SelectInputField<Publication> magazine_field;

  /**
   * The DetailForm instance that manages the detail such as name, type, cost, and magazine.
   */
  private DetailForm detail;

  /**
   * The publication instance that is currently being viewed or edited in the detail view.
   */
  private Publication publication;
  
  /**
   * Initializes the controller class.
   * 
   * @param url The location for the root object.
   * @param rb The resources used to localize the root object.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    detail = new DetailForm(name_field, cost_field, type_field, magazine_field);
    
    detail_panel.addEventHandler(PageEvent.ANY, this::load);
    detail.type().valueProperty().addListener(this::togglePublicationType);
    
    save_btn.visibleProperty().bind(save_btn.managedProperty());

    togglePublicationType(null, "", detail.type().getValue());
  }
  
  /**
   * Load the publication data into the detail view based on the type of event triggered (add, edit, view).
   * 
   * @param event The PageEvent containing the publication data and event type.
   */
  private void load(PageEvent<?> event){
    boolean disabled = PageEvent.VIEW.equals(event.getEventType());
    Publication publication = (Publication) event.getData();
    String type = switch(event.getEventType().getName()){
      case "PAGE_ADD" -> "Add";
      case "PAGE_EDIT" -> "Edit";
      case "PAGE_VIEW" -> "View";
      default -> "View";
    };
    
    detail_title.setText(type + " Publication");
    
    detail.magazine().setItems(
      PublicationService
      .getInstance()
      .getObservableList()
      .filtered(Magazine.class::isInstance)
    );
    
    save_btn.setManaged(!disabled);

    detail.load(publication);
    detail.fields().forEach(e -> e.setDisable(disabled));
    detail.type().setDisable(!PageEvent.ADD.equals(event.getEventType()));
    
    this.publication = publication;
    System.out.println("[Publication]: " + (publication == null ? "New Publication" : publication) + " loaded in " + type + " mode");
  }
  
  /**
   * Save the publication data from the detail view when the save button is clicked, and fire a PublicationEvent to notify other components of the change.
   */
  @FXML
  private void save(){
    ValidatorResult result = detail.validate();

    if(!result.valid()){
      result.field().displayTooltip(result.message());
      return;
    }
    
    if(publication == null){
      publication = switch(detail.type().getValue()){
        case "Magazine" -> new Magazine();
        case "Supplement" -> new Supplement();
        default -> new Supplement();
      };
      
      detail_panel.fireEvent(new PublicationEvent(PublicationEvent.PUBLICATION_CREATED, publication));
    }
    else{
      detail_panel.fireEvent(new PublicationEvent(PublicationEvent.PUBLICATION_EDITED, publication));
    }
    
    detail.save(publication);
    System.out.println("[Publication]: Publication " + publication +  " saved");
    back();
  }

  /**
   * Go back to the previous view by firing a page event and resetting the detail view fields and state.
   */
  @FXML
  private void back(){
    save_btn.setManaged(true);
    
    publication = null;

    detail.fields().forEach(e -> e.reset());
    
    detail.type().setValue("Magazine");
    detail.magazine().setItems(FXCollections.observableArrayList());
    detail_panel.getParent().fireEvent(new PageEvent<>(PageEvent.BACK, null));
  }
  
  /**
   * Toggle the visibility of the magazine field based on the selected publication type. 
   * @param obs The observable value that triggered the change.
   * @param old_value The old value of the publication type before the change.
   * @param new_value The new value of the publication type after the change.
   */
  private void togglePublicationType(Observable obs, String old_value, String new_value){
    detail.magazine().setManaged("Supplement".equals(new_value));
  }
}