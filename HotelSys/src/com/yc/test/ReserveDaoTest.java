package com.yc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.ReserveDao;

/**
 * 预定信息测试类
 * @author 沈俊羽
 *
 */
public class ReserveDaoTest {

	private ReserveDao reserveDao =new ReserveDao();
	
	@Test
	public void findAllReservetest() throws Exception {
		List<Map<String, Object>> list=reserveDao.findAllReserve();
		System.out.println(list);
	}
	
	@Test
	public void updateReserveRoomtest() throws Exception {
		List<List<Object>> params=new ArrayList<List<Object>>();
		List<Object> param1=new ArrayList<Object>();
		param1.add("空闲");
		param1.add("101");
		params.add(param1);
		List<Object> param2=new ArrayList<Object>();
		param2.add("1001");
		param2.add("109");
		param2.add("2019-05-05");
		param2.add("2019-05-10");
		param2.add("3");
		param2.add("2019-05-13");
		param2.add("1970-01-01");
		params.add(param2);
		int i=reserveDao.updateReserveRoom(params);
		for (int j = 0; j <2; j++) {
			System.out.println(params.get(j));
		}
		System.out.println(i);
	}
	
	@Test
	public void findReserveByCid1test() throws Exception {
		List<Object> params = new ArrayList<>();
		String a = "200";
		params.add(a);
		Map<String, Object> result = reserveDao.findReserveByCid1(params);
		System.out.println(result);
	}
	
	@Test
	public void findReserveRoomTest() throws Exception {
		List<Object> params = new ArrayList<>();
		params.add("小华");
		List<Map<String, Object>> list=reserveDao.findReserveRoom(params);
		System.out.println(list);
	}
	
	@Test
	public void findAllReserveCustomerTest() throws Exception {
		List<Map<String, Object>> list=reserveDao.findAllReserveCustomer();
		System.out.println(list);
	}
	
	@Test
	public void findReserveByRidTest() throws Exception {
		List<Object> params = new ArrayList<>();
		params.add("108");
		Map<String, Object> list=reserveDao.findReserveByRid(params);
		System.out.println(list);
	}
	
	@Test
	public void findReserveByCidTest() throws Exception {
		List<Object> params = new ArrayList<>();
		params.add("1004");
		Map<String, Object> list=reserveDao.findReserveByCid(params);
		System.out.println(list);
	}
	
	@Test
	public void deleteReserveTest() throws Exception {
		List<Object> params = new ArrayList<>();
		params.add("108");
		int list=reserveDao.deleteReserve(params);
		System.out.println(list);
	}
	
	@Test
	public void addReserveTest() throws Exception {
		List<Object> params = new ArrayList<>();
		params.add("1004");
		params.add("302");
		params.add("2019-4-23");
		params.add("2019-4-25");
		params.add("3");
		params.add("2019-4-28");
		params.add("");
		int list=reserveDao.addReserve(params);
		System.out.println(list);
	}
}
