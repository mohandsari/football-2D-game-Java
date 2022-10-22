package pes98.network.packet;

import java.net.InetAddress;

import pes98.game.entities.Player;
import pes98.network.NetworkClient;
import pes98.network.server.Server;
import pes98.network.server.entities.NetworkPlayer;

public class PacketPlayerSync extends Packet {
	private String name;
	private int id, posX, posY;

	public PacketPlayerSync() {
		super(1);
	}
	
	public PacketPlayerSync(NetworkPlayer player) {
		super(1);
		writeInt(player.id);
		writeString(player.name);
		writeInt((int)player.posX);
		writeInt((int)player.posY);
	}
	
	public void read(String data) {
		readInt(data); 
		id = readInt(data);
		name = readString(data);
		posX = readInt(data);
		posY = readInt(data);
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
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		client.game.spawnPlayer(new Player(id, name, posX, posY));
	}
}
