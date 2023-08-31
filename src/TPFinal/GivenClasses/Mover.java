package TPFinal.GivenClasses;

import processing.core.PVector;

public class Mover {
	public PVector pos;
	public PVector vel;
	public float mass;
	public float radius;
	public PVector acc;
	
	public Mover(PVector pos, PVector vel, float mass, float radius) {
		this.pos = pos.copy();
		this.vel = vel.copy();
		this.mass = mass;
		this.radius = radius;
		acc = new PVector();
	}
	
	public void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
	}
	
	public void move(float dt) {
		vel.add(acc.mult(dt));
		pos.add(PVector.mult(vel, dt));
		acc.mult(0);
	}
}
