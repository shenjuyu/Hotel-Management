package com.yc.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.StaffDao;

/**
 * 员工测试类
 * @author 沈俊羽
 *
 */
public class StaffDaoTest {

	private StaffDao staffDao=new StaffDao();
	
	@Test
	public void updateStaffTest() throws SQLException{
		List<Object> params=new ArrayList<Object>();
		params.add("a");
		params.add("1000");
		int i=staffDao.updateStaff(params);
		System.out.println(i);
	}
	
	@Test
	public void findByIDTest() throws Exception{
		Map<String, Object> map=staffDao.findByID("1001");
		System.out.println(map);
	}
	
	@Test
	public void findStaffTest() throws Exception{
		String a="查看所有";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("S_ID", null);
		map.put("S_NAME", null);
		map.put("S_SEX", null);
		map.put("S_TEL", null);
		List<Map<String, Object>> list=staffDao.findStaff(a, map);
		System.out.println(list);
	}
	
	@Test
	public void addStaffTest() throws SQLException{
		List<Object> params=new ArrayList<Object>();
		params.add("a");
		params.add("艾雷斯泰");
		params.add("男");
		params.add("12375235236241");
		params.add("123467352345");
		params.add(null);
		int i=staffDao.addStaff(params);
		System.out.println(i);
	}
	
}
