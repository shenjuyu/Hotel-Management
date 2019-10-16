package com.yc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.CustomerDao;
import com.yc.dao.ReserveDao;
import com.yc.dao.RoomDao;
import com.yc.util.ImageResize;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

/**
 * 客户管理界面
 * 
 * @author 沈俊羽
 *
 */
public class CustomerComposite extends Dialog {

	private ReserveDao reserveDao = new ReserveDao();
	private CustomerDao customerDao = new CustomerDao();
	private RoomDao roomDao = new RoomDao();

	protected Object result;
	protected Shell shell;
	private Table table;
	private Text text_tel;
	private Text text_cname;
	private Text text_cname1;
	private Combo combo_rid3;
	private Text text_oldTel;
	private Text text_to_page;
	private Label label_page;
	int NowPage1 = 1;
	int Allpage1 = 0;

	boolean rid;
	boolean all;
	boolean name;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public CustomerComposite(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--预约客户管理");
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
		shell.setImage(SWTResourceManager.getImage(CustomerComposite.class, "/images/WindowIcon.png"));
		shell.setSize(1168, 582);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);

		SashForm sashForm = new SashForm(shell, SWT.NONE);

		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		composite_1.setBackgroundMode(SWT.INHERIT_DEFAULT);

		table = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		// 点击表格后将姓名自动添加到文本框中
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
				TableItem item = (TableItem) e.item;
				text_cname.setText(item.getText(2).trim());
				text_oldTel.setText(item.getText(5).trim());
			}
		});
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		table.setBounds(243, 20, 909, 320);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(44);
		tblclmnNewColumn.setText("序号");

		TableColumn table_cid = new TableColumn(table, SWT.NONE);
		table_cid.setWidth(89);
		table_cid.setText("客户编号");

		TableColumn table_cname = new TableColumn(table, SWT.NONE);
		table_cname.setWidth(85);
		table_cname.setText("客户姓名");

		TableColumn table_csex = new TableColumn(table, SWT.NONE);
		table_csex.setWidth(75);
		table_csex.setText("客户性别");

		TableColumn table_c_id_number = new TableColumn(table, SWT.NONE);
		table_c_id_number.setWidth(173);
		table_c_id_number.setText("客户身份证");

		TableColumn table_c_tel = new TableColumn(table, SWT.NONE);
		table_c_tel.setWidth(143);
		table_c_tel.setText("客户手机号");

		TableColumn table_joindate = new TableColumn(table, SWT.NONE);
		table_joindate.setWidth(139);
		table_joindate.setText("入住日期");

		TableColumn table_leavedate = new TableColumn(table, SWT.NONE);
		table_leavedate.setWidth(153);
		table_leavedate.setText("退房日期");

		Group group_1 = new Group(composite_1, SWT.BORDER);
		group_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		group_1.setText("更改用户信息");
		group_1.setBounds(243, 375, 909, 162);

		Label label = new Label(group_1, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label.setBounds(52, 55, 76, 20);
		label.setText("客户姓名：");

		Label lblNewLabel = new Label(group_1, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lblNewLabel.setBounds(581, 55, 110, 20);
		lblNewLabel.setText("修改电话号码：");

		text_tel = new Text(group_1, SWT.BORDER);
		text_tel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
			}
		});
		text_tel.setBounds(697, 54, 149, 26);

		// 修改电话号码
		Button btnNewButton = new Button(group_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updatetel();
			}
		});
		btnNewButton.setBounds(720, 103, 126, 40);
		btnNewButton.setText("修改");

		text_cname = new Text(group_1, SWT.BORDER | SWT.READ_ONLY);
		text_cname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
			}
		});
		text_cname.setBounds(134, 54, 149, 26);

		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setAlignment(SWT.RIGHT);
		label_3.setBounds(307, 57, 90, 20);
		label_3.setText("旧电话号码：");

		text_oldTel = new Text(group_1, SWT.BORDER | SWT.READ_ONLY);
		text_oldTel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
			}
		});
		text_oldTel.setBounds(403, 54, 149, 26);

		Group group = new Group(composite_1, SWT.BORDER);
		group.setText("查询客户信息");
		group.setBounds(10, 20, 227, 517);

		// 客户姓名查询客户信息
		Button Button_find = new Button(group, SWT.NONE);
		Button_find.setBounds(110, 133, 65, 26);
		Button_find.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
				all = false;
				rid = false;
				name = true;
				NowPage1 = 1;
				String c_name = text_cname1.getText().trim();
				Map<String, Object> map = new HashMap<>();
				map.put("C_NAME", c_name);
				if (c_name == null || "".equals(c_name)) {
					return;
				}

				if ("".equals(c_name)) {
					SwtUtil.showMessage(getParent(), "温馨提示", "请输入姓名");
				}
				try {
					List<Map<String, Object>> list = reserveDao.findReserveByCid2(map, 12, 1);
					Allpage1 = (list.size() + 1) % 12 == 0 ? (list.size() + 1) / 12 : (list.size() + 1) / 12 + 1;
					label_page.setText(NowPage1 + "/" + Allpage1);
					ShowCustomertable(table, list);

				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();

				}

			}
		});
		Button_find.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		Button_find.setText("查询");

		text_cname1 = new Text(group, SWT.BORDER);
		text_cname1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
			}
		});
		text_cname1.setBounds(75, 89, 111, 26);

		Label label_1 = new Label(group, SWT.RIGHT);
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_1.setBounds(10, 56, 59, 26);
		label_1.setText("姓名：");

		// 查找所有客户信息
		Button button = new Button(group, SWT.NONE);
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
				all = true;
				rid = false;
				name = false;
				NowPage1 = 1;
				try {
					List<Map<String, Object>> list = reserveDao.findAllCustomerRE(12, 1);

					ShowCustomertable(table, list);
					Allpage1 = (list.size() + 1) % 12 == 0 ? (list.size() + 1) / 12 : (list.size() + 1) / 12 + 1;

					label_page.setText(NowPage1 + "/" + Allpage1);
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();

				}

			}
		});
		button.setBounds(75, 372, 87, 35);
		button.setText("查询所有");

		combo_rid3 = new Combo(group, SWT.NONE);
		combo_rid3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
			}
		});
		combo_rid3.setBounds(75, 208, 111, 28);

		Label label_2 = new Label(group, SWT.RIGHT);
		label_2.setBounds(0, 182, 69, 20);
		label_2.setText("房间号：");

		// 根据已预定的房间号来查询
		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();// 播放鼠标点击音
				all = false;
				rid = true;
				name = false;
				NowPage1 = 1;
				String r_id = combo_rid3.getText().trim();
				Map<String, Object> map = new HashMap<>();
				map.put("R_ID", r_id);
				if (r_id == null || "".equals(r_id)) {
					return;
				}

				try {
					List<Map<String, Object>> list = reserveDao.findReserveByCid2(map, 12, 1);
					Allpage1 = (list.size() + 1) % 12 == 0 ? (list.size() + 1) / 12 : (list.size() + 1) / 12 + 1;
					label_page.setText(NowPage1 + "/" + Allpage1);
					ShowCustomertable(table, list);
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		button_1.setBounds(110, 271, 65, 26);
		button_1.setText("查询");

		Label label_top = new Label(composite_1, SWT.NONE);
		// 跳转页面到第一页
		label_top.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = 1;
				if (name == false && rid == false && all == true) {
					selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == false && rid == true && all == false) {
					selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == true && rid == false && all == false) {
					selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_top.setBounds(864, 346, 20, 20);
		label_top.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/First.png"), 20, 20));

		Label label_left = new Label(composite_1, SWT.NONE);
		// 跳转页面到上一页
		label_left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 - 1;
				if (NowPage1 < 1) {
					NowPage1 = 1;
				}
				if (name == false && rid == false && all == true) {

					selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));

				} else if (name == false && rid == true && all == false) {
					selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));

				} else if (name == true && rid == false && all == false) {
					selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_left.setBounds(890, 346, 20, 20);
		label_left.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Left.png"), 20, 20));

		Label label_right = new Label(composite_1, SWT.NONE);
		// 跳转页面到下一页
		label_right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 + 1;
				if (NowPage1 > Allpage1) {
					return;
				}

				if (name == false && rid == false && all == true) {
					selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == false && rid == true && all == false) {
					selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == true && rid == false && all == false) {
					selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_right.setBounds(977, 346, 20, 20);
		label_right.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Right.png"), 20, 20));

		Label label_last = new Label(composite_1, SWT.NONE);
		// 跳转页面到最后一页
		label_last.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = Allpage1;
				if (name == false && rid == false && all == true) {
					selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == false && rid == true && all == false) {
					selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == true && rid == false && all == false) {
					selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_last.setBounds(1003, 346, 20, 20);
		label_last.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Last.png"), 20, 20));

		label_page = new Label(composite_1, SWT.NONE);
		label_page.setAlignment(SWT.CENTER);
		label_page.setBounds(916, 346, 55, 20);

		Label label_5 = new Label(composite_1, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setBounds(1029, 346, 45, 20);
		label_5.setText("转到：");

		text_to_page = new Text(composite_1, SWT.BORDER);
		// 回车后转到指定页
		text_to_page.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					// 让按键原有的功能失效
					e.doit = false;
					// 执行你自己的事件
					String page = text_to_page.getText().trim();
					
					if (null == page || "".equals(page)) {
						SwtUtil.showMessage(getParent(), "提示", "页数不能为空");
						return;
					}
					for (int i = 0; i < page.length(); i++) {
						if (!Character.isDigit(page.charAt(i))) {
							SwtUtil.showMessage(getParent(), "提示", "请输入数字...");
							return;
						}
					}
					int page1 = Integer.parseInt(page);
					if (page1 > Allpage1 || page1 < 1) {
						SwtUtil.showMessage(getParent(), "提示", "请输入正确的数字");
						return;
					}
					
					NowPage1 = page1;
					if (name == false && rid == false && all == true) {
						selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
					} else if (name == false && rid == true && all == false) {
						selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
					} else if (name == true && rid == false && all == false) {
						selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
					}
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		text_to_page.setBounds(1080, 346, 45, 20);

		Label label_enter = new Label(composite_1, SWT.NONE);
		// 点击图片后跳转到指定页
		label_enter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String page = text_to_page.getText().trim();
				int page1 = Integer.parseInt(page);
				if (null == page || "".equals(page)) {
					SwtUtil.showMessage(getParent(), "提示", "页数不能为空");
					return;
				}
				for (int i = 0; i < page.length(); i++) {
					if (!Character.isDigit(page.charAt(i))) {
						SwtUtil.showMessage(getParent(), "提示", "请输入数字...");
						return;
					}
				}
				if (page1 > Allpage1 || page1 < 1) {
					SwtUtil.showMessage(getParent(), "提示", "请输入正确的数字");
					return;
				}

				NowPage1 = page1;
				if (name == false && rid == false && all == true) {
					selectAllMessage(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == false && rid == true && all == false) {
					selectByRid(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				} else if (name == true && rid == false && all == false) {
					selectByCname(12 * NowPage1, 1 + 12 * (NowPage1 - 1));
				}
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_enter.setBounds(1131, 346, 20, 20);
		label_enter.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Enter.png"), 20, 20));

		sashForm.setWeights(new int[] { 652 });
		cancelfindroomid();

	}

	/**
	 * 查询客户信息根据客户姓名
	 */
	protected void selectByCname(int top, int low) {
		//
		String c_name = text_cname1.getText().trim();
		Map<String, Object> map = new HashMap<>();
		map.put("C_NAME", c_name);
		if (c_name == null || "".equals(c_name)) {
			return;
		}

		if ("".equals(c_name)) {
			SwtUtil.showMessage(getParent(), "温馨提示", "请输入姓名");
		}
		try {
			List<Map<String, Object>> list = reserveDao.findReserveByCid2(map, top, low);
			ShowCustomertable(table, list);

		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();

		}

	}

	/**
	 * 查所有客户信息
	 */
	protected void selectAllMessage(int top, int low) {
		try {
			List<Map<String, Object>> list = reserveDao.findAllCustomerRE(top, low);

			ShowCustomertable(table, list);

		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();

		}

	}

	/**
	 * 通过已预定房间查询客户信息
	 */
	protected void selectByRid(int top, int low) {

		String r_id = combo_rid3.getText().trim();
		Map<String, Object> map = new HashMap<>();
		map.put("R_ID", r_id);
		if (r_id == null || "".equals(r_id)) {
			return;
		}

		try {
			List<Map<String, Object>> list = reserveDao.findReserveByCid2(map, top, low);
			ShowCustomertable(table, list);
		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
			e1.printStackTrace();
		}

	}

	// 将客户信息显示在表格中
	protected void ShowCustomertable(Table table, List<Map<String, Object>> list) {
		table.removeAll();

		TableItem item = null;

		for (Map<String, Object> map : list) {
			item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {
					StringUtil.objToString(map.get("RN")), StringUtil.objToString(map.get("C_ID")),
					StringUtil.objToString(map.get("C_NAME")), StringUtil.objToString(map.get("C_SEX")),
					StringUtil.objToString(map.get("C_ID_NUMBER")), StringUtil.objToString(map.get("C_TEL")),
					StringUtil.objToString(map.get("E_RES_COMDATE")), StringUtil.objToString(map.get("E_LEAVE_DATE")),
			});
		}
	}

	// 修改客户电话号码
	public void updatetel() {
		PlaySound.mouseClick();// 播放鼠标点击音
		String c_name = text_cname1.getText().trim();
		String c_tel = text_tel.getText().trim();

		if (null == c_name || "".equals(c_name)) {
			return;
		}
		if (null == c_tel || "".equals(c_tel)) {
			return;
		}

		List<Object> params = new ArrayList<>();
		params.add(c_tel);
		params.add(c_name);

		try {
			int result = customerDao.updateCustomer1(params);
			if (result > 0) {
				SwtUtil.showMessage(getParent(), "温馨提示", "取消预定成功");
			} else {
				SwtUtil.showMessage(getParent(), "错误提示", "取消预定失败");
			}
		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 根据房间状态查询房间号
	 */
	public void cancelfindroomid() {
		String r_id = "";// 房间号
		String r_state = "预定";// 状态

		String r_type = "";// 房间类型

		combo_rid3.removeAll();
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
			combo_rid3.setItems(str1);

		} catch (Exception e) {
			SwtUtil.showMessage(getParent(), "错误提示", e.getMessage());
			e.printStackTrace();
		}
	}
}
