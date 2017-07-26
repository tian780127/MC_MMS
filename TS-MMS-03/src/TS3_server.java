import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.ac.kaist.mms_client.MMSClientHandler;
import kr.ac.kaist.mms_client.MMSConfiguration;

/** 
File name : TS3_server.java
	Sending messages to a client which is 
Author : Jin Jeong (jungst0001@kaist.ac.kr)
Creation Date : 2017-07-26
*/

public class TS3_server {
	public static void main(String[] args) throws Exception{
		String myMRN = "urn:mrn:imo:imo-no:ts-mms-03-server";
		String dstMRN = "urn:mrn:imo:imo-no:ts-mms-03-client";

		MMSConfiguration.MMS_URL="127.0.0.1:8088";
		
		MMSClientHandler sender = new MMSClientHandler(myMRN);
		sender.setSender(new MMSClientHandler.ResponseCallback() {
			
			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
//				System.out.println("Client Side");
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());
				}
				System.out.println();
			}
		});
		
		
		String data = null;
		
		data = createDataSize(40*1024*1024);
		sender.sendPostMsg(dstMRN, data);
	}
	
	public static String createDataSize(int size){
		StringBuilder data = new StringBuilder(size);
		
		for(int i = 0 ; i < size; i ++){
			data.append('a');
		}
		
		return data.toString();
	}
}
