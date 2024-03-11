package solution1;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.SystemColor.text;
import static java.lang.Character.*;
import static java.sql.Types.NULL;

class TrivialSAXHandler {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution1\\file.xml";

        try {
            File file = Paths.get(filename).toFile();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();

            parser.parse(file, handler);

//            System.out.println(xmlString);

//            newFile();
        } catch (Exception e) {
            System.out.println("Can not find file with such a name!!!");
        }
    }

    static class XMLHandler extends DefaultHandler {
        static private FileWriter out;
        public static String name, surname;
        public static StringBuilder xmlStr = new StringBuilder();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("person")) {
                name = attributes.getValue("name");
                surname = attributes.getValue("surname");
            }
//            System.out.println(name + " : " + surname);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
//            System.out.println(xmlStr);
            if (qName.equals("persons")) {
//                System.out.println("PERSONS");
            }
            if (qName.equals("person")) {
//                System.out.println("Person 123123123123123123123123123123123");
            }
        }

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
            System.out.println(xmlStr);
            List<String> surnameArr = new ArrayList<>();
            List<String> surnamesFail = new ArrayList<>();
            Pattern namePattern = Pattern.compile("(\\sname(\\s*)=(\\s*)\")(.*?)(\")");
            Matcher nameMatcher = namePattern.matcher(xmlStr);
            Pattern surnamePattern = Pattern.compile("(\\ssurname(\\s*)=(\\s*)\")(.*?)(\")");
            Matcher surnameMatcher = surnamePattern.matcher(xmlStr);
            while (surnameMatcher.find()) {
                String surname = surnameMatcher.group()
                        .replace(surnameMatcher.group(1), "")
                        .replace("\"", "");
                System.out.println(
                        surnameMatcher.group()
                                .replace(surnameMatcher.group(1), "")
                                .replace("\"", "")
                );
                surnameArr.add(surname);
                surnamesFail.add(surnameMatcher.group());
            }
            while (nameMatcher.find()) {
                String name = nameMatcher.group()
                        .replace(nameMatcher.group(1), "")
                        .replace("\"", "");
                System.out.println(
                        nameMatcher.group()
                                .replace(nameMatcher.group(1), "")
                                .replace("\"", "")
                );
                xmlStr = new StringBuilder(xmlStr.toString().replace(name, name + " " + surnameArr.getFirst()));
                xmlStr = new StringBuilder(xmlStr.toString().replace(surnamesFail.getFirst(), ""));
                System.out.println(xmlStr);
                surnameArr.remove(0);
                surnamesFail.remove(0);
            }
            try {
                out.write(String.valueOf(xmlStr));
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void error(SAXParseException e) {
            System.err.println("Erreur non fatale (ligne " +
                    e.getLineNumber() + ", col " +
                    e.getColumnNumber() + ") : " + e.getMessage());
        }

        @Override
        public void fatalError(SAXParseException e) {
            System.err.println("Erreur fatale : " + e.getMessage());
        }

        @Override
        public void warning(SAXParseException e) {
            System.err.println("warning : " + e.getMessage());
        }
    }
}
