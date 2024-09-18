package com.kkozulsrgt.harrykart;

import com.kkozulsrgt.harrykart.exception.HarryKartException;
import com.kkozulsrgt.harrykart.exception.HarryKartXMLValidationException;
import com.kkozulsrgt.harrykart.model.RacePosition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.kkozulsrgt.harrykart.service.HarryKartRaceRankingService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HarrykartApplicationTests {

	@InjectMocks
	HarryKartRaceRankingService harryKartRaceRankingService;

	private String readXMLFromResources(String fileName) {
		String XML = "";
		Path filePath = new File(this.getClass().getClassLoader().getResource(fileName).getFile()).toPath();

        try {
            XML = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return XML;
	}
	@Before
	public void setUp() throws Exception{

	}

	/**
	 * Typical race test, Lanes 1,2,3 will be in 1st,2nd,3rd place.
	 */
	@Test
	public void basicRaceTest() {
		String raceXML = readXMLFromResources("basicRaceTest.xml");
        ArrayList<RacePosition> racePositions = null;
        try {
			racePositions = harryKartRaceRankingService.getRaceResult(raceXML);
        } catch (HarryKartException e) {
            throw new RuntimeException(e);
        }

        ArrayList<RacePosition> expectedPositions = new ArrayList<>();
		expectedPositions.add(new RacePosition(1,"WAIKIKI SILVIO"));
		expectedPositions.add(new RacePosition(2,"TIMETOBELUCKY"));
		expectedPositions.add(new RacePosition(3,"HERCULES BOKO"));

		Assert.assertEquals(Arrays.toString(expectedPositions.toArray()), Arrays.toString(racePositions.toArray()));
	}

	/**
	 * Test where two Participants finish with same raceTime
	 */
	@Test
	public void tieRaceTest() {
		String raceXML = readXMLFromResources("tieRaceTest.xml");
		ArrayList<RacePosition> racePositions = null;
		try {
			racePositions = harryKartRaceRankingService.getRaceResult(raceXML);
		} catch (HarryKartException e) {
			throw new RuntimeException(e);
		}

		ArrayList<RacePosition> expectedPositions = new ArrayList<>();
		expectedPositions.add(new RacePosition(1,"TIMETOBELUCKY"));
		expectedPositions.add(new RacePosition(1,"CARGO DOOR"));
		expectedPositions.add(new RacePosition(2,"HERCULES BOKO"));

		Assert.assertEquals(Arrays.toString(expectedPositions.toArray()), Arrays.toString(racePositions.toArray()));
	}

	/**
	 * Test of disqualification during race processing(baseSpeed <= 0)
	 */
	@Test
	public void disqualificationRaceTest() {
		String raceXML = readXMLFromResources("disqualificationTest.xml");
		ArrayList<RacePosition> racePositions = null;
		try {
			racePositions = harryKartRaceRankingService.getRaceResult(raceXML);
		} catch (HarryKartException e) {
			throw new RuntimeException(e);
		}

		ArrayList<RacePosition> expectedPositions = new ArrayList<>();
		expectedPositions.add(new RacePosition(1,"CARGO DOOR"));
		expectedPositions.add(new RacePosition(2,"TIMETOBELUCKY"));

		Assert.assertEquals(Arrays.toString(expectedPositions.toArray()), Arrays.toString(racePositions.toArray()));
	}

	/**
	 * Test where the passed XML is not in compliance with the harrykart_schema XSD
	 */
	@Test
	public void XMLSchemaMismatchTest(){
		String raceXML = readXMLFromResources("xmlSchemaMismatchTest.xml");
		Assert.assertThrows(HarryKartXMLValidationException.class, () -> harryKartRaceRankingService.getRaceResult(raceXML));
	}

	/**
	 * Test where the passed XML doesn't have properly defined power-ups
	 */
	@Test
	public void incorrectPowerupXMLTest(){
		String raceXML = readXMLFromResources("incorrectPowerupXMLTest.xml");
		Assert.assertThrows(HarryKartXMLValidationException.class, () -> harryKartRaceRankingService.getRaceResult(raceXML));
	}
	/**
	 * Test where the passed XML doesn't have properly defined attributes
	 */
	@Test
	public void invalidAttributesXMLTest(){
		String raceXML = readXMLFromResources("invalidAttributesXMLTest.xml");
		Assert.assertThrows(HarryKartXMLValidationException.class, () -> harryKartRaceRankingService.getRaceResult(raceXML));
	}
}
