package com.kkozulsrgt.harrykart.service;

import com.kkozulsrgt.harrykart.exception.HarryKartException;
import com.kkozulsrgt.harrykart.exception.HarryKartXMLValidationException;
import com.kkozulsrgt.harrykart.model.HarryKart;
import com.kkozulsrgt.harrykart.model.Participant;
import com.kkozulsrgt.harrykart.model.RacePosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import com.kkozulsrgt.harrykart.util.HarryKartValidatorUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Service
@Configurable
public class HarryKartRaceRankingService {
    private static final float LOOP_LENGTH = 1000f;

    Logger logger =  LoggerFactory.getLogger(HarryKartRaceRankingService.class);

    /**
     * Loops through each Participant and their power-ups to calculate their race time
     * @param harryKartXML XML string representing the data structure of the race
     * @return ArrayList<RacePosition> An ArrayList of top 3 race positions ranked from lowest to highest race time
     */
    public ArrayList<RacePosition> getRaceResult(String harryKartXML) throws HarryKartException {
        ArrayList<Participant> rankedParticipants = new ArrayList<>();
        try {
            HarryKart harryKart = HarryKartValidatorUtil.validateHarryKartData(harryKartXML);
            if(harryKart != null) {
                logger.info("Validated HarryKart XML. Starting race...");
                harryKart.getStartList().forEach(participant -> {
                    // First loop participants run on base speed
                    participant.setRaceTime(LOOP_LENGTH / participant.getBaseSpeed());
                    // Loops through each race loop and increases/decreases speed based on the power-up value
                    harryKart.getPowerUps().forEach(loop ->
                            loop.getLanes().forEach(lane -> {
                                if (participant.getLane() == lane.getNumber()) {
                                    participant.setBaseSpeed(participant.getBaseSpeed() + lane.getPowerUpAmount());
                                    // Base speed is at or below 0, cannot progress in race anymore
                                    if(participant.getBaseSpeed() <= 0)
                                        return;

                                    participant.setRaceTime(participant.getRaceTime() + (LOOP_LENGTH / participant.getBaseSpeed()));
                                    logger.info("LOOP {}", loop.getNumber());
                                    harryKart.getStartList().forEach(participantt -> logger.info(participantt.toString()));
                                }
                            }));
                    // Rank only participants that didn't stop
                    if(participant.getBaseSpeed() > 0)
                        rankedParticipants.add(participant);
                });
                logger.info("Race finished! Sorting Participants from lowest raceTime.");
                // Sort participants from lowest raceTime
                Collections.sort(rankedParticipants);
            }
        } catch (IOException e) {
            throw new HarryKartException(e.getMessage());
        } catch (SAXException e) {
            throw new HarryKartXMLValidationException(e.getMessage());
        }
        ArrayList<RacePosition> racePositions = new ArrayList<>();
        logger.info("Creating racePositions.");
        // Set position in race for participants, additionally check for ties and give them the same placement
        int position = 1;
        racePositions.add(new RacePosition(1, rankedParticipants.get(0).getName()));
        for (int i = 1; i < Math.min(rankedParticipants.size(), 3); i++) {
            if(rankedParticipants.get(i-1).getRaceTime() == rankedParticipants.get(i).getRaceTime()){
                racePositions.add(new RacePosition(position, rankedParticipants.get(i).getName()));
            }else{
                racePositions.add(new RacePosition(++position, rankedParticipants.get(i).getName()));
            }
        }
        return racePositions;
    }
}
