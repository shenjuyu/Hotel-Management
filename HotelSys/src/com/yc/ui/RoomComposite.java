package com.yc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.RoomDao;
import com.yc.util.ImageResize;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
/**
 * 客房管理界面
 * @author 沈俊羽
 *
 */
public class RoomComposite extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_rid;//房间号 --查询
	private Text text_type;//房间类型--查询
	private Table table;//显示房间信息的表格
	private Combo combo;//房间状态--查询
	private Text text_rid1;//房间号--修改
	private Button button_free;//空闲
	private Button button_enter;//已入住
	private Button button_reserve;//预定
	private Button button_maintain;//维护
	private RoomDao roomDao=new RoomDao();
	
	private Text text_to_page;
	private Label label_page;
	private int NowPage1 = 1;
	private int Allpage1 = 0;

	

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public RoomComposite(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--客房管理");
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
		shell.setImage(SWTResourceManager.getImage(RoomComposite.class, "/images/WindowIcon.png"));
		shell.setSize(783, 644);
		shell.setText("首席大酒店--客房管理");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		//选择房间信息后，可修改房间状态
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				TableItem item = (TableItem) e.item;
				text_rid1.setText(item.getText(0).trim());
				String state=item.getText(2).trim();
				//选择房间信息后修改单选按钮
				if ("预定".equals(state)) {
					button_reserve.setSelection(true);//√
					button_enter.setSelection(false);
					button_free.setSelection(false);
					button_maintain.setSelection(false);
				} else if ("已入住".equals(state)) {
					button_reserve.setSelection(false);
					button_enter.setSelection(true);//√
					button_free.setSelection(false);
					button_maintain.setSelection(false);
				}else if ("空闲".equals(state)) {
					button_reserve.setSelection(false);
					button_enter.setSelection(false);
					button_free.setSelection(true);//√
					button_maintain.setSelection(false);
				}else if ("维护".equals(state)) {
					button_reserve.setSelection(false);
					button_enter.setSelection(false);
					button_free.setSelection(false);
					button_maintain.setSelection(true);//√
				}
			}
		});
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		table.setBounds(10, 56, 536, 512);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("房间号");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("房间类型");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(109);
		tableColumn_2.setText("房间状态");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(115);
		tableColumn_3.setText("房间押金");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(108);
		tableColumn_4.setText("房间价格");
		
		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_6.setFont(SWTResourceManager.getFont("黑体", 12, SWT.BOLD));
		label_6.setBounds(125, 25, 273, 25);
		label_6.setText("首席大酒店房间基本信息显示");
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(22, 49, 76, 20);
		label.setText(" 房 间 号 ：");
		
		text_rid = new Text(composite_1, SWT.BORDER);
		text_rid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_rid.setBounds(104, 49, 109, 26);
		
		Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(22, 103, 76, 20);
		label_1.setText("房间类型：");
		
		text_type = new Text(composite_1, SWT.BORDER);
		text_type.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_type.setBounds(104, 100, 109, 26);
		
		Label label_2 = new Label(composite_1, SWT.NONE);
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(22, 157, 76, 20);
		label_2.setText("房间状态：");
		
		Button button = new Button(composite_1, SWT.NONE);
		//房间信息查询
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String rid=text_rid.getText().trim();
				String type=text_type.getText().trim();
				String state=combo.getText().trim();
				
				NowPage1=1;
				
				Map<String, Object> map=new HashMap<String,Object>();
				//将参数设置到map中
				if (null!=rid &&!"".equals(rid)) {
					map.put("R_ID", rid);
				}
				if (null!=type && !"".equals(type)) {
					map.put("R_TYPE", type);
				}
				if (null!=state&&!"".equals(state)) {
					map.put("R_STATE", state);
				}
				
				
				try {
					List<Map<String, Object>> list=roomDao.findRoomComposite(map, 100, 1);
					Allpage1=(list.size()+1)%20==0?(list.size()+1)/20:(list.size()+1)/20+1;
					label_page.setText(NowPage1 + "/" + Allpage1);
					showRoomInTable(table,list);
					
				} catch (Exception e1) {
					SwtUtil.showMessage(getParent(), "警告", e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		button.setBounds(60, 205, 98, 30);
		button.setText("查询");
		
		Label label_3 = new Label(composite_1, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(0, 278, 221, 8);
		
		Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(22, 335, 76, 20);
		label_4.setText(" 房 间 号 ：");
		
		text_rid1 = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		text_rid1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_rid1.setBounds(104, 332, 109, 26);
		
		Label label_5 = new Label(composite_1, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setBounds(22, 377, 76, 20);
		label_5.setText("房间状态：");
		
		button_enter = new Button(composite_1, SWT.RADIO);
		button_enter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_enter.setBounds(89, 403, 69, 20);
		button_enter.setText("已入住");
		
		button_reserve = new Button(composite_1, SWT.RADIO);
		button_reserve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_reserve.setBounds(89, 440, 69, 20);
		button_reserve.setText("预定");
		
		button_maintain = new Button(composite_1, SWT.RADIO);
		button_maintain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_maintain.setBounds(89, 477, 69, 20);
		button_maintain.setText("维护");
		
		button_free = new Button(composite_1, SWT.RADIO);
		button_free.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_free.setBounds(89, 511, 69, 20);
		button_free.setText("空闲");
		
		Button button_5 = new Button(composite_1, SWT.NONE);
		//修改房间状态
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String state="";
				if (button_enter.getSelection()) {
					state="已入住";
				} else if (button_free.getSelection()) {
					state="空闲";
				}else if (button_maintain.getSelection()) {
					state="维护";
				}else if (button_reserve.getSelection()) {
					state="预定";
				}else{
					SwtUtil.showMessage(getParent(), "提示", "请选择修改后的状态...");
					return;
				}
				String rid=text_rid1.getText().trim();
				if (null==rid||"".equals(rid)) {
					SwtUtil.showMessage(getParent(), "提示", "请选择要修改的房间信息...");
					return;
				}
				
				List<Object> params=new ArrayList<Object>();
				params.add(state);
				params.add(rid);
				try {
					MessageBox messageBox=new MessageBox(getParent(),SWT.YES|SWT.NO);
					messageBox.setText("提示");
					messageBox.setMessage("是否将"+rid+"号房间状态修改为"+state);
					if (messageBox.open()==SWT.YES) {
						int result=roomDao.updateRoomState(params);
						if (result>0) {
							SwtUtil.showMessage(getParent(), "温馨提示", "修改成功");
						}else{
							SwtUtil.showMessage(getParent(), "提示", "修改失败...");
						}
					}
					
				} catch (SQLException e1) {
					SwtUtil.showMessage(getParent(), "警告", e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		button_5.setBounds(60, 547, 98, 30);
		button_5.setText("修改");
		
		Label label_7 = new Label(composite_1, SWT.NONE);
		label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_7.setFont(SWTResourceManager.getFont("黑体", 11, SWT.BOLD));
		label_7.setBounds(60, 292, 114, 20);
		label_7.setText("房间状态修改");
		
		Label label_8 = new Label(composite_1, SWT.NONE);
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_8.setFont(SWTResourceManager.getFont("黑体", 11, SWT.BOLD));
		label_8.setBounds(60, 17, 114, 26);
		label_8.setText("房间信息查询");
		
		combo = new Combo(composite_1, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo.setItems(new String[] {"","空闲", "已入住", "预定", "维护"});
		combo.setBounds(104, 154, 109, 28);
		
		
		Label label_top = new Label(composite, SWT.NONE);
		// 跳转页面到第一页
		label_top.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = 1;
				selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_top.setBounds(256, 574, 20, 20);
		label_top.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/First.png"), 20, 20));

		Label label_left = new Label(composite, SWT.NONE);
		// 跳转页面到上一页
		label_left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 - 1;
				if (NowPage1 < 1) {
					NowPage1 = 1;
				}
				selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_left.setBounds(282, 574, 20, 20);
		label_left.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Left.png"), 20, 20));

		Label label_right = new Label(composite, SWT.NONE);
		// 跳转页面到下一页
		label_right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 + 1;
				if (NowPage1 > Allpage1) {
					return;
				}

				selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_right.setBounds(369, 574, 20, 20);
		label_right.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Right.png"), 20, 20));

		Label label_last = new Label(composite, SWT.NONE);
		// 跳转页面到最后一页
		label_last.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = Allpage1;
				selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_last.setBounds(395, 574, 20, 20);
		label_last.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Last.png"), 20, 20));

		label_page = new Label(composite, SWT.NONE);
		label_page.setAlignment(SWT.CENTER);
		label_page.setBounds(308, 574, 55, 20);

		Label label_51 = new Label(composite, SWT.NONE);
		label_51.setAlignment(SWT.RIGHT);
		label_51.setBounds(421, 574, 45, 20);
		label_51.setText("转到:");

		text_to_page = new Text(composite, SWT.BORDER);
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
					selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
					label_page.setText(NowPage1 + "/" + Allpage1);
				}
			}
		});
		text_to_page.setBounds(472, 574, 45, 20);

		Label label_enter = new Label(composite, SWT.NONE);
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
				selectRoom(20 * NowPage1, 1 + 20 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_enter.setBounds(523, 574, 20, 20);
		label_enter.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Enter.png"), 20, 20));
		
		
		sashForm.setWeights(new int[] {222, 555});
		
		
	}

	/**
	 * 多条件查询客房信息
	 */
	public void selectRoom(int top,int low){
		String rid=text_rid.getText().trim();
		String type=text_type.getText().trim();
		String state=combo.getText().trim();
		Map<String, Object> map=new HashMap<String,Object>();
		//将参数设置到map中
		if (null!=rid &&!"".equals(rid)) {
			map.put("R_ID", rid);
		}
		if (null!=type && !"".equals(type)) {
			map.put("R_TYPE", type);
		}
		if (null!=state&&!"".equals(state)) {
			map.put("R_STATE", state);
		}
		
		
		try {
			List<Map<String, Object>> list=roomDao.findRoomComposite(map,top,low);
			showRoomInTable(table,list);
			
		} catch (Exception e1) {
			SwtUtil.showMessage(getParent(), "警告", e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	/**
	 * 将房间信息显示到表格中
	 * @param table2
	 * @param list
	 */
	protected void showRoomInTable(Table table2, List<Map<String, Object>> list) {
		table.removeAll();
		if (null==list||list.size()<=0) {
			SwtUtil.showMessage(getParent(), "温馨提示", "无房间信息...");
			return;
		}
		
		TableItem item=null;
		for (Map<String, Object> map : list) {
			item=new TableItem(table, SWT.NONE);
			item.setText(new String[]{
					StringUtil.objToString(map.get("R_ID")),
					StringUtil.objToString(map.get("R_TYPE")),
					StringUtil.objToString(map.get("R_STATE")),
					StringUtil.objToString(map.get("R_DEPOSIT")),
					StringUtil.objToString(map.get("R_PRICE"))
			});
		}
		
	}
}
