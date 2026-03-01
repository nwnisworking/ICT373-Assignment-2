package ict373.assignment2.models.publications;

/**
 * <strong>Supplement class</strong>
 * <p>Supplement class represents a supplement publication.</p>
 * 
 * <p>Assumptions: </p>
 * <p> - A supplement is associated with a single magazine.</p>
 * <p> - The magazine associated with the supplement can be set or retrieved.</p>
 * <p> - If the magazine is changed, the supplement is removed from the previous magazine's supplement list.</p>
 * <p> - If the magazine is set to null, the supplement is disassociated from any magazine.</p>
 * <p> - A customer can only have 1 supplement associated with a magazine they are subscribed to.</p>
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Supplement.java
 * 
 * @version 1.1
 * - Remove toString method as it is no longer necessary with the new structure.
 */
public class Supplement extends Publication{
  /**
   * The magazine associated with the supplement.
   */
  private Magazine magazine = null;

  /**
   * Set the magazine associated with the supplement.
   * @param magazine The magazine to be set.
   */
  public void setMagazine(Magazine magazine){
    Magazine old_magazine = this.magazine;

    this.magazine = magazine;

    if(old_magazine != null && !old_magazine.equals(magazine)){
      old_magazine.removeSupplement(this);
    }

    if(magazine != null){
      magazine.addSupplement(this);
    }
  }

  /**
   * Get the magazine associated with the supplement.
   * @return The magazine.
   */
  public Magazine getMagazine(){
    return magazine;
  }
}
