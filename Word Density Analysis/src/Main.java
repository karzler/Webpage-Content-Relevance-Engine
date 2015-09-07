
/**
 * @author Mohit Mishra
 * @version 1.0
 * @category Web Crawling - Word Density Analysis
 * Last updated: August 9, 2015.
 */

public class Main {
	public static void main(String[] args) {
		// Check validity of number of arguments
		if (args.length < 1 || args.length >= 2) {
			System.out.println("Usage: \n\tjava -jar Assignment.jar <arg>\n\twhere, arg is URL");
		}
		else {
			String url = null;
			try{
				url = args[0];
			} catch(Exception e) {
				System.out.println("Exception encoutered in the URL input.");
				e.printStackTrace();
			}
			Page page = null;
			try {
				// Following Visitor pattern
				page = new Page(url);
				KeywordParser kwp = new KeywordParser ();
				page.visit(new KeyParserVisitor(kwp));
				Analyzer analyzer = new Analyzer ();
				kwp.analyzeVisit(new AnalyzeVisitor (analyzer));
			} catch (Exception e) {
				System.out.println("Exception encoutered while parsing and analyzing page.\n" +
						"Please see the stacktrace below for details.\n");
				e.printStackTrace();
			}
		}
	}
	
}
	

