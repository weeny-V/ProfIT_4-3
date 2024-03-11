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
import java.util.logging.Level;
import java.util.logging.Logger;

class TrivialSAXHandler {
    public static StringBuilder xmlStr;
    static class XMLHandler extends DefaultHandler {
        static private Writer out;
        private String name, surname, lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.println(attributes.getLocalName(1));
//            lastElementName = qName;
//            System.out.println("Qname: " + attributes);
//            if (qName.equals("person")) {
//                System.out.println("Name: " + attributes.getValue("name"));
//                System.out.println("Surname: " + attributes.getValue("surname"));
//            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
//            System.out.println("Info: " + ch.toString());
            for (char c : ch) {
//                System.out.println(c);
                xmlStr.append(c);
            }
        }

        private void emit(String s)
                throws SAXException
        {
            try {
                out.write(s);
                out.flush();
            } catch (IOException e) {
                throw new SAXException("I/O error", e);
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

    public static void newFile(String xmlStr){
        try {
            PrintWriter writer = new PrintWriter("copy.xml");

            writer.append(xmlStr);

            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrivialSAXHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("File not found");
        }

    }
//    public void setDocumentLocator(Locator locator) {
//        System.out.println("Location : " +
//                "publicId=" + locator.getPublicId() +
//                "systemId=" + locator.getSystemId());
//    }
//    public void startDocument(){
//        System.out.println("Debut RSS");
//    }
//    public void endDocument(){
//        System.out.println("Fin RSS");
//
//    }
    public void startElement(String namespace,String qualname,Attributes atts) {

//        System.out.println("Debut de balise : " +
//                "namespace=" +namespace +
//                "qualname="  +qualname);
//        System.out.println("NB atttibuts : " +
//                atts.getLength());
//        for (int i=0;i<atts.getLength();i++) System.out.println("NB atttibuts : " +i+ " name "+atts.getQName(i)+"  = "+atts.getValue(i));
    }
    public void endElement(String namespace,String qualname) {
//        System.out.println("Balise fermante : " +
//                "namespace="+namespace+
//                "qualname="+qualname);

    }
}
