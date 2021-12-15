package src.main.java.bgu.spl.mics.example.messages;

import src.main.java.bgu.spl.mics.application.objects.*;
import src.main.java.bgu.spl.mics.Event;

public class TestModelEvent implements Event<Model> {
	private String result;
	private Model model;
	public TestModelEvent(Model m) {
		model=m;
	}
	public Model getModel() {return model;}
	public void setResult(String s) {result=s;}//the gpumics uses it
	public String getResult() {return result;}
}
