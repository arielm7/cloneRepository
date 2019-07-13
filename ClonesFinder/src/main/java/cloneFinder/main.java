package cloneFinder;

import java.io.File;
import java.util.Scanner;

import cloneFinder.inputConfiguration.Entry;
import cloneFinder.inputConfiguration.Input;

/**
 * Program that reads the entries of a JSON file and 
 * encapsulates them in a class. It also extracts the 
 * files from a route to later analyze them.
 * @author diego
 *
 */
public class main {
	
	//Constants
	static final String S1 = "Ingrese la direccion absoluta del archivo de configuracion: \n";
	static final String S2 = "La direccion usada sera:  ";
	
	
	public static void main(String[] args){
		
		//Get the configuration file path
		Scanner myObj = new Scanner(System.in);
	    System.out.println(S1);
	    
	    String absolutePath = myObj.nextLine();
	    if(absolutePath.isEmpty()) {
	    	absolutePath="/home/ariel/TEC/diseño/implementation/ClonesFinder/resources/entry.json";
	    }
	    System.out.println(S2 + absolutePath);
	    
	   
	    //Wrapper with the entries and class to read the JSON.
		Input inputs = new Input();
		Entry entries = new Entry();
		//Read the file and set the parameters
		entries.readFile(absolutePath,inputs);
		//Iterate through the paths to get the files to analyze.
		inputs.listaArchivos = entries.travelRoot(new File(inputs.path),inputs.listaArchivos);
		
		//Print the paths
		System.out.println("\n Lista de archivos:");
	    for(int x=0;x< inputs.listaArchivos.size();x++)
	        System.out.println(inputs.listaArchivos.get(x));
	    System.out.println("Fin de la lista de archivos: \n");
	    //Instantiate the administrator and give him the entries 
	    //to begin the operations
	    configurationManager adm = new configurationManager();
	    adm.admin(inputs);
	}

}