package tk.valoeghese.zoesteriaconfig.impl;

import java.util.List;
import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

public final class ImplZoesteriaConfigTemplate implements ConfigTemplate {
	public ImplZoesteriaConfigTemplate(Map<String, Object> defaults) {
		this.defaults = defaults;
	}

	private final Map<String, Object> defaults;

	@Override
	public void injectDefaultsIfAbsent(Map<String, Object> map) {
		this.injectDefaultsIfAbsent(map, this.defaults);
	}

	@SuppressWarnings("unchecked")
	private void injectDefaultsIfAbsent(Map<String, Object> map, Map<String, Object> defaults) {
		defaults.forEach((key, value) -> {
			if (!map.containsKey(key)) {
				if (value instanceof Map || value instanceof List || value instanceof String) {
					map.put(key, value);
				} else {
					map.put(key, String.valueOf(value));
				}
			} else if (map.get(key) instanceof Map && defaults.get(key) instanceof Map) {
				this.injectDefaultsIfAbsent((Map<String, Object>) value, (Map<String, Object>) defaults.get(key));
			}
		});
	}
}