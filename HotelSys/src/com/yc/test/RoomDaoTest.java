package com.yc.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yc.dao.RoomDao;

/**
 * 客房信息测试类
 * @author 沈俊羽
 *
 */
public class RoomDaoTest {

	private RoomDao roomDao=new RoomDao();
	
	@Test
	public void updateRoomStateTest() throws SQLException{
		List<Object> params=new ArrayList<Object>();
		params.add("预定");
		params.add("302");
		int i=roomDao.updateRoomState(params);
		System.out.println(i);
	}
	
	@Test
	public void findRoomTest() throws Exception{
		Map<String, Object> map1=new HashMap<String,Object>();
			map1.put("R_ID", null);
			map1.put("R_TYPE", null);
			map1.put("R_STATE", "空闲");
			List<Map<String, Object>> list=roomDao.findRoom(map1);
			System.out.println(list);
	}
}
