package pes98.math;

public class Vec {
	public float x, y;
	
	public Vec() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vec(Vec v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vec(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVec(Vec v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vec normalize() {
		float length = length();
		x /= length;
		y /= length;
		return this;
	}
	
	public Vec negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	public Vec copy() {
		return new Vec(x,y);
	}
	
	public Vec add(Vec v) {
		this.x += v.x;
		this.y += v.y;	
		return this;
	}
	
	public Vec sub(Vec v) {
		this.x -= v.x;
		this.y -= v.y;	
		return this;
	}
	
	public Vec mul(float x) {
		this.x *= x;
		this.y *= x;
		return this;
	}
}
