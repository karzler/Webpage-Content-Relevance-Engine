/*
 * Visitor for Analyzer.java
 */
public class AnalyzeVisitor extends Visitor {
	Analyzer analyzer;
	
	public void doVisit(KeywordParser KWP) {
		analyzer.analyze(KWP);
	}
	
	public AnalyzeVisitor(Analyzer analyze){
		analyzer = analyze;
	}
}
