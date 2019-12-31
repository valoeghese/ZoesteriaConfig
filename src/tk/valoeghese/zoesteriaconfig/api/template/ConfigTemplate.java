package tk.valoeghese.zoesteriaconfig.api.template;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import tk.valoeghese.zoesteriaconfig.impl.ImplZoesteriaConfigTemplate;

public interface ConfigTemplate {
	void injectDefaultsIfAbsent(Map<String, Object> map);
	
	static Builder builder() {
		return new Builder();
	}
	
	static class TemplateContainerBuilder {
		private TemplateContainerBuilder() {
		}

		final Map<String, Object> data = new HashMap<>();
		
		public TemplateContainerBuilder addContainer(String name, Consumer<TemplateContainerBuilder> containerSetup) {
			TemplateContainerBuilder container = new TemplateContainerBuilder();
			containerSetup.accept(container);
			data.put(name, container);
			return this;
		}
	}

	static class Builder extends TemplateContainerBuilder {
		private Builder() {
		}
		
		public ConfigTemplate build() {
			return new ImplZoesteriaConfigTemplate(this.data);
		}
	}
}
