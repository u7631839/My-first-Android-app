package com.example.myapplication.Parser;


import java.util.Objects;

/**
 * Represents a token with a specific type used in search functionality. Each token is
 * composed of a string value and a type.
 *
 * @author Yuan Chen u7631839
 */
public class Token {
    private final String token;
    private final Type type;

    /**
     * Constructs a new Token with the given string value and type.
     *
     * @param token The string representation of the token.
     * @param type  The type of the token, as defined in the {@link Type} enum.
     */
    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    /**
     * Defines the various types of tokens that can be encountered.
     * These types will be used in search functionality.
     */
    public enum Type {PRICE, CLICKS, CATEGORY, BRAND, NAME, LOCATION}

    /**
     * Retrieves the string representation of the token.
     *
     * @return the token string
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Retrieves the type of the token.
     *
     * @return the token type
     */
    public Type getType() {
        return type;
    }

    /**
     * Provides a string representation of the token, including its value and type.
     *
     * @return a string representation of the token
     */
    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * Compares this token to the specified object. The result is {@code true} if and only if
     * the argument is not {@code null} and is a {@code Token} object that has the same
     * token string and type as this object.
     *
     * @param o the object to compare this {@code Token} against
     * @return {@code true} if the given object represents a {@code Token} equivalent to
     * this token, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return getToken().equals(token1.getToken()) && getType() == token1.getType();
    }

    /**
     * Returns a hash code for this token. This method is supported for the benefit of hash
     * tables such as those provided by {@link java.util.HashMap}.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getType());
    }
}
