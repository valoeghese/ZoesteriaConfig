package tk.valoeghese.zoesteriaconfig.api;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.impl.parser.ImplZoesteriaConfigParser;

public final class ZoesteriaConfig {
	private ZoesteriaConfig() {
	}

	public static WritableConfig loadConfig(File file) {
		return new ImplZoesteriaConfigParser(file).asWritableConfig();
	}
}
