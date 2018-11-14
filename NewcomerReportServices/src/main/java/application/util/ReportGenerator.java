package application.util;

import application.database.DatabaseHandler;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportGenerator {

    /**
     * Given a list of column names generate a summary report based on data in DB and generate pie charts as well
     * @param streamName the name of the stream to query
     * @param columnList a list of the column names corresponding to the info you wish to query
     * @param path the path in which to write the pdf files
     * @return a string representing the report
     */
    public static String generateSummaryReport(String streamName, ArrayList<String> columnList, String path) {
        String result = "Summary Report of Frequencies of " + streamName;
        // get the list of columns from the db
        ArrayList<HashMap<String, String>> columnListResult = DatabaseHandler.selectCols(streamName, columnList);
        ArrayList<HashMap<String, Integer>> columnResults = new ArrayList<>();
        ArrayList<String> columnTitles = new ArrayList<>();
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
                String title = EmploymentStreamColumnQueries.fromDbName(key).toString();
                result += "\n" + title +  ":" + "\n";
                String tempResult = "";
                for (String value : frequencyResult.keySet()) {
                    result += "\n" + value + ": " + frequencyResult.get(value) + "\n";
                    tempResult += "\n" + value + ": " + frequencyResult.get(value) + "\n";
                }
                // generate a chart for this column
                writeChartToPDF(generatePieChart(frequencyResult, streamName), 500, 400, path + title + ".pdf", tempResult, "Summary Graph of " + title);
                columnResults.add(frequencyResult);
            }
        }
        return result;

    }

    /**
     * Generates a pie chart.
     * @param frequencyResult the dataset
     * @param streamName name of the stream to place as title
     * @return a Pie Chart representatio using JFreeChart object
     */
    private static JFreeChart generatePieChart(HashMap<String, Integer> frequencyResult, String streamName) {
        DefaultPieDataset dataSet = new DefaultPieDataset();

        // loop through all the frequencies and add it to the data set
        for (String value : frequencyResult.keySet()) {
            dataSet.setValue(value, frequencyResult.get(value));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                streamName, dataSet, true, true, false);

        return chart;
    }

    /**
     * Appends a chart along with the table to a pdf file.
     * Followed the tutorial from: http://www.vogella.com/tutorials/JavaPDF/article.html
     * @param chart JFreeChart that can be any type of chart (bar graph, pie, etc.)
     * @param width the width of the chart in px
     * @param height the height of the chart in px
     * @param fileName the filename tha you wish to create
     */
    private static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName, String tableData, String title) {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(20, 20, width,
                    height);

            chart.draw(graphics2d, rectangle2d);
            document.add(new Paragraph(title));
            document.add(new Paragraph(tableData));
            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
