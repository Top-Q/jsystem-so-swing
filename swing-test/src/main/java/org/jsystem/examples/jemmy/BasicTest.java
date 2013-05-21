/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.examples.jemmy;

import jsystem.framework.fixture.RootFixture;
import junit.framework.SystemTestCase4;

import org.jsystem.environment.EnvironmentController;
import org.jsystem.objects.clients.ExampleClient;
import org.junit.Before;

/**
 * a Basic test to be extended by all tests<br>
 * starts the XmlRpc server and starts the application.
 * 
 * @author Nizan Freedman
 *
 */
public class BasicTest extends SystemTestCase4 {

	protected ExampleClient exampleClient;
	
	public BasicTest(){
		setFixture(StartApplicationFixture.class);
		setTearDownFixture(RootFixture.class);
	}
	
	@Before
	public void setUp() throws Exception {
		EnvironmentController env = (EnvironmentController)system.getSystemObject("environment");
		exampleClient = new ExampleClient(env.getConnectionHandler());
	}

}
