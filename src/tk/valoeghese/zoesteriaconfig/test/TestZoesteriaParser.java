package tk.valoeghese.zoesteriaconfig.test;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfigEntry;
import tk.valoeghese.zoesteriaconfig.impl.parser.ZoesteriaConfigParser;

public final class TestZoesteriaParser {
	public static void main(String[] args) {
		ZoesteriaConfigParser parser = new ZoesteriaConfigParser(new File("./example.zfg"));
		ZoesteriaConfigEntry entry = parser.asConfigEntry();
		System.out.println(entry.getStringValue("generation.biomeGroups.0"));
	}
}
