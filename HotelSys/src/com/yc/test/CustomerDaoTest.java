package com.yc.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.CustomerDao;

/**
 * 客户测试类
 * @author 沈俊羽
 *
 */
public class CustomerDaoTest {

	private CustomerDao customerDao=new CustomerDao();
	
	@Test
	public void findTest() throws Exception{
		boolean i=customerDao.find("13245691025");
		System.out.println(i);
	}
	
	@Test
	public void updateCustomerTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("12345678901");
		params.add("1006");
		int i=customerDao.updateCustomer(params);
		System.out.println(i);
	}
	
	@Test
	public void addCustomerTest() throws Exception{
		List<Object> params=new ArrayList<Object>();
		params.add("秩序册");
		params.add("15648153054");
		params.add("37132561591512021");
		params.add("男");
		int i=customerDao.addCustomer(params);
		System.out.println(i);
	}
	
	@Test
	public void findCustomerTest() throws Exception{
		String a="查找所有";
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("C_ID", null);
		map1.put("C_NAME", null);
		map1.put("C_SEX", null);
		map1.put("C_TEL", null);
		List<Map<String, Object>> list=customerDao.findCustomer(a, map1);
		System.out.println(list);
	}
}
