package tk.valoeghese.zoesteriaconfig.api.deserialiser;

import java.util.Map;

public interface ZFGExtendedDeserialiser<T> extends ZFGDeserialiser<T> {
	Map<String, Object> asMap();
}
