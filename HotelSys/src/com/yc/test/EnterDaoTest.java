package com.yc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.EnterDao;

/**
 * 入住信息测试类
 * @author 沈俊羽
 *
 */
public class EnterDaoTest {

	private EnterDao enterDao=new EnterDao();
	
	@Test
	public void findEnterByRidTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("104");
		Map<String, Object> map=enterDao.findEnterByRid(params);
		System.out.println(map);
	}

	@Test
	public void findEnterByCidTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("1003");
		Map<String, Object> map=enterDao.findEnterByCid(params);
		System.out.println(map);
	}
	
	@Test
	public void deleteEnterTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("104");
		int map=enterDao.deleteEnter(params);
		System.out.println(map);
	}
	
	@Test
	public void addEnterTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("1006");
		params.add("108");
		params.add("2019-4-28");
		params.add("3");
		params.add("2019-5-1");
		params.add("0");
		params.add("0");
		int map=enterDao.addEnter(params);
		System.out.println(map);
	}
}
