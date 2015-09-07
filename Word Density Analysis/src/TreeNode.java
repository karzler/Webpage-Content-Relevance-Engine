/*
 * This class represents a Tree node
 * A node has the following fields:
 * 		value    : stores the keyword
 * 		frequency: frequency of occurrence in the Tree
 * 		wordCount: number of words in the keyword phrase
 * 		score    : score determines the density ranking = frequency * wordCount
 * 		children : Children derived from "value" keyword
 */
import java.util.HashMap;
import java.util.StringTokenizer;

public class TreeNode implements Comparable<Object> {
	/*
	 * Fields of TreeNode
	 */
	private String value;
	private int frequency;
	private int wordCount;
	private int score;
	private HashMap<String, TreeNode> children;	
	/*
	 * Non-parameterized constructor
	 */
	public TreeNode() {
		value = "";
		frequency = 0;
		wordCount = 0;
		score = 0;
		children = new HashMap<String, TreeNode>();
	}
	/*
	 * Parameterized constructor 1 for TreeNode
	 * Parameters: value
	 */
	public TreeNode(String value) {
		this.value = value;
		frequency = 1;
		wordCount = countWords(value);
		score = frequency * wordCount;
		children = new HashMap<String, TreeNode>();
	}
	
	/*
	 * Parameterized constructor 2 for TreeNode
	 * Parameters: value and frequency
	 */
	public TreeNode(String value, int frequency) {
		this.value = value;
		this.frequency = frequency;
		wordCount = countWords(value);
		score = frequency * wordCount;
		children = new HashMap<String, TreeNode>();
	}
	
	/*
	 * Adds a new child TreeNode. 
	 * If child already exists, then increment the child's frequency.
	 * Return the child TreeNode
	 */
	public TreeNode add(String child) {
		if (!children.containsKey(child))
			children.put(child, new TreeNode(child));
		else
			children.get(child).increment();
		
		return children.get(child);
	}
	
	/*
	 * Returns the "child" of this.TreeNode
	 */
	public TreeNode get(String child) {
		return children.get(child);
	}
	
	/*
	 * Increments the frequency of the TreeNode
	 */
	public void increment() {		
		frequency += 1;
		score += wordCount;
	}
	
	/*
	 * Returns the value of the TreeNode
	 */
	public String getValue() {
		return value;
	}
	/*
	 * Returns the frequency of the TreeNode
	 */
	public int getFrequency(){
		return frequency;
	}
	/*
	 * Returns the score of the TreeNode
	 */
	public int getScore() {
		return score;
	}
	/*
	 * Returns a HashMap containing children strings (and their TreeNodes)
	 */
	public HashMap<String, TreeNode> getChildren() {
		return children;
	}
	
	/*
	 * Returns true if the TreeNode has children
	 */
	public boolean hasChildren() {
		return children.size() != 0;
	}
	/*
	 * Returns if the Node a is greater than Node b in terms of score
	 * This is used for priority queue data structure used in KeyParser.java
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object obj) {
		TreeNode a = this;
		TreeNode b = (TreeNode) obj;		
		if (a.getScore() > b.getScore())
			return -1;
		else if (a.getScore() < b.getScore())
			return 1;
		else
			return 0;
	}
	/*
	 * Returns the number of words in a string
	 * Note that any numbers are not considered as words in this method
	 * To qualify to be a word, a word needs to have at least one letter
	 */
	public int countWords(String s) {
	    int wordCount = 0;
	    StringTokenizer st = new StringTokenizer(s);
	    while(st.hasMoreTokens()) {
	    	boolean containsAnyLetter = false;
	    	for (char c : st.nextToken().toCharArray()) {
	    		if (Character.isLetter(c)) {
	    			containsAnyLetter = true;
	    			break;
	    		}
	    	}
	    	if (containsAnyLetter == true) {
	    		wordCount++;
	    	}
	    }
	    return wordCount;
	}
}