package pes98.network.server.entities;

import java.net.InetAddress;

import pes98.network.server.Server;

public class NetworkPlayer {
	public String name;
	public Server server;
	public InetAddress address;
	public int id, posX, posY, port;
	
	public NetworkPlayer(int id, int x, int y, String name, int port, Server server, InetAddress address) {
		this.id = id;
		this.posX = x;
		this.posY = y;
		this.name = name;
		this.port = port;
		this.server = server;
		this.address = address;
	}
}
