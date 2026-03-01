package ict373.assignment2.models.publications;

import java.util.ArrayList;

/**
 * <strong>Magazine class</strong>
 * 
 * <p>Magazine class represents a magazine publication.</p>
 * 
 * <p>Assumptions: </p>
 * <p> - A magazine can have multiple supplements associated with it.</p>
 * <p> - Supplements can be added or removed from the magazine.</p>
 * <p> - User can have multiple similar magazines.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Magazine.java
 */
public class Magazine extends Publication{
  /**
   * List of supplements associated with the magazine.
   */
  private ArrayList<Supplement> supplements = new ArrayList<>();

  /**
   * Add a supplement to the magazine.
   * @param supplement The supplement to be added.
   */
  public void addSupplement(Supplement supplement){
    // Prevent adding null or duplicate supplements
    if(supplement == null || supplements.contains(supplement)) return;

    supplements.add(supplement);
    supplement.setMagazine(this);
  }

  /**
   * Remove all supplements from the magazine.
   */
  public void removeAllSupplements(){
    for(Supplement s : getSupplements()) {
      s.setMagazine(null);
    }

    supplements.clear();
  }

  /**
   * Remove a supplement from the magazine.
   * @param supplement The supplement to be removed.
   */
  public void removeSupplement(Supplement supplement){
    // Prevent removing null or non-existing supplements
    if(supplement == null || !supplements.contains(supplement)) return;

    supplement.setMagazine(null);
    supplements.remove(supplement);
  }

  /**
   * Get the list of supplements associated with the magazine.
   * @return The list of supplements.
   */
  public ArrayList<Supplement> getSupplements(){
    return new ArrayList<>(supplements);
  }
}
