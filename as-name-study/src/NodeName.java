import java.util.HashMap;
import java.util.Map;

public class NodeName implements Comparable<NodeName>{

	private static Map<String, NodeName> nodeNames;

	private final String name;
	private final int hash;

	private NodeName(String nodeName){
			name = nodeName.intern();
			hash = name.hashCode();
	}
	
	@Override
	public int hashCode(){
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
/* 		if (o instanceof NodeName){
			NodeName oNode = (NodeName) o;
			if (oNode.type.equals(this.type) && number==oNode.number)
				return true;
		}  */
		return false;
	}
	
	public static NodeName getNodeName(String nodeName) {
		nodeName = nodeName.intern();
		if(nodeNames == null)
			nodeNames = new HashMap<String, NodeName>();
		if (nodeNames.containsKey(nodeName)) {
			NodeName anode = nodeNames.get(nodeName);
			return anode;
		}
		else {
			NodeName newOne = new NodeName(nodeName);
			nodeNames.put(nodeName, newOne);
			return newOne;
		}
	}
	
	@Override
	public Object clone(){
		return this;
	}

	public static void resetStatic() {
		try {
			nodeNames.clear();
			nodeNames = null;
		} catch (NullPointerException npe) { }
	}

	@Override
	public int compareTo(NodeName arg0) {
		return this.name.compareTo(arg0.name);
	}
}
