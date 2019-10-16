package com.yc.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.yc.dao.BillDao;
import com.yc.util.CreateChartUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

/**
 * 账单统计图弹窗
 * @author 沈俊羽
 *
 */
public class BillChartDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_year_time;
	private Text text_year_time1;
	private Text text_quarter;
	private Text text_month_time;
	private String r_type1 = "经济房";
	private String r_type2 = "高级房";
	private String r_type3 = "总统套房";
	private BillDao billDao=new BillDao();
	private Combo combo;
	private Combo combo_1;
	private Combo combo_2;
	private Combo combo_3;
	private Combo combo_4;
	private Label label_11;
	private Label label_9;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public BillChartDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setImage(SWTResourceManager.getImage(BillChartDialog.class, "/images/WindowIcon.png"));
		shell.setSize(609, 406);
		shell.setText("首席大酒店--账单统计图");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(10, 10, 583, 351);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("年度账单");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		
		Label label = new Label(composite, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(28, 84, 169, 20);
		label.setText("请输入查询年份范围：");
		
		text_year_time = new Text(composite, SWT.BORDER);
		text_year_time.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String yearTime=text_year_time.getText().trim();
				if (null==yearTime||"".equals(yearTime)) {
					SwtUtil.showMessage(getParent(), "提示", "请输入初始年份");
					return;
				}
			}
		});
		text_year_time.setBounds(203, 81, 87, 26);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setBounds(296, 84, 41, 20);
		label_1.setText("——");
		
		text_year_time1 = new Text(composite, SWT.BORDER);
		text_year_time1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String yearTime1=text_year_time1.getText().trim();
				if (null==yearTime1||"".equals(yearTime1)) {
					SwtUtil.showMessage(getParent(), "提示", "请输入截至日期");
					return;
				}
			}
		});
		text_year_time1.setBounds(343, 81, 87, 26);
		
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String time1 = text_year_time.getText().trim();// 2019
				String time2 = text_year_time1.getText().trim();// 2020
				String charType=combo.getText().trim();
				
				DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

				Map<String, Object> map1 = new HashMap<>();
				map1.put("R_TYPE", r_type1);
				Map<String, Object> map2 = new HashMap<>();
				map2.put("R_TYPE", r_type2);
				Map<String, Object> map3 = new HashMap<>();
				map3.put("R_TYPE", r_type3);

				Date date = null;
				Date date1 = null;// 2019

				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

				try {
					if ("".equals(time1) || null == time1 || "".equals(time2) || null == time2) {
						SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
						return;
					}
					date1 = format.parse(time1);// 2019
					long aa = date1.getTime();// 得到2019的毫秒
					// 得到经济房一年的账单收入
					List<Map<String, Object>> list1 = billDao.FindBillByRtype(map1);
					for (int j = 1; j <= (Integer.parseInt(time2) - Integer.parseInt(time1)); j++) {
						long bb=format.parse(String.valueOf(Integer.parseInt(time1)+j)).getTime();
						double price = 0;
						for (Map<String, Object> map : list1) {
							date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa <= cc && cc < bb) {
								price += Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "经济房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "经济房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("饼状图".equals(charType)) {
							
						}
						
						aa=bb;
					}
					
					//高级房
					List<Map<String, Object>> list2 = billDao.FindBillByRtype(map2);
					aa = date1.getTime();// 得到2019的毫秒
					for (int j = 1; j <= (Integer.parseInt(time2) - Integer.parseInt(time1)); j++) {
						long bb=format.parse(String.valueOf(Integer.parseInt(time1)+j)).getTime();
						double price = 0;
						for (Map<String, Object> map : list2) {
							date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa <= cc && cc < bb) {
								price += Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "高级房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "高级房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("饼状图".equals(charType)) {
							
						}
						aa=bb;
					}
					
					//总统套房
					List<Map<String, Object>> list3 = billDao.FindBillByRtype(map3);
					aa = date1.getTime();// 得到2019的毫秒
					for (int j = 1; j <= (Integer.parseInt(time2) - Integer.parseInt(time1)); j++) {
						long bb=format.parse(String.valueOf(Integer.parseInt(time1)+j)).getTime();
						double price = 0;
						for (Map<String, Object> map : list3) {
							date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa <= cc && cc < bb) {
								price += Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "总统套房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "总统套房",String.valueOf(Integer.parseInt(time1)+j-1)+"年");
						}else if ("饼状图".equals(charType)) {
							
						}
						aa=bb;
					}
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}

				if ("柱状图".equals(charType)) {
					CreateChartUtil.createHistogram(dataset1, "首席大酒店"+time1+"~"+time2+"年报表", "年份", "收入总值");
				}else if ("折线图".equals(charType)) {
					CreateChartUtil.createLineChart(dataset1, "首席大酒店"+time1+"~"+time2+"年报表", "年份", "收入总值");
				}else if ("饼状图".equals(charType)) {
					CreatePieChart1();
				}
			}
		});
		button.setBounds(139, 197, 98, 30);
		button.setText("生成报表");
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(121, 149, 76, 20);
		label_2.setText("报表类型：");
		
		combo = new Combo(composite, SWT.NONE);
		combo.setItems(new String[] {"柱状图", "折线图", "饼状图"});
		combo.setBounds(203, 146, 87, 28);
		combo.select(0);
		
		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("季度账单");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_1);
		
		Label label_3 = new Label(composite_1, SWT.NONE);
		label_3.setAlignment(SWT.RIGHT);
		label_3.setBounds(81, 83, 147, 20);
		label_3.setText("请输入查询年份：");
		
		text_quarter = new Text(composite_1, SWT.BORDER);
		text_quarter.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String quater=text_quarter.getText().trim();
				if (null==quater||"".equals(quater)) {
					SwtUtil.showMessage(getParent(), "提示", "请输入查询日期");
					return;
				}
			}
		});
		text_quarter.setBounds(234, 80, 85, 26);
		
		Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(124, 157, 104, 20);
		label_4.setText("报表类型：");
		
		combo_1 = new Combo(composite_1, SWT.NONE);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ("饼状图".equals(combo_1.getText().trim())) {
					combo_3.setVisible(true);
					label_9.setVisible(true);
				}else{
					combo_3.setVisible(false);
					label_9.setVisible(false);
				}
			}
		});
		combo_1.setItems(new String[] {"柱状图", "折线图", "饼状图"});
		combo_1.setBounds(234, 154, 104, 28);
		combo_1.select(0);
		
		
		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String time1 = text_quarter.getText().trim();// 2019
				String charType=combo_1.getText().trim();
				
				DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
				
				Map<String, Object> map1 = new HashMap<>();
				map1.put("R_TYPE", r_type1);
				Map<String, Object> map2 = new HashMap<>();
				map2.put("R_TYPE", r_type2);
				Map<String, Object> map3 = new HashMap<>();
				map3.put("R_TYPE", r_type3);

				Date date = null;
				Date date1 = null;// 2019

				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");// 数据库中的年份月份的格式
				SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM");
				try {
					if ("".equals(time1) || null == time1) {
						SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
						return;
					}
					System.out.println(text_quarter.getText().trim()+"-"+0);
					date1 = format2.parse(text_quarter.getText().trim()+"-"+0);// 2019
					long aa = date1.getTime();// 得到2019的毫秒

					// 得到经济房每个月份的账单收入
					List<Map<String, Object>> list1 = billDao.FindBillByRtype(map1);
					int i=1;
					for(int j=3;j<=12;j=j+3){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						System.out.println(time2);
						long bb=format2.parse(time2).getTime();
						double price=0;
						for (Map<String, Object> map : list1) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "经济房", "第"+i+"季度");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "经济套房", "第"+i+"季度");
						}else if ("饼状图".equals(charType)) {
							
						}
						
						i++;
						aa=bb;
					}
						
					// 得到高级房每个季度的账单收入
					List<Map<String, Object>> list2 = billDao.FindBillByRtype(map2);
					i=1;
					for(int j=3;j<=12;j=j+3){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						long bb=format2.parse(time2).getTime();
						double price=0;
						for (Map<String, Object> map : list2) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "高级房", "第"+i+"季度");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "高级套房", "第"+i+"季度");
						}else if ("饼状图".equals(charType)) {
							
						}
						i++;
						aa=bb;
					}

					// 得到总统套房每个季度的账单收入
					List<Map<String, Object>> list3 = billDao.FindBillByRtype(map3);
					i=1;
					for(int j=3;j<=12;j=j+3){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						long bb=format2.parse(time2).getTime();
						double price=0;
						for (Map<String, Object> map : list3) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "总统套房", "第"+i+"季度");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "总统套房", "第"+i+"季度");
						}else if ("饼状图".equals(charType)) {
							
						}
						i++;
						aa=bb;
					}
					
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
				if ("柱状图".equals(charType)) {
					CreateChartUtil.createHistogram(dataset1, "首席大酒店"+time1+"年季度报表", "季度", "收入总值");
				}else if ("折线图".equals(charType)) {
					CreateChartUtil.createLineChart(dataset1, "首席大酒店"+time1+"年季度报表", "季度", "收入总值");
				}else if ("饼状图".equals(charType)) {
					CreatePieChart2();
				}
				
			}
		});
		button_1.setBounds(234, 248, 98, 30);
		button_1.setText("生成报表");
		
		label_9 = new Label(composite_1, SWT.NONE);
		label_9.setAlignment(SWT.RIGHT);
		label_9.setBounds(363, 157, 76, 20);
		label_9.setText("第几季度：");
		label_9.setVisible(false);
		
		combo_3 = new Combo(composite_1, SWT.NONE);
		combo_3.setItems(new String[] {"第一季度", "第二季度", "第三季度", "第四季度"});
		combo_3.setBounds(445, 154, 98, 28);
		combo_3.select(0);
		combo_3.setVisible(false);
		
		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("月度账单");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_2);
		
		Label label_6 = new Label(composite_2, SWT.NONE);
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(84, 81, 126, 20);
		label_6.setText("请输入查询年份：");
		
		text_month_time = new Text(composite_2, SWT.BORDER);
		text_month_time.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String month=text_month_time.getText().trim();
				if (null==month||"".equals(month)) {
					SwtUtil.showMessage(getParent(), "提示", "请输入日期");
					return;
				}
			}
		});
		text_month_time.setBounds(216, 78, 86, 26);
		
		Label label_8 = new Label(composite_2, SWT.NONE);
		label_8.setBounds(134, 147, 76, 20);
		label_8.setText("报表类型：");
		
		combo_2 = new Combo(composite_2, SWT.NONE);
		combo_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ("饼状图".equals(combo_2.getText().trim())) {
					label_11.setVisible(true);
					combo_4.setVisible(true);
				}
			}
		});
		combo_2.setItems(new String[] {"柱状图", "折线图", "饼状图"});
		combo_2.setBounds(216, 144, 86, 28);
		combo_2.select(0);
		
		Button button_2 = new Button(composite_2, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String time1 = text_month_time.getText().trim();// 2019
				String charType=combo_2.getText().trim();
				
				DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
				
				
				Map<String, Object> map1 = new HashMap<>();
				map1.put("R_TYPE", r_type1);
				Map<String, Object> map2 = new HashMap<>();
				map2.put("R_TYPE", r_type2);
				Map<String, Object> map3 = new HashMap<>();
				map3.put("R_TYPE", r_type3);

				Date date = null;
				Date date1 = null;// 2019

				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");// 数据库中的年份月份的格式
				SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM");
				try {
					if ("".equals(time1) || null == time1) {
						SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
						return;
					}
					date1 = format2.parse(text_month_time.getText().trim()+"-"+00);// 2019
					long aa = date1.getTime();// 得到2019的毫秒

					// 得到经济房每个月份的账单收入
					List<Map<String, Object>> list1 = billDao.FindBillByRtype(map1);
					for(int j=1;j<=12;j++){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						long bb=format2.parse(time2).getTime();
						double price=0;
						for (Map<String, Object> map : list1) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "经济房", j+"月");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "经济房", j+"月");
						}else if ("饼状图".equals(charType)) {
							
						}
						aa=bb;
					}
						
					// 得到高级房每个月份的账单收入
					List<Map<String, Object>> list2 = billDao.FindBillByRtype(map2);
					aa = date1.getTime();// 得到2019的毫秒
					for(int j=1;j<=12;j++){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						long bb=format2.parse(time2).getTime();
						System.out.println(aa+"-----"+bb);
						double price=0;
						for (Map<String, Object> map : list2) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "高级房", j+"月");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "高级房", j+"月");
						}else if ("饼状图".equals(charType)) {
							
						}
						aa=bb;
					}

					// 得到总统套房每个月份的账单收入
					List<Map<String, Object>> list3 = billDao.FindBillByRtype(map3);
					aa = date1.getTime();// 得到2019的毫秒
					for(int j=1;j<=12;j++){
						String time2="";
						if (j+1==13) {
							time2=String.valueOf(Integer.parseInt(time1)+1)+"-"+"01";
						}else{
							time2=time1+"-"+String.valueOf(j+1);
						}
						long bb=format2.parse(time2).getTime();
						double price=0;
						for (Map<String, Object> map : list3) {
							date = format1.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
							long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
							if (aa<=cc&&cc<bb) {
								price+=Double.parseDouble(map.get("B_ALLPAY").toString());
							}
						}
						if ("柱状图".equals(charType)) {
							dataset1.addValue(price, "总统套房", j+"月");
						}else if ("折线图".equals(charType)) {
							dataset1.addValue(price, "总统套房", j+"月");
						}else if ("饼状图".equals(charType)) {
							
						}
						aa=bb;
					}
					
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
				if ("柱状图".equals(charType)) {
					CreateChartUtil.createHistogram(dataset1, "首席大酒店"+time1+"年月度报表", "月份", "收入总值");
				}else if ("折线图".equals(charType)) {
					CreateChartUtil.createLineChart(dataset1,"首席大酒店"+time1+"年月度报表","月份","收入总值");
				}else if ("饼状图".equals(charType)) {
					CreatePieChart3();
				}
			}
		});
		button_2.setBounds(220, 245, 98, 30);
		button_2.setText("生成报表");
		
		label_11 = new Label(composite_2, SWT.NONE);
		label_11.setAlignment(SWT.RIGHT);
		label_11.setBounds(325, 147, 76, 20);
		label_11.setText("第几月：");
		label_11.setVisible(false);
		
		combo_4 = new Combo(composite_2, SWT.NONE);
		combo_4.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
		combo_4.setBounds(407, 144, 76, 28);
		combo_4.select(0);
		combo_4.setVisible(false);

	}

	protected void CreatePieChart3() {
		String time1 = text_month_time.getText().trim();// 2019
		//String time2 = text_time2.getText().trim();// 2020
		String time3 = "";
		String time4 = "";
		String month=combo_4.getText().trim();//得到月份

		Map<String, Object> map1 = new HashMap<>();
		map1.put("R_TYPE", r_type1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("R_TYPE", r_type2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("R_TYPE", r_type3);
		
		Date date = null;
		Date date1 = null;// 2019
		Date date2 = null;// 2020
		
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM");

		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			if ("".equals(time1) || null == time1) {
				SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
				return;
			}
			
			long aa =0 ;
			long bb =0;
		
			//判断1月
			if("1".equals(month)||"1"==month){
				
				time3=time1+"-"+"00";
				time4=time1+"-"+"01";//一月底
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				  aa=date1.getTime();
				  bb=date2.getTime();
				
				 
				//判断2月
			}if("2".equals(month)||"2"==month){
				time3=time1+"-"+"01";//一月底
				time4=time1+"-"+"02";//二月底
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				
				//判断3月
			}if("3".equals(month)||"3"==month){
				time3=time1+"-"+"02";
				time4=time1+"-"+"03";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				//判断4月
			}if("4".equals(month)||"4"==month){
				time3=time1+"-"+"04";;
				time4=time1+"-"+"05";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				aa=date1.getTime();
				bb=date2.getTime();
			}	//判断5月
			if("5".equals(month)||"5"==month){
				time3=time1+"-"+"05"+"-"+"00";
				time4=time1+"-"+"06"+"-"+"00";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				  aa=date1.getTime();
				  bb=date2.getTime();
				
				 
				//判断6月
			}if("6".equals(month)||"6"==month){
				time3=time1+"-"+"06";
				time4=time1+"-"+"07";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				
				//判断7月
			}if("7".equals(month)||"7"==month){
				time3=time1+"-"+"07";
				time4=time1+"-"+"08";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				//判断8月
			}if("8".equals(month)||"8"==month){
				time3=time1+"-"+"08";
				time4=time1+"-"+"09";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				aa=date1.getTime();
				bb=date2.getTime();
			}
			
			///判断9月
			if("9".equals(month)||"9"==month){
				time3=time1+"-"+"09";
				time4=time1+"-"+"10";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				  aa=date1.getTime();
				  bb=date2.getTime();
				
				 
				//判断10月
			}if("10".equals(month)||"2"==month){
				time3=time1+"-"+"10";
				time4=time1+"-"+"11";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				
				//判断11月
			}if("11".equals(month)||"11"==month){
				time3=time1+"-"+"11";
				time4=time1+"-"+"12";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				//判断12月
			}if("12".equals(month)||"12"==month){
				time3=time1+"-"+"12";
				time4=String.valueOf(Integer.parseInt(time1)+1)+"-"+"00";
			
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				aa=date1.getTime();
				bb=date2.getTime();
			}
			
			System.out.println(aa);
			System.out.println(bb);
			
			
			// 得到经济房季度的账单收入
			List<Map<String, Object>> list1 = billDao.FindBillByRtype(map1);
				double price = 0;
				for (Map<String, Object> map : list1) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type1, price);
			
			// 得到高级房房一年的账单收入
			List<Map<String, Object>> list2 = billDao.FindBillByRtype(map2);
		
				price = 0;
				for (Map<String, Object> map : list2) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa<= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type2, price);
				
				
			
			// 得到总统套房房房一年的账单收入
			List<Map<String, Object>> list3 = billDao.FindBillByRtype(map3);

				price = 0;
				for (Map<String, Object> map : list3) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type3, price);
			


		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ChartPanel frame1;
		DefaultPieDataset data = dataset;
		JFreeChart chart = ChartFactory.createPieChart3D("收益总值", data, true, false, false);
		// 设置百分比
		PiePlot pieplot = (PiePlot) chart.getPlot();
		DecimalFormat df = new DecimalFormat("0.00%");// 获得一个DecimalFormat对象，主要是设置小数问题
		NumberFormat nf = NumberFormat.getNumberInstance();// 获得一个NumberFormat对象
		StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);// 获得StandardPieSectionLabelGenerator对象
		pieplot.setLabelGenerator(sp1);// 设置饼图显示百分比

		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setCircular(false);
		pieplot.setLabelGap(0.02D);

		pieplot.setIgnoreNullValues(true);// 设置不显示空值
		pieplot.setIgnoreZeroValues(true);// 设置不显示负值
		frame1 = new ChartPanel(chart, true);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
		PiePlot piePlot = (PiePlot) chart.getPlot();// 获取图表区域对象
		piePlot.setLabelFont(new Font("宋体", Font.BOLD, 10));// 解决乱码
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));

		JFrame frame = new JFrame("Java数据统计图");
		frame.getContentPane().setLayout(new GridLayout(1, 1, 10, 10));
		frame.getContentPane().add(frame1); // 添加饼状图
		frame.setBounds(50, 50, 800, 600);
		frame.setVisible(true);
		
	}

	protected void CreatePieChart2() {
		String time1 = text_quarter.getText().trim();// 2019
		//String time2 = text_time2.getText().trim();// 2020
		String time3 = "";
		String time4 = "";
		String jidu1=combo_3.getText().trim();//得到季度

		Map<String, Object> map1 = new HashMap<>();
		map1.put("R_TYPE", r_type1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("R_TYPE", r_type2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("R_TYPE", r_type3);

		

		
		Date date = null;
		Date date1 = null;// 2019
		Date date2 = null;// 2020
		
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM");

		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			if ("".equals(time1) || null == time1) {
				SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
				return;
			}
			//date1 = format1.parse(time1);// 2019
			//date2 = format.parse(time2);// 2020
			long aa =0 ;
			long bb =0;
		
			//判断第一季度
			if("第一季度".equals(jidu1)||"1"==jidu1){
				time3=time1+"-"+"00";;
				time4=time1+"-"+"03";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				  aa=date1.getTime();
				  bb=date2.getTime();
				
				 
				//判断第二季度
			}if("第二季度".equals(jidu1)||"2"==jidu1){
				time3=time1+"-"+"04";;
				time4=time1+"-"+"06";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				
				//判段第三季度
			}if("第三季度".equals(jidu1)||"3"==jidu1){
				time3=time1+"-"+"07";;
				time4=time1+"-"+"09";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				 aa=date1.getTime();
				 bb=date2.getTime();
				//判段第三季度
			}if("第四季度".equals(jidu1)||"4"==jidu1){
				time3=time1+"-"+"10";;
				time4=time1+"-"+"12";
				
				date1=format3.parse(time3);
				date2=format3.parse(time4);	
				
				aa=date1.getTime();
				bb=date2.getTime();
			}
			
			System.out.println(date1);
			System.out.println(date2);
			// 得到经济房季度的账单收入
			List<Map<String, Object>> list1 = billDao.FindBillByRtype(map1);
				double price = 0;
				for (Map<String, Object> map : list1) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type1, price);
			
			// 得到高级房的季度账单收入
			List<Map<String, Object>> list2 = billDao.FindBillByRtype(map2);
		
				price = 0;
				for (Map<String, Object> map : list2) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa<= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type2, price);
				
				
			
			// 得到总统套房的季度账单收入
			List<Map<String, Object>> list3 = billDao.FindBillByRtype(map3);

				price = 0;
				for (Map<String, Object> map : list3) {
					date = format2.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type3, price);
			


		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ChartPanel frame1;
		DefaultPieDataset data = dataset;
		JFreeChart chart = ChartFactory.createPieChart3D("收益总值", data, true, false, false);
		// 设置百分比
		PiePlot pieplot = (PiePlot) chart.getPlot();
		DecimalFormat df = new DecimalFormat("0.00%");// 获得一个DecimalFormat对象，主要是设置小数问题
		NumberFormat nf = NumberFormat.getNumberInstance();// 获得一个NumberFormat对象
		StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);// 获得StandardPieSectionLabelGenerator对象
		pieplot.setLabelGenerator(sp1);// 设置饼图显示百分比

		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setCircular(false);
		pieplot.setLabelGap(0.02D);

		pieplot.setIgnoreNullValues(true);// 设置不显示空值
		pieplot.setIgnoreZeroValues(true);// 设置不显示负值
		frame1 = new ChartPanel(chart, true);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
		PiePlot piePlot = (PiePlot) chart.getPlot();// 获取图表区域对象
		piePlot.setLabelFont(new Font("宋体", Font.BOLD, 10));// 解决乱码
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));

		JFrame frame = new JFrame("Java数据统计图");
		frame.getContentPane().setLayout(new GridLayout(1, 1, 10, 10));
		frame.getContentPane().add(frame1); // 添加饼状图
		frame.setBounds(50, 50, 800, 600);
		frame.setVisible(true);
		
	}

	/**
	 * 生成年度饼状图
	 */
	protected void CreatePieChart1() {
		String time11 = text_year_time.getText().trim();// 2019
		String time21 = text_year_time1.getText().trim();// 2020
		//String time3 = "";

		Map<String, Object> map11 = new HashMap<>();
		map11.put("R_TYPE", r_type1);
		Map<String, Object> map21 = new HashMap<>();
		map21.put("R_TYPE", r_type2);
		Map<String, Object> map31 = new HashMap<>();
		map31.put("R_TYPE", r_type3);


		Date date111 = null;// 2019
		Date date2 = null;// 2020

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat format21 = new SimpleDateFormat("yyyy-MM-dd");
	//	SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM");

		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			if ("".equals(time11) || null == time11 || "".equals(time21) || null == time21) {
				SwtUtil.showMessage(shell, "错误提示", "请写出查询时间");
				return;
			}
			date111 = format1.parse(time11);// 2019
			date2 = format1.parse(time21);// 2020
			long aa = date111.getTime();// 得到2019的毫秒
			long bb = date2.getTime();// 得到2020的毫秒
			
			// 得到经济房所有年的账单收入
			List<Map<String, Object>> list1 = billDao.FindBillByRtype(map11);
				aa = date111.getTime();//重新设定输入时间
				double price = 0;
				for (Map<String, Object> map : list1) {
					date111 = format21.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date111.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type1, price);
				aa=Integer.parseInt(time11)+1;//2019+1=2020
				
			

			// 得到高级房房所有年的账单收入
			List<Map<String, Object>> list2 = billDao.FindBillByRtype(map21);

			
				aa = date111.getTime();//重新设定输入时间
				
				 price = 0;
				for (Map<String, Object> map : list2) {
					date111 = format21.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date111.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type2, price);
				aa=Integer.parseInt(time11)+1;//2019+1=2020
				
			
			// 得到总统套房所有年的账单收入
			List<Map<String, Object>> list3 = billDao.FindBillByRtype(map31);


			
				aa = date111.getTime();//重新设定输入时间
			
				 price = 0;
				for (Map<String, Object> map : list3) {
					date111 = format21.parse(map.get("NOW_DATE").toString());// 得到选定房间类型的下的月份格式
					long cc = date111.getTime();// 得到选定类型的房间下月份的的时间毫秒
					if (aa <= cc && cc < bb) {
						price += Double.parseDouble(map.get("B_ALLPAY").toString());
					}
				}
				System.out.println(price);
				dataset.setValue(r_type3, price);
				aa=Integer.parseInt(time11)+1;//2019+1=2020
		

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ChartPanel frame1;
		DefaultPieDataset data = dataset;
		JFreeChart chart = ChartFactory.createPieChart3D("首席大酒店"+time11+"~"+time21+"收入", data, true, false, false);
		// 设置百分比
		PiePlot pieplot = (PiePlot) chart.getPlot();
		DecimalFormat df = new DecimalFormat("0.00%");// 获得一个DecimalFormat对象，主要是设置小数问题
		NumberFormat nf = NumberFormat.getNumberInstance();// 获得一个NumberFormat对象
		StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);// 获得StandardPieSectionLabelGenerator对象
		pieplot.setLabelGenerator(sp1);// 设置饼图显示百分比

		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setCircular(false);
		pieplot.setLabelGap(0.02D);

		pieplot.setIgnoreNullValues(true);// 设置不显示空值
		pieplot.setIgnoreZeroValues(true);// 设置不显示负值
		frame1 = new ChartPanel(chart, true);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
		PiePlot piePlot = (PiePlot) chart.getPlot();// 获取图表区域对象
		piePlot.setLabelFont(new Font("宋体", Font.BOLD, 10));// 解决乱码
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));

		JFrame frame = new JFrame("首席大酒店收入统计图");
		frame.getContentPane().setLayout(new GridLayout(1, 2, 10, 10));
		frame.getContentPane().add(frame1); // 添加饼状图
		frame.setBounds(50, 50, 800, 600);
		frame.setVisible(true);
		
	}
}
