package com.kkozulsrgt.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Lane {
    @JacksonXmlProperty(isAttribute = true)
    int number;
    @JacksonXmlText
    int powerUpAmount;

    public int getPowerUpAmount() {
        return powerUpAmount;
    }

    public int getNumber() {
        return number;
    }
}
