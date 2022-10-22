package pes98.game.entities;

import java.awt.event.KeyEvent;
import java.security.SecureRandom;

import javax.swing.JOptionPane;

import pes98.Main;
import pes98.math.Vec;
import pes98.game.Game;
import pes98.inputs.Input;
import pes98.rendering.Screen;
import pes98.network.NetworkClient;
import pes98.network.packet.PacketConnect;
import pes98.network.packet.PacketPosition;

public class ClientPlayer extends Player {
	NetworkClient net;
	private float radius = 17, speed = 0.03f, strikePow = 5, passPow = 2;

	public ClientPlayer(Vec position) {
		super((int) Math.abs(System.nanoTime() + new SecureRandom().nextInt()), JOptionPane.showInputDialog("Pseudo: "), (int) position.x, (int) position.y);
	}
	
	public void connect(Game game, String address, int port) {
		net = new NetworkClient(game, address, port);
		net.send(new PacketConnect(this).data.getBytes());
	}
	
	public void move () {
		if(Input.getKey(KeyEvent.VK_UP)) body.applyForce(new Vec(0, -1), speed);
		if(Input.getKey(KeyEvent.VK_DOWN)) body.applyForce(new Vec(0, 1), speed);
		if(Input.getKey(KeyEvent.VK_LEFT)) body.applyForce(new Vec(-1, 0), speed);
		if(Input.getKey(KeyEvent.VK_RIGHT)) body.applyForce(new Vec(1, 0), speed);
	}
	
	public void touchSide(int XY, float position) {
		if(XY == 0 ) {
			body.position.x = position;
			body.speed.x = 0;
		} else if (XY == 1) {
			body.position.y = position;
			body.speed.y = 0;
		}
	}
	
	public void update() {
		if(Input.getKey(KeyEvent.VK_R)) {//sprint
			speed = speed * (3/2);
			move();
			speed = speed / (3/2);
		}
		move();
		if(Input.getKey(KeyEvent.VK_SPACE)) {//shoot
			if(body.nearBody != null && !(body.nearBody.holder instanceof ClientPlayer)) {
				body.nearBody.applyForce(body.nearData.normal, strikePow);
			}
		}
		if(Input.getKey(KeyEvent.VK_T)) {//pass
			if(body.nearBody != null && !(body.nearBody.holder instanceof ClientPlayer)) {
				body.nearBody.applyForce(body.nearData.normal, passPow);
			}
		}
		if(body.speed.length() > 0) net.send(new PacketPosition(this).data.getBytes());
		position.setVec(body.position);
		if(position.x < radius) touchSide(0, radius);//LeftSide
		if(position.y < radius) touchSide(1, radius);//UpSide
		if(position.x > Main.WIDTH - radius) touchSide(0, Main.WIDTH - radius);//RightSide
		if(position.y > Main.HEIGHT - radius) touchSide(1, Main.HEIGHT - radius);//DownSide
	}
	
	public void render(Screen screen) {
		if(Input.getKey(KeyEvent.VK_SPACE)) screen.drawBall((int) position.x, (int) position.y, (int) radius, 0xff0000);//red
		else if(Input.getKey(KeyEvent.VK_T)) screen.drawBall((int) position.x, (int) position.y, (int) radius, 0x0066ff);//blue
		else if(Input.getKey(KeyEvent.VK_R)) screen.drawBall((int) position.x, (int) position.y, (int) radius, 0xffff00);//yellow
		else screen.drawBall((int) position.x, (int) position.y, (int) radius, 0x000000);//black
		screen.drawBall((int) position.x, (int) position.y, (int) radius - 3, 0xaaaaaa);//grey
	}
	
	public NetworkClient getNetwork() {
		return net;
	}
}
