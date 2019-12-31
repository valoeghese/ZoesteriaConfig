package tk.valoeghese.zoesteriaconfig.api;

import java.util.List;
import java.util.Map;

public interface ZoesteriaConfigEntry {
	Map<String, Object> getContainer(String key);
	List<Object> getList(String key);

	String getStringValue(String key);
	Integer getIntegerValue(String key);
	Float getFloatValue(String key);
	Double getDoubleValue(String key);
}
