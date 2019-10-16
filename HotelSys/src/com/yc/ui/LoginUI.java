package com.yc.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.dao.StaffDao;
import com.yc.util.AdminUtil;
import com.yc.util.ImageResize;
import com.yc.util.PlaySound;
import com.yc.util.StringUtil;
import com.yc.util.SwtUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;

/**
 * 登陆界面
 * @author 沈俊羽
 *
 */
public class LoginUI {

	private int EasterEggs=0;
	protected static Shell shell;
	public static String testcode = "";// 验证码，保存验证码，取这个值于用户输入的值作比较
	private Text text_pwd;//密码
	private Text text_verifyCode;//验证码输入文本框
	private Label tishi_verifyCode;//验证码提示框
	private StaffDao staffDao=new StaffDao();
	private Label tishi_name;
	private Label tishi_pwd;
	private Combo user_name;
	private Button button_remeber;
	private Preferences pre = Preferences.userNodeForPackage(this.getClass()).node("/com/yc/test");
	//数据库对象的声明
		/*java官方API（Preferences）
		这个类用起来比较方便，，但是有许多限制，，只能访问“HKEY_LOCAL_MACHINE\SOFTWARE\Javasoft”
		HKEY_CURRENT_USER\Software\JavaSoft\prefs下写入注册表值.
		*/

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginUI window = new LoginUI();
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
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		shell.setImage(SWTResourceManager.getImage(LoginUI.class, "/images/WindowIcon.png"));
		shell.setBackgroundImage(SWTResourceManager.getImage(LoginUI.class, "/images/BackGround.png"));
		shell.setSize(901, 638);
		shell.setText("首席大酒店管理系统");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);

		Label label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBounds(571, 314, 143, 55);

		Button button = new Button(shell, SWT.NONE);
		button.setForeground(SWTResourceManager.getColor(219, 112, 147));
		//登陆操作
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				String verofyCode=text_verifyCode.getText().trim();
				if (null==verofyCode||"".equals(verofyCode)) {
					tishi_verifyCode.setText("验证码不能为空...");
					return;
				} else if(!testcode.equals(verofyCode)){
					tishi_verifyCode.setText("验证码有误...");
					testcode="";
					image(label);
					return;
				}else{
					tishi_verifyCode.setText("");
				}
				String id=user_name.getText().trim();
				String pwd=text_pwd.getText().trim();
				try {
					Map<String, Object> map=staffDao.findByID(id);
					if (null==map||map.size()<=0) {
						SwtUtil.showMessage(shell, "错误提示", "用户名或密码错误...");
						pre.remove(id);
			        	try {
			    			String[] keys = pre.keys();//获得注册表中所有的键
			    			user_name.removeAll();
			    			user_name.setItems(keys);//将所有的键添加到下拉框中
			    		} catch (BackingStoreException e1) {
			    			System.out.println("错误信息:"+e1.getMessage());
			    		}
						return;
					}
					if (!pwd.equals(StringUtil.objToString(map.get("S_PWD")))) {
						SwtUtil.showMessage(shell, "错误提示", "用户名或密码错误...");
						pre.remove(id);
			        	try {
			    			String[] keys = pre.keys();//获得注册表中所有的键
			    			if (null!=keys && keys.length>0) {
								user_name.removeAll();
								user_name.setItems(keys);//将所有的键添加到下拉框中
							}
			    		} catch (BackingStoreException e1) {
			    			System.out.println("错误信息:"+e1.getMessage());
			    		}
						return;
					}
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put(id, pwd);
					if (null==map1||map1.size()<0) {
						SwtUtil.showMessage(shell, "错误提示", "登陆失败...");
					}else{
						AdminUtil.admin=map1;
						AdminUtil.adminid=id;
						AdminUtil.power=StringUtil.objToString(map.get("S_POWER"));
						/*将map1传给AdminUtil中用于在mainUI中判断是否已登陆，
						若未登录则无法打开MainUI
						*/
						if (button_remeber.getSelection()) {
					        pre.put(id, pwd);//保存密码到注册表
						} else {
							System.out.println("zxcv");
							//记住密码就将相应的值保存   不再记住之后删掉记录
							pre.remove(id);
						}
			        	try {
			    			String[] keys = pre.keys();//获得注册表中所有的键
			    			if (null!=keys && keys.length>0) {
								user_name.removeAll();
								user_name.setItems(keys);//将所有的键添加到下拉框中
							}
			    			
			    		} catch (BackingStoreException e1) {
			    			SwtUtil.showMessage(shell, "错误提示", e1.getMessage());
			    		}
			        	shell.close();
			        	shell.dispose();
						MainUI mainUI=new MainUI();
						mainUI.open();
					}
					
				} catch (Exception e1) {
					SwtUtil.showMessage(shell, "错误提示", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(584, 429, 98, 30);
		button.setText("登陆");
		
		
		Label label_user = new Label(shell, SWT.NONE);
		label_user.setBounds(543, 167, 27, 26);
		label_user.setImage(ImageResize.resize(SWTResourceManager.getImage(LoginUI.class, "/images/Customer.png"), 27, 30));
		
		
		Label label_pwd = new Label(shell, SWT.NONE);
		label_pwd.setImage(ImageResize.resize(SWTResourceManager.getImage(LoginUI.class, "/images/Pwd.png"),27,30));
		label_pwd.setBounds(543, 225, 27, 26);
		
		text_pwd = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_pwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//失去焦点后的判断
		text_pwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd=text_pwd.getText().trim();
				if(null==pwd||"".equals(pwd)){
					tishi_pwd.setText("密码不能为空");
				}else{
					tishi_pwd.setText("");
				}
			}
		});
		text_pwd.setBounds(571, 225, 143, 26);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(255, 255, 0));
		label_1.setFont(SWTResourceManager.getFont("黑体", 9, SWT.NORMAL));
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(494, 275, 76, 20);
		label_1.setText("验证码：");
		
		text_verifyCode = new Text(shell, SWT.BORDER);
		text_verifyCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//判断验证码是否正确
		text_verifyCode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String verofyCode=text_verifyCode.getText().trim();
				if (null==verofyCode||"".equals(verofyCode)) {
					tishi_verifyCode.setText("验证码不能为空...");
				} else if(!testcode.equals(verofyCode)){
					tishi_verifyCode.setText("验证码有误...");
					testcode="";
					image(label);
				}else{
					tishi_verifyCode.setText("");
				}
			}
		});
		text_verifyCode.setBounds(571, 272, 143, 26);
		
		Label label_2 = new Label(shell, SWT.NONE);
		//点击换验证码
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				testcode="";
				text_verifyCode.setText("");
				image(label);
			}
		});
		label_2.setForeground(SWTResourceManager.getColor(255, 255, 0));
		label_2.setFont(SWTResourceManager.getFont("华文新魏", 10, SWT.NORMAL));
		label_2.setBounds(720, 335, 76, 20);
		label_2.setText("换一张");
		
		tishi_verifyCode = new Label(shell, SWT.NONE);
		tishi_verifyCode.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_verifyCode.setBounds(720, 275, 122, 20);
		
		// imageSize(label);
		image(label);
		
		button_remeber = new Button(shell, SWT.CHECK);
		button_remeber.setForeground(SWTResourceManager.getColor(255, 255, 0));
		button_remeber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		button_remeber.setBounds(571, 390, 84, 20);
		button_remeber.setText("记住密码");
		
		user_name = new Combo(shell, SWT.NONE);//下拉框
		user_name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
			}
		});
		//失去焦点后的判断
		user_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name=user_name.getText().trim();
				if(null==name||"".equals(name)){
					tishi_name.setText("用户名不能为空");
				}else{
					tishi_name.setText("");
				}
			}
		});
		//在下拉框中的元素被选中之后将密码自动添加
		user_name.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlaySound.mouseClick();//播放鼠标点击音
				text_pwd.setText(pre.get(user_name.getText().trim(), ""));
				button_remeber.setSelection(true);
			}
		});
		user_name.setBounds(571, 167, 143, 28);
		
		tishi_name = new Label(shell, SWT.NONE);
		tishi_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_name.setBounds(720, 173, 122, 20);
		
		tishi_pwd = new Label(shell, SWT.NONE);
		tishi_pwd.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tishi_pwd.setBounds(720, 231, 122, 20);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				EasterEggs++;
				if (EasterEggs==10) {
					SwtUtil.showMessage(shell, "", "呀！被发现了，快跑!!!");
				}else if (EasterEggs==50) {
					SwtUtil.showMessage(shell, "", "点了这么久一定很辛苦吧\n后面没有了哦...");
				}else if (EasterEggs==100) {
					SwtUtil.showMessage(shell, "", "啊,被你发现了呢,后面真的没有了...");
				}else if (EasterEggs==200) {
					SwtUtil.showMessage(shell, "", "你还没走啊,我要走了哦...");
				}else if (EasterEggs==1000) {
					SwtUtil.showMessage(shell, "", "呀！你真是一个有耐心的宝贝呢...");
				}
			}
		});
		label_3.setImage(SWTResourceManager.getImage(LoginUI.class, "/images/LoginUse.png"));
		label_3.setBounds(10, 33, 441, 522);
		
		
		
		//将注册表中的所有的键全部放到下拉框中
		try {
			String[] keys = pre.keys();
			if (null==keys||keys.length>0) {
				user_name.setItems(keys);
			}
		} catch (BackingStoreException e1) {
			user_name.add("");
		}
		
		

	}

	/**
	 * 图片验证码
	 * 
	 * @param label
	 * @throws IOException
	 */
	public static void image(Label label){
		try {
			// BufferedImage 的构造函数可以设置图片的大小
			// 这里设置图片的大小
			BufferedImage image = new BufferedImage(label.getSize().x, label.getSize().y,
					BufferedImage.TYPE_INT_RGB);
			// 这里需要使用到java.awt.Graphics来绘制图片
			java.awt.Graphics graphics = image.getGraphics();
			Color color = new Color(245, 222, 179);
			graphics.setColor(color);// 为图片添加的底色
			graphics.fillRect(0, 0, label.getSize().x, label.getSize().y);
			char[] content = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			Random random = new Random();
			int index;
			for (int i = 0; i < 4; i++) {// 验证码长度
				index = random.nextInt(content.length);
				testcode += String.valueOf(content[index]);// testcode是验证码
				Font mfont = new Font("Jokerman", Font.BOLD | Font.ITALIC, 30);//图中文字的字体，大小
				graphics.setFont(mfont);
				// 图片中文字的颜色
				graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				// 图片中文字的位置
				graphics.drawString(content[index] + "", 10 + 30 * i, 40 + random.nextInt(4));
				// 1,验证码文字,2文字距离上边的距离3,距离下部分的距离,可以更改这后面的两个数据,来改变图片的,验证码显示位置

			}
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", stream);
			InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
			label.setImage(new Image(null, new ImageData(inputStream).scaledTo(label.getSize().x, label.getSize().y)));
		} catch (IOException e) {
			SwtUtil.showMessage(shell, "错误", e.getMessage());
			e.printStackTrace();
		}
	}

	// 图片自适应面板(窗口用)
	public void imageSize(Label label) {
		// 获取背景图片
		Image image = label.getImage();

		label.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				final Rectangle bounds = image.getBounds();

				int picwidth = bounds.width; // 图片宽

				int picheight = bounds.height; // 图片高

				double H = label.getSize().y; // label的高

				double W = label.getSize().x; // label的宽

				double ratio = 1; // 缩放比率

				double r1 = H / picheight;

				double r2 = W / picwidth;

				ratio = Math.min(r1, r2);

				e.gc.drawImage(image, 0, 0, picwidth, picheight, 0, 0,

						(int) (picwidth * ratio), (int) (picheight * ratio));
			}
		});
	}
}
