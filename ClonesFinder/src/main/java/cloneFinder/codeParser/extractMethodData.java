package cloneFinder.codeParser;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class extractMethodData extends VoidVisitorAdapter<ArrayList<methodData>> {
        private boolean analysisByToken; //defines if the code is parsed by token
        private boolean analysisByCharacter; ///defines if the code is parsed by character
        private boolean analysisByStatement; //defines if the code is parsed by statement
        private boolean useWhiteSpaces;
        private String path;
        public extractMethodData(boolean analysisByCharacter, boolean analysisByToken, 
                                 boolean analysisByStatement, boolean useWhiteSpaces, String path){
            
            this.analysisByToken=analysisByToken;
            this.analysisByCharacter=analysisByCharacter;
            this.analysisByStatement=analysisByStatement;
            this.useWhiteSpaces=useWhiteSpaces;
            this.path=path;
        
        }

        

        private static ArrayList<parsedData> extractTokenSequence(BlockStmt codeBody, boolean useWhiteSpaces){
             
        	ArrayList<parsedData> tokenList = new ArrayList<parsedData>();
            codeBody.getTokenRange().get().forEach( t -> {
            if (useWhiteSpaces || !t.getText().matches("\\s+"))  {
                tokenList.add( new parsedData(t.getText(), 
                t.getRange().get().begin.line , 
                t.getRange().get().begin.line, 
                t.getRange().get().end.line, 
                t.getRange().get().end.column));
            }    
            });
            return tokenList;
                     
        }

        @Override
        public void visit(MethodDeclaration md, ArrayList<methodData> methodList) {
            super.visit(md, methodList);
            methodData tempObj = new methodData(md.getSignature().asString(),path,md.getBody().get().toString(),md.getBegin().get().line);
            BlockStmt codeBody= md.getBody().get();
            
            ArrayList<parsedData> tokenList = extractTokenSequence(codeBody,useWhiteSpaces);
            if(analysisByCharacter){
            	
            	ArrayList<parsedData> newCharacterSequence = new ArrayList<parsedData>();
            	
            	
            	 int TokenLIne=0;
                
                 for (int i=0;i<tokenList.size();i++) {
                 	parsedData token=tokenList.get(i);
                 	
                 	TokenLIne=token.getBeginLine();
                 		
                 	String strTemp = token.getText();
                 	char[] tempChar=strTemp.toCharArray();
                 	for(char c:tempChar){
                 		newCharacterSequence.add(new parsedData(String.valueOf(c), TokenLIne,0,TokenLIne, 0));
                 	}
                 
                 	
                 }
                 tempObj.setCharacterSequence(newCharacterSequence);
            
            
            
            
            
            }

            if(analysisByToken){
            	
                tempObj.setTokenSequence(tokenList);
            }
            if(analysisByStatement){
            	
            	//Analisis by statement. This section removes first the commentaries 
            	//to get the sentences properly.
            	ArrayList<parsedData> newStatementSequence = new ArrayList<parsedData>();
            	String temp = "";
			/*
			 * method = method.replaceAll("// .*{0,}\n",""); method =
			 * method.replaceAll("/[*].*[*]/",""); method = method.replaceAll("[\n]","");
			 * method = method.replaceAll("[  ]","");
			 */
                
                int fisrtTokenLIne=0;
                int LastTokenLIne=0;
                boolean init=true;
                for (int i=0;i<tokenList.size();i++) {
                	parsedData token=tokenList.get(i);
                	if(init) {
                		fisrtTokenLIne=token.getBeginLine();
                		init=false;
                	}
                	String strTemp = token.getText();
                	if ((strTemp!=";")&&(strTemp!="{")&&(strTemp!="}")){
                		temp = temp + strTemp;
                	}else{
                		System.out.println(temp+" &\n");
                		LastTokenLIne=token.getEndLine();
                		System.out.println(fisrtTokenLIne+ "iii");
                		System.out.println(LastTokenLIne+ "fff");
                		newStatementSequence.add(new parsedData(temp, fisrtTokenLIne,0,LastTokenLIne, 0));
                		temp = "";
                		init=true;
                	}
                }
                tempObj.setStatementSequence(newStatementSequence);
                
            }
    
            methodList.add(tempObj);
        }
        
    }