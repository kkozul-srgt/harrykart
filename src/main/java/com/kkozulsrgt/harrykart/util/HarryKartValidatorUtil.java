package com.kkozulsrgt.harrykart.util;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kkozulsrgt.harrykart.exception.HarryKartXMLValidationException;
import com.kkozulsrgt.harrykart.model.HarryKart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Objects;

public class HarryKartValidatorUtil {
    private static final String XSD_SCHEMA = "harrykart_schema.xsd";

    static Logger logger = LoggerFactory.getLogger(HarryKartValidatorUtil.class);

    /**
     *
     * @param harryKartXML String XML representation of HarryKart race
     * @return HarryKart Returns an instance of HarryKart
     */
    public static HarryKart validateHarryKartData(String harryKartXML) throws IOException, SAXException, HarryKartXMLValidationException {
        HarryKart harryKart = null;
        if(validateXML(harryKartXML)){
            harryKart = new XmlMapper().readValue(harryKartXML, HarryKart.class);
            if(!validateLoopCount(harryKart))
                throw new HarryKartXMLValidationException("Power-up configuration in XML is mismatched with defined number of loops.");
        }
        return harryKart;
    }

    /**
     * Validates XML against a predefined harryKart XSD schema
     * @param xml XML string to validate
     * @return boolean  Whether the XML is valid or not
     */
    private static boolean validateXML(String xml) throws IOException, SAXException {
        logger.info("Validating XML against XSD schema.");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL resource = HarryKartValidatorUtil.class.getClassLoader().getResource(XSD_SCHEMA);
        Objects.requireNonNull(resource);
        Schema schema = schemaFactory.newSchema(new File(resource.getFile()));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(xml)));

        return true;
    }

    /**
     * Checks if the loop count in power-ups equals the race number of loops(Excluding the first loop)
     * @param harryKart An instance of HarryKart
     * @return Returns whether the loop count is valid
     */
    private static boolean validateLoopCount(HarryKart harryKart){
        return harryKart.getPowerUps().size() == harryKart.getNumberOfLoops()-1;
    }
}
