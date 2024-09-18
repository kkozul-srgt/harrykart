package com.kkozulsrgt.harrykart.model;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class Loop {
    @JacksonXmlProperty(isAttribute = true)
    int number;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "lane")
    @JsonMerge
    ArrayList<Lane> lanes;

    public int getNumber() {
        return number;
    }

    public ArrayList<Lane> getLanes() {
        return lanes;
    }
}
