package com.shoppingcart.cart;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.shoppingcart.model.Campaign;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Coupon;
import com.shoppingcart.model.Product;

public final class ShoppingCart extends Cart{

	private static final long serialVersionUID = 1L;
	
	private double totalPriceBeforeDiscounts = 0.0;
	private double campaignDiscount = 0.0;
	private double couponDiscount = 0.0;
	private double deliveryCost = 0.0;
	
	
	public ShoppingCart() { }
	
	public ShoppingCart(Product product, Integer quantity) {
		this.addItem(product, quantity);
	}

	@Override
	public void applyDiscounts(Campaign... campaigns) {
		try{
			this.totalPriceBeforeDiscounts = ShoppingCartUtil.getTotal(this);
			
			List<Category> uniqueCampaignCategories = Arrays.asList(campaigns).stream()
											              .map(Campaign::getCategory)
											              .distinct()
											              .collect(Collectors.toList()); 
			
			HashMap<Category, ShoppingCart> customizedShoppingCart = ShoppingCartUtil.customizeShoppingCartByCategory(this, Boolean.TRUE);
			double discount = 0.0;
			for(Category category : uniqueCampaignCategories){ // "You can apply discounts to a category"
				ShoppingCart affectedShoppingCart = customizedShoppingCart.get(category); // Get affected ShoppingCart by category
				
				List<Campaign> filteredCampaigns = Arrays.asList(campaigns).stream().filter(campaign -> 
												campaign.getCategory().equals(category)).collect(Collectors.toList()); //Campaigns filter by category
				
				discount += ShoppingCartUtil.getCampaignDiscount(affectedShoppingCart,
									ShoppingCartUtil.getApplicableLimitCampaign(filteredCampaigns, affectedShoppingCart.values().stream().reduce(0, Integer::sum)));
			}
			this.campaignDiscount = discount;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void applyCoupon(Coupon coupon) {
		try{
			if((this.totalPriceBeforeDiscounts - this.campaignDiscount) >= coupon.getMinPurchaseAmount()){ // "Campaign discounts are applied first, then coupons"
				switch (coupon.getDiscountType()) {
					case RATE:
							this.couponDiscount = (coupon.getWorth() * (this.totalPriceBeforeDiscounts - this.campaignDiscount)) / 100;
						break;
					case AMOUNT:
							this.couponDiscount = coupon.getWorth();
						break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public double getTotalAmountAfterDiscount() {
		try{
			return ShoppingCartUtil.getTotal(this) - (this.campaignDiscount + this.couponDiscount); 
		}catch(Exception e){
			e.printStackTrace();
			return 0.0;
		}
	}

	@Override
	public double getCouponDiscount() {
		return this.couponDiscount;
	}

	@Override
	public double getCampaignDiscount() {
		return this.campaignDiscount;
	}

	@Override
	public double getDeliveryCost() {
		return this.deliveryCost;
	}
	
	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	@Override
	public String print() {
		StringBuffer logger = new StringBuffer();
		DecimalFormat formatter = new DecimalFormat("#0.00");
		try{
			for(Map.Entry<Category, ShoppingCart> entry : ShoppingCartUtil.customizeShoppingCartByCategory(this, Boolean.FALSE).entrySet()){
				logger.append("Category : " + entry.getKey().getTitle() + System.lineSeparator());
				for(Product product : entry.getValue().keySet().stream()
												.sorted(Comparator.comparing(Product::getPrice).reversed())
												.collect(Collectors.toList()))// Sort Product By Price
					{
					logger.append(" " + product.getName() + " " + entry.getValue().get(product) + "*" + product.getPrice() + " -> " + product.getPrice() * entry.getValue().get(product) + System.lineSeparator());
				}
			}
			logger.append("-------------------" + System.lineSeparator());
			logger.append("Price: " + this.totalPriceBeforeDiscounts + System.lineSeparator());
			logger.append("Campaign Discounts: " + this.campaignDiscount + System.lineSeparator());
			logger.append("Coupon Discounts: " + this.couponDiscount + System.lineSeparator());
			logger.append("After Discounts Price: " + (this.totalPriceBeforeDiscounts - (this.campaignDiscount + this.couponDiscount)) + System.lineSeparator());
			logger.append("-------------------" + System.lineSeparator());
			logger.append("Delivery Cost : " + formatter.format(this.deliveryCost) + System.lineSeparator()); 
			logger.append("Total: " + (this.totalPriceBeforeDiscounts - (this.campaignDiscount + this.couponDiscount) + this.deliveryCost));
			System.out.println(logger);
		}catch(Exception e){
			e.printStackTrace();
		}
		return logger.toString();
	}


	


	
}
