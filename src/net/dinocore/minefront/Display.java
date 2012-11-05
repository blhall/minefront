package net.dinocore.minefront;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import net.dinocore.minefront.graphics.Render;
import net.dinocore.minefront.graphics.Screen;
import net.dinocore.minefront.input.Controller;
import net.dinocore.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Minefront pre-alpa 0.1";

	private Thread thread;
	private boolean running = false;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;
	private int newY = 0;
	private int oldY = 0;
	private int fps;
	

	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		screen = new Screen(WIDTH, HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		requestFocus();
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
			
			newX = InputHandler.MouseX;
			if (newX > oldX){
				Controller.turnRight = true;
			}
			else if (newX < oldX){
				Controller.turnLeft = true;
			}
			else if (newX == oldX) {
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}			
			oldX = newX;
			
			newY = InputHandler.MouseY;
			if (newY > oldY){
				
			}
			else if (newY < oldY){
				
			}
			else if (newY == oldY) {
				
			}			
			oldY = newY;
		}
	}

	private void tick() {
		game.tick(input.key);
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Verdana", 2, 25));
		g.setColor(Color.YELLOW);
		g.drawString(fps + " FPS", 20, 50);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		System.out.println("Running...");

		game.start();
	}
}