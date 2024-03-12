package solution2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class FineJSONObject {
    private Map<String, Map<String, Map<String, Integer>>> map = new HashMap<>();

    public LinkedHashMap<String, Map<String, Integer>> sortJSON(Map<String, Map<String, Integer>> map) {
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
}
