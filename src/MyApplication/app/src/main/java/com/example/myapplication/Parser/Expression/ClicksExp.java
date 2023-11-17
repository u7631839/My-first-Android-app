package com.example.myapplication.Parser.Expression;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

public class ClicksExp extends Exp {
    private String comparator = "";
    private String clicks;

    public ClicksExp(String clicks) {
        if (Integer.parseInt(clicks.substring(1)) < 1 || Integer.parseInt(clicks.substring(1)) > 10000) {
            throw new IllegalArgumentException("Clicks value out of range. Must be between 1 and 10000.");
        }
        //change if necessary
        this.clicks = clicks;
        setComparator();
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlEngine engine = new Engine();
        JexlScript script = engine.createScript("if(good.clicks" + comparator + clicks + "){return true;}else{return false;}");
        if (script.execute(context).toString().equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    private void setComparator() {
        // if typed in "=", change it to "=="
        // if ">" "<" ">=" "<=" then comparator = "";
        if (clicks.charAt(0) == '=') {
            comparator = "==";
            clicks = clicks.substring(1);
        } else if (clicks.charAt(0) == '>' && clicks.charAt(1) == '=') {
            comparator = ">=";
            clicks = clicks.substring(2);
        } else if (clicks.charAt(0) == '<' && clicks.charAt(1) == '=') {
            comparator = "<=";
            clicks = clicks.substring(2);
        } else {
            comparator = clicks.substring(0, 1);
            clicks = clicks.substring(1);
        }
    }

    public String getComparator() {
        return comparator;
    }

    public String getClicks() {
        return clicks;
    }
}
