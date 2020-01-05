package com.shoppingcart.model;

import com.shoppingcart.enums.DiscountType;

public class Coupon {
	
	private Double minPurchaseAmount;
	private Double worth;
	private DiscountType discountType;
	
	public Coupon(Double minPurchaseAmount, Double worth, DiscountType discountType) {
		this.minPurchaseAmount = minPurchaseAmount;
		this.worth = worth;
		this.discountType = discountType;
	}
	
	public Double getMinPurchaseAmount() {
		return minPurchaseAmount;
	}
	public void setMinPurchaseAmount(Double minPurchaseAmount) {
		this.minPurchaseAmount = minPurchaseAmount;
	}
	public Double getWorth() {
		return worth;
	}
	public void setWorth(Double worth) {
		this.worth = worth;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	
	
	
}
