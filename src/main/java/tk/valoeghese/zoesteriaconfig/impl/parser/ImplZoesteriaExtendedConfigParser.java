package tk.valoeghese.zoesteriaconfig.impl.parser;

import java.io.File;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.ZFGExtendedDeserialiser;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

public final class ImplZoesteriaExtendedConfigParser<T> extends ImplZoesteriaConfigParser<ZFGExtendedDeserialiser<T>, T> {
	public ImplZoesteriaExtendedConfigParser(File file, ZFGExtendedDeserialiser<T> deserialiser) {
		super(file, deserialiser);
	}
	
	public WritableConfig asWritableConfig() {
		return new ImplZoesteriaConfigAccess(this.asMap());
	}

	public WritableConfig asWritableConfig(ConfigTemplate template) {
		ImplZoesteriaConfigAccess result = new ImplZoesteriaConfigAccess(this.deserialiser.asMap());
		template.injectDefaultsIfAbsent(result.parserMap);
		return result;
	}

	public Map<String, Object> asMap() {
		return this.deserialiser.asMap();
	}
}
