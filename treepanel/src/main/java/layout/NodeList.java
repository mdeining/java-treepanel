package layout;

public class NodeList {
	
	public NodeClass PREVNODE;
	public NodeList NEXTLEVEL;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(NodeList p = this; p != null; p = p.NEXTLEVEL)
			sb.append(p.PREVNODE.toString() + " -> ");
		sb.append("null");
		return sb.toString();
	}

}
