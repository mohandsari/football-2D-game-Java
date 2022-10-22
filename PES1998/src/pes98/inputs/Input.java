package pes98.inputs;

public class Input {
	private static InputHandler input;
	
	static {
		input = new InputHandler();
	}
	
	public static boolean getKey(int key) {
		return input.keys[key];
	}
	
	public static InputHandler getInputHandler() {
		return input;
	}
}
