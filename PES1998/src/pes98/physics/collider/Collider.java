package pes98.physics.collider;

import pes98.math.Vec;
import pes98.physics.CollisionData;

public abstract class Collider {
	public Vec position;
	public abstract CollisionData getCollisionData(Collider collider);
	public abstract CollisionData isNear(Collider collider);
}
