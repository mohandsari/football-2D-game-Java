package pes98.game;

import javax.swing.JOptionPane;

import pes98.Main;
import pes98.math.Vec;
import pes98.rendering.Image;
import pes98.rendering.Screen;
import pes98.game.entities.Ball;
import pes98.game.entities.Entity;
import pes98.game.entities.Player;
import pes98.game.entities.ClientPlayer;

public class Game {
	public static String address;
	public static boolean host = true;
	public Ball ball;
	public Console console;
	public ClientPlayer player;
	public EntityManager entityManager;
	public int RPoints = 0, LPoints = 0;
	private int heightGoal = 128, widthGoal = 42;
	Image field = Image.loadImage("/terrain.png");
	Image gLImage = Image.loadImage("/GoalLeft.png");
	Image gRImage = Image.loadImage("/GoalRight.png");
	
	public Game() {
		entityManager = new EntityManager();
		player = new ClientPlayer(new Vec((int)(Math.random() * Main.WIDTH),(int)( Math.random() * Main.HEIGHT)));
		player.game = this;		
		player.connect(this, JOptionPane.showInputDialog("Host IP address: "), Integer.parseInt(JOptionPane.showInputDialog("Port: ")));
		ball = new Ball(new Vec(Main.WIDTH / 2, Main.HEIGHT / 2));
		entityManager.add(player, this);//spawnPlayer
		entityManager.add(ball, this);//spawnBall
		console = new Console();
	}

	public void render(Screen screen) {
		screen.drawImage(0, 0, screen.width, screen.height, field);
		screen.drawImage(0, screen.height / 2 - heightGoal / 2, widthGoal, heightGoal, gLImage);
		screen.drawImage(screen.width - widthGoal, screen.height / 2 - heightGoal / 2, widthGoal, heightGoal, gRImage);
		entityManager.render(screen);
	}
	
	public void spawnPlayer(Player p) {
		if(player.id == p.id) return;
		entityManager.add(p, this);
	}
	
	public Player getPlayer(int id) {
		for(Entity e : entityManager.getEntities()) {
			if(e instanceof Player && ((Player) e).id == id) return (Player) e;
		}
		return null;
	}
}