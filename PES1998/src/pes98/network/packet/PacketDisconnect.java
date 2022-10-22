package pes98.network.packet;

import java.net.InetAddress;

import pes98.network.NetworkClient;
import pes98.network.server.Server;
import pes98.game.entities.ClientPlayer;
import pes98.network.server.entities.NetworkPlayer;

public class PacketDisconnect extends Packet{
	private int id;
	
	public PacketDisconnect() {
		super(3);
	}
	
	public PacketDisconnect(ClientPlayer player) {
		super(3);
		writeInt(player.id);
	}
	
	public PacketDisconnect(NetworkPlayer player) {
		super(3);
		writeInt(player.id);
	}
	
	public void read(String data) {
		readInt(data);
		id = readInt(data);
	}
	
	public void process(Server server, InetAddress address, int port) {
		NetworkPlayer player = server.getPlayer(id);
		server.players.remove(server.getPlayer(id));
		server.sendToAll(new PacketDisconnect(player).data.getBytes());
		System.out.println("[SERVER] " + player.name + " disconnected !");
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		String playerName = client.game.getPlayer(id).name;
		client.game.entityManager.getEntities().remove(client.game.getPlayer(id));
		client.game.console.addToLog(playerName + " disconnected!");
	}
}
