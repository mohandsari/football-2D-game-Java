package pes98.network.packet;

import java.net.InetAddress;

import pes98.network.NetworkClient;
import pes98.network.server.Server;

public abstract class Packet {
	public String data;
	private int readIndex = 0;
	
	public Packet(int packetType) {
		data = "" + String.valueOf(packetType);
	}
	
	public abstract void read(String data);
	public abstract void process(Server server, InetAddress address, int port);
	public abstract void process(NetworkClient client, InetAddress address, int port);
	
	protected void writeInt(int data) {
		this.data += " " + data;
	}
	
	protected void writeString(String data) {
		this.data += " " + data;
	}
	
	protected int readInt(String data) {
		int value = Integer.parseInt(data.split(" ")[readIndex].trim());
		readIndex++;
		return value;
	}
	
	protected String readString(String data) {
		String value = data.split(" ")[readIndex].trim();
		readIndex++;
		return value;
	}
}
