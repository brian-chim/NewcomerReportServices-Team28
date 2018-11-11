package application.util;

import application.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportGenerator {

    /**
     * Given a list of column names generate a summary report based on data in DB
     * @param streamName the name of the stream to query
     * @param columnList a list of the column names corresponding to the info you wish to query
     * @return a string representing the report
     */
    public static String generateSummaryReport(String streamName, ArrayList<String> columnList) {
        String result = "Summary Report of Frequencies of " + streamName;
        // get the list of columns from the db
        ArrayList<HashMap<String, String>> columnListResult = DatabaseHandler.selectCols(streamName, columnList);
        ArrayList<HashMap<String, Integer>> columnResults = new ArrayList<>();
        if (columnListResult.size() >= 1) {
            HashMap<String, String> columnMap = columnListResult.get(0);
            for (String key : columnMap.keySet()) {
                // go through all the column elements and count the values
                HashMap<String, Integer> frequencyResult = new HashMap<>();
                for (int i = 0; i < columnListResult.size(); i++) {
                    String value = columnListResult.get(i).get(key);
                    if (frequencyResult.containsKey(value)) {
                        frequencyResult.replace(value, frequencyResult.get(value) + 1);
                    } else {
                        frequencyResult.put(value, 1);
                    }
                }
                // formulate the string out of the hashmaps
                result += "\n" + EmploymentStreamColumnQueries.fromDbName(key) +  ":" + "\n";
                for (String value: frequencyResult.keySet()) {
                    result += "\n" + value + ": " + frequencyResult.get(value) + "\n";
                }
                columnResults.add(frequencyResult);
            }
        }
        return result;
    }
}
