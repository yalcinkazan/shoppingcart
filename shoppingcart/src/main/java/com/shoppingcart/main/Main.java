package com.shoppingcart.main;

import com.shoppingcart.cart.ShoppingCart;
import com.shoppingcart.delivery.DeliveryCostCalculator;
import com.shoppingcart.delivery.DeliveryCostType;
import com.shoppingcart.enums.DiscountType;
import com.shoppingcart.model.Campaign;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Coupon;
import com.shoppingcart.model.Product;

public class Main {

	public static void main(String[] args){
		
		Category parentCategory = new Category("Items");
		Category childCagetoryFood = new Category("Food", parentCategory);
		Category childCagetoryClothing = new Category("Clothing", parentCategory);
		
		Product apple = new Product("Apple", 100.0, childCagetoryFood);
		Product almond = new Product("Almond", 200.0, childCagetoryFood);
		Product tie = new Product("Tie", 250.0, childCagetoryClothing);
		
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addItem(apple, 3);
		shoppingCart.addItem(almond, 1);
		shoppingCart.addItem(tie, 2);
		
		Campaign campaign1 = new Campaign(parentCategory, 20.0, 3, DiscountType.RATE);
		Campaign campaign2 = new Campaign(parentCategory, 50.0, 5, DiscountType.RATE);
		Campaign campaign3 = new Campaign(parentCategory, 5.0, 5, DiscountType.AMOUNT);
		shoppingCart.applyDiscounts(campaign1, campaign2, campaign3);
		
		Coupon coupon = new Coupon(100.0, 10.0, DiscountType.RATE);
		shoppingCart.applyCoupon(coupon);
		
		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2.0, 5.0, DeliveryCostType.FIXED_COST.getValue());
		deliveryCostCalculator.calculateFor(shoppingCart);
		
		shoppingCart.print();
	}
}
