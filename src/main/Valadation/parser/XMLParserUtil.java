package main.Valadation.parser;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import main.Valadation.entity.XMI;

import java.io.FileInputStream;
import java.io.IOException;

public class XMLParserUtil {


    public static XMI parserXML() throws IOException {
        String filePath = "source.xml";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XMI xmi = xmlMapper.readValue(fileInputStream, XMI.class);
    return xmi;
    }
}
