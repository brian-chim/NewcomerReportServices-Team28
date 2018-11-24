package application.util;

import org.jfree.chart.JFreeChart;

public class ReportPage {

    public JFreeChart chart;
    public String title;
    public String chartData;

    public ReportPage(JFreeChart chart, String title, String chartData) {
        this.chart = chart;
        this.title = title;
        this.chartData = chartData;
    }
}
