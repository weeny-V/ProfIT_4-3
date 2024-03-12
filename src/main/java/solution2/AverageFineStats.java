package solution2;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class AverageFineStats {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        File input = new File("src/main/java/solution2.file.xml");;
        File output = new File("src/main/java/solution2/output.json");
        FineJSONObject fineJSONObject = new FineJSONObject();

        if (input.exists()) {
            Writer writer = new Writer();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLParse handler = new XMLParse();

            parser.parse(input, handler);

            String jsonStr = fineJSONObject.getMap().toString();
            System.out.println(jsonStr);
            writer.writeFile(output.getAbsolutePath(), jsonStr.replaceAll("=", ":"));
        } else {
            System.out.println("File with such a name doesn't exist");
        }
    }
}
