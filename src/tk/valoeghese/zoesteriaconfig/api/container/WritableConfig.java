package tk.valoeghese.zoesteriaconfig.api.container;

import java.io.File;

public interface WritableConfig extends Container {
	void writeToFile(File file);
}
