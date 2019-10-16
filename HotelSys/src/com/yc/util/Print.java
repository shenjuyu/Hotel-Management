package com.yc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

/**
 * 打印主体
 * @author 沈俊羽
 *
 */
public class Print implements Printable {
	private static String TITLE;// 标题
	private static String billId;// 单号
	private static String hotelName;// 门店
	private static String CASHIER;// 收银员
	private static String billTime;// 账单时间
	private static String shouldPay;// 结算时应收
	private static String shouldReturn;// 应当退还押金
	private static String allCost;// 总现金
	private static String customerName;// 客户名称
	private static String c_tel;// 客户电话
	private static String TEL;// 客服电话
	private static String ADDRESS;// 地址
	private static List<Integral> GOODSARRAY;

	/**
	 * 
	 * @param billid	账单号
	 * @param HotelName	酒店名称
	 * @param cashier	收银员
	 * @param BillTime	账单时间
	 * @param Shouldpay	本次应收
	 * @param ShouldReturn	退还押金
	 * @param Allcost	本次服务共收
	 * @param CustomerName	客户名称
	 * @param Ctel		客户联系方式
	 * @param tel		电话
	 * @param address	酒店地址
	 * @param goodsArray	清单
	 */
	private static void chushihua(String billid, String HotelName, String cashier,
			String BillTime,String Shouldpay,String ShouldReturn, String Allcost,
			String CustomerName, String Ctel, String tel,
			String address, List<Integral> goodsArray) {
		TITLE = "首席大酒店消费账单";
		billId = billid;
		hotelName = HotelName;
		CASHIER = cashier;
		billTime=BillTime;
		shouldPay=Shouldpay;
		shouldReturn = ShouldReturn;
		allCost = Allcost;
		customerName = CustomerName;
		c_tel = Ctel;
		TEL = tel;
		ADDRESS = address;
		GOODSARRAY = goodsArray;

	}

	/**
	 * 用于将商品零售进行进行打印
	 */
	public static void printSheet(String billid, String HotelName, String cashier,
			String BillTime,String Shouldpay,String ShouldReturn, String Allcost,
			String CustomerName, String Ctel, String tel,
			String address, List<Integral> goodsArray) {
		
		chushihua( billid,  HotelName,  cashier,BillTime, Shouldpay, ShouldReturn,
				Allcost,CustomerName,  Ctel,  tel, address,  goodsArray);
		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT); // LANDSCAPE表示横打;PORTRAIT表示竖打;REVERSE_LANDSCAPE表示打印空白
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		int length = printSize(GOODSARRAY) + 40;// 加值参数为115，增加行数需要增加高度
		p.setSize(165, length); // 纸张大小A4纸(595, 842),经测试58mm为165
		p.setImageableArea(5, 5, 155, length); // 设置打印区域，A4纸的默认X,Y边距是72
		// x - 用来设置此 paper 可成像区域左上角的 x 坐标
		// y - 用来设置此 paper 可成像区域左上角的 y 坐标
		// width - 用来设置此 paper 可成像区域宽度的值
		// height - 用来设置此 paper 可成像区域高度的值
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new Print(), pf);
		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		// 设置打印类
		job.setPageable(book);
		try {
			// // 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			// boolean a = job.printDialog();
			// if (a) {
			job.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public static Integer printSize(List<Integral> goodsArray) {
		int height = 240;// 加值参数为140，增加行数需要增加高度
		if (goodsArray.size() > 0) {
			height += goodsArray.size() * 10;
			for (int i = 0; i < goodsArray.size(); i++) {
				goodsArray.get(i).getRid();
				// String[] goods = goodsArray[i].split(",");
				if (goodsArray.get(i).getRid().length() > 8) {// 名称超11个字,换行
					height += 10;
				}
			}
		}
		return height;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		try {
			/**
			 * * @param Graphic指明打印的图形环境 PageFormat指明打印页格式（页面大小以点为计量单位，
			 * 1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595×842点） *
			 * 
			 * @param pageIndex指明页号
			 **/
			// 转换成Graphics2D
			Graphics2D g2d = (Graphics2D) graphics;
			// 设置打印颜色为黑色
			g2d.setColor(Color.black);
			// 打印起点坐标
			switch (pageIndex) {
			case 0:
				String xuxian = "------------------------------------";
				double x = pageFormat.getImageableX() + 10;
				double y = pageFormat.getImageableY() + 10;
				// 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
				Font fontTitle = new Font("新宋体", Font.BOLD, 10);
				g2d.setFont(fontTitle); // 设置字体
				// 打印标题
				g2d.drawString(TITLE, (float) x + 20, (float) y);
				y += fontTitle.getSize2D() + 4;

				Font fontContent = new Font("新宋体", Font.PLAIN, 7);
				g2d.setFont(fontContent); // 设置字体
				// 打印 订单号
				g2d.drawString("单号：" + billId, (float) x, (float) y);
				y += fontContent.getSize2D() + 4;
				g2d.drawString("门店：" + hotelName, (float) x, (float) y);
				y += fontContent.getSize2D() + 4;
				g2d.drawString("收银员：" + CASHIER, (float) x, (float) y);
				y += fontContent.getSize2D() + 4;
				g2d.drawString("时间：" +billTime , (float) x, (float) y);
				y += fontContent.getSize2D() + 4;

				g2d.drawString(xuxian, (float) x, (float) y);
				y += fontContent.getSize2D() + 2;

				y += fontContent.getSize2D() + 2;
				g2d.drawString("房间号：" + GOODSARRAY.get(0).getRid(), (float) x, (float) y);
				g2d.drawString("房间类型："+GOODSARRAY.get(0).getType().toString(), (float) x+ 65, (float) y);
				
				y += fontContent.getSize2D() + 2;
				g2d.drawString("入住天数：" + GOODSARRAY.get(0).getDays() + "天", (float) x , (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("房价："+GOODSARRAY.get(0).getPrice().toString()+"元/天", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("押金："+GOODSARRAY.get(0).getDeposit().toString()+"元", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("其他消费："+GOODSARRAY.get(0).getOther().toString()+"元", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;

				
				g2d.drawString(xuxian, (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("结算时应收：" +shouldPay+"元", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("应退还押金：" + shouldReturn+"元", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("本次服务共收：" + allCost+"元", (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("客户名称：" + customerName, (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("客户联系方式：" + c_tel, (float) x, (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString(xuxian, (float) x, (float) y);
				y += fontContent.getSize2D() + 4;
				String address = ADDRESS.length() > 16 ? ADDRESS.substring(0, 16) : ADDRESS;
				g2d.drawString("地址：" + address, (float) x, (float) y);
				if (ADDRESS.length() > 16) {
					y += fontContent.getSize2D() + 2;
					g2d.drawString(ADDRESS.substring(16), (float) x + 20, (float) y);
				}
				y += fontContent.getSize2D() + 2;
				g2d.drawString("客服电话：" +TEL, (float) x , (float) y);
				y += fontContent.getSize2D() + 2;
				g2d.drawString("谢谢惠顾期待您的再次光临", (float) x + 22, (float) y);
				y += fontContent.getSize2D() + 6;
				g2d.drawString("钱财请当面点清，一旦离柜概不负责", (float) x + 11, (float) y);
				y += fontContent.getSize2D() + 4;
				return PAGE_EXISTS;
			default:
				return NO_SUCH_PAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
