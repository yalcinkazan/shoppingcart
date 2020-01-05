package com.shoppingcart.cart;

import java.util.HashMap;

import com.shoppingcart.model.Campaign;
import com.shoppingcart.model.Coupon;
import com.shoppingcart.model.Product;

public abstract class Cart extends HashMap<Product, Integer>{

	private static final long serialVersionUID = 1L;
	
	
	public void addItem(Product product, Integer quantity) {
		if (product == null || quantity == null || quantity <= 0){
			 throw new IllegalArgumentException("Please check informations");
		}
		if(this.containsKey(product)) {
			quantity += this.get(product);
		}
		this.put(product, quantity);
	}
	
	public abstract void applyDiscounts(Campaign... args);
	
	public abstract void applyCoupon(Coupon coupon);
	
	public abstract double getTotalAmountAfterDiscount();
	
	public abstract double getCouponDiscount();
	
	public abstract double getCampaignDiscount();
	
	public abstract double getDeliveryCost();
	
	public abstract String print();	
	
}
