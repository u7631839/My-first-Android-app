package com.example.myapplication.Parser;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.MyApplication;
import com.example.myapplication.Parser.Expression.BrandExp;
import com.example.myapplication.Parser.Expression.CategoryExp;
import com.example.myapplication.Parser.Expression.ClicksExp;
import com.example.myapplication.Parser.Expression.Exp;
import com.example.myapplication.Parser.Expression.NameExp;
import com.example.myapplication.Parser.Expression.PriceExp;
import com.example.myapplication.entity.Good;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
/**
 * A parser that processes search queries using a provided {@link SearchTokenizer}.
 * Once parsed, the search queries are transformed into a list of expressions,
 * each of which represents a different attribute to filter by.
 *
 * @author Yuan Chen u7631839
 */
public class Parser {
    public static AVLCateTree goodAVLtree;
    private JexlEngine engine = new Engine();
    private JexlContext context = new MapContext();

    private SearchTokenizer searchTokenizer;
    public ArrayList<Exp> queryAttributes = new ArrayList<>();
    private List<Good> goodSourceList = new ArrayList<>();
    private ArrayList<Good> searchResultList = new ArrayList<>();

    public Parser(SearchTokenizer searchTokenizer) {
        this.searchTokenizer = searchTokenizer;
    }
    /**
     * Parses the tokens provided by the {@link SearchTokenizer} and converts them
     * into a list of expressions. Each expression represents an attribute to filter by.
     * More related funtions are in SeachActivity due to the project structure.
     */
    public void parseExp() {
        while (searchTokenizer.hasNext()) {
            switch (searchTokenizer.current().getType()) {
                case CATEGORY:
                    queryAttributes.add(new CategoryExp(searchTokenizer.current().getToken()));
                    break;
                case BRAND:
                    queryAttributes.add(new BrandExp(searchTokenizer.current().getToken()));
                    break;
                case PRICE:
                    queryAttributes.add(new PriceExp(searchTokenizer.current().getToken()));
                    break;
                case NAME:
                    queryAttributes.add(new NameExp(searchTokenizer.current().getToken()));
                    break;
                case CLICKS:
                    queryAttributes.add(new ClicksExp(searchTokenizer.current().getToken()));
                    break;
                default:
                    break;
            }
            searchTokenizer.next();
        }
    }
    /**
     * Returns the list of query attributes or expressions generated by parsing.
     *
     * @return a list of query expressions
     */
    public ArrayList<Exp> getQueryAttributes() {
        return queryAttributes;
    }
}





