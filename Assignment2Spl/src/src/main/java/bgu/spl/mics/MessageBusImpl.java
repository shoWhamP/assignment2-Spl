package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private ConcurrentHashMap<Class<? extends Event>,Queue<MicroService>> evenToSubs = new ConcurrentHashMap<Class<? extends Event>,Queue<MicroService>>();
	private ConcurrentHashMap<MicroService,Queue<Message>> micsToMsgs = new ConcurrentHashMap<MicroService,Queue<Message>>();
	private ConcurrentHashMap<Class<? extends Broadcast>,Queue<MicroService>> broadcasToSubs = new ConcurrentHashMap<Class<? extends Broadcast>,Queue<MicroService>>();
	private ConcurrentHashMap<Event,Future> evenToFuture = new ConcurrentHashMap<Event,Future>();
	private static MessageBusImpl instance=null;

	private MessageBusImpl() {
		instance=this;
		// TODO Auto-generated constructor stub
	}
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub
		if(evenToSubs.get(type)==null) {
			Queue <MicroService> q = new LinkedList<MicroService>();
			q.add(m);
			evenToSubs.put(type, q);
		}
		else {
			evenToSubs.get(type).add(m);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub
		if(broadcasToSubs.get(type)==null) {
			Queue <MicroService> q = new LinkedList<MicroService>();
			q.add(m);
			broadcasToSubs.put(type, q);
		}
		else {
			broadcasToSubs.get(type).add(m);
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub
		evenToFuture.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub
		//need to notify all the mcis that were subscribed incase they're waiting***
		Queue <MicroService> micsQ=broadcasToSubs.get(b);
		for(MicroService m: micsQ) {
			micsToMsgs.get(m).add(b);
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		if(evenToSubs.get(e)!=null) {
			// need to send the right microservice the event according to the robin shit
			
			MicroService m=evenToSubs.get(e).remove();// remove the mics that get the current message
			micsToMsgs.get(m).add(e);//adds the message to the mics msgQ
			evenToSubs.get(e).add(m);//re-adding the mics to the event micsQ- now it is last to recieve event
			
			//need to notify m incase he is wating in awaitmessage***
			Future<T>f=new Future<T>();
			evenToFuture.put(e, f);
			return f;
		}
		return null;
	}

	@Override
	public void register(MicroService m) {
		// TODO Auto-generated method stub
		Queue <Message> q= new LinkedList<Message>();
		micsToMsgs.put(m, q);
		

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub
		if(micsToMsgs.get(m)!=null) {
			micsToMsgs.remove(m);
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		if(micsToMsgs.get(m)==null)
			throw new IllegalStateException("mics was not regestired");
		while(micsToMsgs.get(m).isEmpty()) {
			wait();
		}
		return micsToMsgs.get(m).remove();
	}
	
	public static MessageBusImpl getInstance() {
		if(instance==null)
		{
			instance = new MessageBusImpl();
		}
		return instance;
	}

	

}
