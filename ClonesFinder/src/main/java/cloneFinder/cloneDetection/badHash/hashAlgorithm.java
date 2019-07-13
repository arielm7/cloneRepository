package cloneFinder.cloneDetection.badHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.utils.Pair;

import cloneFinder.codeParser.methodData;
import cloneFinder.codeParser.parsedData;


public class hashAlgorithm {

    private int cloneType;

    public hashAlgorithm(int cloneType) {
        this.cloneType=cloneType;
    }

    private long sha1(String input) {
        MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			return 0;
		}
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return Long.parseLong(sb.toString().substring(0, 15),16);
    }

    private String normalize(String input){
        String normalizeString;
        normalizeString = input.replaceAll("[a-zA-Z]", "t").replaceAll("(t){2,}", "t");
        normalizeString= normalizeString.replaceAll("[0-9]", "1").replaceAll("(1){2,}", "1");;
        return normalizeString;
    }
    
    public long calcHash(String input){
        
    	switch (this.cloneType) {
        case 1:
            
        	return sha1(input);
    
        case 2:
            
        	return sha1(normalize(input));
        
        case 3:
        	long hash=sha1(normalize(input)); 
        	long comp=hash&1;
    		if(comp==1) {return hash;}
    		return 0;   
        default:
            break;
    }
		return 0;
    	        

    }
}