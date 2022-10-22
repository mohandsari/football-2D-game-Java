package pes98.network.packet;

import java.net.InetAddress;

import pes98.game.entities.Player;
import pes98.network.NetworkClient;
import pes98.network.server.Server;
import pes98.game.entities.ClientPlayer;
import pes98.network.server.entities.NetworkPlayer;

public class PacketConnect extends Packet{
	private String name;
	private int id, posX, posY, LPoint, RPoint;
	
	public PacketConnect() {
		super(0);
	}
	
	public PacketConnect(ClientPlayer player) {
		super(0);
		writeInt(player.id);
		writeString(player.name);
		writeInt( (int) player.position.x);
		writeInt( (int) player.position.y);
		writeInt(player.game.LPoints);
		writeInt(player.game.RPoints);
	}
	
	public PacketConnect(NetworkPlayer player) {
		super(0);
		writeInt(player.id);
		writeString(player.name);
		writeInt(player.posX);
		writeInt(player.posY);
		writeInt(player.server.LScore);
		writeInt(player.server.RScore);
	}

	public void read(String data) {
		readInt(data); 
		id = readInt(data);
		name = readString(data);
		posX = readInt(data);
		posY = readInt(data);
		RPoint = readInt(data);
		LPoint = readInt(data);
	}

	public void process(Server server, InetAddress address, int port) {
		NetworkPlayer player = new NetworkPlayer(id, posX, posY, name, port, server, address);
		for(int i = 0; i < server.players.size(); i++){
			NetworkPlayer p = server.players.get(i);
			if(p.id == id) continue;
			Packet packet = new PacketPlayerSync(p);
			server.send(packet.data.getBytes(), address, port);
		}
		server.players.add(player);
		server.sendToAll(new PacketConnect(player).data.getBytes());
		System.out.println("[SERVER] " + name + " just connected !");
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		client.game.spawnPlayer(new Player(id, name, posX, posY));
		client.game.LPoints = LPoint;
		client.game.RPoints = RPoint;
		client.game.console.addToLog(name + " just connected !");
	}
}
