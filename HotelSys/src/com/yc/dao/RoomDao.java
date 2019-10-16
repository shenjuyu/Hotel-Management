package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 房间信息Dao
 * @author 沈俊羽
 *
 */
public class RoomDao {

	private DbHepler db=new DbHepler();
	
	/**
	 * 根据房间号修改房间状态
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateRoomState(List<Object> params) throws SQLException{
		String sql="update ht_room set r_state=? where r_id=?";
		return db.update(sql, params);
	}
	
	/**
	 * 根据多条件查找房间信息
	 * @param map   键：查找的条件     值：查找的条件的值
	 * 例：
		//获取文本框的条件
		String name=text_name.getText().trim();
		String company=text_company.getText().trim();
		String type=text_type.getText().trim();
		String isbn=text_isbn.getText().trim();
		//将参数设置到map中
		Map<String, Object> map1=new HashMap<String,Object>();
		if (null!=name &&!"".equals(name)) {
			map1.put("B_NAME", name);
		}
		if (null!=company && !"".equals(company)) {
			map1.put("B_COMPANY", company);
		}
		if (null!=isbn&&!"".equals(isbn)) {
			map1.put("ISBN", isbn);
		}
		if (null!=type&&!"".equals(type)) {
			map1.put("T_NAME", type);
		}
		
		//调用查询方法
		try {
			List<Map<String, Object>> list=bookDao.findByTrem(map1);
			showTableBooks(table, list);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findRoom(Map<String, Object> map) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("select r_id,r_type,r_state,r_deposit,r_price from ht_room ");
		
		if (null==map||map.size()<=0) {
			sb.append(" order by r_id ");
			return db.selectMutil(sb.toString(), null);
		}
		
		List<Object> params=new ArrayList<Object>();
		sb.append(" where ");
		if (null!=map.get("R_ID")) {
			//sql语句拼接
			sb.append(" r_id=? ");
			params.add(map.get("R_ID"));
		}
		
		if (null!=map.get("R_ID") &&null!=map.get("R_TYPE")) {
			sb.append(" and r_type=? ");
			params.add(map.get("R_TYPE"));
		} else if(null!=map.get("R_TYPE")){
			sb.append(" r_type=? ");
			params.add(map.get("R_TYPE"));
		}
		
		if ((null!=map.get("R_ID") ||null!=map.get("R_TYPE"))&&null!=map.get("R_STATE")) {
			sb.append(" and r_state=? ");
			params.add(map.get("R_STATE"));
		} else if(null!=map.get("R_STATE")){
			sb.append(" r_state=? ");
			params.add(map.get("R_STATE"));
		}
		
		sb.append(" order by r_id ");
		
		return db.selectMutil(sb.toString(), params);
	}
	
	
	/**
	 * 根据多条件查找房间信息
	 * @param map   键：查找的条件     值：查找的条件的值
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findRoomComposite(Map<String, Object> map,int top,int low) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("select b.r_id,b.r_type,b.r_state,b.r_deposit,b.r_price,b.rn from (select a.r_id,a.r_type,a.r_state,a.r_deposit,a.r_price,rownum as rn from (select r_id,r_type,r_state,r_deposit,r_price from ht_room ");
		
		List<Object> params=new ArrayList<Object>();
		
		if (null==map||map.size()<=0) {
			params.add(top);
			params.add(low);
			sb.append(" order by r_id  ) a where rownum<=?) b where rn>=? ");
			return db.selectMutil(sb.toString(), params);
		}
		sb.append(" where ");
		
		if (null!=map.get("R_ID")) {
			//sql语句拼接
			sb.append(" r_id=? ");
			params.add(map.get("R_ID"));
		}
		
		if (null!=map.get("R_ID") &&null!=map.get("R_TYPE")) {
			sb.append(" and r_type=? ");
			params.add(map.get("R_TYPE"));
		} else if(null!=map.get("R_TYPE")){
			sb.append(" r_type=? ");
			params.add(map.get("R_TYPE"));
		}
		
		if ((null!=map.get("R_ID") ||null!=map.get("R_TYPE"))&&null!=map.get("R_STATE")) {
			sb.append(" and r_state=? ");
			params.add(map.get("R_STATE"));
		} else if(null!=map.get("R_STATE")){
			sb.append(" r_state=? ");
			params.add(map.get("R_STATE"));
		}
		sb.append(" order by r_id  ) a where rownum<=?) b where rn>=? ");
		params.add(top);
		params.add(low);
		
		return db.selectMutil(sb.toString(), params);
	}
	
}
