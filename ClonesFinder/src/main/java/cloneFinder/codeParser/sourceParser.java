package cloneFinder.codeParser;

import com.github.javaparser.*;
import com.github.javaparser.ast.visitor.VoidVisitor;

import cloneFinder.inputConfiguration.Input;

import com.github.javaparser.ast.CompilationUnit;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


public class sourceParser {

    /**
     * Method that receives the file to extract the 
     * methods and the struct of the configuration file.
     * @param filePath Path at the file to analize
     * @param input Data from the configuration file entry
     * @return List with each one of the methods
     * @throws Exception
     */
    public ArrayList<methodData> getMethods(String filePath,Input input) throws Exception {
    	//Store the methods of a file in an arraylist
        return parseMethods(filePath,input.granularityByCharacter,
        		input.granularityByToken,input.granularityBySentence,input.whiteSpaces);
    }

    /**
     * 
     * @param path
     * @param analysisByCharacter
     * @param analysisByToken
     * @param analysisByStatement
     * @param useWhiteSpaces
     * @return
     */
    public ArrayList<methodData> parseMethods(String path, 
    		boolean analysisByCharacter,boolean analysisByToken,
    		boolean analysisByStatement,boolean useWhiteSpaces){

        ArrayList<methodData> methodDataList = new ArrayList<methodData>();
        try {
            
            CompilationUnit cu = JavaParser.parse(new File(path));
            VoidVisitor<ArrayList<methodData>> methodNameVisitor;
            methodNameVisitor = new extractMethodData(analysisByCharacter,analysisByToken,
                                                      analysisByStatement,useWhiteSpaces,path);
            methodNameVisitor.visit(cu, methodDataList);
            
        } catch (FileNotFoundException e) {
            System.out.println("there is no source code");
            return methodDataList;
        } catch (ParseProblemException e){
            System.out.println("problems during parsing");
            return methodDataList;
        }
        catch (Exception e) {
            System.out.println("Exception occurred");
            return methodDataList;
         }
        methodDataList.forEach((n)->n.setFile(path));
       
        return methodDataList;
    }
}