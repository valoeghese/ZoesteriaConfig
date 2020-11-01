package tk.valoeghese.zoesteriaconfig.api.deserialiser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ZFGContainerDeserialiser {
	void readList(String key, List<Object> list);
	void readData(String key, String data);
	ZFGContainerDeserialiser createSubContainer(String key);
	void readComment(Comment comment);
	Map<String, Object> dataMap();

	default Map<String, Object> asMap() {
		return new LinkedHashMap<>(this.dataMap());
	}
}
