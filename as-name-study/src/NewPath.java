import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewPath extends ArrayList<NodeName>  {

	private static final long serialVersionUID = 8176733012854775625L;
/*	@Override
	public boolean equals(Object arg0) {
		NewPath arg = (NewPath)arg0;
		if (arg.size()!=this.size())
			return false;
		Iterator<NodeName> iter = arg.iterator();
		for (NodeName aNode:this)
			if (!aNode.equals(iter.next()))
				return false;
		return true;
	}*/

	private int hash=0;
	
	public NewPath(List<NodeName> nodes){
		super(nodes.size());
		super.addAll(nodes);
		if (nodes instanceof NewPath)
			this.hash = ((NewPath)nodes).hash;
	}
	
	public NewPath(NodeName src, NodeName dest) {
		super(2) ;
		this.add(src) ;
		this.add(dest) ;
	}
	
	public NewPath getSubPath(int sourceIndex, int destinationIndex){
		return new NewPath(this.subList(sourceIndex, destinationIndex+1));
	}
	
	public String toString(){
		StringBuffer buff = new StringBuffer(this.size()*12);
		synchronized (buff) {
			for (NodeName aNode:this){
				buff.append(aNode).append(" - ");
			}
		}
		return buff.substring(0,buff.length()-3);
	}
	
	public int hashCode(){
		if (hash==0)
			for (NodeName aNode:this)
				hash=(hash*163)^aNode.hashCode();
		return hash;			
	}
	
	@Override
	public void add(int arg0, NodeName arg1) {
		hash=0;
		super.add(arg0, arg1);
	}

	@Override
	public boolean add(NodeName arg0) {
		hash=0;
		return super.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends NodeName> arg0) {
		hash=0;
		return super.addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends NodeName> arg1) {
		hash=0;
		return super.addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		hash=0;
		super.clear();
	}

	@Override
	public NodeName remove(int arg0) {
		hash=0;
		return super.remove(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		hash=0;
		return super.remove(arg0);
	}

	@Override
	protected void removeRange(int arg0, int arg1) {
		hash=0;
		super.removeRange(arg0, arg1);
	}

	@Override
	public NodeName set(int arg0, NodeName arg1) {
		hash=0;
		return super.set(arg0, arg1);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		hash=0;
		return super.removeAll(arg0);
	}

	@Override
	public Object clone() {
		return this;
	}

	/**
	 * Returns a view of the portion of this instance of NewPath excluding the two endpoints (Ingress and Egress). (If fromIndex and toIndex are equal, the returned list is empty.) The returned list is backed by this list, so non-structural changes in the returned list are reflected in this list, and vice-versa. The returned list supports all of the optional list operations supported by this list.
	 * @return
	 */
	public List<NodeName> getMiddleNodes() {
		if (this.size()<=2)
			return new ArrayList<NodeName>();
		return this.subList(1,this.size()-1);
	}
}
