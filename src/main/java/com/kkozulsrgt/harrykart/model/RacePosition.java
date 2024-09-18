package com.kkozulsrgt.harrykart.model;

public class RacePosition {
    int positionNumber;
    String participantName;

    public RacePosition(int positionNumber, String participantName) {
        this.positionNumber = positionNumber;
        this.participantName = participantName;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public String getParticipantName() {
        return participantName;
    }

    @Override
    public String toString() {
        return "| " + positionNumber + " | " + participantName + " |";
    }
}
