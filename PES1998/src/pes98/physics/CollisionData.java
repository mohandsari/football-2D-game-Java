package pes98.physics;

import pes98.math.Vec;

public class CollisionData {
	public Vec normal;
	public float inLength;
	public boolean collision;
	
	public CollisionData(boolean collision, Vec normal, float inLength) {
		this.normal = normal;
		this.inLength = inLength;
		this.collision = collision;
	}
}
