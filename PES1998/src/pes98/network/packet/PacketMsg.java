package pes98.network.packet;

import java.net.InetAddress;

import pes98.network.NetworkClient;
import pes98.network.server.Server;
import pes98.game.entities.ClientPlayer;
import pes98.network.server.entities.NetworkPlayer;

public class PacketMsg extends Packet{
	private String msg;
	private int id;

	public PacketMsg() {
		super(6);
	}

	public PacketMsg(ClientPlayer player, String message) {
		super(6);
		writeInt(player.id);
		writeString(message);
		writeString(player.name);
	}
	
	public PacketMsg(NetworkPlayer player, String message) {
		super(6);
		writeInt(player.id);
		writeString(message);
		writeString(player.name);
	}
		
	public void read(String data) {
		readInt(data);
		id = readInt(data);
		msg = readString(data);
	}

	public void process(Server server, InetAddress address, int port) {
		NetworkPlayer player = server.getPlayer(id);
		server.sendToAll(new PacketMsg(player, msg).data.getBytes());
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		String playerName = client.game.getPlayer(id).name;
		client.game.console.addToLog(playerName + ": " + msg);
	}
}

