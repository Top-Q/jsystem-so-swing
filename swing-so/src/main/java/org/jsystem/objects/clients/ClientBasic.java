/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.clients;

import java.util.Vector;

import org.jsystem.objects.xmlrpc.XmlRpcHelper;

import jsystem.framework.system.SystemObjectImpl;

/**
 * a base client to be extended by all classes<br>
 * holds basic method for sending Xml-Rpc commands
 * 
 * @author Nizan Freedman
 *
 */
public abstract class ClientBasic extends SystemObjectImpl {

	private XmlRpcHelper xmlHelper; 

	protected abstract String getHandlerName();
	
	protected ClientBasic(XmlRpcHelper connectionHandler){
		this.xmlHelper = connectionHandler;
	}
	
	protected Object handleCommand(String title, String command, Object...params) throws Exception{
		return xmlHelper.handleXmlCommand(getHandlerName(),title, command, params);
	}
	
	protected Object handleCommand(String title, String command, Vector<Object> params) throws Exception{
		return xmlHelper.handleXmlCommand(getHandlerName(),title, command, params);
	}
	
}
