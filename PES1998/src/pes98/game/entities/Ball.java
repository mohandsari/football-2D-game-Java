package pes98.game.entities;

import java.awt.Graphics;

import pes98.Main;
import pes98.math.Vec;
import pes98.game.Game;
import pes98.rendering.Screen;
import pes98.physics.Rigidbody;
import pes98.physics.PhysicsEngine;
import pes98.network.packet.PacketScore;
import pes98.physics.collider.SphereCollider;
import pes98.network.packet.PacketBallPosition;

public class Ball extends Entity {
	public Rigidbody body;
	public int x, y, radius = 13;

	public Ball(Vec position) {
		super(position);		
	}

	public void initEntity() {
		body = new Rigidbody(position, new SphereCollider(radius), this);
		PhysicsEngine.bodys.add(body);
	}
	
	public void goal(int XY, int pos) {
		if(XY == 0 || XY == 1) {
			if (position.y > Main.HEIGHT / 2 - 64 && position.y < Main.HEIGHT / 2 + 64) {//goal
				body.position.x = Main.WIDTH / 2;
				body.position.y = Main.HEIGHT / 2;
				body.speed.x = 0;
				body.speed.y = 0;
				game.player.getNetwork().send(new PacketBallPosition((int) position.x, (int) position.y, game.player.id).data.getBytes());
				if(XY == 0) {
					game.LPoints++;
					game.player.getNetwork().send(new PacketScore(game.RPoints, game.LPoints).data.getBytes());
				}
				else {
					game.RPoints++;
					game.player.getNetwork().send(new PacketScore(game.RPoints, game.LPoints).data.getBytes());
			}
				return;
			} else {
				body.position.x = pos;
				body.speed.x = -body.speed.x;
			}
		} else {
			body.position.y = pos;
			body.speed.y = -body.speed.y;
		}
	}
	
	public void update() {
		if(position.x < 10) goal(0, 10);//LeftSide
		if(position.y < 10) goal(2, 10);//UpSide
		if(position.x > Main.WIDTH - 10) goal(1, Main.WIDTH - 10);//RightSide
		if(position.y > Main.HEIGHT - 10) goal(2, Main.HEIGHT - 10);//DownSide
		position.setVec(body.position);	
		if(body.speed.length() > 0)
				game.player.getNetwork().send(new PacketBallPosition((int) position.x, (int) position.y, game.player.id).data.getBytes());
	}

	public void render(Screen screen) {
		screen.drawBall((int) position.x, (int) position.y, radius, 0x333333);//grey
		screen.drawBall((int) position.x, (int) position.y, radius - 3, 0xffffff);//white 
	}
	
	public void init(Game game) {
		this.game = game;		
	}

	public void renderGui(Graphics g) {}
}
