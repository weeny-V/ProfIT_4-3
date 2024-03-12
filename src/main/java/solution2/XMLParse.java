package solution2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;

class XMLParse extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("fine")) {
            String first_name = attributes.getValue("first_name");
            String last_name = attributes.getValue("last_name");
            String type = attributes.getValue("type");
            Integer fine_amount = Integer.valueOf(attributes.getValue("fine_amount"));
            String date_time = attributes.getValue("date_time");

            Fine fine = new Fine(first_name, last_name, type, fine_amount, date_time);

            try {
                FineJSONObject fineJSONObject = new FineJSONObject();
                fineJSONObject.setMapValue(fine);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
