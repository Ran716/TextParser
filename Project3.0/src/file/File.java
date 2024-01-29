package file;

import util.*;
import java.awt.Color;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.*;
import javax.swing.JComboBox;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Font;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;

public class File {
    public String fileName = "D:/Code_sum/Java/Project3.0/Project3.0/novel.txt";

    public StringBuffer stringBuffer = new StringBuffer();
    public People pl = new People();

    public void init(Map<String, Person> people) {
        BufferedReader br = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    stringBuffer.append(line);
                }
            }

            pl.novel_length = stringBuffer.length();

            for (Map.Entry<String, Person> entry : people.entrySet()) {

                Person person = entry.getValue();

                checkName(person);

                checkAliases(person);
                Collections.sort(person.indexList);
                person.span = person.lastindex - person.firstindex;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkName(Person person) {
        String name = person.getName();
        Pattern pattern = Pattern.compile(name);
        Matcher matcher = pattern.matcher(stringBuffer);

        int first = stringBuffer.indexOf(name) + 1;

        if (person.firstindex == -1) {
            person.firstindex = first;
        } else if (first < person.firstindex) {
            person.firstindex = first;
        }
        int last = stringBuffer.lastIndexOf(name) + 1;

        if (person.lastindex == -1) {
            person.lastindex = last;

        } else if (last > person.lastindex) {
            person.lastindex = last;
        }

        while (matcher.find()) {
            person.times++;
            person.indexList.add(matcher.start() + 1);
        }
    }

    public void checkAliases(Person person) {

        List<String> aliases = person.aliases;
        for (String alias : aliases) {

            Pattern pattern = Pattern.compile(alias);
            Matcher matcher = pattern.matcher(stringBuffer);

            int first = stringBuffer.indexOf(alias) + 1;

            if (person.firstindex == -1) {
                person.firstindex = first;
            } else if (first < person.firstindex) {
                person.firstindex = first;
            }
            int last = stringBuffer.lastIndexOf(alias) + 1;
            if (person.lastindex == -1) {
                person.lastindex = last;
            } else if (last > person.lastindex) {
                person.lastindex = last;
            }

            while (matcher.find()) {
                person.times++;
                person.indexList.add(matcher.start() + 1);
            }
        }
    }

    public JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "人物出现次数", // 标题
                "", // 横轴标签
                "次数", // 纵轴标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向
                false, // 是否显示图例
                true, // 是否生成工具提示
                false // 是否生成URL链接
        );

        return barChart;
    }

    public JFreeChart createStackedBarChart(DefaultCategoryDataset dataset) {
        JFreeChart stackedBarChart = ChartFactory.createStackedBarChart(
                "人物跨度",
                "",
                "",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false);

        return stackedBarChart;
    }

    public JFreeChart createStackedBarChart_span(DefaultCategoryDataset dataset) {
        JFreeChart stackedBarChart_span = ChartFactory.createStackedBarChart(
                "跨度篇幅",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        return stackedBarChart_span;
    }

    public JFreeChart createScatterPlot(DefaultXYDataset dataset) {
        JFreeChart ScatterPlot = ChartFactory.createScatterPlot(
                "人物散点图",
                "位置",
                "次数",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        return ScatterPlot;
    }

    public ChartPanel createChartPanel(Map<String, Person> people) {

        List<Map.Entry<String, Person>> list = new ArrayList<Map.Entry<String, Person>>(people.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Person>>() {
            @Override
            public int compare(Map.Entry<String, Person> p1, Map.Entry<String, Person> p2) {
                Integer times1 = p1.getValue().times;
                Integer times2 = p2.getValue().times;

                return times1.compareTo(times2);
            }
        });
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Person> map : list) {
            String name = map.getKey();
            int times = map.getValue().times;
            dataset.addValue(times, "次数", name);
        }

        JFreeChart barChart = createBarChart(dataset);
        barChart.setBackgroundPaint(Color.WHITE);
        TextTitle textTitle = barChart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD, 18));

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(null);

        Color color = new Color(30, 144, 255);
        plot.getRenderer().setSeriesPaint(0, color);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        ChartPanel chartPanel = new ChartPanel(barChart);

        return chartPanel;
    }

    public ChartPanel createStackedChartPanel(Map<String, Person> people) {

        List<Map.Entry<String, Person>> list = new ArrayList<Map.Entry<String, Person>>(people.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Person>>() {
            @Override
            public int compare(Map.Entry<String, Person> p1, Map.Entry<String, Person> p2) {
                Double span1 = p1.getValue().span;
                Double span2 = p2.getValue().span;

                return span2.compareTo(span1);
            }
        });
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Person> map : list) {
            String name = map.getKey();
            Person person = map.getValue();
            dataset.addValue((double) (person.firstindex / pl.novel_length), "", name);
            dataset.addValue((double) ((person.lastindex - person.firstindex) / pl.novel_length), "跨度", name);
        }

        JFreeChart stackedBarChart = createStackedBarChart(dataset);
        stackedBarChart.setBackgroundPaint(Color.WHITE);
        TextTitle textTitle = stackedBarChart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD, 18));

        CategoryPlot plot = stackedBarChart.getCategoryPlot();
        plot.setBackgroundPaint(null);
        Color color = new Color(61, 145, 64);
        plot.getRenderer().setSeriesPaint(1, color);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 1);
        rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
        StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 0, 0, 0));

        ChartPanel stackedChartPanel = new ChartPanel(stackedBarChart);

        return stackedChartPanel;
    }

    public ChartPanel createStackedChartPanel_span(Map<String, Person> people) {

        List<Map.Entry<String, Person>> list = new ArrayList<Map.Entry<String, Person>>(people.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Person>>() {
            @Override
            public int compare(Map.Entry<String, Person> p1, Map.Entry<String, Person> p2) {
                Double span1 = p1.getValue().span;
                Double span2 = p2.getValue().span;

                return span2.compareTo(span1);
            }
        });
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Person> map : list) {
            String name = map.getKey();
            Person person = map.getValue();
            dataset.addValue(0, "", name);
            dataset.addValue(person.lastindex - person.firstindex, "跨度", name);
        }

        JFreeChart stackedBarChart = createStackedBarChart_span(dataset);
        stackedBarChart.setBackgroundPaint(Color.WHITE);
        TextTitle textTitle = stackedBarChart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD, 18));

        CategoryPlot plot = stackedBarChart.getCategoryPlot();
        plot.setBackgroundPaint(null);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, pl.novel_length);
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 0, 0, 0));

        ChartPanel stackedChartPanel = new ChartPanel(stackedBarChart);

        return stackedChartPanel;
    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    public ChartPanel creatScatterPLotChartPanel(Map<String, Person> people) {
        DefaultXYDataset dataset = new DefaultXYDataset();

        for (Map.Entry<String, Person> map : people.entrySet()) {
            Person person = map.getValue();
            int index = 1;
            person.scatterArray = new double[2][person.indexList.size()];
            for (int i = 0; i < person.indexList.size(); i++) {
                person.scatterArray[0][i] = person.indexList.get(i); // x
                person.scatterArray[1][i] = index; // y
                index++;
            }

            dataset.addSeries(person.name, person.scatterArray);
        }

        JFreeChart ScatterPLot = createScatterPlot(dataset);

        ScatterPLot.setBackgroundPaint(Color.WHITE);

        TextTitle textTitle = ScatterPLot.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD, 18));

        XYPlot plot = ScatterPLot.getXYPlot();
        plot.setBackgroundPaint(null);

        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        XYItemRenderer renderer = plot.getRenderer();
        for (int i = 0; i < 10; i++) {
            renderer.setSeriesPaint(i, getRandomColor());
        }
        ChartPanel scatterPLotChartPanel = new ChartPanel(ScatterPLot);

        LegendTitle legend = scatterPLotChartPanel.getChart().getLegend(); // 获取图例对象
        legend.setItemFont(new Font("宋体", Font.PLAIN, 12));

        return scatterPLotChartPanel;
    }

    public JComboBox<String> createJComboBox(Map<String, Person> people) {
        JComboBox<String> comboBox = new JComboBox<>();
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            String name = entry.getKey();
            comboBox.addItem(name);
        }

        return comboBox;
    }

    public JFreeChart createClosedRelationshipChart(DefaultCategoryDataset dataset, Person person) {
        JFreeChart barChart = ChartFactory.createBarChart(
                person.getName() + "与其余人物亲密度",
                "",
                "亲密度",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        return barChart;
    }

    public File() {
        init(this.pl.people);
    }

}
