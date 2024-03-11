package solution1;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLHandlerParser {
    static class XMLHandler extends DefaultHandler {
        private String name, surname, lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
            System.out.println("Qname: " + attributes);
            if (qName.equals("person")) {
                System.out.println("Name: " + attributes.getValue("name"));
                System.out.println("Surname: " + attributes.getValue("surname"));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);
            System.out.println("Info: " + lastElementName);
            System.out.println(information);
        }
    }

    public static void main(String[] args) throws Exception {
        String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution1\\file.xml";

        try {
            File file = Paths.get(filename).toFile();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();

            parser.parse(file, handler);
//            StringBuilder xmlString = readFile(file);
//            Document doc = writeFile(String.valueOf(xmlString));
//
//            String str = convertDocumentToString(doc);
//            System.out.println(doc);
        } catch (Exception e) {
            System.out.println("Can not find file with such a name!!!");
        }
    }

//    public static StringBuilder readFile(File file) throws Exception {
//        StringBuilder xmlString = new StringBuilder();
//
//        try (InputStream is = new FileInputStream(file)){
//            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)
//                    .useDelimiter("\n");
//            while (scanner.hasNext()) {
//                String row = scanner.next();
//
//                StringBuilder person = new StringBuilder();
////                StringBuilder name = new StringBuilder();
////                StringBuilder surname = new StringBuilder();
////                System.out.println(row);
//                if (row.contains("<person") && !row.contains("/>") && !row.contains("persons")) {
//                    System.out.println("Person contains: " + row);
//                    person.append(row);
//                } else if (row.contains("/>")) {
//                    person.append(row);
////                    System.out.println(person);
//                    xmlString.append(person);
//                    person.setLength(0);
//                } else if (!person.isEmpty()) {
//                    System.out.println("Is empty: " + row);
//                    person.append(row);
//                }
//
////                //Search name attribute
////                Pattern namePattern = Pattern.compile("\\sname(\\s*)=(\\s*)\"(.*?)\"");
////                Matcher nameMatcher = namePattern.matcher(row);
////
////                //Search surname attribute
////                Pattern surnamePattern = Pattern.compile("\\ssurname(\\s*)=(\\s*)\"(.*?)\"");
////                Matcher surnameMatcher = surnamePattern.matcher(row);
////
////                while (nameMatcher.find()) {
////                    Pattern betweenQuotesPattern = Pattern.compile("\"(.*?)\"");
////
////                    Matcher betweenQuotesMatcher = betweenQuotesPattern.matcher(nameMatcher.group());
////
////                    while (betweenQuotesMatcher.find()) {
////                        System.out.println(betweenQuotesMatcher.group());
////                    }
////                }
//
////                System.out.println(row);
//                xmlString.append(row.replace("name", "newName")).append("\n");
////                if (nameMatcher.matches()) {
////                    System.out.println("Matches: " + nameMatcher.group());
////                }
////
////                if (surnameMatcher.matches()) {
////                    System.out.println("sdfsdfsdf");
////                }
////                String name = row.
////                System.out.println("123: " + row.);
//            }
//
//            scanner.close();
////            System.out.println(xmlString);
//
//            return xmlString;
//        }
//    }
//
//    public static Document writeFile(String xmlSource) throws ParserConfigurationException, IOException, SAXException, TransformerException {
//        // Parse the given input
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
//
//        // Write the parsed document to a xml file
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);
//
//        StreamResult result =  new StreamResult(new File("my-file2.xml"));
//        transformer.transform(source, result);
//
//        return doc;
//    }
//
//    private static String convertDocumentToString(Document doc) {
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer transformer;
//        try {
//            transformer = tf.newTransformer();
//            // below code to remove XML declaration
//            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            StringWriter writer = new StringWriter();
//            transformer.transform(new DOMSource(doc), new StreamResult(writer));
//            String output = writer.getBuffer().toString();
//            return output;
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
