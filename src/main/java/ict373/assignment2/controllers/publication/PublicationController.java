package ict373.assignment2.controllers.publication;

import ict373.assignment2.controllers.customer.CustomerDetailController;
import ict373.assignment2.events.ButtonEvent;
import ict373.assignment2.events.RowEvent;
import ict373.assignment2.models.customers.Customer;
import ict373.assignment2.models.publications.Publication;
import ict373.assignment2.services.CustomerService;
import ict373.assignment2.services.PublicationService;
import ict373.assignment2.ui.table.DataTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PublicationController implements Initializable{
  private static final int TOTAL_ROWS = 10;

  @FXML
	private HBox header;
  
  @FXML
	private Pagination pagination;
	
	@FXML
	private BorderPane detail;

	@FXML
	private DataTableView<Publication> table;
  
  @FXML
	private PublicationDetailController detailController;
  
  private final PublicationService publication_service = PublicationService.getInstance(); 
  
  private final FilteredList<Publication> filtered_list = new FilteredList<>(publication_service.getObservableList());

	private final ObservableList<Publication> page_items = FXCollections.observableArrayList();

  
  @Override
  public void initialize(URL url, ResourceBundle rb){
    table.setItems(page_items);
    
    pagination
    .currentPageIndexProperty()
    .addListener((obs, o, n) -> updateTableView(n.intValue()));
    
    detail.setUserData(this);
    
    header.visibleProperty().bind(header.managedProperty());
    table.visibleProperty().bind(table.managedProperty());
    pagination.visibleProperty().bind(pagination.managedProperty());
    detail.visibleProperty().bind(detail.managedProperty());
    
    updatePagination();
  }
  
  @FXML
  private void addPublication(){
    displayDetail(true);
    loadDetail(null, "Add");
  }
  
  @FXML
  private void searchPublication(KeyEvent event){
    TextField search_field = (TextField) event.getTarget();
		String text = search_field.getText();

    filtered_list.setPredicate(e -> text.isEmpty() || (!text.isEmpty() && e.getName().contains(text)));
		updatePagination();
  }
  
  @FXML
  private void buttonClicked(ButtonEvent<Publication> event){
    Publication publication = event.getItem();
    
    if(event.getType().equals("Delete")){
      publication_service.remove(publication.getId());
      updateTableView(pagination.getCurrentPageIndex());

      System.out.println("[Publication]: Publication " + publication + " deleted");

      return;
    }
    
    loadDetail(publication, event.getType());
  }
  
  @FXML
  private void rowClicked(RowEvent<Publication> event){
    loadDetail(event.getItem(), event.getType());
  }
  
  public void displayDetail(boolean show){
		header.setManaged(!show);
		table.setManaged(!show);
		pagination.setManaged(!show);
		detail.setManaged(show);
	}
  
  public void loadDetail(Publication publication, String mode){
    displayDetail(true);
		detailController.load(publication, mode);
	}
  
	public void updatePagination(){
		// At least 1 page should be shown even if there is no data. 
		// Also, any remainder should be rounded up to the next page. 
		int page_count = Math.max(1, (int) Math.ceil((double) filtered_list.size() / TOTAL_ROWS));
		
		pagination.setPageCount(page_count);
		pagination.setCurrentPageIndex(0);
		pagination.setMaxPageIndicatorCount(5);
		
		updateTableView(0);
	}
  
	private void updateTableView(int index){
		int from = index * TOTAL_ROWS;
		int to = Math.min(from + TOTAL_ROWS, filtered_list.size());

		page_items.setAll(filtered_list.subList(from, to));
	}
}


  
  
  
  
  
  
  
  