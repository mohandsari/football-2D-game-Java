package pes98.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import pes98.game.Game;
import pes98.network.packet.*;

public class NetworkClient implements Runnable{
	private int port;
	public Game game;
	private InetAddress address;
	private DatagramSocket socket;
	private boolean running = false;
	
	public NetworkClient(Game game, String address, int port) {
		this.game = game;
		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			socket = new DatagramSocket();
			running = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	
	public void run() {
		System.out.println("Client running: " + address.getHostAddress() + ", on port: " + port);
		while(running) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
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
		if(packet == null)return;
		packet.read(packetData);
		packet.process(this, address, port);
	}
	
	public void send(byte[] data) {
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
}
