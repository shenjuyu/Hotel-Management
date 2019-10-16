package com.yc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.CustomerDao;
import com.yc.dao.EnterDao;
import com.yc.dao.ReserveDao;
import com.yc.dao.RoomDao;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

/**
 * 预定界面
 * 
 * @author 沈俊羽
 *
 */
public class ReserveComposite extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_id_number;// 身份证
	private Text text_ctel;// 手机号
	private Text text_price;// 房价
	private Text text_deposit;// 押金
	private RoomDao roomDao = new RoomDao();
	private Combo combo_rid;// 房间id
	private Combo combo_type;
	private Text text_joindate;// 入住日期
	private Text text_num;// 入住天数
	private Text text_nowdate;// 预约时那天的日期
	private Text text_leavedate;// 离开日期
	private Label tishi_joindate;// 提示加入日期
	private CustomerDao customerDao = new CustomerDao();
	private Button button_woman;// 选择性别按钮 女
	private Button button_man;// 选择性别按钮 男
	private Combo combo_cname;// 拉窗式选择客户姓名
	private Label tishi_name;// 提示姓名不能为空
	private ReserveDao reserveDao = new ReserveDao();
	private EnterDao enterDao=new EnterDao();
	private Label tishi_id_number;// 提示身份证格式
	private Label tishi_ctel;// 提示手机号码格式
	private Text text_ctel1;
	private Text text_c_id_number1;// 取消预定界面客户身份证
	private Text text_joindate1;// 取消预定的入住日期
	private Text text_canceldate1;// 取消预定的日期
	private Text text_type1;// 取消预定的房间类别
	private Text text_price1;// 取消预定的房间价钱
	private Text text_deposit1;// 取消预定的房间押金s
	private Combo combo_rid1;// 取消预定的的房间号
	private Text text_nowdate1;
	private Combo combo_cname1;

	private Date date = new Date();
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
	private Text text_cid;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ReserveComposite(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--客房预订");
	}

	/**
	 * Open the dialog.
	 * 
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
		shell.setImage(SWTResourceManager.getImage(ReserveComposite.class, "/images/WindowIcon.png"));
		shell.setSize(926, 642);
		shell.setText(getText());
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, 
				(dimension.height - shell.getSize().y) / 2);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(0, 0, 910, 607);

		TabItem tbtmNewI = new TabItem(tabFolder, SWT.NONE);
		tbtmNewI.setText("预定");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		tbtmNewI.setControl(composite);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Group grpAsgs = new Group(composite, SWT.BORDER);
		grpAsgs.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		grpAsgs.setText("客房信息");
		grpAsgs.setBounds(10, 10, 549, 237);

		// 查看房间信息得到房间号
		combo_type = new Combo(grpAsgs, SWT.READ_ONLY);
		combo_type.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_type.setItems(new String[] {"经济房", "高级房", "总统套房"});
		combo_type.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				findroomid();
			}
		});
		combo_type.setBounds(89, 63, 114, 28);

		// 选择房间号,得到押金和房价
		combo_rid = new Combo(grpAsgs, SWT.READ_ONLY);
		combo_rid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_rid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PlaySound.mouseClick();//播放鼠标点击音
				String r_rid = combo_type.getText().trim();
				String r_price = "";
				String r_deposit = "";

				Map<String, Object> map1 = new HashMap<String, Object>();
				if (null != r_rid && !"".equals(r_rid)) {
					map1.put("R_TYPE", r_rid);
				}
				if (null != r_price && !"".equals(r_price)) {
					map1.put("R_PRICE", r_price);
				}
				if (null != r_deposit && !"".equals(r_deposit)) {
					map1.put("R_DEPOSIT", r_deposit);
				}

				try {
					List<Map<String, Object>> list = roomDao.findRoom(map1);
					for (Map<String, Object> map : list) {
						text_price.setText(StringUtil.objToString(map.get("R_PRICE")));
						text_deposit.setText(StringUtil.objToString(map.get("R_DEPOSIT")));

					}

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}
		});
		combo_rid.setBounds(89, 146, 114, 28);
		combo_rid.select(0);

		Label label_price = new Label(grpAsgs, SWT.NONE);
		label_price.setAlignment(SWT.RIGHT);
		label_price.setBounds(238, 66, 76, 20);
		label_price.setText("价格(元/日)：");

		text_price = new Text(grpAsgs, SWT.BORDER | SWT.READ_ONLY);
		text_price.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_price.setBounds(328, 63, 114, 26);

		Label label_type = new Label(grpAsgs, SWT.NONE);
		label_type.setAlignment(SWT.RIGHT);
		label_type.setBounds(10, 66, 76, 20);
		label_type.setText("房间类型：");

		Label label_rid = new Label(grpAsgs, SWT.NONE);
		label_rid.setAlignment(SWT.RIGHT);
		label_rid.setBounds(7, 146, 76, 20);
		label_rid.setText("房间号：");

		Label label_deposit = new Label(grpAsgs, SWT.NONE);
		label_deposit.setAlignment(SWT.RIGHT);
		label_deposit.setBounds(223, 146, 91, 20);
		label_deposit.setText("押金：");

		text_deposit = new Text(grpAsgs, SWT.BORDER | SWT.READ_ONLY);
		text_deposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_deposit.setBounds(328, 146, 114, 26);

		Group group = new Group(composite, SWT.BORDER);
		group.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		group.setText("客户信息");
		group.setBounds(565, 10, 327, 424);

		Label label_cname = new Label(group, SWT.NONE);
		label_cname.setAlignment(SWT.RIGHT);
		label_cname.setBounds(31, 59, 76, 20);
		label_cname.setText("姓名：");

		Label label_cid = new Label(group, SWT.NONE);
		label_cid.setAlignment(SWT.RIGHT);
		label_cid.setBounds(31, 138, 76, 20);
		label_cid.setText("身份证：");

		Label label_ctel = new Label(group, SWT.NONE);
		label_ctel.setAlignment(SWT.RIGHT);
		label_ctel.setBounds(31, 215, 76, 20);
		label_ctel.setText("手机号：");

		Label label_sex = new Label(group, SWT.NONE);
		label_sex.setAlignment(SWT.RIGHT);
		label_sex.setBounds(31, 286, 76, 20);
		label_sex.setText("性别：");

		text_id_number = new Text(group, SWT.BORDER);
		text_id_number.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_id_number.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String id_number = text_id_number.getText().trim();// 身份证号验证
				String ID_RG = "^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
				if (null == id_number || "".equals(id_number)) {
					tishi_id_number.setText("身份证号不能为空...");
					return;
				} else if (!id_number.matches(ID_RG)) {
					tishi_id_number.setText("身份证格式不正确...");
					return;
				} else {
					tishi_id_number.setText("");
				}

			}
		});
		text_id_number.setBounds(113, 135, 179, 26);

		text_ctel = new Text(group, SWT.BORDER);
		text_ctel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_ctel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String tel_RG = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";// 手机号验证
				String tel1 = text_ctel.getText().trim();
				if (null == tel1 || "".equals(tel1)) {
					tishi_ctel.setText("手机号不能为空...");
					return;
				} else if (!tel1.matches(tel_RG)) {
					tishi_ctel.setText("手机号格式不正确...");
					return;
				} else {
					tishi_ctel.setText("");
				}
			}
		});
		text_ctel.setBounds(113, 212, 179, 26);

		button_man = new Button(group, SWT.RADIO);
		button_man.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_man.setBounds(133, 286, 51, 20);
		button_man.setText("男");

		button_woman = new Button(group, SWT.RADIO);
		button_woman.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_woman.setBounds(190, 286, 62, 20);
		button_woman.setText("女");

		combo_cname = new Combo(group, SWT.NONE);
		combo_cname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_cname.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String cname = combo_cname.getText().trim();
				String c_id = "";
				String c_id_number = "";
				String ctel = "";
				String attribute = "客户姓名";
				String sex = "";

				Map<String, Object> map = new HashMap<>();
				map.put("C_ID", c_id);
				map.put("C_NAME", cname);
				map.put("C_ID_NUMBER", c_id_number);
				map.put("C_TEL", ctel);

				try {
					List<Map<String, Object>> list = customerDao.findCustomer(attribute, map);
					for (Map<String, Object> map1 : list) {
						text_ctel.setText(StringUtil.objToString(map1.get("C_TEL")));
						text_id_number.setText(StringUtil.objToString(map1.get("C_ID_NUMBER")));
						sex = StringUtil.objToString(map1.get("C_SEX"));
					}
					if ("男".equals(sex)) {
						button_man.setSelection(true);
						button_woman.setSelection(false);
					} else if ("女".equals(sex)) {
						button_man.setSelection(false);
						button_woman.setSelection(true);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		combo_cname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name = combo_cname.getText().trim();

				if (null == name || "".equals(name)) {
					tishi_name.setText("姓名不能为空");
					return;
				}
			}
		});
		combo_cname.setBounds(113, 56, 179, 28);

		tishi_id_number = new Label(group, SWT.NONE);
		tishi_id_number.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_id_number.setBounds(113, 167, 159, 20);

		tishi_ctel = new Label(group, SWT.NONE);
		tishi_ctel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_ctel.setBounds(113, 244, 159, 20);

		tishi_name = new Label(group, SWT.NONE);
		tishi_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_name.setBounds(113, 93, 159, 20);

		Group group_1 = new Group(composite, SWT.BORDER);
		group_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		group_1.setText("预定");
		group_1.setBounds(10, 268, 549, 296);

		Label label_joindate = new Label(group_1, SWT.NONE);
		label_joindate.setBounds(10, 66, 76, 20);
		label_joindate.setText("入住日期：");

		// 得到正确的日期格式
		text_joindate = new Text(group_1, SWT.BORDER);
		text_joindate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_joindate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String join = text_joindate.getText().trim();
				String nowDate=text_nowdate.getText().trim();
				// 日期的正则表达式
				String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
				// 判断输入的日期格式
				if (null == join || "".equals(join)) {
					tishi_joindate.setText("入住日期不能为空");
					return;
				} else if (!join.matches(rexp1) || "".equals(join)) {
					tishi_joindate.setText("入住日期格式不正确");
					return;
				} else {
					tishi_joindate.setText("");
				}
				
				Date date=new Date();
				Date date2=new Date();
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				try {
					date=simpleDateFormat.parse(join);
					long a=date.getTime();//预定的入住时间的毫秒数
					date2=simpleDateFormat.parse(nowDate);
					long b=date2.getTime();//当前时间的毫秒数
					if (a<b) {
						tishi_joindate.setText("时间输入错误");
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		text_joindate.setBounds(92, 63, 108, 26);

		Label label_num = new Label(group_1, SWT.NONE);
		label_num.setBounds(10, 156, 76, 20);
		label_num.setText("预定天数：");

		// 得到退房时间
		text_num = new Text(group_1, SWT.BORDER);
		text_num.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_num.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String inDate = text_joindate.getText().trim();// 时间
				String num = text_num.getText().trim();// 天数
				if (num == null || "".equals(num)) {
					return;
				}
				long inNum = Long.parseLong(num);
				if (inNum<=0) {
					SwtUtil.showMessage(getParent(), "提示", "入住天数不能为负数");
					return;
				}
				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
				try {
					date = format.parse(inDate);// 传进
					long start = date.getTime();// 得到毫秒数
					long dd = start + inNum * 1000 * 3600 * 24;// 天数转化为毫秒数再相加
					Date date2 = new Date(dd);
					text_leavedate.setText(format.format(date2));

					findReservedate();
					findEnterdate();
				} catch (ParseException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}

			}
		});
		text_num.setBounds(92, 153, 108, 26);

		Label label_nowdate = new Label(group_1, SWT.NONE);
		label_nowdate.setBounds(268, 66, 76, 20);
		label_nowdate.setText("预定日期：");

		text_nowdate = new Text(group_1, SWT.BORDER | SWT.READ_ONLY);
		text_nowdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_nowdate.setBounds(350, 63, 108, 26);
		// 得到预定日期的时间
		text_nowdate.setText(format.format(date));

		Label label_leavedate = new Label(group_1, SWT.NONE);
		label_leavedate.setBounds(268, 156, 76, 20);
		label_leavedate.setText("退房日期");

		text_leavedate = new Text(group_1, SWT.BORDER | SWT.READ_ONLY);
		text_leavedate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_leavedate.setBounds(350, 153, 108, 26);

		tishi_joindate = new Label(group_1, SWT.NONE);
		tishi_joindate.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_joindate.setBounds(92, 95, 139, 20);

		// 预定 添加入住的日期与客户信息
		Button button = new Button(composite, SWT.NONE);
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PlaySound.mouseClick();//播放鼠标点击音
				String join = text_joindate.getText().trim();// 预定入住的日期
				String num = text_num.getText().trim();// 入住天数
				String now = text_nowdate.getText().trim();// 预定房间时的时间
				String leave = text_leavedate.getText().trim();// 离开时候的日期
				String ctel = text_ctel.getText().trim();// 客户电话
				String rid = combo_rid.getText().trim();// 房间号码
				String cid = "";// 客户编号
				String r_state = "预定";// 更新房间状态时用
				String name=combo_cname.getText().trim();//姓名验证
				String id_number=text_id_number.getText().trim();//身份证号验证
				
				try {
					Date date=new Date();
					Date date2=new Date();
					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
					date=simpleDateFormat.parse(join);
					long a=date.getTime();//预定的入住时间的毫秒数
					date2=simpleDateFormat.parse(now);
					long b=date2.getTime();//当前时间的毫秒数
					if (a<b) {
						SwtUtil.showMessage(getParent(), "错误提示", "时间输入错误");
						return;
					}
				} catch (ParseException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
				if (null==name||"".equals(name)) {
					SwtUtil.showMessage(getParent(), "错误提示", "姓名不能为空...");
					return;
				}
				
				String tel_RG="^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";//手机号验证
				if (null==ctel||"".equals(ctel)) {
					SwtUtil.showMessage(getParent(), "错误提示", "手机号不能为空...");
					return;
				} else if(!ctel.matches(tel_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "手机号格式不正确...");
					return;
				}
				
				String ID_RG="^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
				if (null==id_number||"".equals(id_number)) {
					SwtUtil.showMessage(getParent(), "错误提示", "身份证号不能为空...");
					return;
				} else if(!id_number.matches(ID_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "身份证格式不正确...");
					return;
				}
				
				if (null==num||"".endsWith(num)) {
					SwtUtil.showMessage(getParent(), "错误提示", "天数不能为空...");
					return;
				}else if (Double.parseDouble(num)<=0) {
					SwtUtil.showMessage(getParent(), "提示", "入住天数不能为负数");
					return;
				}
				

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("C_ID", null);
				map.put("C_NAME", null);
				map.put("C_SEX", null);
				map.put("C_TEL", ctel);
				// 根据客户手机号查询客户信息 如果客户表中有信息，则证明表中已有该客户的信息 反之添加
				String attribute="手机号";
				
				try {
					List<Map<String, Object>> list=reserveDao.findAllReserve();
					if (null != list && list.size() > 0) {
						for (Map<String, Object> map2 : list) {
							if (ctel.equals(StringUtil.objToString(map2.get("C_TEL")))) {
								SwtUtil.showMessage(getParent(), "提示", "该用户已预约房间\n不能再次预约");
							return;
							}
						}
					}
					
					List<Map<String, Object>> list4=enterDao.findEnterAll();
					if (null != list4 && list4.size() > 0) {
						for (Map<String, Object> map2 : list4) {
							if (ctel.equals(StringUtil.objToString(map2.get("C_TEL")))) {
								SwtUtil.showMessage(getParent(), "提示", "该用户已入住\n不能再次预约");
								return;
							}
						}
					}
					
					if (!customerDao.find(ctel)) {
						addCustomer();// 添加客户信息
					}
					
					List<Map<String, Object>> list1 = customerDao.findCustomer(attribute, map);
					if (null == list1 || list1.size() <= 0) {
						SwtUtil.showMessage(getParent(), "提示", "该手机号对应的客户不存在");
						return;
					}
					for (Map<String, Object> map2 : list1) {
						cid = StringUtil.objToString(map2.get("C_ID"));//得到c_id
					}
					
					
					
					List<Object> list2 = new ArrayList<Object>();// 添加预定信息用
					list2.add(cid);
					list2.add(rid);
					list2.add(now);
					list2.add(join);
					list2.add(num);
					list2.add(leave);
					list2.add("1970-01-01");

					List<Object> list3 = new ArrayList<Object>();// 更改房间状态用
					list3.add(r_state);
					list3.add(rid);

					List<List<Object>> params = new ArrayList<List<Object>>();
					params.add(list3);
					params.add(list2);
					int result = reserveDao.updateReserveRoom(params);// 更改房间状态&&添加预定信息
					if (result <= 0) {
						SwtUtil.showMessage(getParent(), "错误提示", "预定失败...");
					}
					if (result >= 1) {
						SwtUtil.showMessage(getParent(), "温馨提示", "欢迎预定首席大酒店...");
						text_price.setText("");
						text_deposit.setText("");
						text_joindate.setText("");
						text_num.setText("");
						text_leavedate.setText("");
						text_id_number.setText("");
						text_ctel.setText("");
						button_man.setSelection(false);
						button_woman.setSelection(false);
						FindEverCustomer();//刷新姓名
						combo_type.select(-1);
						findroomid();//刷新房间号
//						//刷新主界面
//						MainUI mainUI=new MainUI();
//						mainUI.rushRoom();
					}

				} catch (SQLException e) {
					SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setBounds(589, 471, 112, 43);
		button.setText("预定");

		// 返回主界面
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		button_1.setLocation(747, 471);
		button_1.setSize(112, 43);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PlaySound.mouseClick();//播放鼠标点击音
				shell.dispose();
			}
		});
		button_1.setText("返回");

		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("取消预定");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tabItem.setControl(composite_1);
		composite_1.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Group group_2 = new Group(composite_1, SWT.BORDER);
		group_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		group_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		group_2.setText("查询客户信息");
		group_2.setBounds(10, 10, 882, 554);

		Label label_ctel1 = new Label(group_2, SWT.NONE);
		label_ctel1.setAlignment(SWT.RIGHT);
		label_ctel1.setBounds(10, 357, 90, 20);
		label_ctel1.setText("手机号：");

		text_ctel1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_ctel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_ctel1.setBounds(102, 354, 172, 26);

		Label label_cname1 = new Label(group_2, SWT.NONE);
		label_cname1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_cname1.setAlignment(SWT.RIGHT);
		label_cname1.setBounds(392, 95, 107, 20);
		label_cname1.setText("客户姓名：");

		Label label_c_id_number1 = new Label(group_2, SWT.NONE);
		label_c_id_number1.setBounds(10, 275, 90, 20);
		label_c_id_number1.setText("客户身份证：");

		text_c_id_number1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_c_id_number1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_c_id_number1.setBounds(102, 272, 172, 26);

		Label label_joindate1 = new Label(group_2, SWT.NONE);
		label_joindate1.setBounds(552, 275, 115, 20);
		label_joindate1.setText("预定入住的日期：");

		text_joindate1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_joindate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_joindate1.setBounds(691, 272, 134, 26);

		Label label_cancel1 = new Label(group_2, SWT.NONE);
		label_cancel1.setBounds(552, 357, 115, 20);
		label_cancel1.setText("取消入住的日期：");

		text_canceldate1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_canceldate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_canceldate1.setBounds(691, 354, 134, 26);
		// 显示取消时的当天日期
		text_canceldate1.setText(format.format(date));

		Label label_type1 = new Label(group_2, SWT.NONE);
		label_type1.setBounds(292, 193, 76, 20);
		label_type1.setText("房间类型：");

		text_type1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_type1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_type1.setBounds(384, 190, 115, 26);

		Label label_rid1 = new Label(group_2, SWT.RIGHT);
		label_rid1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_rid1.setAlignment(SWT.RIGHT);
		label_rid1.setBounds(56, 93, 152, 22);
		label_rid1.setText("请选择房间号：");

		Label label_price1 = new Label(group_2, SWT.NONE);
		label_price1.setAlignment(SWT.RIGHT);
		label_price1.setBounds(292, 275, 76, 20);
		label_price1.setText("房价：");

		text_price1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_price1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_price1.setBounds(384, 272, 115, 26);

		Label label_deposit1 = new Label(group_2, SWT.NONE);
		label_deposit1.setAlignment(SWT.RIGHT);
		label_deposit1.setBounds(292, 357, 76, 20);
		label_deposit1.setText("押金：");

		text_deposit1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_deposit1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_deposit1.setBounds(384, 354, 115, 26);

		// 取消预定
		Button button_2 = new Button(group_2, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PlaySound.mouseClick();//播放鼠标点击音
				String r_id = combo_rid1.getText().trim();
				String r_state = "空闲";
				List<Object> params = new ArrayList<>();
				params.add(r_id);

				List<Object> params1 = new ArrayList<>();
				params1.add(r_state);
				params1.add(r_id);
				String name=combo_cname1.getText().trim();
				if (null==r_id||"".equals(r_id)) {
					return;
				}
				if (null==name||"".equals(name)) {
					return;
				}
				String tel=text_ctel1.getText().trim();
				if (null==tel||"".equals(tel)) {
					return;
				}

				try {

					MessageBox messageBox = new MessageBox(getParent(), SWT.YES | SWT.NO);
					messageBox.setText("提示");
					messageBox.setMessage("是否取消预定？");
					if (messageBox.open() == SWT.YES) {

						// 房间状态
						int result1 = roomDao.updateRoomState(params1);
						if (result1 < 1) {
							SwtUtil.showMessage(getParent(), "错误提示", "取消预定失败");
							return;
						}

						// 取消预定
						int result = reserveDao.deleteReserve(params);
						if (result > 0) {
							SwtUtil.showMessage(getParent(), "温馨提示", "取消预定成功");
							text_type1.setText("");
							text_price1.setText("");
							text_deposit1.setText("");
							text_cid.setText("");
							text_c_id_number1.setText("");
							text_ctel1.setText("");
							text_nowdate1.setText("");
							text_joindate1.setText("");
							cancelfindroomid();//刷新房间号
							findCustomerName();//刷新客户姓名
//							//刷新主界面
//							MainUI mainUI=new MainUI();
//							mainUI.showRoom(mainUI.group, "经济房");
//							mainUI.showRoom(mainUI.group_1, "高级房");
//							mainUI.showRoom(mainUI.group_2, "总统套房");
						} else {
							SwtUtil.showMessage(getParent(), "错误提示", "取消预定失败");
						}
					}

				} catch (SQLException e) {

					SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
					e.printStackTrace();
				}

			}

		});
		button_2.setBounds(638, 470, 120, 40);
		button_2.setText("取消预定");

		// 取消预定时,//根据房间号来查出客户姓名
		combo_rid1 = new Combo(group_2, SWT.NONE);
		combo_rid1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_rid1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				findCustomerName();

			}
		});
		combo_rid1.setBounds(214, 93, 115, 28);

		Label label_nowdate1 = new Label(group_2, SWT.NONE);
		label_nowdate1.setBounds(552, 193, 115, 20);
		label_nowdate1.setText("何时预定的日期：");

		text_nowdate1 = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_nowdate1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_nowdate1.setBounds(691, 193, 134, 26);

		// 取消预定时，显示客户信息
		combo_cname1 = new Combo(group_2, SWT.NONE);
		combo_cname1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_cname1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				findcustomer();

			}
		});
		combo_cname1.setBounds(515, 94, 172, 28);

		Label label = new Label(group_2, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(20, 193, 76, 20);
		label.setText("客户编号：");

		text_cid = new Text(group_2, SWT.BORDER | SWT.READ_ONLY);
		text_cid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_cid.setBounds(102, 193, 172, 26);

		cancelfindroomid();
		FindEverCustomer();

	}

	/**
	 * 取消预定时
	 * 根据房间号来查出客户姓名
	 */
	public void findCustomerName(){
		PlaySound.mouseClick();//播放鼠标点击音
		String r_id = combo_rid1.getText().trim();// 房间号
		
		List<Object> params = new ArrayList<Object>();
		params.add(r_id);

		try {
			List<Map<String, Object>> list = reserveDao.findnamebyrid(params);

			if (list == null) {
				return;
			}
			// 显示客户姓名
			String[] str = new String[20];
			int i = 0;
			for (Map<String, Object> map1 : list) {
				str[i] = StringUtil.objToString(map1.get("C_NAME"));
				i++;

			}
			String[] str1 = new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j] = str[j];

			}

			combo_cname1.setItems(str1);

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	// 显示以前的客户信息
	public void FindEverCustomer() {
		String c_id = "";// 客户编号
		String cname = "";// 添加客户信息
		String c_id_number = "";// 身份证
		String ctel = "";// 电话
		String attribute = "查找所有";

		Map<String, Object> map = new HashMap<>();
		map.put("C_ID", c_id);
		map.put("C_NAME", cname);
		map.put("C_ID_NUMBER", c_id_number);
		map.put("C_TEL", ctel);

		combo_cname.removeAll();

		try {
			List<Map<String, Object>> list = customerDao.findCustomer(attribute, map);

			// 显示客户姓名
			String[] str = new String[20];
			int i = 0;
			for (Map<String, Object> map1 : list) {
				str[i] = StringUtil.objToString(map1.get("C_NAME"));
				i++;

				// text_ctel.setText(StringUtil.objToString(map1.get("C_TEL")));
				// text_id_number.setText(StringUtil.objToString(map1.get("C_ID_NUMBER")));
			}
			String[] str1 = new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j] = str[j];
			}
			combo_cname.setItems(str1);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 取消预定时,根据房间状态查询房间号
	public void cancelfindroomid() {
		String r_id = "";// 房间号
		String r_state = "预定";// 状态

		String r_type = "";// 房间类型

		combo_rid1.removeAll();
		Map<String, Object> map1 = new HashMap<String, Object>();

		if (null != r_id && !"".equals(r_id)) {
			map1.put("R_ID", r_id);
		}
		if (null != r_state && !"".equals(r_state)) {
			map1.put("R_STATE", r_state);
		}
		if (null != r_type && !"".equals(r_type)) {// 类型
			map1.put("R_TYPE", r_type);
		}
		try {
			List<Map<String, Object>> list = roomDao.findRoom(map1);
			if (null == list || list.size() <= 0) {
				// SwtUtil.showMessage(getParent(), "错误提示", "未找到房间");
				return;
			}

			// 显示房间号
			String[] str = new String[20];
			int i = 0;
			for (Map<String, Object> map : list) {
				str[i] = StringUtil.objToString(map.get("R_ID"));
				i++;
			}
			String[] str1 = new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j] = str[j];
			}
			combo_rid1.setItems(str1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/// 添加客户信息
	protected void addCustomer() {
		String cname = combo_cname.getText().trim();
		String cid_number = text_id_number.getText().trim();
		String ctel = text_ctel.getText().trim();
		String sex = "";
		String tel_RG = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";// 手机号验证

		if (button_man.getSelection()) {
			sex = "男";
		} else if (button_woman.getSelection()) {
			sex = "女";
		} else if ("".equals(sex)) {
			SwtUtil.showMessage(getParent(), "错误提示", "请选择性别");
			return;
		}

		if (null == cname || "".equals(cname)) {
			tishi_name.setText("姓名不能为空");
			return;
		}

		if (null == ctel || "".equals(ctel)) {
			tishi_ctel.setText("手机号不能为空...");
			return;
		} else if (!ctel.matches(tel_RG)) {
			tishi_ctel.setText("手机号格式不正确...");
			return;
		} else {
			tishi_ctel.setText("");
		}

		String ID_RG = "^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
		if (null == cid_number || "".equals(cid_number)) {
			tishi_id_number.setText("身份证号不能为空...");
			return;
		} else if (!cid_number.matches(ID_RG)) {
			tishi_id_number.setText("身份证格式不正确...");
			return;
		} else {
			tishi_id_number.setText("");
		}

		try {

			// 根据客户电话查询客户信息 如果客户表中的手机号与文本框中的手机号相同，则证明表中已有该客户的信息 反之添加
			if (!customerDao.find(ctel)) {
				List<Object> params = new ArrayList<Object>();
				params.add(cname);
				params.add(ctel);
				params.add(cid_number);
				params.add(sex);

				int result = customerDao.addCustomer(params);
				if (result < 0) {
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

	// 根据房间类型查询房间号
	public void findroomid() {
		PlaySound.mouseClick();//播放鼠标点击音
		String r_id = "";
		String r_type = combo_type.getText().trim();
		String r_state = "";
		
		if (null==r_type||"".equals(r_type)) {
			return;
		}
		
		combo_rid.removeAll();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (null != r_id && !"".equals(r_id)) {
			map1.put("R_ID", r_id);
		}
		if (null != r_type && !"".equals(r_type)) {
			map1.put("R_TYPE", r_type);
		}
		if (null != r_state && !"".equals(r_state)) {
			map1.put("R_STATE", r_state);
		}

		try {
			List<Map<String, Object>> list = roomDao.findRoom(map1);
			if (null == list || list.size() <= 0) {
				SwtUtil.showMessage(getParent(), "错误提示", "未找到房间");
				return;
			}

			// 显示房间号
			String[] str = new String[20];
			int i = 0;
			for (Map<String, Object> map : list) {
				str[i] = StringUtil.objToString(map.get("R_ID"));
				i++;
			}
			String[] str1 = new String[i];
			for (int j = 0; j < str1.length; j++) {
				str1[j] = str[j];
			}
			combo_rid.setItems(str1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 判断房间日期是否重合
	public void findReservedate() {
		String r_id = combo_rid.getText().trim();
		String joindate = text_joindate.getText().trim();
		//String leavedate = text_leavedate.getText().trim();
		Date date1 = null;
		//Date date2 = null;
		Date date3 = null;
		Date date4 = null;

		List<Object> params = new ArrayList<Object>();

		params.add(r_id);

		String[] str = new String[20];
		String[] str1 = new String[20];
		int i = 0;

		try {
			List<Map<String, Object>> list = reserveDao.findReserveByRid1(params);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : list) {
				str[i] = StringUtil.objToString(map.get("E_RES_COMDATE"));
				str1[i] = StringUtil.objToString(map.get("E_LEAVE_DATE"));


				date1 = format.parse(joindate);// 输入的入住日期
				//date2 = format.parse(leavedate);// 输入的退房日期
				date3 = format.parse(str[i]);// 已存入的预约入住日期
				date4 = format.parse(str1[i]);// 已存入的预约退房日期

				// 得到毫秒
				long join = date1.getTime();
				//long leave = date2.getTime();
				long everjoin = date3.getTime();
				long everleave = date4.getTime();

				// 判断要预约的时间是否被占据
				long aa = join - everjoin;// 输入的入住日期-已存入的预约入住日期
				long bb = everleave - join;// 已存入的预约退房日期-输入的入住日期

				if (aa >= 0 && 0 < bb) {

					SwtUtil.showMessage(getParent(), "温馨提示", "此时间已被预约,请在此时间之后预约:" + str1[i]);
					return;

				}
				i++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	// 判断房间日期是否重合
	public void findEnterdate() {
		String r_id = combo_rid.getText().trim();
		String joindate = text_joindate.getText().trim();
		//String leavedate = text_leavedate.getText().trim();
		Date date1 = null;
		//Date date2 = null;
		Date date3 = null;
		Date date4 = null;

		List<Object> params = new ArrayList<Object>();

		params.add(r_id);

		String[] str = new String[20];
		String[] str1 = new String[20];
		int i = 0;

		try {
			List<Map<String, Object>> list = enterDao.findEnterByRid1(params);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : list) {
				str[i] = StringUtil.objToString(map.get("JOIN_DATE"));
				str1[i] = StringUtil.objToString(map.get("RETURN_DATE"));


				date1 = format.parse(joindate);// 输入的入住日期
				//date2 = format.parse(leavedate);// 输入的退房日期
				date3 = format.parse(str[i]);// 已存入的预约入住日期
				date4 = format.parse(str1[i]);// 已存入的预约退房日期

				// 得到毫秒
				long join = date1.getTime();
				//long leave = date2.getTime();
				long everjoin = date3.getTime();
				long everleave = date4.getTime();

				// 判断要预约的时间是否被占据
				long aa = join - everjoin;// 输入的入住日期-已存入的预约入住日期
				long bb = everleave - join;// 已存入的预约退房日期-输入的入住日期

				if (aa >= 0 && 0 < bb) {

					SwtUtil.showMessage(getParent(), "温馨提示", "此时间已被入住,请在此时间之后预约:" + str1[i]);
					return;

				}
				i++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 取消预定时根据姓名得到客户信息
	public void findcustomer() {
		PlaySound.mouseClick();//播放鼠标点击音
		String c_name1 = combo_cname1.getText().trim();// 姓名

		// String c_name1="";
		/*
		 * String r_state = "预约";//状态
		 * 
		 * String r_type="";//房间类型 String r_price="";//房价 String r_deposit
		 * ="";//房间押金
		 * 
		 * String cname="";//客户姓名 String c_id_number="";//客户身份证 String
		 * ctel="";//电话
		 * 
		 * String join="";//预定入住的日期 String num="";//入住天数 String
		 * true_leave="";//取消时候的日期
		 */

		List<Object> params = new ArrayList<Object>();
		params.add(c_name1);

		try {
			Map<String, Object> map1 = reserveDao.findReserveByCid2(params);

			text_type1.setText(StringUtil.objToString(map1.get("R_TYPE")));
			text_price1.setText(StringUtil.objToString(map1.get("R_PRICE")));
			text_deposit1.setText(StringUtil.objToString(map1.get("R_DEPOSIT")));

			text_cid.setText(StringUtil.objToString(map1.get("C_ID")));
			text_c_id_number1.setText(StringUtil.objToString(map1.get("C_ID_NUMBER")));
			text_ctel1.setText(StringUtil.objToString(map1.get("C_TEL")));

			text_nowdate1.setText(StringUtil.objToString(map1.get("E_RESERVEDATE")));
			text_joindate1.setText(StringUtil.objToString(map1.get("E_RES_COMDATE")));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

}
