package src.main.java.bgu.spl.mics.example.messages;
import java.util.*;
import src.main.java.bgu.spl.mics.Broadcast;

public class PublishConferenceBroadcast implements Broadcast {
	private List<String> goodResults;
	public PublishConferenceBroadcast(List<String> gr) {goodResults=gr;}
	public List<String> getResults(){return goodResults;}

}
