package tk.valoeghese.zoesteriaconfig.test;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.Comment;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

public final class TestZoesteriaWriter {
	public static void main(String[] args) {
		WritableConfig entry = ZoesteriaConfig.loadConfigWithDefaults(
				new File("./example.zfg"),
				ConfigTemplate.builder()
				.addComment(new Comment(" Example Config by Valoeghese."))
				.build());
		entry.writeToFile(new File("./exampleoutput.zfg"));
	}
}
