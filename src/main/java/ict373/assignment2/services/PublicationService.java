package ict373.assignment2.services;

import ict373.assignment2.models.publications.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <strong>PublicationService class</strong>
 * 
 * <p>Service class for managing publications.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename PublicationService.java
 * 
 * @version 1.1
 * - Let the Service class handle storing the publications in a HashMap while the PublicationService class maintains an ObservableList for UI purposes.
 * - Update the add and remove methods to ensure both the HashMap and ObservableList are updated accordingly.
 * - Delete the containsPublication method as it is no longer necessary with the new structure.
 * - Add getObservableList method to provide access to the ObservableList of publications for UI components.
 */
public class PublicationService extends Service<Integer, Publication>{
	/**
	 * Singleton instance of PublicationService.
	 */
	private static PublicationService instance = null;
	
	/**
	 * Unique identifier for publications which increments with each new publication added.
	 */
	private int id = 0;

	/**
	 * ObservableList of publications for UI purposes. This list is kept in sync with the HashMap in the Service class.
	 */
	private transient ObservableList<Publication> publications;

	/**
	 * Default constructor for PublicationService.
	 */
	public PublicationService(){
		init();
	}

	/**
	 * Add a new publication to the service.
	 * @param publication The publication to be added.
	 */
	public void add(Publication publication){
		publication.setId(id);
		publications.add(publication);
		super.add(id++, publication);
	}

	/**
	 * Remove a publication from the service by their unique ID.
	 * @param publication_id The unique ID of the publication to be removed.
	 */
	@Override
	public void remove(Integer publication_id){
    remove(get(publication_id));
	}

  public void remove(Publication publication){
    if(publication == null) return;
    
    if(publication instanceof Magazine){
			Magazine magazine = (Magazine) publication;
			magazine.removeAllSupplements();
		}
		else{
			Supplement supplement = (Supplement) publication;
			supplement.setMagazine(null);
		}
    
    publications.remove(publication);
		super.remove(publication.getId());
  }
  
	/**
	 * Retrieve a publication by their unique ID.
	 * @param publication_id The unique ID of the publication.
	 * @return The Publication object if found, null otherwise.
	 */
	public Publication get(int publication_id){
		return super.get(publication_id);
	}

	/**
	 * Get the ObservableList of publications for UI purposes.
	 * @return The ObservableList of publications.
	 */
	public ObservableList<Publication> getObservableList(){
		return publications;
	}

	/**
	 * Get the singleton instance of PublicationService.
	 * @return The singleton instance of PublicationService.
	 */
	public static PublicationService getInstance(){
		if(instance == null) instance = new PublicationService();

		return instance;
	}
	
	public static void setInstance(PublicationService service){
		if(service != null){
      instance = service;
      service.init();
    }
	}

	/**
	 * Initialize the PublicationService instance and the ObservableList of publications.
	 */
	@Override
	public final void init(){
		instance = this;
		publications = FXCollections.observableArrayList(items.values());
	}
}