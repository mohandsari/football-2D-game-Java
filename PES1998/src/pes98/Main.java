package pes98;

import java.awt.Font;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pes98.game.Game;
import pes98.inputs.Input;
import pes98.rendering.Screen;
import pes98.physics.PhysicsEngine;
import pes98.network.packet.PacketMsg;
import pes98.network.packet.PacketDisconnect;

public class Main extends Canvas implements Runnable {
	private Game game;
	private Screen screen;
	private int[] pixelsTab;
	private BufferedImage image;
	private boolean running = false;
	private final int centerScale = 30;
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 720, HEIGHT = 480;
	
	public Main() {
		image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixelsTab = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		screen = new Screen(WIDTH,HEIGHT);
		this.addKeyListener(Input.getInputHandler());
		game = new Game();
	}
	
	public void update() throws InterruptedException {
		PhysicsEngine.update();
		game.entityManager.update();
		game.console.update();
		if(Input.getKey(KeyEvent.VK_M)) {
			Thread.sleep(100);
			String msg = JOptionPane.showInputDialog("Your message: ");
			//if(msg.equals("null")) JOptionPane.showMessageDialog(null, "Message canceled", "Error ", JOptionPane.INFORMATION_MESSAGE);
			game.player.getNetwork().send(new PacketMsg(game.player, msg).data.getBytes());
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		game.render(screen);
		for(int i = 0; i < pixelsTab.length; i++) {
			pixelsTab[i]=screen.pixelsSc[i];
			screen.pixelsSc[i]= 0;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 1, 30));
		g.drawString(game.RPoints + " - " + game.LPoints, WIDTH / 2 - centerScale, 40);
		game.entityManager.renderGui(g);
		game.console.render(g);
		g.dispose();
		bs.show();
	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		while(running) {
			try {
				update();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			render();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		main.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		main.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		JFrame frame= new JFrame("PES98");
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				main.game.player.getNetwork().send(new PacketDisconnect(main.game.player).data.getBytes());
				System.exit(0);
			}
		});
		frame.add(main);
		frame.pack(); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		main.start();
		
	 }
}