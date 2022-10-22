package pes98.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	public boolean[] keys = new boolean[256];
	
	public void keyTyped(KeyEvent e) {	
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;		
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
