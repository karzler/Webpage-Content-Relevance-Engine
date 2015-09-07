/*
 * This class analyzes the keywords to print out most "relevant" ones
 */
import java.util.PriorityQueue;
import java.util.HashMap;
public class Analyzer {
	/*
	 * Analyzes the Keywords parsed in KeyParser 
	 * A default threshold of 0.125 * maximum score is taken
	 * If a candidate keyword's score is > threshold, 
	 * it is considered as "relevant" keyword/key-phrase
	 */
	public void analyze(KeywordParser KWP){
		// Priority Queue data structure for scores stored in non-increasing order
		PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>();
     	System.out.println("------------------------------------------------------------------\n"
     			+ "@author: Mohit Mishra\n"
     			+ "@affliation: Indian Institute of Technology (BHU) Varansi\n"
     			+ "@version: 1.0\n"
     			+ "@purpose: BrightEdge 48-hours Coding Assignment\n"
     			+ "@Category: Software Engineer\n"
     			+ "@Task: Word Density Analysis\n"
     			+ "Last Updated: August 10, 2015");
     	System.out.println("-------------------------------------------------------------------\n");
		System.out.println("URL: \n"+(KWP.getUrl()));
		System.out.println("\nRelevant Keywords/Phrases: \n");
		for (String s : KWP.getKeyset()) {
				HashMap <String, Integer> keywordsMap = KWP.get(s);
				for (String keyword : keywordsMap.keySet()) {
					pq.add(new TreeNode(keyword, keywordsMap.get(keyword)));
				}
		}
		// threshold field, will be set in the while loop later
		double threshold = 0;
		/* default percentile value, above which all keywords/keyphrases
		 * are considered as "relevant" 
		 */		
		double percentile = 0.125;
		if (!pq.isEmpty()){
				// compute threshold (e.g. = 0.125 * maximum Score)
				threshold = pq.peek().getScore() * percentile;
		}
		while(!pq.isEmpty()) {
				TreeNode node = pq.remove();
				// if score >= threshold, the keyword/keyphrase is relevant
				if (node.getScore() >= threshold)
					System.out.println(node.getValue().trim());
		}
	}
}
