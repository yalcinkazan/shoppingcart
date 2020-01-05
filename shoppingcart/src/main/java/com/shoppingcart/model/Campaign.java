package com.shoppingcart.model;

import com.shoppingcart.enums.DiscountType;

public class Campaign {
	
	private Category category;
	private Double worth;
	private Integer quantity;
	private DiscountType discountType;
	
	public Campaign(Category category, Double worth, Integer quantity, DiscountType discountType) {
		this.category = category;
		this.worth = worth;
		this.quantity = quantity;
		this.discountType = discountType;
	}
	
	public Campaign(Double worth){
		this.worth = worth;
	}
	
	public boolean isRateDiscount(Campaign campaign){
		return campaign.getDiscountType().equals(DiscountType.RATE);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	
	
	
}
