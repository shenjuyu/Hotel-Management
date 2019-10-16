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
 * 用户须知
 * @author DELL
 *
 */
public class CustomerShouldKnow extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CustomerShouldKnow(Shell parent, int style) {
		super(parent, style);
		setText("首席大酒店--用户须知");
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setImage(SWTResourceManager.getImage(CustomerShouldKnow.class, "/images/WindowIcon.png"));
		shell.setSize(519, 482);
		shell.setText(getText());
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width-shell.getSize().x)/2,
				(dimension.height-shell.getSize().y)/2);
		
		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label.setFont(SWTResourceManager.getFont("黑体", 13, SWT.BOLD));
		label.setBounds(191, 25, 92, 22);
		label.setText("客户须知");
		
		Label label_1 = new Label(shell, SWT.WRAP);
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label_1.setBounds(70, 62, 364, 327);
		label_1.setText("1. 预定成功后如果不能按时入住，超时一天加收20元\r\n\n2. 我方会尽最大的努力保护您的信息安全\r\n  （不包括但不限于警方查询，法律规定等我方必须提供您的个人信息时）\r\n\n3. 我方会尽最大的努力维护您的合法权益，将您的入住体验达到极致\r\n\n4.本酒店一人仅可同时预约(入住)一次\r\n\n5. 本条款的最终解释权归首席大酒店所有");
	}

}
