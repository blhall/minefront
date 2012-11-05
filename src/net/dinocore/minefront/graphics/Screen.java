// episode 15 - part of way through 16
package net.dinocore.minefront.graphics;

import java.util.Random;

import net.dinocore.minefront.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt();
		}
	}

	public void render(Game game) {
		render.floor(game);
		render.renderDistanceLimiter();
		draw(render, 0, 0);
	}
}
