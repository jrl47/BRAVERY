package Main;

/**
 * 
 * @author Jacob
 * Keeps track of universal values and sets up Game framework.
 */
public class Game implements Runnable{
	private final double TICK_LENGTH = 1000000000.0/30.0;
	
	private Thread thread;
	private boolean exitGame;
	private Display view;
	private World model;
	private InputListener listener;
	public static long ticks;
	public Game(){
		listener = new InputListener();
		view = new Display(listener);
		model = new World();
		init();
	}
	public synchronized void init(){
		thread = new Thread(this, "Crystals");
		exitGame = false;
		thread.start();
	}
	public void run() {
		long firstTime = System.nanoTime();
		while(exitGame==false){
			step();
			render();
			useInput();
			long now = System.nanoTime();
			while(now - (firstTime+ticks*TICK_LENGTH) < TICK_LENGTH){
				Thread.yield();
				try{
					Thread.sleep(1);
				} catch(Exception e) {}
				now = System.nanoTime();
			}
			ticks++;
		}
	}
	public synchronized void exit(){
		exitGame = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void step() {
		model.step();
	}
	public void render() {
		view.render(model);
	}
	public void useInput() {
		listener.step(model.getComponents());
	}
}
