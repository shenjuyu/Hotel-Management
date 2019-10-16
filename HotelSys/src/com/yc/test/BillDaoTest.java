package com.yc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.BillDao;

/**
 * 账单测试类
 * @author 沈俊羽
 *
 */
public class BillDaoTest {

	private BillDao billDao=new BillDao();
	
	@Test
	public void updateBillEnterRoomTest() throws Exception{
		List<List<Object>> params=new ArrayList<List<Object>>();
		List<Object> param=new ArrayList<Object>();
		param.add("101");
		params.add(param);
		List<Object> param1=new ArrayList<Object>();
		param1.add("空闲");
		param1.add("101");
		params.add(param1);
		List<Object> param2=new ArrayList<Object>();
		param2.add("2017201904301008");
		param2.add("101");
		param2.add("0");
		param2.add("300");
		param2.add("350");
		params.add(param2);
		int i=billDao.updateBillEnterRoom(params);
		for (int j = 0; j <3; j++) {
			System.out.println(params.get(j));
		}
		System.out.println(i);
	}
	
	@Test
	public void findBillTest() throws Exception{
		List<Map<String, Object>> list=billDao.findBill();
		System.out.println(list);
	}
	
	@Test
	public void addBillTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("2017201904280001");
		params.add("307");
		params.add("100");
		params.add("600");
		params.add("700");
		int i=billDao.addBill(params);
		System.out.println(i);
	}
}
