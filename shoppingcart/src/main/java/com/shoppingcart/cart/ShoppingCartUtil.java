package com.shoppingcart.cart;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shoppingcart.enums.DiscountType;
import com.shoppingcart.model.Campaign;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;

public class ShoppingCartUtil {

	/**
	 * Get total amount from shopping cart (Price*Quantity)
	 * 
	 * @param shoppingCart ShoppingCart
	 * @return Shopping cart total price
	 * @throws Exception
	 */
	public static double getTotal(ShoppingCart shoppingCart) throws Exception {
		Double totalPrice = 0.0;
		if(shoppingCart != null){
			for(Entry<Product,Integer> entry : shoppingCart.entrySet()){
				totalPrice += entry.getKey().getPrice() * entry.getValue();
			}
		}
		return totalPrice;
	}
	
	/**
	 * Get campaign discount
	 * 
	 * @param shoppingCart Categorized ShoppingCart
	 * @param campaigns Categorized campaign list 
	 * @return Decides which one to use by maximum discount then apply and return value
	 */
	public static double getCampaignDiscount(ShoppingCart shoppingCart, List<Campaign> campaigns) throws Exception {
		Campaign maxRateTypedCampaign = campaigns.stream()
				.filter(campaing -> campaing.getDiscountType().equals(DiscountType.RATE))
				.max(Comparator.comparing(Campaign::getWorth))
				.orElse(new Campaign(0.0)); //Maximum worth of 'rate' typed campaigns discount
		
		Campaign maxAmountTypedCampaign = campaigns.stream()
				.filter(campaing -> campaing.getDiscountType().equals(DiscountType.AMOUNT))
				.max(Comparator.comparing(Campaign::getWorth))
				.orElse(new Campaign(0.0)); //Maximum worth of 'amount' typed campaigns discount
		
		Double categoryPrice = ShoppingCartUtil.getTotal(shoppingCart); // "Cart should apply the maximum amount of discount to the cart"
		if(maxAmountTypedCampaign.getWorth() > (maxRateTypedCampaign.getWorth() * categoryPrice) / 100){ 
			return maxAmountTypedCampaign.getWorth();
		}else{
			return (maxRateTypedCampaign.getWorth() * categoryPrice) / 100;
		}
	}
	
	/**
	 * Get all applicable campaigns which campaign quantity little than limit value 
	 * 
	 * @param campaigns Campaign list
	 * @param quantity Limit value
	 * @return Applicable campaigns
	 */
	public static List<Campaign> getApplicableLimitCampaign(List<Campaign> campaigns, int quantity) throws Exception{
		return campaigns.stream()                
                .filter(campaign -> quantity >= campaign.getQuantity())    
                .collect(Collectors.toList());
	}
	
	/**
	 * The shopping cart customization by category
	 * 
	 * @param shoppingCart Shopping cart
	 * @param parent Is parent care about Boolean.TRUE or Boolean.FALSE
	 * @return Customized shopping cart mapped by category
	 */
	public static HashMap<Category, ShoppingCart> customizeShoppingCartByCategory(ShoppingCart shoppingCart, boolean parent) throws Exception{
		HashMap<Category, ShoppingCart> customizedShoppingCart = new HashMap<Category, ShoppingCart>();
		for (Product product : shoppingCart.keySet()) {
			Category category = product.getCategory();
			if (customizedShoppingCart.containsKey(category)) {
				customizedShoppingCart.get(category).put(product, shoppingCart.get(product));
			} else {
				customizedShoppingCart.put(category, new ShoppingCart(product, shoppingCart.get(product)));
			}
			if(parent){ // Deciding the parent categories will be customized 
				Category parentCategory = product.getCategory().getParentCategory();
				if (parentCategory != null) { 
					if (customizedShoppingCart.containsKey(parentCategory)) {
						customizedShoppingCart.get(parentCategory).put(product, shoppingCart.get(product));
					} else {
						customizedShoppingCart.put(parentCategory, new ShoppingCart(product, shoppingCart.get(product)));
					}
				}
			}
		}
		return customizedShoppingCart;
	}
}
