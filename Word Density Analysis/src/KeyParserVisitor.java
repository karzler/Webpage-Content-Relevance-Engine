/*
 * Visitor for KeyParser.java
 */
public class KeyParserVisitor extends PageVisitor {
    private KeywordParser keywordParser;
    
	public KeyParserVisitor(KeywordParser KWP) {
		this.keywordParser = KWP;
	}
	
	@Override
	public void doVisit(Page page) {
		keywordParser.parse(page);
	}
}
