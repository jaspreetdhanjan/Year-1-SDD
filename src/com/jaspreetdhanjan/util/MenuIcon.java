package com.jaspreetdhanjan.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MenuIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;

	private String path;

	public MenuIcon(Image img, String path) {
		super(img);
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}