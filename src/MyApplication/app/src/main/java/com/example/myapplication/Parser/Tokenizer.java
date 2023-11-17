package com.example.myapplication.Parser;




/**
 * This interface provides a mechanism for tokenizing input data. A tokenizer breaks input data
 * into tokens and provides methods to iterate through these tokens.
 *
 * @author Yuan Chen u7631839
 */
public interface Tokenizer {

    /**
     * Checks whether the tokenizer still has more tokens left.
     *
     * @return true if there are more tokens, false otherwise
     */
    public boolean hasNext();

    /**
     * Returns the current token. If there are no tokens or the end has been reached,
     * the behavior is implementation-specific.
     *
     * @return the current token
     */
    public Token current();

    /**
     * Extracts the next token from the input buffer and updates the current token.
     * If there are no more tokens, the behavior is implementation-specific.
     */
    public void next();
}

