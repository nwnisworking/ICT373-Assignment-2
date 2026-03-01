package ict373.assignment2.models.payments;

import java.io.Serializable;

/**
 * <strong>Method interface</strong>
 * 
 * <p>Method interface represents a payment method.</p>
 * 
 * @author nwnisworking
 * @date 25/1/2026
 * @filename Method.java
 * 
 * @version 1.1
 * - Delete toString method as it is no longer necessary with the new structure.
 * - Remove Tabular interface implementation as it is no longer necessary with the new structure.
 * - Add serialVersionUID for serialization purposes.
 */
public interface Method extends Serializable{
  public static final long serialVersionUID = 4L;
}
