package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 预定信息Dao
 * @author 沈俊羽
 *
 */
public class ReserveDao {

	private DbHepler db=new DbHepler();
	
	/**
	 * 查询某页最顶行到最低行的数据
	 * @param top
	 * @param low
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAllCustomerRE(Integer top,Integer low) throws Exception{
		String sql="select b.rn,b.c_id,b.c_name,b.c_sex,b.c_id_number,b.c_tel,"
				+ "b.e_res_comdate,b.e_leave_date from (select a.c_id,a.c_name,"
				+ "a.c_sex,a.c_id_number,a.c_tel,a.e_res_comdate,a.e_leave_date,"
				+ "rownum as rn from (select hc.c_id,hc.c_name,hc.c_sex,"
				+ "hc.c_id_number,hc.c_tel,hr.e_res_comdate,hr.e_leave_date "
				+ "from ht_customer hc,ht_reserve hr where hc.c_id=hr.c_id order by hr.c_id) a "
				+ "where rownum<=?) b where rn>=?";
		List<Object> params=new ArrayList<Object>();
		params.add(top);
		params.add(low);
		return db.selectMutil(sql, params);
	}
	
	/**
	 * 查询所有已预定的客户的手机号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAllReserve() throws Exception{
		String sql="select cu.c_tel from ht_reserve re,ht_customer cu where re.c_id=cu.c_id ";
		return db.selectMutil(sql, null);
	}
	
	/**
	 * 同时执行多条sql语句
	 *  修改房间状态   
	 *  添加账单信息
	 *   七个'?'分别是
	 * 客户编号、房间编号、何时预定的日期、预定的入住日期、入住天数、退房日期、实际的入住日期/取消预定日期 （此字段有两个功用）
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateReserveRoom(List<List<Object>> params) throws SQLException{
		//修改房间状态
		String sql="update ht_room set r_state=? where r_id=?";
		//添加预定信息
		String sql1="insert into ht_reserve values(seq_reserve_id.nextval,?,?,"
				+ "to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,"
				+ "to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),null,null,null)";
		
		List<String> sqls=new ArrayList<String>();
		sqls.add(sql);
		sqls.add(sql1);
		return db.update(sqls, params);
	}
	
	/**
	 * 根据房间号来查询 
	 * 客户：名字 ，身份证，手机号 。
	 * 房间：房间状态，房价 ，押金。
	 * 时间：当时预定时间，预订入住时间。
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findReserveByCid1(List<Object> params) throws Exception{		
			String sql="select cu.c_name,cu.c_id_number,cu.c_tel,ro.r_type,ro.r_price,ro.r_deposit,"
					+ "re.e_res_comdate,re.e_reservedate from ht_reserve re,ht_room ro,"
					+ "ht_customer cu where re.c_id=cu.c_id and re.r_id=ro.r_id and  ro.r_id= ? ";
			return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据姓名来查询 
	 * 客户：客户编号 ，身份证，手机号 。//姓名改为编号
	 * 房间：房间状态，房价 ，押金。
	 * 时间：当时预定时间，预订入住时间。
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findReserveByCid2(List<Object> params) throws Exception{		
			String sql="select cu.c_id,cu.c_id_number,cu.c_tel,ro.r_type,ro.r_price,ro.r_deposit,re.e_res_comdate,re.e_reservedate from ht_reserve re,ht_room ro,ht_customer cu where  re.r_id=ro.r_id and   cu.c_name= ? ";
			return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据姓名，房间号来查询 
	 * 客户：客户编号 ，身份证，手机号 ,性别。//姓名改为编号
	 * 房间：房间状态，房价 ，押金。
	 * 时间：当时预定时间，预订入住时间，预定退房时间。
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findReserveByCid2(Map<String, Object> map,Integer top,Integer low) throws Exception{	
			StringBuffer sql=new StringBuffer();
			 sql.append("select b.rn ,b.r_id, b.c_id,b.c_name,b.c_sex,b.c_id_number,b.c_tel,b.e_res_comdate,b.e_leave_date "
			 		+ "from (select a.c_id,a.c_name,a.c_sex,a.c_id_number,a.c_tel,a.e_res_comdate,a.e_leave_date,a.r_id,"
			 		+ "rownum as rn from (select hc.c_id,hc.c_name,hc.c_sex,hc.c_id_number,hc.c_tel,hr.e_res_comdate,"
			 		+ "hr.e_leave_date ,hr.r_id from ht_customer hc,ht_reserve hr where hc.c_id=hr.c_id order by hr.c_id )"
			 		+ " a where rownum<=?) b where rn>=? and  ");
			 List<Object> params=new ArrayList<Object>();
			params.add(top);
			params.add(low);
			 
			 if (null!=map.get("R_ID")) {
					//sql语句拼接
					sql.append(" b.r_id= ? ");
					params.add(map.get("R_ID"));
				}
			if (null!=map.get("C_NAME")) {
				//sql语句拼接
				sql.append(" b.c_name= ?");
				params.add(map.get("C_NAME"));
			}
			System.out.println(sql.toString());
			return db.selectMutil(sql.toString(), params);
		
			 
	}
	
	/**
	 * 根据客户姓名得到客户编号，根据客户编号得到房间号，根据房间号得到房间信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findReserveRoom(List<Object> params) throws Exception{
		String sql="select ro.r_id,ro.r_type,ro.r_state,ro.r_deposit,ro.r_price from ht_reserve re,"
			+ "ht_customer cu,ht_room ro where re.c_id=cu.c_id and re.r_id=ro.r_id and cu.c_name=?";
		return db.selectMutil(sql, params);
	}
	
	/**
	 * 查询所有的预定了的客户信息
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> findAllReserveCustomer() throws Exception{
		String sql="select hc.c_name from ht_customer hc,ht_reserve hr where hc.c_id=hr.c_id";
		return db.selectMutil(sql, null);
	}
	
	/**
	 * 根据房间号来查询 姓名
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>findnamebyrid(List<Object>params) throws Exception{	
			String sql="select cu.c_name from ht_room ro,ht_customer cu,ht_reserve re where re.c_id = cu.c_id and re.r_id=ro.r_id and ro.r_id= ?";
			return db.selectMutil(sql, params);
	}
	
	/**
	 * 根据房间号查找预定信息
	 * 查询单条记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findReserveByRid(List<Object> params) throws Exception{
		String sql="select e_id,c_id,r_id,e_reservedate,e_res_comdate,e_days,e_leave_date"
				+ ",e_true_leave from ht_reserve where r_id=?";
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据房间号查找预定信息
	 * 查询多条记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findReserveByRid1(List<Object> params) throws Exception{
		String sql="select e_id ,c_id ,r_id,e_reservedate,e_res_comdate,e_days,e_leave_date"
				+ " from ht_reserve where r_id=?";
		return db.selectMutil(sql, params);
	}
	
	/**
	 * 根据客户编号查找预定信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findReserveByCid(List<Object> params) throws Exception{
		String sql="select e_id,c_id,r_id,e_reservedate,e_res_comdate,e_days,e_leave_date,"
				+ "e_true_leave from ht_reserve where c_id=?";
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 根据房间号删除预定信息
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int deleteReserve(List<Object> params) throws SQLException{
		String sql="delete ht_reserve where r_id=?";
		return db.update(sql, params);
	}
	
	/**
	 * 添加预定信息
	 * 七个'?'分别是
	 * 客户编号、房间编号、何时预定的日期、预定的入住日期、入住天数、退房日期、实际的入住日期/取消预定日期 （此字段有两个功用）
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public int addReserve(List<Object> params) throws SQLException{
		String sql="insert into ht_reserve values(seq_reserve_id.nextval,?,?,"
				+ "to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,"
				+ "to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),null,null,null)";
		return db.update(sql, params);
	}
}
