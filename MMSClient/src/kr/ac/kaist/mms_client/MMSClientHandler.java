package kr.ac.kaist.mms_client;

/* -------------------------------------------------------- */
/** 
File name : MMSClientHandler.java
	It provides APIs for MMS clients. 
Author : Jaehyun Park (jae519@kaist.ac.kr)
	Haeun Kim (hukim@kaist.ac.kr)
	Jaehee Ha (jaehee.ha@kaist.ac.kr)
Creation Date : 2016-12-03

Rev. history : 2017-02-01
Version : 0.3.01
	Added setting header field features. 
	Added locator registering features.
Modifier : Jaehee Ha (jaehee.ha@kaist.ac.kr)

Rev. history : 2017-02-14
	fixed http get request bugs
	fixed http get file request bugs
	added setting context features
Modifier : Jaehee Ha (jaehee.ha@kaist.ac.kr)

Rev. history : 2017-04-20 
Version : 0.5.0
Modifier : Jaehee Ha (jaehee.ha@kaist.ac.kr)

Rev. history : 2017-04-25
Modifier : Jaehee Ha (jaehee.ha@kaist.ac.kr)

Rev. history : 2017-04-27
Version : 0.5.1
Modifier : Jaehee Ha (jaehee.ha@kaist.ac.kr)
*/
/* -------------------------------------------------------- */

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MMSClientHandler {
	
	private static final String TAG = "[MMSClientHandler] ";
	
	private RcvHandler rcvHandler = null;
	private PollHandler pollHandler = null;
	private SendHandler sendHandler = null;
	private String clientMRN = "";
	private int clientPort = 0;
	private Map<String,String> headerField = null;
	
	public MMSClientHandler(String clientMRN) throws IOException{
		this.clientMRN = clientMRN;
		rcvHandler = null;
		pollHandler = null;
		sendHandler = null;
	}
	
	public interface PollingResponseCallback{
		void callbackMethod(Map<String,List<String>> headerField, String message);
	}
	public interface RequestCallback{
		String respondToClient(Map<String,List<String>> headerField, String message);
		int setResponseCode();
	}
	
	public interface ResponseCallback{
		void callbackMethod(Map<String,List<String>> headerField, String message);
	}
	
	public void startPolling (String dstMRN, String svcMRN, int interval, PollingResponseCallback callback) throws IOException{
		if (this.sendHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done setSender()");
		} else if (this.rcvHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done setServerPort() or setFileServerPort()");
		} else {
			this.pollHandler = new PollHandler(clientMRN, dstMRN, svcMRN, interval, headerField);
			this.pollHandler.ph.setPollingResponseCallback(callback);
			this.pollHandler.ph.start();
		}
	}
	
	private boolean isErrorForSettingServerPort (){
		if (this.sendHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done setSender()");
			return true;
		} else if (this.pollHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done startPolling()");
			return true;
		}
		return false;
	}
	
	public void setServerPort (int port, RequestCallback callback) throws IOException{
		if (!isErrorForSettingServerPort()){
			this.rcvHandler = new RcvHandler(port);
			setPortAndCallback(port, callback);
		}
	}
	
	public void setServerPort (int port, String context, RequestCallback callback) throws IOException{
		if (!isErrorForSettingServerPort()){
			this.rcvHandler = new RcvHandler(port, context);
			setPortAndCallback(port, callback);
		}
	}
	
	public void setFileServerPort (int port, String fileDirectory, String fileName) throws IOException {
		if (!isErrorForSettingServerPort()){
			this.clientPort = port;
			this.rcvHandler = new RcvHandler(port, fileDirectory, fileName);
			registerLocator(port);	
		}
	}
	
	private void setPortAndCallback (int port, RequestCallback callback) {
		this.clientPort = port;
		this.rcvHandler.hrh.setRequestCallback(callback);
		registerLocator(port);	
	}
	
	public void addContext (String context) {
		if(this.rcvHandler != null) {
			this.rcvHandler.addContext(context);
		} else {
			System.out.println(TAG+"Failed! HTTP server is required! Do setServerPort()");
		}
	}
	
	public void addFileContext (String fileDirectory, String fileName) {
		if(this.rcvHandler != null) {
			this.rcvHandler.addFileContext(fileDirectory, fileName);
		} else {
			System.out.println(TAG+"Failed! HTTP file server is required! Do setFileServerPort()");
		}
	}
	
	public void setSender (ResponseCallback callback) {
		if (this.rcvHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done setServerPort()");
		} else if (this.pollHandler != null) {
			System.out.println(TAG+"Failed! MMSClientHandler must have exactly one function! It already has done startPolling()");
		} else {
			this.sendHandler = new SendHandler(clientMRN);
			this.sendHandler.setResponseCallback(callback);
		}
		
	}
	
	@Deprecated
	private void registerLocator(int port){
		try {
			new MMSSndHandler(clientMRN).registerLocator(port);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(MMSConfiguration.LOGGING)e.printStackTrace();
			return;
		}
	}
	
	//HJH
	public void setMsgHeader(Map<String,String> headerField) throws Exception{
		this.headerField = headerField;
	}
	
	public void sendPostMsg(String dstMRN, String loc, String data) throws Exception{
		if (this.sendHandler == null) {
			System.out.println(TAG+"Failed! HTTP client is required! Do setSender()");
		} else {
			this.sendHandler.sendHttpPost(dstMRN, loc, data, headerField);
		}
	}
	
	public void sendPostMsg(String dstMRN, String data) throws Exception{
		if (this.sendHandler == null) {
			System.out.println(TAG+"Failed! HTTP client is required! Do setSender()");
		} else {
			this.sendHandler.sendHttpPost(dstMRN, "", data, headerField);
		}
	}
	
	//HJH
	public void sendGetMsg(String dstMRN) throws Exception{
		if (this.sendHandler == null) {
			System.out.println(TAG+"Failed! HTTP client is required! Do setSender()");
		} else {
			this.sendHandler.sendHttpGet(dstMRN, "", "", headerField);
		}
	}
	
	//HJH
	public void sendGetMsg(String dstMRN, String loc, String params) throws Exception{
		if (this.sendHandler == null) {
			System.out.println(TAG+"Failed! HTTP client is required! Do setSender()");
		} else {
			this.sendHandler.sendHttpGet(dstMRN, loc, params, headerField);
		}
	}
	
	//OONI
	public String requestFile(String dstMRN, String fileName) throws Exception{
		if (this.sendHandler == null) {
			System.out.println(TAG+"Failed! HTTP client is required! Do setSender()");
			return null;
		} else {
			return this.sendHandler.sendHttpGetFile(dstMRN, fileName, headerField);
		}
	}
	
	private class RcvHandler extends MMSRcvHandler{
		RcvHandler(int port) throws IOException {
			super(port);
		}
		RcvHandler(int port, String context) throws IOException {
			super(port, context);
		}
		RcvHandler(int port, String fileDirectory, String fileName) throws IOException {
			super(port, fileDirectory, fileName);
		}
	}
	
	private class SendHandler extends MMSSndHandler{
		SendHandler(String clientMRN) {
			super(clientMRN);
		}
	}
	
	private class PollHandler extends MMSPollHandler{

		PollHandler(String clientMRN, String dstMRN, String svcMRN, int interval, Map<String, String> headerField) throws IOException {
			super(clientMRN, dstMRN, svcMRN, interval, clientPort, 1, headerField);
		}
	}
	

}

