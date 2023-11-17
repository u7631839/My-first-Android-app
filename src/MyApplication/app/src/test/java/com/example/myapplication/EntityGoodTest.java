package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;

import org.junit.Test;

import java.util.List;

public class EntityGoodTest {
    @Test
    public void testGoodCreation() {
        Good good = new Good("123", "Apple", "Fruit", 0.99, 0L, 0, 10, "BestBrand", "001", 50.0, 60.0);

        assertEquals("123", good.getUid());
        assertEquals("Apple", good.getName());
        assertTrue(good.getCate().contains("apple"));
        assertTrue(good.getCate().contains("fruit"));
        assertTrue(good.getCate().contains("bestbrand"));
    }

    @Test
    public void testCartFunctionality() {
        Cart cart = new Cart();
        Good good1 = new Good("123", "Apple", "Fruit", 0.99, 123,"BestBrand");
        Good good2 = new Good("124", "Banana", "Fruit", 0.59, 456,"SecondBrand");

        cart.addGoodtoCart(good1);
        cart.addGoodtoCart(good2);

        List<Good> goodsInCart = cart.getGoodModelList();

        // Since goods are sorted by price in descending order, the Apple should be the first item
        assertEquals("Apple", goodsInCart.get(0).getName());
        assertEquals("Banana", goodsInCart.get(1).getName());
    }

}
