package application.util;

import application.database.DatabaseHandler;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
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
        ArrayList<ReportPage> pieCharts = new ArrayList<>();
        ArrayList<ReportPage> barCharts = new ArrayList<>();
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
                String title = "";
                if(streamName.equals(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES.getDbName())) {
                	title = EmploymentStreamColumnQueries.fromDbName(key).toString();
                } else if (streamName.equals(DatabaseServiceStreams.NEEDSASSESSMENT.getDbName())) {
                	title = NeedsAssessmentsColumnQueries.fromDbName(key).toString();
                } else if (streamName.equals(DatabaseServiceStreams.COMMUNITYCONN.getDbName())) {
                	title = CommunityConnectionsColumnQueries.fromDbName(key).toString();
                } else if (streamName.equals(DatabaseServiceStreams.INFOANDORIENTATION.getDbName())) {
                    title = InfoOrientationColumnQueries.fromDbName(key).toString();
                }

                result += "\n" + title +  ":" + "\n";
                String tempResult = "";
                for (String value : frequencyResult.keySet()) {
                    result += "\n" + value + ": " + frequencyResult.get(value) + "\n";
                    tempResult += "\n" + value + ": " + frequencyResult.get(value) + "\n";
                }
                // generate a pie chart and bar graph for this column
                pieCharts.add(new ReportPage(generatePieChart(frequencyResult, streamName), title, tempResult));
                barCharts.add(new ReportPage(generateBarChart(frequencyResult, streamName), title, tempResult));
                columnResults.add(frequencyResult);
            }
        }
        writeChartToPDF(pieCharts, 500, 400, path + streamName + "-PieCharts.pdf");
        writeChartToPDF(barCharts, 500, 400, path + streamName + "-BarCharts.pdf");

        return result;

    }

    /**
     * Generates a pie chart.
     * @param frequencyResult the dataset
     * @param streamName name of the stream to place as title
     * @return a Pie Chart representation using JFreeChart object
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
     * Generates a bar chart.
     * @param frequencyResult the dataset
     * @param streamName name of the stream to place as title
     * @return a Bar Chart representation using JFreeChart object
     */
    private static JFreeChart generateBarChart(HashMap<String, Integer> frequencyResult, String streamName) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        // loop through all the frequencies and add it to the data set
        for (String value : frequencyResult.keySet()) {
            dataSet.setValue(frequencyResult.get(value), "Value", value);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                streamName, "Value", "Frequency", dataSet,  PlotOrientation.VERTICAL, false, true, false);

        return chart;
    }

    /**
     * Appends a chart along with the table to a pdf file.
     * Followed the tutorial from: http://www.vogella.com/tutorials/JavaPDF/article.html
     * @param charts Arraylist of ReportPage objects that contains the following data: String tableData, String title, JFreeChart chart. JFreeChart can be any type of chart (bar graph, pie, etc.)
     * @param width the width of the chart in px
     * @param height the height of the chart in px
     * @param fileName the filename tha you wish to create
     */
    private static void writeChartToPDF(ArrayList<ReportPage> charts, int width, int height, String fileName) {
        PdfWriter writer = null;

        Document document = new Document();
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();

        for (ReportPage page : charts) {
            try {
                PdfContentByte contentByte = writer.getDirectContent();
                PdfTemplate template = contentByte.createTemplate(width, height);
                Graphics2D graphics2d = template.createGraphics(width, height,
                        new DefaultFontMapper());
                Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                        height);

                page.chart.draw(graphics2d, rectangle2d);
                document.add(new Paragraph(page.title));
                document.add(new Paragraph(page.chartData));
                graphics2d.dispose();
                contentByte.addTemplate(template, 0, 0);
                document.newPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        document.close();
    }
}
