package com.jaspreetdhanjan.menu.item;

import com.jaspreetdhanjan.util.MenuIcon;

public class StarterItem extends MenuItem {
	public StarterItem(String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image) {
		super(STARTER_ITEM, name, cost, calories, isVegetarian, isGlutenFree, image);
	}
}