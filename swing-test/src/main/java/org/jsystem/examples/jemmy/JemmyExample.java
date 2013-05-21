/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.examples.jemmy;

import org.junit.Test;

public class JemmyExample extends BasicTest {
	
	@Test
	public void pushOnButton() throws Exception{
		for (int i=0 ; i<2 ; i++){
			exampleClient.pressOnButton();
			sleep(1000);
		}
		for (int i=1 ; i<3 ; i++){
			exampleClient.selectCheckBox(i, true);
			sleep(1000);
		}
		for (int i=1 ; i<3 ; i++){
			exampleClient.selectCheckBox(i, false);
			sleep(1000);
		}
		
		exampleClient.selectMenuItem("File", "check all");
		sleep(2000);
		exampleClient.selectMenuItem("File", "uncheck all");
		sleep(2000);
	}
}
