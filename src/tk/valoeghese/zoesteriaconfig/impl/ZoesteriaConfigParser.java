package tk.valoeghese.zoesteriaconfig.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.impl.util.FileUtil;

public final class ZoesteriaConfigParser {
	private final Map<String, Object> data = new HashMap<>();
	private int index, bufferSize;

	private ZoesteriaConfigParser(File file) {
		try {
			char[] charBuffer = FileUtil.readFile(file);
			this.index = 0;
			this.bufferSize = charBuffer.length;
			this.parseContainer(charBuffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> parseContainer(char[] data) {
		Map<String, Object> container = new HashMap<>();
		byte mode = 0; // 0 = var names, 1 = var values
		StringBuilder buffer = new StringBuilder();
		
		while (this.index + 1 < this.bufferSize) {
			++this.index;
			char c = data[this.index];
			
			if (c == '}') {
				break;
			} else if (c == '#' ) { // comment
				this.parseComment(data);
			} else if (mode == 1) {
				if (!Character.isWhitespace(c)) {
					if (c == '{') { // new container
						this.data.put(buffer.toString(), this.parseContainer(data));
					} else if (c == '[') { // new list
						this.data.put(buffer.toString(), this.parseList(data));
					} else if (c == ';') { // new empty data object
						this.data.put(buffer.toString(), "");
					} else { // new data object
						this.data.put(buffer.toString(), this.parseData(data));
					}
				}
			}
		}
	}
}