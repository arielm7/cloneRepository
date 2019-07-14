package cloneFinder.cloneDetection.badHash;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.utils.Pair;
import com.google.common.collect.HashBasedTable; 
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import cloneFinder.codeParser.methodData;
import cloneFinder.codeParser.sourceParser;
import cloneFinder.codeParser.parsedData;
import cloneFinder.cloneDetection.badHash.hashAlgorithm;
/**
 * @author ariel
 *
 */
public class SearchManager {

    
    private int cloneType=1;
    private int shingleLength=10;
    private int granularity=2;
    
    
    public SearchManager(int granularity, int cloneType, int shingleLength) {
    	
		this.cloneType = cloneType;
        this.shingleLength = shingleLength;
        this.granularity= granularity;
	}


    /**
     * @param method : object with the information for a method. 
     * @param algorithm: object that performs the hash according to the type of clone
     * @param shingleLength: number of source code elements of a shingle (minimun text unit used during clone search)
     * @return a list of pairs. pair -> [shingle hash value,[begin line in source code, end line in source code]]
     */
    private ArrayList<ShingleData> getShingles(methodData method, hashAlgorithm algorithm, int shingleLength){

        ArrayList<ShingleData> shingles= new ArrayList<ShingleData>();
        ArrayList<parsedData> sourceElements = method.getSourceCodeElements(granularity) ;
        for(int i=0;i+shingleLength <sourceElements.size();i++){
        	List<parsedData> groupTokens= sourceElements.subList(i, i+shingleLength);
            String newShingle="";
            for (int j=0;j<groupTokens.size();j++) {
            	newShingle=newShingle.concat(groupTokens.get(j).getText());
            }
            Pair<Integer,Integer> shinglePos = new Pair<Integer,Integer>(sourceElements.get(i).getBeginLine() ,sourceElements.get(i+shingleLength-1).getEndLine());
            ShingleData shingleData = new ShingleData(algorithm.calcHash(newShingle),shinglePos);
            if(shingleData.getHashValue()!=0){ 
            shingles.add(shingleData);
            }
        }
        return shingles;

    }
    
    private ArrayList<ShingleData> getSketch(methodData method, hashAlgorithm algorithm, int shingleLength, int sketchLength){

        ArrayList<ShingleData> shingles= new ArrayList<ShingleData>();
        ArrayList<parsedData> tokens = method.getSourceCodeElements(granularity);
        int sketchBegin=tokens.get(0).getEndLine();
        int sketchEnd=0;
        ArrayList<String> sketch = new ArrayList<String>();
        
        for(int i=0;i+shingleLength <tokens.size();i+=1){
        	
        	List<parsedData> groupTokens;
        	if(i+shingleLength>=tokens.size()-shingleLength ) {
        		groupTokens= tokens.subList(i,tokens.size()-1); 
        	}
        	else {
        	groupTokens= tokens.subList(i, i+shingleLength);
        	}
            String newShingle=" ";
            for (int j=0;j<groupTokens.size();j++) {
            	newShingle=newShingle.concat(groupTokens.get(j).getText());
            }
            
            
            sketch.add(newShingle);
            if(sketch.size()>=sketchLength || i+shingleLength>=tokens.size()-shingleLength ) {
            	sketchEnd=tokens.get(i+shingleLength).getEndLine();
				Pair<Integer,Integer> shinglePos = new Pair<Integer,Integer>(sketchBegin,sketchEnd);
				sketchBegin=tokens.get(i+1).getEndLine();
				long xor=0;
				String s="";
				for(int k=0;k<sketch.size();k++) {
					
					
					long newHash=algorithm.calcHash(sketch.get(k));
					if(newHash!=0){ 
						s=s.concat(sketch.get(k));
			            xor=xor^newHash;
			        }
					if(xor==0) {
						xor=sketch.get(k).hashCode();
					}
				}
				ShingleData shingleData = new ShingleData(xor,shinglePos);
				shingles.add(shingleData);
				sketch.remove(0);
			}
        }
        return shingles;

    }

    private ArrayList<ShingleData> getSnippet(methodData method,hashAlgorithm algorithm){
     
        switch (this.granularity) {
            case 1:
                
            	if(this.cloneType==3) {
            		return getSketch(method,algorithm,this.shingleLength,4);
            		
            	}
            	return getShingles(method,algorithm,this.shingleLength);
        
            case 2:
            	if(this.cloneType==3) {
            		return getSketch(method,algorithm,this.shingleLength,4);
            		
            	}
            	return getShingles(method,algorithm,this.shingleLength);

            
            case 3:
            	
            	if(this.cloneType==3) {
            		return getSketch(method,algorithm,this.shingleLength,4);
            		
            	}
            	return getShingles(method,algorithm,this.shingleLength);        
            default:
                break;
        }   
        return null;	
	}
    
	public Table<Long, String, Pair<Integer,Integer>> search(ArrayList<methodData> methodList) {
		
		hashAlgorithm cloneHash= new hashAlgorithm(this.cloneType);
		Table<Long, String, Pair<Integer,Integer>> cloneTable = TreeBasedTable.create();
		Iterator<methodData> methodIte=methodList.iterator();
        ArrayList<ShingleData> snippets=new ArrayList<ShingleData>();
        while(methodIte.hasNext()){
            methodData method = methodIte.next();
            
            snippets=getSnippet(method,cloneHash);
            snippets.forEach((s)->{ 
                cloneTable.put(s.getHashValue(), method.getFullName(), s.getShingleLocation());
            });
             
        }
		
		return cloneTable;
	}
    
    
    
    
    

		

}