package com.kkozulsrgt.harrykart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkozulsrgt.harrykart.model.RacePosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kkozulsrgt.harrykart.service.HarryKartRaceRankingService;

import java.util.ArrayList;

@RestController
public class RaceController {
    @Autowired
    HarryKartRaceRankingService harryKartRaceRankingService;

    Logger logger = LoggerFactory.getLogger(RaceController.class);

    /**
     * Endpoint which starts race processing
     * @param harryKartXML String representing the XML configuration of a race
     * @return String Returns the result of the operation as String
     */
    @PostMapping(path = "/race/start", consumes = MediaType.APPLICATION_XML_VALUE)
    public String startRace(@RequestBody String harryKartXML) {
        String result;
        try {
            ArrayList<RacePosition> racePositions = harryKartRaceRankingService.getRaceResult(harryKartXML);
            logger.info("TOP 3");
            logger.info("| Position | Horse Name |");
            racePositions.forEach(racePosition -> logger.info(racePosition.toString()));

            result = new ObjectMapper().writeValueAsString(racePositions);
            logger.info(result);
        }catch (Exception e) {
            result = e.getMessage();
            logger.info(result);
        }
        return result;
    }
}
