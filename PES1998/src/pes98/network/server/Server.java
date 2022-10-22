package pes98.network.server;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import pes98.network.packet.*;
import pes98.network.server.entities.*;

public class Server implements Runnable{
	private int  port;
	public NetworkBall ball;
	private DatagramSocket socket;
	private boolean running = false;
	public int RScore = 0, LScore = 0;
	public List<NetworkPlayer> players = new ArrayList<NetworkPlayer>();

	public Server(int port) {
		try {
			socket = new DatagramSocket(port);
			this.port = port;
		} catch (SocketException e) {
			System.out.println("The port n°" + port + " is already in use");
			System.exit(1);
		}
		ball = new NetworkBall();
		running = true;
		new Thread(this, "Server").start();
	}

	public void run() {
		System.out.println("Server started on port " + port);
		receive();
	}
	
	public void receive() {
		new Thread("Receive") {
			public void run() {
				try {
					while(running) {
						byte[] data = new byte[1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						socket.receive(packet);
						parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void parsePacket(byte[] data, InetAddress address, int port) {
		String packetData = new String(data);
		int packetType = Integer.parseInt(packetData.split(" ")[0]);
		Packet packet = null;
		if(packetType == 0) packet = new PacketConnect();
		else if(packetType == 1) packet = new PacketPlayerSync();
		else if(packetType == 2) packet = new PacketPosition();
		else if(packetType == 3) packet = new PacketDisconnect();
		else if(packetType == 4) packet = new PacketBallPosition();
		else if(packetType == 5) packet = new PacketScore();
		else if(packetType == 6) packet = new PacketMsg();
		if(packet == null) return;
		packet.read(packetData);
		packet.process(this, address, port);
	}
	
	public void send(byte[] data, InetAddress address, int port) {
		new Thread("Send") {
			public void run() {
				try {
					DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void sendToAll(byte[] data) {
		for(int i = 0; i < players.size(); i++) {
			NetworkPlayer p = players.get(i);
			send(data, p.address, p.port);
		}
	}
	
	public void sendToOne(byte[] data, int id) {
		for(int i = 0; i < players.size(); i++) {
			NetworkPlayer p = players.get(i);
			if(p.id == id)continue;
			send(data, p.address, p.port);
		}
	}
	
	public NetworkPlayer getPlayer(int id) {
		for(NetworkPlayer p : players) {
			if(p.id == id) return p;
		}
		return null;
	}
	
	public static void ErrorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error ", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) throws Exception {
		String host = JOptionPane.showInputDialog("Are you the host? y/n");
		try {	
			if(host.equals("y") || host.equals("Y")) {
				new Server(Integer.parseInt(JOptionPane.showInputDialog("Port: ")));
				Runtime runHost = Runtime. getRuntime();
				runHost.exec("java -jar Main.jar");
			}else if(host.equals("n") || host.equals("N")) {
				Runtime runGuest = Runtime. getRuntime();
				runGuest.exec("java -jar Main.jar");
			} else ErrorMsg("Enter Y for Yes or N for No");
		} catch (NumberFormatException e) {
			ErrorMsg("Port number must be an integer");
		}
	}
}