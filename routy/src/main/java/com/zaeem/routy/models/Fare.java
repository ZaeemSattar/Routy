/*
 *
 *   Created Zaeem Sattar on 3/28/21 4:43 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/28/21 4:43 PM
 * /
 */

package com.zaeem.routy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fare implements Serializable {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("text")
    @Expose
    private String text;
    private final static long serialVersionUID = 3789634605222995831L;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}