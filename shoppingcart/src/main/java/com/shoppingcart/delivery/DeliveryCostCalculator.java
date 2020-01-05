package com.shoppingcart.delivery;

import com.shoppingcart.cart.Cart;
import com.shoppingcart.cart.ShoppingCart;
import com.shoppingcart.cart.ShoppingCartUtil;

public class DeliveryCostCalculator implements Delivery{
	
	private Double costPerDelivery;
	private Double costPerProduct;
	private Double fixedCost;
	public static final double FIXED_COST = 2.99;
	
	public DeliveryCostCalculator(Double costPerDelivery, Double costPerProduct, Double fixedCost) {
		this.costPerDelivery = costPerDelivery;
		this.costPerProduct = costPerProduct;
		this.fixedCost = fixedCost;
	}

	@Override
	public <T extends Cart> void calculateFor(T cart) {
		try {
			int numberOfDeliveries;
			numberOfDeliveries = ShoppingCartUtil.customizeShoppingCartByCategory((ShoppingCart) cart, Boolean.FALSE).keySet().size();// By distinct Category number
			int numberOfProducts = cart.keySet().size(); // By different product number
			((ShoppingCart) cart).setDeliveryCost((this.costPerDelivery * numberOfDeliveries) + (this.costPerProduct * numberOfProducts) + this.fixedCost);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
