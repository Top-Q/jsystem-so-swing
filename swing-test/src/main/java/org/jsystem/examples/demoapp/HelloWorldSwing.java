/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.examples.demoapp;

/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * a Swing example application
 * 
 * @author Nizan Freedman
 *
 */
public class HelloWorldSwing {
	private static JLabel label;
	private static int COUNTER = 0;
    
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setLocation(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));
        //Add the ubiquitous "Hello World" label.
        label = new JLabel("Hello World");
        label.setFont(new Font("sansserif", Font.BOLD, 16));
        JPanel panel2 = new JPanel();
        panel2.add(label);
        panel.add(panel2,BorderLayout.CENTER);
        final JCheckBox box1 = new JCheckBox("checkBox 1");
        final JCheckBox box2 = new JCheckBox("checkBox 2");
        
        JButton button = new JButton("Shalom");
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(""+COUNTER++);
			}
        	
        });
        panel.add(box1);
        panel.add(box2);
        panel.add(button,BorderLayout.SOUTH);
        //frame.getContentPane().add(button);
        frame.getContentPane().add(panel);
        
        JMenu menu = new JMenu("File");
		JMenuItem item1 = new JMenuItem("uncheck all");
		item1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				box1.setSelected(false);
				box2.setSelected(false);
			}
        	
        });
		JMenuItem item2 = new JMenuItem("check all");
		item2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				box1.setSelected(true);
				box2.setSelected(true);
			}
        	
        });
		menu.add(item2);
		menu.add(item1);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		JPanel consolePanel = new JPanel();
		JTextArea text = new JTextArea();
		text.setFont(new Font("sansserif", Font.BOLD, 16));
		text.setPreferredSize(new Dimension(350,50));
		consolePanel.setBorder(BorderFactory.createEtchedBorder());
		consolePanel.add(text);
		consolePanel.setPreferredSize(new Dimension(200,100));
		panel.add(consolePanel);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
