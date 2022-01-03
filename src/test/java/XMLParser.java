package test.java;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entity.XMI;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class XMLParser {
    private static final String filePath = "src/main/resources/parser-test.xml";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XMI xmi = xmlMapper.readValue(fileInputStream, XMI.class);
        System.out.println(xmi.toString());
    }
}
