package tk.valoeghese.zoesteriaconfig.api.container;

import java.util.List;
import java.util.Map;

public interface Container {
	Container getContainer(String key);
	Map<String, Object> getMap(String key);
	List<Object> getList(String key);

	String getStringValue(String key);
	Integer getIntegerValue(String key);
	Float getFloatValue(String key);
	Double getDoubleValue(String key);
}
