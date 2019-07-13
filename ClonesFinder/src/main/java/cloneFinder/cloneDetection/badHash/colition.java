package cloneFinder.cloneDetection.badHash;

import com.github.javaparser.utils.Pair;

public final class colition{
    private Pair<Integer, Integer> thisFunc;
    private Pair<Integer, Integer> thatFunc;

    public colition(Pair<Integer, Integer> thisFunc,Pair<Integer, Integer> thatFunc){
        setThisFunc(thisFunc);
        setThatFunc(thatFunc);
    }
    /**
     * @return the thisFunc
     */
    public Pair<Integer, Integer> getThisFunc() {
        return thisFunc;
    }

    /**
     * @return the thatFunc
     */
    public Pair<Integer, Integer> getThatFunc() {
        return thatFunc;
    }

    /**
     * @param thatFunc the thatFunc to set
     */
    public void setThatFunc(Pair<Integer, Integer> thatFunc) {
        this.thatFunc = thatFunc;
    }

    /**
     * @param thisFunc the thisFunc to set
     */
    public void setThisFunc(Pair<Integer, Integer> thisFunc) {
        this.thisFunc = thisFunc;
    }

}