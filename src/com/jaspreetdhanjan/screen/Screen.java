package com.jaspreetdhanjan.screen;

import java.awt.*;

import javax.swing.JFrame;

import com.apple.eawt.Application;
import com.jaspreetdhanjan.util.ResourceLoader;

public class Screen extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String BUSINESS_NAME = "Frank's Pizzeria";

	protected final Container cp;
	private final String title;
	private final int width, height;

	public Screen(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		cp = getContentPane();
	}

	protected void addComponent(Component... components) {
		for (int i = 0; i < components.length; i++) {
			cp.add(components[i]);
		}
	}

	protected void createWindow() {
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle(title);
		setVisible(true);
		setIconImage(ResourceLoader.loadImage("/img/icon.png"));
		Application.getApplication().setDockIconImage(getIconImage());
	}

	public void setScreen(Screen screen) {
		this.dispose();
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}