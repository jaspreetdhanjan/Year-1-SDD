package com.jaspreetdhanjan.screen;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jaspreetdhanjan.options.*;

public class OptionsScreen extends Screen {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 450; //600-150
	private static final String TITLE = BUSINESS_NAME + " Options";

	public OptionsScreen() {
		super(TITLE, WIDTH, HEIGHT);
		cp.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Add Menu Item", new AddPanel());

		RemovePanel removeCard = new RemovePanel();
		tabbedPane.addTab("Remove Menu Item", removeCard);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if (index == 1) {
					// Reload the menu if the state has been changed.
					removeCard.loadAll();
				}
			}
		});

		cp.add(tabbedPane, BorderLayout.CENTER);
		createWindow();
	}
}