package trees.layout;

public class PlaceHolder extends Node {

	public PlaceHolder() {
		super(null);
	}

	@Override
	public boolean isPlaceHolder() {
		return true;
	}
}
