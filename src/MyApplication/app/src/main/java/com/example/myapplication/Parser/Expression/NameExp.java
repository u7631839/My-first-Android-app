package com.example.myapplication.Parser.Expression;

import com.example.myapplication.entity.Good;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class NameExp extends Exp {
    private String name;


    public NameExp(String name) {
        this.name = name.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean evaluate(JexlContext context) {
        Good good=(Good) context.get("good");
        return good.getCate().contains(name);
    }

    public String getName() {
        return name;
    }
}
