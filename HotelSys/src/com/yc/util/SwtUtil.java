package com.yc.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SwtUtil {

	/**
	 * 基本弹窗工具类
	 * @param shell
	 * @param text
	 * @param message
	 */
	public static void showMessage(Shell shell,String text,String message){
		MessageBox messageBox=new MessageBox(shell,SWT.NONE);
		messageBox.setText(text);;
		messageBox.setMessage(message);
		messageBox.open();
	}
}
