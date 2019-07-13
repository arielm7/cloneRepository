package cloneFinder.output;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.javaparser.utils.Pair;

import cloneFinder.cloneDetection.badHash.colition;

public class generateResults{

	ArrayList<Pair<String,colition>> finalCloneList;
	
	public generateResults(ArrayList<Pair<String, colition>> finalCloneList) {
		super();
		this.finalCloneList = finalCloneList;
	}

	public void printResults(ArrayList<Pair<String,colition>> finalCloneListPerFunction, String funcName) {
		Iterator<Pair<String,colition>>finalCloneListPerFunctionIterator=finalCloneListPerFunction.iterator();
		//System.out.println("\n############################################################################\n");
		//System.out.println("\n--------------Clones detectados para el método: "+funcName+ "--------------\n");
		while(finalCloneListPerFunctionIterator.hasNext()) {
			Pair<String,colition> clone=finalCloneListPerFunctionIterator.next();
        	System.out.println("Ubicacion del codigo en el metodo: "+funcName+" "+clone.b.getThisFunc());
        	System.out.println("Clon detectado en el método: "+clone.a+" "+clone.b.getThatFunc()+"\n");
        	
    
        
        	}
	
	}

}