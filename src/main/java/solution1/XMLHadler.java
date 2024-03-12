package solution1;

import org.xml.sax.helpers.DefaultHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class XMLHandler extends DefaultHandler {
    private static StringBuilder xmlStr = new StringBuilder();

    @Override
    public void characters(char[] ch, int start, int length) {
        for (char c : ch) {
            if (c != 0 && xmlStr.indexOf("</persons>") == -1) {
                xmlStr.append(c);
            }
        }
    }

    @Override
    public void endDocument() {
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
    }

    public StringBuilder getXmlString() {
        return xmlStr;
    }
}
