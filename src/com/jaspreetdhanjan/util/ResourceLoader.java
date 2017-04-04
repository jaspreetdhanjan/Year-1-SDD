package com.jaspreetdhanjan.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class ResourceLoader {
	private static final boolean VERBOSE = false;

	private static Map<String, BufferedImage> resourceCache = new HashMap<String, BufferedImage>();

	public static BufferedImage loadImage(String filename) {
		if (resourceCache.containsKey(filename)) {
			if (VERBOSE) System.out.println("Loading from cache -> " + filename);
			return resourceCache.get(filename);
		}

		BufferedImage img = null;
		try {
			img = ImageIO.read(ResourceLoader.class.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (VERBOSE) System.out.println("Loading resource from -> " + filename);
		resourceCache.put(filename, img);
		return img;
	}

	public static MenuIcon loadIcon(String path) {
		return new MenuIcon(loadImage(path), path);
	}
}