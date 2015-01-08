package SimpleDeferExample;

import java.util.ArrayList;

import com.stumbleupon.async.Callback;

/**
 * group callbacks that takes an array of string, print them and return them.
 * @author syle
 *
 */
public class MyGroupCallback implements Callback<ArrayList<String>, ArrayList<String>>{

	public ArrayList<String> call(ArrayList<String> arg) throws Exception {
		System.out.println("group callback");
		
		for (String s : arg){
			System.out.println("group cb: " + s);
		}
		
		
		return arg;
	}
}
