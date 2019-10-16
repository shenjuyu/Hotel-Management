package com.yc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.yc.dao.CustomerDao;
import com.yc.dao.EnterDao;
import com.yc.dao.ReserveDao;
import com.yc.dao.RoomDao;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
/**
 * 入住界面
 * @author 沈俊羽
 *
 */
public class CheckInComposite extends Dialog {

	protected Object result;
	protected Shell shell;
	private CustomerDao customerDao=new CustomerDao();
	private RoomDao roomDao=new RoomDao();
	private ReserveDao reserveDao=new ReserveDao();
	private EnterDao enterDao=new EnterDao();
	private Combo combo_name;//姓名
	private Text text_tel;//电话
	private Text text_id_number;//身份证号
	private Button button_man;//单选按钮     男
	private Button button_woman;//单选按钮    女
	private Combo combo_type;//房间类型
	private Combo combo_rid;//房间号
	private Text text_price;//房价  每天
	private Text text_inDate;//入住时间
	private Text text_out_date;//退房时间
	private Text text_date_num;//入住天数
	private Text text_deposit;//押金
	private Text text_roomPrice;//房间费用
	private Text text_trueMoney;//本次应缴
	
	private Combo combo_name1;//姓名
	private Text text_id_number1;//身份证号
	private Text text_tel1;//电话
	private Button button_man1;//单选按钮  男
	private Button button_woman1;//单选按钮  女
	private Text text_rid1;//房间号
	private Text text_type1;//房间类型
	private Text text_price1;//房价  每天
	private Text text_reserveDate;//预定入住时间
	private Text text_inDate1;//入住时间
	private Text text_outDate1;//退房时间
	private Text text_penalty;//违约金
	private Text text_deposit1;//押金
	private Text text_roomPrice1;//房价费用
	private Text text_trueMoney1;//本次应缴
	private Label tishi_inNum1;
	private Text text_inNum;
	private Label tishi_tel;
	private Label tishi_name;
	private Label tishi_id_number;
	private Label tishi_inNum;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CheckInComposite(Shell parent, int style) {
		super(parent, style);
		setText("欢迎入住首席大酒店");
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
		shell.setImage(SWTResourceManager.getImage(CheckInComposite.class, "/images/WindowIcon.png"));
		shell.setSize(690, 707);
		shell.setText("欢迎入住首席大酒店");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tabFolder.setBounds(10, 10, 664, 652);
		
		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("普通客户入住");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		tabItem_1.setControl(composite_1);
		composite_1.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Group group = new Group(composite_1, SWT.BORDER);
		group.setText("客户信息");
		group.setBounds(10, 21, 291, 276);
		
		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 35, 76, 20);
		label.setText("客户姓名：");
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(10, 104, 76, 20);
		label_1.setText("客户电话：");
		
