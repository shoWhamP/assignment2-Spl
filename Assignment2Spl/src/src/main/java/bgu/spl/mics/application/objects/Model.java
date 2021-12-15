package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
	public enum Status {
		 PreTrained, Training, Trained,Tested
	   }
	public enum Result {
		 None, Good, Bad
	   }
	private String name;
	private Data data;
	private Student student;
	private Status status;
	private Result results;
	
	Model(String name,Data d, Student s, Status stat) {
		this.name=name;
		this.data=d;
		this.student=s;
		this.status = stat;
		this.results=Result.None;
	}
	
    public String getName() {
    	return this.name;
    }
    
	public Data getData() {
		return this.data;
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Result getResult() {
		return this.results;
	}
	
}
