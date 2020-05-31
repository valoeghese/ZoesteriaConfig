package tk.valoeghese.zoesteriaconfig.api.template;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import tk.valoeghese.zoesteriaconfig.api.deserialiser.Comment;
import tk.valoeghese.zoesteriaconfig.impl.Counter;
import tk.valoeghese.zoesteriaconfig.impl.ImplZoesteriaConfigTemplate;

public interface ConfigTemplate {
	void injectDefaultsIfAbsent(Map<String, Object> map);

	static Builder builder() {
		return new Builder();
	}

	static class TemplateContainerBuilder {
		private TemplateContainerBuilder(Counter counter) {
			this.counter = counter;
		}

		final Counter counter;
		final Map<String, Object> data = new LinkedHashMap<>();

		public TemplateContainerBuilder addContainer(String name, Consumer<TemplateContainerBuilder> defaultContainerSetup) {
			TemplateContainerBuilder container = new TemplateContainerBuilder(this.counter);
			defaultContainerSetup.accept(container);
			this.data.put(name, container.data);
			return this;
		}

		public TemplateContainerBuilder addContainer(String name, Map<String, Object> defaultContainerData) {
			this.data.put(name, defaultContainerData);
			return this;
		}

		public TemplateContainerBuilder addList(String name, Consumer<List<Object>> defaultListSetup) {
			List<Object> list = new ArrayList<>();
			defaultListSetup.accept(list);
			this.data.put(name, list);
			return this;
		}

		public TemplateContainerBuilder addList(String name, List<Object> defaultListData) {
			this.data.put(name, defaultListData);
			return this;
		}

		public <T> TemplateContainerBuilder addDataEntry(String name, T defaultValue) {
			this.data.put(name, defaultValue);
			return this;
		}

		/**
		 * Adds a comment with the specified value if a comment with the same message does not already exist in the current data container.
		 */
		public TemplateContainerBuilder addComment(Comment comment) {
			this.data.put(".comment_template" + this.counter.get(), comment);
			return this;
		}
	}

	static class Builder extends TemplateContainerBuilder {
		private Builder() {
			super(new Counter());
		}

		@Override
		public Builder addContainer(String name, Consumer<TemplateContainerBuilder> defaultContainerSetup) {
			return (Builder) super.addContainer(name, defaultContainerSetup);
		}

		@Override
		public Builder addContainer(String name, Map<String, Object> defaultContainerData) {
			return (Builder) super.addContainer(name, defaultContainerData);
		}

		@Override
		public Builder addList(String name, Consumer<List<Object>> defaultListSetup) {
			return (Builder) super.addList(name, defaultListSetup);
		}

		@Override
		public Builder addList(String name, List<Object> defaultListData) {
			return (Builder) super.addList(name, defaultListData);
		}

		@Override
		public <T> Builder addDataEntry(String name, T defaultValue) {
			return (Builder) super.addDataEntry(name, defaultValue);
		}

		@Override
		public Builder addComment(Comment comment) {
			return (Builder) super.addComment(comment);
		}

		public ConfigTemplate build() {
			return new ImplZoesteriaConfigTemplate(this.data);
		}
	}
}
