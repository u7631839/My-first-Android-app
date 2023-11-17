package com.example.myapplication.Parser.Expression;

import com.example.myapplication.entity.Good;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class CategoryExp extends Exp{
    private String category;

    public CategoryExp(String category) {
        this.category = category.toLowerCase(Locale.ROOT); // Make it lowercase for case-insensitive comparison.
    }

    @Override
    public boolean evaluate(JexlContext context) {
        Good good = (Good) context.get("good");
        return good.getCate().contains(category);
    }

    public String getCategory() {
        return category;
    }
}




