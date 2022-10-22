package pes98.network.packet;

import java.net.InetAddress;

import pes98.network.NetworkClient;
import pes98.network.server.Server;

public class PacketScore extends Packet{
	private int RPoints, LPoints;

	public PacketScore() {
		super(5);
	}

	public PacketScore(int RPoint, int LPoint) {
		super(5);
		writeInt(RPoint);
		writeInt(LPoint);
	}
		
	public void read(String data) {
		readInt(data);
		LPoints = readInt(data);
		RPoints = readInt(data);
	}

	public void process(Server server, InetAddress address, int port) {
		server.RScore = RPoints;
		server.LScore = LPoints;
		server.sendToAll(new PacketScore(RPoints, LPoints).data.getBytes());
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		client.game.LPoints = LPoints;
		client.game.RPoints = RPoints;
	}
}
