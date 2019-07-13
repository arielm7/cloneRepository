package cloneFinder.inputConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Entry {
	//Constants
	static final String PN    = "projectName";
	static final String PID   = "projectId";
	static final String PP    = "projectPath";
	static final String CRED  = "credentials";
	static final String MS    = "minimumSize";
	static final String GRAN  = "granularity";
	static final String KOC   = "kindOfClone";
	static final String CHAR  = "character";
	static final String TOK   = "token";
	static final String SEN   = "sentence";
	static final String MET   = "method";
	static final String ONE   = "1";
	static final String TWO   = "2";
	static final String THREE = "3";
	static final String SHIN  = "shingle";
	static final String MAT   = "matrix";
	static final String HASH  = "hash";
	static final String WS    = "whiteSpaces";
	static final String COM  = "commentaries";
	
	/**
	 * Extract the metadata from a file given for 
	 * a path. Store the results in the input class.
	 * @param path String with the absolute path. Root of the project.
	 * @param input Wrapper to the metadata.
	 */
	public void readFile(String path,Input input) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			
			String projectName = (String) jsonObject.get(PN);
			input.name = projectName;
			System.out.println("Name is: " + projectName);
			
			String projectId = (String) jsonObject.get(PID);
			input.id = projectId;
			System.out.println("The id is: " + projectId);
			
			String projectPath = (String) jsonObject.get(PP);
			input.path = projectPath;
			System.out.println("The path is: " + projectPath);
			
			String credentials = (String) jsonObject.get(CRED);
			input.credential = credentials;
			System.out.println("My credential is: " + credentials);
			
			String minimumSize = (String) jsonObject.get(MS);
			input.minimumSize = minimumSize;
			System.out.println("The minimum size is: " + minimumSize);
			
			String commentaries = (String) jsonObject.get(COM);
			input.commentaries = (commentaries.equals("yes")?true:false);
			System.out.println("The commentaries are: " + input.commentaries);
			
			String whiteSpaces = (String) jsonObject.get(WS);
			input.whiteSpaces = (whiteSpaces.equals("yes")?true:false);
			System.out.println("The white space is: " + input.whiteSpaces);

			//loop array
			JSONArray granularity = (JSONArray) jsonObject.get(GRAN);
			JSONArray kindOfClone = (JSONArray) jsonObject.get(KOC);
			JSONArray method      = (JSONArray) jsonObject.get(MET);
			Iterator<String> granularityIterator = granularity.iterator();
			Iterator<String> kindOfCloneIterator = kindOfClone.iterator();
			Iterator<String> methodIterator      = method.iterator();
			boolean granularityExit  = false;
			boolean kindOfCloneExit  = false;
			boolean kindOfMethodExit = false;
			String temp;
			while(!(granularityExit && kindOfCloneExit && kindOfMethodExit)) {
				if(granularityIterator.hasNext()) {
					temp = granularityIterator.next();
					switch(temp) {
					  case CHAR:
						  input.granularityByCharacter = true;
						  System.out.println("Granularity: " + temp);
					    break;
					  case TOK:
						  input.granularityByToken = true;
						  System.out.println("Granularity: " + temp);
						  break;
					  case SEN:
						  input.granularityBySentence = true;
						  System.out.println("Granularity: " + temp);
						  break;
					  default:
					    // code block
					}
				}else {
					granularityExit = true;
				}
				
				if(kindOfCloneIterator.hasNext()) {
					temp = kindOfCloneIterator.next();
					switch(temp) {
					  case ONE:
						  input.clone1 = true;
						  System.out.println("The kind of clone is: " + temp);
					    break;
					  case TWO:
						  input.clone2 = true;
						  System.out.println("The kind of clone is: " + temp);
						  break;
					  case THREE:
						  input.clone3 = true;
						  System.out.println("The kind of clone is: " + temp);
						  break;
					  default:
					    // code block
					}
				}else {
					kindOfCloneExit = true;
				}
				
				if(methodIterator.hasNext()) {
					temp = methodIterator.next();
					switch(temp) {
					  case SHIN:
						  input.shingle = true;
						  System.out.println("The method is: " + temp);
					    break;
					  case MAT:
						  input.matrix = true;
						  System.out.println("The method is: " + temp);
						  break;
					  case HASH:
						  input.hash = true;
						  System.out.println("The method is: " + temp);
						  break;
					  default:
					    // code block
					}
				}else {
					kindOfMethodExit = true;
				}
			}
			
		}catch(FileNotFoundException e) {e.printStackTrace();}
		 catch(IOException e) {e.printStackTrace();}
		 catch(ParseException e) {e.printStackTrace();}
		 catch(Exception e) {e.printStackTrace();}
	}

	/**
	 * Iterate through the paths to get all the 
	 * file to analyze.
	 * @param dir Root to analyze.
	 * @param listaArchivos List with the paths of the files to analyze.
	 * @return The list with the files to analyze.
	 */
	public List<String> travelRoot(File dir,List<String> listaArchivos) {
		 
        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory())
                	travelRoot(listFile[i],listaArchivos);
                else {
                	if(listFile[i].getPath().endsWith(".java"))
                		listaArchivos.add(listFile[i].getPath());
                	}
                }
            return listaArchivos;
        }
        return null;
	}
	
}