/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.xmlrpc;

import java.util.logging.Logger;
import jsystem.utils.StringUtils;
import org.apache.xmlrpc.WebServer;
import org.jsystem.objects.handlers.HandlerBasic;
import org.jsystem.objects.handlers.HandlersList;

/**
 * server side Process<br>
 * creates the WebServer, registers the handlers to it
 * 
 * 
 * @author Nizan Freedman
 *
 */
public abstract class XmlRpcServer {
	static private int port = 8999;
	static private WebServer webServer;
	
	private static Logger log = Logger.getLogger(XmlRpcServer.class.getName());
	
	public static void main(String[] args) throws Exception{
		if (args != null && args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		String handlersHolder = args[1];
		webServer = new WebServer(port);
//		register handlers in server
		Class c = Class.forName(handlersHolder);
		
		HandlersList list = (HandlersList) c.newInstance();
		
		HandlerBasic[] handlers = list.getHandlersList();
		for (HandlerBasic handler : handlers){
			webServer.addHandler(handler.getName(),handler);
		}
		webServer.start();
		System.out.println("Web server started. Listening on port " + port);
	}
	
	/**
	 * close the web server connection
	 * kill the java process of JServer
	 * @return
	 * @throws Exception
	 */
	public static int exit() throws Exception {
		(new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
					if (webServer != null) {
						try {
							webServer.shutdown();
						} catch (Exception ignore) {
							// ignore
						}
					}
				} catch (InterruptedException e) {
					log.warning("Problem shutting down WebServer! "+StringUtils.getStackTrace(e));
				}
				try {
					System.exit(0);
				} catch (Throwable t) {
					// ignore
				}
			}
		}).start();
		return 0;
	}
}
