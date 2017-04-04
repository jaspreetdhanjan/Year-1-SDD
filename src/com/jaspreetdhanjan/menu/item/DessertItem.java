package com.jaspreetdhanjan.menu.item;

import com.jaspreetdhanjan.util.MenuIcon;

public class DessertItem extends MenuItem {
	public DessertItem(String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image) {
		super(DESSERT_ITEM, name, cost, calories, isVegetarian, isGlutenFree, image);
	}
}