package trees.layout;

public class PlaceHolder extends Node {

	public PlaceHolder(Class<?> cls) {
		super(new NodeProxy(cls));
	}

	@Override
	public boolean isPlaceHolder() {
		return true;
	}
}
