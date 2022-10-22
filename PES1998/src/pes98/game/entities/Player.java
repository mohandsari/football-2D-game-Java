package pes98.game.entities;

import java.awt.Font;
import java.awt.Graphics;

import pes98.Main;
import pes98.math.Vec;
import pes98.rendering.Screen;
import pes98.physics.Rigidbody;
import pes98.physics.PhysicsEngine;
import pes98.network.packet.PacketPosition;
import pes98.physics.collider.SphereCollider;

public class Player extends Entity {
	public int id;
	public Font font;
	public String name;
	public Rigidbody body;
	private float radius = 17;

	public Player(int id, String name, int x, int y) {
		super(new Vec(x, y));
		this.id = id;
		this.name = name;
		this.font = new Font("Arial", 0, 16);
	}
	
	public void initEntity() {
		body = new Rigidbody(position, new SphereCollider(radius - 3), this);
		body.mass = 2;
		PhysicsEngine.bodys.add(body);				
	}
	
	public void touchSide(int XY, float pos) {
		if(XY == 0 ) {
			body.position.x = pos;
			body.speed.x = 0;
		} else if (XY == 1) {
			body.position.y = pos;
			body.speed.y = 0;
		}
	}
	
	public void update() {
		if(body.speed.length() > 0)
				game.player.getNetwork().send(new PacketPosition(this, game.player.id).data.getBytes());
		position.setVec(body.position);
		if(position.x < radius) touchSide(0, radius);//LeftSide
		if(position.y < radius) touchSide(1, radius);//UpSide
		if(position.x > Main.WIDTH - radius) touchSide(0, Main.WIDTH - radius);//RightSide
		if(position.y > Main.HEIGHT - radius) touchSide(1, Main.HEIGHT - radius);//DownSide
	}
	
	public void render(Screen screen) {
		screen.drawBall((int) position.x, (int) position.y, (int) radius, 0x000000);//yellow
		screen.drawBall((int) position.x, (int) position.y, (int) radius - 3, 0x000000);//grey
	}

	public void renderGui(Graphics g) {
		if (this instanceof ClientPlayer) return;
		g.setFont(font);
		g.drawString(name, (int) position.x - (int) radius - 3, (int) position.y + (int) radius + 19); 		
	}
}
