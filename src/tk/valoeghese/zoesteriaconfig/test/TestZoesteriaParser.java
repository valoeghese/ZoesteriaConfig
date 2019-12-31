package tk.valoeghese.zoesteriaconfig.test;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.Container;

public final class TestZoesteriaParser {
	public static void main(String[] args) {
		Container entry = ZoesteriaConfig.loadConfig(new File("./example.zfg"));
		System.out.println(entry.getStringValue("generation.biomeGroups.0"));
	}
}
