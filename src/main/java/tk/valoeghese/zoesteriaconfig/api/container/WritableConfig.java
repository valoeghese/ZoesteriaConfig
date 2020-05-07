package tk.valoeghese.zoesteriaconfig.api.container;

import java.io.File;

public interface WritableConfig extends EditableContainer {
	void writeToFile(File file);
}
