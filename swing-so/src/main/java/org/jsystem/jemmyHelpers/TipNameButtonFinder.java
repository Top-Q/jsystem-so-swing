/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.jemmyHelpers;

import java.awt.Component;

import javax.swing.JButton;

import org.netbeans.jemmy.operators.JButtonOperator.JButtonFinder;

/**
 * an example of a Component Finder that finds a JButton by either it's text or tooltip 
 * 
 * @author Nizan Freedman
 *
 */
public class TipNameButtonFinder extends JButtonFinder{
	
	private String toFind;
	
	public TipNameButtonFinder(String toFind){
		this.toFind = toFind;
	}
	
	public boolean checkComponent(Component comp){
		if (comp instanceof JButton){
			String toolTipStrin = ((JButton)comp).getToolTipText();
			String name = ((JButton)comp).getText();
			if(name != null && !name.equals("")){
				if(name.indexOf(toFind) >= 0){
					return true;
				}
			}
			if(toolTipStrin != null){
				if(toolTipStrin.indexOf(toFind) >= 0){
					return true;
				}
			}
		}
		return false;
	}
	
	public String getDescription(){
		return "a button that the name or tooltip is: \'" + toFind +"\'";
	}

}