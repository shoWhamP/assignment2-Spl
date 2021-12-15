package bgu.spl.mics.application.objects;

import java.util.*;
import java.util.LinkedList;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	private Collection<GPU> GPUs;
	private Collection<CPU> CPUs;
	private Queue<DataBatch> unproccessed=new LinkedList<DataBatch>();
	private LinkedList<String> modelNames;
	private int processedBatchesCount;
	private int cpuUsedTime;
	private int gpuUsedTime;
	/**
     * Retrieves the single instance of this class.
     */
	private static Cluster cluster=new Cluster();
	private Cluster() {
		this.CPUs=null;
		this.GPUs=null;
		this.modelNames=new LinkedList<String>();
		this.cpuUsedTime=0;
		this.gpuUsedTime=0;
		this.processedBatchesCount=0;
	}
	public static Cluster getInstance() {
		//TODO: Implement this
		return new Cluster();
	}
	public void passToCpu(DataBatch b) {unproccessed.add(b);}
	public DataBatch getNextData() {return unproccessed.remove();}
	

}
