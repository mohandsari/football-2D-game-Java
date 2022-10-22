package pes98.network.packet;

import java.net.InetAddress;

import pes98.game.entities.Player;
import pes98.network.NetworkClient;
import pes98.network.server.Server;
import pes98.game.entities.ClientPlayer;
import pes98.network.server.entities.NetworkPlayer;

public class PacketPosition extends Packet {
	private int id, ignore, posX, posY;

	public PacketPosition() {
		super(2);
	}

	public PacketPosition(Player player, int playerId) {
		super(2);
		writeInt(player.id);
		writeInt(playerId);
		writeInt((int)player.position.x);
		writeInt((int)player.position.y);
	}
	
	public PacketPosition(ClientPlayer player) {
		super(2);
		writeInt(player.id);
		writeInt(player.id);
		writeInt((int)player.position.x);
		writeInt((int)player.position.y);
	}
	
	public PacketPosition(NetworkPlayer player) {
		super(2);
		writeInt(player.id);
		writeInt(player.id);
		writeInt((int)player.posX);
		writeInt((int)player.posY);
	}
	
	public void read(String data) {
		readInt(data);
		id = readInt(data);
		ignore = readInt(data);
		posX = readInt(data);
		posY = readInt(data);
	}

	public void process(Server server, InetAddress address, int port) {
		NetworkPlayer player = server.getPlayer(id);
		if (player == null) return;
		player.posX = posX;
		player.posY = posY;
		server.sendToOne(new PacketPosition(player).data.getBytes(), ignore);
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		Player player = client.game.getPlayer(id);
		if (player == null) return;
		player.body.position.x = posX;
		player.body.position.y = posY;
		player.body.speed.x = 0;
		player.body.speed.y = 0;
	}
}
