
public class FlatKey implements Comparable<FlatKey>{
	
	private final transient NodeName sNode, dNode;
	private final int hash;
	private final boolean isDirect;
	
	public FlatKey(NodeName sourceNode, NodeName destinationNode) {
		sNode = sourceNode;
		dNode = destinationNode;
		hash = getHashCode();
		isDirect = (sourceNode.compareTo(destinationNode)<=0);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this;
	}

	public boolean isDirect(){
		return isDirect;
	}

	public NodeName getSourceNode() {
		return sNode;
	}

	public NodeName getDestinationNode() {
		return dNode;
	}

	public FlatKey getReverse(){
		return new FlatKey(dNode,sNode);
	}
	
	public FlatKey makeDirect(){
		if (this.isDirect)
			return this;
		else
			return this.getReverse();
	}

	@Override
	public int compareTo(FlatKey otherK){
		int res=this.sNode.compareTo(otherK.getSourceNode());
		if (res==0)
			res=this.dNode.compareTo(otherK.getDestinationNode());
		return res;
	}

	public boolean sameSDNodes(Object anObject){
		if (anObject == this)
			return true;  	  
		if (anObject == null)
			return false;
		try{
			FlatKey key = (FlatKey)anObject;		  
			return this.sNode.equals(key.getSourceNode()) && this.dNode.equals(key.getDestinationNode());
		} catch (ClassCastException cce){
			try{
				PathKey key = (PathKey)anObject;		  
				return this.sNode.equals(key.getSourceNode()) && this.dNode.equals(key.getDestinationNode());
			} catch (ClassCastException cce1){
				return false;
			}
		}
	}
	
	/**
	 * A DetailedKey and a SDKey may be equal, if and only if they have: (used in eliminateOneHopTraffic in SensibilityMatrix)
	 *   1. the same source
	 *   2. the same destination
	 *   3. the size of the PathKey is below or equal to 2.
	 */
	@Override
	public boolean equals(Object anObject){
		if (this.sameSDNodes(anObject))
			if (anObject instanceof PathKey)
				return false;
			else
				return true;
		else
			return false;
	}
	
	@Override
	public final int hashCode(){
		return hash;
	}

	protected int getHashCode() {
		return (sNode.hashCode()*163)^dNode.hashCode();
	}

	@Override
	public String toString() {
		return (sNode + "-" + dNode);
	}

}
