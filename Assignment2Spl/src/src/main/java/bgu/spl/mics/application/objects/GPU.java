package src.main.java.bgu.spl.mics.application.objects;

import java.util.Collection;
import java.util.*;
import src.main.java.bgu.spl.mics.application.objects.*;
/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
	private static int id=0;
    public enum Type {RTX3090, RTX2080, GTX1080}
    private int capacity;
    private Type type;
    private Queue <DataBatch> vRam;
    private Queue <DataBatch> unprocessed;
    private Cluster cluster;
    private int trainDuration;
    //private Model model;
    private int GpuId;
    private int batchestoTrain=0;
    
    GPU(Type t/*, Model m, Cluster c*/){
    	this.type=t;
    	this.cluster=Cluster.getInstance();
    	// define capacity on basis of type
    	if(type==Type.RTX3090) {
    		this.capacity=32;
    		this.trainDuration=1;}
    	else if(type==Type.RTX2080) {
    		this.capacity=16;
    		this.trainDuration=2;}
    	else {this.capacity=8;
    		this.trainDuration=4;
    	}
    	this.vRam=new PriorityQueue<DataBatch>();//check if its ok to do that
    	this.unprocessed=new PriorityQueue<DataBatch>();
    	this.GpuId=id;
    	id++;
    	//******* need to start running his servant
    	
    }
    public void sendData() {//maybe we will have a thread that will be incharge of this
    	//sends data to cluster.
    	while(!unprocessed.isEmpty()) {
    		if(capacity!=0) {
    			cluster.passToCpu(unprocessed.remove());
    			capacity--;}
    		else {
    			try {
    			wait();
    			} catch(InterruptedException e) {}
    		}
    	}
    	
    }
    
    public void addProcessedBatch(DataBatch d) {
    	//this is the method the cluster uses to add processed dataBatch
    	if(capacity==0)
    		throw new IllegalStateException("yaben shel zona havRam mfotszts");
    	vRam.add(d);
    }
    
    public void trainModel() {//maybe we will have a thread that will be incharge of this
    	//this is the method the gpu service uses to train the model with processed data need to be used only if vRam is not empty
    	if(!vRam.isEmpty()) {
    		vRam.peek().increaseTick();
    		if(vRam.peek().geTicks()==trainDuration) {
    			vRam.remove();
    			capacity++;//we can now send another dataBatch to be trained.
    			notify();
    			batchestoTrain--;
    		}
    	}
    }
     
    public void divideToBatches(Model model) {
    	//this is the method the gpu services uses to divide the data to batches and initialize the unprocessed collection
    	int i=0;
    	while(i<model.getData().getSize()) {
    		DataBatch d=new DataBatch(model.getData(),i);
    		unprocessed.add(d);
    		i=i+1000;
    	}
    	batchestoTrain=unprocessed.size();
    }
    
    public int batchesLeft() {return batchestoTrain;}

}
