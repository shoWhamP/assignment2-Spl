package src.main.java.bgu.spl.mics.application.objects;

import src.main.java.bgu.spl.mics.application.services.ConferenceService;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private String name;
    private int date;
    public ConfrenceInformation(String name, int date) {
    	this.name=name;
    	this.date=date;
    	ConferenceService dobbi=new ConferenceService(name+"'s houseElf");
    	dobbi.run();
    }
    public String getName() {return name;}
    public int getDate() {return date;}
}
