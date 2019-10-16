package com.yc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印的工具类
 * @author 沈俊羽
 *
 */
public class Printer {

	/**
	 * 
	 * @param rid
	 *            房间号
	 * @param days
	 *            入住天数
	 * @param type
	 *            房间类型
	 * @param rprice
	 *            房价/天
	 * @param posite
	 *            押金
	 * @param otherCost
	 *            其他消费
	 * @param billid
	 *            账单编号
	 * @param cashier
	 *            收银员
	 * @param BillTime
	 *            账单时间
	 * @param Shouldpay
	 *            本次应付
	 * @param ShouldReturn
	 *            应退还押金
	 * @param Allcost
	 *            本次服务应收
	 * @param CustomerName
	 *            客户名称
	 * @param Ctel
	 *            客户联系方式
	 */
	public static void printBill(String rid, String days, String type, String rprice, String posite, String otherCost,
			String billid, String cashier, String BillTime, String Shouldpay, String ShouldReturn, String Allcost,
			String CustomerName, String Ctel) {
		List<Integral> list = new ArrayList<>();
		Integral integral = new Integral(rid, days, type, rprice, posite, otherCost);
		list.add(integral);
		Print.printSheet(billid, "首席大酒店", cashier, BillTime, Shouldpay, ShouldReturn, Allcost, CustomerName, Ctel,
				"1888888888", "湖南工学院", list);
	}

}
