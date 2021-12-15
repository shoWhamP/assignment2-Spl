package src.main.java.bgu.spl.mics.application.services;

import java.util.List;
import src.main.java.bgu.spl.mics.example.messages.*;
import src.main.java.bgu.spl.mics.application.objects.*;
import src.main.java.bgu.spl.mics.*;
import src.main.java.bgu.spl.mics.example.messages.PublishConferenceBroadcast;
import src.main.java.bgu.spl.mics.example.messages.TrainModelEvent;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {
	private List<Model> models;
	private Student student;
    public StudentService(String name,List<Model> m, Student s) {
        super(name);
        models=m;
        student=s;
        // TODO Implement this
    }

    @Override
    protected void initialize() {
        // TODO Implement this
    	subscribeBroadcast(PublishConferenceBroadcast.class, (PublishConferenceBroadcast pcb)->{
    		List<String> toRead = pcb.getResults();
    		for(String name: toRead) {
    			for(Model m:models) {
    				if(m.getName().equals(name))
    					student.setPublications(student.getPublications()+1);//increase publications
    			student.setPapersRead(student.getPapersRead()+1);//increase papersRead
    			}
    		}
    	}); //end of callback function
    	
    	Thread modelHandler = new Thread(()->{    	for(Model model:models) {
    													TrainModelEvent scar = new TrainModelEvent(model);
    													Future<TrainModelEvent> trained = sendEvent(scar);
    													TestModelEvent testhim= new TestModelEvent(trained.get().getModel());
    													Future<TestModelEvent>  tested = sendEvent(testhim);
    													PublishResultsEvent cnn= new PublishResultsEvent(tested.get().getModel());
    													Future <PublishResultsEvent> publishim = sendEvent(cnn);
    													publishim.get();}
    													
    	});
    	modelHandler.start();
    	
    }
}
