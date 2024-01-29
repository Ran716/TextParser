package frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import file.File;
import util.Person;

import community.CommunityInfo;

public class Frame extends JFrame {
    public JFrame frame = new JFrame();

    public JPanel jpl = new JPanel();

    JComboBox<String> comboBox = new JComboBox<>();

    public JLabel nameJLabel = new JLabel("请选择人物:");

    public JLabel closedRelationshipJTable_Tile = new JLabel("人物关系紧密程度");

    public JButton analyzeButton = new JButton("亲密度分析");

    public JButton backButton = new JButton("回退");

    private Image cxk;

    public Box VBox11 = new Box(BoxLayout.Y_AXIS);
    public Box VBox12 = new Box(BoxLayout.Y_AXIS);
    public Box VBox21 = new Box(BoxLayout.Y_AXIS);
    public Box VBox22 = new Box(BoxLayout.Y_AXIS);
    public Box VBox23 = new Box(BoxLayout.Y_AXIS);

    public JScrollPane jScrollPane = new JScrollPane();

    public ChartPanel chartPanel;

    public ChartPanel scatterPlotChartPanel;

    public ChartPanel stackedChartPanel;

    public ChartPanel stacked_spanChartPanel;

    public ChartPanel closedRelationshipChartPanel;

    public String ComboBox_selectedString;

    public JTextArea jta;

    public File fl = new File();

    public Object[][] data = new Object[10][10];

    public CommunityInfo communityInfo;

    public JComboBox<String> getComboBox() {
        return fl.createJComboBox(fl.pl.people);
    }

    public ChartPanel getChartPanel() {
        return fl.createChartPanel(fl.pl.people);
    }

    public ChartPanel getStackedChartPanel() {
        return fl.createStackedChartPanel(fl.pl.people);
    }

    public ChartPanel getStacked_spanChartPanel() {
        return fl.createStackedChartPanel_span(fl.pl.people);
    }

    public ChartPanel getScatterPlotChartPanel() {
        return fl.creatScatterPLotChartPanel(fl.pl.people);
    }

