package pes98.physics;

import java.util.List;
import java.util.ArrayList;

public class PhysicsEngine {
	public static List<Rigidbody> bodys = new ArrayList<Rigidbody>();
	
	public static void update() {
		for(Rigidbody bodyA : bodys) {
			for(Rigidbody bodyB : bodys) {
				if(bodyA.equals(bodyB)) continue;
				if(bodyA.isNear(bodyB).collision) {
					bodyA.nearBody = bodyB;
					bodyA.nearData = bodyA.isNear(bodyB);
				} else {
					bodyA.nearBody = null;
					bodyA.nearData = null;
				}
				CollisionData data = bodyA.collider.getCollisionData(bodyB.collider);
				if(data.collision) bodyA.handleCollision(bodyB, data);
			}
			bodyA.update();
		}
	}
}