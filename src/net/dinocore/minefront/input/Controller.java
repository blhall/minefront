package net.dinocore.minefront.input;

public class Controller {
	public double x, z, y, rotation, xa, za, ya, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	
	public void tick(boolean forward, boolean back,boolean left,boolean right) {
		double rotationSpeed = 0.01;
		double walkSpeed = 1.0;
		double xMove = 0.0;
		double zMove = 0.0;
		
		if (forward) {
			zMove += walkSpeed;
		}
		
		if (back) {
			zMove -= walkSpeed;
		}
		
		if (left) {
			xMove -= walkSpeed;
		}
		
		if (right) {
			xMove += walkSpeed;
		}
		
		if (turnLeft) {
			rotationa -= rotationSpeed;
		}
		
		if (turnRight) {
			rotationa += rotationSpeed;
		}
		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		
		x += xa;
		z += za;
		
		xa *= 0.1;
		za *= 0.1;
		rotation +=  rotationa;
		rotationa *= 0.8;
	}
}
