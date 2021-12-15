package src.main.java.bgu.spl.mics.application.services;

import java.util.LinkedList;
import java.util.List;

import src.main.java.bgu.spl.mics.MicroService;
import src.main.java.bgu.spl.mics.application.objects.Model.Result;
import src.main.java.bgu.spl.mics.example.messages.PublishConferenceBroadcast;
import src.main.java.bgu.spl.mics.example.messages.PublishResultsEvent;
import src.main.java.bgu.spl.mics.example.messages.TickBroadcast;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConfrenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    private List<String> successfulModels=new LinkedList<String>();
	private int date;
    public ConferenceService(String name,int d) {
        super(name);
        date=d;
    }

    @Override
    protected void initialize() {
        // TODO Implement this
    	subscribeBroadcast(TickBroadcast.class, (TickBroadcast t)->{
    												if(t.getTime()==date) {
    													PublishConferenceBroadcast toPublish=new PublishConferenceBroadcast(successfulModels);
    													sendBroadcast(toPublish);
    													terminate();}
    													});
    	subscribeEvent(PublishResultsEvent.class, (PublishResultsEvent pre)->{
    		if(pre.getModel().getResult()==Result.Good)
    			successfulModels.add(pre.getModel().getName());
    	});
    }
}
