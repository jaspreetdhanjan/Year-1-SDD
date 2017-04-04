package com.jaspreetdhanjan.menu.item;

import com.jaspreetdhanjan.util.MenuIcon;

public class MainItem extends MenuItem {
	public MainItem(String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image) {
		super(MAIN_ITEM, name, cost, calories, isVegetarian, isGlutenFree, image);
	}
}