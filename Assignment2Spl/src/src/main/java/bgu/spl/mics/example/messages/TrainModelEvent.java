package src.main.java.bgu.spl.mics.example.messages;
import src.main.java.bgu.spl.mics.application.objects.*;
import src.main.java.bgu.spl.mics.Event;

public class TrainModelEvent implements Event<Model> {
	private Model model;
	
	public TrainModelEvent(Model m) {model=m;}
	public Model getModel() {return model;}

}
