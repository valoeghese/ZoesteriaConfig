package tk.valoeghese.zoesteriaconfig.impl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class FileUtil {
	private FileUtil() {
	}

	public static char[] readFile(File file) throws IOException {
		try (FileReader fileReader = new FileReader(file)) {
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			char[] result = new char[(int) file.length()];

			bufferedReader.read(result);  
			bufferedReader.close();
			return result;
		}
	}
}
