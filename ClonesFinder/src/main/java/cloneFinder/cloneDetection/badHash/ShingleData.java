package cloneFinder.cloneDetection.badHash;

import com.github.javaparser.utils.Pair;

public class ShingleData {
	
	private long hashValue;
	private Pair<Integer,Integer> shingleLocation;
	
	public ShingleData(long hashValue, int beginLine, int endLine) {
		super();
		this.hashValue = hashValue;
		this.setShingleLocation(new Pair<Integer,Integer>(beginLine,endLine));
	}
	public ShingleData(long hashValue, Pair<Integer, Integer> shingleLocation) {
		super();
		this.hashValue = hashValue;
		this.shingleLocation = shingleLocation;
	}
	public long getHashValue() {
		return hashValue;
	}
	public void setHashValue(long hashValue) {
		this.hashValue = hashValue;
	}
	public Pair<Integer, Integer> getShingleLocation() {
		return shingleLocation;
	}
	public void setShingleLocation(Pair<Integer, Integer> shingleLocation) {
		this.shingleLocation = shingleLocation;
	}
}
