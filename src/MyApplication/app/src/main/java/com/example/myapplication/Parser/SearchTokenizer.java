package com.example.myapplication.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
/**
 * Implements the {@link Tokenizer} interface to tokenize search queries.
 * A search query is broken down into {@link Token}s based on predefined criteria.
 *
 * @author Yuan Chen u7631839
 */
public class SearchTokenizer implements Tokenizer{
    // String to be transformed into tokens
    private String buffer;

    private int index;

    //The current token, will be updated when next() is called
    private Token currentToken;
    private static final String TOKEN_NAME = "name=";
    private static final String TOKEN_PRICE = "price";
    private static final String TOKEN_CLICKS = "clicks";
    private static final String TOKEN_BRAND = "brand=";
    private static final String TOKEN_CATEGORY = "category=";
    private static final String TOKEN_LOCATION = "location=";
    static final char[] whiteSpaces = {' ', '\n', '\t'};


    String [] validComparator = {"<",">","=","<=",">="};
    /**
     * Exception thrown when encountering an illegal token.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }


    public SearchTokenizer(String buffer) {
        this.buffer = buffer; // get the String from search bar
        next(); // extract the first token and store it in the currentToken
    }

    @Override
    public boolean hasNext() {
        return currentToken!=null;
    }

    @Override
    public Token current() {
        return currentToken;
    }
    /**
     * Moves to the next token in the search query and updates the current token.
     */
    @Override
    public void next() {
        buffer = buffer.toLowerCase(Locale.ROOT);

        // Check if the buffer is empty or the index exceeds its length.
        if (buffer.isEmpty() || index >= buffer.length()) {
            currentToken = null;
            return;
        }

        // Initialize a StringBuilder to accumulate characters from the buffer.
        StringBuilder stringBuilder = new StringBuilder();

        // Append characters to the StringBuilder until a ';' is encountered or the end of the buffer is reached.
        while (index < buffer.length() && buffer.charAt(index) != ';') {
            stringBuilder.append(buffer.charAt(index++));
        }

        // Convert the StringBuilder to a string and remove any leading or trailing spaces.
        String currentString = stringBuilder.toString().trim();

        // Check the prefix of the currentString to determine the type of the token and create it accordingly.
        if (currentString.startsWith(TOKEN_NAME)) {
            currentToken = new Token(currentString.substring(TOKEN_NAME.length()), Token.Type.NAME);
        } else if (currentString.startsWith(TOKEN_PRICE)) {
            currentToken = new Token(currentString.substring(TOKEN_PRICE.length()), Token.Type.PRICE);
        } else if (currentString.startsWith(TOKEN_CLICKS)) {
            currentToken = new Token(currentString.substring(TOKEN_CLICKS.length()), Token.Type.CLICKS);
        } else if (currentString.startsWith(TOKEN_BRAND)) {
            currentToken = new Token(currentString.substring(TOKEN_BRAND.length()), Token.Type.BRAND);
        } else if (currentString.startsWith(TOKEN_CATEGORY)) {
            currentToken = new Token(currentString.substring(TOKEN_CATEGORY.length()), Token.Type.CATEGORY);
        } else if (currentString.startsWith(TOKEN_LOCATION)) {
            currentToken = new Token(currentString.substring(TOKEN_LOCATION.length()), Token.Type.LOCATION);
        } else {
            // If the currentString doesn't match any known token type, throw an exception.
            throw new IllegalTokenException(currentString);
        }

        // Validate the created token using a separate validation method.
        validateToken(currentToken);

        // Move to the next character in the buffer if it exists.
        if (index < buffer.length()) {
            index++;
        }
    }

    /**
     * Validates the format and content of a given token.
     *
     * @param token the token to validate
     */
    private void validateToken(Token token) {
        if (token.getToken().length() < 1) {
            throw new IllegalTokenException(token.getToken());
        }

        String tokenValue = token.getToken();

        if (tokenValue.startsWith(validComparator[3]) || tokenValue.startsWith(validComparator[4])) {
            String numberPart = tokenValue.substring(2);
            if (!isANumber(numberPart)) {
                throw new IllegalTokenException(tokenValue);
            }
        } else if (tokenValue.startsWith(validComparator[0]) || tokenValue.startsWith(validComparator[1]) || tokenValue.startsWith(validComparator[2])) {
            String numberPart = tokenValue.substring(1);
            if (!isANumber(numberPart)) {
                throw new IllegalTokenException(tokenValue);
            }
        }
    }


    public String getBuffer() {
        return buffer;
    }

    public int getIndex() {
        return index;
    }

    public Token getCurrentToken() {
        return currentToken;
    }
    /**
     * Checks if a given string is a valid number.
     *
     * @param str the string to check
     * @return {@code true} if the string represents a valid number, {@code false} otherwise
     */
    public static boolean isANumber(String str) {
        if (str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}