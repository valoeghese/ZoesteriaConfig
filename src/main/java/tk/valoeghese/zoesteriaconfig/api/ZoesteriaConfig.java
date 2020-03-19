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

public final class ZoesteriaConfig {
	private ZoesteriaConfig() {
	}

	public static WritableConfig loadConfig(File file) {
		return new ImplZoesteriaExtendedConfigParser<>(file, new ImplZoesteriaDefaultDeserialiser()).asWritableConfig();
	}

	public static WritableConfig loadConfigWithDefaults(File file, ConfigTemplate defaults) {
		return new ImplZoesteriaExtendedConfigParser<>(file, new ImplZoesteriaDefaultDeserialiser()).asWritableConfig(defaults);
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
