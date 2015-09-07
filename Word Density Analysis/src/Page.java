/*
 * Page class handles page parsing from high-level,
 * following the visitor pattern
 */
import java.net.URL;
import java.net.MalformedURLException;

public class Page {	
	// URL 
	private URL url;	
	/*
	 *  validate url before proceeding to parsing
	 */
	private void URLValidator(String url) {		
		try{
			this.setURL(new URL(url));
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception encountered.\n"
					+ "Please check if the url entered is correct or not.\n");
			e.printStackTrace();
		}
	}	
	/*
	 *  Setter method for accepting url
	 */
	public void setURL(String url) {
		URLValidator(url);
	}	
	/*
	 * Getter method for retrieving url.
	 */
	public URL getURL() {
		return this.url;
	}
	/*
	 *  Non-parameterized constructor for Page
	 *  Default URL passed: http://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster
	 *  
	 */
	public Page() {
		setURL("http://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster");
	}	
	/*
	 *  Parameterized constructor for Page
	 */
	public Page(String url) {
		setURL(url);
	}
	/*
	 * following visitor pattern, calls visit to begin doing the task
	 */
	public void visit(PageVisitor v) {
		v.doVisit (this);
		
	}
    /*
     * Setter method for accepting "validated" URL
     */
	public void setURL(URL url) {
		this.url = url;
	}	

} 