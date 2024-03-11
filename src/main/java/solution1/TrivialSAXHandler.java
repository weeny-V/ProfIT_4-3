package solution1;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TrivialSAXHandler {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution1\\file.xml";

        try {
            File file = Paths.get(filename).toFile();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();

            parser.parse(file, handler);
        } catch (Exception e) {
            System.out.println("Can not find file with such a name!!!");
        }
    }

    static class XMLHandler extends DefaultHandler {
        static private FileWriter out;
        public static StringBuilder xmlStr = new StringBuilder();

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            for (char c : ch) {
                if (c != 0 && xmlStr.indexOf("</persons>") == -1) {
                    xmlStr.append(c);
                }
            }
        }

        @Override
        public void startDocument() throws SAXException {
            String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution1\\output.xml";
            try {
                out = new FileWriter(filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void endDocument() throws SAXException {
            Pattern namePattern = Pattern.compile("(\\sname(\\s*)=(\\s*)\")(.*?)(\")");
            Matcher nameMatcher = namePattern.matcher(xmlStr);
            Pattern surnamePattern = Pattern.compile("(\\ssurname(\\s*)=(\\s*)\")(.*?)(\")");
            Matcher surnameMatcher = surnamePattern.matcher(xmlStr);
            while (surnameMatcher.find()) {
                if (nameMatcher.find()) {
                    String surname = surnameMatcher.group()
                            .replace(surnameMatcher.group(1), "")
                            .replace("\"", "");
                    String name = nameMatcher.group()
                            .replace(nameMatcher.group(1), "")
                            .replace("\"", "");
                    xmlStr = new StringBuilder(xmlStr.toString().replace(name, name + " " + surname));
                    xmlStr = new StringBuilder(xmlStr.toString().replace(surnameMatcher.group(), ""));
                }
            }

            try {
                out.write(String.valueOf(xmlStr));
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
