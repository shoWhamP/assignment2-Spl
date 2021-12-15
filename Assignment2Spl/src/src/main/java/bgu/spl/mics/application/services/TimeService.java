package src.main.java.bgu.spl.mics.application.services;
import bgu.spl.mics.Broadcast;
import  src.main.java.bgu.spl.mics.*;
import src.main.java.bgu.spl.mics.example.messages.TickBroadcast;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int time=0;
	private int speed;
	private int duration;
	
	public TimeService(int speed, int duration) {
		super("TimeService");
		this.speed = speed;
		this.duration = duration;
	}

	@Override
	protected void initialize() {
		while(duration!=0) {
			Broadcast tick= new TickBroadcast();
			sendBroadcast(tick);
			try {
			java.lang.Thread.sleep(speed);
			}
			catch(InterruptedException e) {}
			duration--;
			time++;
		}
		terminate();
	}

}
