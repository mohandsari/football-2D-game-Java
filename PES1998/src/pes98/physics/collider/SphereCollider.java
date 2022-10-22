package pes98.physics.collider;

import pes98.math.Vec;
import pes98.physics.CollisionData;

public class SphereCollider extends Collider {
	private float radius;
	
	public SphereCollider(float radius) {
		this.radius = radius;
	}

	public CollisionData getCollisionData(Collider collider) {
		CollisionData result = new CollisionData(false, new Vec(), 0);
		if(collider instanceof SphereCollider) {
			SphereCollider coll = (SphereCollider) collider;
			float dist = position.copy().sub(coll.position).length();
			if(dist < radius + coll.radius) {
				result.collision = true;
				Vec a = new Vec(position);
				Vec b = new Vec(coll.position);
				result.normal = new Vec(a.sub(b).normalize().negate());
				result.inLength = dist - (radius + coll.radius);
			}
		}
		return result;
	}

	public CollisionData isNear(Collider collider) {
		CollisionData result = new CollisionData(false, new Vec(), 0);
		if(collider instanceof SphereCollider) {
			SphereCollider coll = (SphereCollider) collider;
			if(position.copy().sub(coll.position).length() < radius + coll.radius + 1) {
				result.collision = true;
				Vec a = new Vec(position);
				Vec b = new Vec(coll.position);
				result.normal = new Vec(a.sub(b).normalize().negate());
			}
		}
		return result;
	}
}