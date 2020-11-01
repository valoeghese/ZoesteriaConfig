package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.deserialiser.Comment;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGContainerDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGDeserialiser;

public class ImplZoesteriaDefaultDeserialiser implements ZFGDeserialiser<Map<String, Object>> {
	public ImplZoesteriaDefaultDeserialiser(boolean stripComments) {
		this.dataMap = new LinkedHashMap<>();
		this.stripComments = stripComments;
	}

	private final Map<String, Object> dataMap;
	private final boolean stripComments;
	private int commentKeyId = 0;

	@Override
	public void readList(String key, List<Object> list) {
		this.dataMap.put(key, list);
	}

	@Override
	public void readData(String key, String data) {
		this.dataMap.put(key, data);
	}

	@Override
	public void readComment(Comment comment) {
		if (!this.stripComments) {
			// use "." at the beginning since "." is a special character in key parsing.
			this.dataMap.put(".comment" + this.commentKeyId++, comment);
		}
	}

	@Override
	public ZFGContainerDeserialiser createSubContainer(String key) {
		ImplZoesteriaDefaultDeserialiser newDeserialiser = new ImplZoesteriaDefaultDeserialiser(this.stripComments);
		this.dataMap.put(key, newDeserialiser.dataMap);
		return newDeserialiser;
	}

	@Override
	public ZFGContainerDeserialiser newContainerDeserialiser() {
		return new ImplZoesteriaDefaultDeserialiser(this.stripComments);
	}

	@Override
	public Map<String, Object> dataMap() {
		return this.dataMap;
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
