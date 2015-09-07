/*
 * Tree class represents the entire tree "map" structure of the keywords/keyphrases
 * represented as TreeNodes
 */
import java.util.HashMap;
import java.util.Set;

public class Tree {
	private HashMap<String, Integer> map = null;
	private HashMap<String, TreeNode> tree;	
	public Tree() {
		tree = new HashMap<String, TreeNode>();	
	}
	/*
	 *  Add a keyword to the tree. 
	 *  If keyword already exists then increments the TreeNode's frequency
	 */
	public void add(String keyword) {
		if (!tree.containsKey(keyword))
			tree.put(keyword, new TreeNode(keyword));
		else {
			get(keyword).increment();
		}
	}
	/*
	 * Add keyphrases and corresponding individual keywords to the tree map
	 */
	public void add(String[] keywords) {		
		int index = 1;
		for (String keyword : keywords) {
			add(keyword);
			addUtility(keywords, index, tree.get(keyword));
			index++;
		}
	}	
	/*
	 * Utility function for add method
	 * This utility function adds to the tree a keyphrase with parent as the 
	 * tree node at index
	 */
	private void addUtility(String[] keywords, int index, TreeNode parent) {
		if (index != keywords.length) {
			parent.add(keywords[index]);
			addUtility(keywords, index + 1, parent.get(keywords[index]));
		}
	}	
	/*
	 * Returns the TreeNode associated with the string 'keyword'
	 */
	public TreeNode get(String keyword) {
		return tree.get(keyword);
	}	
	/*
	 * Returns a Set of all keys in the tree HashMap
	 */
	public Set<String> getKeySet() {
		return tree.keySet();
	}	
	/*
	 * Returns a HashMap containing each keyword phrase starting with 
	 * string 'startKeyword' and their respective size
	 */
	public HashMap<String, Integer> getKeywords(String startKeyword) {
		return getKeywords(get(startKeyword));
	}	
	/*
	 * Utility method for getKeywords(String startKeyword)
	 */
	private HashMap<String, Integer> getKeywords(TreeNode startNode) {		
		map = new HashMap<String, Integer>();
		getKeywordsUtility(startNode, startNode.getValue());		
		return map;
	}	
	/*
	 * Utility method for getKeywords(TreeNode startNode)
	 */
	private String getKeywordsUtility(TreeNode node, String keywords) {
		
		if (node.hasChildren()) {
			HashMap<String, TreeNode> ch = node.getChildren();
			for (String child : ch.keySet()) {
				getKeywordsUtility(ch.get(child), keywords + " " + child);
			}
		}		
		map.put(keywords, node.getScore());		
		return keywords.trim();
	}	
	/*
	 * Returns a HashMap containing all keywords in the tree 
	 * and their respective score
	 */
	public HashMap<String, Integer> getAllKeywords() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (String startKeyword : getKeySet()) {
			HashMap<String, Integer> keywords = getKeywords(startKeyword);
			for (String key : keywords.keySet()) {
				map.put(key, keywords.get(key));
			}
		}
		return map;
	}
		
}