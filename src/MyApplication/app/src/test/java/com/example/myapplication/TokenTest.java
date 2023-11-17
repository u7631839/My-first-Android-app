package com.example.myapplication;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.Parser.SearchTokenizer;
import com.example.myapplication.Parser.Token;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TokenTest {

    private SearchTokenizer tokenizer;

    @BeforeEach
    public void setUp() {
        tokenizer = null;
    }

    @Test
    public void testTokenCreation() {
        Token token = new Token("brand", Token.Type.BRAND);
        assertEquals("brand", token.getToken());
        assertEquals(Token.Type.BRAND, token.getType());
    }

    @Test
    public void testTokenEquality() {
        Token token1 = new Token("brand", Token.Type.BRAND);
        Token token2 = new Token("brand", Token.Type.BRAND);
        Token token3 = new Token("name", Token.Type.NAME);

        assertEquals(token1, token2);
        assertNotEquals(token1, token3);
    }

    @Test
    public void testValidTokenizer() {
        tokenizer = new SearchTokenizer("name=apple;brand=sony;price>1000;");
        assertTrue(tokenizer.hasNext());
        assertEquals(Token.Type.NAME, tokenizer.current().getType());
        assertEquals("apple", tokenizer.current().getToken());

        tokenizer.next();
        assertTrue(tokenizer.hasNext());
        assertEquals(Token.Type.BRAND, tokenizer.current().getType());
        assertEquals("sony", tokenizer.current().getToken());

        tokenizer.next();
        assertTrue(tokenizer.hasNext());
        assertEquals(Token.Type.PRICE, tokenizer.current().getType());
        assertEquals(">1000", tokenizer.current().getToken());

        tokenizer.next();
        assertFalse(tokenizer.hasNext());
    }
}
