package com.yc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.BillDao;
import com.yc.dao.EnterDao;
import com.yc.dao.RoomDao;
import com.yc.util.AdminUtil;
import com.yc.util.PlaySound;
import com.yc.util.Printer;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
/**
 * 退房管理界面   （客户结算）
 * @author 沈俊羽
 *
 */
public class CheckOutComposite extends Dialog {

	protected Object result;
	protected Shell shell;
	private BillDao billDao=new BillDao();
	private EnterDao enterDao=new EnterDao();
	private RoomDao roomDao=new RoomDao();
	private Text text_billId;//账单号
	private Text text_time;//当前时间
	private Text text_r_type;//房间类型
	private Text text_c_name;//客户姓名
	private Text text_c_tel;//客户手机号
	private Text text_enterDate;//入住时间
	private Text text_outDate;//退房时间
	private Text text_deposit;//押金
	private Text text_cost;//房间费用
	private Text text_otherPrice;//其他消费
	private Text text_penalty;//罚金
	private Text text_shouldPay;//本次应缴
	private Text text_return;//退还
	private Text text_allCost;//本次服务共消费
	private Combo combo_r_id;//房间编号
	private Text text_days;
	private String price;//房价/天

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CheckOutComposite(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--退房管理");
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setImage(SWTResourceManager.getImage(CheckOutComposite.class, "/images/WindowIcon.png"));
		shell.setSize(713, 762);
		shell.setText(getText());
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		
		Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(191, 99, 76, 20);
		label.setText("账单号：");
		
		text_billId = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_billId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_billId.setBounds(273, 96, 196, 26);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_1.setFont(SWTResourceManager.getFont("黑体", 13, SWT.BOLD));
		label_1.setBounds(223, 35, 207, 26);
		label_1.setText("首席大酒店消费清单");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(191, 144, 76, 20);
		label_2.setText("时间：");
		
		text_time = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_time.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_time.setBounds(273, 141, 196, 26);
		
		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(0, 185, 707, 12);
		
		Label label_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setBounds(0, 74, 707, 8);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setBounds(36, 202, 76, 20);
		label_5.setText("房间号：");
		
		combo_r_id = new Combo(shell, SWT.READ_ONLY);
		combo_r_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//选择房间号后将房间的信息和客户信息以及入住信息填充到各个文本框中
		combo_r_id.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String r_id=combo_r_id.getText().trim();//房间号
				//填充账单号
				text_billId.setText(createBillId(r_id));
				//显示当前时间
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				text_time.setText(dateFormat.format(date));
				try {
					Map<String, Object> map=enterDao.findMessageByRid(r_id);
					if (null==map||map.size()<=0) {
						return;
					}
					text_r_type.setText(StringUtil.objToString(map.get("R_TYPE")));
					text_deposit.setText(StringUtil.objToString(map.get("R_DEPOSIT")));
					price=StringUtil.objToString(map.get("R_PRICE"));
					text_c_name.setText(StringUtil.objToString(map.get("C_NAME")));
					text_c_tel.setText(StringUtil.objToString(map.get("C_TEL")));
					text_enterDate.setText(StringUtil.objToString(map.get("JOIN_DATE")));//入住时间
					text_penalty.setText(StringUtil.objToString(map.get("CP")));
					
					SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
					Date date2=new Date();//当前时间
					text_outDate.setText(dateFormat2.format(date2));
					long currenttime=date2.getTime();//当前时间  毫秒
					Date date3=dateFormat2.parse(StringUtil.objToString(map.get("JOIN_DATE")));
					long entertime=date3.getTime();//入住时间  毫秒
					int days=(int) ((currenttime-entertime)/1000/3600/24);
					//如果入住表中的入住时间比当前时间-入住时间短则显示入住时间
					if (days>Integer.parseInt(StringUtil.objToString(map.get("E_DAY")))) {
						text_days.setText(StringUtil.objToString(map.get("E_DAY")));
					}else{
						text_days.setText(String.valueOf(days));
					}
					double cost=Double.parseDouble(price)*Double.parseDouble(text_days.getText().trim());
					text_cost.setText(String.valueOf(cost));
					
					
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示",e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		combo_r_id.setBounds(118, 199, 171, 28);
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(36, 259, 76, 20);
		label_6.setText("房间类型：");
		
		text_r_type = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_r_type.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_r_type.setBounds(118, 256, 171, 26);
		
		Label label_7 = new Label(shell, SWT.RIGHT);
		label_7.setBounds(393, 203, 76, 20);
		label_7.setText("客户姓名：");
		
		text_c_name = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_c_name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_c_name.setBounds(475, 203, 171, 26);
		
		Label label_8 = new Label(shell, SWT.RIGHT);
		label_8.setBounds(393, 256, 76, 20);
		label_8.setText("手机号：");
		
		text_c_tel = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_c_tel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_c_tel.setBounds(475, 253, 171, 26);
		
		Label label_9 = new Label(shell, SWT.RIGHT);
		label_9.setText("入住时间：");
		label_9.setBounds(35, 310, 77, 20);
		
		text_enterDate = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_enterDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_enterDate.setBounds(118, 308, 171, 26);
		
		Label label_10 = new Label(shell, SWT.RIGHT);
		label_10.setBounds(36, 410, 76, 20);
		label_10.setText("退房时间：");
		
		text_outDate = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_outDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_outDate.setBounds(118, 408, 171, 26);
		
		Label label_11 = new Label(shell, SWT.RIGHT);
		label_11.setBounds(393, 313, 76, 20);
		label_11.setText("房间押金：");
		
		text_deposit = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_deposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_deposit.setBounds(475, 310, 171, 26);
		
		Label label_12 = new Label(shell, SWT.RIGHT);
		label_12.setBounds(393, 363, 76, 20);
		label_12.setText("房间费用：");
		
		text_cost = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_cost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_cost.setBounds(475, 360, 171, 26);
		
		Label label_13 = new Label(shell, SWT.RIGHT);
		label_13.setBounds(393, 414, 76, 20);
		label_13.setText("其他消费：");
		
		text_otherPrice = new Text(shell, SWT.BORDER);
		text_otherPrice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//将其他消费、应收、房间费用、退还押金、本次服务共收添加到文本框中
		text_otherPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String otherPrice=text_otherPrice.getText().trim();
				double othprice=0;
				if (null==otherPrice||"".equals(otherPrice)) {//其他消费
					othprice=0;
				}else{
					othprice=Double.parseDouble(otherPrice);
				}
				if ("".equals(price)) {
					price="0";
				}
				String days=text_days.getText().trim();//入住天数
				double day=0;
				if (null==days||"".equals(days)) {
					day=0;
				}else{
					day=Double.parseDouble(days);
				}
				double cost=Double.parseDouble(price)*day;//房间费用
				double shouldPay=cost+othprice;
				text_shouldPay.setText(String.valueOf(shouldPay));//本次应收
				
				String deposite=text_deposit.getText().trim();
				double deposite1=0;
				if (null==deposite||"".equals(deposite)) {//押金
					deposite1=0;
				}else{
					deposite1=Double.parseDouble(deposite);
				}
				text_return.setText(String.valueOf(deposite1));//本次应退
				
				String violatemoney=text_penalty.getText().trim();//违约金
				double violatemoney1=0;
				if (null==violatemoney||"".equals(violatemoney)) {
					violatemoney1=0;
				}else{
					violatemoney1=Double.parseDouble(violatemoney);
				}
				
				text_allCost.setText(String.valueOf(violatemoney1+cost+othprice));//本次服务共收
			}
		});
		text_otherPrice.setBounds(475, 409, 171, 26);
		
		Label label_14 = new Label(shell, SWT.RIGHT);
		label_14.setBounds(35, 460, 77, 20);
		label_14.setText("违约金：");
		
		text_penalty = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_penalty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_penalty.setBounds(118, 457, 171, 26);
		
		Label label_15 = new Label(shell, SWT.NONE);
		label_15.setAlignment(SWT.RIGHT);
		label_15.setBounds(191, 527, 76, 20);
		label_15.setText("应收：");
		
		text_shouldPay = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_shouldPay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_shouldPay.setBounds(273, 524, 171, 26);
		
		Label label_16 = new Label(shell, SWT.NONE);
		label_16.setAlignment(SWT.RIGHT);
		label_16.setBounds(191, 571, 76, 20);
		label_16.setText("退还：");
		
		text_return = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_return.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_return.setBounds(273, 568, 171, 26);
		
		Label label_17 = new Label(shell, SWT.RIGHT);
		label_17.setBounds(149, 616, 118, 20);
		label_17.setText("本次服务共收：");
		
		text_allCost = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_allCost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_allCost.setBounds(273, 613, 171, 26);
		
		Button button = new Button(shell, SWT.NONE);
		//结算
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String billId=text_billId.getText().trim();//账单号
				String nowDate=text_time.getText().trim();//结算时间
				String rid=combo_r_id.getText().trim();//房间号
				String state="空闲";//房间状态
				String state1=text_r_type.getText().trim();//房间类型
				String enterDate=text_enterDate.getText().trim();//入住时间
				String dayNum=text_days.getText().trim();//入住天数
				String outDate=text_outDate.getText().trim();//离店时间
				String violatemoney=text_penalty.getText().trim();//违约金
				String cName=text_c_name.getText().trim();//客户姓名
				String cTel=text_c_tel.getText().trim();//客户电话
				String deposit=text_deposit.getText().trim();//房间押金
				String cost=text_shouldPay.getText().trim();//房间费用
				String otherPrice=text_otherPrice.getText().trim();//其他消费
				String shouldPay=text_shouldPay.getText().trim();//本次应收
				String returnPrice=text_return.getText().trim();//退还
				String allCost=text_allCost.getText().trim();//本次服务共收
				
				
				List<List<Object>> params=new ArrayList<List<Object>>();
				List<Object> param=new ArrayList<Object>();//删除入住表数据用
				param.add(rid);
				params.add(param);
				
				List<Object> param1=new ArrayList<Object>();//更新房间状态用
				param1.add(state);
				param1.add(rid);
				params.add(param1);
				
				List<Object> param2=new ArrayList<Object>();//添加账单表用
				param2.add(billId);//账单号
				param2.add(nowDate);//当前时间
				param2.add(rid);//房间号
				param2.add(state1);//房间类型
				param2.add(enterDate);//入住时间
				param2.add(dayNum);//天数
				param2.add(outDate);//离店时间
				param2.add(violatemoney);//违约金
				param2.add(cName);//客户姓名
				param2.add(cTel);//手机号
				param2.add(deposit);//押金
				param2.add(cost);//房间费用
				param2.add(otherPrice);//其他消费
				param2.add(shouldPay);//本次应收
				param2.add(returnPrice);//应退还
				param2.add(allCost);//本次服务共收
				params.add(param2);
				try {
					int i=billDao.updateBillEnterRoom(params);//执行更新房间状态  删除入住表对应数据  添加账单表的数据
					if (i>0) {
						SwtUtil.showMessage(getParent(), "温馨提示", "退房成功\n欢迎下次光临(≧∇≦)ﾉ");
						text_allCost.setText("");
						text_billId.setText("");
						text_c_name.setText("");
						text_c_tel.setText("");
						text_cost.setText("");
						text_days.setText("");
						text_deposit.setText("");
						text_enterDate.setText("");
						text_otherPrice.setText("");
						text_outDate.setText("");
						text_penalty.setText("");
						text_r_type.setText("");
						text_return.setText("");
						text_shouldPay.setText("");
						text_time.setText("");
						showRoomId();
						String cashier=AdminUtil.adminid;
						Printer.printBill(rid, dayNum, state1, price, deposit,
								otherPrice, billId, cashier, nowDate, shouldPay, 
								returnPrice, allCost, cName, cTel);
					}else{
						SwtUtil.showMessage(getParent(), "错误提示", "退房失败...");
					}
				} catch (SQLException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(297, 675, 98, 30);
		button.setText("结算");
		
		Label label_18 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_18.setBounds(0, 500, 707, 13);
		
		Label label_19 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_19.setBounds(339, 190, 9, 313);
		
		Label label_20 = new Label(shell, SWT.NONE);
		label_20.setAlignment(SWT.RIGHT);
		label_20.setBounds(36, 363, 76, 20);
		label_20.setText("入住天数：");
		
		text_days = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_days.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_days.setBounds(118, 360, 171, 26);

		showRoomId();//将已预定的房间号添加到下拉框
	}
	
	
	/**
	 * 生成账单号
	 * @param r_id
	 * @return
	 */
	public String createBillId(String r_id){
		String head="2017";//账单号的头部
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
		String mid=dateFormat.format(date);//获得账单号的中间部分
		//获取账单号的尾部--客户编号
		List<Object> params=new ArrayList<Object>();
		params.add(r_id);
		String tail="";
		try {
			Map<String, Object> map=enterDao.findEnterByRid(params);
			if (null==map||map.size()<=0) {
				SwtUtil.showMessage(getParent(), "错误提示", "账单号生成失败...\n客户信息查询失败...");
				return "";
			}
			tail=StringUtil.objToString(map.get("C_ID"));
		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}
		return head+mid+tail;
	}
	
	//将已入住的房间号添加到下拉框
	public void showRoomId(){
		String r_state="已入住";
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("R_ID", null);
		map1.put("R_TYPE", null);
		map1.put("R_STATE", r_state);
		
		try {
			List<Map<String, Object>> list=roomDao.findRoom(map1);
			combo_r_id.removeAll();
			if (null==list||list.size()<=0) {
				return;
			}
			String [] str=new String [100];
			int i=0;
			for (Map<String, Object> map : list) {
				str[i]=StringUtil.objToString(map.get("R_ID"));
				i++;
			}
			String [] str1=new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j]=str[j];
			}
			combo_r_id.setItems(str1);
		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();
		}
	}
}
