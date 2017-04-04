package com.jaspreetdhanjan.menu;

import java.util.*;

import javax.swing.DefaultListModel;

import com.jaspreetdhanjan.menu.item.*;

public class Menu {
	private List<StarterItem> starterItems = new ArrayList<StarterItem>();
	private List<MainItem> mainItems = new ArrayList<MainItem>();
	private List<DessertItem> dessertItems = new ArrayList<DessertItem>();
	private List<DrinksItem> drinksItems = new ArrayList<DrinksItem>();

	private List<MenuItem> items = new ArrayList<MenuItem>();
	private boolean isDirty = true;

	public void add(MenuItem item) {
		isDirty = true;
		if (item instanceof StarterItem) starterItems.add((StarterItem) item);
		if (item instanceof MainItem) mainItems.add((MainItem) item);
		if (item instanceof DessertItem) dessertItems.add((DessertItem) item);
		if (item instanceof DrinksItem) drinksItems.add((DrinksItem) item);
	}

	public void remove(MenuItem item) {
		isDirty = true;
		if (item instanceof StarterItem) starterItems.remove((StarterItem) item);
		if (item instanceof MainItem) mainItems.remove((MainItem) item);
		if (item instanceof DessertItem) dessertItems.remove((DessertItem) item);
		if (item instanceof DrinksItem) drinksItems.remove((DrinksItem) item);
	}

	/**
	 * Adds items from the menu into the DefaultListModel.
	 * 
	 * @param itemList
	 *            The DefaultListModel to add the items to.
	 * @param sort
	 *            The sort preferences of the items being added. Can be null.
	 */
	public void addItems(DefaultListModel<MenuItem> itemList, MenuSort sort) {
		for (MenuItem item : getItems()) {
			if (sort != null) {
				if (sort.allowGlutenFree() && !item.isGlutenFree()) continue;
				if (sort.allowVegetarian() && !item.isVegetarian()) continue;
			}

			itemList.addElement(item);
		}
	}

	/**
	 * Creates an evenly distributed array of random meals.
	 * 
	 * @param random
	 *            An instance of Random, if seeding is required.
	 * @param amt
	 *            The length of the return array.
	 * @return Array of random menu items.
	 */
	public MenuItem[] getRandomItems(Random random, int amt) {
		MenuItem[] results = new MenuItem[amt];
		for (int i = 0; i < amt; i++) {
			if (i % 1 == 0) results[i] = starterItems.get(random.nextInt(starterItems.size()));
			if (i % 2 == 0) results[i] = mainItems.get(random.nextInt(mainItems.size()));
			if (i % 3 == 0) results[i] = dessertItems.get(random.nextInt(dessertItems.size()));
			if (i % 4 == 0) results[i] = drinksItems.get(random.nextInt(drinksItems.size()));
		}
		return results;
	}

	public List<StarterItem> getStarters() {
		return starterItems;
	}

	public List<MainItem> getMains() {
		return mainItems;
	}

	public List<DessertItem> getDesserts() {
		return dessertItems;
	}

	public List<DrinksItem> getDrinks() {
		return drinksItems;
	}

	public List<MenuItem> getItems() {
		if (isDirty) {
			updateItems();
		}
		return items;
	}

	private void updateItems() {
		isDirty = false;
		items.clear();
		items.addAll(starterItems);
		items.addAll(mainItems);
		items.addAll(dessertItems);
		items.addAll(drinksItems);
	}
}