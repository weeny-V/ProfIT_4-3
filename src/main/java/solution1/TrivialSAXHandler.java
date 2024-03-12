package solution1;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

class TrivialSAXHandler {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        File file = new File ("src/main/java/solution1/file.xml");
        File outputFile = new File("src/main/java/solution1/output.xml");

        if (file.exists()) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();
            Writer writer = new Writer();

            parser.parse(file, handler);

            writer.writeFile(outputFile.getAbsolutePath(), handler.getXmlString().toString());

        } else {
            System.out.println("File doesn't exist!");
        }
    }
}
