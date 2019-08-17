package cloneFinder.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import com.github.javaparser.utils.Pair;

/**
 *
 * @author diego
 */
public class JsonGenereator {

	public void cloneToJson(JSONArray clonArray, final String metodo1, final String metodo2,
			final Pair<Integer, Integer> metodo1Pos, final Pair<Integer, Integer> metodo2Pos) {
		final JSONObject clonElement = new JSONObject();
		clonElement.put("Metodo 1", metodo1);
		clonElement.put("Metodo 2", metodo2);
		clonElement.put("Linea_inicio_metodo_1", metodo1Pos.a);
		clonElement.put("Linea_Final_metodo_1", metodo1Pos.b);
		clonElement.put("Linea_inicio_metodo_2", metodo2Pos.a);
		clonElement.put("Linea_Final_metodo_2", metodo2Pos.b);
		clonArray.put(clonElement);
	}

	private void generalCloneInfo(final JSONObject output) {

		output.put("Nombre_proyecto", getString(15));
		output.put("Ruta_proyecto", "/" + getString(5) + "/" + getString(5) + "/" + getString(5));
		output.put("Fecha_procesamiento",
				Integer.toString(getRandomNumberRange(1, 30)) + "/" + Integer.toString(getRandomNumberRange(1, 12))
						+ "/" + Integer.toString(getRandomNumberRange(2017, 2020)));
		output.put("Hora_procesamiento", getRandomNumberRange(0, 24) + ":" + getRandomNumberRange(0, 59));
		output.put("Granularidad", getStringGranularity());
		output.put("Tipos_clones", getStringCloneType());
		output.put("Tamaño_minimo_clones", getRandomNumberRange(12345, 99999));
		final int cantidadClones = getRandomNumberRange(10, 20);
		output.put("Cantidad_clones", cantidadClones);
	}

	public void generateJson(final JSONArray clonArray) {
		final JSONObject moduleOutput = new JSONObject();
		final JSONObject output = new JSONObject();
		generalCloneInfo(output);
		output.put("Clones", clonArray);
		moduleOutput.accumulate("Analisis", output);
		// ****************************************************+

		try (FileWriter file = new FileWriter("clonOutput.json")) {
			System.out.println("Escribiendo en archivo .json ...");
			file.write(moduleOutput.toString());
			System.out.println("Terminado");
			System.out.println("Salvando en MngoDB");

		} catch (final IOException e) {
			e.printStackTrace();
		}
		// Object objectStringJson =
		// com.mongodb.util.JSON.parse(moduleOutput.toString());
		// PostDB mongoDB = new PostDB();
		// mongoDB.saveMongoDB(objectStringJson);

	}

	private int getRandomNumberRange(final int min, final int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		final Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private String getString(final int size) {
		final String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < size) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		final String saltStr = salt.toString();
		return saltStr;

	}

	private String getStringCloneType() {
		final int randomNumber = getRandomNumberRange(0, 9);
		String cloneTypeReturn = "";
		if (randomNumber >= 0 && randomNumber < 3) {
			cloneTypeReturn = "Tipo 1";
		}
		if (randomNumber >= 3 && randomNumber < 6) {
			cloneTypeReturn = "Tipo 2";
		}
		if (randomNumber >= 6 && randomNumber < 9) {
			cloneTypeReturn = "Tipo 3";
		}
		return cloneTypeReturn;

	}

	private String getStringGranularity() {
		final int randomNumber = getRandomNumberRange(0, 9);
		String granularityReturn = "";
		if (randomNumber >= 0 && randomNumber < 3) {
			granularityReturn = "token";
		}
		if (randomNumber >= 3 && randomNumber < 6) {
			granularityReturn = "caracter";
		}
		if (randomNumber >= 6 && randomNumber < 9) {
			granularityReturn = "sentencia";
		}
		return granularityReturn;

	}

}