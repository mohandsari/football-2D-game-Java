package pes98.physics;

import pes98.math.Vec;
import pes98.game.entities.Entity;
import pes98.physics.collider.Collider;

public class Rigidbody {
	public Vec position, speed;
	public Collider collider;
	public float drag, mass;
	public Rigidbody nearBody;
	public CollisionData nearData;
	public Entity holder;
	
	public Rigidbody(Vec pos, Collider collider, Entity e) {
		this.mass = 1;
		this.holder = e;
		this.drag = 0.98f;
		this.position = pos;
		this.speed = new Vec();
		this.collider = collider;
		this.collider.position = pos;
	}
		
	public CollisionData isNear(Rigidbody body) {
		return collider.isNear(body.collider);
	}
	
	public void applyForce(Vec direction, float force) {
		speed.add(direction.copy().mul(force));
	}
	
	public void update() {
		position.add(speed);
		speed.mul(drag);
		collider.position = position;
	}
	
	public void handleCollision(Rigidbody body, CollisionData data) {
		float dist = Math.abs(data.inLength / 2);
		Vec startSpeed1 = new Vec(speed);
		Vec startSpeed2 = new Vec(body.speed);		
		speed.add(data.normal.mul((startSpeed2.length() - startSpeed1.length()) * -(1.1f  + dist)));
	}
}