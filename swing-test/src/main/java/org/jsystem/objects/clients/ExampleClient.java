/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.clients;

import org.jsystem.objects.handlers.ExampleHandler;
import org.jsystem.objects.xmlrpc.XmlRpcHelper;

/**
 * an example of a Client side object
 * 
 * @author Nizan Freedman
 *
 */
public class ExampleClient extends ClientBasic {
	
	public ExampleClient(XmlRpcHelper connectionHandler){
		super(connectionHandler);
	}
	
	public void startApplication() throws Exception {
		handleCommand("Starting Swing application", "launch");
	}

	public void pressOnButton() throws Exception {
		handleCommand("Pressing on button", "pressOnButton");
	}
	
	public void selectCheckBox(int number,boolean check) throws Exception {
		String checkString = check? "Check" : "Uncheck";
		handleCommand(checkString + " Checkbox "+number,"selectCheckBox",number,check);
	}
	
	public void selectMenuItem(String menuName,String itemName) throws Exception {
		handleCommand("select menu "+menuName + " --> "+itemName, "selectMenuItem" , menuName, itemName);
	}
	
	public void closeApp() throws Exception{
		try {
			handleCommand("Close application", "closeApp");
		}catch (Exception e){
			//Exception is expected since we are shutting the remote application
		}
	}

	@Override
	protected String getHandlerName() {
		return ExampleHandler.class.getSimpleName();
	}
}
