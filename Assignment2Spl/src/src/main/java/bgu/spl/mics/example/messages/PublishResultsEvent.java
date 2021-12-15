package src.main.java.bgu.spl.mics.example.messages;

import src.main.java.bgu.spl.mics.application.objects.Model;
import src.main.java.bgu.spl.mics.Event;

public class PublishResultsEvent implements Event<Model> {
	private Model model;
	private boolean reachedConfrence=false;
	public PublishResultsEvent(Model model){
		this.model=model;
		}
	public Model getModel() {return model;}
	public void read() {
		reachedConfrence=true;
	}

}
