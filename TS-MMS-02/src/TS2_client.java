import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.ac.kaist.mms_client.MMSClientHandler;
import kr.ac.kaist.mms_client.MMSConfiguration;

/** 
File name : TS2_client.java
	Relaying message function for the purpose of testing MMS
Author : Young Jin Kim (jcdad3000@kaist.ac.kr)
Creation Date : 2017-07-23
 */

public class TS2_client {
	public static Map<String, List<String>> hh = null;
	public static String mm = null;
	
	public static void main(String[] args) throws Exception{
		String MRNcase1 = "urn:mrn:imo:imo-no:cli"; 
		String MRNcase2 = "urn:mrn:imo:imo-no:ts-mms-02-clientAAAAAAAAAAA"; 
		String MRNcase3 = "urn:mrn:imo:imo-no:ts-mms-02-client"; 
		String MRNcase4 = "urn:mrn:imo:imo-no:ts-mms-02-clientv2"; 

		MMSConfiguration.MMS_URL="127.0.0.1:8088";			

		MMSClientHandler client1 = new MMSClientHandler(MRNcase1);
		MMSClientHandler client2 = new MMSClientHandler(MRNcase2);
		MMSClientHandler client3 = new MMSClientHandler(MRNcase3);
		MMSClientHandler client4 = new MMSClientHandler(MRNcase4);
		
		Map<String, List<String>> myHdr = new HashMap<String, List<String>>();
		
		ArrayList<String> Oauth_token = new ArrayList<String>();
		ArrayList<String> Oauth_realm = new ArrayList<String>();
		ArrayList<String> Oauth_signature = new ArrayList<String>();
		ArrayList<String> Oauth_signature_method = new ArrayList<String>();
		ArrayList<String> Oauth_timestamp = new ArrayList<String>();
		ArrayList<String> Oauth_nonce = new ArrayList<String>();
		ArrayList<String> Oauth_consumer_key = new ArrayList<String>();
		ArrayList<String> Oauth_message = new ArrayList<String>();
		ArrayList<String> Oauth_version = new ArrayList<String>();
		Oauth_token.add("ad180jjd733klru7");
		Oauth_realm.add("Example");
		Oauth_signature.add("wOJIO9A2W5mFwDgiDvZbTSMK%2FPY%3D");
		Oauth_signature_method.add("HMAC-SHA1");
		Oauth_timestamp.add("137131200");
		Oauth_nonce.add("4572616e48616d6d65724c61686176");
		Oauth_consumer_key.add("0685bd9184jfhq22");
		Oauth_version.add("1.0");
		Oauth_message.add("HEllo");
		myHdr.put("Oauth_token",Oauth_token);
		myHdr.put("Oauth_realm",Oauth_realm);
		myHdr.put("Oauth_signature",Oauth_signature);
		myHdr.put("Oauth_signature_method",Oauth_signature_method);
		myHdr.put("Oauth_timestamp",Oauth_timestamp);
		myHdr.put("Oauth_nonce",Oauth_nonce);
		myHdr.put("Oauth_consumer_key",Oauth_consumer_key);
		myHdr.put("Oauth_version",Oauth_version);
		myHdr.put("Oauth_message",Oauth_message);
		
		client3.setMsgHeader(myHdr);
		
		client1.setSender(new MMSClientHandler.ResponseCallback() {

			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				Iterator<String> iter = headerField.keySet().iterator();
				int calcSize=0;
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());	

					if(key!=null)
					{
						calcSize +=key.length();
					}
					calcSize+=headerField.get(key).toString().length()-1;
				}
				System.out.println("size : "+ calcSize);
				System.out.println("message: " + message);
				System.out.println("Arrival of Message");
			}


		});

		client2.setSender(new MMSClientHandler.ResponseCallback() {

			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				Iterator<String> iter = headerField.keySet().iterator();
				int calcSize=0;
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());	

					if(key!=null)
					{
						calcSize +=key.length();
					}
					calcSize+=headerField.get(key).toString().length()-1;
				}
				System.out.println("size : "+ calcSize);
				System.out.println("message: " + message);
				System.out.println("Arrival of Message");
			}


		});

		client3.setSender(new MMSClientHandler.ResponseCallback() {

			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				Iterator<String> iter = headerField.keySet().iterator();
				int calcSize=0;
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());	

					if(key!=null)
					{
						calcSize +=key.length();
					}
					calcSize+=headerField.get(key).toString().length()-1;
				}
				System.out.println("size : "+ calcSize);
				System.out.println("message: " + message);
				System.out.println("Arrival of Message");
			}


		});

		client4.setSender(new MMSClientHandler.ResponseCallback() {

			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				Iterator<String> iter = headerField.keySet().iterator();
				int calcSize=0;
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());	

					if(key!=null)
					{
						calcSize +=key.length();
					}
					calcSize+=headerField.get(key).toString().length()-1;
				}
				System.out.println("size : "+ calcSize);
				System.out.println("message: " + message);
				System.out.println("Arrival of Message");
			}


		});

		String data = "hi";

		client1.sendPostMsg("urn:mrn:imo:imo-no:ser", data);
		System.out.println("sendMEssage1");
		client2.sendPostMsg("urn:mrn:imo:imo-no:ts-mms-02-serverAAAAAAAAAAA", data);
		System.out.println("sendMEssage2");
		client3.sendPostMsg("urn:mrn:imo:imo-no:ts-mms-02-server", data);
		System.out.println("sendMEssage3");
		client4.sendPostMsg("urn:mrn:imo:imo-no:ts-mms-02-serverv2", data);
		System.out.println("sendMEssage4");
		sendHttpPost("", data);
		
		
		System.out.println();
		Iterator<String> iter = hh.keySet().iterator();
		int calcSize=0;
		while (iter.hasNext()){
			String key = iter.next();
			System.out.println(key+":"+hh.get(key).toString());	

			if(key!=null)
			{
				calcSize +=key.length();
			}
			calcSize+=hh.get(key).toString().length()-1;
		}
		System.out.println("size : "+ calcSize);
		System.out.println("message: " + mm);
		System.out.println("Arrival of Message");
	}
	
	public static void sendHttpPost(String loc, String data) throws Exception{
		String url = "http://"+MMSConfiguration.MMS_URL; // MMS Server
		if (!loc.startsWith("/")) {
			loc = "/" + loc;
		}
		url += loc;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "MMSClient/0.5.0");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//		con.setRequestProperty("srcMRN", clientMRN);
