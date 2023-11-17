package com.example.myapplication.Parser.Expression;

import com.example.myapplication.entity.Good;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class BrandExp extends Exp{
    private String brand;

    public BrandExp(String brand) {
        this.brand = brand.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean evaluate(JexlContext context) {
        Good good = (Good) context.get("good");
        return good.getCate().contains(brand);
    }
    public String getBrand() {
        return brand;
    }
}