		text_tel = new Text(group, SWT.BORDER);
		text_tel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_tel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String tel_RG="^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
				String tel=text_tel.getText().trim();
				if (null==tel||"".equals(tel)) {
					tishi_tel.setText("手机号不能为空...");
				} else if(!tel.matches(tel_RG)){
					tishi_tel.setText("手机号格式不正确...");
				}else{
					tishi_tel.setText("");
				}
			}
		});
		text_tel.setBounds(92, 98, 167, 26);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(10, 167, 76, 20);
		label_2.setText("身份证号：");
		
		text_id_number = new Text(group, SWT.BORDER);
		text_id_number.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_id_number.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String id_number=text_id_number.getText().trim();
				String ID_RG="^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
				if (null==id_number||"".equals(id_number)) {
					tishi_id_number.setText("身份证号不能为空...");
				} else if(!id_number.matches(ID_RG)){
					tishi_id_number.setText("身份证格式不正确...");
				}else{
					tishi_id_number.setText("");
				}
			}
		});
		text_id_number.setBounds(92, 167, 167, 26);
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(10, 232, 76, 20);
		label_3.setText("性别：");
		
		button_man = new Button(group, SWT.RADIO);
		//将客户信息添加到客户表中
		button_man.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				addCustomer();
			}
		});
		button_man.setBounds(121, 232, 39, 20);
		button_man.setText("男");
		
		button_woman = new Button(group, SWT.RADIO);
		//将客户信息添加到客户表中
		button_woman.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				addCustomer();
			}
		});
		button_woman.setBounds(191, 232, 39, 20);
		button_woman.setText("女");
		
		tishi_name = new Label(group, SWT.NONE);
		tishi_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_name.setBounds(92, 69, 158, 20);
		
		tishi_tel = new Label(group, SWT.NONE);
		tishi_tel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_tel.setBounds(92, 130, 158, 20);
		
		tishi_id_number = new Label(group, SWT.NONE);
		tishi_id_number.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_id_number.setBounds(92, 199, 158, 20);
		
		combo_name = new Combo(group, SWT.NONE);
		combo_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name=combo_name.getText().trim();
				if (null==name||"".equals(name)) {
					tishi_name.setText("姓名不能为空...");
				}else{
					tishi_name.setText("");
				}
			}
		});
		combo_name.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String name=combo_name.getText().trim();
				String attribute="客户姓名";
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("C_ID", null);
				map.put("C_NAME", name);
				map.put("C_SEX", null);
				map.put("C_TEL", null);
				try {
					//根据客户姓名查询客户信息
					List<Map<String, Object>> list=customerDao.findCustomer(attribute, map);
					if (null==list||list.size()<=0) {
						return;
					}
					for (Map<String, Object> map2 : list) {
						text_tel.setText(StringUtil.objToString(map2.get("C_TEL")));
						text_id_number.setText(StringUtil.objToString(map2.get("C_ID_NUMBER")));
						String sex=StringUtil.objToString(map2.get("C_SEX"));
						if ("男".equals(sex)) {
							button_man.setSelection(true);
							button_woman.setSelection(false);
						} else if("女".equals(sex)){
							button_man.setSelection(false);
							button_woman.setSelection(true);
						}
					}
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		combo_name.setBounds(92, 32, 167, 28);
		
		Group group_1 = new Group(composite_1, SWT.BORDER);
		group_1.setText("客房信息");
		group_1.setBounds(10, 314, 291, 276);
		
		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(10, 42, 91, 20);
		label_4.setText("房间类型：");
		
		combo_type = new Combo(group_1, SWT.READ_ONLY);
		//根据房间类型查找房间号
		combo_type.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showRoomId();//将对应的房间类型的空闲房间的房间号添加到下拉框中
			}
		});
		combo_type.setItems(new String[] {"经济房", "高级房", "总统套房"});
		combo_type.setBounds(107, 39, 145, 28);
		combo_type.select(0);
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setBounds(10, 129, 91, 20);
		label_5.setText("房间号：");
		
		combo_rid = new Combo(group_1, SWT.READ_ONLY);
		//得到房价和房间押金
		combo_rid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String rid=combo_rid.getText().trim();
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("R_ID", rid);
				map1.put("R_TYPE", null);
				map1.put("R_STATE", null);
				
				try {
					List<Map<String, Object>> list=roomDao.findRoom(map1);
					if (null==list||list.size()<=0) {
						SwtUtil.showMessage(getParent(), "错误提示", "无房间信息...");
						return;
					}
					
					for (Map<String, Object> map : list) {
						if ("维护".equals(StringUtil.objToString(map.get("R_STATE")))) {
							SwtUtil.showMessage(getParent(), "提示", "该房间处于维护状态,暂不支持入住...");
							return;
						}
						text_price.setText(StringUtil.objToString(map.get("R_PRICE")));
						text_deposit.setText(StringUtil.objToString(map.get("R_DEPOSIT")));
					}
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		combo_rid.setBounds(107, 126, 145, 28);
		combo_rid.select(0);
		
		Label label_6 = new Label(group_1, SWT.NONE);
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(10, 200, 91, 20);
		label_6.setText("房价(\\天)：");
		
		text_price = new Text(group_1, SWT.BORDER | SWT.READ_ONLY);
		text_price.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_price.setBounds(107, 194, 145, 26);
		
		Label label_7 = new Label(composite_1, SWT.NONE);
		label_7.setBounds(359, 56, 76, 20);
		label_7.setText("入住日期：");
		
		Label label_8 = new Label(composite_1, SWT.NONE);
		label_8.setBounds(359, 121, 76, 20);
		label_8.setText("入住天数：");
		
		text_date_num = new Text(composite_1, SWT.BORDER);
		text_date_num.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//得到退房时间
		text_date_num.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inDate=text_inDate.getText().trim();//时间
				String num=text_date_num.getText().trim();//天数
				if (null==num||"".endsWith(num)) {
					tishi_inNum.setText("天数不能为空...");//判空提示
					return;
				}
				if (null==inDate||"".endsWith(inDate)) {
					SwtUtil.showMessage(getParent(), "提示", "入住时间不能为空");//判空提示
					return;
				}
				
				findEnterdate();
				findReservedate();
				
				
				tishi_inNum.setText("");
				long inNum=Long.parseLong(num);//将String类型天数转化为Long类型
				
				if (inNum<=0) {
					SwtUtil.showMessage(getParent(), "提示", "入住天数不能为负数或0");
					return;
				}
				
				Date date=null;//声明date
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//日期格式
				try {
					date=format.parse(inDate);//传进时间
					long start=date.getTime();//得到毫秒数
					long dd=start+inNum*1000*3600*24;//天数转化为毫秒数再相加
					Date date2=new Date(dd);//将毫秒数传进，创建date
					text_out_date.setText(format.format(date2));//将date转化为String类型并设置到文本框中
				} catch (ParseException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				if (null==text_price.getText().trim()||"".equals(text_price.getText().trim())) {
					SwtUtil.showMessage(getParent(), "提示", "房间价格不能为空");
					return;
				}
				double price=Double.parseDouble(text_price.getText().trim());
				double roomPrice=price*(double)inNum;
				text_roomPrice.setText(String.valueOf(roomPrice));
				double deposit=Double.parseDouble(text_deposit.getText().trim());
				text_trueMoney.setText(String.valueOf(roomPrice+deposit));
			}
		});
		text_date_num.setBounds(441, 118, 119, 26);
		
		Label label_9 = new Label(composite_1, SWT.NONE);
		label_9.setBounds(359, 183, 76, 20);
		label_9.setText("退房时间：");
		
		Label label_10 = new Label(composite_1, SWT.NONE);
		label_10.setBounds(359, 246, 76, 20);
		label_10.setText("房间押金：");
		
		text_deposit = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_deposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_deposit.setBounds(441, 243, 119, 26);
		
		Label label_11 = new Label(composite_1, SWT.NONE);
		label_11.setBounds(359, 299, 76, 20);
		label_11.setText("房间费用：");
		
		text_roomPrice = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_roomPrice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_roomPrice.setBounds(441, 296, 119, 26);
		
		Label label_12 = new Label(composite_1, SWT.NONE);
		label_12.setBounds(359, 361, 76, 20);
		label_12.setText("实收金额：");
		
		text_trueMoney = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_trueMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_trueMoney.setBounds(441, 358, 119, 26);
		
		Button button = new Button(composite_1, SWT.NONE);
		//入住
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String name=combo_name.getText().trim();//姓名验证
				if (null==name||"".equals(name)) {
					SwtUtil.showMessage(getParent(), "错误提示", "姓名不能为空...");
					return;
				}
				
				String tel_RG="^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";//手机号验证
				String tel1=text_tel.getText().trim();
				if (null==tel1||"".equals(tel1)) {
					SwtUtil.showMessage(getParent(), "错误提示", "手机号不能为空...");
					return;
				} else if(!tel1.matches(tel_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "手机号格式不正确...");
					return;
				}
				
				String id_number=text_id_number.getText().trim();//身份证号验证
				String ID_RG="^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
				if (null==id_number||"".equals(id_number)) {
					SwtUtil.showMessage(getParent(), "错误提示", "身份证号不能为空...");
					return;
				} else if(!id_number.matches(ID_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "身份证格式不正确...");
					return;
				}
				
				
				
				String inNum1=text_date_num.getText().trim();
				if (null==inNum1||"".endsWith(inNum1)) {
					SwtUtil.showMessage(getParent(), "错误提示", "天数不能为空...");
					return;
				}else if (Integer.parseInt(inNum1)<=0) {
					SwtUtil.showMessage(getParent(), "错误提示", "入住天数不能为负数或0");
					return;
				}
				
				
				String tel=text_tel.getText().trim();
				String attribute="手机号";
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("C_ID", null);
				map.put("C_NAME", null);
				map.put("C_SEX", null);
				map.put("C_TEL", tel);
				try {
					
					List<Map<String, Object>> list5=reserveDao.findAllReserve();
					if (null != list5 && list5.size() > 0) {
						for (Map<String, Object> map2 : list5) {
							if (tel.equals(StringUtil.objToString(map2.get("C_TEL")))) {
								SwtUtil.showMessage(getParent(), "提示", "该用户已预约房间\n不能在本界面入住");
								return;
							}
						}
					}
					
					List<Map<String, Object>> list4=enterDao.findEnterAll();
					if (null != list4 && list4.size() > 0) {
						for (Map<String, Object> map2 : list4) {
							if (tel.equals(StringUtil.objToString(map2.get("C_TEL")))) {
								SwtUtil.showMessage(getParent(), "提示", "该用户已入住\n不能再次入住");
								return;
							}
						}
					}
					
					List<Map<String, Object>> list=customerDao.findCustomer(attribute, map);
					if (null==list||list.size()<=0) {
						return;
					}
					String c_id="";
					for (Map<String, Object> map2 : list) {
						c_id=StringUtil.objToString(map2.get("C_ID"));
					}
					String r_id=combo_rid.getText().trim();
					String inDate=text_inDate.getText().trim();
					String inNum=text_date_num.getText().trim();
					String outDate=text_out_date.getText().trim();
					String cp="0";
					String rs="0";
					
					if (null==r_id||"".equals(r_id)) {
						SwtUtil.showMessage(getParent(), "错误提示", "房间号未选择...");
						return;
					}
					
					List<Object> params=new ArrayList<Object>();
					params.add(c_id);
					params.add(r_id);
					params.add(inDate);
					params.add(inNum);
					params.add(outDate);
					params.add(cp);
					params.add(rs);
					
					List<Object> params1=new ArrayList<Object>();
					params1.add("已入住");
					params1.add(r_id);
					int result1=roomDao.updateRoomState(params1);//更新房间状态
					if (result1<=0) {
						SwtUtil.showMessage(getParent(), "错误提示", "请查询信息正确性后再试...");
						return;
					}
					
					int result=enterDao.addEnter(params);
					
					if (result>0) {
						SwtUtil.showMessage(getParent(), "温馨提示", "欢迎入住首席大酒店");
						findAllCustomer();
						showRoomId();
						combo_name.select(-1);
						text_tel.setText("");
						text_id_number.setText("");
						button_man.setSelection(false);
						button_woman.setSelection(false);
						combo_type.select(0);
						combo_rid.select(-1);
						text_out_date.setText("");
						text_date_num.setText("");
						text_deposit.setText("");
						text_price.setText("");
						text_roomPrice.setText("");
						text_trueMoney.setText("");
						text_inDate.setText("");
//						//刷新主界面
//						MainUI mainUI=new MainUI();
//						mainUI.showRoom(mainUI.group, "经济房");
//						mainUI.showRoom(mainUI.group_1, "高级房");
//						mainUI.showRoom(mainUI.group_2, "总统套房");
					} else {
						SwtUtil.showMessage(getParent(), "错误提示", "请查询信息正确性后再试...");
					}
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(441, 433, 98, 30);
		button.setText("入住");
		
		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				shell.dispose();
			}
		});
		button_1.setBounds(420, 528, 140, 30);
		button_1.setText("返回主界面");
		
		text_inDate = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_inDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//得到入住日期
		text_inDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				text_inDate.setText(dateFormat.format(date));
			}
		});
		text_inDate.setBounds(441, 53, 119, 26);
		
		text_out_date = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_out_date.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_out_date.setBounds(441, 183, 119, 26);
		
		tishi_inNum = new Label(composite_1, SWT.NONE);
		tishi_inNum.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_inNum.setBounds(441, 150, 119, 20);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("预定客户入住");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Group group_2 = new Group(composite, SWT.BORDER);
		group_2.setText("客户信息");
		group_2.setBounds(10, 29, 291, 276);
		
		Label label_13 = new Label(group_2, SWT.NONE);
		label_13.setAlignment(SWT.RIGHT);
		label_13.setBounds(10, 41, 101, 20);
		label_13.setText("客户姓名：");
		
		combo_name1 = new Combo(group_2, SWT.READ_ONLY);
		//填充客户信息，房间信息    根据客户姓名得到客户编号，根据客户编号得到房间号，根据房间号得到房间信息
		combo_name1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String name=combo_name1.getText().trim();
				String attribute="客户姓名";
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("C_ID", null);
				map.put("C_NAME", name);
				map.put("C_SEX", null);
				map.put("C_TEL", null);
				try {
					//根据客户姓名查询客户信息
					List<Map<String, Object>> list=customerDao.findCustomer(attribute, map);
					if (null==list||list.size()<=0) {
						return;
					}
					for (Map<String, Object> map2 : list) {
						text_tel1.setText(StringUtil.objToString(map2.get("C_TEL")));
						text_id_number1.setText(StringUtil.objToString(map2.get("C_ID_NUMBER")));
						String sex1=StringUtil.objToString(map2.get("C_SEX"));
						if ("男".equals(sex1)) {
							button_man1.setSelection(true);
							button_woman1.setSelection(false);
						} else if("女".equals(sex1)){
							button_man1.setSelection(false);
							button_woman1.setSelection(true);
						}
					}
					List<Object> params=new ArrayList<Object>();
					params.add(name);
					List<Map<String, Object>> list1=reserveDao.findReserveRoom(params);
					if (null==list1||list1.size()<=0) {
						return;
					}
					for (Map<String, Object> map2 : list1) {
						text_rid1.setText(StringUtil.objToString(map2.get("R_ID")));
						text_type1.setText(StringUtil.objToString(map2.get("R_TYPE")));
						text_price1.setText(StringUtil.objToString(map2.get("R_PRICE")));
						text_deposit1.setText(StringUtil.objToString(map2.get("R_DEPOSIT")));
					}
					
					//根据房间号查询预定信息
					
					List<Object> params1=new ArrayList<Object>();
					params1.add(text_rid1.getText().trim());
					Map<String, Object> map1=reserveDao.findReserveByRid(params1);/**
					 * 
					 * 
					 * 
					 * 错误处
					 * 
					 * 
					 */
					text_reserveDate.setText(StringUtil.objToString(map1.get("E_RES_COMDATE")));
					text_inNum.setText(StringUtil.objToString(map1.get("E_DAYS")));
					text_outDate1.setText(StringUtil.objToString(map1.get("E_LEAVE_DATE")));
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		combo_name1.setBounds(116, 38, 153, 28);
		
		Label label_14 = new Label(group_2, SWT.NONE);
		label_14.setAlignment(SWT.RIGHT);
		label_14.setBounds(8, 103, 101, 20);
		label_14.setText("身份证号：");
		
		text_id_number1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_id_number1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_id_number1.setBounds(116, 100, 153, 26);
		
		Label label_15 = new Label(group_2, SWT.NONE);
		label_15.setAlignment(SWT.RIGHT);
		label_15.setBounds(6, 160, 103, 20);
		label_15.setText("手机号：");
		
		text_tel1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_tel1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_tel1.setBounds(116, 157, 153, 26);
		
		Label label_16 = new Label(group_2, SWT.NONE);
		label_16.setAlignment(SWT.RIGHT);
		label_16.setBounds(10, 219, 101, 20);
		label_16.setText("性别：");
		
		button_man1 = new Button(group_2, SWT.RADIO);
		button_man1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_man1.setBounds(146, 219, 39, 20);
		button_man1.setText("男");
		
		button_woman1 = new Button(group_2, SWT.RADIO);
		button_woman1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_woman1.setBounds(202, 219, 39, 20);
		button_woman1.setText("女");
		
		Group group_3 = new Group(composite, SWT.BORDER);
		group_3.setText("房间信息");
		group_3.setBounds(10, 311, 291, 276);
		
		Label label_20 = new Label(group_3, SWT.NONE);
		label_20.setAlignment(SWT.RIGHT);
		label_20.setBounds(10, 55, 96, 20);
		label_20.setText("房间号：");
		
		Label label_21 = new Label(group_3, SWT.NONE);
		label_21.setAlignment(SWT.RIGHT);
		label_21.setBounds(10, 110, 96, 20);
		label_21.setText("房间类型：");
		
		Label label_22 = new Label(group_3, SWT.NONE);
		label_22.setAlignment(SWT.RIGHT);
		label_22.setBounds(10, 167, 96, 20);
		label_22.setText("房价：");
		
		text_rid1 = new Text(group_3, SWT.BORDER | SWT.READ_ONLY);
		text_rid1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_rid1.setBounds(112, 52, 148, 26);
		
		text_type1 = new Text(group_3, SWT.BORDER | SWT.READ_ONLY);
		text_type1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_type1.setBounds(112, 107, 148, 26);
		
		text_price1 = new Text(group_3, SWT.BORDER | SWT.READ_ONLY);
		text_price1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_price1.setBounds(112, 164, 148, 26);
		
		Label label_23 = new Label(composite, SWT.NONE);
		label_23.setAlignment(SWT.RIGHT);
		label_23.setBounds(307, 47, 95, 20);
		label_23.setText("预定时间：");
		
		Label label_24 = new Label(composite, SWT.NONE);
		label_24.setAlignment(SWT.RIGHT);
		label_24.setBounds(307, 112, 95, 20);
		label_24.setText("入住时间：");
		
		Label label_25 = new Label(composite, SWT.NONE);
		label_25.setAlignment(SWT.RIGHT);
		label_25.setBounds(307, 208, 95, 20);
		label_25.setText("退房时间：");
		
		Label label_26 = new Label(composite, SWT.NONE);
		label_26.setAlignment(SWT.RIGHT);
		label_26.setBounds(307, 271, 95, 20);
		label_26.setText("违约金：");
		
		text_penalty = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_penalty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_penalty.setBounds(408, 268, 132, 26);
		
		Label label_27 = new Label(composite, SWT.NONE);
		label_27.setAlignment(SWT.RIGHT);
		label_27.setBounds(307, 334, 95, 20);
		label_27.setText("房间押金：");
		
		text_deposit1 = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_deposit1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_deposit1.setBounds(408, 331, 132, 26);
		
		Label label_28 = new Label(composite, SWT.NONE);
		label_28.setAlignment(SWT.RIGHT);
		label_28.setBounds(307, 393, 95, 20);
		label_28.setText("房间费用：");
		
		text_roomPrice1 = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_roomPrice1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_roomPrice1.setBounds(408, 390, 132, 26);
		
		Label label_29 = new Label(composite, SWT.NONE);
		label_29.setAlignment(SWT.RIGHT);
		label_29.setBounds(307, 452, 95, 20);
		label_29.setText("应收金额：");
		
		text_trueMoney1 = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_trueMoney1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_trueMoney1.setBounds(408, 449, 132, 26);
		
		Button button_2 = new Button(composite, SWT.NONE);
		//入住操作
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				
				String name=combo_name1.getText().trim();//姓名验证
				if (null==name||"".equals(name)) {
					SwtUtil.showMessage(getParent(), "错误提示", "姓名不能为空...");
					return;
				}
				
				
				String r_id1=text_rid1.getText().trim();//对房间号的判断
				if (null==r_id1||"".equals(r_id1)) {
					SwtUtil.showMessage(getParent(), "错误提示", "房间号未选择...");
					return;
				}
				
				
				String r_id=text_rid1.getText().trim();//房间号
				String inDate=text_inDate1.getText().trim();//入住日期
				String inNum=text_inNum.getText().trim();//天数
				String outDate=text_outDate1.getText().trim();//退房日期
				String cp=text_penalty.getText().trim();//违约金
				
				if (null==inNum||"".equals(inNum)) {
					SwtUtil.showMessage(getParent(), "错误提示", "入住天数不能为空...");
					return;
				}
				
				
				String rs="1";//是否预定
				
				String tel=text_tel1.getText().trim();
				String attribute="手机号";
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("C_ID", null);
				map.put("C_NAME", null);
				map.put("C_SEX", null);
				map.put("C_TEL", tel);
				try {
					//获得客户编号
					List<Map<String, Object>> list=customerDao.findCustomer(attribute, map);
					if (null==list||list.size()<=0) {
						return;
					}
					String c_id="";
					for (Map<String, Object> map2 : list) {
						c_id=StringUtil.objToString(map2.get("C_ID"));
					}
					
					
					
					
					List<Object> params1=new ArrayList<Object>();
					params1.add("已入住");
					params1.add(r_id);
					//修改房间状态
					int result1=roomDao.updateRoomState(params1);
					if (result1<=0) {
						SwtUtil.showMessage(getParent(), "错误提示", "请查询信息正确性后再试...");
						return;
					}
					
					//删除预定表中的数据
					List<Object> params2=new ArrayList<Object>();
					params2.add(r_id);
					int result2=reserveDao.deleteReserve(params2);
					if (result2<=0) {
						SwtUtil.showMessage(getParent(), "错误提示", "请查询信息正确性后再试...");
						return;
					}
					
					
					List<Object> params=new ArrayList<Object>();
					params.add(c_id);
					params.add(r_id);
					params.add(inDate);
					params.add(inNum);
					params.add(outDate);
					params.add(cp);
					params.add(rs);
					int result=enterDao.addEnter(params);//添加入住信息
					
					if (result>0) {
						SwtUtil.showMessage(getParent(), "温馨提示", "欢迎入住首席大酒店");
						
						
						findReserveCustomer();//将所有的预定了的客户表中的客户名插入到下拉框中
						text_tel1.setText("");//电话
						text_id_number1.setText("");//身份证号
						button_man1.setSelection(false);//男
						button_woman1.setSelection(false);//女
						text_type1.setText("");//房间类型
						text_rid1.setText("");//房间号
						text_reserveDate.setText("");//预定入住日期
						text_penalty.setText("");//违约金
						text_outDate1.setText("");//退房日期
						text_inNum.setText("");//入住天数
						text_deposit1.setText("");//押金
						text_price1.setText("");//房价（每天）
						text_roomPrice1.setText("");//房间费用
						text_trueMoney1.setText("");//本次消费
						text_inDate1.setText("");//实际入住日期
					} else {
						SwtUtil.showMessage(getParent(), "错误提示", "请查询信息正确性后再试...");
					}
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button_2.setBounds(392, 517, 98, 30);
		button_2.setText("入住");
		
		text_reserveDate = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_reserveDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_reserveDate.setBounds(408, 44, 132, 26);
		
		text_inDate1 = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_inDate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//获得当前时间
		text_inDate1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				text_inDate1.setText(dateFormat.format(date));
			}
		});
		text_inDate1.setBounds(408, 109, 132, 26);
		
		text_outDate1 = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_outDate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//获得实际退房时间
		text_outDate1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String reserveDate=text_reserveDate.getText().trim();//预计入住时间
				String inDate=text_inDate1.getText().trim();//实际入住时间
				String num=text_inNum.getText().trim();//天数
				if (null==num||"".endsWith(num)) {
					return;
				}
				long inNum=Long.parseLong(num);
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//日期格式
				try {
					Date date=format.parse(inDate);//传进    实际入住时间
					long start=date.getTime();//得到毫秒数
					long dd=start+inNum*1000*3600*24;//天数转化为毫秒数再相加
					Date date2=new Date(dd);
					text_outDate1.setText(format.format(date2));
					
					Date date1=format.parse(reserveDate);//传进  预定入住时间
					long ends=date1.getTime();//得到毫秒数
					long dd1=(start-ends)/1000/3600/24;//得到违约天数
					double price=Double.parseDouble(text_price1.getText().trim());//房价/天
					double penatly=0;//违约金
					if (dd1>0) {
						penatly=price*dd1*0.2;//违约后每天交房价的百分值20
					}
					text_penalty.setText(String.valueOf(penatly));//将违约金设置到文本框
					
					double roomPrice=price*(double)inNum;
					text_roomPrice1.setText(String.valueOf(roomPrice));//将房间费用设置到文本框
					double deposit=Double.parseDouble(text_deposit1.getText().trim());
					text_trueMoney1.setText(String.valueOf(roomPrice+deposit+penatly));//将本次实际费用设置到文本框
				} catch (ParseException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		text_outDate1.setBounds(408, 205, 132, 26);
		
		Label label_17 = new Label(composite, SWT.NONE);
		label_17.setAlignment(SWT.RIGHT);
		label_17.setBounds(307, 161, 95, 20);
		label_17.setText("入住天数：");
		
		text_inNum = new Text(composite, SWT.BORDER);
		text_inNum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//入住日期格式是否正确
		text_inNum.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inNum=text_inNum.getText().trim();
				if (null==inNum||"".equals(inNum)) {
					tishi_inNum1.setText("不能为空...");
				}else{
					tishi_inNum1.setText("");
				}
				
				String reserveDate=text_reserveDate.getText().trim();//预计入住时间
				String inDate=text_inDate1.getText().trim();//实际入住时间
				String num=text_inNum.getText().trim();//天数
				if (null==num||"".endsWith(num)) {
					return;
				}
				long inNum1=Long.parseLong(num);
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//日期格式
				try {
					Date date=format.parse(inDate);//传进    实际入住时间
					long start=date.getTime();//得到毫秒数
					long dd=start+inNum1*1000*3600*24;//天数转化为毫秒数再相加
					Date date2=new Date(dd);
					text_outDate1.setText(format.format(date2));
					
					Date date1=format.parse(reserveDate);//传进  预定入住时间
					long ends=date1.getTime();//得到毫秒数
					long dd1=(start-ends)/1000/3600/24;//得到违约天数
					double price=Double.parseDouble(text_price1.getText().trim());//房价/天
					double penatly=0;//违约金
					if (dd1>0) {
						penatly=price*dd1*0.2;//违约后每天交房价的百分值20
					}
					text_penalty.setText(String.valueOf(penatly));//将违约金设置到文本框
					
					double roomPrice=price*(double)inNum1;
					text_roomPrice1.setText(String.valueOf(roomPrice));//将房间费用设置到文本框
					double deposit=Double.parseDouble(text_deposit1.getText().trim());
					text_trueMoney1.setText(String.valueOf(roomPrice+deposit+penatly));//将本次实际费用设置到文本框
				} catch (ParseException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		text_inNum.setBounds(408, 158, 132, 26);
		
		tishi_inNum1 = new Label(composite, SWT.NONE);
		tishi_inNum1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_inNum1.setBounds(546, 161, 100, 20);
		
		Label label_18 = new Label(composite, SWT.NONE);
		label_18.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_18.setBounds(546, 271, 100, 20);
		label_18.setText("房价*0.2(每天)");
		
		findAllCustomer();//将所有的客户表中的客户名插入到下拉框中
		showRoomId();//将对应的房间类型的空闲房间的房间号添加到下拉框中
		findReserveCustomer();//将所有的预定列表中的客户名添加到下拉框中
	}
	
	// 判断房间日期是否重合
	public void findEnterdate() {
		String r_id = combo_rid.getText().trim();//房间号
		String joindate = text_inDate.getText().trim();//客户的入住日期
		// String leavedate = text_leavedate.getText().trim();
		Date date1 = null;
		// Date date2 = null;
		Date date3 = null;
		Date date4 = null;

		List<Object> params = new ArrayList<Object>();

		params.add(r_id);

		String[] str = new String[20];
		String[] str1 = new String[20];
		int i = 0;

		try {
			List<Map<String, Object>> list = enterDao.findEnterByRid1(params);//根据房间号查找入住信息 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//时间的格式
			for (Map<String, Object> map : list) {//对list进行循环将表中的入住日期和表中的离店日期放到数组中
				str[i] = StringUtil.objToString(map.get("JOIN_DATE"));
				str1[i] = StringUtil.objToString(map.get("RETURN_DATE"));

				date1 = format.parse(joindate);// 输入的入住日期
				// date2 = format.parse(leavedate);// 输入的退房日期
				date3 = format.parse(str[i]);// 已存入的预约入住日期
				date4 = format.parse(str1[i]);// 已存入的预约退房日期

				// 得到毫秒
				long join = date1.getTime();
				// long leave = date2.getTime();
				long everjoin = date3.getTime();
				long everleave = date4.getTime();

				// 判断要预约的时间是否被占据
				long aa = join - everjoin;// 输入的入住日期-已存入的预约入住日期
				long bb = everleave - join;// 已存入的预约退房日期-输入的入住日期

				if (aa >= 0 && 0 < bb) {
					SwtUtil.showMessage(getParent(), "温馨提示", "此时间已被入住,请在此时间之后入住:" + str1[i]);
				}
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//判断预定日期是否重合
	public void findReservedate() {
		String r_id = combo_rid.getText().trim();//房间号
		String joindate = text_inDate.getText().trim();//客户的入住日期
		// String leavedate = text_leavedate.getText().trim();
		Date date1 = null;
		// Date date2 = null;
		Date date3 = null;
		Date date4 = null;

		List<Object> params = new ArrayList<Object>();

		params.add(r_id);

		String[] str = new String[20];
		String[] str1 = new String[20];
		int i = 0;

		try {
			List<Map<String, Object>> list = reserveDao.findReserveByRid1(params);//根据房间号查找入住信息 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//时间的格式
			for (Map<String, Object> map : list) {//对list进行循环将表中的入住日期和表中的离店日期放到数组中
				str[i] = StringUtil.objToString(map.get("E_RES_COMDATE"));
				str1[i] = StringUtil.objToString(map.get("E_LEAVE_DATE"));

				date1 = format.parse(joindate);// 输入的入住日期
				// date2 = format.parse(leavedate);// 输入的退房日期
				date3 = format.parse(str[i]);// 已存入的预约入住日期
				date4 = format.parse(str1[i]);// 已存入的预约退房日期

				// 得到毫秒
				long join = date1.getTime();
				// long leave = date2.getTime();
				long everjoin = date3.getTime();
				long everleave = date4.getTime();

				// 判断要预约的时间是否被占据
				long aa = join - everjoin;// 输入的入住日期-已存入的预约入住日期
				long bb = everleave - join;// 已存入的预约退房日期-输入的入住日期

				if (aa >= 0 && 0 < bb) {
					SwtUtil.showMessage(getParent(), "温馨提示", "此时间已被预定,请在此时间之后入住:" + str1[i]);
				}
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	// 判断房间日期是否重合
	/**
	 * 已废弃的方法
	 */
//	public void finddate() {
//		String r_id = combo_rid.getText().trim();//房间号
//		String joindate = text_inDate.getText().trim();//客户输入的入住日期
//		String trueInDate="";//客户正确的入住时间
//		String trueOutDate="";//客户正确的离店时间
//		
//		String[] str1 = new String[20];//预定表中入住时间
//		String[] str2 = new String[20];//预定表中离店时间
//		String[] str3 = new String[20];//入住表中入住时间
//		String[] str4 = new String[20];//入住表中离店时间
//		
//		Date date1 = null;
//		Date date2 = null;
//		Date date3 = null;
//		Date date4 = null;
//
//		List<Object> params = new ArrayList<Object>();
//		params.add(r_id);
//
//		
//		
//
//		try {
//			List<Map<String, Object>> list = reserveDao.findReserveByRid1(params);//根据房间号查找预定信息 
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//时间的格式
//			int i = 0;
//			for (Map<String, Object> map : list) {//对list进行循环将表中的入住日期和表中的离店日期放到数组中
//				str1[i] = StringUtil.objToString(map.get("E_RES_COMDATE"));
//				str2[i] = StringUtil.objToString(map.get("E_LEAVE_DATE"));
//				i++;
//			}
//
//			List<Map<String, Object>> list1=enterDao.findEnterByRid1(params);
//			int j=0;
//			for (Map<String, Object> map : list1) {
//				str3[j]=StringUtil.objToString(map.get("JOIN_DATE"));
//				str4[j]=StringUtil.objToString(map.get("RETURN_DATE"));
//				j++;
//			}
//			
//			for(int k=0;k<str1.length;k++){
//				date1=format.parse(str1[k]);
//				date2=format.parse(joindate);
//				date3=format.parse(str2[k]);
//				if (date1.getTime()>date2.getTime()||date2.getTime()>date3.getTime()) {
//					trueInDate=joindate;
//					break;
//				}
//				trueInDate=str2[k];
//			}
//			
//			double num=0;
//			int trueNum=0;
//			for (int k = 0; k < str2.length; k++) {
//				date1=format.parse(str2[k]);
//				long outdate=date1.getTime();
//				date2=format.parse(joindate);
//				long indate=date2.getTime();
//				num=(outdate-indate)/1000/3600/24;
//				if (num<0) {
//					continue;
//				}
//				
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	/**
	 * 将所有的预定了的客户表中的客户名插入到下拉框中    在预定表中查找客户信息(客户编号)，
	 * 查找到客户编号后  根据客户编号查找客户姓名放到字符串数组中
	 */
	public void findReserveCustomer(){
		combo_name1.removeAll();
		try {
			List<Map<String, Object>> list=reserveDao.findAllReserveCustomer();
			if (null==list||list.size()<=0) {
				return;
			}
			String [] str=new String [20];
			int i=0;
			for (Map<String, Object> map : list) {
				str[i]=StringUtil.objToString(map.get("C_NAME"));
				i++;
			}
			String [] str1=new String [i];
			for (int j = 0; j < str1.length; j++) {
				str1[j]=str[j];
			}
			combo_name1.setItems(str1);
		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}
	}
	
	//将所有的客户表中的客户名插入到下拉框中
	public void findAllCustomer(){
		String attribute="查找所有";
		Map<String, Object> map=new HashMap<String,Object>();
		
		try {
			List<Map<String, Object>> list=customerDao.findCustomer(attribute, map);
			if (null==list||list.size()<=0) {
				return;
			}
			combo_name.removeAll();
			String [] str=new String [100];
			int i=0;
			for (Map<String, Object> map2 : list) {
				str[i]=StringUtil.objToString(map2.get("C_NAME"));
				i++;
			}
			String [] str1=new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j]=str[j];
			}
			combo_name.setItems(str1);
		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}
	}
	
	//添加客户
	public void addCustomer(){
		String name=combo_name.getText().trim();
		String tel=text_tel.getText().trim();
		String id_number=text_id_number.getText().trim();
		String sex="";
		if (button_man.getSelection()) {
			sex="男";
		} else if(button_woman.getSelection()){
			sex="女";
		}
		
		if (null==name||"".equals(name)||null==tel||"".equals(tel)||null==id_number||"".equals(id_number)) {
			return;
		}
		
		try {

			//根据客户电话查询客户信息     如果客户表中的手机号与文本框中的手机号相同，则证明表中已有该客户的信息  反之添加
			if (!customerDao.find(tel)) {
				List<Object> params=new ArrayList<Object>();
				params.add(name);
				params.add(tel);
				params.add(id_number);
				params.add(sex);
			
				int result=customerDao.addCustomer(params);
				if (result<0) {
					SwtUtil.showMessage(getParent(), "错误提示", "用户信息添加失败...");
				}
			}
			
			
		} catch (SQLException e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();
		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}
	}

	//将对应空闲的房间类型的房间号下拉框
	public void showRoomId(){
		PlaySound.mouseClick();//播放鼠标点击音
		String r_type=combo_type.getText().trim();
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("R_ID", null);
		map1.put("R_TYPE", r_type);
		map1.put("R_STATE", null);
		
		try {
			List<Map<String, Object>> list=roomDao.findRoom(map1);
			combo_rid.removeAll();
			if (null==list||list.size()<=0) {
				SwtUtil.showMessage(getParent(), "错误提示", "无房间信息...");
				return;
			}
			String [] str=new String [20];
			int i=0;
			for (Map<String, Object> map : list) {
				str[i]=StringUtil.objToString(map.get("R_ID"));
				i++;
			}
			String [] str1=new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j]=str[j];
			}
			combo_rid.setItems(str1);
		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();
		}
	}
}
