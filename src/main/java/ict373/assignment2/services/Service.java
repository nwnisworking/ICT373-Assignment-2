package ict373.assignment2.services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <strong>Service class</strong>
 * 
 * <p>Abstract base class for services that manage collections of items.</p>
 */
public abstract class Service<K, V> implements Serializable{
	/**
	 * Serial version UID for serialization compatibility.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Map to store items managed by the service, where the key is a unique identifier and the value is the item itself.
	 */
	protected Map<K, V> items = new HashMap<>();

	/**
	 * Add an item to the service with a specified key.
	 * @param key The unique identifier for the item.
	 * @param item The item to be added to the service.
	 */
	protected void add(K key, V item){
		items.put(key, item);
	}

	/**
	 * Remove an item from the service by its unique key.
	 * @param key The unique identifier of the item to be removed.
	 */
	protected void remove(K key){
		items.remove(key);
	}

	/**
	 * Get an item from the service by its unique key.
	 * @param key The unique identifier of the item to be retrieved.
	 * @return The item associated with the specified key, or null if no such item exists.
	 */
	protected V get(K key){
		return items.get(key);
	}

	/**
	 * Write the Service instance to an ObjectOutputStream.
	 * @param output The ObjectOutputStream to write the Service instance to.
	 * @throws IOException If an I/O error occurs while writing the object.
	 */
	public void write(ObjectOutputStream output) throws IOException{
		output.writeObject(this);
	}

	/**
	 * Abstract method to initialize the service instance. 
	 */
	abstract public void init();

	/**
	 * Abstract method to read the service instance from an ObjectInputStream.
	 * @param input The ObjectInputStream to read the Service instance from.
	 * @throws IOException If an I/O error occurs while reading the object.
	 */
	abstract public void read(ObjectInputStream input);
}