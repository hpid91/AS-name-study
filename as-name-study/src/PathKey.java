
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class PathKey extends FlatKey {
	private final NewPath myPath;
	private final int size; //nbr de Hops
	private final boolean neighbours;
	private final boolean isShort;
	

	public PathKey(NewPath aPath) {
		super(aPath.get(0),aPath.get(aPath.size()-1));
		this.myPath = aPath;
		this.size = myPath.size()-1;
		this.neighbours = size == 1;
		this.isShort = size<=1;
	}
	public NewPath getPath() {
		return this.myPath;
	}

	/**
	 * @return true if PathKey corresponds to neighbours
	 */
	protected boolean isShort() {
		return isShort;
	}

	@Override
	public PathKey getReverse() {
		NewPath reversePath = new NewPath(this.myPath);
		Collections.reverse(reversePath);
		return new PathKey(reversePath);
	}
	
	public int getNumberOfLinks(){
		return this.size;
	}
	
	public int getNumberOfHops() {
		return this.getNumberOfLinks() ;
	}
	
	public boolean areNeighbours(){
		return neighbours;
	}
	
	/**
	 * Gets the sub-paths of this instance.
	 * @return 
	 */
	public Set<FlatKey> getSubPaths() {
		return this.getSubPaths(1,0) ;
	}

	/**
	 * Gets sub-paths of this instance which have a size equal or greater than {@code filter}.
	 * @return 
	 */
	public Set<FlatKey> getSubPaths(int minLengthFilter, int maxLengthFilter) {
		
		int iMaxBound = this.size;
		int eMaxBound = this.size+1;
		Set<FlatKey> result = new HashSet<FlatKey>(iMaxBound*eMaxBound);
		for(int i = 0; i < iMaxBound; i++) {
			for(int e = i + minLengthFilter; e < eMaxBound; e++) {
				result.add(new PathKey(myPath.getSubPath(i,e)));
			}
		}
		return result;
	}
	
	@Override
	protected int getHashCode() {
		if (myPath==null)
			return super.getHashCode();
		else
			return myPath.hashCode();
	}

	@Override
	public boolean equals(Object anObject){
		if (super.sameSDNodes(anObject))
			if (anObject instanceof PathKey){
				PathKey otherKey = (PathKey)anObject;
				if (this.isShort && otherKey.isShort)
					return true;
//				if (anObject instanceof UndeterminedKey ^ this instanceof UndeterminedKey)
//					return false;
				return myPath.equals(otherKey.myPath);
			} else
				return this.isShort;
		else
			return false;
	}
}
