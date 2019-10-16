package com.yc.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 图片缩放
 * @author 沈俊羽
 *
 */
public class ImageResize {

	/**
     * 根据指定的宽高对{@link Image}图像进行绽放
     * @param src 原图对象
     * @param width 目标图像宽度
     * @param height 目标图像高度
     * @return 返回缩放后的{@link Image}对象
     */
    public static Image resize(Image src, int width, int height) {
        Image scaled = new Image(Display.getDefault(), width, height);
        GC gc = new GC(scaled);
        try{
            gc.setAdvanced(true); // 打开高级绘图模式
            gc.setAntialias(SWT.ON);// 设置消除锯齿
            gc.setInterpolation(SWT.HIGH); // 设置插值
            gc.drawImage(src, 0, 0, src.getBounds().width, src.getBounds().height,0, 0, width, height);
        }finally{
            gc.dispose();
        }
        return scaled;
    }
}
