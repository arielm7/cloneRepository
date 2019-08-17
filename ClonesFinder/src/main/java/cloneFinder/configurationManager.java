package cloneFinder;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import com.github.javaparser.utils.Pair;
import com.google.common.collect.Table;

import cloneFinder.cloneDetection.badHash.SearchManager;
import cloneFinder.cloneDetection.badHash.cloneDetector;
import cloneFinder.cloneDetection.badHash.colition;
import cloneFinder.codeParser.methodData;
import cloneFinder.codeParser.sourceParser;
import cloneFinder.inputConfiguration.Input;
import cloneFinder.output.generateResults;

public class configurationManager {

	private static final int character = 1;
	private static final int token = 2;
	private static final int statement = 3;
	private static final int typeOne = 1;
	private static final int typeTwo = 2;
	private static final int typeThree = 3;

	private int cloneType = typeOne;
	private int granularity = token;
	private int minimunCloneSize = 10;

	// selection of the detection algorithm
	public void admin(final Input input) {

		if (input.clone2) {
			cloneType = typeTwo;
		} else if (input.clone3) {
			cloneType = typeThree;
		}
		if (input.granularityByCharacter) {
			granularity = character;
		} else if (input.granularityBySentence) {
			granularity = statement;
		}
		minimunCloneSize = Integer.parseInt(input.minimumSize);

		final sourceParser parser = new sourceParser();
		final ArrayList<methodData> projectMethodList = new ArrayList<>(); // list to be filled with the
																		   // parsed methods of the project 
		System.out.println("Inicio de extraccion de metodos");
		System.out.println("analizando código fuente, " + input.listaArchivos.size() + " archivos por analizar");
		for (int i = 0; i < input.listaArchivos.size(); i++) { // parsing all the methods per file

			try {

				projectMethodList.addAll(parser.getMethods(input.listaArchivos.get(i), input));

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin de extraccion de metodos");
		// selection of the detection algorithm
		if (input.hash) {
			detectionByHash(input, projectMethodList);
		} else if (input.matrix) {
			adminByMatrix(input, projectMethodList);
		} else if (input.shingle) {
			adminByShingle(input, projectMethodList);
		}
	}

	/**
	 *
	 * @param input
	 */
	private void adminByMatrix(final Input input, final ArrayList<methodData> tempA) {

		if (input.granularityByCharacter) {
//			if(input.clone1){ System.out.println("Matrix by character for clone type 1");}
//			if(input.clone2){ System.out.println("Matrix by character for clone type 2");}
//			if(input.clone3){ System.out.println("Matrix by character for clone type 3");}
		}
		if (input.granularityByToken) {
//			if(input.clone1){ System.out.println("Matrix by token for clone type 1");}
//			if(input.clone2){ System.out.println("Matrix by token for clone type 2");}
//			if(input.clone3){ System.out.println("Matrix by token for clone type 3");}
		}
		if (input.granularityBySentence) {
//			if(input.clone1){ System.out.println("Matrix by sentence for clone type 1");}
//			if(input.clone2){ System.out.println("Matrix by sentence for clone type 2");}
//			if(input.clone3){ System.out.println("Matrix by sentence for clone type 3");}
		}
	}

	/**
	 *
	 * @param input
	 */
	private void adminByShingle(final Input input, final ArrayList<methodData> tempA) {

		if (input.granularityByCharacter) {
//			if(input.clone1){ System.out.println("Shingle by character for clone type 1");}
//			if(input.clone2){ System.out.println("Shingle by character for clone type 2");}
//			if(input.clone3){ System.out.println("Shingle by character for clone type 3");}
		}
		if (input.granularityByToken) {
//			if(input.clone1){ System.out.println("Shingle by token for clone type 1");}
//			if(input.clone2){ System.out.println("Shingle by token for clone type 2");}
//			if(input.clone3){ System.out.println("Shingle by token for clone type 3");}
		}
		if (input.granularityBySentence) {
//			if(input.clone1){ System.out.println("Shingle by sentence for clone type 1");}
//			if(input.clone2){ System.out.println("Shingle by sentence for clone type 2");}
//			if(input.clone3){ System.out.println("Shingle by sentence for clone type 3");}
		}
	}

	/**
	 *
	 * @param input
	 */
	/**
	 * @param input
	 * @param projectMethodList
	 */
	private void detectionByHash(final Input input, final ArrayList<methodData> projectMethodList) {

		int clonCant = 0;

		System.out.println("analizando código fuente, " + projectMethodList.size() + " métodos por analizar");
		final long startTime = System.nanoTime();

		final SearchManager busqueda = new SearchManager(granularity, cloneType, minimunCloneSize);
		final cloneDetector detector = new cloneDetector();
		final Table<Long, String, Pair<Integer, Integer>> cloneTable = busqueda.search(projectMethodList);

		cloneTable.rowKeySet();

		/*
		 * clonesKeys.forEach((k)->{ Set<String> columnas=cloneTable.row(k).keySet(); if
		 * (1<columnas.size()){
		 *
		 * System.out.println("metodos con clones: "+cloneTable.row(k).keySet());
		 * System.out.println( cloneTable.row(k).values()); } } );
		 */

		final Iterator<String> colIt = cloneTable.columnKeySet().iterator();
		final generateResults printer = new generateResults();
		while (colIt.hasNext()) {
			final String col = colIt.next();
			final ArrayList<Pair<String, colition>> colitionList = detector.getColitions(cloneTable, col);
			final Iterator<ArrayList<Pair<String, colition>>> colitionByFunctionIte = detector
					.splitColitions(colitionList).iterator();
			System.out.println("\n############################################################################\n");
			System.out.println("\n--------------Clones detectados para el método: " + col + "--------------\n");
			while (colitionByFunctionIte.hasNext()) {
				final ArrayList<Pair<String, colition>> colitionByFunction = colitionByFunctionIte.next();
				final ArrayList<Pair<String, colition>> finalCloneListPerFunction = detector
						.getFinalClones(colitionByFunction, cloneType);
				

				printer.printResults(finalCloneListPerFunction, col);
				clonCant += finalCloneListPerFunction.size();
			}
		}
		printer.saveResults();
		final long endTime = System.nanoTime();
		final long timeElapsed = endTime - startTime;

		System.out.println("Execution time in miliseconds  : " + timeElapsed / 1000000.0);
		System.out.println("se encontraon " + clonCant + " clones");
	}
}