package pes98.game.entities;

import java.awt.Graphics;

import pes98.math.Vec;
import pes98.game.Game;
import pes98.rendering.Screen;

public abstract class Entity {
	public Vec position;
	public Game game;
	
	public Entity(Vec pos) {
		this.position = pos;
	}

	public abstract void initEntity();
	public abstract void update();
	public abstract void render(Screen screen);
	public abstract void renderGui(Graphics g);
}