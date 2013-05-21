/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.handlers;

import org.jsystem.examples.demoapp.HelloWorldSwing;
import org.jsystem.jemmyHelpers.TipNameButtonFinder;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;

/**
 * an example of a server side Xml-Rpc handler
 * 
 * @author Nizan Freedman
 *
 */
public class ExampleHandler extends HandlerBasic{

	public int launch() throws Exception {
		new ClassReference(HelloWorldSwing.class.getName()).startApplication();
		mainFrame = new JFrameOperator(mainFrameTitle);
		console = new JTextAreaOperator(mainFrame);
		
		return 0;
	}
	
	public int pressOnButton() throws Exception {
		printToTextArea("Press \"Shalom\" button");
		new JButtonOperator(mainFrame, new TipNameButtonFinder("Shalom")).push();
		
		return 0;
	}
	
	public int selectCheckBox(int number,boolean check) throws Exception {
		String checkString = check? "Check" : "Uncheck";
		printToTextArea(checkString + " CheckBox " + number);
		JCheckBoxOperator checkBox = new JCheckBoxOperator(mainFrame,number - 1);
		checkBox.setSelected(check);
		
		return 0;
	}
	
	public int selectMenuItem(String menuName,String itemName) throws Exception {
		printToTextArea("Select menuItem : " + menuName + " --> " + itemName);
		JMenuOperator menuOperator = new JMenuOperator(mainFrame,menuName);
		JMenuItemOperator itemOperator = menuOperator.showMenuItem(itemName);
		itemOperator.clickMouse();
		
		return 0;
	}
	
	public int closeApp() throws InterruptedException{
		printToTextArea("Close Application");
		Thread.sleep(1000);
		mainFrame.close();
		
		return 0;
	}

}
