package com.shoppingcart.delivery;

public enum DeliveryCostType {

	    FIXED_COST(2.99);
	 
	    public final Double deliveryCost;
	 
	    private DeliveryCostType(Double deliveryCost) {
	        this.deliveryCost = deliveryCost;
	    }
	    
	    public Double getValue(){
	    	return this.deliveryCost;
	    }
	
}
