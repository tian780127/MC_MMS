package kr.ac.kaist.mms_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MMSSndHandler {
	private static final String TAG = "MMSSndHandler";
	private final String USER_AGENT = "MMSClient/0.1";
	private String clientMRN;
	MMSSndHandler (String clientMRN){
		this.clientMRN = clientMRN;
	}

	String registerLocator(int port) throws Exception {
		return sendHttpPost("urn:mrn:smart-navi:device:mms1", "/registering", port+":2", null);
		
	}
	
	String sendHttpPost(String dstMRN, String loc, String data, JSONObject headerField) throws Exception{
		
		String url = "http://"+MMSConfiguration.MMSURL; // MMS Server
		if (loc.startsWith("/")) {
			url += loc;
		} else {
			url += "/" + loc;
		}
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("srcMRN", clientMRN);
		con.setRequestProperty("dstMRN", dstMRN);
		//con.addRequestProperty("Connection","keep-alive");
		
		if (headerField != null) {
			if(MMSConfiguration.logging)System.out.println("set headerfield");
			for (Iterator keys = headerField.keySet().iterator() ; keys.hasNext() ;) {
				String key = (String) keys.next();
				String value = (String) headerField.get(key);
				if(MMSConfiguration.logging)System.out.println(key+":"+value);
				con.setRequestProperty(key, value);
			}
		} 
		
		//load contents
		String urlParameters;
		if (!loc.equals("/registering")){
			//		change the string data to json format
			JSONObject jsonFrame = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("seq", "1");
			jsonObject.put("srcMRN", clientMRN);
			jsonObject.put("data", data);
			jsonArray.add(jsonObject);
			jsonFrame.put("payload", jsonArray);
			
			String jsonPayload = jsonFrame.toJSONString();
			
			urlParameters = jsonPayload;
		} else {
			urlParameters = data;
		}

		if(MMSConfiguration.logging)System.out.println("urlParameters: "+urlParameters);
		
		// Send post request
		con.setDoOutput(true);
		BufferedWriter wr = new BufferedWriter(
				new OutputStreamWriter(con.getOutputStream(),Charset.forName("UTF-8")));
		wr.write(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(MMSConfiguration.logging)System.out.println("\nSending 'POST' request to URL : " + url);
		if(MMSConfiguration.logging)System.out.println("Post parameters : " + urlParameters);
		if(MMSConfiguration.logging)System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		if(MMSConfiguration.logging)System.out.println("response: " + response.toString());
		return new String(response.toString().getBytes(), "utf-8");
	}
	
	//OONI
	String sendHttpGetFile(String dstMRN, String fileName, JSONObject headerField) throws Exception {

		String url = "http://"+MMSConfiguration.MMSURL; // MMS Server
		if (fileName.startsWith("/")) {
			url += fileName;
		} else {
			url += "/" + fileName;
		}
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("srcMRN", clientMRN);
		con.setRequestProperty("dstMRN", dstMRN);
		if (headerField != null) {
			for (Iterator keys = headerField.keySet().iterator() ; keys.hasNext() ;) {
				String key = (String) keys.next();
				String value = (String) headerField.get(key);
				con.setRequestProperty(key, value);
			}
		}
		//con.addRequestProperty("Connection","keep-alive");
		

		// Send post request
		con.setDoOutput(true);
		BufferedWriter wr = new BufferedWriter(
				new OutputStreamWriter(con.getOutputStream(),Charset.forName("UTF-8")));
		wr.write("");
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(MMSConfiguration.logging)System.out.println("\nSending 'POST' request to URL : " + url);
		if(MMSConfiguration.logging)System.out.println("Post parameters : " + "");
		if(MMSConfiguration.logging)System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
		String inputLine;
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		
		while ((inputLine = in.readLine()) != null) {
			out.append(inputLine); out.newLine();
		}
		
		out.flush();
		out.close();
		in.close();
		return fileName + "is saved";
	}
	//OONI end
	
	//HJH
	String sendHttpGet(String dstMRN, String loc, String params, JSONObject headerField) throws Exception {

		String url = "http://"+MMSConfiguration.MMSURL; // MMS Server
		if (loc.startsWith("/")) {
			url += loc;
		} else {
			url += "/" + loc;
		}
		
		if (params != null) {
			if (params.equals("")) {
				
			}
			else if (params.startsWith("?")) {
				url += params;
			} else {
				url += "?" + params;
			}
		}
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("srcMRN", clientMRN);
		con.setRequestProperty("dstMRN", dstMRN);
		if (headerField != null) {
			for (Iterator keys = headerField.keySet().iterator() ; keys.hasNext() ;) {
				String key = (String) keys.next();
				String value = (String) headerField.get(key);
				con.setRequestProperty(key, value);
			}
		}
		//con.addRequestProperty("Connection","keep-alive");
		
		// Send get request
		con.setDoOutput(true);
		BufferedWriter wr = new BufferedWriter(
				new OutputStreamWriter(con.getOutputStream(),Charset.forName("UTF-8")));
		wr.write("");
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(MMSConfiguration.logging)System.out.println("\nSending 'GET' request to URL : " + url);
		if(MMSConfiguration.logging)System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		if(MMSConfiguration.logging)System.out.println("response: " + response.toString());
		return new String(response.toString().getBytes(), "utf-8");
	}
	
	
	String sendHttpsPost(String dstMRN, String params, JSONObject headerField) throws Exception {
		return null;
	}
	
	String sendHttpsGet(String dstMRN, String params, JSONObject headerField) throws Exception {
		return null;
	}
	//HJH end
}
