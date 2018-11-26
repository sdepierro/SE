import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JFrame {
    TextTitle sub = new TextTitle();
    TextTitle sub2 = new TextTitle();

        public Graph(DefaultTableModel data, int column, String imm, String vir, String dur, String rate, String pop, String inf) {
            initUI(data, column);
            sub.setText("Init. Immunity: " + imm + "%     Virulence: " + vir + "%     Duration: " + dur + " days     R0: " + rate);
            sub2.setText("Init. Population: " + pop + "     Init. Infected: " + inf);
            }
        private void initUI(DefaultTableModel data, int column) {
            
            XYDataset dataset = createDataset(data, column);
            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);
            
            add(chartPanel);

            pack();
            setTitle("Line chart");

            chart.addSubtitle(sub);
            chart.addSubtitle(sub2);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        
        private XYDataset createDataset(DefaultTableModel data, int column) {
            XYSeries series = new XYSeries("Number of People Infected");
            
            for (int i = 0; i < data.getRowCount(); i++) {
                series.add((double) i, Double.parseDouble(data.getValueAt(i, column).toString()));
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            
            return dataset;
        }
        
        private JFreeChart createChart(XYDataset dataset) {

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Disease Transmission Simulation",
                    "Time in Days",
                    "Number of People Infected",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));

            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.BLACK);

            chart.getLegend().setFrame(BlockBorder.NONE);

            chart.setTitle(new TextTitle("Disease Transmission Simulation",
                            new Font("Serif", java.awt.Font.BOLD, 18)
                    )
            );

            return chart;

        }
        }
        




