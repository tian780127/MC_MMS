import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.ac.kaist.mms_client.MMSClientHandler;
import kr.ac.kaist.mms_client.MMSConfiguration;

/** 
File name : TS3_client.java
	Sending polling request to MMS for getting messages
Author : Jin Jeong (jungst0001@kaist.ac.kr)
Creation Date : 2017-07-26
*/

public class TS3_client {
	public static void main(String[] args) throws Exception{
		String myMRN = "urn:mrn:imo:imo-no:ts-mms-03-client";

		MMSConfiguration.MMS_URL="10.0.11.1:8088";
		
		String dstMRN = "urn:mrn:smart-navi:device:mms1";
		String svcMRN = "urn:mrn:imo:imo-no:ts-mms-03-server";
		
		MMSClientHandler client = new MMSClientHandler(myMRN);
		client.startPolling(dstMRN, svcMRN, 1, new MMSClientHandler.PollingResponseCallback() {
			
			@Override
			public void callbackMethod(Map<String, List<String>> headerField, String message) {
				// TODO Auto-generated method stub
				Iterator<String> iter = headerField.keySet().iterator();
				while (iter.hasNext()){
					String key = iter.next();
					System.out.println(key+":"+headerField.get(key).toString());
				}
				System.out.println();
			}
		});
	}
	

}
