package tk.valoeghese.zoesteriaconfig.api;

import java.io.File;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGExtendedDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;
import tk.valoeghese.zoesteriaconfig.impl.parser.ImplZoesteriaConfigParser;
import tk.valoeghese.zoesteriaconfig.impl.parser.ImplZoesteriaDefaultDeserialiser;
import tk.valoeghese.zoesteriaconfig.impl.parser.ImplZoesteriaExtendedConfigParser;

/**
 * A collection of various methods for loading, deserialising, and creating configs.
 */
public final class ZoesteriaConfig {
	private ZoesteriaConfig() {
	}

	public static WritableConfig loadConfig(File file) {
		return loadConfig(file, false);
	}

	public static WritableConfig loadConfigWithDefaults(File file, ConfigTemplate defaults) {
		return loadConfigWithDefaults(file, defaults, false);
	}

	public static WritableConfig loadConfig(File file, boolean stripComments) {
		return new ImplZoesteriaExtendedConfigParser<>(file, new ImplZoesteriaDefaultDeserialiser(stripComments)).asWritableConfig();
	}

	public static WritableConfig loadConfigWithDefaults(File file, ConfigTemplate defaults, boolean stripComments) {
		return new ImplZoesteriaExtendedConfigParser<>(file, new ImplZoesteriaDefaultDeserialiser(stripComments)).asWritableConfig(defaults);
	}

	public static <T> T deserialiseConfig(File file, ZFGDeserialiser<T> deserialiser) {
		return new ImplZoesteriaConfigParser<>(file, deserialiser).getDeserialiser().getDeserialisedObject();
	}

	public static Map<String, Object> deserialiseConfigToMap(File file, ZFGExtendedDeserialiser<?> deserialiser) {
		return new ImplZoesteriaExtendedConfigParser<>(file, deserialiser).getDeserialiser().asMap();
	}

	public static WritableConfig createWritableConfig(Map<String, Object> data) {
		return ImplZoesteriaConfigParser.createAccess(data);
	}
}
