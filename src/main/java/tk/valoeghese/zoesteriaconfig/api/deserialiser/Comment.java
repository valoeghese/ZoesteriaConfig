package tk.valoeghese.zoesteriaconfig.api.deserialiser;

public class Comment {
	public Comment(String stringValue) {
		this.stringValue = stringValue;
	}

	public final String stringValue;

	@Override
	public String toString() {
		return "comment(" + this.stringValue + ")";
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		} else if (other instanceof Comment) {
			return ((Comment) other).stringValue.equals(this.stringValue);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
