package com.shoppingcart.delivery;

import com.shoppingcart.cart.Cart;

public interface Delivery {
	
	public <T extends Cart> void calculateFor(T cart);
}
