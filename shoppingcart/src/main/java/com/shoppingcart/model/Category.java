package com.shoppingcart.model;

import java.util.Comparator;

public class Category implements Comparator<Category>{
	
	private String title;
	private Category parentCategory;

	public Category(String title) {
		if(title == null){
			throw new IllegalArgumentException("Please check informations");
		}
		this.title = title;
	}
	
	public Category(String title, Category parentCategory) {
		if(title == null){
			throw new IllegalArgumentException("Please check informations");
		}
		this.title = title;
		this.parentCategory = parentCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Override
	public int compare(Category o1, Category o2) {
		return o1.getTitle().compareTo(o2.getTitle());
		
	}
	
}
