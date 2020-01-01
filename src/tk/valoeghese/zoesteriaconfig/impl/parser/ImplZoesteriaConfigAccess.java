package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.container.Container;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.impl.util.FileUtil;

final class ImplZoesteriaConfigAccess implements WritableConfig {
	ImplZoesteriaConfigAccess(Map<String, Object> configMap) {
		this.parserMap = configMap;
	}

	private final Map<String, Object> parserMap;

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

	@Override
	public Container getContainer(String key) {
		return new ImplZoesteriaConfigAccess(this.getMap(key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMap(String key) {
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
	private void writeObject(StringBuilder fileDataBuffer, Object data, int indent) {
		if (data instanceof Map) {
			fileDataBuffer.append("{\n");
			this.writeContainerData(fileDataBuffer, ((Map<String, Object>) data), indent + INDENT_SIZE);

			if (indent > 0) {
				fileDataBuffer.append(indent(indent));
			}
			fileDataBuffer.append("}");
		} else if (data instanceof List) {
			this.writeList(fileDataBuffer, ((List<Object>) data), indent + INDENT_SIZE);
		} else if (data instanceof String) {
			fileDataBuffer.append((String) data).append(";");
		} else {
			fileDataBuffer.append(data.toString()).append(";");
		}

		if (indent == 0) {
			fileDataBuffer.append("\n");
		}
	}

	private void writeContainerData(StringBuilder fileDataBuffer, Map<String, Object> containerData, int indent) {
		containerData.forEach((key, value) -> {
			if (indent > 0) {
				fileDataBuffer.append(indent(indent));
			}

			fileDataBuffer.append(key).append(" = ");
			this.writeObject(fileDataBuffer, value, indent);
			fileDataBuffer.append("\n");
		});
	}

	private void writeList(StringBuilder fileDataBuffer, List<Object> listData, int indent) {
		fileDataBuffer.append("[\n");
		listData.forEach(value -> {
			if (indent > 0) {
				fileDataBuffer.append(indent(indent));
			}

			this.writeObject(fileDataBuffer, value, indent);
			fileDataBuffer.append("\n");
		});

		int finalIndent = indent - INDENT_SIZE;

		if (finalIndent > 0) {
			fileDataBuffer.append(indent(finalIndent));
		}

		fileDataBuffer.append("]");

		if (finalIndent == 0) {
			fileDataBuffer.append("\n");
		}
	}

	@Override
	public void writeToFile(File file) {
		StringBuilder fileDataBuffer = new StringBuilder();
		this.writeContainerData(fileDataBuffer, this.parserMap, 0);
		FileUtil.writeFile(file, fileDataBuffer.toString());
	}

	private static final int INDENT_SIZE = 2;
	private static final Map<Integer, String> INDENT_STZE_STRING_MAP = new HashMap<>();

	private static String[] path(String path) {
		return path.split("\\.");
	}

	private static String indent(int indentSize) {
		return INDENT_STZE_STRING_MAP.computeIfAbsent(indentSize, size -> {
			char[] buffer = new char[size];

			for (int i = 0; i < size; ++i) {
				buffer[i] = ' ';
			}
			return String.valueOf(buffer);
		});
	}
}
