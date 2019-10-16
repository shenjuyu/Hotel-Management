package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 入住信息dao
 * @author 沈俊羽
 *
 */
public class EnterDao {

	private DbHepler db=new DbHepler();
	
	/**
	 * 查找所有入住信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findEnterAll() throws Exception{
		String sql="select er.e_id,er.c_id,er.r_id,er.join_date,er.e_day,er.return_date,er.cp,er.rs,cu.c_name,cu.c_id,cu.c_tel,cu.c_id_number,cu.c_sex from ht_enter er,ht_customer cu where er.c_id=cu.c_id";
		return db.selectMutil(sql, null);
	}
	
	/**
	 * 根据房间号查询房间信息，客户信息，入住表信息
	 * @param rid
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findMessageByRid(String rid) throws Exception{
		String sql="select ro.r_type,ro.r_deposit,ro.r_price,cu.c_name,cu.c_tel,"
				+ "en.join_date,en.e_day,en.return_date,en.cp from ht_enter en,ht_customer cu,"
				+ "ht_room ro where en.c_id=cu.c_id and en.r_id=ro.r_id and en.r_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(rid);
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据房间号查找入住信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findEnterByRid(List<Object> params) throws Exception{
		String sql="select e_id,c_id,r_id,join_date,e_day,return_date,cp,rs from ht_enter where r_id=?";
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据房间号查找入住信息
	 * 返回多条记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findEnterByRid1(List<Object> params) throws Exception{
		String sql="select e_id,c_id,r_id,join_date,e_day,return_date,cp,rs from ht_enter where r_id=?";
		return db.selectMutil(sql, params);
	}
	
	/**
	 * 根据客户编号查找入住信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findEnterByCid(List<Object> params) throws Exception{
		String sql="select e_id,c_id,r_id,join_date,e_day,return_date,cp,rs from ht_enter where c_id=?";
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据房间号删除入住信息
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int deleteEnter(List<Object> params) throws SQLException{
		String sql="delete ht_enter where r_id=?";
		return db.update(sql, params);
	}
	
	/**
	 * 添加住房信息(入住)
	 * 七个'？'分别是
	 * 客户编号、房间编号、入住日期、入住天数、退房日期、违约金、是否预定
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int addEnter(List<Object> params) throws SQLException{
		String sql="insert into ht_enter values(seq_e_id.nextval,?,?,to_date(?,'yyyy-MM-dd'),?,to_date(?,'yyyy-MM-dd'),?,?,null,null,null)";
		return db.update(sql, params);
	}
}
