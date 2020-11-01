package tk.valoeghese.zoesteriaconfig.test;

import java.io.File;

import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.Container;

public class TestContainerInList {
	public static void main(String[] args) {
		Container entry = ZoesteriaConfig.loadConfig(new File("./edgecase0.zfg"));
		System.out.println(entry.getIntegerValue("list.1.v0")); // -2
		System.out.println(entry.getIntegerValue("list.0.v1")); // 3
	}
}
