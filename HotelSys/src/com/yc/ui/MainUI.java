package com.yc.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.RoomDao;
import com.yc.util.AdminUtil;
import com.yc.util.ImageResize;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
/**
 * 主界面
 * @author 沈俊羽
 *
 */
public class MainUI {

	protected Shell shell;
	private RoomDao roomDao=new RoomDao();
	private Label label_time;//当前时间
	private Label label_rid;//房间号
	private Label label_style;//房间类型  经济房   高级房	总统套房
	private Label label_state;//房间状态   空闲   已入住   预定   维护
	private Label label_price;//房价/天
	private Label label_deposit;//押金
	protected Group group;
	protected Group group_2;
	protected Group group_1;
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainUI window = new MainUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//防止再关闭窗口时报错
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				System.exit(0);
			}
		});
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setImage(SWTResourceManager.getImage(MainUI.class, "/images/WindowIcon.png"));
		shell.setSize(1187, 788);
		shell.setText("首席大酒店管理系统");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		
		Label label_enter = new Label(shell, SWT.BORDER);
		//入住
		label_enter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				CheckInComposite checkInComposite=new CheckInComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				CustomerShouldKnow customerShouldKnow=new CustomerShouldKnow(shell,SWT.CLOSE );
				customerShouldKnow.open();
				checkInComposite.open();
			}
		});
		label_enter.setBounds(10, 10, 100, 100);
		label_enter.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/EnterRegister.png"), 100, 100));
		
		Label label_reserve = new Label(shell, SWT.BORDER);
		//预约
		label_reserve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				ReserveComposite reserveComposite=new ReserveComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				CustomerShouldKnow customerShouldKnow=new CustomerShouldKnow(shell,SWT.CLOSE );
				customerShouldKnow.open();
				reserveComposite.open();
				
			}
		});
		label_reserve.setBounds(116, 10, 100, 100);
		label_reserve.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/Reserve.png"), 100, 100));
		
		Label label_settlement = new Label(shell, SWT.BORDER);
		//退房(客户结算)
		label_settlement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				CheckOutComposite checkOutComposite=new CheckOutComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				checkOutComposite.open();
			}
		});
		label_settlement.setBounds(222, 10, 100, 100);
		label_settlement.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/Settlement.png"), 100, 100));
		
		Label label_customerManagement = new Label(shell, SWT.BORDER);
		//客户管理
		label_customerManagement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				CustomerComposite customerComposite=new CustomerComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				customerComposite.open();
			}
		});
		label_customerManagement.setBounds(328, 10, 100, 100);
		label_customerManagement.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/CustomerManagement.png"), 100, 100));
		
		Label label_roomManagement = new Label(shell, SWT.BORDER);
		//房间管理
		label_roomManagement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				RoomComposite roomComposite=new RoomComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				roomComposite.open();
			}
		});
		label_roomManagement.setBounds(434, 10, 100, 100);
		label_roomManagement.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/RoomManagement.png"), 100, 100));
		
		Label label_staffManagement = new Label(shell, SWT.BORDER);
		//员工管理
		label_staffManagement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				StaffComposite staffComposite=new StaffComposite(shell, SWT.MIN|SWT.MAX|SWT.CLOSE);
				staffComposite.open();
			}
		});
		label_staffManagement.setBounds(540, 10, 100, 100);
		label_staffManagement.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/StaffManagement.png"), 100, 100));
		
		Label label_chart = new Label(shell, SWT.BORDER);
		//生成账单统计图图表
		label_chart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				BillChartDialog billChartDialog=new BillChartDialog(shell,SWT.CLOSE );
				billChartDialog.open();
			}
		});
		label_chart.setBounds(646, 10, 100, 100);
		label_chart.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/Chart.png"), 100, 100));
		
		Label label_aboutUs = new Label(shell, SWT.BORDER);
		//关于我们
		label_aboutUs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				AboutUs aboutUs=new AboutUs(shell, SWT.CLOSE);
				aboutUs.open();
			}
		});
		label_aboutUs.setBounds(752, 10, 100, 100);
		label_aboutUs.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/AboutUs.png"), 100, 100));
		
		Label label_outSystem = new Label(shell, SWT.BORDER);
		//退出系统
		label_outSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				MessageBox box=new MessageBox(shell, SWT.YES|SWT.NO);
				box.setText("登出提示");
				box.setMessage("是否退出系统！！！");
				if (box.open()==SWT.YES) {
					System.exit(0);
				}
			}
		});
		label_outSystem.setBounds(858, 10, 100, 100);
		label_outSystem.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/outSystem.png"), 100, 100));
		
		label_time = new Label(shell, SWT.NONE);
		label_time.setFont(SWTResourceManager.getFont("Arvo", 16, SWT.BOLD));
		label_time.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		label_time.setBounds(992, 5, 167, 99);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label.setBounds(0, 116, 1169, 20);
		
		group = new Group(shell, SWT.NONE);
		group.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		group.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
		group.setText("经济房");
		group.setBounds(178, 142, 981, 204);
		group.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		
		group_1 = new Group(shell, SWT.NONE);
		group_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		group_1.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
		group_1.setText("高级房");
		group_1.setBounds(178, 352, 981, 204);
		
		
		group_2 = new Group(shell, SWT.NONE);
		group_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		group_2.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
		group_2.setText("总统套房");
		group_2.setBounds(178, 560, 981, 176);
		
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(0, 136, 164, 210);
		label_1.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
				(MainUI.class, "/images/LoginUse.png"), 172, 244));
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(10, 438, 76, 20);
		label_2.setText(" 房 间 号 ：");
		
		label_rid = new Label(shell, SWT.NONE);
		label_rid.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_rid.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_rid.setBounds(92, 438, 76, 20);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(10, 532, 76, 20);
		label_4.setText("房间状态：");
		
		label_state = new Label(shell, SWT.NONE);
		label_state.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_state.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_state.setBounds(92, 532, 76, 20);
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(10, 578, 76, 20);
		label_6.setText("房间单价：");
		
		label_price = new Label(shell, SWT.NONE);
		label_price.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_price.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_price.setBounds(92, 578, 76, 20);
		
		Label label_8 = new Label(shell, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_8.setAlignment(SWT.RIGHT);
		label_8.setBounds(10, 627, 76, 20);
		label_8.setText("房间押金：");
		
		label_deposit = new Label(shell, SWT.NONE);
		label_deposit.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_deposit.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_deposit.setBounds(92, 627, 76, 20);
		
		Label label_10 = new Label(shell, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_10.setAlignment(SWT.RIGHT);
		label_10.setBounds(10, 485, 76, 20);
		label_10.setText("房间类型：");
		
		label_style = new Label(shell, SWT.NONE);
		label_style.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label_style.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		label_style.setBounds(92, 485, 76, 20);
		
		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_3.setBounds(170, 142, 2, 594);
		
		Label label_5 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setBounds(0, 352, 172, 2);
		
		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.BOLD));
		label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_7.setBounds(37, 380, 88, 30);
		label_7.setText("房间信息");
		
		
		Label label_9 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_9.setBounds(968, 0, 8, 116);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				group.dispose();
				group_1.dispose();
				group_2.dispose();
				
				group = new Group(shell, SWT.NONE);
				group.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseUp(MouseEvent e) {
						PlaySound.mouseClick();//播放鼠标点击音
					}
				});
				group.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
				group.setText("经济房");
				group.setBounds(178, 142, 871, 204);
				group.setBackgroundMode(SWT.INHERIT_DEFAULT);
				
				
				group_1 = new Group(shell, SWT.NONE);
				group_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						PlaySound.mouseClick();//播放鼠标点击音
					}
				});
				group_1.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
				group_1.setText("高级房");
				group_1.setBounds(178, 352, 871, 204);
				
				
				group_2 = new Group(shell, SWT.NONE);
				group_2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						PlaySound.mouseClick();//播放鼠标点击音
					}
				});
				group_2.setFont(SWTResourceManager.getFont("幼圆", 11, SWT.BOLD));
				group_2.setText("总统套房");
				group_2.setBounds(178, 560, 871, 176);
				
				
				showRoom(group, "经济房");
				showRoom(group_1, "高级房");
				showRoom(group_2, "总统套房");
			}
		});
		button.setBounds(37, 675, 98, 30);
		button.setText("刷新");
		
		
		
		//doCheckLogin();
		showRoom(group, "经济房");
		showRoom(group_1, "高级房");
		showRoom(group_2, "总统套房");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					shell.getDisplay().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							Date date=new Date();
							DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String day=format.format(date).substring(0, 10);
							String time=format.format(date).substring(10,19);
							label_time.setText("\n"+day+"\n  "+time);
						}
					});
				}
				
			}
		}).start();
	}
	
	/**
	 * 在主界面显示所有的房间   房间状态用图片显示
	 * @param composite  label 所在的面板
	 * @param r_type  房间类型
	 */
	public void showRoom(Group group,String r_type){
		int a=10;//固定的起始位置
		int b=20;//固定的起始位置
		Point p=group.getSize();
		//获得面板大小
		int gruwidth=p.x;//面板的宽度
		
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("R_ID", null);
		map1.put("R_TYPE", r_type);
		map1.put("R_STATE", null);
	
		try {
			List<Map<String, Object>> list=roomDao.findRoomComposite(map1, 1000, 1);
			if (null==list||list.size()<=0) {
				SwtUtil.showMessage(shell, "错误提示", "无房间信息...");
				return;
			}
			
			for (Map<String, Object> map : list) {
				String roomId=StringUtil.objToString(map.get("R_ID"));
				String roomState=StringUtil.objToString(map.get("R_STATE"));
				Label label_image = new Label(group, SWT.NONE);
				label_image.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseUp(MouseEvent e) {
						PlaySound.mouseClick();//播放鼠标点击音
						Map<String, Object> map1=new HashMap<String, Object>();
						map1.put("R_ID", roomId);
						map1.put("R_TYPE", null);
						map1.put("R_STATE", null);
						
							try {
								//点击后添加房间信息到某处
								List<Map<String, Object>> list=roomDao.findRoomComposite(map1, 1000, 1);
								if (null==list||list.size()<=0) {
									SwtUtil.showMessage(shell, "错误提示", "无房间信息...");
									return;
								}
								
								for (Map<String, Object> map2 : list) {
									label_rid.setText(roomId);
									label_style.setText(StringUtil.objToString(map2.get("R_TYPE")));
									label_state.setText(roomState);
									label_price.setText(StringUtil.objToString(map2.get("R_PRICE")));
									label_deposit.setText(StringUtil.objToString(map2.get("R_DEPOSIT")));
								}
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				});
				label_image.setBounds(a, b, 83, 80);
				if ("空闲".equals(roomState)) {
					label_image.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
							(MainUI.class, "/images/Free.png"), 83, 80));
				} else if("预定".equals(roomState)){
					label_image.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
							(MainUI.class, "/images/Scheduled.png"), 83, 80));
				}else if("已入住".equals(roomState)){
					label_image.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
							(MainUI.class, "/images/AlreadyIn.png"), 83, 80));
				}else if("维护".equals(roomState)){
					label_image.setBackgroundImage(ImageResize.resize(SWTResourceManager.getImage
							(MainUI.class, "/images/Maintain.png"), 83, 80));
				}
				
				
				
				label_image.setText("\n\n\n       "+roomId);
				Point label_imageSize=label_image.getSize();
				int labelWidth=label_imageSize.x;
				int labelHeigth=label_imageSize.y;
				a=a+labelWidth+27;
				if (gruwidth-a<labelWidth) {
					a=10;
					b=b+labelHeigth+20;
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	//查看用户是否登录
	public void doCheckLogin(){
		if(null==AdminUtil.admin){
			LoginUI ui=new LoginUI();
			shell.dispose();
			ui.open();
		}
	}
}
