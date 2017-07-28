import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.ac.kaist.mms_client.MMSClientHandler;
import kr.ac.kaist.mms_client.MMSConfiguration;

/** 
File name : TS2_server.java
	Relaying message function for the purpose of testing MMS
Author : Young Jin Kim(jungst0001@kaist.ac.kr)
Creation Date : 2017-07-23
 */


public class TS2_server {
	public static void main(String[] args) throws Exception{
		String MRNcase1 = "urn:mrn:imo:imo-no:ser";
		String MRNcase2 = "urn:mrn:imo:imo-no:ts-mms-02-serverAAAAAAAAAAA";
		String MRNcase3 = "urn:mrn:imo:imo-no:ts-mms-02-server";
		String MRNcase4 = "urn:mrn:imo:imo-no:ts-mms-02-serverv2";
		MMSConfiguration.MMS_URL="127.0.0.1:8088";
		int port1 = 8902;
		int port2 = 8903;
		int port3 = 8904;
		int port4 = 8905;


		MMSClientHandler server1 = new MMSClientHandler(MRNcase1);
		MMSClientHandler server2 = new MMSClientHandler(MRNcase2);
		MMSClientHandler server3 = new MMSClientHandler(MRNcase3);
		MMSClientHandler server4 = new MMSClientHandler(MRNcase4);

		server1.setServerPort(port1, new MMSClientHandler.RequestCallback() {

			@Override
			public Map<String, List<String>> setResponseHeader() {
				// TODO Auto-generated method stub
				Map<String, List<String>> myHdr = new HashMap<String, List<String>>();
				ArrayList<String> srcMRN = new ArrayList<String>();
				ArrayList<String> dstMRN = new ArrayList<String>();							
				srcMRN.add("urn:mrn:imo:imo-no:ser");
				dstMRN.add("urn:mrn:imo:imo-no:cli");				
				myHdr.put("srcMRN", srcMRN);
				myHdr.put("dstMRN", dstMRN);
				return myHdr;			

			}

			@Override
			public int setResponseCode() {
				// TODO Auto-generated method stub
				return 200;
			}

			@Override
			public String respondToClient(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				int calcSize =0;
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());

					calcSize +=key.length();
					calcSize+=headerField.get(key).toString().length()-1;
				}

				System.out.println("total Size : "+calcSize);
				System.out.println(message);	
				System.out.println("case1");
				return "OK";
			}



		}); 			

		server2.setServerPort(port2,new MMSClientHandler.RequestCallback() {

			@Override
			public Map<String, List<String>> setResponseHeader() {
				Map<String, List<String>> myHdr = new HashMap<String, List<String>>();
				ArrayList<String> srcMRN = new ArrayList<String>();
				ArrayList<String> dstMRN = new ArrayList<String>();				
				srcMRN.add("urn:mrn:imo:imo-no:ts-mms-02-serverAAAAAAAAAAA");
				dstMRN.add("urn:mrn:imo:imo-no:ts-mms-02-clientAAAAAAAAAAA");				
				myHdr.put("srcMRN", srcMRN);
				myHdr.put("dstMRN", dstMRN);
				return myHdr;	
			}

			@Override
			public int setResponseCode() {
				// TODO Auto-generated method stub
				return 200;
			}

			@Override
			public String respondToClient(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				int calcSize =0;
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());

					calcSize +=key.length();
					calcSize+=headerField.get(key).toString().length()-1;
				}

				System.out.println("total Size : "+calcSize);
				System.out.println(message);	
				System.out.println("case2");
				return "OK";
			}
		} );
		server3.setServerPort(port3,new MMSClientHandler.RequestCallback() {

			@Override
			public Map<String, List<String>> setResponseHeader() {
				Map<String, List<String>> myHdr = new HashMap<String, List<String>>();
				ArrayList<String> srcMRN = new ArrayList<String>();
				ArrayList<String> dstMRN = new ArrayList<String>();
				ArrayList<String> Oauth_token = new ArrayList<String>();
				Oauth_token.add("ad180jjd733klru7");
				srcMRN.add("urn:mrn:imo:imo-no:ts-mms-02-server");
				dstMRN.add("urn:mrn:imo:imo-no:ts-mms-02-client");
				myHdr.put("Oauth_token",Oauth_token);
				myHdr.put("srcMRN", srcMRN);
				myHdr.put("dstMRN", dstMRN);
				return myHdr;	
			}

			@Override
			public int setResponseCode() {
				// TODO Auto-generated method stub
				return 200;
			}

			@Override
			public String respondToClient(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				int calcSize =0;
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());

					calcSize +=key.length();
					calcSize+=headerField.get(key).toString().length()-1;
				}

				System.out.println("total Size : "+calcSize);
				System.out.println(message);
				System.out.println("case3");
				return "OK";
			}
		} );

		server4.setServerPort(port4,new MMSClientHandler.RequestCallback() {

			@Override
			public Map<String, List<String>> setResponseHeader() {
				Map<String, List<String>> myHdr = new HashMap<String, List<String>>();
				ArrayList<String> srcMRN = new ArrayList<String>();
				ArrayList<String> dstMRN = new ArrayList<String>();
				ArrayList<String> Oauth_token = new ArrayList<String>();
				Oauth_token.add("ad180jjd733klru7adkfb129h2o3bjk42lbkgjdbk3ndj32ngjb23g9p293dih2n9ub58bg4b39vruncjk3nu9fn9u3hngj2n4kjldkn903z,fuhn7"
						+ "vn27hn2h4r7hfyh2yvh3g6xgf8h7vn2547vcnm7hynzgg638gcv6bng46739gvty29c7ny478cvn7234yhtcxn347tcvn27nvcg3427tnvc732bn72vcgy7"
						+ "3h47cn347t2hcndxhasdas");
				srcMRN.add("urn:mrn:imo:imo-no:ts-mms-02-server");
				dstMRN.add("urn:mrn:imo:imo-no:ts-mms-02-client");
				myHdr.put("Oauth_token",Oauth_token);
				myHdr.put("srcMRN", srcMRN);
				myHdr.put("dstMRN", dstMRN);

				return myHdr;	
			}

			@Override
			public int setResponseCode() {
				// TODO Auto-generated method stub
				return 200;
			}

			@Override
			public String respondToClient(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				int calcSize =0;
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());

					calcSize +=key.length();
					calcSize+=headerField.get(key).toString().length()-1;
				}

				System.out.println("total Size : "+calcSize);
				System.out.println(message);
				System.out.println("case4");
				return "OK";
			}
		} );	
	}
}
