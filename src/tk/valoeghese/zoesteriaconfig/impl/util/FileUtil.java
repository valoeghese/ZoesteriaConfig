package tk.valoeghese.zoesteriaconfig.impl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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

	public static boolean writeFile(File file, String data) {
		try(Writer writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(data);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
