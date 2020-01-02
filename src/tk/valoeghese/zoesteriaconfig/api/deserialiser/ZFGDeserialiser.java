package tk.valoeghese.zoesteriaconfig.api.deserialiser;

public interface ZFGDeserialiser<T> extends ZFGContainerDeserialiser {
	ZFGContainerDeserialiser newContainerDeserialiser();
	T getDeserialisedObject();
}
