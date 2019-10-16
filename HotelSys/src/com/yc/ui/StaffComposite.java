package com.yc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.StaffDao;
import com.yc.util.AdminUtil;
import com.yc.util.FileUtil;
import com.yc.util.ImageResize;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import org.eclipse.swt.widgets.TabFolder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
/**
 * 员工管理界面
 * @author 沈俊羽
 *
 */
public class StaffComposite extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_name;//员工姓名
	private Text text_pwd;//密码
	private Text text_truepwd;//确认密码
	private Label tishi_truepwd;//确认密码提示
	private Text text_id_number;//身份证号
	private Text text_tel;//手机号
	private Label tishi_name;//姓名提示
	private Label tishi_pwd;//密码提示
	private Label tishi_id_number;//身份证提示
	private Label tishi_tel;//手机号提示
	private Button button_woman;//单选按钮   女
	private Button button_man;//单选按钮   男
	private StaffDao staffDao=new StaffDao();
	private Text text_pwd1;//修改密码界面         密码
	private Text text_newPwd;//修改密码界面    新密码
	private Text text_newPwd1;//修改密码界面  确认密码
	private Label tishi_pwd1;//修改密码界面         密码提示
	private Label tishi_newPwd;//修改密码界面    新密码提示
	private Label tishi_newPwd1;//修改密码界面  确认密码提示
	private Text text_request;
	private Table table;
	private Text text_sid1;//修改密码界面   员工编号
	private Label tishi_sid;
	private Combo combo;//用户权限
	private Text text_sid;
	private Combo combo_request;
	
	private Text text_to_page;
	private Label label_page;
	int NowPage1 = 1;
	int Allpage1 = 0;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StaffComposite(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--员工管理--"+AdminUtil.power);
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
		shell.setImage(SWTResourceManager.getImage(StaffComposite.class, "/images/WindowIcon.png"));
		shell.setSize(733, 700);
		shell.setText(getText());
		shell.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
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
		tabFolder.setFont(SWTResourceManager.getFont("华文新魏", 12, SWT.NORMAL));
		tabFolder.setBounds(10, 10, 707, 645);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("员工注册");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		tabItem.setControl(composite);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label.setAlignment(SWT.RIGHT);
		label.setBounds(10, 57, 89, 20);
		label.setText("员工姓名：");
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(10, 121, 89, 20);
		label_1.setText("员工密码：");
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_2.setAlignment(SWT.RIGHT);
		label_2.setBounds(10, 237, 89, 20);
		label_2.setText("身份证号：");
		
		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_3.setAlignment(SWT.RIGHT);
		label_3.setBounds(23, 295, 76, 20);
		label_3.setText("手机号：");
		
		text_name = new Text(composite, SWT.BORDER);
		text_name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//员工姓名输入检测
		text_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name=text_name.getText().trim();
				if (null==name||"".equals(name)) {
					tishi_name.setText("姓名不能为空...");
				}else{
					tishi_name.setText("");
				}
			}
		});
		text_name.setBounds(105, 54, 176, 26);
		
		text_pwd = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_pwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//员工密码输入判断
		text_pwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd=text_pwd.getText().trim();
				if (null==pwd||"".equals(pwd)) {
					tishi_pwd.setText("密码不能为空...");
				}else{
					tishi_pwd.setText("");
				}
			}
		});
		text_pwd.setBounds(105, 118, 176, 26);
		
		text_id_number = new Text(composite, SWT.BORDER);
		text_id_number.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//身份证号的验证
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
		text_id_number.setBounds(105, 234, 176, 26);
		
		text_tel = new Text(composite, SWT.BORDER);
		text_tel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//手机号格式验证
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
		text_tel.setBounds(105, 292, 176, 26);
		
		Label label_image = new Label(composite, SWT.NONE);
		label_image.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		label_image.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		label_image.setAlignment(SWT.CENTER);
		label_image.setText("\n\n\n\n请选择图片...");
		label_image.setBounds(466, 54, 139, 190);
		
		Label label_4 = new Label(composite, SWT.NONE);
		//图片选择
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
					PlaySound.mouseClick();//播放鼠标点击音
					FileDialog fd = new FileDialog(getParent(), SWT.NONE);
					fd.setFilterPath("SystemRoot");
					fd.setFilterExtensions(new String[] { "*.*", "*.jpg", "*.png", "*.gif" });
					String select = fd.open();
					if (null == select || "".equals(select)) {
						return;
					}
					FileUtil.filePath = select;
					File file = new File(select);
					InputStream in = new FileInputStream(file);
					ImageData data = new ImageData(in);
					Image image = new Image(getParent().getDisplay(), data);
					label_image.setImage(ImageResize.resize(image, 139, 190));
					label.redraw();
					in.close();
				} catch (FileNotFoundException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		label_4.setFont(SWTResourceManager.getFont("幼圆", 9, SWT.BOLD));
		label_4.setForeground(SWTResourceManager.getColor(0, 0, 0));
		label_4.setAlignment(SWT.CENTER);
		label_4.setBounds(466, 260, 139, 20);
		label_4.setText("双击选择图片");
		
		Button button = new Button(composite, SWT.NONE);
		//员工注册
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				
				if (!"Boss".equals(AdminUtil.power)) {
					SwtUtil.showMessage(getParent(), "提示", "权限不足");
					return;
				}
				
				//得到文本框内容
				String sex="";
				if (button_man.getSelection()) {
					sex="男";
				}else if(button_woman.getSelection()){
					sex="女";
				}else if(null==sex||"".equals(sex)){
					SwtUtil.showMessage(getParent(), "错误提示", "性别不能为空...");
					return;
				}
				String pwd=text_pwd.getText().trim();
				String name=text_name.getText().trim();
				String id_number=text_id_number.getText().trim();
				String tel=text_tel.getText().trim();
				String truePwd=text_truepwd.getText().trim();
				String power=combo.getText().trim();
				
				
				if (null==name||"".equals(name)) {//姓名文本框格式的判断
					SwtUtil.showMessage(getParent(), "错误提示", "姓名不能为空...");
					return;
				}
				
				if (null==pwd||"".equals(pwd)) {//密码文本框格式的判断
					SwtUtil.showMessage(getParent(), "错误提示", "密码不能为空...");
					return;
				}
				
				if (null==truePwd||"".equals(truePwd)) {//确认密码文本框格式的判断
					SwtUtil.showMessage(getParent(), "错误提示", "确认密码不能为空...");
					return;
				} else if(!pwd.equals(truePwd)){
					SwtUtil.showMessage(getParent(), "错误提示", "两次输入密码不一致...");
					return;
				}
				
				String ID_RG="^\\d{6}(18|19|20){1}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
				if (null==id_number||"".equals(id_number)) {//身份证号文本框格式的判断
					SwtUtil.showMessage(getParent(), "错误提示", "身份证号不能为空...");
					return;
				} else if(!id_number.matches(ID_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "身份证格式不正确...");
					return;
				}
				
				
				String tel_RG="^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
				if (null==tel||"".equals(tel)) {//手机号文本框格式的判断
					SwtUtil.showMessage(getParent(), "错误提示", "手机号不能为空...");
					return;
				} else if(!tel.matches(tel_RG)){
					SwtUtil.showMessage(getParent(), "错误提示", "手机号格式不正确...");
					return;
				}
				
				if (null==power||"".equals(power)) {
					SwtUtil.showMessage(getParent(), "错误提示", "权限不能为空...");
					return;
				}
				
				if ("".equals(FileUtil.filePath)) {//照片的格式的判断
					SwtUtil.showMessage(getParent(), "温馨提示", "未选择照片");
					return;
				}
				
				InputStream in=null;
				try {
					File file=new File(FileUtil.filePath);//数据库中存放图片使用字节数组存放
					in=new FileInputStream(file);
					byte [] bt=new byte[(int)file.length()];
					in.read(bt);//得到照片的字节数组
					
					//登陆密码、员工姓名、性别、身份证号、手机号、照片
					List<Object> params=new ArrayList<Object>();
					params.add(pwd);
					params.add(name);
					params.add(sex);
					params.add(id_number);
					params.add(tel);
					params.add(bt);
					params.add(power);
					
					int result=staffDao.addStaff(params);//执行添加操作
					
					if (result>0) {
						SwtUtil.showMessage(getParent(), "温馨提示", "注册成功!!!");
						text_name.setText("");
						text_pwd.setText("");
						text_truepwd.setText("");
						text_id_number.setText("");
						text_tel.setText("");
						label_image.setImage(null);
						label_image.setText("\n\n\n\n请选择图片...");
						button_man.setSelection(false);
						button_woman.setSelection(false);
						FileUtil.filePath="";//将文件路径重置
						combo.select(-1);
					}else{
						SwtUtil.showMessage(getParent(), "错误提示", "注册失败...");
					}
				} catch (FileNotFoundException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				} catch (SQLException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}finally{
					try {
						in.close();
					} catch (IOException e1) {
						SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		button.setFont(SWTResourceManager.getFont("华文新魏", 12, SWT.BOLD));
		button.setBounds(206, 462, 98, 30);
		button.setText("注册");
		
		tishi_name = new Label(composite, SWT.NONE);
		tishi_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_name.setBounds(287, 57, 163, 20);
		
		tishi_pwd = new Label(composite, SWT.NONE);
		tishi_pwd.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_pwd.setBounds(287, 121, 163, 20);
		
		tishi_id_number = new Label(composite, SWT.NONE);
		tishi_id_number.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_id_number.setBounds(287, 237, 163, 20);
		
		tishi_tel = new Label(composite, SWT.NONE);
		tishi_tel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_tel.setBounds(287, 295, 163, 20);
		
		Label label_5 = new Label(composite, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_5.setBounds(10, 347, 89, 20);
		label_5.setText("员工性别：");
		
		button_man = new Button(composite, SWT.RADIO);
		button_man.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_man.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		button_man.setBounds(133, 347, 39, 20);
		button_man.setText("男");
		
		button_woman = new Button(composite, SWT.RADIO);
		button_woman.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_woman.setBounds(208, 347, 39, 20);
		button_woman.setText("女");
		
		Button button_3 = new Button(composite, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				shell.dispose();
			}
		});
		button_3.setFont(SWTResourceManager.getFont("华文新魏", 12, SWT.BOLD));
		button_3.setBounds(388, 462, 112, 30);
		button_3.setText("返回主界面");
		
		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_6.setAlignment(SWT.RIGHT);
		label_6.setBounds(10, 179, 89, 20);
		label_6.setText("确认密码：");
		
		text_truepwd = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_truepwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//确认密码提示
		text_truepwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd=text_pwd.getText().trim();
				String truePwd=text_truepwd.getText().trim();
				if (null==truePwd||"".equals(truePwd)) {
					tishi_truepwd.setText("确认密码不能为空...");
				} else if(!pwd.equals(truePwd)){
					tishi_truepwd.setText("两次输入密码不一致...");
				}else{
					tishi_truepwd.setText("");
				}
			}
		});
		text_truepwd.setBounds(105, 173, 176, 26);
		
		tishi_truepwd = new Label(composite, SWT.NONE);
		tishi_truepwd.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_truepwd.setBounds(287, 179, 163, 20);
		
		Label label_11 = new Label(composite, SWT.NONE);
		label_11.setAlignment(SWT.RIGHT);
		label_11.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_11.setBounds(23, 393, 76, 20);
		label_11.setText("权限：");
		
		combo = new Combo(composite, SWT.READ_ONLY);
		combo.setItems(new String[] {"Boss", "Staff"});
		combo.setBounds(105, 387, 176, 28);
		
		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("修改密码");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		tabItem_1.setControl(composite_1);
		composite_1.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Label label_7 = new Label(composite_1, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_7.setAlignment(SWT.RIGHT);
		label_7.setBounds(147, 140, 104, 20);
		label_7.setText("员工密码：");
		
		text_pwd1 = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		text_pwd1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//验证密码是否正确
		text_pwd1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd=text_pwd1.getText().trim();
				if (null==pwd||"".equals(pwd)) {
					tishi_pwd1.setText("旧密码不能为空...");
				}else{
					tishi_pwd1.setText("");
				}
			}
		});
		text_pwd1.setBounds(257, 134, 135, 26);
		
		Label label_8 = new Label(composite_1, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_8.setAlignment(SWT.RIGHT);
		label_8.setBounds(147, 189, 104, 20);
		label_8.setText("新密码：");
		
		text_newPwd = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		text_newPwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//判断新密码是否正确
		text_newPwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String newPwd=text_newPwd.getText().trim();
				if (null==newPwd||"".equals(newPwd)) {
					tishi_newPwd.setText("新密码不能为空...");
				}else{
					tishi_newPwd.setText("");
				}
			}
		});
		text_newPwd.setBounds(256, 183, 136, 26);
		
		Label label_9 = new Label(composite_1, SWT.NONE);
		label_9.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_9.setAlignment(SWT.RIGHT);
		label_9.setBounds(147, 241, 104, 20);
		label_9.setText("确认密码：");
		
		text_newPwd1 = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		text_newPwd1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//判断确认密码是否正确
		text_newPwd1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String newPwd1=text_newPwd1.getText().trim();
				String newPwd=text_newPwd.getText().trim();
				if (null==newPwd1||"".equals(newPwd1)) {
					tishi_newPwd1.setText("确认密码不能为空...");
				}else if(!newPwd.equals(newPwd1)){
					tishi_newPwd1.setText("两次密码不统一...");
				}else{
					tishi_newPwd1.setText("");
				}
			}
		});
		
		text_newPwd1.setBounds(256, 235, 136, 26);
		
		tishi_pwd1 = new Label(composite_1, SWT.NONE);
		tishi_pwd1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_pwd1.setBounds(398, 137, 142, 20);
		
		tishi_newPwd = new Label(composite_1, SWT.NONE);
		tishi_newPwd.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_newPwd.setBounds(398, 186, 142, 20);
		
		tishi_newPwd1 = new Label(composite_1, SWT.NONE);
		tishi_newPwd1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_newPwd1.setBounds(399, 241, 142, 20);
		
		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//修改密码
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sid=text_sid1.getText().trim();
				String pwd=text_pwd1.getText().trim();
				String newPwd=text_newPwd.getText().trim();
				String newPwd1=text_newPwd1.getText().trim();
				
				
				if (null==sid||"".equals(sid)) {
					SwtUtil.showMessage(getParent(), "错误提示", "员工编号不能为空...");
					return;
				}
				if (null==pwd||"".equals(pwd)) {
					SwtUtil.showMessage(getParent(), "错误提示", "密码不能为空...");
					return;
				}
				if (null==newPwd||"".equals(newPwd)) {
					SwtUtil.showMessage(getParent(), "错误提示", "新密码不能为空...");
					return;
				}
				if (!newPwd.equals(newPwd1)) {//验证两次密码是否相同
					SwtUtil.showMessage(getParent(), "错误提示", "两次密码不同请重新输入...");
					text_pwd1.setText("");
					text_newPwd.setText("");
					text_newPwd1.setText("");
					return;
				}
				
				try {
					//验证是否为本人操作
					Map<String, Object> map=staffDao.findByID(sid);
					if (null==map||map.size()<=0) {
						SwtUtil.showMessage(getParent(), "错误提示", "无该员工信息!!!");
						return;
					}
					if (!map.get("S_PWD").equals(pwd)) {
						SwtUtil.showMessage(getParent(), "错误提示", "密码错误请重新输入...");
						text_pwd1.setText("");
						text_newPwd.setText("");
						text_newPwd1.setText("");
						return;
					}
					
					//修改密码
					List<Object> params=new ArrayList<Object>();
					params.add(newPwd1);
					params.add(sid);
					int result=staffDao.updateStaff(params);
					if (result>0) {
						SwtUtil.showMessage(getParent(), "温馨提示", "修改成功！");
						text_sid1.setText("");
						text_pwd1.setText("");
						text_newPwd.setText("");
						text_newPwd1.setText("");
					}else{
						SwtUtil.showMessage(getParent(), "错误提示", "修改失败...");
						text_sid1.setText("");
						text_pwd1.setText("");
						text_newPwd.setText("");
						text_newPwd1.setText("");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(261, 319, 98, 30);
		button_1.setText("修改密码");
		
		Label label_10 = new Label(composite_1, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_10.setAlignment(SWT.RIGHT);
		label_10.setBounds(147, 89, 104, 20);
		label_10.setText("员工编号：");
		
		text_sid1 = new Text(composite_1, SWT.BORDER);
		text_sid1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//判断员工编号是否正确
		text_sid1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String sid=text_sid1.getText().trim();
				if (null==sid||"".equals(sid)) {
					tishi_sid.setText("员工编号不能为空...");
				}else{
					tishi_sid.setText("");
				}
			}
		});
		text_sid1.setBounds(257, 83, 136, 26);
		
		tishi_sid = new Label(composite_1, SWT.NONE);
		tishi_sid.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_sid.setBounds(398, 86, 143, 20);
		
		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("查询信息");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		tabItem_2.setControl(composite_2);
		composite_2.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		combo_request = new Combo(composite_2, SWT.NONE);
		combo_request.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_request.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		combo_request.setItems(new String[] {"查找所有", "员工编号", "员工姓名", "性别", "手机号"});
		combo_request.setBounds(142, 46, 92, 28);
		combo_request.select(0);
		
		text_request = new Text(composite_2, SWT.BORDER);
		text_request.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		text_request.setBounds(240, 46, 163, 26);
		
		Button button_boss = new Button(composite_2, SWT.NONE);
		//Boss查询员工信息操作
		button_boss.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				
				NowPage1=1;
				
				String attribute = combo_request.getText().trim();
				String tends = text_request.getText().trim();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("S_ID", tends);
				map.put("S_NAME", tends);
				map.put("S_SEX", tends);
				map.put("S_TEL", tends);
				try {
					List<Map<String, Object>> list = staffDao.findStaffComposite(attribute, map, 4, 1);
					Allpage1=(list.size()+1)%4==0?(list.size()+1)/4:(list.size()+1)/4+1;
					label_page.setText(NowPage1 + "/" + Allpage1);
					showTableBooks(table, list); //显示到表格中
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		button_boss.setBounds(409, 46, 98, 30);
		button_boss.setText("查询");
		
		table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				TableItem item = (TableItem) e.item;
				text_sid.setText(item.getText(0).trim());
			}
		});
		table.setBounds(10, 94, 679, 345);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
		tableColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn.setWidth(117);
		tableColumn.setText("员工编号");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.LEFT);
		tableColumn_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn_1.setWidth(78);
		tableColumn_1.setText("员工姓名");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn_2.setWidth(56);
		tableColumn_2.setText("性别");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.LEFT);
		tableColumn_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn_4.setWidth(187);
		tableColumn_4.setText("身份证号");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.LEFT);
		tableColumn_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn_3.setWidth(146);
		tableColumn_3.setText("手机号");
		
		TableColumn tableColumn_5 = new TableColumn(table, SWT.LEFT);
		tableColumn_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		tableColumn_5.setWidth(84);
		tableColumn_5.setText("照片");
		
		Button button_staff = new Button(composite_2, SWT.NONE);
		button_staff.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String attribute ="员工编号";
				String tends = AdminUtil.adminid;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("S_ID", tends);
				map.put("S_NAME", tends);
				map.put("S_SEX", tends);
				map.put("S_TEL", tends);
				try {
					List<Map<String, Object>> list = staffDao.findStaff(attribute, map);
					showTableBooks(table, list); //显示到表格中
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		button_staff.setBounds(278, 44, 98, 30);
		button_staff.setText("查询");
		button_staff.setVisible(false);
		
		Label label_12 = new Label(composite_2, SWT.NONE);
		label_12.setAlignment(SWT.RIGHT);
		label_12.setBounds(113, 502, 106, 20);
		label_12.setText("员工编号：");
		label_12.setVisible(false);
		
		text_sid = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY);
		text_sid.setBounds(225, 499, 147, 26);
		text_sid.setVisible(false);
		
		Button button_2 = new Button(composite_2, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String sid=text_sid.getText().trim();
				if (null==sid||"".equals(sid)) {
					SwtUtil.showMessage(getParent(), "提示", "员工编号不能为空");
					return;
				}
				
				try {
					MessageBox messageBox=new MessageBox(getParent(), SWT.YES|SWT.NO);
					messageBox.setText("提示");
					messageBox.setMessage("是否解雇编号为"+sid+"的员工");
					if (messageBox.open()==SWT.YES) {
						int result=staffDao.deleteStaffById(sid);
						if (result>0) {
							SwtUtil.showMessage(getParent(), "提示", "员工编号为"+sid+"的员工已解雇");
						}else{
							SwtUtil.showMessage(getParent(), "提示", "员工编号为"+sid+"的员工解雇失败");
						}
					}
				} catch (SQLException e1) {
					SwtUtil.showMessage(getParent(), "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		button_2.setBounds(378, 497, 98, 30);
		button_2.setText("解雇");
		button_2.setVisible(false);
		
		Label label_top = new Label(composite_2, SWT.NONE);
		// 跳转页面到第一页
		label_top.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = 1;
				BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_top.setBounds(402, 445, 20, 20);
		label_top.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/First.png"), 20, 20));

		Label label_left = new Label(composite_2, SWT.NONE);
		// 跳转页面到上一页
		label_left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 - 1;
				if (NowPage1 < 1) {
					NowPage1 = 1;
				}
				BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_left.setBounds(428, 445, 20, 20);
		label_left.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Left.png"), 20, 20));

		Label label_right = new Label(composite_2, SWT.NONE);
		// 跳转页面到下一页
		label_right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = NowPage1 + 1;
				if (NowPage1 > Allpage1) {
					return;
				}

				BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_right.setBounds(515, 445, 20, 20);
		label_right.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Right.png"), 20, 20));

		Label label_last = new Label(composite_2, SWT.NONE);
		// 跳转页面到最后一页
		label_last.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				NowPage1 = Allpage1;
				BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_last.setBounds(541, 445, 20, 20);
		label_last.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Last.png"), 20, 20));

		label_page = new Label(composite_2, SWT.NONE);
		label_page.setAlignment(SWT.CENTER);
		label_page.setBounds(454, 445, 55, 20);

		Label label_51 = new Label(composite_2, SWT.NONE);
		label_51.setAlignment(SWT.RIGHT);
		label_51.setBounds(567, 445, 45, 20);
		label_51.setText("转到:");

		text_to_page = new Text(composite_2, SWT.BORDER);
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
					BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
					label_page.setText(NowPage1 + "/" + Allpage1);
				}
			}
		});
		text_to_page.setBounds(618, 445, 45, 20);

		Label label_enter = new Label(composite_2, SWT.NONE);
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
				BossSelectStaff(4 * NowPage1, 1 + 4 * (NowPage1 - 1));
				label_page.setText(NowPage1 + "/" + Allpage1);
			}
		});
		label_enter.setBounds(669, 445, 20, 20);
		label_enter.setBackgroundImage(
				ImageResize.resize(SWTResourceManager.getImage(CustomerComposite.class, "/images/Enter.png"), 20, 20));
		
		
		
		
		//Boss权限设置，员工权限设置
		if ("Staff".equals(AdminUtil.power)) {
			button_staff.setVisible(true);
			button_boss.setVisible(false);
			combo_request.setVisible(false);
			text_request.setVisible(false);
			label_12.setVisible(false);
			text_sid.setVisible(false);
			button_2.setVisible(false);
		}else if ("Boss".equals(AdminUtil.power)) {
			button_boss.setVisible(true);
			button_staff.setVisible(false);
			combo_request.setVisible(true);
			text_request.setVisible(true);
			label_12.setVisible(true);
			text_sid.setVisible(true);
			button_2.setVisible(true);
		}

	}
	
	/**
	 * Boss权限拥有者查询员工信息
	 * @param top
	 * @param low
	 */
	public void BossSelectStaff(int top,int low){
		String attribute = combo_request.getText().trim();
		String tends = text_request.getText().trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("S_ID", tends);
		map.put("S_NAME", tends);
		map.put("S_SEX", tends);
		map.put("S_TEL", tends);
		try {
			List<Map<String, Object>> list = staffDao.findStaffComposite(attribute, map, top, low);
			showTableBooks(table, list); //显示到表格中
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
	/**
	 * 显示到表格中
	 * @param table2
	 * @param list
	 */
	public void showTableBooks(Table table2, List<Map<String, Object>> list) {
		table2.removeAll();
		if (null==list||list.size()<=0) {
			SwtUtil.showMessage(getParent(), "错误提示", "员工信息查询无果...");
			return;
		}
		
		TableItem item=null;
		for (Map<String, Object> map : list) {
			item=new TableItem(table2, getStyle());
			item.setText(new String[]{
					StringUtil.objToString(map.get("S_ID")),
					StringUtil.objToString(map.get("S_NAME")),
					StringUtil.objToString(map.get("S_SEX")),
					StringUtil.objToString(map.get("S_ID_NUMBER")),
					StringUtil.objToString(map.get("S_TEL"))
			});
			byte [] bt=(byte[]) map.get("S_IMAGE");
			if (null==bt||bt.length<=0) {
				continue;
			}
			Image image=null;
			ByteArrayInputStream stream=new ByteArrayInputStream(bt);
			ImageData data=new ImageData(stream);
			image=new Image(getParent().getDisplay(), data);
			item.setImage(5, ImageResize.resize(image,50, 70));
		}
		
	}
}
