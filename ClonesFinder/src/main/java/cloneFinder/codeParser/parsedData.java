package cloneFinder.codeParser;

public final class parsedData{
    private String text;
    private int beginLine, beginColumn, endLine, endColumn;

    parsedData(String text, int beginLine, int beginColumn, int endLine, int endColumn) {
        this.setText(text);
        this.setBeginLine(beginLine);
        this.setEndLine(endLine);
        this.setEndColumn(endColumn);
    }

    /**
     * @return the endColumn
     */
    public int getEndColumn() {
        return endColumn;
    }

    /**
     * @param endColumn the endColumn to set
     */
    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    /**
     * @return the endLine
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * @param endLine the endLine to set
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    /**
     * @return the beginColumn
     */
    public int getBeginColumn() {
        return beginColumn;
    }

    /**
     * @param beginColumn the beginColumn to set
     */
    public void setBeginColumn(int beginColumn) {
        this.beginColumn = beginColumn;
    }

    /**
     * @return the beginLine
     */
    public int getBeginLine() {
        return beginLine;
    }

    /**
     * @param beginLine the beginLine to set
     */
    public void setBeginLine(int beginLine) {
        this.beginLine = beginLine;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }


}