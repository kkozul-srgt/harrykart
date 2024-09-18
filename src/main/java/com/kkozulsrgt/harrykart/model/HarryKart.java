package com.kkozulsrgt.harrykart.model;

import java.util.ArrayList;

public class HarryKart {
    int numberOfLoops;
    ArrayList<Participant> startList;
    ArrayList<Loop> powerUps;

    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    public ArrayList<Participant> getStartList() {
        return startList;
    }

    public ArrayList<Loop> getPowerUps() {
        return powerUps;
    }
}
