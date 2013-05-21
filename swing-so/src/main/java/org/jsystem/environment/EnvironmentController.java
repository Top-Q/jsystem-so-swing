/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.logging.Logger;

import jsystem.framework.system.SystemObjectImpl;
import jsystem.utils.StringUtils;

import org.jsystem.objects.xmlrpc.XmlRpcHelper;

/**
 * Xml-Rpc Environment Definitions. this class is responsible of creating the
 * correct classPath, locating a free port and starting the xml-rpc server
 * Process.
 * 
 * @author Nizan Freedman
 * 
 */
abstract public class EnvironmentController extends SystemObjectImpl {
	private static Logger log = Logger.getLogger(EnvironmentController.class.getName());
	private Process p;
	private String applicationClassPath = "classes";
	private String serverClass = "org.jsystem.objects.xmlrpc.XmlRpcServer";
	private String handlersListObject;
	private int portNumber = -1;
	private char classPathSeperator = File.pathSeparatorChar;
	private XmlRpcHelper connectionHandler;
	private String javaExecutable = "java";

	/**
	 * create the classPath String
	 * 
	 * @return
	 */
	protected String buildClassPath() {
		String runnerRoot = System.getenv("RUNNER_ROOT");
		StringBuffer sb = new StringBuffer();
		sb.append(getApplicationClassPath() + classPathSeperator);
		sb.append(runnerRoot + "/lib/swing.jar" + classPathSeperator);
		sb.append(runnerRoot + "/lib/jsystemCore.jar" + classPathSeperator);
		sb.append(runnerRoot + "/thirdparty/lib/xmlrpc-2.0.jar" + classPathSeperator);
		sb.append(runnerRoot + "/thirdparty/lib/jemmy.jar" + classPathSeperator);
		sb.append(runnerRoot + "/thirdparty/commonLib/commons-codec-1.3.jar");
		return sb.toString();
	}

	/**
	 * Locate a port, gather classPath, update Environment variables and start
	 * the XmlRpcServer Process
	 * 
	 * @throws Exception
	 */
	public void startXmlRpcServer() throws Exception {
		String classPath = buildClassPath();
		log.info(classPath);
		preExecutionParametersToUpdate();
		ServerSocket ss = new ServerSocket(0);
		int foundPort = portNumber < 0 ? ss.getLocalPort() : portNumber;
		ss.close();
		String processCommand = getJavaExecutable() + " " + getJVMArguments() + " -classpath " + classPath + " "
				+ getServerClass() + " " + foundPort + " " + getHandlersListObject();
		report.report(processCommand);
		p = Runtime.getRuntime().exec(processCommand, null, getApplicationRootDirectory());
		(new BufferReader(p.getInputStream(), "out.txt")).start();
		(new BufferReader(p.getErrorStream(), "error.txt")).start();
		Thread.sleep(5000);
		report.report("Xml-Rpc Server started on port " + foundPort);
		connectionHandler = new XmlRpcHelper(foundPort);
		postExecutionParametersToUpdate();
	}

	/**
	 * This method was designed for overriding. This will allow the inherit
	 * class to set the JVM arguments that will be used in the XML-RPC server.
	 * For example, remote debugging parameters.
	 * 
	 * @return JVM arguments
	 */
	protected String getJVMArguments() throws Exception {
		return "";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract File getApplicationRootDirectory() throws Exception;

	/**
	 * destroy the XmlRpcServer Process
	 * 
	 * @throws Exception
	 */
	public void stopXmlRpcServer() throws Exception {
		p.destroy();
	}

	/**
	 * all Environment parameters that are needed to be configured <B>BEFORE</B>
	 * executing the Xml-Rpc Server should be added here
	 */
	protected void preExecutionParametersToUpdate() {
		report.report("Setting up pre Execution environment parameters");
	}

	/**
	 * all Environment parameters that are needed to be configured <B>AFTER</B>
	 * executing the Xml-Rpc Server should be added here
	 */
	protected void postExecutionParametersToUpdate() {
		report.report("Setting up post Execution environment parameters");
	}

	protected XmlRpcHelper getXmlRpcHelper() {
		return connectionHandler;
	}

	/***************************** SUT VARIABLES **************************************/

	public String getApplicationClassPath() {
		return applicationClassPath;
	}

	public void setApplicationClassPath(String applicationClassPath) {
		this.applicationClassPath = applicationClassPath;
	}

	public String getServerClass() {
		return serverClass;
	}

	public void setServerClass(String serverClass) {
		this.serverClass = serverClass;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getHandlersListObject() {
		return handlersListObject;
	}

	public void setHandlersListObject(String handlersListObject) {
		this.handlersListObject = handlersListObject;
	}

	public Process getP() {
		return p;
	}

	public void setP(Process p) {
		this.p = p;
	}

	public XmlRpcHelper getConnectionHandler() {
		return connectionHandler;
	}

	public String getJavaExecutable() {
		return javaExecutable;
	}

	public void setJavaExecutable(String javaExecutable) {
		this.javaExecutable = javaExecutable;
	}
}

/**
 * used for input and error stream
 * 
 * @author Nizan Freedman
 * 
 */
class BufferReader extends Thread {
	InputStream in;
	String outFile;

	private Logger log = Logger.getLogger(BufferReader.class.getName());

	public BufferReader(InputStream in, String outFile) {
		this.in = in;
		this.outFile = outFile;
	}

	public void run() {
		try {
			FileOutputStream fos = new FileOutputStream(outFile);
			int c;
			while ((c = in.read()) >= 0) {
				System.out.print((char) c);
				fos.write(c);
			}
		} catch (Exception e) {
			log.warning("Problem Writing to output buffer " + StringUtils.getStackTrace(e));
		}
	}
}
