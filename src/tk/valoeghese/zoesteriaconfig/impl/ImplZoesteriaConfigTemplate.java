package tk.valoeghese.zoesteriaconfig.impl;

import java.util.Map;

import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

public final class ImplZoesteriaConfigTemplate implements ConfigTemplate {
	public ImplZoesteriaConfigTemplate(Map<String, Object> defaults) {
		// TODO Also map of setup functions for containers so that missing things in subcontainers can be set up
	}

	@Override
	public void injectDefaultsIfAbsent(Map<String, Object> map) {
		
	}
}