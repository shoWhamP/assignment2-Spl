package bgu.spl.mics.application.objects;

import java.util.Collection;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    private Collection <DataBatch> processed;
    private Collection <DataBatch> unprocessed;
    private Cluster cluster;
    private int cores;
    
    CPU(int coresNum, Cluster c){
    	this.cluster=c;
    	this.cores=coresNum;
    	this.processed=null;
    	this.unprocessed=null;
    }
    
    public void uploadData() {
    	//this is the method the cluster uses to uploade data to be processed
    }
    
    public void proccessAndSend() {
    	//this is the method that proccesses data and then immidiatly sends it?
    }
    
    public DataBatch sendData() {
    	//this is the method that sends data back to cluster might not be needed because we can send data from the proccess method-needs to think.
    }
}

