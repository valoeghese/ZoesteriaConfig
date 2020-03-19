package tk.valoeghese.zoesteriaconfig.test;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;

public final class TestZoesteriaWriter {
	public static void main(String[] args) {
		WritableConfig entry = ZoesteriaConfig.loadConfig(new File("./example.zfg"));
		entry.writeToFile(new File("./exampleoutput.zfg"));
	}
}
