package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import tk.valoeghese.zoesteriaconfig.api.container.Container;
import tk.valoeghese.zoesteriaconfig.impl.util.FileUtil;

public final class ImplZoesteriaConfigParser {
	private final Map<String, Object> dataMap;
	private int index, bufferSize;

	public ImplZoesteriaConfigParser(File file) {
		try {
			char[] charBuffer = FileUtil.readFile(file);
			this.index = -1;
			this.bufferSize = charBuffer.length;
			this.dataMap = this.parseContainer(new HashMap<>(), charBuffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> parseContainer(Map<String, Object> container, char[] data) {
		byte mode = 0; // 0 = var names, 1 = var values
		StringBuilder buffer = new StringBuilder();

		while (this.index + 1 < this.bufferSize) {
			++this.index;
			char c = data[this.index];

			if (c == '}') {
				break;
			} else if (c == '#') { // comment
				this.parseComment(data);
			} else if (mode == 1) {
				if (!Character.isWhitespace(c)) {
					if (c == '{') { // new container
						container.put(buffer.toString(), this.parseContainer(new HashMap<>(), data));
					} else if (c == '[') { // new list
						container.put(buffer.toString(), this.parseList(data));
					} else if (c == ';') { // new empty data object
						container.put(buffer.toString(), "");
					} else { // new data object
						container.put(buffer.toString(), this.parseData(data));
					}
					
					buffer = new StringBuilder();
					mode = 0;
				}
			} else if (c == '=') {
				mode = 1;
			} else if (!Character.isWhitespace(c)) {
				buffer.append(c); // append character to string buffer
			}
		}

		return container;
	}

	private List<Object> parseList(char[] data) {
		List<Object> result = new ArrayList<>();

		while (this.index + 1 < this.bufferSize) {
			++this.index;
			char c = data[index];

			if (c == ']') {
				break;
			} else if (c == '#') {
				this.parseComment(data);
			} else if (!Character.isWhitespace(c)) {
				if (c == '{') { // new container
					result.add(this.parseContainer(new HashMap<>(), data));
				} else if (c == '[') { // new list
					result.add(this.parseList(data));
				} else if (c == ';') { // new empty data object
					result.add("");
				} else { // new data object
					result.add(this.parseData(data));
				}
			}
		}

		return result;
	}

	private String parseData(char[] data) {
		StringBuilder buffer = new StringBuilder().append(data[this.index]); // initial character is already at the index

		while (this.index + 1 < this.bufferSize) {
			++this.index;
			char c = data[this.index];

			if (c == ';') {
				break;
			} else if ((!Character.isWhitespace(c)) || c == ' ') {
				// the only form of whitespace in data values allowed is spaces
				// tabs, carriage return, and line feed are considered merely formatting
				buffer.append(c);
			}
		}

		return buffer.toString().trim(); // remove trailing whitespace
	}

	private void parseComment(char[] data) {
		while (this.index + 1 < this.bufferSize) {
			++this.index;
			char c = data[this.index];

			if (c == '\n') { // break comment on new line
				break;
			}
		}
	}

	public Function<String, Object> asFunction() {
		return this.dataMap::get;
	}

	public Container asContainer() {
		return new ImplZoesteriaConfigAccess(this.asMap());
	}

	public Map<String, Object> asMap() {
		Map<String, Object> result = new HashMap<>();
		this.dataMap.forEach(result::put);
		return result;
	}

	public Map<String, Object> getInternalData() {
		return this.dataMap;
	}
	
	@Override
	public String toString() {
		return this.dataMap.toString();
	}
}