package SimpleDeferExample;

import java.util.ArrayList;
import java.util.List;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

public class MainDeferExample {
	/**
	 * group in any order
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<Deferred<String>> lstDfd = new ArrayList<Deferred<String>>();
			List<Callback<String, String>> lstCb = new ArrayList<Callback<String, String>>();
			
			for (int i = 1; i <= 5; i++){
				Callback<String, String> cb = new MyCallback();
				Deferred<String> dfd = new Deferred<String>();
				
				dfd.addCallback(cb);
				
				lstDfd.add(dfd);
				lstCb.add(cb);
			}
			
			
			//combine stuff
			Deferred<ArrayList<String>> dfdGroup = Deferred.group(lstDfd);
			Callback<ArrayList<String>, ArrayList<String>> cbGroup = new MyGroupCallback();
			dfdGroup.addCallback(cbGroup);
			
			
			//randomly call the back
			lstDfd.get(3).callback(String.valueOf(4));
			lstDfd.get(2).callback(String.valueOf(3));
			lstDfd.get(4).callback(String.valueOf(5));
			lstDfd.get(1).callback(String.valueOf(2));
			lstDfd.get(0).callback(String.valueOf(1));
			
		} catch (Exception e) {}
	}
	
	
	/**
	 * simplest with only 1 defer
	 * @param args
	 */
	public static void main1(String[] args) {
		try {
			Deferred<String> dfd = new Deferred<String>();
			Callback<String, String> cb = new MyCallback();

			dfd.addCallback(cb);

			System.out.println("test");
			Thread.sleep(4000);
			dfd.callback("callback data after 4 seconds aaa");
		} catch (Exception e) {}
	}
	
	
	
	/**
	 * group in order
	 * @param args
	 */
	public static void main2(String[] args) {
		try {
			List<Deferred<String>> lstDfd = new ArrayList<Deferred<String>>();
			List<Callback<String, String>> lstCb = new ArrayList<Callback<String, String>>();
			
			for (int i = 1; i <= 10; i++){
				Callback<String, String> cb = new MyCallback();
				Deferred<String> dfd = new Deferred<String>();
				
				dfd.addCallback(cb);
				
				lstDfd.add(dfd);
				lstCb.add(cb);
			}
			
			
			//combine stuff
			Deferred<ArrayList<String>> dfdGroup = Deferred.groupInOrder(lstDfd);
			Callback<ArrayList<String>, ArrayList<String>> cbGroup = new MyGroupCallback();
			dfdGroup.addCallback(cbGroup);
			
			for (int i = lstDfd.size() - 1; i >= 0; i--){
				lstDfd.get(i).callback(String.valueOf(i + 1));
			}
			
		} catch (Exception e) {}
	}	
}