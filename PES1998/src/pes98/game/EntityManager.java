package pes98.game;

import java.util.List;
import java.awt.Graphics;
import java.util.ArrayList;

import pes98.rendering.Screen;
import pes98.game.entities.Entity;

public class EntityManager {
	private List<Entity> entities;
	
	public EntityManager() {
		entities = new ArrayList<Entity>();
	}
	
	public void add(Entity e, Game game) {
		e.game = game;
		e.initEntity();
		entities.add(e);
	}
	
	public void update() throws InterruptedException {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
	}
	
	public void render(Screen screen) {
		for(Entity e : entities) {
			e.render(screen);
		}
	}
	
	public void renderGui(Graphics g) {
		for(Entity e : entities) {
			e.renderGui(g);
		}
	}
	
	public List<Entity> getEntities(){
		return entities;
	}
}