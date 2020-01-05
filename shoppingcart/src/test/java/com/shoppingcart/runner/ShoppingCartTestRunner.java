package com.shoppingcart.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.shoppingcart.cart.ShoppingCartTest;

public class ShoppingCartTestRunner {
	
   public static void main(String[] args) {
	   
      Result result = JUnitCore.runClasses(ShoppingCartTest.class);
		
      if(result.wasSuccessful()){
    	  System.out.println("Shopping Cart Test Result Was Successful");
      }else{
    	  System.err.println("Shopping Cart Test Result Was Failure!");
    	  for (Failure failure : result.getFailures()) {
	         System.out.println("Failure: " + failure.toString());
	      }
      }
   }
   
} 