package ict373.assignment2.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * <p><strong>BaseModel class</strong></p>
 * 
 * <p>Base model class for managing data in the system.</p>
 * 
 * <p>This class provides methods for reading and writing data to a file, as well as managing a collection of data.</p>
 */
public class BaseModel<T extends Identity>{
  /**
   * URL of the data file.
   */
  protected URL url;
  
  /**
   * Observable list to hold the data.
   */
  protected ObservableList<T> data = FXCollections.observableArrayList();
  
  /**
   * Default constructor for BaseModel.
   * @param path The path to the data file.
   * @throws FileNotFoundException if the data file is not found.
   */
  public BaseModel(String path) throws FileNotFoundException{
    url = getClass().getResource(path);
    
    if (url == null){
      throw new FileNotFoundException("Resource not found: " + url);
    }
    
    init();
  }
  
  /**
   * Initialize the model by reading data from the file.
   */
  protected void init(){
    System.out.println("Reading: " + url);
    try(ObjectInputStream input = new ObjectInputStream(
      new FileInputStream(url.getPath())
    )){
      ArrayList<T> temp = (ArrayList<T>) input.readObject();
      
      data.setAll(temp);
    }
    catch(IOException | ClassNotFoundException ex){
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Warning");
      alert.setContentText("Failed to read data from file.");
      
      alert.showAndWait();
    }
  }
  
  /**
   * Save the current data to the file.
   * This method serializes the data and writes it to the file specified by the URL.
   */
  public void save(){
    System.out.println("Saving: " + url);
    try(ObjectOutputStream output = new ObjectOutputStream(
      new FileOutputStream(url.getPath())
    )){
      output.writeObject(new ArrayList<>(data));
      output.flush();
      output.close();
    }
    catch(IOException | ClassCastException ex){
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Warning");
      alert.setContentText("Failed to save data to file.");

      alert.showAndWait();
    }
  }
  
  /**
   * Get the observable list of data.
   * @return An observable list containing the data.
   */
  public ObservableList<T> getData(){
    return data;
  }
  
  /**
   * Get the maximum ID from the data.
   * @return The maximum ID found in the data.
   */
  public int maxID(){
    int index = 0;
    
    for(int i = 0; i < data.size(); i++){
      T pub = (T) data.get(i);
      
      if(index < pub.getId()){
        index = pub.getId();
      }
    }
    
    return index;
  }
  
  /**
   * Get an item by its ID.
   * @param id The ID of the item to retrieve.
   * @return The item with the specified ID, or null if not found.
   */
  public T getId(int id){
    return data.stream().filter(e->e.getId() == id)
            .findFirst()
            .orElse(null);
  }
}
