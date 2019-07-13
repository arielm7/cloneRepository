package cloneFinder.cloneDetection.badHash;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import com.github.javaparser.utils.Pair;
import com.google.common.collect.Table;

public class cloneDetector{

	public ArrayList<Pair<String,colition>> getColitions(Table<Long, String, Pair<Integer,Integer>> cloneTable, String col) {
		Set<Long> clonesKeys = cloneTable.rowKeySet();
		ArrayList<Pair<String,colition>> colitionList= new ArrayList<Pair<String,colition>>();
    	clonesKeys.forEach((k)->{
            Set<String> columnas=cloneTable.row(k).keySet();
            if (1<columnas.size() && columnas.contains(col)){
            	columnas.forEach((c)->{
            		if(!c.equals(col)) {
            			colition pClone=new colition(cloneTable.get(k,col),cloneTable.get(k,c));
            			Pair<String,colition> pos = new Pair<String,colition>(c,pClone);
            			colitionList.add(pos);
            		}
            	});
                
            }
        });
    	colitionList.sort(new SortbyFunction());
    	return colitionList;
	}
	
	public ArrayList<ArrayList<Pair<String,colition>>>splitColitions(ArrayList<Pair<String,colition>> colitioList) {
		
		ArrayList<ArrayList<Pair<String,colition>>> allColitions= new ArrayList<ArrayList<Pair<String,colition>>>();
		
		if(colitioList.isEmpty()) {
			return allColitions;
		}
		String funtionNames;
		funtionNames=colitioList.get(0).a;
		
		Iterator<Pair<String, colition>> colIte= colitioList.iterator();
		ArrayList<Pair<String,colition>> tempList = new ArrayList<Pair<String,colition>>();
		while(colIte.hasNext()) {
			Pair<String, colition> colition = colIte.next();
			
			if(0==colition.a.compareTo(funtionNames)) {
				tempList.add(colition);
			}
			else {
				
				allColitions.add(tempList);
				funtionNames=colition.a;
				tempList=new ArrayList<Pair<String,colition>>();
				tempList.add(colition);
			}
		}
		allColitions.add(tempList);
		
		return allColitions;
	}
	
	private boolean isNext(int thatFuncfirstColitionEnd,
						   int thatFuncSecondColitionstart,
						   int thisFuncfirstColitionEnd,
						   int thisFuncSecondColitionstart,
						   int separation,
						   int cloneType,
						   int thatFuncCloneStart,
						   int thisFuncCloneStart) {
		
		if(thatFuncCloneStart>thatFuncSecondColitionstart || thisFuncCloneStart>thisFuncSecondColitionstart ) {
			return true;
		}
		if(cloneType!=3) {
			return (thatFuncfirstColitionEnd+separation<thatFuncSecondColitionstart) || (thisFuncfirstColitionEnd+separation<thisFuncSecondColitionstart) ;
		}
		return ((thatFuncfirstColitionEnd+separation<thatFuncSecondColitionstart) && (thisFuncfirstColitionEnd+separation<thisFuncSecondColitionstart)) ;
		
	}
	public ArrayList<Pair<String,colition>> getFinalClones(ArrayList<Pair<String,colition>> colitionList, int cloneType) {
		
		colitionList.sort(new SortbyLine());
		int separation=1;
		if(cloneType==3) {separation=4;}
		
		String thatFuncName=colitionList.get(0).a;
		int thatFuncfirstColitionEnd=colitionList.get(0).b.getThatFunc().b;
    	int thisFuncfirstColitionEnd=colitionList.get(0).b.getThisFunc().b;
    	int thatFuncSecondColitionstart=0;
    	int thisFuncSecondColitionstart=0;
    	int thatFuncCloneStart=colitionList.get(0).b.getThatFunc().a;
    	int thisFuncCloneStart=colitionList.get(0).b.getThisFunc().a;
    	int thatFuncCloneEnd=0;
    	int thisFuncCloneEnd=0;
    	ArrayList<Pair<String,colition>> finalCloneList= new ArrayList<Pair<String,colition>>();
    	
    	thatFuncCloneStart=colitionList.get(0).b.getThatFunc().a;
    	thisFuncCloneStart=colitionList.get(0).b.getThisFunc().a;
    	for(int i=0;i<colitionList.size()-1;i++) {
    		
        	thatFuncSecondColitionstart=colitionList.get(i+1).b.getThatFunc().a;
        	thisFuncSecondColitionstart=colitionList.get(i+1).b.getThisFunc().a;
        	
    		if(isNext(thatFuncfirstColitionEnd,thatFuncSecondColitionstart,thisFuncfirstColitionEnd,thisFuncSecondColitionstart,separation,cloneType,thatFuncCloneStart,thisFuncCloneStart) || i==colitionList.size()-1 ) {
    			thatFuncCloneEnd=colitionList.get(i).b.getThatFunc().b;
    			thisFuncCloneEnd=colitionList.get(i).b.getThisFunc().b;
    			//if(thatFuncCloneEnd>=thatFuncCloneStart && thisFuncCloneEnd>=thisFuncCloneStart){
    			Pair<Integer,Integer> thatFuncClonePos=new Pair<Integer,Integer>(thatFuncCloneStart,thatFuncCloneEnd);
    			Pair<Integer,Integer> thisFuncClonePos=new Pair<Integer,Integer>(thisFuncCloneStart,thisFuncCloneEnd);
    			colition cloneColition= new colition(thisFuncClonePos, thatFuncClonePos);
    			Pair<String,colition> finalClone = new Pair<String,colition>(thatFuncName,cloneColition);
    			finalCloneList.add(finalClone);
    			//}
    			thatFuncCloneStart=colitionList.get(i+1).b.getThatFunc().a;
    			thisFuncCloneStart=colitionList.get(i+1).b.getThisFunc().a;
     			
    		}
    		if( i==colitionList.size()-2) {
    			thatFuncCloneEnd=colitionList.get(i+1).b.getThatFunc().b;
    			thisFuncCloneEnd=colitionList.get(i+1).b.getThisFunc().b;
    			//if(thatFuncCloneEnd>=thatFuncCloneStart && thisFuncCloneEnd>=thisFuncCloneStart){
    			Pair<Integer,Integer> thatFuncClonePos=new Pair<Integer,Integer>(thatFuncCloneStart,thatFuncCloneEnd);
    			Pair<Integer,Integer> thisFuncClonePos=new Pair<Integer,Integer>(thisFuncCloneStart,thisFuncCloneEnd);
    			colition cloneColition= new colition(thisFuncClonePos, thatFuncClonePos);
    			Pair<String,colition> finalClone = new Pair<String,colition>(thatFuncName,cloneColition);
    			finalCloneList.add(finalClone);
    			//}
    		}
    
  
    		thatFuncfirstColitionEnd=colitionList.get(i+1).b.getThatFunc().b;
    		thisFuncfirstColitionEnd=colitionList.get(i+1).b.getThisFunc().b;
    	}
    	
    	return finalCloneList;
	}


}

class SortbyLine implements Comparator<Pair<String,colition>> 
{ 
	// Used for sorting in ascending order of 
    // roll number 
    public int compare(Pair<String,colition> a, Pair<String,colition> b) 
    { 
        return (a.b.getThatFunc().a)-(b.b.getThatFunc().a) ;
    } 
} 

class SortbyFunction implements Comparator<Pair<String,colition>> 
{ 
	// Used for sorting in ascending order of 
    // roll number 
    public int compare(Pair<String,colition> a, Pair<String,colition> b) 
    { 
        return a.a.compareTo(b.a);
    } 
} 