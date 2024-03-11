package solution2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class AverageFineStats {
    static FineJSONObject fineJSONObject = new FineJSONObject();

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution2\\file.xml";

        try {
            File file = Paths.get(filename).toFile();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLParse handler = new XMLParse();

            parser.parse(file, handler);

            String jsonStr = fineJSONObject.getMap().toString();
            System.out.println(jsonStr);
            fineJSONObject.createJSONFile(jsonStr.replaceAll("=", ":"));
        } catch (Exception e) {
            System.out.println("Can not find file with such a name!!!");
        }
    }

    static class Fine {
        public String first_name;
        public String last_name;
        public String type;
        public Integer fine_amount;
        public String date_time;

        public Fine (String first_name, String last_name, String type, Integer fine_amount, String date_time) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.type = type;
            this.fine_amount = fine_amount;
            this.date_time = date_time;
        }

        @Override
        public String toString() {
            return "{ " + "first_name: " + first_name + ", last_name: " + last_name + ", type: " + type + ", fine_amount: " + fine_amount + ", date_time: " + date_time + " }";
        }
    }

    static class XMLParse extends DefaultHandler {
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
                    fineJSONObject.setMapValue(fine);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static LinkedHashMap<String, Map<String, Integer>> sortJSON(Map<String, Map<String, Integer>> map) {
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        ArrayList<String> list = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
            list.add(String.valueOf(entry.getValue().get("\"average_amount\"")));
        }
        Collections.sort(list, new Comparator<String>() {
            public int compare(String str, String str1) {
                return (str1).compareTo(str);
            }
        });
        for (String str : list) {
            for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
                if (entry.getValue().get("\"average_amount\"").toString().equals(str)) {
                    sortedMap.put(entry.getKey(), map.get(entry.getKey()));
                }
            }
        }

        return sortedMap;
    }

    public static class FineJSONObject {
        private static Map<String, Map<String, Map<String, Integer>>> map = new HashMap<>();

        public void setMapValue(Fine fine) throws ParseException {
            //Get year when fine was get
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = parser.parse(fine.date_time);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(dateTime);
            LocalDate localDate = LocalDate.parse(formattedDate);
            Integer year = localDate.getYear();

            //If key is already exist we do next if it's not we just create fine type and
            // add this fine to his year
            if (map.containsKey("\"" + year + "\"")) {
                Map<String, Map<String, Integer>> yearMap = map.get("\"" + year + "\"");

                if (yearMap.containsKey("\"" + fine.type + "\"")) {
                    Map<String, Integer> typeMap = yearMap.get("\"" + fine.type + "\"");
                    int count = typeMap.get("\"count\"");
                    int newCount = count + 1;
                    Integer averageValue = ((typeMap.get("\"average_amount\"") * count) + fine.fine_amount) / newCount;

                    typeMap.put("\"average_amount\"", averageValue);
                    typeMap.put("\"count\"", newCount);

                    yearMap.put("\"" + fine.type + "\"", typeMap);
                } else {
                    Map<String, Integer> mapTypeValues = new HashMap<>();
                    mapTypeValues.put("\"average_amount\"", fine.fine_amount);
                    mapTypeValues.put("\"count\"", 1);

                    yearMap.put("\"" + fine.type + "\"", mapTypeValues);
                }

                LinkedHashMap<String, Map<String, Integer>> sortedMap = sortJSON(yearMap);

                map.put("\"" + year + "\"", sortedMap);
            } else {
                Map<String, Integer> mapTypeValues = new HashMap<>();
                mapTypeValues.put("\"average_amount\"", fine.fine_amount);
                mapTypeValues.put("\"count\"", 1);

                Map<String, Map<String, Integer>> mapType = new HashMap<>();
                mapType.put("\"" + fine.type + "\"", mapTypeValues);

                map.put("\"" + year + "\"", mapType);
            }
        }

        public Map<String, Map<String, Map<String, Integer>>> getMap() {
            return map;
        }

        public void createJSONFile (String jsonStr) throws IOException {
            String filename = "C:\\Users\\user\\IdeaProjects\\solution3\\src\\main\\java\\solution2\\output.json";
            FileWriter file = new FileWriter(filename);

            file.write(jsonStr);
            file.close();

//            return file;
        }
    }
}
