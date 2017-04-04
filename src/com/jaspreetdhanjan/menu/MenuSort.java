package com.jaspreetdhanjan.menu;

public class MenuSort {
	private boolean allowGlutenFree;
	private boolean allowVegetarian;

	public MenuSort(boolean allowGlutenFree, boolean allowVegetarian) {
		this.allowGlutenFree = allowGlutenFree;
		this.allowVegetarian = allowVegetarian;
	}

	public boolean allowVegetarian() {
		return allowVegetarian;
	}

	public boolean allowGlutenFree() {
		return allowGlutenFree;
	}
}