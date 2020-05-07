package tk.valoeghese.zoesteriaconfig.api.container;

import java.util.List;
import java.util.Map;

/**
 * Interface representing a section of key=value data in a config.
 * Keys for retrieving data from within this container may use dots as a way of separating further sub keys.
 * @apiNote Comments may be in the deserialised content (as separate objects under invalid keys) in order to be able to add comments to configs from code.
 */
public interface Container {
	/**
	 * Retrieves a sub container from within this container.
	 * @param key the key of the sub container.
	 * @return the sub container, if found. Otherwise, null.
	 */
	Container getContainer(String key);
	/**
	 * Retrieves a sub container from within this container as its raw map form. Editing this map <b>will</b> edit the container.
	 * @param key the key of the sub container.
	 * @return the sub container's map form, if found. Otherwise, null.
	 */
	Map<String, Object> getMap(String key);
	/**
	 * Retrieves a list from within this container. The objects in the list could be {@code Comment} comments, {@code String} values, {@code Map<String, Object>} containers, or {@code List<Object>} lists.
	 * @param key the key of the list.
	 * @return the list, if found. Otherwise, null.
	 */
	List<Object> getList(String key);

	/**
	 * Retrieves a string value from within this container. 
	 * @param key the key of the value.
	 * @return the value, if found. Otherwise, null.
	 */
	String getStringValue(String key);
	/**
	 * Retrieves an integer value from within this container. 
	 * @param key the key of the value.
	 * @return the value, if found. Otherwise, null.
	 */
	Integer getIntegerValue(String key);
	/**
	 * Retrieves a float value from within this container. 
	 * @param key the key of the value.
	 * @return the value, if found. Otherwise, null.
	 */
	Float getFloatValue(String key);
	/**
	 * Retrieves a double value from within this container. 
	 * @param key the key of the value.
	 * @return the value, if found. Otherwise, null.
	 */
	Double getDoubleValue(String key);
	/**
	 * Retrieves a boolean value from within this container. 
	 * @param key the key of the value.
	 * @return the value, if found. Otherwise, null.
	 */
	Boolean getBooleanValue(String key);

	/**
	 * @return whether the specified key is in the container.
	 */
	boolean containsKey(String key);
}
