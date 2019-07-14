package cloneFinder.codeParser;

import java.util.ArrayList;


public final class methodData{

  private String name;
  private String fullName;
  private String body;
  private int line;
  private String file;
  private ArrayList<parsedData> characterSequence;
  private ArrayList<parsedData>tokenSequence;
  private ArrayList<parsedData> StatementSequence;

  methodData(String name, String file ,String body, int line) {
      this.setName(name);
      this.setBody(body);
      this.setLine(line);
      this.file=file;
      String[] pathArray=file.split("/");
      this.fullName=pathArray[pathArray.length-1].concat(": "+this.name);
  }

  public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

/**
   * @return the statementSequence
   */
  protected ArrayList<parsedData> getStatementSequence() {
      return StatementSequence;
  }

  /**
   * @param statementSequence the statementSequence to set
   */
  public void setStatementSequence(ArrayList<parsedData> statementSequence) {
      this.StatementSequence = statementSequence;
  }

  /**
   * @return the tokenList
   */
  public ArrayList<parsedData> getTokenSequence() {
      return tokenSequence;
  }

  /**
   * @param tokenList the tokenList to set
   */
  public void setTokenSequence(ArrayList<parsedData> tokenList) {
      this.tokenSequence = tokenList;
  }

  /**
   * @return the characterSequence
   */
  protected ArrayList<parsedData> getCharacterSequence() {
      return characterSequence;
  }

  /**
   * @param characterSequence the characterSequence to set
   */
  public void setCharacterSequence(ArrayList<parsedData> characterSequence) {
      this.characterSequence = characterSequence;
  }

  /**
   * @return the name
   */
  public String getName() {
      return name;
  }

  /**
   * @return the file
   */
  public String getFile() {
      return file;
  }

  /**
   * @param file the file to set
   */
  public void setFile(String file) {
      this.file = file;
  }

  /**
   * @return the line
   */
  public int getLine() {
      return line;
  }

  /**
   * @param line the line to set
   */
  public void setLine(int line) {
      this.line = line;
  }

  /**
   * @return the body
   */
  public String getBody() {
      return body;
  }

  /**
   * @param body the body to set
   */
  public void setBody(String body) {
      this.body = body;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
      this.name = name;
  }
//get the list of elements according to the desired granularity
  public ArrayList<parsedData> getSourceCodeElements(int granularity) {
	  
	  if(granularity==1) {return getCharacterSequence();}
	  if(granularity==3) {return getStatementSequence();}
	  else {return getTokenSequence();}
  }

}