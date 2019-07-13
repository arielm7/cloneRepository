package cloneFinder.inputConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper with the metadata extracted 
 * from the configuration file.
 * @author diego
 *
 */
public class Input {
	public String name;
	public String id;
	public String path;
	public boolean commentaries = false;
	public boolean whiteSpaces  = false;
	public boolean granularityByCharacter = false;
	public boolean granularityByToken     = false;
	public boolean granularityBySentence  = false;
	public boolean clone1  = false;
	public boolean clone2  = false;
	public boolean clone3  = false;
	public boolean hash    = false;
	public boolean shingle = false;
	public boolean matrix  = false;
	public String credential;
	public String minimumSize;
	public List<String> listaArchivos = new ArrayList<String>();
}