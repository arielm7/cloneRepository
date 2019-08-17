package cloneFinder.output;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import com.github.javaparser.utils.Pair;
import cloneFinder.cloneDetection.badHash.colition;

public class generateResults {

	private ArrayList<Pair<String, colition>> finalCloneList;
	private JSONArray clonArray;
	JsonGenereator json; 
	public generateResults() {
		super();
		this.clonArray = new JSONArray();
		this.json = new JsonGenereator();
	}

	public void printResults(final ArrayList<Pair<String, colition>> finalCloneListPerFunction, final String funcName) {
		final Iterator<Pair<String, colition>> finalCloneListPerFunctionIterator = finalCloneListPerFunction.iterator();
		
		while (finalCloneListPerFunctionIterator.hasNext()) {
			final Pair<String, colition> clone = finalCloneListPerFunctionIterator.next();
			System.out.println("Ubicacion del codigo en el metodo: " + funcName + " " + clone.b.getThisFunc());
			System.out.println("Clon detectado en el método: " + clone.a + " " + clone.b.getThatFunc() + "\n");
			json.cloneToJson(clonArray, funcName, clone.a, clone.b.getThisFunc(), clone.b.getThatFunc());
		}
		
		

	}
	
	public void saveResults() {
		json.generateJson(clonArray);
	}

}