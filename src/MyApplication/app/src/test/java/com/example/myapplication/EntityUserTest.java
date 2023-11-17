package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

public class EntityUserTest {

    private User user1, user2;
    private Good good1, good2;

    @Before
    public void setUp() {
        user1 = new User("password1", "email1");
        user2 = new User("password2", "email2", "uid2");

        good1 = new Good();
        good1.setCategory("category1");
        good1.setPrice(10.0);

        good2 = new Good();
        good2.setCategory("category2");
        good2.setPrice(20.0);
    }

    @Test
    public void testGetSetAttributes() {
        user1.setPassword("newPassword");
        assertEquals("newPassword", user1.getPassword());

        user1.setEmail("newEmail");
        assertEquals("newEmail", user1.getEmail());

        user1.setUid("newUid");
        assertEquals("newUid", user1.getUid());
    }

    @Test
    public void testAddGood() {
        user1.addGood(good1);
        assertEquals(1, user1.getCartList().size());

        user1.addGood(good2);
        assertEquals(2, user1.getCartList().size());
    }

    @Test
    public void testCompareTo() {
        assertTrue(user1.compareTo(user2) != 0);
    }
}
