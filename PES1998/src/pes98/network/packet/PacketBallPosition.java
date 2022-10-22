package pes98.network.packet;

import java.net.InetAddress;

import pes98.network.NetworkClient;
import pes98.network.server.Server;

public class PacketBallPosition extends Packet {
	private int posX, posY, id;

	public PacketBallPosition() {
		super(4);
	}

	public PacketBallPosition(int x, int y, int id) {
		super(4);
		writeInt(id);
		writeInt(x);
		writeInt(y);
	}
	
	public void read(String data) {
		readInt(data);
		id = readInt(data);
		posX = readInt(data);
		posY = readInt(data);
	}

	public void process(Server server, InetAddress address, int port) {
		server.ball.posX = posX;
		server.ball.posY = posY;
		server.sendToOne(new PacketBallPosition(posX, posY, id).data.getBytes(), id);
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		client.game.ball.body.position.x = posX;
		client.game.ball.body.position.y = posY;
		client.game.ball.body.speed.x = 0;
		client.game.ball.body.speed.y = 0;
	}
}
