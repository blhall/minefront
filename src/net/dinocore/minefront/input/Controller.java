package net.dinocore.minefront.input;

public class Controller {
	public double x, y, z, rotation, xa, ya, za, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
		
	public void tick(boolean forward, boolean back,boolean left,boolean right, boolean jump, boolean crouch, boolean prone, boolean run) {
		double rotationSpeed = 0.01;
		double walkSpeed = 0.5;
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		double proneHeight = 0.75;
		double xMove = 0.0;
		double zMove = 0.0;
		
		if (forward) {
			zMove += walkSpeed;
			walk = true;
		}
		
		if (back) {
			zMove -= walkSpeed;
			walk = true;
		}
		
		if (left) {
			xMove -= walkSpeed;
			walk = true;
		}
		
		if (right) {
			xMove += walkSpeed;
			walk = true;
		}
		
		if (turnLeft) {
			rotationa -= rotationSpeed;
		}
		
		if (turnRight) {
			rotationa += rotationSpeed;
		}
		
		if (jump) {
			y += jumpHeight;
		}
		
		if (crouch) {
			y -= crouchHeight;
			run = false;
		}
		
		if (prone) {
			y -= proneHeight;
			run = false;
		}
		
		if (run) {
			walkSpeed = walkSpeed * 2;
			walk = true;
		}
		
		if(!forward && !back && !run && !left && !right) {
			walk = false;
		}
		

		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		
		x += xa;
		y *= 0.9;
		z += za;
		
		
		xa *= 0.1;
		za *= 0.1;
		rotation +=  rotationa;
		rotationa *= 0.8;
	}
}
