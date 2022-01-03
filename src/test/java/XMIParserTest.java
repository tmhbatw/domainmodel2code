package test.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entity.EAnnotations;
import entity.XMI;
import entity.tag.Aggregate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author SunWarriorZLX
 * @since
 **/
public class XMIParserTest {
    private static final String filePath = "src/main/resources/parser-test.xml";

    public static void main(String[] args) throws JsonProcessingException {
        Aggregate aggregate = new Aggregate();
        aggregate.setBaseClass("123");
        aggregate.setId("1234");
        XmlMapper xmlMapper = new XmlMapper();
        try {
            String xml = xmlMapper.writeValueAsString(aggregate);
            System.out.println(xml);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Aggregate aggregate1 = xmlMapper.readValue("<Aggregate id=\"1234\" base_class=\"123\"/>", Aggregate.class);
        System.out.println(aggregate1.toString());
    }
}