//		con.setRequestProperty("dstMRN", dstMRN);
		//con.addRequestProperty("Connection","keep-alive");
		
//		if (headerField != null) {
//			con = addCustomHeaderField(con, headerField);
//		} 
		
		//load contents
		String urlParameters = data;
		

//		if(MMSConfiguration.LOGGING)System.out.println(TAG+"urlParameters: "+urlParameters);
		
		// Send post request
		con.setDoOutput(true);
		BufferedWriter wr = new BufferedWriter(
				new OutputStreamWriter(con.getOutputStream(),Charset.forName("UTF-8")));
		
//		if(MMSConfiguration.LOGGING)System.out.println(TAG+"Trying to send message");
		wr.write(urlParameters);
		wr.flush();
		wr.close();

		Map<String,List<String>> inH = con.getHeaderFields();
		inH = getModifiableMap(inH);
		int responseCode = con.getResponseCode();
		List<String> responseCodes = new ArrayList<String>();
		responseCodes.add(responseCode+"");
		inH.put("Response-code", responseCodes);
		
//		if(MMSConfiguration.LOGGING){
//			System.out.println("\n"+TAG+"Sending 'POST' request to URL : " + url);
//			System.out.println(TAG+"Post parameters : " + urlParameters);
//			System.out.println(TAG+"Response Code : " + responseCode);
//		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
//		if(MMSConfiguration.LOGGING)System.out.println(TAG+"Response: " + response.toString() + "\n");
//		receiveResponse(inH, response.toString());
		hh = inH;
		mm = response.toString();
		
		return;
	}
	
	public static Map<String, List<String>> getModifiableMap (Map<String, List<String>> map) {
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		Set<String> resHeaderKeyset = map.keySet(); 
		for (Iterator<String> resHeaderIterator = resHeaderKeyset.iterator();resHeaderIterator.hasNext();) {
			String key = resHeaderIterator.next();
			List<String> values = map.get(key);
			ret.put(key, values);
		}
	
		return ret;
	}
	
//	public static void receiveResponse (Map<String,List<String>> headerField, String message) {
//		if (!isRgstLoc) {
//			isRgstLoc = false;
//			try {
//				myCallback.callbackMethod(headerField, message);
//			} catch (NullPointerException e) {
//				System.out.println(TAG+"NullPointerException : Have to set response callback interface! MMSClientHandler.setSender()");
//			}
//		}
//			
//		return;
//	}
}
