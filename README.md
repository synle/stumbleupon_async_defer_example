Stumble Upon Async Defer example
================================

Intro
=====
What I found in this implementation of async in java is a lack of examples. Below is some code examples

Main repo
=========
https://github.com/OpenTSDB/async

https://github.com/StumbleUponArchive/async

Examples
========
Simplest Example
================

```
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
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
```  

Group In Any Order
==================
```
  /**
  * group in any order
  * @param args
  */
  public static void main(String[] args) {
		try {
			List<Deferred<String>> lstDfd = new ArrayList<Deferred<String>>();
			List<Callback<String, String>> lstCb = new ArrayList<Callback<String, String>>();
			List<Integer> lstIdx = new ArrayList<Integer>();
			
			for (int i = 0; i < 10; i++){
				Callback<String, String> cb = new MyCallback();
				Deferred<String> dfd = new Deferred<String>();
				
				dfd.addCallback(cb);
				
				lstDfd.add(dfd);
				lstCb.add(cb);
				lstIdx.add(i);
			}
			
			
			//combine stuff
			Deferred<ArrayList<String>> dfdGroup = Deferred.group(lstDfd);
			Callback<ArrayList<String>, ArrayList<String>> cbGroup = new MyGroupCallback();
			dfdGroup.addCallback(cbGroup);
			
			
			//randomly call the back
			while (lstIdx.size() > 0){
				int randomIdx = randInt(0, lstIdx.size() - 1);
				int deferIdx = lstIdx.get(randomIdx);
				
				lstDfd.get(deferIdx).callback(String.valueOf(deferIdx));
				lstIdx.remove(randomIdx);
			}
			
		} catch (Exception e) {}
	}
```

Sample run for group in any order
```
  mycb is called with: 4
  mycb is called with: 3
  mycb is called with: 5
  mycb is called with: 2
  mycb is called with: 1
  group callback
  group cb: 4
  group cb: 3
  group cb: 5
  group cb: 2
  group cb: 1
```

Group In Order
==============
```  
    /**
    * group in order
    * @param args
    */
    public static void main(String[] args) {
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

        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
```

Sample run for callback run in order
```
      mycb is called with: 10
      mycb is called with: 9
      mycb is called with: 8
      mycb is called with: 7
      mycb is called with: 6
      mycb is called with: 5
      mycb is called with: 4
      mycb is called with: 3
      mycb is called with: 2
      mycb is called with: 1
      group callback
      group cb: 1
      group cb: 2
      group cb: 3
      group cb: 4
      group cb: 5
      group cb: 6
      group cb: 7
      group cb: 8
      group cb: 9
      group cb: 10
```


Implmentation of Callback

```
      /**
      * simple callback that takes a string, print it and return it
      * @author syle
      *
      */
      public static class MyCallback implements Callback<String, String> {

        public String call(String arg) throws Exception {
          System.out.println("mycb is called with: " + arg);

          // TODO Auto-generated method stub
          return arg;
        }
      }




      /**
      * group callbacks that takes an array of string, print them and return them.
      * @author syle
      *
      */
      public static class MyGroupCallback implements Callback<ArrayList<String>, ArrayList<String>>{

        public ArrayList<String> call(ArrayList<String> arg) throws Exception {
          System.out.println("group callback");

          for (String s : arg){
            System.out.println("group cb: " + s);
          }

          // TODO Auto-generated method stub
          return arg;
        }
      }
```
