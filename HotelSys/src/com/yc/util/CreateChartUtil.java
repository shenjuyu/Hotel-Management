package com.yc.util;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class CreateChartUtil {

	/**
	 * 柱状图
	 * @param dataset1
	 * @param chartName
	 * @param XName
	 * @param YName
	 */
	public static void createHistogram(DefaultCategoryDataset dataset1,String chartName,String XName,String YName){
		CategoryDataset dataset = dataset1;
		JFreeChart chart = ChartFactory.createBarChart3D(chartName, // 图表标题
				XName, // 目录轴的显示标签
				YName, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false // 是否生成URL链接
		);

		// 从这里开始
		CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		// ValueAxis价值轴
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体

		// 到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
		ChartPanel frame1 = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame（框架）
		JFrame frame = new JFrame("首席大酒店收入统计图");
		frame.getContentPane().setLayout(new GridLayout(1, 2, 10, 10));
		frame.getContentPane().add(frame1);
		frame.setBounds(50, 50, 800, 600);
		frame.setVisible(true);
	}
	
	/**
	 * 折线图
	 * @param dataset1
	 * @param chartName
	 * @param XName
	 * @param YName
	 */
	public static void createLineChart(DefaultCategoryDataset dataset1,String chartName,String XName,String YName){
		CategoryDataset mDataset = dataset1;
		JFreeChart mChart = ChartFactory.createLineChart(chartName,// 图名字
				XName,// 横坐标
				YName,// 纵坐标
				mDataset,// 数据集
				PlotOrientation.VERTICAL, true, // 显示图例
				true, // 采用标准生成器
				false);// 是否生成超链接
 
		// 从这里开始
		CategoryPlot plot = mChart.getCategoryPlot();// 获取图表区域对象
		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		rangeAxis.setUpperMargin(0.5);
		// ValueAxis价值轴
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		mChart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		mChart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体

		// 到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
		
		ChartFrame mChartFrame = new ChartFrame("首席大酒店收入统计图", mChart);
		mChartFrame.pack();
		mChartFrame.setBounds(50,50, 800, 600);
		mChartFrame.setVisible(true);
	}
	
//	public static void createPieChart(){

//	}
}
