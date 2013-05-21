/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.examples.jemmy;

import jsystem.framework.fixture.Fixture;

import org.jsystem.environment.EnvironmentController;
import org.jsystem.objects.clients.ExampleClient;

public class StartApplicationFixture extends Fixture {

	protected ExampleClient exampleClient;
	private EnvironmentController env;
	
	public void setUp() throws Exception{
		env = (EnvironmentController)system.getSystemObject("environment");
		env.startXmlRpcServer();
		
		exampleClient = new ExampleClient(env.getConnectionHandler());
		exampleClient.startApplication();
	}
	
	
	public void tearDown() throws Exception{
		exampleClient.closeApp();
	}
	
}
