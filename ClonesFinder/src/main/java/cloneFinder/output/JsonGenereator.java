package cloneFinder.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author diego
 */
public class JsonGenereator {

    public void generateJson(int howMany) {
        JSONObject moduleOutput = new JSONObject();
        for (int i = 0; i < howMany; i++) {
            JSONObject output = new JSONObject();
            output.put("ID_proyecto", "P" + getRandomNumberRange(12345, 99999));
            output.put("Nombre_proyecto", getString(15));
            output.put("Ruta_proyecto", "/" + getString(5) + "/" + getString(5) + "/" + getString(5));
            output.put("Fecha_procesamiento", Integer.toString(getRandomNumberRange(1, 30)) + "/" + Integer.toString(getRandomNumberRange(1, 12)) + "/" + Integer.toString(getRandomNumberRange(2017, 2020)));
            output.put("Hora_procesamiento", getRandomNumberRange(0, 24) + ":" + getRandomNumberRange(0, 59));
            output.put("Granularidad", getStringGranularity());
            output.put("Tipos_clones", getStringCloneType());
            output.put("Tamaño_minimo_clones", getRandomNumberRange(12345, 99999));
            int cantidadClones = getRandomNumberRange(10, 20);
            output.put("Cantidad_clones", cantidadClones);
            JSONArray clonArray = new JSONArray();
            for (int j = 0; j < cantidadClones; j++) {
                JSONObject clonElement = new JSONObject();
                int cantidadCopias = getRandomNumberRange(1, 3);
                clonElement.put("Cantidad_copias", cantidadCopias);
                clonElement.put("Clase", getString(15));
                clonElement.put("Metodo", getString(15));
                clonElement.put("Numero_linea", getRandomNumberRange(0, 999));
                clonElement.put("Codigo", getString(200));
                clonElement.put("Copias", getString(200));
                JSONArray clonArrayCopies = new JSONArray();
                for (int k = 0; k < cantidadCopias; k++) {
                    JSONObject clonCopiesElement = new JSONObject();
                    clonCopiesElement.put("Clase", getString(15));
                    clonCopiesElement.put("Metodo", getString(15));
                    clonCopiesElement.put("Numero_linea", getRandomNumberRange(0, 999));
                    clonCopiesElement.put("Codigo", getString(200));
                    clonArrayCopies.put(clonCopiesElement);
                    
                }
                clonElement.put("Copias", clonArrayCopies);
                clonArray.put(clonElement);
            }
            output.put("Clones", clonArray);
            moduleOutput.accumulate("Analisis", output);
            //****************************************************+
        }
        try (FileWriter file = new FileWriter("clonOutput.json")) {
            System.out.println("Escribiendo en archivo .json ...");
            file.write(moduleOutput.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Terminado");
        System.out.println("Salvando en MngoDB");
       // Object objectStringJson = com.mongodb.util.JSON.parse(moduleOutput.toString());
       // PostDB mongoDB = new PostDB();
       // mongoDB.saveMongoDB(objectStringJson);
        

    }

    private String getString(int size) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private String getStringGranularity() {
        int randomNumber = getRandomNumberRange(0, 9);
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

    private String getStringCloneType() {
        int randomNumber = getRandomNumberRange(0, 9);
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

    private int getRandomNumberRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}