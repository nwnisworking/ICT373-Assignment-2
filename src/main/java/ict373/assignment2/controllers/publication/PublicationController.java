package ict373.assignment2.controllers.publication;

import ict373.assignment2.events.*;
import ict373.assignment2.models.publications.Publication;
import ict373.assignment2.services.PublicationService;
import ict373.assignment2.ui.table.DataTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PublicationController implements Initializable{
  /**
   * The total number of rows to display per page in the publication table. 
   */
  private static final int TOTAL_ROWS = 10;
  
  /**
   * The maximum page to display.
   */
  private static final int MAX_PAGE = 5;

  /**
   * The content holding the header, table view, and detail.
   */
  @FXML
  private BorderPane content;
  
  /**
   * The header section of the publication view, which contain a search bar and an add button. 
   */
  @FXML
	private HBox header;
  
  /**
   * Pagination control for navigating through pages of publications in the table view.
   */
  @FXML
	private Pagination pagination;
	
  /**
   * The detail section of the publication view, which displays detailed information about a selected publication and allows
   * for editing or adding new publications.
   */
	@FXML
	private BorderPane detail;

  /**
   * The table view that displays the list of customers in a table format. 
   */
	@FXML
	private DataTableView<Publication> table;
  
  /**
   * The PublicationService instance used to manage publication data and operations.
   */
  private final PublicationService publication_service = PublicationService.getInstance(); 
  
  /**
   * The filtered list of publications based on the search criteria. This list is used to implement the search functionality, allowing the table
   * to match publication based on the search criteria.
   */
  private final FilteredList<Publication> filtered_list = new FilteredList<>(publication_service.getObservableList());

  /**
   * The page item to be displayed in the table view. 
   */
	private final ObservableList<Publication> page_items = FXCollections.observableArrayList();
  
  /**
   * Initialize the controller by setting up the table view, pagination, and event handlers for the publication view.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb){
    table.setItems(page_items);
    
    pagination
    .currentPageIndexProperty()
    .addListener(this::paginationHandler);
      
    // Lazy way to toggle visibleProperty by combining managedProperty
    header.visibleProperty().bind(header.managedProperty());
    table.visibleProperty().bind(table.managedProperty());
    pagination.visibleProperty().bind(pagination.managedProperty());
    detail.visibleProperty().bind(detail.managedProperty());
    
    // Add event listener.
    content.addEventHandler(TableEvent.ANY, this::viewPage);
    content.addEventHandler(PageEvent.ANY, this::triggerAction);
    content.addEventHandler(PublicationEvent.ANY, this::handlePublication);

    updatePageCount();
    updateTableView(0);
  }
  
  /**
   * Handle publication events such as creating or deleting a publication.
   * 
   * @param event The PublicationEvent triggered by an action related to publication management.
   */
  private void handlePublication(PublicationEvent event){
    event.consume();
    
    Publication publication = event.getPublication();
    EventType<? extends Event> event_type = event.getEventType();

    if(publication == null){
      System.out.println("[Publication]: Invalid publication event data passed");
      return;
    }
    
    if(event_type.equals(PublicationEvent.PUBLICATION_CREATED)){
        publication_service.add(publication);
        updatePageCount();
        updateTableView(0);
        content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, publication + " added"));
    }
    else if(event_type.equals(PublicationEvent.PUBLICATION_DELETED)){
      publication_service.remove(publication.getId());
      content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, publication + " deleted"));
      updatePageCount();
      updateTableView(pagination.getCurrentPageIndex());
    }
    else{
      content.fireEvent(new ToastEvent(ToastEvent.ANY, ToastEvent.Status.SUCCESS, publication + " data modified"));
    }
  }

  /**
   * Handle page events such as navigating back from the detail view or showing the detail view for editing or adding a publication.
   * 
   * @param event The PageEvent triggered by an action in the detail view.
   */
  private void triggerAction(PageEvent<?> event) {
    event.consume();

    if(PageEvent.BACK.equals(event.getEventType())){
      displayDetail(false);
    }
    else{
      displayDetail(true);
    }
  }

  /**
   * Handle table events such as editing, viewing, or deleting a publication.
   * 
   * @param event The TableEvent triggered by an action in the table view.
   */
  private void viewPage(TableEvent<?> event){
    event.consume();
    String type = event.getEventType().getName();
    Publication publication = (Publication) event.getData();
    
    switch(type){
      case "TABLE_EDIT" -> detail.fireEvent(new PageEvent<>(PageEvent.EDIT, publication));
      case "TABLE_VIEW" -> detail.fireEvent(new PageEvent<>(PageEvent.VIEW, publication));
      case "TABLE_DELETE" -> content.fireEvent(new PublicationEvent(PublicationEvent.PUBLICATION_DELETED, publication));
    }
  }

  /**
   * Handle pagination changes by updating the table view to display the correct set of customers.
   * 
   * @param obs The observable value that triggered the pagination change.
   * @param old_value The old page index before the change.
   * @param new_value The new page index after the change.
   */
  private void paginationHandler(Observable obs, Number old_value, Number new_value){
    updateTableView(new_value.intValue());
  }

  /**
   * Trigger the add publication action.
   */
  @FXML
  private void addPublication(){
    detail.fireEvent(new PageEvent<>(PageEvent.ADD, null));
  }
  
  /**
   * Search for publications based on the text entered in the search field.
   *  
   * @param event The KeyEvent triggered by typing in the search field.
   */
  @FXML
  private void searchPublication(KeyEvent event){
    TextField search_field = (TextField) event.getTarget();
		String text = search_field.getText();

    filtered_list.setPredicate(e -> text.isEmpty() || (!text.isEmpty() && e.getName().contains(text)));
		updatePageCount();
		updateTableView(0);
  }
  
  /**
   * Toggle the visibility of the detail view and the main content (header, table, pagination) based on the show parameter.
   * 
   * @param show A boolean indicating whether to show the detail view (true) or the main content (false). 
   */
  public void displayDetail(boolean show){
		header.setManaged(!show);
		table.setManaged(!show);
		pagination.setManaged(!show);
		detail.setManaged(show);
	}
  
  /**
   * Update the publication data inside the table.
   *  
   * @param new_value The new page index to display in the table view.
   */
  private void updateTableView(int new_value){
    System.out.println("[Publication]: Navigated to page " + (new_value + 1));

    int from = new_value * TOTAL_ROWS;
		int to = Math.min(from + TOTAL_ROWS, filtered_list.size());

		page_items.setAll(filtered_list.subList(from, to));
  }

	/**
   * Update the page count for the pagination control based on the size of the filtered publication list and the total number of rows per page.
   */
  private void updatePageCount(){
    int page_count = Math.max(1, (int) Math.ceil((double) filtered_list.size() / TOTAL_ROWS));
    pagination.setPageCount(page_count);
    pagination.setMaxPageIndicatorCount(MAX_PAGE);
  }
}