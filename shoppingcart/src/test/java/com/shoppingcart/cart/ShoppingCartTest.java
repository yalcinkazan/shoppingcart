package com.shoppingcart.cart;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.Before;
import org.junit.Test;

import com.shoppingcart.cart.ShoppingCart;
import com.shoppingcart.delivery.DeliveryCostCalculator;
import com.shoppingcart.delivery.DeliveryCostType;
import com.shoppingcart.enums.DiscountType;
import com.shoppingcart.model.Campaign;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Coupon;
import com.shoppingcart.model.Product;


public class ShoppingCartTest {
	
    private ShoppingCart testShoppingCart;
    private Category testParentCategory1, testParentCategory2, testChildCategory1, testChildCategory2;
    private Product testProduct1, testProduct2, testProduct3, testProduct4, testProduct5;
    
	@Before
    public void setUp() {
		this.testParentCategory1 = new Category("TestParentCategory1");
		this.testParentCategory2 = new Category("TestParentCategory2");
		this.testChildCategory1 = new Category("TestChildCategory1", this.testParentCategory1);
		this.testChildCategory2 = new Category("TestChildCategory2", this.testParentCategory1);
		
		this.testProduct1 = new Product("TestProduct1", 100.0, this.testChildCategory1);
		this.testProduct2 = new Product("TestProduct2", 200.0, this.testChildCategory1);
		this.testProduct3 = new Product("TestProduct3", 300.0, this.testChildCategory1);
		this.testProduct4 = new Product("TestProduct4", 400.0, this.testChildCategory2);
		this.testProduct5 = new Product("TestProduct5", 500.0, this.testParentCategory2);
		
		this.testShoppingCart = new ShoppingCart();
		
    }
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfProductQuantityIsLittleThanOneTest() {
	    this.testShoppingCart.addItem(this.testProduct1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfProductQuantityIsNullTest() {
	    this.testShoppingCart.addItem(this.testProduct1, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfProductIsNullTest() {
	    this.testShoppingCart.addItem(null, 5);
	}
	
	@Test
	  public void addProdutToShoppingCartTest() {
	    this.testShoppingCart.addItem(this.testProduct1, 1);
	    
	    assertEquals(1, this.testShoppingCart.get(this.testProduct1), 0.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfCategoryTıtleIsNull() {
		new Category(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfCategoryTıtleAndParentCategoryIsNull() {
		new Category(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	  public void throwsIllegalArgumentExceptionIfOnlyCategoryTıtleIsNull() {
		new Category(null, this.testParentCategory1);
	}
	
	@Test() 
    public void categoryWithoutParentTest() {
		Category category = new Category("Category");
		
		assertNull(category.getParentCategory());
    }
	
	@Test() 
    public void categoryWithParentTest() {
		Category category = new Category("Category", this.testParentCategory1);
		
		assertSame(this.testParentCategory1, category.getParentCategory());
    }
	
	@Test() 
    public void campaignCategoryTest() {
		Campaign campaign = new Campaign(this.testParentCategory1, 20.0, 3, DiscountType.RATE);
		
		assertSame(this.testParentCategory1, campaign.getCategory());
    }
	
	
	@Test() 
    public void shoppingCartGetCampaignDiscountAmountByRateTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 20.0, 1, DiscountType.RATE);
		
		this.testShoppingCart.applyDiscounts(campaign);
		
		assertEquals(20.0, this.testShoppingCart.getCampaignDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartGetCampaignDiscountAmountByAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign);
		
		assertEquals(10.0, this.testShoppingCart.getCampaignDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartApplyCampaingDiscountByRateThenGetTotalAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 20.0, 1, DiscountType.RATE);
		
		this.testShoppingCart.applyDiscounts(campaign);
		
		assertEquals(80.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);
    }
	
	@Test() 
    public void shoppingCartApplyCampaingDiscountByAmountThenGetTotalAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign);
		
		assertEquals(90.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);
    }
	
	@Test() 
    public void shoppingCartGetCouponDiscountAmountByRateTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(50.0, 10.0, DiscountType.RATE);
		
		this.testShoppingCart.applyDiscounts(campaign);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(9.0, this.testShoppingCart.getCouponDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartGetCouponDiscountAmountByAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(50.0, 10.0, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(10.0, this.testShoppingCart.getCouponDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartApplyCouponDiscountByRateThenGetTotalAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(50.0, 10.0, DiscountType.RATE);
		
		this.testShoppingCart.applyDiscounts(campaign);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(81.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);		        
    }
	
	@Test() 
    public void shoppingCartApplyCouponDiscountByAmountThenGetTotalAmountTest() {
		this.testShoppingCart.addItem(this.testProduct1, 1);
		Campaign campaign = new Campaign(this.testChildCategory1, 10.0, 1, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(50.0, 10.0, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(80.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);
    }
	
	@Test() 
    public void shoppingCartGetTotalAmountAfterDiscountsBySingleCategoryTest() {
		this.testShoppingCart.addItem(this.testProduct1, 7);
		this.testShoppingCart.addItem(this.testProduct2, 2);
		this.testShoppingCart.addItem(this.testProduct3, 3);
		Campaign campaign1 = new Campaign(this.testChildCategory1, 10.0, 5, DiscountType.RATE);
		Campaign campaign2 = new Campaign(this.testChildCategory1, 20.0, 7, DiscountType.RATE);
		Campaign campaign3 = new Campaign(this.testChildCategory1, 100.0, 5, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign1, campaign2, campaign3);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(1500.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartGetTotalAmountAfterDiscountsByMultipleCategoryTest() {
		this.testShoppingCart.addItem(this.testProduct1, 7);
		this.testShoppingCart.addItem(this.testProduct2, 1);
		this.testShoppingCart.addItem(this.testProduct3, 3);
		this.testShoppingCart.addItem(this.testProduct4, 5);
		this.testShoppingCart.addItem(this.testProduct5, 2);
		Campaign campaign1 = new Campaign(this.testChildCategory1, 10.0, 5, DiscountType.RATE);
		Campaign campaign2 = new Campaign(this.testChildCategory2, 20.0, 5, DiscountType.RATE);
		Campaign campaign3 = new Campaign(this.testChildCategory1, 100.0, 5, DiscountType.AMOUNT);
		Campaign campaign4 = new Campaign(this.testParentCategory2, 20.0, 2, DiscountType.RATE);
		Campaign campaign5 = new Campaign(this.testParentCategory2, 180.0, 2, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);
		
		this.testShoppingCart.applyDiscounts(campaign1, campaign2, campaign3, campaign4, campaign5);
		this.testShoppingCart.applyCoupon(coupon);
		
		assertEquals(3920.0, this.testShoppingCart.getTotalAmountAfterDiscount(), 0.0);
	}
	
	@Test() 
    public void shoppingCartGetDeliveryCostBySingleCategoryTest() {
		this.testShoppingCart.addItem(this.testProduct1, 7);
		this.testShoppingCart.addItem(this.testProduct2, 2);
		this.testShoppingCart.addItem(this.testProduct3, 3);
		Campaign campaign1 = new Campaign(this.testChildCategory1, 10.0, 5, DiscountType.RATE);
		Campaign campaign2 = new Campaign(this.testChildCategory1, 20.0, 7, DiscountType.RATE);
		Campaign campaign3 = new Campaign(this.testChildCategory1, 100.0, 5, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);
		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5.0, 4.0, DeliveryCostType.FIXED_COST.getValue());
		
		this.testShoppingCart.applyDiscounts(campaign1, campaign2, campaign3);
		this.testShoppingCart.applyCoupon(coupon);
		deliveryCostCalculator.calculateFor(this.testShoppingCart);
		
		DecimalFormat formatter = new DecimalFormat("#0.00");     
		assertEquals(formatter.format(19.99), formatter.format(this.testShoppingCart.getDeliveryCost()));
	}
	
	@Test() 
    public void shoppingCartGetDeliveryCostByMultipleCategoryTest() {
		this.testShoppingCart.addItem(this.testProduct1, 7);
		this.testShoppingCart.addItem(this.testProduct2, 1);
		this.testShoppingCart.addItem(this.testProduct3, 3);
		this.testShoppingCart.addItem(this.testProduct4, 5);
		this.testShoppingCart.addItem(this.testProduct5, 2);
		Campaign campaign1 = new Campaign(this.testChildCategory1, 10.0, 5, DiscountType.RATE);
		Campaign campaign2 = new Campaign(this.testChildCategory2, 20.0, 5, DiscountType.RATE);
		Campaign campaign3 = new Campaign(this.testChildCategory1, 100.0, 5, DiscountType.AMOUNT);
		Campaign campaign4 = new Campaign(this.testParentCategory2, 20.0, 2, DiscountType.RATE);
		Campaign campaign5 = new Campaign(this.testParentCategory2, 180.0, 2, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);
		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5.0, 4.0, DeliveryCostType.FIXED_COST.getValue());
		DecimalFormat formatter = new DecimalFormat("#0.00");  
		
		this.testShoppingCart.applyDiscounts(campaign1, campaign2, campaign3, campaign4, campaign5);
		this.testShoppingCart.applyCoupon(coupon);
		deliveryCostCalculator.calculateFor(this.testShoppingCart);
		
		assertEquals(formatter.format(37.99), formatter.format(this.testShoppingCart.getDeliveryCost()));
	}
	
	@Test() 
    public void printTest() {
		this.testShoppingCart.addItem(this.testProduct1, 5);
		this.testShoppingCart.addItem(this.testProduct2, 2);
		this.testShoppingCart.addItem(this.testProduct3, 3);
		Campaign campaign1 = new Campaign(this.testChildCategory1, 10.0, 5, DiscountType.RATE);
		Campaign campaign2 = new Campaign(this.testChildCategory1, 20.0, 7, DiscountType.RATE);
		Campaign campaign3 = new Campaign(this.testChildCategory1, 100.0, 5, DiscountType.AMOUNT);
		Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);
		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5.0, 4.0, DeliveryCostType.FIXED_COST.getValue());
		StringBuffer testLogger = new StringBuffer();
		testLogger.append("Category : TestChildCategory1" + System.lineSeparator());
		testLogger.append(" TestProduct3 3*300.0 -> 900.0" + System.lineSeparator());
		testLogger.append(" TestProduct2 2*200.0 -> 400.0" + System.lineSeparator());
		testLogger.append(" TestProduct1 5*100.0 -> 500.0" + System.lineSeparator());
		testLogger.append("-------------------" + System.lineSeparator());
		testLogger.append("Price: 1800.0" + System.lineSeparator());
		testLogger.append("Campaign Discounts: 360.0" + System.lineSeparator());
		testLogger.append("Coupon Discounts: 100.0" + System.lineSeparator());
		testLogger.append("After Discounts Price: 1340.0" + System.lineSeparator());
		testLogger.append("-------------------" + System.lineSeparator());
		testLogger.append("Delivery Cost : 19.99" + System.lineSeparator());
		testLogger.append("Total: 1359.99");
		
		this.testShoppingCart.applyDiscounts(campaign1, campaign2, campaign3);
		this.testShoppingCart.applyCoupon(coupon);
		deliveryCostCalculator.calculateFor(this.testShoppingCart);
		
		assertEquals(testLogger.toString(), this.testShoppingCart.print());
		
	}

}
