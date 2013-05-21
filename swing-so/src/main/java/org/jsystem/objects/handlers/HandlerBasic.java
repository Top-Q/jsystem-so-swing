/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.handlers;

import org.jsystem.jemmyHelpers.JemmySupport;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;

public abstract class HandlerBasic {

	protected static String mainFrameTitle = "HelloWorldSwing";
	protected static JFrameOperator mainFrame;
	protected static JTextAreaOperator console;
	protected static JemmySupport jemmySupport = new JemmySupport();
	
	
	protected void printToTextArea(String s){
		console.setText(s);
	}
	
	public String getName(){
		return this.getClass().getSimpleName();
	}
}
