/*
 * This class is responsible for storing the keywords extracted from
 * contents of meta tags of the document
 */
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
public class MetaKeywords {
	// HastSet containing keywords of contents of meta tags
	private Set<String> meta = new HashSet<String>();
	/*
	 *  Add token (non-stopword) extracted from the content
	 */
	public void addTokens(String text, HashSet<String> stopwords){
		// Replace all "," and ";" with whitespace " "
		text = text.replace(",", " ").replace(";"," ");
		// Tokenize text
		StringTokenizer st = new StringTokenizer(text);
		while(st.hasMoreElements()) {
			String key = st.nextToken();
			/*
			 *  Check if the token (key) ends with any character other than alphabet/digit
			 *  e.g. BrightEdge:
			 *  If it does, remove the character until alphanumeric encountered,
			 *  and then add to the Set "meta"
			 */
			int index = key.length()-1;
			while (index >= 0 && !Character.isLetter(key.charAt(index)) && 
					!Character.isDigit(key.charAt(index))) {
						index--;
			}
			if (index >= 1) {
				String extractKey = key.substring(0, index + 1);
				if (!meta.contains(extractKey)) {
					meta.add(extractKey);
				}
			} else {
				continue;
			}
		}
	}
	public MetaKeywords(String str, HashSet<String> stopwords) {
		addTokens(str, stopwords);
	}
	/*
	 * Getter method to return meta
	 */
	public Set<String> getMeta() {
		return meta;
	}
	/*
	 * Setter method for setting meta
	 */
	public void setMeta(Set<String> meta) {
		this.meta = meta;
	}
}
