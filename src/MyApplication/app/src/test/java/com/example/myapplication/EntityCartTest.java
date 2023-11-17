package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;

public class EntityCartTest {

    private Cart cart;
    private Good good1, good2, good3;

    @Before
    public void setUp() {
        cart = new Cart();

        good1 = new Good();
        good1.setPrice(10.0);

        good2 = new Good();
        good2.setPrice(20.0);

        good3 = new Good();
        good3.setPrice(15.0);

    }

    @Test
    public void testGetSetAttributes() {
        cart.setCategoryui("Electronics");
        assertEquals("Electronics", cart.getCategoryui());

        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        cart.setGoodModelList(goods);

        assertEquals(2, cart.getGoodModelList().size());
        assertEquals(good1, cart.getGoodModelList().get(0));
        assertEquals(good2, cart.getGoodModelList().get(1));
    }

    @Test
    public void testAddGoodToCart() {
        cart.addGoodtoCart(good2);
        assertEquals(1, cart.getGoodModelList().size());
        assertEquals(good2, cart.getGoodModelList().get(0));

        cart.addGoodtoCart(good1);
        assertEquals(2, cart.getGoodModelList().size());
        assertEquals(good2, cart.getGoodModelList().get(0));
        assertEquals(good1, cart.getGoodModelList().get(1));

        cart.addGoodtoCart(good3);
        assertEquals(3, cart.getGoodModelList().size());
        assertEquals(good2, cart.getGoodModelList().get(0));
        assertEquals(good3, cart.getGoodModelList().get(1));
        assertEquals(good1, cart.getGoodModelList().get(2));
    }
}
