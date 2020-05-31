package tk.valoeghese.zoesteriaconfig.impl;

public final class Counter {
	public Counter() {
	}

	private int num = 0;

	public int get() {
		return this.num++;
	}
}
