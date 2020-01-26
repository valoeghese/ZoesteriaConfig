package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGContainerDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGDeserialiser;
import tk.valoeghese.zoesteriaconfig.impl.util.FileUtil;

public class ImplZoesteriaConfigParser<E extends ZFGDeserialiser<T>, T> {
	private int index, bufferSize;
	protected final E deserialiser;

	public ImplZoesteriaConfigParser(File file, E deserialiser) {
		try {
			char[] charBuffer = FileUtil.readFile(file);
			this.index = -1;
			this.bufferSize = charBuffer.length;
			this.deserialiser = deserialiser;
			this.parseContainer(this.deserialiser, charBuffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ZFGContainerDeserialiser parseContainer(ZFGContainerDeserialiser container, char[] data) {
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
						this.parseContainer(container.createSubContainer(buffer.toString()), data);
					} else if (c == '[') { // new list
						List<Object> list = this.parseList(data);
						container.readList(buffer.toString(), list);
					} else if (c == ';') { // new empty data object
						container.readData(buffer.toString(), "");
					} else { // new data object
						container.readData(buffer.toString(), this.parseData(data));
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
					result.add(this.parseContainer(this.deserialiser.newContainerDeserialiser(), data));
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

	public E getDeserialiser() {
		return this.deserialiser;
	}

	@Override
	public String toString() {
		return "parserOf(" + this.deserialiser.toString() + ")";
	}

	public static WritableConfig createAccess(Map<String, Object> data) {
		return new ImplZoesteriaConfigAccess(data);
	}
}