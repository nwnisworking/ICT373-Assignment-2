package ict373.assignment2.models;

import ict373.assignment2.publications.Publication;
import ict373.assignment2.utils.BaseModel;

import java.io.FileNotFoundException;

/**
 * <p><strong>PublicationModel class</strong></p>
 * 
 * <p>Model for managing publications in the system.</p>
 * 
 * <p>This class uses singleton pattern to ensure only one instance of PublicationModel exists.</p>
 * 
 * @author nwnisworking
 * @date 15/7/2025
 * @filename PublicationModel.java
 */
public class PublicationModel extends BaseModel<Publication>{
  /**
   * Stores an instance of PublicationModel.
   */
  private static PublicationModel instance;

  /**
   * Default constructor for MagazineModel.
   * @throws FileNotFoundException 
   */
  private PublicationModel() throws FileNotFoundException{
    super("publication.dat");
  }
  
  /**
   * Get the instance of PublicationModel.
   * @return An instance of magazine model or a new PublicationModel.
   */
  public static PublicationModel getInstance(){
    if(instance == null) {
      try {
        instance = new PublicationModel();
      } 
      catch(FileNotFoundException ex){
        throw new RuntimeException("Unable to load Publication.dat", ex);
      }
    }

    return instance;
  }
}
