package com.yc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
/**
 * 关于我们
 * @author 沈俊羽
 *
 */
public class AboutUs extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AboutUs(Shell parent, int style) {
		super(parent, style);
		setText("关于我们");
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
		shell.setImage(SWTResourceManager.getImage(AboutUs.class, "/images/WindowIcon.png"));
		shell.setBackgroundImage(SWTResourceManager.getImage(AboutUs.class, "/images/WorkToghter.jpg"));
		shell.setSize(508, 358);
		shell.setText(getText());
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("黑体", 15, SWT.BOLD | SWT.ITALIC));
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		label.setBounds(24, 26, 119, 25);
		label.setText("关于我们");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setAlignment(SWT.RIGHT);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_1.setBounds(249, 180, 76, 20);
		label_1.setText("组长：");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_2.setBounds(331, 180, 76, 20);
		label_2.setText("沈俊羽");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_3.setBounds(193, 149, 90, 25);
		label_3.setText("本系统由：");
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_4.setAlignment(SWT.RIGHT);
		label_4.setBounds(249, 217, 76, 20);
		label_4.setText("组员：");
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_5.setBounds(331, 217, 76, 20);
		label_5.setText("李泽林");
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_6.setBounds(331, 243, 76, 20);
		label_6.setText("邹忠伟");
		
		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_7.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.BOLD));
		label_7.setBounds(366, 291, 126, 22);
		label_7.setText("共同完成！！！");
	}
}
