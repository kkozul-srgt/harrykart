package com.kkozulsrgt.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Participant implements Comparable<Participant> {
    @JacksonXmlProperty
    int lane;
    @JacksonXmlProperty
    String name;
    @JacksonXmlProperty
    int baseSpeed;

    float raceTime;

    public Participant() {
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public float getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "lane=" + lane +
                ", name='" + name + '\'' +
                ", baseSpeed=" + baseSpeed +
                ", raceTime=" + raceTime +
                '}';
    }

    @Override
    public int compareTo(Participant other) {
        return Float.compare(this.getRaceTime(), other.getRaceTime());
    }
}
