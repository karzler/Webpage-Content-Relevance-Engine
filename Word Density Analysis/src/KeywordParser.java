/*
 * This class is responsible for parsing of the document text
 * extracted from the given url page
 * @author Mohit Mishra
 * Last Updated: August 10, 2015.
 */
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.xml.ws.ProtocolException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class KeywordParser {	
	// Site URL
	private URL url;	
	// Tree structure containing every possible keyword
	protected Tree KYTree;	
	// Set containing words to ignore
	private HashSet<String> stopwords;	
	/*
	 * Wrapper Method to parse the given page
	 */	
	public void parse(Page page){
		this.setUrl(page.getURL());
		KYTree = new Tree();
		stopwords = new HashSet<String>();
		initParsing();
	}	
    /*
	 *  initParsing method initiates parsing of stop-words and keywords
	 */
	private void initParsing() {
		parseStopwords();
		parseKeywords();
	}	
	/*
	 * parseStopWords produces stopwords that help in "extracting" keywords 
	 */
	private void parseStopwords() {
		/*
		 *  Treat the stopwords list file as a resource for being able to generate a jar file 
		 */
		Scanner scan = new Scanner(getClass().getResourceAsStream("stop-words_english_1_en.txt"));
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		}
		// to prevent Resource leaks, close scan
		scan.close();
	}
	/*
	 * Returns a string with all unnecessary characters removed
	 */
	private String removeRedundantChar(String inputStr) {
		
		// Remove extra whitespace
		String str = inputStr.trim();
				
		// Remove special characters
		str = str.replace("!", "");
		str = str.replace("?", "");
		str = str.replace("\\", "");
		str = str.replace("/", "");
		str = str.replace(",", "");
		str = str.replace("(", "");
		str = str.replace(")", "");
		str = str.replace("<", "");
		str = str.replace(">", "");
		str = str.replace("#", "");	
		str = str.replace("\"", "");
		str = str.replace("+", "");
		str = str.replace(":", "");
		str = str.replace("=", "");
		str = str.replace("--", "");
		str = str.replace("*", "");
		// Check for words like "Ph.D.", where the "." shouldn't be removed 
		if (str.endsWith(".") && !Character.isUpperCase(str.charAt(str.length()-2)))
			str = str.replace(".", "");		
		return str;
	}	
	/*
	 * Parse the keywords and build Tree map of keywords/keyphrases
	 */
	private void parseKeywords() {
		
		// Connect to given url
		Document doc = null;
		try {
			doc = Jsoup.connect(getUrl().toString()).get();
		} catch (IOException e) {
			System.out.println("Input/Output Exception encountered.\n");
			e.printStackTrace();
		} catch(ProtocolException e) {
			System.out.println("Protocol Exception encountered.\n");
			e.printStackTrace();
		}		
		// Store all content inside html element
		String title = doc.title();
		if (title == null)
			title = "";
		MetaKeywords MKY = new MetaKeywords(removeRedundantChar(title).trim(), stopwords);
		for(Element meta : doc.select("meta")) {
		    //System.out.println("Name: " + meta.attr("name") + " - Content: " + meta.attr("content"));
		    String content = removeRedundantChar(meta.attr("content"));
		    MKY.addTokens(content, stopwords);
		}			
		// Scanner for parsing each individual string in document
		Scanner scan = new Scanner(doc.text());		
		// Next candidate keyword in the document text
		String nextWord = "";		
		// Longest Keyphrase that can be built before stopword/stopping character is encountered
		String keyPhrase = "";		
		// Start scanning through the document text
		while (scan.hasNext()) {
			// Stores next word in document
			nextWord = scan.next();			
			// Check if nextWord ends with a stopping character like ".", ",", ";", "!" or "?" 
			boolean endsWithStopChar = (nextWord.endsWith(".") || nextWord.endsWith(",") || nextWord.endsWith("!") || nextWord.endsWith("?"));
			// Check if nextWord is a stopword
			boolean stopword = isStopword(removeRedundantChar(nextWord.toLowerCase().trim()));	
			/*
			 *  build relevant keyphrases from keywords extracted
			 */
			if (endsWithStopChar || stopword) {
				/*
				 *  Remove all stopping characters and add to the keyPhrase to be built,
				 *  if it is not a stopword
				 */				
				if (endsWithStopChar && !stopword) {
					keyPhrase += " " + removeRedundantChar(nextWord).trim();
				}
				if (keyPhrase != "") {
					// Convert to an array of Strings (tokens) split by whitespace " "
					String[] keywords = keyPhrase.trim().split(" ");
					// Add only those keyword phrases whose length is < 10
					if (keywords.length < 10){
						if (title != "" || !MKY.getMeta().isEmpty()) {
							for (String keyword : keywords){
								// Check if any of the keyword is contained in MKY's meta set
								if (MKY.getMeta().contains(keyword)) {
									KYTree.add(keywords);
									break;
								}								
							}
						}
						// If the MKY's meta set is empty, then dump all keywords in KYTree
						else {
							KYTree.add(keywords);
						}	
					}	
					// Restore keyphrase 
					keyPhrase = "";
				}
			}
			else {
				keyPhrase += removeRedundantChar(nextWord) + " ";
			}
		}
		// To prevent Resource leaks, close scan
		scan.close();
	}	
	/*
	 * Returns true if a string is a stopword
	 */
	private boolean isStopword(String word) {
		return stopwords.contains(word.toLowerCase()); 
	}
	/*
	 *  Returns a set of all single keywords stored in the KYTree
	 */
	public Set<String> getKeyset() {
		return KYTree.getKeySet();
	}	
	/*
	 *  Returns a HashMap containing all keyword phrases starting with string 'str' and their respective count
	 */
	public HashMap<String, Integer> get(String str) {
		return KYTree.getKeywords(str);
	}	
	/*
	 *  Returns a HashMap containing all keyword phrases and their respective count
	 */
	public HashMap<String, Integer> getAll() {
		return KYTree.getAllKeywords();
	}
	/*
	 * Following visitor pattern
	 */
	public void analyzeVisit(AnalyzeVisitor tv){
		tv.doVisit(this);
	}
	/*
	 * Getter method for url
	 */
	public URL getUrl() {
		return url;
	}
	/*
	 * Setter method for "valid" url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
}