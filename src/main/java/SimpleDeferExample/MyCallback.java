package SimpleDeferExample;

import com.stumbleupon.async.Callback;

/**
 * simple callback that takes a string, print it and return it
 * @author syle
 *
 */
public class MyCallback implements Callback<String, String> {

	public String call(String arg) throws Exception {
		System.out.println("mycb is called with: " + arg);

		
		return arg;
	}
}