package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfigEntry;

final class ZoesteriaConfigParserUnwrapper implements ZoesteriaConfigEntry {
	public ZoesteriaConfigParserUnwrapper(ZoesteriaConfigParser parser) {
		this.parserMap = parser.asMap();
	}

	private final Map<String, Object> parserMap;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getContainer(String key) {
		Object result = this.getEntry(key);

		try {
			return (Map<String, Object>) result;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(String key) {
		Object result = this.getEntry(key);

		try {
			return (List<Object>) result;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public String getStringValue(String key) {
		Object result = this.getEntry(key);

		try {
			return (String) result;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public Integer getIntegerValue(String key) {
		Object result = this.getEntry(key);

		try {
			return Integer.valueOf((String) result);
		} catch (NumberFormatException | ClassCastException e) {
			return null;
		}
	}

	@Override
	public Float getFloatValue(String key) {
		Object result = this.getEntry(key);

		try {
			return Float.valueOf((String) result);
		} catch (NumberFormatException | ClassCastException e) {
			return null;
		}
	}

	@Override
	public Double getDoubleValue(String key) {
		Object result = this.getEntry(key);

		try {
			return Double.valueOf((String) result);
		} catch (NumberFormatException | ClassCastException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Object getEntry(String key) {
		String[] path = path(key);

		try {
			Object buffer = this.parserMap;

			for (String item : path) {
				int index = -1;

				try {
					index = Integer.valueOf(item);
				} catch (NumberFormatException e) {
					// Is not an integer;
				}

				if (index == -1) {
					Object newObject = ((Map<String, Object>) buffer).get(item);
					buffer = newObject;
				} else {
					Object newObject = ((List<Object>) buffer).get(index);
					buffer = newObject;
				}
			}

			return buffer;
		} catch (ClassCastException e) {
			return null;
		}
	}

	private static String[] path(String path) {
		return path.split("\\.");
	}
}
