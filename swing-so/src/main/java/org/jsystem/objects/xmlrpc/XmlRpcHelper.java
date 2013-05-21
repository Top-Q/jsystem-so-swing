/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.xmlrpc;

import java.util.Vector;

import jsystem.framework.system.SystemObjectImpl;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcClientException;
import org.apache.xmlrpc.XmlRpcException;

/**
 * Handles all message sending and receiving in the Xml-Rpc structure 
 * 
 * @author Nizan Freedman
 *
 */
public class XmlRpcHelper extends SystemObjectImpl{
	String host = "127.0.0.1";
	private int port = 8082;
	private static final int retryConnectfNumber = 5;
	private static final int sleepDuraionInMilliSeconds = 2000;
	
	
	public XmlRpcHelper(int port){
		this.port = port;
	}
		
	public synchronized Object handleXmlCommand(String handler,String title, String command, Object... objects) throws Exception {
		if(objects.length == 0){
			return handleXmlCommand(handler,title, command, new Vector<Object>());
		}
		Vector<Object> v = new Vector<Object>();
		for (Object object : objects){
			if (object == null){
				v.add("");
			}else{
				v.addElement(object);
			}
		}
		return handleXmlCommand(handler,title, command, v);
	}
	
	/**
	 * transfer xml request to server
	 * 
	 */
	public synchronized Object handleXmlCommand(String handler,String title, String command, Vector<Object> v) throws Exception {
		int looper = 0;
		Object o = null;
		report.report(title);
		report.report("handler is "+handler+" and command is "+command+" arguments are "+ v);
		while(true){
			try{
				if(looper > 0){
					report.report("trying to send xml command for the "+looper+"time");
				}
				o = execute(handler,command, v);
			}catch (Exception e){
				if(e.getMessage().contains("Connection refused")){
					report.report("received connection refused, retrying");
					if(looper < retryConnectfNumber){
						Thread.sleep(sleepDuraionInMilliSeconds);
						looper++;
						continue;
					}
				}
				throw e;
			}
			
			if (o instanceof XmlRpcException) {
				// server exception
	
				throw (XmlRpcException) o;
			}
			
			if(o instanceof XmlRpcClientException){
				throw(XmlRpcClientException)o;
			}
	
			return o;
		}
	}
	
	
	
	/**
	 * creates an XmlRpcClient and calls it's execute method to connect
	 * to a server
	 * @param command
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object execute(String handler,String command, Vector<Object> params)throws Exception{
		try{
			XmlRpcClient client = new XmlRpcClient("http://" + host + ":" + port + "/RPC2");
			return client.execute(handler+"."+command, params);
		}
		catch(Exception e){
			throw e;
		}
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}