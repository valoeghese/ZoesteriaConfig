package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGContainerDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGExtendedDeserialiser;

public class ImplZoesteriaDefaultDeserialiser implements ZFGExtendedDeserialiser<Map<String, Object>> {
	public ImplZoesteriaDefaultDeserialiser() {
		this.dataMap = new HashMap<>();
	}

	private final Map<String, Object> dataMap;

	@Override
	public void readList(String key, List<Object> list) {
		this.dataMap.put(key, list);
	}

	@Override
	public void readData(String key, String data) {
		this.dataMap.put(key, data);
	}

	@Override
	public ZFGContainerDeserialiser createSubContainer(String key) {
		ImplZoesteriaDefaultDeserialiser newDeserialiser = new ImplZoesteriaDefaultDeserialiser();
		this.dataMap.put(key, newDeserialiser.dataMap);
		return newDeserialiser;
	}

	@Override
	public ZFGContainerDeserialiser newContainerDeserialiser() {
		return new ImplZoesteriaDefaultDeserialiser();
	}

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> result = new HashMap<>();
		this.dataMap.forEach(result::put);
		return result;
	}

	@Override
	public Map<String, Object> getDeserialisedObject() {
		return this.dataMap;
	}

	@Override
	public String toString() {
		return this.dataMap.toString();
	}
}
