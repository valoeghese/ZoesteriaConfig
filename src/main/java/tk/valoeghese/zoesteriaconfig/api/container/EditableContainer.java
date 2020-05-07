package tk.valoeghese.zoesteriaconfig.api.container;

import java.util.List;
import java.util.Map;

public interface EditableContainer extends Container {
	@Override
	EditableContainer getContainer(String key);

	void putMap(String key, Map<String, Object> map);
	void putList(String key, List<Object> list);

	void putStringValue(String key, String value);
	void putIntegerValue(String key, int value);
	void putFloatValue(String key, float value);
	void putDoubleValue(String key, double value);
	void putBooleanValue(String key, boolean value);

	void addComment(String comment);
}
