package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/9.
 */
public enum LayoutId implements IEnum {
    layout_101("101"),
    layout_201("201"),
    layout_204("204"),
    layout_301("301"),
   /* layout_202("202"),
    layout_203("203"),
    
    layout_205("205"),*/
    /*
    layout_302("302"),
    layout_303("303"),*/
    layout_304("304"),
    layout_305("305"),
    layout_401("401"),
   /* layout_402("402"),
    layout_403("403"),*/
    layout_404("404"),
    layout_405("405"),
    /*
    layout_502("502"),*/
    layout_501("501"),
    layout_503("503"),
    layout_601("601"),
    layout_602("602"),
    layout_603("603"),
    layout_701("701"),
    layout_801("801"),
    layout_901("901"),
    /*layout_902("902"),
    layout_903("101"),*/
    layout_904("904"),
    layout_1001("1001"),
    layout_1002("1002"),
    layout_1003("1003"),
    layout_1201("1201"),
    layout_1301("1301"),
    layout_1302("1302"),
    layout_1601("1601");


    private String value;

    private LayoutId(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static LayoutId getLayoutId(String value)
    {
        for(LayoutId layoutId : LayoutId.values())
        {
            if(layoutId.getValue().equals(value))
                return layoutId;
        }

        return null;
    }
}
