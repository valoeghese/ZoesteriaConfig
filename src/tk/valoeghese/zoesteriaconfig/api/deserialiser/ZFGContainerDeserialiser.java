package tk.valoeghese.zoesteriaconfig.api.deserialiser;

import java.util.List;

public interface ZFGContainerDeserialiser {
	void readList(String key, List<Object> list);
	void readData(String key, String data);
	ZFGContainerDeserialiser createSubContainer(String key);
}
