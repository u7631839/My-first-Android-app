package com.example.myapplication.Parser.Expression;

import org.apache.commons.jexl3.JexlContext;


public abstract class Exp {
    public abstract boolean evaluate(JexlContext context);
}
