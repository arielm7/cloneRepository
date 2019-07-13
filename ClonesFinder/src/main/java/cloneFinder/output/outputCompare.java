package cloneFinder.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class outputCompare {
	
	
	public static void main(String[] args) {
		
		JSONParser parser = new JSONParser();
		try {
			Object referenceObj = parser.parse(new FileReader("/home/ariel/TEC/diseño/implementation/ClonesFinder/resources/referenceLog.js"));
			JSONObject referenceJson = (JSONObject) referenceObj;
			Object outObj = parser.parse(new FileReader("/home/ariel/TEC/diseño/implementation/ClonesFinder/resources/outLog.js"));
			JSONObject outJson = (JSONObject) outObj;
			
			JSONObject referenceAnalisis= (JSONObject)referenceJson.get("Analisis");
			JSONArray referenceClones= (JSONArray)referenceAnalisis.get("Clones");
			JSONObject outAnalisis= (JSONObject)outJson.get("Analisis");
			JSONArray outClones= (JSONArray)outAnalisis.get("Clones");
			
			JSONObject referenceCopias= (JSONObject)referenceClones.get(0);
			JSONObject outCopias= (JSONObject)outClones.get(0);
			
			JSONArray referenceArrayCopias= (JSONArray)referenceCopias.get("Copias");
			JSONArray outArrayCopias= (JSONArray)outCopias.get("Copias");
			
			int rIndex=0; int oIndex=0;
			int verdaderosPositivos=0;
			int verdaderoNegativo=0;
			while(true) {
				
				if(oIndex>=outArrayCopias.size()) {
					break;
				}
				if(rIndex>=referenceArrayCopias.size()) {
					rIndex=oIndex;
				}
				JSONObject oCopia = (JSONObject)outArrayCopias.get(oIndex);
				JSONObject rCopia = (JSONObject)referenceArrayCopias.get(rIndex);
				
				String oName= oCopia.get("Metodo").toString();
				String rName= rCopia.get("Metodo").toString();
				
				int oLine= Integer.parseInt(oCopia.get("Numero_linea").toString());
				int rLine= Integer.parseInt(rCopia.get("Numero_linea").toString());
				if(oName.compareTo(rName)==0) {
					if(oLine==rLine) {
						verdaderosPositivos++;
					}
						
					oIndex++;
					rIndex++;
				}
				else if(oName.compareTo(rName)>0) {
					rIndex++;
				}
				else {
					verdaderoNegativo++;
					oIndex++;
				}
			
			}
			System.out.println("verdaderos positivos: "+verdaderosPositivos);
			System.out.println("verdaderosNegativos: "+verdaderoNegativo);
			
			
			
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
		catch(ParseException e) {e.printStackTrace();}
		catch(Exception e) {e.printStackTrace();}
		
		
		System.out.print("--");
	}
	

}