    public JScrollPane getRelationshipJTable() {

        fl.pl.getClosedRelationship();

        for (int i = 0; i < 10; i++) {
            data[i][0] = fl.pl.getPerson(i).getName();
            int index = 0;
            for (int j = 1; j <= 9; j++) {
                String tempString = fl.pl.getPerson(fl.pl.matrix_SortIndex[i][index]).getName();
                if (tempString != data[i][0]) {
                    data[i][j] = tempString;
                    index++;
                } else {
                    index++;
                    data[i][j] = fl.pl.getPerson(fl.pl.matrix_SortIndex[i][index]).getName();
                }

            }
        }
        String[] columnStrings = { "人物姓名", "紧密程度1", "紧密程度2", "紧密程度3",
                "紧密程度4", "紧密程度5", "紧密程度6", "紧密程度7", "紧密程度8",
                "紧密程度9" };
        JTable jtb = new JTable();

        jtb.setModel(new DefaultTableModel(data, columnStrings) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane scrollPane = new JScrollPane(jtb);

        return scrollPane;
    }

    public ChartPanel getClosedRelationshipChartPanel(String ComboBox_selectedString) {
        Person person = fl.pl.getPerson(ComboBox_selectedString);
        int matrix_index = person.matrix_index;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i < 10; i++) {

            String name = (String) data[matrix_index][i];
            dataset.addValue(fl.pl.matrix[matrix_index][i - 1], "亲密度", name);
        }
        JFreeChart chart = fl.createClosedRelationshipChart(dataset, person);
        chart.setBackgroundPaint(Color.WHITE);
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD, 18));

        // 设置横轴
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(null);
        Color color = new Color(30, 144, 255);
        plot.getRenderer().setSeriesPaint(0, color);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
        // 设置纵轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));

        ChartPanel chartPanel = new ChartPanel(chart);

        return chartPanel;
    }

    public JTextArea getJTextArea() {
        communityInfo = new CommunityInfo(data);
        JTextArea jta = new JTextArea();
        jta.append("\n" + "\n");
        jta.append("经算法判断，共有以下几个小团体：" + "\n" + "\n" + "\n");
        int count = 0;
        for (ArrayList<String> innerList : communityInfo.finalCommunities) {
            count++;
            if (count == 2 || count == 3) {
                continue;
            } else {
                String innerString = String.join(" ", innerList);
                jta.append("          " + innerString);
                jta.append("是一个小团体." + "\n" + "\n" + "\n");
            }
        }
        return jta;
    }

    public Frame() {

        frame.setTitle(
                "                                                                                                                                                        《三国演义》人物分析器");

        cxk = Toolkit.getDefaultToolkit().createImage("D:\\Code_sum\\Java\\textparser\\frame\\cxk.jpg");
        frame.setIconImage(cxk);
        frame.setLocation(240, 0);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.setLayout(new BorderLayout());

        nameJLabel.setFont(new Font("宋体", Font.BOLD, 18));
        jpl.add(nameJLabel);

        comboBox = getComboBox();

        jpl.add(comboBox);
        jpl.add(analyzeButton);
        jpl.add(backButton);
        jpl.setBackground(Color.PINK);
        jpl.setBorder(BorderFactory.createLineBorder(Color.black));
        jpl.setLayout(new FlowLayout());
        frame.add(jpl, BorderLayout.NORTH);

        scatterPlotChartPanel = getScatterPlotChartPanel();
        chartPanel = getChartPanel();
        VBox11.add(scatterPlotChartPanel);
        VBox11.add(chartPanel);
        VBox11.setBackground(Color.PINK);
        VBox11.setPreferredSize(new Dimension(600, 800));

        stackedChartPanel = getStackedChartPanel();
        stacked_spanChartPanel = getStacked_spanChartPanel();
        VBox12.add(stackedChartPanel);
        VBox12.add(stacked_spanChartPanel);
        VBox12.setBackground(Color.PINK);
        VBox12.setPreferredSize(new Dimension(600, 800));

        jScrollPane = getRelationshipJTable();
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closedRelationshipJTable_Tile.setFont(new Font("宋体", Font.BOLD, 18));
        closedRelationshipJTable_Tile.setAlignmentX(Component.CENTER_ALIGNMENT);
        VBox21.add(closedRelationshipJTable_Tile);
        VBox21.add(jScrollPane);
        VBox21.setPreferredSize(new Dimension(1000, 220));

        frame.add(VBox11, BorderLayout.WEST);
        frame.add(VBox12, BorderLayout.EAST);

        jta = getJTextArea();
        jta.setEditable(false);
        jta.setFont(new Font("宋体", Font.PLAIN, 16));
        jta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        VBox23.add(jta);
        VBox23.setPreferredSize(new Dimension(600, 800));

        frame.pack();

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedOption = comboBox.getSelectedItem();
                ComboBox_selectedString = (String) selectedOption;
                closedRelationshipChartPanel = getClosedRelationshipChartPanel(ComboBox_selectedString);

                VBox22.removeAll();
                VBox22.add(closedRelationshipChartPanel);
                VBox22.setPreferredSize(new Dimension(600, 800));

                frame.remove(VBox11);
                frame.remove(VBox12);

                frame.add(VBox21, BorderLayout.SOUTH);
                frame.add(VBox22, BorderLayout.WEST);
                frame.add(VBox23, BorderLayout.EAST);

                frame.revalidate();
                frame.repaint();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.remove(VBox21);
                frame.remove(VBox22);
                frame.remove(VBox23);

                frame.add(VBox11, BorderLayout.WEST);
                frame.add(VBox12, BorderLayout.EAST);

                frame.revalidate();
                frame.repaint();
            }
        });
    }
}